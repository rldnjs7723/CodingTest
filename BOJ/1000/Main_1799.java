import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 1799번 비숍
 * 문제 분류: DFS, 비트마스킹, 백트래킹, 분할 정복
 * @author Giwon
 */
public class Main_1799 {
	public static final int NW = 0, NE = 1, SW = 2, SE = 3;
	public static final int[] dRow = {-1, -1, 1, 1};
	public static final int[] dCol = {-1, 1, -1, 1};
	public static final int BLACK = 0, WHITE = 1;
	public static int max;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 체스판 크기
		int N = Integer.parseInt(br.readLine());
		// 체스판 상태 입력
		StringTokenizer st;
		int[][] chess = new int[N][N]; 
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				chess[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 체스판 흑백 부분을 나눠서 체크
		int[][] odd = new int[N][N];
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				if((row + col) % 2 == 0) {
					odd[row][col] = BLACK;
				} else {
					odd[row][col] = chess[row][col];
				}
			}
		}
		
		// 체스판 흑백 부분을 나눠서 체크
		int[][] even = new int[N][N];
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				if((row + col) % 2 == 1) {
					even[row][col] = BLACK;
				} else {
					even[row][col] = chess[row][col];
				}
			}
		}
		
		int result = 0;
		// dfs로 탐색
		// 체스판 흑부분
		max = 0;
		countBishop(odd, 0, N);
		result += max;
		
		// 체스판 백부분
		max = 0;
		countBishop(even, 0, N);
		result += max;
		
		// 최대 수 출력
		System.out.println(result);
		
		br.close();
	}
	
	// 행, 열에 해당하는 비트마스크 리턴
	public static int getBitmask(int row, int col) {
		int result = 0;
		result |= (row << 4);
		result |= col;
		return result << 2;
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	// DFS로 비숍 최대 수 탐색
	public static void countBishop(int[][] chess, int count, int N) {
		// 최대 count 갱신
		max = Math.max(max, count);
		
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				// 현재 위치에 비숍을 놓을 수 있는 경우
				if(chess[row][col] == WHITE) {
					// 비숍을 놓는 경우
					putBishop(row, col, chess, N);
					countBishop(chess, count + 1, N);
					
					// 비숍을 놓지 않는 경우
					rollback(row, col, chess, N);
					chess[row][col] = BLACK;
					countBishop(chess, count, N);
					
					// 롤백
					chess[row][col] = WHITE;
					return;
				}
			}
		}
	}
	
	// 현재 행, 열에 비숍을 두었을 때 더 이상 비숍을 놓을 수 없는 곳 체크
	public static void putBishop(int row, int col, int[][] chess, int N) {
		int bitmask = getBitmask(row, col);
		// 현재 위치 체크
		chess[row][col] = bitmask;
		// 북서, 북동, 남서, 남동 방향은 전부 놓을 수 없다고 체크
		int r, c;
		for(int dir = 0; dir < 4; dir++) {
			r = row + dRow[dir];
			c = col + dCol[dir];
			while(checkRange(r, N) && checkRange(c, N)) {
				if(chess[r][c] == WHITE) chess[r][c] = bitmask;
				r += dRow[dir];
				c += dCol[dir];
			}
		}
	}
	
	// 현재 행, 열에 놓여있는 비숍 제거
	public static void rollback(int row, int col, int[][] chess, int N) {
		int bitmask = getBitmask(row, col);
		// 현재 위치 체크
		chess[row][col] = WHITE;
		// 북서, 북동, 남서, 남동 방향에 비숍을 놓아서 표기했던 부분 롤백
		int r, c;
		for(int dir = 0; dir < 4; dir++) {
			r = row + dRow[dir];
			c = col + dCol[dir];
			while(checkRange(r, N) && checkRange(c, N)) {
				if(chess[r][c] == bitmask) chess[r][c] = WHITE;
				r += dRow[dir];
				c += dCol[dir];
			}
		}
	}
	
	// 디버깅용 배열 출력
	public static void printArr(int[][] chess) {
		for(int[] inner: chess) {
			System.out.println(Arrays.toString(inner));
		}
	}
}