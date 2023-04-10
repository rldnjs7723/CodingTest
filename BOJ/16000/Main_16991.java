import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 16991번 외판원 순회 3
 * 문제 분류: 비트 필드를 이용한 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_16991 {
	public static final int INF = 1000000000, INVALID = 100000000;
	// 모든 도시 방문한 경우
	public static int FULL;
	// 시작점 0번 도시로 고정
	public static final int START = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 도시의 수
		final int N = Integer.parseInt(br.readLine().trim());
		// 도시 좌표 입력
		Coord[] cities = new Coord[N];
		int x, y;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			cities[i] = new Coord(x, y);
		}
		
		// 모든 도시 방문한 경우
		FULL = (1 << N) - 1;
		// 다른 도시 방문하는 비용 인접 행렬
		double[][] cost = getCost(N, cities);
		// 현재 curr의 위치에 있고, bitmask 상태에서 다른 모든 점을 방문하는 최단 거리 = tsp[curr][bitmask]
		double[][] tsp = new double[N][FULL + 1];
		// 시작 위치 초기화
		for(int i = 0; i < N; i++) {
			Arrays.fill(tsp[i], INF);
		}
		
		System.out.println(dfs(START, 1 << START, N, cost, tsp));
		br.close();
	}
	
	// curr에서 시작했을 때, 다음 도시에서 남은 점을 방문하는 최소 거리 탐색
	public static double dfs(int curr, int bitmask, int N, double[][] cost, double[][] tsp) {
		if(bitmask == FULL) {
			// 출발점으로 돌아오는 최소 비용 갱신
			
			// 출발점으로 돌아가지 못하는 경우
			if(cost[curr][START] == INF) {
				tsp[curr][bitmask] = INVALID;
			} else {
				tsp[curr][bitmask] = cost[curr][START];
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
			
			// 다음 점까지의 거리 + 다음 점에 방문하고 남은 점을 최적 경로로 돌았을 때의 거리가 최소가 되는 값 탐색
			tsp[curr][bitmask] = Math.min(tsp[curr][bitmask], dfs(i, next, N, cost, tsp) + cost[curr][i]);
		}
		
		return tsp[curr][bitmask];
	}
	
	// 도시 별 이동하는 비용 인접 행렬 리턴
	public static double[][] getCost(int N, Coord[] cities) {
		double[][] adjMat = new double[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				adjMat[i][j] = cities[i].getDistance(cities[j]);
			}
		}
		
		return adjMat;
	}

	public static class Coord {
		double x, y;
		
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public double getDistance(Coord obj) {
			return Math.sqrt(Math.pow(obj.x - this.x, 2) + Math.pow(obj.y - this.y, 2));
		}
	}
}
