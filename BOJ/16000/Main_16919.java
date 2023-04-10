import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 16919번 봄버맨2
 * 문제 분류: 구현, 애드 혹
 * @author Giwon
 */
public class Main_16919 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	public static final int BLANK = 0, BOMB = 4;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 행 크기
		final int R = Integer.parseInt(st.nextToken());
		// 열 크기
		final int C = Integer.parseInt(st.nextToken());
		// 격자판 상태를 확인할 시간
		int N = Integer.parseInt(st.nextToken());
		
		// 격자판 상태 입력
		String input;
		int[][] state = new int[R][C];
		for(int row = 0; row < R; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < C; col++) {
				state[row][col] = input.charAt(col) == '.' ? BLANK : BOMB;
			}
		}
		
//		
		// 상태 저장할 리스트
		List<State> prevState = new ArrayList<>();
//		N %= 4;
		flowTime(R, C, state);
		int time = 0;
		// 상태 저장
		prevState.add(new State(time, deepCopy(R, C, state)));
		// 1초 동안 봄버맨은 아무 것도 하지 않음
		flowTime(R, C, state);
		time++;
		prevState.add(new State(time, deepCopy(R, C, state)));
		
		while(time < N) {
			// 설치되지 않은 모든 칸에 폭탄 설치
			setBomb(R, C, state);
			flowTime(R, C, state);
			time++;
			prevState.add(new State(time, deepCopy(R, C, state)));
			// 4초 주기로 상태 반복
			if(time > 4 && prevState.get(time - 4).isEqual(R, C, prevState.get(time))) {
				N -= time - 4;
				N %= 4;
				N += time - 4;
				state = prevState.get(N).bombState;
				break;
			}
		}
		
		// 폭탄 상태 출력
		printState(R, C, state);
		br.close();
	}
	
	// 현재 상태 copy
	public static int[][] deepCopy(int R, int C, int[][] state) {
		int[][] copy = new int[R][C];
		
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				copy[row][col] = state[row][col];
			}
		}
		return copy;
	}
	
	public static void debug(int R, int C, int[][] state) {
		for(int[] inner: state) {
			System.out.println(Arrays.toString(inner));
		}
	}
	
	// 현재 폭탄 상태 출력
	public static void printState(int R, int C, int[][] state) {
		StringBuilder sb = new StringBuilder();
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				sb.append(state[row][col] == BLANK ? '.' : 'O');
			}
			sb.append("\n");
		}
		
		System.out.print(sb.toString());
	}
	
	// 시간 1초 흐르게 하기
	public static void flowTime(int R, int C, int[][] state) {
		// Deep Copy
		int[][] copy = new int[R][C];
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				copy[row][col] = state[row][col];
			}
		}
		
		// 폭발 시간 1초 남은 폭탄 폭발
		int r, c;
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				// 1초 남은 폭탄이 아니라면 생략
				if(copy[row][col] != 1) continue;
				state[row][col] = BLANK;
				
				for(int dir = 0; dir < 4; dir++) {
					r = row + dRow[dir];
					c = col + dCol[dir];
					
					// 범위를 벗어난 경우 생략
					if(!checkRange(r, R) || !checkRange(c, C)) continue;
					// 인접한 영역도 빈칸으로 변경
					state[r][c] = BLANK;
				}
			}
		}
		
		// 남아 있는 폭탄 시간 1초 감소
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				// 폭탄이 아니라면 생략
				if(state[row][col] == BLANK) continue;
				state[row][col]--;
			}
		}
	}
	
	// 비어 있는 칸에 폭탄 설치
	public static void setBomb(int R, int C, int[][] state) {
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				// 빈 칸이 아니라면 생략
				if(state[row][col] != BLANK) continue;
				state[row][col] = BOMB;
			}
		}
	}

	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}
	
	public static class State {
		int time;
		int[][] bombState;
		
		public State(int time, int[][] bombState) {
			this.time = time;
			this.bombState = bombState;
		}
		
		public boolean isEqual(int R, int C, State obj) {
			for(int row = 0; row < R; row++) {
				for(int col = 0; col < C; col++) {
					if(this.bombState[row][col] != obj.bombState[row][col]) return false;
				}
			}
			
			return true;
		}
	}
}
