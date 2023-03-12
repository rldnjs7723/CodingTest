import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3282번 0/1 Knapsack
 * 문제 분류: 다이나믹 프로그래밍
 * @author Giwon
 */
public class Solution_3282 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, K, V, C;
		int[] cost;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 물건의 개수
			N = Integer.parseInt(st.nextToken());
			// 부피 합 제한
			K = Integer.parseInt(st.nextToken());
			
			// 0부터 부피 K까지 넣었을 때 최대 가치를 저장할 배열
			cost = new int[K + 1];
			
			// 물건 입력
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				// 부피
				V = Integer.parseInt(st.nextToken());
				// 가치
				C = Integer.parseInt(st.nextToken());
				
				// 입력 받은 물건을 넣었을 때의 가치 갱신
				for(int v = K; v >= V; v--) {
					cost[v] = Math.max(cost[v], cost[v - V] + C);
				}
			}
			
			System.out.println("#" + test_case + " " + cost[K]);
		}
		br.close();
	}

}
