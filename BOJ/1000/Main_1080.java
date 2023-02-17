import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/** 
 * 백준 1080번 행렬
 * 문제 분류: 그리디 알고리즘, 사방 탐색 (팔방 탐색)
 * @author GIWON
 */
public class Main_1080 {
	// 자신 / 북 / 북동 / 동 / 남동 / 남 / 남서 / 서 / 북서
	public static final int O = 0, N = 1, NE = 2, E = 3, SE = 4, S = 5, SW = 6, W = 7, NW = 8;
	public static final int[] dRow = {0, -1, -1, 0, 1, 1, 1, 0, -1};
	public static final int[] dCol = {0, 0, 1, 1, 1, 0, -1, -1, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		// 행
		int N = Integer.parseInt(input.split(" ")[0]);
		// 열
		int M = Integer.parseInt(input.split(" ")[1]);
		
		// 시작 행렬 A
		boolean[][] start = new boolean[N][M];
		for(int row = 0; row < N; row++) {
			input = br.readLine();
			for(int col = 0; col < M; col++) {
				start[row][col] = input.charAt(col) - '0' == 0 ? false : true;
			}
		}
		// 목표 행렬 B
		boolean[][] end = new boolean[N][M];
		for(int row = 0; row < N; row++) {
			input = br.readLine();
			for(int col = 0; col < M; col++) {
				end[row][col] = input.charAt(col) - '0' == 0 ? false : true;
			}
		}
		
		boolean[][] state = new boolean[N][M];
		// 3 x 3 행렬을 만족하는 경우에만 뒤집기 가능
		for(int row = 1; row < N - 1; row++) {
			for(int col = 1; col < M - 1; col++) {
				// 자신의 왼쪽 위가 제대로 된 값이 되도록 체크
				if(checkDiff(row - 1, col - 1, start, end) ^ checkCount(row - 1, col - 1, state, N, M)) state[row][col] = true;
			}
		}
		
		// 행렬을 전부 뒤집은 후 행렬 A, B가 같은지 확인
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				// 현재 값이 목표 값과 다르면 -1출력
				if(checkDiff(row, col, start, end) ^ checkCount(row, col, state, N, M)) {
					System.out.println(-1);
					br.close();
					return;
				}
			}
		}
		
		//현재 값과 목표 값이 같으면 뒤집은 횟수 출력
		System.out.println(countTrue(state));
		br.close();
	}
	
	// 디버깅용 배열 출력
	public static void printArr(boolean[][] arr) {
		for(boolean[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
	}
	
	// 전체 행렬 뒤집은 횟수 카운트
	public static int countTrue(boolean[][] state) {
		int count = 0;
		for(boolean[] inner: state) {
			for(boolean val: inner) {
				if(val) count++;
			}
		}
		
		return count;
	}
	
	// 현재 행, 열 위치의 값이 뒤집어져야 하는지 여부 판단
	public static boolean checkDiff(int row, int col, boolean[][] start, boolean[][] end) {
		return start[row][col] != end[row][col];
	}
	
	// 현재 행, 열 주변에서 행렬을 뒤집은 횟수 카운트
	public static boolean checkCount(int row, int col, boolean[][] state, int N, int M) {
		int r, c, count = 0;
		for(int dir = 0; dir < 9; dir++) {
			r = row + dRow[dir];
			c = col + dCol[dir];
			
			if(checkRange(r, N) && checkRange(c, M) && state[r][c]) count++;
		}
		return count % 2 == 1 ? true : false;
	}
	
	// 8방 탐색 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
}
