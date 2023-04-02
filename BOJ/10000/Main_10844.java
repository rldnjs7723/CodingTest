

import java.util.Scanner;

/**
 * 백준 10844번 쉬운 계단 수
 * 문제 분류: 다이나믹 프로그래밍, 정수론
 * @author Giwon
 */
public class Main_10844 {
	public static final int div = 1000000000;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		
		// 길이가 N인 계단 수 중 마지막 자리가 k인 경우의 수 = dp[N][k]
		long[][] dp = new long[N + 1][10];
		// 초기값 설정
		dp[1] = new long[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		for(int i = 2; i <= N; i++) {
			for(int j = 0; j < 10; j++) {
				if(checkRange(j - 1)) {
					dp[i][j] += dp[i - 1][j - 1];
					// 나머지 연산 성질 활용
					dp[i][j] %= div;
				}
				if(checkRange(j + 1)) {
					dp[i][j] += dp[i - 1][j + 1];
					// 나머지 연산 성질 활용
					dp[i][j] %= div;
				}
			}
		}
		
		long result = 0;
		for(int i = 0; i < 10; i++) {
			result += dp[N][i];
			result %= div;
		}
		
		System.out.println(result);
		sc.close();
	}
	
	// 0 ~ 9 사이에 있는지 확인
	public static boolean checkRange(int target) {
		return target >= 0 && target < 10;
	}
}
