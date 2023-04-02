

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

/**
 * 백준 1463번 1로 만들기
 * 문제 분류: 다이나믹 프로그래밍, BFS
 * @author Giwon
 */
public class Main_1463 {
	public static final int MAX = (int) 1E6;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();
		int[] dp = new int[MAX + 1];
		// 초기값 설정
		Arrays.fill(dp, MAX);
		dp[N] = 0;
		// BFS로 가장 적은 연산 횟수 계산
		Queue<Integer> bfsQueue = new ArrayDeque<>();
		bfsQueue.offer(N);
		int curr, temp;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			// 3으로 나누어 떨어지면 3으로 나누기
			if(curr % 3 == 0) {
				temp = curr / 3;
				if(dp[temp] > dp[curr] + 1) {
					dp[temp] = dp[curr] + 1;
					bfsQueue.offer(temp);
				}
			}
			
			// 2로 나누어 떨어지면 2로 나누기
			if(curr % 2 == 0) {
				temp = curr / 2;
				if(dp[temp] > dp[curr] + 1) {
					dp[temp] = dp[curr] + 1;
					bfsQueue.offer(temp);
				}
			}
			
			// 1빼기
			temp = curr - 1;
			if(temp > 0 && dp[temp] > dp[curr] + 1) {
				dp[temp] = dp[curr] + 1;
				bfsQueue.offer(temp);
			}
		}
		
		System.out.println(dp[1]);
		sc.close();
	}

}
