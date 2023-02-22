import java.util.Arrays;
import java.util.Scanner;

/**
 * 백준 10844번 쉬운 계단 수
 * 문제 분류: 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_10844 {

	public static final int div = 1000000000;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		
		// 0 ~ 9까지 나눠서 각각의 수열이 0 ~ 9로 끝나는 경우의 수를 나눠서 계산
		long[][] dp = new long[N][10];
		dp[0] = new long[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		for(int i = 1; i < N; i++) {
			for(int j = 0; j < 10; j++) {
				if(checkRange(j - 1)) {
					dp[i][j] += dp[i - 1][j - 1];
					dp[i][j] %= div;
				}
				if(checkRange(j + 1)) {
					dp[i][j] += dp[i - 1][j + 1];
					dp[i][j] %= div;
				}
			}
			System.out.println(Arrays.toString(dp[i]));
		}
		
		long result = 0;
		for(int i = 0; i < 10; i++) {
			result += dp[N - 1][i];
			result %= div;
		}
		
		System.out.println(result);
		sc.close();
	}
	
	public static boolean checkRange(int target) {
		return target >= 0 && target < 10;
	}

}
