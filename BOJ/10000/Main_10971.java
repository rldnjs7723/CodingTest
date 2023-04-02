import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 10971번 외판원 순회 2
 * 문제 분류: 완전 탐색
 * @author Giwon
 */
public class Main_10971 {
	public static final int INVALID = 0, INF = Integer.MAX_VALUE;
	public static int START;
	public static int N;
	public static int DONE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 도시 수
		N = Integer.parseInt(br.readLine().trim());
		DONE = (int) Math.pow(2, N) - 1;
		// 비용 입력
		int[][] cost = new int[N][N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				cost[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 시작 도시를 0으로 고정하더라도 Hamiltonian Cycle이 존재한다면 최적해 만족
		START = 0;
		System.out.println(Permutation(START, 1, 0, cost));
		br.close();
	}

	public static int Permutation(int curr, int bitmask, int totalCost, int[][] cost) {
		// 모든 도시를 방문했을 때 시작 도시로 돌아갈 수 있는지 확인
		if(bitmask == DONE) {
			if(cost[curr][START] == INVALID) return INF;
			else {
				// 돌아갈 수 있다면 비용 리턴
				return totalCost + cost[curr][START];
			}
		}
		
		int nextBit;
		int minCost = INF;
		for(int i = 0; i < N; i++) {
			nextBit = bitmask | (1 << i);
			// 이미 방문한 도시는 생략
			if(nextBit == bitmask) continue;
			// 이동할 수 없는 도시는 생략
			if(cost[curr][i] == INVALID) continue;
			// 최소 비용 갱신
			minCost = Math.min(minCost, Permutation(i, nextBit, totalCost + cost[curr][i], cost));
		}
		
		return minCost;
	}
}
