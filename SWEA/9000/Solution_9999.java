
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SWEA 9999번 광고 시간 정하기
 * 문제 분류: 이분 탐색 (파라미터 탐색)
 * @author Giwon
 */
public class Solution_9999 {
	public static final int MAX = (int) 1e8;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int T = Integer.parseInt(br.readLine().trim());
		int N, L;
		for(int test_case = 1; test_case <= T; test_case++) {
			
			// 각 피크 시간대의 시작점을 기준으로 광고 종료 시간 사이에 존재하는 가장 큰 종료 피크 시간대를 탐색
		}
		
		
		br.close();
	}

	public static class PeekTime {
		int s, e;
		// 현재 시간대 이전까지의 시간 총 합
		int timeSum;
		
		public PeekTime(int s, int e) {
			this.s = s;
			this.e = e;
		}
		
	}
}
