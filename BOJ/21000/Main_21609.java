import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 21609번 상어 중학교
 * 문제 분류: 구현, 시뮬레이션, Union Find, BFS, 완전 탐색
 * @author Giwon
 */
public class Main_21609 {
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dRow = {0, -1, 0, 1};
	public static final int[] dCol = {-1, 0, 1, 0};	
	public static final int BLACK = -1, RAINBOW = 0, BLANK = -2;
	public static int N, M;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 격자 크기
		N = Integer.parseInt(st.nextToken());
		// 색상의 개수
		M = Integer.parseInt(st.nextToken());
		
		// 격자 입력
		int[][] grid = new int[N][N]; 
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				grid[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		System.out.println(autoPlay(grid));
		br.close();
	}
	
	// 오토 플레이 후 점수 리턴
	public static int autoPlay(int[][] grid) {
		int score = 0;
		
		// 더 이상 제거할 수 있는 블록이 없을 때까지 오토 플레이
		Queue<Group> queue = new PriorityQueue<>();
		Group[][] temp, groups;
		Group target;
		// 그룹 선택
		groups = null;
		// 1 <= M <= 5;
		for(int color = 1; color <= M; color++) {
			temp = findGroup(color, grid, queue);
			// 블록 개수가 가장 큰 그룹 상태만 남기기
			if(!queue.isEmpty() && queue.peek().color == color) groups = temp;
		}
		while(!queue.isEmpty()) {
			// 점수 추가
			target = queue.poll();
			score += target.size * target.size;
			// 그룹 삭제
			removeBlocks(grid, groups, target);
			// 중력 작용
			gravity(grid);
			// 90도 반시계 방향 회전
			counterClockwise(grid);
			// 중력 작용
			gravity(grid);
			
			// 그룹 선택
			queue.clear();
			groups = null;
			// 1 <= M <= 5;
			for(int color = 1; color <= M; color++) {
				temp = findGroup(color, grid, queue);
				// 블록 개수가 가장 큰 그룹 상태만 남기기
				if(!queue.isEmpty() && queue.peek().color == color) groups = temp;
			}
		}
		
		return score;
	}
	
	// 90도 반시계 방향으로 격자 회전
	public static void counterClockwise(int[][] grid) {
		int[][] temp = new int[N][N];
		// Deep Copy
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				temp[row][col] = grid[row][col];
			}
		}
		// 격자 회전
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				grid[row][col] = temp[col][N - 1 - row];
			}
		}
	}
	
	/**
	 * 목표 블록 격자에서 제거
	 * @param grid			격자 배열
	 * @param groups		그룹 배열
	 * @param target		삭제하려는 블록 그룹
	 */
	public static void removeBlocks(int[][] grid, Group[][] groups, Group target) {
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				if(groups[row][col] == target) {
					grid[row][col] = BLANK;
				}
			}
		}
	}
	
	// 중력에 맞게 블록 내리기
	public static void gravity(int[][] grid) {
		// 아래에서부터 차례대로 중력에 맞게 내리기
		for(int row = N - 2; row >= 0; row--) {
			for(int col = 0; col < N; col++) {
				for(int iter = 0; iter < N - 1 - row; iter++) {
					if(grid[row + 1 + iter][col] == BLANK && grid[row + iter][col] > BLACK) {
						grid[row + 1 + iter][col] = grid[row + iter][col];
						grid[row + iter][col] = BLANK;
					} else break;
				}
			}
		}
	}
	
	/**
	 * 특정 색상을 기준으로 그룹을 구성했을 때 그룹들 체크
	 * @param color			탐색할 그룹 색상
	 * @param grid			격자 배열
	 * @param queue		그룹 우선순위 큐
	 * @return 				구성한 그룹 배열
	 */
	public static Group[][] findGroup(int color, int[][] grid, Queue<Group> queue) {
		Group[][] groups = new Group[N][N];
		
		Queue<Coord> bfs = new ArrayDeque<>();
		Coord curr;
		int r, c;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				// 현재 블록에 방문하지 않은 경우 그룹 체크
				if(grid[row][col] == color && groups[row][col] == null) {
					groups[row][col] = new Group(row, col, color);
					
					bfs.clear();
					bfs.offer(new Coord(row, col));
					while(!bfs.isEmpty()) {
						curr = bfs.poll();
						
						for(int dir = 0; dir < 4; dir++) {
							r = curr.row + dRow[dir];
							c = curr.col + dCol[dir];
							// 이전에 방문하지 않았고, 색상이 동일한 경우(무지개 포함) 체크
							if(checkRange(r) && checkRange(c) &&
									(grid[r][c] == groups[curr.row][curr.col].color || grid[r][c] == RAINBOW) &&
									groups[r][c] == null) {
								groups[r][c] = groups[curr.row][curr.col];
								groups[r][c].size++;
								if(grid[r][c] == RAINBOW) groups[r][c].rainbow++;
								else {
									// 기준 블록 갱신
									if(r <= groups[r][c].row) {
										if(r == groups[r][c].row) {
											// 기준 블록은 행이 같은 경우 열이 가장 작은 블록
											groups[r][c].col = Math.min(groups[r][c].col, c);
										} else {
											// 기준 블록은 행이 가장 작은 블록
											groups[r][c].row = r;
											groups[r][c].col = c;
										}
									}
								}
								
								// 갱신한 블록 기준으로 다시 탐색
								bfs.offer(new Coord(r, c));
							}	
						}
					}
					
					// 현재 그룹 우선순위 큐에 추가 (그룹에 속한 블록의 수는 2개 이상이어야 함)
					if(groups[row][col].size > 1) queue.offer(groups[row][col]);
				}
			}
		}
		
		return groups;
	}
	
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static class Group implements Comparable<Group>{
		int size, rainbow, color;
		// 기준 블록의 행, 열
		int row, col;
		
		public Group(int row, int col, int color) {
			this.size = 1;
			this.rainbow = 0;
			this.color = color;
			this.row = row;
			this.col = col;
		}

		// 우선순위 큐 정렬을 위한 Comparable 인터페이스 구현
		@Override
		public int compareTo(Group o) {
			// 블록의 크기에 따라 내림차순 정렬
			if(this.size != o.size) return Integer.compare(o.size, this.size);
			// 무지개 블록의 수에 따라 내림차순 정렬
			if(this.rainbow != o.rainbow) return Integer.compare(o.rainbow, this.rainbow);
			// 기준 블록의 행에 따라 내림차순 정렬
			if(this.row != o.row) return Integer.compare(o.row, this.row);
			// 기준 블록의 열에 따라 내림차순 정렬
			return Integer.compare(o.col, this.col);
		}
		
		// 디버깅용
		@Override
		public String toString() {
			return "[ color: " + color + " size: " + size + " rainbow: " + rainbow + " row: " + row + " col: " + col + " ]";
		}
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	// 디버깅용 배열 출력
	public static void printArr(int[][] arr) {
		StringBuilder sb = new StringBuilder();
		for(int[] inner: arr) {
			for(int val: inner) {
				sb.append((val == BLANK ? " " : val) + "| ");
			}
			sb.append("\n");
		}
		System.out.print(sb.toString());
	}
}
