import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 7576번 토마토
 * 문제 분류: Flood Fill, BFS
 * @author Giwon
 */
public class Main_7576 {
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dRow = {0, -1, 0, 1};
	public static final int[] dCol = {-1, 0, 1, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 열 크기
		int M = Integer.parseInt(st.nextToken());
		// 행 크기
		int N = Integer.parseInt(st.nextToken());

		// 토마토 상태 입력
		Tomato[][] state = new Tomato[N][M];
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		int input;
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < M; col++) {
				input = Integer.parseInt(st.nextToken());
				if(input == 1) {
					// 익은 토마토
					state[row][col] = Tomato.getDone();
					bfsQueue.offer(new Coord(row, col, 0));
				} else if(input == -1) {
					// 빈 칸
					state[row][col] = Tomato.getBlank();
				}
			}
		}
		
		// BFS로 익은 토마토 수 탐색
		int time = 0;
		Coord curr;
		int r, c;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			time = Math.max(time, curr.time);
			
			for(int dir = 0; dir < 4; dir++) {
				r = curr.row + dRow[dir];
				c = curr.col + dCol[dir];
				
				// 범위에서 벗어난 경우 생략
				if(!checkRange(r, N) || !checkRange(c, M)) continue;
				// 이미 익은 토마토는 생략
				if(state[r][c] != null) continue;
				
				state[r][c] = Tomato.getDone();
				bfsQueue.offer(new Coord(r, c, curr.time + 1));
			}
		}
		
		if(allDone(state)) {
			System.out.println(time);
		} else {
			System.out.println(-1);
		}
		br.close();
	}
	
	// 모든 토마토가 익었는지 확인
	public static boolean allDone(Tomato[][] state) {
		for(Tomato[] inner: state) {
			for(Tomato obj: inner) {
				if(obj == null) return false;
			}
		}
		
		return true;
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}

	public static class Coord {
		// 행, 열
		int row, col;
		// 익을 때 걸린 시간
		int time;
		
		public Coord(int row, int col, int time) {
			this.row = row;
			this.col = col;
			this.time = time;
		}
	}
	
	public static class Tomato {
		// 익은 토마토
		private static Tomato DONE = new Tomato();
		// 빈 칸
		private static Tomato BLANK = new Tomato();
		private Tomato() {}
		
		public static Tomato getDone() {
			return DONE;
		}
		
		public static Tomato getBlank() {
			return BLANK;
		}
	}
	
}
