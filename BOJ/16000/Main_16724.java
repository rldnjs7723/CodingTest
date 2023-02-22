import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * 백준 16724번 피리 부는 사나이
 * 문제 분류: BFS, Union Find (Disjoint Set), 사방 탐색
 * @author Giwon
 */
public class Main_16724 {
	// 사방 탐색 이렇게 설정하면 반대 방향을 (curr + 2) % 4로 표현 가능
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dRow = {0, -1, 0, 1};
	public static final int[] dCol = {-1, 0, 1, 0};	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		// 행 크기
		int N = Integer.parseInt(input.split(" ")[0]);
		// 열 크기
		int M = Integer.parseInt(input.split(" ")[1]);
		// 지도 입력
		int[][] map = new int[N][M];
		for(int row = 0; row < N; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < M; col++) {
				map[row][col] = parseInput(input.charAt(col));
			}
		}
		// Union Find 기법으로 탐색
		Disjoint[][] union = new Disjoint[N][M];
		int r, c;
		Coord curr;
		Queue<Coord> queue = new ArrayDeque<>();
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				// 서로 연결되어 있는 부분 탐색
				if(union[row][col] == null) {
					// 그룹 생성
					union[row][col] = new Disjoint();
					queue.clear();
					
					queue.offer(new Coord(row, col));
					while(!queue.isEmpty()) {
						curr = queue.poll();
						
						// 좌 -> 상 -> 우 -> 하 순서로 탐색
						for(int dir = LEFT; dir <= DOWN; dir++) {
							r = curr.row + dRow[dir];
							c = curr.col + dCol[dir];
							
							// 현재 위치에서 나가는 방향이고, 탐색하지 않은 구역인 경우
							if(dir == map[curr.row][curr.col] && union[r][c] == null) {
								// 지도 밖으로 나가는 부분의 입력은 주어지지 않음
								// 그룹에 포함 (방문 체크)
								union[r][c] = union[curr.row][curr.col];
								// 현재 위치에서 탐색 반복
								queue.offer(new Coord(r, c));
								continue;
							}
							
							//현재 위치로 들어오는 방향이고, 탐색하지 않은 구역인 경우
							if(checkRange(r, N) && checkRange(c, M) && 
									union[r][c] == null && (dir + 2) % 4 == map[r][c]) {
								// 그룹에 포함 (방문 체크)
								union[r][c] = union[curr.row][curr.col];
								// 현재 위치에서 탐색 반복
								queue.offer(new Coord(r, c));
							}
						}
					}
				}
			}
		}
		
		// 전체 구역 개수 카운트
		Set<Disjoint> set = new HashSet<>();
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				set.add(union[row][col]);
			}
		}
		System.out.println(set.size());
		
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	// 지도 입력 파싱
	public static int parseInput(char input) {
		switch (input) {
			case 'L':
				return LEFT;
			case 'R':
				return RIGHT;
			case 'U':
				return UP;
			case 'D':
				return DOWN;
			default:
				return -1;
		}
	}
	
	// 서로소 집합 구성하기 위한 더미 클래스
	public static class Disjoint {}
	
	// 행, 열 좌표 저장용 클래스
	public static class Coord {
		int row, col;

		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
}
