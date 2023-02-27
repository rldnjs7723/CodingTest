import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1238번 파티
 * 문제 분류: Dijkstra 알고리즘 / Floyd-Warshall 알고리즘 (Java 추가 시간 통과)
 * @author Giwon
 */
public class Main_1238 {
	public static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 마을 개수 (학생 수)
		int N = Integer.parseInt(st.nextToken());
		// 도로 개수
		int M = Integer.parseInt(st.nextToken());
		// 파티 진행 마을
		int X = Integer.parseInt(st.nextToken());
		
		// 도로 입력
		Graph graph = new Graph(N);
		int u, v, w;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			graph.addEdge(u, v, w);
		}
		// 오고 가는 시간이 가장 오래 걸리는 소요 시간
		System.out.println(graph.getWorst(X));
		br.close();
	}

	@SuppressWarnings("serial")
	public static class Vertex extends HashMap<Integer, Integer> implements Comparable<Vertex>{
		int cost;
		
		// 시작 비용 무한으로 초기화
		public Vertex() {
			super();
			this.cost = INF;
		}

		// 우선순위 큐 사용을 위한 Comparable 인터페이스 구현
		// 오름차순 정렬
		@Override
		public int compareTo(Vertex o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	public static class Graph {
		int N;
		Vertex[] V;
		
		public Graph(int N) {
			this.N = N;
			this.V = new Vertex[N + 1];
			
			for(int i = 0; i <= N; i++) {
				V[i] = new Vertex();
			}
		}
		
		// 여러 번의 다익스트라 수행해야 하므로 비용 초기화 수행 필요
		public void reset() {
			for(int i = 0; i <= N; i++) {
				V[i].cost = INF;
			}
		}
		
		// 간선 추가
		public void addEdge(int u, int v, int w) {
			if(V[u].containsKey(v)) {
				V[u].put(v, Math.min(V[u].get(v), w));
			} else {
				V[u].put(v, w);
			}
		}
		
		// 다익스트라 알고리즘 수행
		public int dijkstra(int start, int end) {
			// 비용 무한으로 초기화
			reset();
			// 시작 정점 비용은 0
			V[start].cost = 0;
			// 대기열에 시작 정점 추가
			Queue<Vertex> minCost = new PriorityQueue<>();
			minCost.offer(V[start]);
			Vertex curr, target;
			while(!minCost.isEmpty()) {
				curr = minCost.poll();
				
				for(Entry<Integer, Integer> edge: curr.entrySet()) {
					target = V[edge.getKey()];
					
					// 최소 비용 갱신
					if(target.cost > curr.cost + edge.getValue()) {
						target.cost = curr.cost + edge.getValue();
						minCost.offer(target);
					}
				}
			}
			
			return V[end].cost;
		}
		
		public int getWorst(int party) {
			int max = 0;
			int curr;
			for(int i = 1; i <= N; i++) {
				curr = dijkstra(i, party) + dijkstra(party, i);
				max = Math.max(max, curr);
			}
			
			return max;
		}
		
	}
}

// Floyd-Warshall 코드
//public class Main_1238 {
//	// 오버플로우 방지하기 위해 10억 정도의 값만 사용
//	public static final int INF = Integer.MAX_VALUE / 2 - 1;
//
//	public static void main(String[] args) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		StringTokenizer st = new StringTokenizer(br.readLine());
//		// 마을 개수 (학생 수)
//		int N = Integer.parseInt(st.nextToken());
//		// 도로 개수
//		int M = Integer.parseInt(st.nextToken());
//		// 파티 진행 마을
//		int X = Integer.parseInt(st.nextToken());
//		
//		// 도로 입력
//		Graph graph = new Graph(N);
//		int u, v, w;
//		for(int i = 0; i < M; i++) {
//			st = new StringTokenizer(br.readLine());
//			u = Integer.parseInt(st.nextToken());
//			v = Integer.parseInt(st.nextToken());
//			w = Integer.parseInt(st.nextToken());
//			
//			graph.setCost(u, v, w);
//		}
//		// 플로이드-워셜 알고리즘 수행
//		graph.floyd();
//		// 오고 가는 시간이 가장 오래 걸리는 소요 시간
//		System.out.println(graph.getWorst(X));
//		br.close();
//	}
//
//	public static class Graph {
//		int N;
//		int[][] edges;
//		
//		public Graph(int N) {
//			this.N = N;
//			this.edges = new int[N][N];
//			// 존재하지 않는 간선들의 비용 무한으로 초기화
//			for(int[] edge: edges) {
//				Arrays.fill(edge, INF);
//			}
//		}
//		
//		// u -> v로 도달하는 최소 비용 리턴
//		public int getCost(int u, int v) {
//			return edges[u - 1][v - 1];
//		}
//		
//		// u -> v로 도달하는 비용 갱신
//		public void setCost(int u, int v, int w) {
//			edges[u - 1][v - 1] = Math.min(edges[u - 1][v - 1], w);
//		}
//		
//		// 플로이드-워셜 알고리즘 수행
//		public void floyd() {
//			// 중간 정점 k
//			for(int k = 1; k <= N; k++) {
//				// 시작 정점 i
//				for(int i = 1; i <= N; i++) {
//					// 끝 정점 j
//					for(int j = 1; j <= N; j++) {
//						// i -> j로 도달하는 최소비용 갱신
//						if(getCost(i, j) > getCost(i, k) + getCost(k, j)) {
//							setCost(i, j, getCost(i, k) + getCost(k, j));
//						}
//					}
//				}
//			}
//			
//			// 자기 자신한테 가는 경우 비용 0으로 설정
//			for(int i = 1; i <= N; i++) {
//				setCost(i, i, 0);
//			}
//		}
//		
//		// 왕복 시간이 가장 큰 값을 반환
//		public int getWorst(int party) {
//			int max = 0;
//			int curr;
//			for(int i = 1; i <= N; i++) {
//				curr = getCost(i, party) + getCost(party, i);
//				max = Math.max(max, curr);
//			}
//			
//			return max;
//		}
//	}
//}
