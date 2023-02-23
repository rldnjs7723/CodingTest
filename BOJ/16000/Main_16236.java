import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 16236번 아기 상어
 * 문제 분류: BFS, 우선순위 큐, 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_16236 {
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	public static final int[] dRow = {-1, 0, 1, 0};
	public static final int[] dCol = {0, 1, 0, -1};
	public static int N, R, C;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 공간 크기
		N = Integer.parseInt(br.readLine());
		// 공간 초기 상태 입력
		Fish[][] map = new Fish[N][N];
		int val = 0;
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				val = Integer.parseInt(st.nextToken());
				// 아기 상어 위치 체크
				if(val == 9) {
					R = row;
					C = col;
					map[row][col] = new Fish(row, col, 0, 0);
				} else {
					map[row][col] = new Fish(row, col, val, 0);
				}
			}
		}
		// 상어 시작 크기 2
		int size = 2;
		int time = 0;
		int count = 0;
		// 가장 가까운 생선 탐색용 큐
		Queue<Fish> closest = new PriorityQueue<>();
		// BFS용 큐
		Queue<Fish> queue = new ArrayDeque<>();
		// 방문 체크용 배열
		boolean[][] visited = new boolean[N][N];
		Fish curr, next;
		do {
			// 먹을 수 있는 생선 먹기
			if(closest.size() > 0) {
				curr = closest.poll();
				map[curr.row][curr.col].size = 0;
				// 크기와 같은 개수의 생선을 먹으면 크기 증가
				count++;
				if(count == size) {
					size++;
					count = 0;
				}
				R = curr.row;
				C = curr.col;
				time += curr.distance;
			}
			
			// 주변에 먹을 수 있는 생선 위치를 BFS로 탐색
			closest.clear();
			queue.clear();
			for(boolean[] inner: visited) {
				Arrays.fill(inner, false);
			}
			// 상어 시작 위치 대기열에 추가
			queue.add(new Fish(R, C, 0, 0));
			int r, c;
			while(!queue.isEmpty()) {
				curr = queue.poll();
				
				for(int dir = 0; dir < 4; dir++) {
					r = curr.row + dRow[dir];
					c = curr.col + dCol[dir];
					
					// 방문하지 않았고 자신보다 작은 크기의 물고기가 있는 경우 탐색
					if(checkRange(r) && checkRange(c) &&
							!visited[r][c] && map[r][c].size <= size) {
						next = map[r][c];
						next.distance = curr.distance + 1;
						queue.offer(next);
						// 상어보다 작은 생선이라면 우선순위 큐에 넣기
						if(next.size > 0 && next.size < size) closest.offer(next);
						// 방문 체크
						visited[r][c] = true;
					}
				}
			}
		} while (!closest.isEmpty());
		
		System.out.println(time);
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	
	public static class Fish implements Comparable<Fish>{
		int row, col, size, distance;
		
		public Fish(int row, int col, int size, int distance) {
			this.row = row;
			this.col = col;
			this.size = size;
			this.distance = distance;
		}
		
		// 우선순위 큐를 사용하기 위한 Comparable 인터페이스 구현
		@Override
		public int compareTo(Fish o) {
			// 거리가 가까운 물고기를 먼저 선택
			if(this.distance != o.distance) return Integer.compare(this.distance, o.distance);
			// 거리가 같다면 가장 위에 있는 물고기를 먼저 선택
			if(this.row != o.row) return Integer.compare(this.row, o.row);
			// 행이 같다면 가장 왼쪽에 있는 물고기를 먼저 선택
			return Integer.compare(this.col, o.col);
		}
		
		// 디버깅용
		@Override
		public String toString() {
			return "[ row: " + row + " col : " + col + " ]";
		}
	}
}
