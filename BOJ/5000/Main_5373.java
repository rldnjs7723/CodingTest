import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 5373번 큐빙
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_5373 {
	public static final int UP = 0, DOWN = 1, FORE = 2, BACK = 3, LEFT = 4, RIGHT = 5;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 큐브 회전 수
			N = Integer.parseInt(br.readLine().trim());
			// 큐브 생성
			Cube cube = new Cube();
			// 큐브 회전 입력
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i++) {
				cube.rotateCube(st.nextToken());
			}
			
			// 큐브 윗면 출력
			System.out.print(cube);
		}
		br.close();
	}

	public static class Cube {
		// 현재 큐브 색상 상태
		char[][][] state;
		
		public Cube() {
			// 큐브 초기화
			// 큐브 각 면은 좌상단이 (0, 0), 우하단이 (2, 2)
			state = new char[6][3][3];
			
			// 윗 면 흰색
			for(char[] inner: state[UP]) {
				Arrays.fill(inner, 'w');
			}
			// 아랫 면 노란색
			for(char[] inner: state[DOWN]) {
				Arrays.fill(inner, 'y');
			}
			// 앞 면 빨간색
			for(char[] inner: state[FORE]) {
				Arrays.fill(inner, 'r');
			}
			// 뒷 면 주황색
			for(char[] inner: state[BACK]) {
				Arrays.fill(inner, 'o');
			}
			// 왼쪽 면 초록색
			for(char[] inner: state[LEFT]) {
				Arrays.fill(inner, 'g');
			}
			// 오른쪽 면 파란색
			for(char[] inner: state[RIGHT]) {
				Arrays.fill(inner, 'b');
			}
		}
		
		public void rotateCube(String input) {
			char[] temp;
			switch (input.charAt(0)) {
				// 큐브 위쪽 회전
				case 'U':
					temp = new char[] {state[FORE][0][0], state[FORE][0][1], state[FORE][0][2]};
					if(input.charAt(1) == '+') {
						// 시계 방향 회전
						state[FORE][0][0] = state[RIGHT][0][0];
						state[FORE][0][1] = state[RIGHT][0][1];
						state[FORE][0][2] = state[RIGHT][0][2];
						
						state[RIGHT][0][0] = state[BACK][0][0];
						state[RIGHT][0][1] = state[BACK][0][1];
						state[RIGHT][0][2] = state[BACK][0][2];
						
						state[BACK][0][0] = state[LEFT][0][0];
						state[BACK][0][1] = state[LEFT][0][1];
						state[BACK][0][2] = state[LEFT][0][2];
						
						state[LEFT][0][0] = temp[0];
						state[LEFT][0][1] = temp[1];
						state[LEFT][0][2] = temp[2];
						
						// 윗면 회전
						clockwiseArr(state[UP]);
					} else {
						// 반시계 방향 회전
						state[FORE][0][0] = state[LEFT][0][0];
						state[FORE][0][1] = state[LEFT][0][1];
						state[FORE][0][2] = state[LEFT][0][2];
						
						state[LEFT][0][0] = state[BACK][0][0];
						state[LEFT][0][1] = state[BACK][0][1];
						state[LEFT][0][2] = state[BACK][0][2];
						
						state[BACK][0][0] = state[RIGHT][0][0];
						state[BACK][0][1] = state[RIGHT][0][1];
						state[BACK][0][2] = state[RIGHT][0][2];
						
						state[RIGHT][0][0] = temp[0];
						state[RIGHT][0][1] = temp[1];
						state[RIGHT][0][2] = temp[2];
						
						// 윗면 회전
						counterClockwiseArr(state[UP]);
					}
					break;
					
				// 큐브 아래쪽 회전
				case 'D':
					temp = new char[] {state[FORE][2][0], state[FORE][2][1], state[FORE][2][2]};
					if(input.charAt(1) == '+') {
						// 시계 방향 회전
						state[FORE][2][0] = state[LEFT][2][0];
						state[FORE][2][1] = state[LEFT][2][1];
						state[FORE][2][2] = state[LEFT][2][2];
						
						state[LEFT][2][0] = state[BACK][2][0];
						state[LEFT][2][1] = state[BACK][2][1];
						state[LEFT][2][2] = state[BACK][2][2];
						
						state[BACK][2][0] = state[RIGHT][2][0];
						state[BACK][2][1] = state[RIGHT][2][1];
						state[BACK][2][2] = state[RIGHT][2][2];
						
						state[RIGHT][2][0] = temp[0];
						state[RIGHT][2][1] = temp[1];
						state[RIGHT][2][2] = temp[2];
						
						// 아랫면 회전
						clockwiseArr(state[DOWN]);
					} else {
						// 반시계 방향 회전
						state[FORE][2][0] = state[RIGHT][2][0];
						state[FORE][2][1] = state[RIGHT][2][1];
						state[FORE][2][2] = state[RIGHT][2][2];
						
						state[RIGHT][2][0] = state[BACK][2][0];
						state[RIGHT][2][1] = state[BACK][2][1];
						state[RIGHT][2][2] = state[BACK][2][2];
						
						state[BACK][2][0] = state[LEFT][2][0];
						state[BACK][2][1] = state[LEFT][2][1];
						state[BACK][2][2] = state[LEFT][2][2];
						
						state[LEFT][2][0] = temp[0];
						state[LEFT][2][1] = temp[1];
						state[LEFT][2][2] = temp[2];
						
						// 아랫면 회전
						counterClockwiseArr(state[DOWN]);
					}
					break;
					
				// 큐브 앞쪽 회전
				case 'F':
					temp = new char[] {state[UP][2][0], state[UP][2][1], state[UP][2][2]};
					if(input.charAt(1) == '+') {
						// 시계 방향 회전
						state[UP][2][0] = state[LEFT][2][2];
						state[UP][2][1] = state[LEFT][1][2];
						state[UP][2][2] = state[LEFT][0][2];
						
						state[LEFT][2][2] = state[DOWN][0][2];
						state[LEFT][1][2] = state[DOWN][0][1];
						state[LEFT][0][2] = state[DOWN][0][0];
						
						state[DOWN][0][2] = state[RIGHT][0][0];
						state[DOWN][0][1] = state[RIGHT][1][0];
						state[DOWN][0][0] = state[RIGHT][2][0];
						
						state[RIGHT][0][0] = temp[0];
						state[RIGHT][1][0] = temp[1];
						state[RIGHT][2][0] = temp[2];
						
						// 앞면 회전
						clockwiseArr(state[FORE]);
					} else {
						// 반시계 방향 회전
						state[UP][2][0] = state[RIGHT][0][0];
						state[UP][2][1] = state[RIGHT][1][0];
						state[UP][2][2] = state[RIGHT][2][0];
						
						state[RIGHT][0][0] = state[DOWN][0][2];
						state[RIGHT][1][0] = state[DOWN][0][1];
						state[RIGHT][2][0] = state[DOWN][0][0];
						
						state[DOWN][0][2] = state[LEFT][2][2];
						state[DOWN][0][1] = state[LEFT][1][2];
						state[DOWN][0][0] = state[LEFT][0][2];
						
						state[LEFT][2][2] = temp[0];
						state[LEFT][1][2] = temp[1];
						state[LEFT][0][2] = temp[2];
						
						// 앞면 회전
						counterClockwiseArr(state[FORE]);
					}
					break;
					
				// 큐브 뒤쪽 회전
				case 'B':
					temp = new char[] {state[UP][0][0], state[UP][0][1], state[UP][0][2]};
					if(input.charAt(1) == '+') {
						// 시계 방향 회전
						state[UP][0][0] = state[RIGHT][0][2];
						state[UP][0][1] = state[RIGHT][1][2];
						state[UP][0][2] = state[RIGHT][2][2];
						
						state[RIGHT][0][2] = state[DOWN][2][2];
						state[RIGHT][1][2] = state[DOWN][2][1];
						state[RIGHT][2][2] = state[DOWN][2][0];
						
						state[DOWN][2][2] = state[LEFT][2][0];
						state[DOWN][2][1] = state[LEFT][1][0];
						state[DOWN][2][0] = state[LEFT][0][0];
						
						state[LEFT][2][0] = temp[0];
						state[LEFT][1][0] = temp[1];
						state[LEFT][0][0] = temp[2];
						
						// 뒷면 회전
						clockwiseArr(state[BACK]);
					} else {
						// 반시계 방향 회전
						state[UP][0][0] = state[LEFT][2][0];
						state[UP][0][1] = state[LEFT][1][0];
						state[UP][0][2] = state[LEFT][0][0];
						
						state[LEFT][2][0] = state[DOWN][2][2];
						state[LEFT][1][0] = state[DOWN][2][1];
						state[LEFT][0][0] = state[DOWN][2][0];
						
						state[DOWN][2][2] = state[RIGHT][0][2];
						state[DOWN][2][1] = state[RIGHT][1][2];
						state[DOWN][2][0] = state[RIGHT][2][2];
						
						state[RIGHT][0][2] = temp[0];
						state[RIGHT][1][2] = temp[1];
						state[RIGHT][2][2] = temp[2];
						
						// 뒷면 회전
						counterClockwiseArr(state[BACK]);
					}
					break;
					
				// 큐브 왼쪽 회전
				case 'L':
					temp = new char[] {state[UP][0][0], state[UP][1][0], state[UP][2][0]};
					if(input.charAt(1) == '+') {
						// 시계 방향 회전
						state[UP][0][0] = state[BACK][2][2];
						state[UP][1][0] = state[BACK][1][2];
						state[UP][2][0] = state[BACK][0][2];
						
						state[BACK][2][2] = state[DOWN][0][0];
						state[BACK][1][2] = state[DOWN][1][0];
						state[BACK][0][2] = state[DOWN][2][0];
						
						state[DOWN][0][0] = state[FORE][0][0];
						state[DOWN][1][0] = state[FORE][1][0];
						state[DOWN][2][0] = state[FORE][2][0];
						
						state[FORE][0][0] = temp[0];
						state[FORE][1][0] = temp[1];
						state[FORE][2][0] = temp[2];
						
						// 왼쪽면 회전
						clockwiseArr(state[LEFT]);
					} else {
						// 반시계 방향 회전
						state[UP][0][0] = state[FORE][0][0];
						state[UP][1][0] = state[FORE][1][0];
						state[UP][2][0] = state[FORE][2][0];
						
						state[FORE][0][0] = state[DOWN][0][0];
						state[FORE][1][0] = state[DOWN][1][0];
						state[FORE][2][0] = state[DOWN][2][0];
						
						state[DOWN][0][0] = state[BACK][2][2];
						state[DOWN][1][0] = state[BACK][1][2];
						state[DOWN][2][0] = state[BACK][0][2];
						
						state[BACK][2][2] = temp[0];
						state[BACK][1][2] = temp[1];
						state[BACK][0][2] = temp[2];
						
						// 왼쪽면 회전
						counterClockwiseArr(state[LEFT]);
					}
					break;
					
				// 큐브 오른쪽 회전
				case 'R':
					temp = new char[] {state[UP][0][2], state[UP][1][2], state[UP][2][2]};
					if(input.charAt(1) == '+') {
						// 시계 방향 회전
						state[UP][0][2] = state[FORE][0][2];
						state[UP][1][2] = state[FORE][1][2];
						state[UP][2][2] = state[FORE][2][2];
						
						state[FORE][0][2] = state[DOWN][0][2];
						state[FORE][1][2] = state[DOWN][1][2];
						state[FORE][2][2] = state[DOWN][2][2];
						
						state[DOWN][0][2] = state[BACK][2][0];
						state[DOWN][1][2] = state[BACK][1][0];
						state[DOWN][2][2] = state[BACK][0][0];
						
						state[BACK][2][0] = temp[0];
						state[BACK][1][0] = temp[1];
						state[BACK][0][0] = temp[2];
						
						// 오른쪽면 회전
						clockwiseArr(state[RIGHT]);
					} else {
						// 반시계 방향 회전
						state[UP][0][2] = state[BACK][2][0];
						state[UP][1][2] = state[BACK][1][0];
						state[UP][2][2] = state[BACK][0][0];
						
						state[BACK][2][0] = state[DOWN][0][2];
						state[BACK][1][0] = state[DOWN][1][2];
						state[BACK][0][0] = state[DOWN][2][2];
						
						state[DOWN][0][2] = state[FORE][0][2];
						state[DOWN][1][2] = state[FORE][1][2];
						state[DOWN][2][2] = state[FORE][2][2];
						
						state[FORE][0][2] = temp[0];
						state[FORE][1][2] = temp[1];
						state[FORE][2][2] = temp[2];
						
						// 오른쪽면 회전
						counterClockwiseArr(state[RIGHT]);
					}
					break;
				default:
					break;
			}
		}
		
		// 배열 시계 방향으로 90도 회전
		public static void clockwiseArr(char[][] arr) {
			char[][] result = new char[3][3];
			
			for(int row = 0; row < 3; row++) {
				for(int col = 0; col < 3; col++) {
					result[row][col] = arr[2 - col][row];
				}
			}
			
			for(int row = 0; row < 3; row++) {
				for(int col = 0; col < 3; col++) {
					arr[row][col] = result[row][col];
				}
			}
		}
		
		// 배열 반시계 방향으로 90도 회전
		public static void counterClockwiseArr(char[][] arr) {
			char[][] result = new char[3][3];
			
			for(int row = 0; row < 3; row++) {
				for(int col = 0; col < 3; col++) {
					result[row][col] = arr[col][2 - row];
				}
			}
			
			for(int row = 0; row < 3; row++) {
				for(int col = 0; col < 3; col++) {
					arr[row][col] = result[row][col];
				}
			}
		}
		
		public void print() {
			for(int i = 0; i < 6; i++) {
				for(char[] inner: state[i]) {
					System.out.println(Arrays.toString(inner));
				}
				System.out.println("------");
			}
			System.out.println("DONE");
		}
		
		// 윗면 출력용
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int row = 0; row < 3; row++) {
				for(int col = 0; col < 3; col++) {
					sb.append(state[UP][row][col]);
				}
				sb.append("\n");
			}
			
			return sb.toString();
		}
	}
}
