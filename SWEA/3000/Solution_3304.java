import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3304번 최장 공통 부분 수열
 * 문제 분류: LCS (다이나믹 프로그래밍)
 * @author Giwon
 */
public class Solution_3304 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		String A, B;
		int[][] lcsCount;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 두 문자열 입력
			A = st.nextToken();
			B = st.nextToken();
			
			lcsCount = new int[A.length() + 1][B.length() + 1];
			
			for(int i = 0; i < A.length(); i++) {
				for(int j = 0; j < B.length(); j++) {
					if (A.charAt(i) == B.charAt(j)) {
						// 현재 인덱스의 두 문자가 같으면 대각선 성분 + 1
						lcsCount[i + 1][j + 1] = lcsCount[i][j] + 1;
					} else {
						// 현재 인덱스의 두 문자가 다르면 위, 왼쪽 중 큰 값 유지
						lcsCount[i + 1][j + 1] = Math.max(lcsCount[i][j + 1], lcsCount[i + 1][j]);
					}
				}
			}
			
			System.out.println("#" + test_case + " " + lcsCount[A.length()][B.length()]);
		}
		
		br.close();
	}

}
