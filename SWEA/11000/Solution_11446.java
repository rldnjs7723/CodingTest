import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 11446번 사탕 가방
 * 문제 분류: 이분 탐색
 * @author Giwon
 */
public class Solution_11446 {
	public static final long MAX = (long) 1e18;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N;
		long M;
		long[] candies;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 사탕 종류
			N = Integer.parseInt(st.nextToken());
			// 가방 안에 들어있어야 하는 사탕 개수
			M = Long.parseLong(st.nextToken());
			
			// 종류별 사탕 개수 입력
			st = new StringTokenizer(br.readLine());
			candies = new long[N];
			for(int i = 0; i < N; i++) {
				candies[i] = Long.parseLong(st.nextToken());
			}
			
			// 이분 탐색으로 사탕 봉지 개수의 최댓값 구하기
			System.out.println("#" + test_case + " " + binarySearch(0, MAX, N, candies, M));
		}
		br.close();
	}
	
	// 재귀로 수행하면 Stack이 터질 수 있으므로 반복문으로 구현
	public static long binarySearch(long start, long end, int N, long[] candies, long M) {
		long mid, count;
		while(start != end) {
			mid = start + (end - start) / 2 + 1;
			count = 0;
			for(int i = 0; i < N; i++) {
				count += candies[i] / mid;
			}
			
			// 사탕 봉지에 담아야 하는 개수보다 적다면 사탕 봉지의 총 수를 줄이기
			if(count < M) end = mid - 1;
			// 사탕 봉지에 담아야 하는 개수를 만족했다면 사탕 봉지의 개수를 최대한 늘리기
			else start = mid;
		}
		
		return start;
	}
}
