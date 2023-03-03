import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 7569번 토마토
 * 문제 분류: Flood Fill, BFS
 * @author Giwon
 */
public class Main_7569 {
	// 6방 탐색
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, FRONT = 4, BACK = 5;
	public static final int[] dRow = {0, 0, 0, 0, 1, -1};
	public static final int[] dCol = {0, 0, -1, 1, 0, 0};
	public static final int[] dHeight = {1, -1, 0, 0, 0, 0};
	public static int M, N, H;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 열 크기
		M = Integer.parseInt(st.nextToken());
		// 행 크기
		N = Integer.parseInt(st.nextToken());
		// 높이
		H = Integer.parseInt(st.nextToken());
		
		// 토마토 상자 상태 입력
		Tomato[][][] state = new Tomato[H][N][M];
		// 토마토 좌표를 담은 Queue
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		int temp;
		for(int height = 0; height < H; height++) {
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < M; col++) {
					temp = Integer.parseInt(st.nextToken());
					// 익은 토마토
					if(temp == 1) {
						state[height][row][col] = Tomato.getInstance();
						bfsQueue.offer(new Coord(height, row, col, 0));
					}
					// 빈 칸
					else if(temp == -1) state[height][row][col] = Tomato.getBlank();
				}
			}
		}
		
		// BFS로 익을 수 있는 토마토가 없을 때까지 탐색
		int time = 0;
		Coord curr;
		int height, row, col;
		while(!bfsQueue.isEmpty()) {
			// 익은 토마토 위치
			curr = bfsQueue.poll();
			height = curr.height;
			row = curr.row;
			col = curr.col;
			time = Math.max(time, curr.time);
			
			// 빈 칸인 경우 빈 칸 유지
			if(state[height][row][col] == Tomato.getBlank()) continue; 
			
			// 익은 토마토라면 주변 6방향 토마토 익게 만들기
			int h, r, c;
			for(int dir = 0; dir < 6; dir++) {
				h = height + dHeight[dir];
				r = row + dRow[dir];
				c = col + dCol[dir];
				
				// 범위를 넘은 경우 다음 방향 탐색
				if(!checkRange(h, H) || !checkRange(r, N) || !checkRange(c, M)) continue;
				
				if(state[h][r][c] == null) {
					// 주변에 익지 않은 토마토가 있는 경우
					state[h][r][c] = Tomato.getInstance();
					bfsQueue.offer(new Coord(h, r, c, curr.time + 1));
				}
			}
		}
		
		if(Tomato.isAllTomatoRiped(state)) {
			// 모든 토마토가 익은 경우
			System.out.println(time);
		} else {
			// 모든 토마토가 익을 수 없는 경우
			System.out.println(-1);
		}
		br.close();
	}
	
	// 배열 null로 초기화
	public static void resetArray(Tomato[][][] state) {
		for(int height = 0; height < H; height++) {
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < M; col++) {
					state[height][row][col] = null;
				}
			}
		}
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	// BFS용 좌표
	public static class Coord {
		int height, row, col, time;

		public Coord(int height, int row, int col, int time) {
			this.height = height;
			this.row = row;
			this.col = col;
			this.time = time;
		}
		
		@Override
		public String toString() {
			return "[ h: " + height + " r: " + row + " c: " + col + " t: " + time + " ]";
		}
	}

	public static class Tomato {
		// 익은 상태를 나타내기 위해 Singleton
		private static Tomato instance = new Tomato();
		private static Tomato BLANK = new Tomato();
		private Tomato() {}
		
		public static Tomato getInstance() {
			return instance;
		}
		
		public static Tomato getBlank() {
			return BLANK;
		}
		
		// 모든 토마토가 익었는지 체크
		public static boolean isAllTomatoRiped(Tomato[][][] state) {
			for(Tomato[][] box: state) {
				for(Tomato[] line: box) {
					for(Tomato tomato: line) {
						// 익지 않은 토마토가 있는 경우
						if(tomato == null) return false;
					}
				}
			}
			// 익지 않은 토마토가 없다면 참
			return true;
		}
	}
}
