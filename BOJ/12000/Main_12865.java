import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 12865번 평범한 배낭
 * 문제 분류: 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_12865 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 물품의 수
		final int N = Integer.parseInt(st.nextToken());
		// 버틸 수 있는 무게
		final int K = Integer.parseInt(st.nextToken());
		
		// 무게 별 물건 최대 가치 DP로 계산
		int[] valueDP = new int[K + 1];
		int W, V;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			// 무게
			W = Integer.parseInt(st.nextToken());
			// 가치
			V = Integer.parseInt(st.nextToken());
			// 물건을 중복해서 넣지 않도록 뒤부터 갱신
			for(int weight = K; weight >= W; weight--) {
				// 현재 물건을 넣은 후의 가치가 더 높으면 값 갱신
				valueDP[weight] = Math.max(valueDP[weight], valueDP[weight - W] + V);
			}
		}
		
		System.out.println(valueDP[K]);
		br.close();
	}
}
