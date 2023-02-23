import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// TODO
/**
 * SWEA 1767번 프로세서 연결하기
 * 문제 분류: 백트래킹
 * @author Giwon
 */
public class Solution_1767 {
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	public static final int[] dRow = {-1, 0, 1, 0};
	public static final int[] dCol = {0, 1, 0, -1};
	public static final int BLANK = 0, CORE = 1;
	public static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 테스트 케이스 개수
		int T = Integer.parseInt(br.readLine());
		int[][] maxinos, nextDirection;
		int coreCount;
		long combination;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 멕시노스 크기
			N = Integer.parseInt(br.readLine());
			// 멕시노스 코어 상태 입력
			coreCount = 0;
			maxinos = new int[N][N];
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < N; col++) {
					maxinos[row][col] = Integer.parseInt(st.nextToken());
					// 코어의 개수 세기
					if(row > 0 && row < N - 1 && col > 0 && col < N - 1 && maxinos[row][col] == CORE) coreCount++;
				}
			}
			
			// 일단 완전 탐색으로 연결한 최대 코어 수 세기
			
			

			
			
			
			
		}
		
		
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}

}
