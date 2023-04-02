

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 17070번 파이프 옮기기 2
 * 문제 분류: 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_17069 {
	public static final int E = 0, SE = 1, S = 2;
	public static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine().trim());
		State[][] state = new State[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				state[row][col] = new State(Integer.parseInt(st.nextToken()) == 0);
			}
		}
		
		// 시작점 초기화
		state[0][1].count[E] = 1;
		Coord curr, next;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				for(int dir = 0; dir < 3; dir++) {
					// 현재 위치에 현재 방향으로 들어올 수 없다면 생략
					if(state[row][col].count[dir] == 0) continue;
					
					curr = new Coord(row, col, dir);
					for(int rotate = 0; rotate < 3; rotate++) {
						next = curr.move(rotate, state);
						// 현재 방향으로 이동, 회전이 불가능하면 생략
						if(next == null) continue;
						
						// 도달할 수 있는 위치에 경우의 수 추가
						state[next.row][next.col].count[next.dir] += state[row][col].count[dir];
					}
				}
			}
		}
		
		System.out.println(state[N - 1][N - 1].getCount());
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	
	public static class Coord {
		int row, col, dir;
		
		public Coord(int row, int col, int dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		public Coord move(int rotate, State[][] state) {
			// 이동 위치 계산
			int R = -1, C = -1;
			switch (dir) {
				// 45도만 회전 가능
				case E:
					if(rotate == E) {
						R = row;
						C = col + 1;
					} else if(rotate == SE) {
						R = row + 1;
						C = col + 1;
					}
					break;
				case S:
					if(rotate == S) {
						R = row + 1;
						C = col;
					} else if(rotate == SE) {
						R = row + 1;
						C = col + 1;
					}
					break;
				case SE:
					if(rotate == E) {
						R = row;
						C = col + 1;
					} else if(rotate == SE) {
						R = row + 1;
						C = col + 1;
					} else if(rotate == S) {
						R = row + 1;
						C = col;
					}
					break;
			}
			
			// 격자 범위를 벗어나면 생략
			if(!checkRange(R) || !checkRange(C)) return null;
			
			// 이동 위치에 벽이 있으면 생략
			if(!state[R][C].blank) return null;
			if(rotate == SE) {
				if(!state[R - 1][C].blank || !state[R][C - 1].blank) return null;
			}
			
			return new Coord(R, C, rotate);
		}
	}
	
	public static class State {
		long[] count;
		boolean blank;
		
		public State(boolean blank) {
			count = new long[3];
			this.blank = blank;
		}
		
		public long getCount() {
			long result = 0;
			for(long val: count) {
				result += val;
			}
			
			return result;
		}
		
		@Override
		public String toString() {
			return Long.toString(getCount());
		}
	}
	
}
