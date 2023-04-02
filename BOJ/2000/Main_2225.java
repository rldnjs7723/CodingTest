

import java.util.Scanner;

/**
 * 백준 2225번 합분해
 * 문제 분류: 다이나믹 프로그래밍, 정수론
 * @author Giwon
 */
public class Main_2225 {
	public static final int DIV = (int) 1E9;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		final int N = sc.nextInt();
		final int K = sc.nextInt();
		
		long[][] dp = new long[N + 1][K + 1];
		// 초기값 설정
		for(int i = 0; i <= N; i++) {
			// 각각의 수를 1번 더해서 해당 수를 만드는 경우
			dp[i][1] = 1;
		}
		
		for(int target = 0; target <= N; target++) {
			for(int count = 2; count <= K; count++) {
				for(int num = 0; num <= target; num++) {
					// count개의 숫자를 더하여 target을 만드는  경우의 수
					dp[target][count] += dp[target - num][count - 1] * dp[num][1];
					// 나머지 연산의 성질 활용
					dp[target][count] %= DIV;
				}
			}
		}

		System.out.println(dp[N][K]);
		sc.close();
	}

}
