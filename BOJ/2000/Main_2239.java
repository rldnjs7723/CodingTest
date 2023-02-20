import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 백준 2239번 스도쿠
 * 문제 분류: 비트마스킹, 백트래킹
 * @author GIWON
 */
public class Main_2239 {
	public static final int SIZE = 9;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		// 입력으로 주어진 초기 스도쿠 상태
		int[][] start = new int[SIZE][SIZE];
		// 스도쿠 행, 열, 보드 별 비트마스크
		Sudoku sudoku = new Sudoku();
		for(int row = 0; row < SIZE; row++) {
			input = br.readLine();
			for(int col = 0; col < SIZE; col++) {
				start[row][col] = input.charAt(col) - '0';
				// 기존에 주어진 부분 할당
				if(start[row][col] > 0) sudoku.check(row, col, start[row][col]);
			}
		}
		
		// 딥 카피
		int[][] state = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++) {
			state[i] = Arrays.copyOf(start[i], SIZE);
		}
		
		// 백트래킹으로 입력할 수 없다면 되돌아가서 다시 값 입력
		int row = 0, col = 0, val;
		while(row < SIZE) {
			// 빈칸 채워넣기
			if(start[row][col] == 0) {
				val = sudoku.findVal(row, col, state[row][col]);
				
				// 넣을 수 있는 값이 없다면 롤백 수행
				if(val == -1) {
					state[row][col] = 0;
					do {
						if(col == 0) {
							row--;
							col = SIZE - 1;
						} else {
							col--;
						}
					} while(start[row][col] != 0);
					sudoku.rollback(row, col, state[row][col]);
					continue;
				}
				
				// 넣을 수 있는 값이 있다면 체크
				state[row][col] = val;
				sudoku.check(row, col, state[row][col]);
			}
			
			// 다음 행, 열로 진행
			if(col == SIZE - 1) {
				row++;
				col = 0;
			} else {
				col++;
			}
		}
		
		// 완성된 스도쿠 출력
		StringBuilder sb = new StringBuilder();
		for(int[] inner: state) {
			for(int value: inner) {
				sb.append(value);
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	public static class Sudoku {
		int[] rowBitmask, colBitmask, squareBitmask;
		
		public Sudoku() {
			this.rowBitmask = new int[SIZE];
			this.colBitmask = new int[SIZE];
			this.squareBitmask = new int[SIZE];
		}
		
		// 현재 행, 열, 보드에 지정된 수를 비트마스킹
		public void check(int row, int col, int val) {
			int bit = 1 << (val - 1);
			rowBitmask[row] |= bit;
			colBitmask[col] |= bit;
			squareBitmask[3 * (row / 3) + col / 3] |= bit;
		}
		// 현재 행, 열, 보드에 체크된 비트 롤백
		public void rollback(int row, int col, int val) {
			int bit = 1 << (val - 1);
			rowBitmask[row] -= bit;
			colBitmask[col] -= bit;
			squareBitmask[3 * (row / 3) + col / 3] -= bit;
		}
		// 현재 행, 열, 보드에 지정된 수가 체크되어 있는지 확인
		public boolean duplicate(int row, int col, int val) {
			int bit = 1 << (val - 1);
			return ((rowBitmask[row] & bit) > 0) || 
						((colBitmask[col] & bit) > 0) ||
						((squareBitmask[3 * (row / 3) + col / 3] & bit) > 0);
		}
		// 현재 행, 열에 넣을 수 있는 가장 작은 값을 반환
		public int findVal(int row, int col, int val) {
			for(int i = val + 1; i <= SIZE; i++) {
				if(!duplicate(row, col, i)) return i;
			}
			return -1;
		}
		
		// 디버깅용 문자열 변환
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("행\n");
			for(int val: rowBitmask) {
				sb.append(Integer.toBinaryString(val) + "\n");
			}
			sb.append("열\n");
			for(int val: colBitmask) {
				sb.append(Integer.toBinaryString(val) + "\n");
			}
			sb.append("보드\n");
			for(int val: squareBitmask) {
				sb.append(Integer.toBinaryString(val) + "\n");
			}
			return sb.toString();
		}
	}
}
