import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 2098번 외판원 순회
 * 문제 분류: 다이나믹 프로그래밍, 비트마스킹
 * @author Giwon
 */
public class Main_2098 {
	// 오버플로우를 막기 위한 무한대 값 설정
	public static final int INF = 1000000000;
	public static final int INVALID = 50000000;
	// 도시 개수
	public static int N;
	// 비트마스크 수
	public static int SIZE;
	// 비트마스크 완료
	public static int FULL;
	// 도시 이동 비용
	public static int[][] W;
	// TSP 비용
	public static int[][] tsp;
	// 시작점 고정
	public static final int START = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 도시 개수
		N = Integer.parseInt(br.readLine());
		// i에서 j로 가기 위한 비용 입력
		W = new int[N][N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
				// 갈 수 없는 곳은 비용 무한
				if(W[i][j] == 0) W[i][j] = INF;
			}
		}
		// 비트마스크 모든 도시 탐색한 경우
		FULL = (1 << N) - 1;
		
		SIZE = (int) Math.pow(2, N);
		// 현재 curr의 위치에 있고, bitmask 상태에서 다른 모든 점을 방문하는 최단 거리 = tsp[curr][bitmask]
		tsp = new int[N][SIZE + 1];
		// 시작 위치 초기화
		for(int i = 0; i < N; i++) {
			Arrays.fill(tsp[i], INF);
		}
		
		// 0번 도시를 시작점으로 설정하여 탐색
		System.out.println(dfs(START, 1 << START));
		br.close();
	}
	
	// curr에서 시작했을 때, 다음 도시에서 남은 점을 방문하는 최소 거리 탐색
	public static int dfs(int curr, int bitmask) {
		if(bitmask == FULL) {
			// 출발점으로 돌아오는 최소 비용 갱신
			
			// 출발점으로 돌아가지 못하는 경우
			if(W[curr][START] == INF) {
				tsp[curr][bitmask] = INVALID;
			} else {
				tsp[curr][bitmask] = W[curr][START];
			}
			return tsp[curr][bitmask];
		}
		
		// 이미 탐색을 수행했다면 생략
		if(tsp[curr][bitmask] != INF) return tsp[curr][bitmask];
		
		// 현재 위치에서 하나씩 더하며 탐색 수행
		int next;
		for(int i = 0; i < N; i++) {		
			next = bitmask | (1 << i);
			// 이미 방문한 경우 생략
			if(next == bitmask) continue;
			// 다음 점까지 이동할 수 없다면 생략
			if(W[curr][i] == INF) continue;
			
			// 다음 점까지의 거리 + 다음 점에 방문하고 남은 점을 최적 경로로 돌았을 때의 거리가 최소가 되는 값 탐색
			tsp[curr][bitmask] = Math.min(tsp[curr][bitmask], dfs(i, next) + W[curr][i]);
		}
		
		return tsp[curr][bitmask];
	}
}