import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 백준 19237번 어른 상어
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_19237 {
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public static final int[] dRow = {-1, 1, 0, 0};
	public static final int[] dCol = {0, 0, -1, 1};
	public static int N, M, k;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 격자 크기
		N = Integer.parseInt(st.nextToken());
		// 상어 수
		M = Integer.parseInt(st.nextToken());
		// 냄새가 사라지기 까지 걸리는 시간
		k = Integer.parseInt(st.nextToken());
		
		// 상어 위치 입력
		int[][] grid = new int[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				grid[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		// 상어 방향 입력
		int[] dirs = new int[M];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < M; i++) {
			dirs[i] = Integer.parseInt(st.nextToken());
		}
		
		// 상어 객체 생성
		Shark[] sharks = new Shark[M];
		Smell[][] smell = new Smell[N][N]; 
		int idx;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				if(grid[row][col] > 0) {
					idx = grid[row][col] - 1;
					sharks[idx] = new Shark(idx, dirs[idx], row, col);
					// 냄새 남기기
					smell[row][col] = new Smell(idx, k);
				}
			}
		}
		
		// 상어 방향 우선순위 입력
		for(int i = 0; i < M; i++) {
			for(int dir = 0; dir < 4; dir++) {
				sharks[i].parsePriority(dir, br.readLine());
			}
		}
		// 상어를 하나만 남기기 위한 우선순위 큐 초기화
		Room[][] rooms = new Room[N][N];
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				rooms[row][col] = new Room();
			}
		}
		
		int time = 0;
		for(time = 0; time <= 1000; time++) {
			// 상어가 한 마리만 남았다면 중단
			if(Shark.sharkCount(sharks) == 1) break;
			// 모든 존재하는 상어 이동
			Shark.moveShark(sharks, smell, rooms);
//			printArr(smell);
		}
		
		// 1000초가 넘어도 다른 상어가 존재한다면 -1 출력
		System.out.println(time > 1000 ? -1 : time);
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	
	// 디버깅용 배열 출력
	public static void printArr(Object[][] arr) {
		for(Object[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
		System.out.println("--------------");
	}
	
	// 번호가 가장 작은 상어만 남기기 위한 우선순위 큐
	@SuppressWarnings("serial")
	public static class Room extends PriorityQueue<Shark>{}
	
	public static class Smell {
		// 냄새를 뿌린 상어 번호
		int idx;
		// 냄새가 사라지기까지 남은 시간
		int time;
		
		public Smell(int idx, int time) {
			this.idx = idx;
			this.time = time;
		}
		
		// 1초 후 냄새가 사라질지 여부를 리턴
		public boolean afterOneSec() {
			this.time--;
			if(this.time == 0) return true;
			return false;
		}
		
		@Override
		public String toString() {
			return Integer.toString(this.idx);
		}
	}
	
	public static class Shark implements Comparable<Shark>{
		// 상어 번호
		int idx;
		// 상어가 바라보는 방향
		int direction;
		// 상어 위치
		int row, col;
		// 상어별 바라보는 방향에 따른 우선 순위
		int[][] priority;
		// 상어가 남아있는지 여부 체크
		boolean exist;
		
		public Shark(int idx, int direction, int row, int col) {
			this.idx = idx;
			// 방향 0부터 시작하게 바꾸기
			this.direction = direction - 1;
			this.row = row;
			this.col = col;
			this.priority = new int[4][4];
			this.exist = true;
		}
		
		// 상어의 방향 우선순위 입력 파싱
		public void parsePriority(int dir, String input) {
			StringTokenizer st = new StringTokenizer(input);
			for(int i = 0; i < 4; i++) {
				// 방향 0부터 시작하게 바꾸기
				priority[dir][i] = Integer.parseInt(st.nextToken()) - 1;
			}
		}
		
		// 번호 기준으로 오름차순 정렬
		@Override
		public int compareTo(Shark o) {
			return Integer.compare(this.idx, o.idx);
		}
		
		@Override
		public String toString() {
			return Integer.toString(this.idx);
		}
		
		// 상어 이동
		public void move(Smell[][] smell, Room[][] rooms) {
			// 인접한 칸 중 아무 냄새가 없는 칸을 탐색
			int r, c;
			for(int dir: priority[this.direction]) {
				r = this.row + dRow[dir];
				c = this.col + dCol[dir];
				
				// 범위에서 벗어나면 다른 방향 탐색
				if(!checkRange(r) || !checkRange(c)) continue;
				
				// 아무 냄새가 없다면 해당 칸으로 이동
				if(smell[r][c] == null) {
					this.direction = dir;
					this.row = r;
					this.col = c;
					this.exist = false;
					rooms[r][c].offer(this);
					
					return;
				}
			}
			
			// 인접한 칸 중에서 자신의 냄새가 있는 칸을 탐색
			for(int dir: priority[this.direction]) {
				r = this.row + dRow[dir];
				c = this.col + dCol[dir];
				
				// 범위에서 벗어나면 다른 방향 탐색
				if(!checkRange(r) || !checkRange(c)) continue;
				
				// 자신의 냄새가 난다면 해당 칸으로 이동
				if(smell[r][c].idx == this.idx) {
					this.direction = dir;
					this.row = r;
					this.col = c;
					this.exist = false;
					rooms[r][c].offer(this);
					
					return;
				}
			}
			
			// 상어는 항상 이동할 수 있다는 조건
		}
		
		public static void moveShark(Shark[] sharks, Smell[][] smell, Room[][] rooms) {
			// 존재하는 모든 상어 이동
			for(Shark shark: sharks) {
				if(shark.exist) shark.move(smell, rooms);
			}
			
			// 냄새 시간 줄이기
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					if(smell[row][col] != null && smell[row][col].afterOneSec()) {
						// 냄새가 사라질 시간이 된 경우 없애기
						smell[row][col] = null;
					}
				}
			}
			
			// 각 방에 위치한 상어 중, 번호가 가장 작은 상어만 남기기
			Shark curr;
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					// 현재 방에 상어가 존재하지 않으면 생략
					if(rooms[row][col].isEmpty()) continue;
						
					curr = rooms[row][col].poll();
					rooms[row][col].clear();
					// 한 마리의 상어만 생존
					curr.exist = true;
					
					// 냄새 갱신
					if(smell[curr.row][curr.col] == null) smell[curr.row][curr.col] = new Smell(curr.idx, k);
					else smell[curr.row][curr.col].time = k;
				}
			}
		}
		
		// 상어 마리 수 리턴
		public static int sharkCount(Shark[] sharks) {
			int count = 0;
			for(Shark shark: sharks) {
				if(shark.exist) count++;
			}
			return count;
		}
	}
}
