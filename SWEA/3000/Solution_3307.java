import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 3307번 최장 증가 부분 수열
 * 문제 분류: 다이나믹 프로그래밍 (LIS), 이분 탐색
 * @author Giwon
 */
public class Solution_3307 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N;
		int[] lisDP;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 수열 길이
			N = Integer.parseInt(br.readLine().trim());
			// 수열 입력
			st = new StringTokenizer(br.readLine());
			int val, idx;
			int size = 0;
			lisDP = new int[N];
			for(int i = 0; i < N; i++) {
				val = Integer.parseInt(st.nextToken());
				// 이분 탐색으로 들어갈 수 있는 위치 탐색
				idx = Arrays.binarySearch(lisDP, 0, size, val);
				if(idx < 0) idx = -idx - 1;
				
				// 현재 수가 가장 크다면 맨 뒤에 넣기
				if(idx == size) lisDP[size++] = val;
				// 아니라면 현재 위치에 있는 수 대체
				else lisDP[idx] = val;
			}
			
			System.out.println("#" + test_case + " " + size);
		}
		br.close();
	}

}
