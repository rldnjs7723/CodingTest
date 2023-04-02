

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 1149번 RGB거리
 * 문제 분류: 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_1149 {
	public static final int R = 0, G = 1, B = 2;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int N = Integer.parseInt(br.readLine().trim());
		StringTokenizer st = new StringTokenizer(br.readLine());
		// N번 집을 i번 색으로 칠할 때의 최소 비용 = cost[N - 1][i];
		int[][] cost = new int[N][3];
		// 초기값 설정
		for(int[] inner: cost) {
			Arrays.fill(inner, Integer.MAX_VALUE);
		}
		cost[0][R] = Integer.parseInt(st.nextToken());
		cost[0][G] = Integer.parseInt(st.nextToken());
		cost[0][B] = Integer.parseInt(st.nextToken());
		
		int currCost;
		for(int i = 1; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int color = 0; color < 3; color++) {
				currCost = Integer.parseInt(st.nextToken());
				for(int c = 0; c < 3; c++) {
					// 이전 집의 색상과 동일하면 생략
					if(c == color) continue;
					// 이전 집의 비용 + 현재 색을 칠하는 비용으로 최소 비용 계산
					cost[i][color] = Math.min(cost[i][color], cost[i - 1][c] + currCost);
				}
			}
		}
		
		// 최소 비용 계산
		int min = Integer.MAX_VALUE;
		for(int color = 0; color < 3; color++) {
			min = Math.min(min, cost[N - 1][color]);
		}
		System.out.println(min);
		br.close();
	}

}
