import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 1219번 오민식의 고민
 * 문제 분류: Bellman-Ford
 * @author Giwon
 */
public class Main_1219 {
	public static final long INF = Long.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 도시의 수
		final int N = Integer.parseInt(st.nextToken());
		// 시작 도시
		final int start = Integer.parseInt(st.nextToken());
		// 도착 도시
		final int end = Integer.parseInt(st.nextToken());
		// 교통 수단 개수
		final int M = Integer.parseInt(st.nextToken());
		
		// 교통 수단 임시 입력
		Edge[] edges = new Edge[M];
		int u, v, cost;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 출발 도시
			u = Integer.parseInt(st.nextToken());
			// 도착 도시
			v = Integer.parseInt(st.nextToken());
			// 교통 수단 비용
			cost = Integer.parseInt(st.nextToken());
			
			edges[i] = new Edge(u, v, cost);
		}
		
		// 각 도시에 도착했을 때 벌 수 있는 돈 입력
		int[] money = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			money[i] = Integer.parseInt(st.nextToken());
			
			// 현재 도시에 도착하는 모든 교통 수단에 금액 반영
			for(Edge edge: edges) {
				if(edge.v == i) edge.cost -= money[i];
			}
		}
		
		// 그래프 구성
		Graph graph = new Graph(N);
		for(Edge edge: edges) {
			graph.put(edge);
		}
		
		// 벨만-포드 알고리즘 수행
		boolean minusCycle = graph.bellmanFord(start, end, money[start]);
		
		// 도착할 수 없는 경우
		if(graph.V[end].cost == INF) System.out.println("gg");
		// 돈을 무한히 많이 가질 수 있는 경우
		else if(minusCycle) System.out.println("Gee");
		// 돈의 액수 최댓값
		else System.out.println(-graph.V[end].cost);
		br.close();
	}
	
	// 간선
	public static class Edge {
		int u, v, cost;
		
		public Edge(int u, int v, int cost) {
			this.u = u;
			this.v = v;
			this.cost = cost;
		}
	}
	
	// 정점
	public static class Vertex {
		long cost;
		// 음의 사이클과 연결되어 있는지 확인하기 위한 방문 체크
		boolean visited;
		// 도착 도시, 비용
		Map<Integer, Integer> edges;
		
		public Vertex() {
			this.cost = INF;
			this.edges = new HashMap<>();
			this.visited = false;
		}
		
		// 간선 비용 입력
		public void put(int v, int cost) {
			if(edges.containsKey(v)) edges.put(v, Math.min(edges.get(v), cost));
			else edges.put(v, cost);
		}
	}
	
	public static class Graph {
		int N;
		Vertex[] V;
		
		public Graph(int N) {
			this.N = N;
			this.V = new Vertex[N];
			
			for(int i = 0; i < N; i++) {
				V[i] = new Vertex();
			}
		}
		
		// 간선 추가
		public void put(Edge edge) {
			V[edge.u].put(edge.v, edge.cost);
		}
		
		// 벨만-포드 알고리즘 수행
		public boolean bellmanFord(int start, int end, int money) {
			// 시작 정점 비용 해당 도시에 방문했을 때의 금액으로 설정
			V[start].cost = -money;
			
			// 모든 정점에 대해 Relaxation 수행하는 과정 정점 수만큼 반복
			Vertex curr;
			int changed = 0;
			for(int iter = 0; iter < N; iter++) {
				changed = 0;
				for(int u = 0; u < N; u++) {
					curr = V[u];
					// 현재 위치에 도달할 수 있는 경우에만 비용 갱신 
					if(curr.cost == INF) continue;
					// Relaxation 수행
					changed += relaxation(curr);
				}
				
				// 변화한 정점이 없다면 탐색 종료
				if(changed == 0) break;
			}
			
			// 한 번 더 Relaxation을 수행 했을 때 갱신이 된다면 음의 사이클이 존재 
			int v, cost;
			Vertex next;
			Set<Integer> minusCycle = new HashSet<>();
			for(int u = 0; u < N; u++) {
				curr = V[u];
				// 현재 위치에 도달할 수 있는 경우에만 비용 갱신 
				if(curr.cost == INF) continue;
				// Relaxation 수행
				for(Entry<Integer, Integer> entry: curr.edges.entrySet()) {
					v = entry.getKey();
					cost = entry.getValue();
					
					next = V[v];
					// Relaxation
					if(next.cost > curr.cost + cost) {
						next.cost = curr.cost + cost;
						// 음의 사이클 내부의 정점 기록
						minusCycle.add(u);
					}
				}
			}
			
			if(!minusCycle.isEmpty()) {
				// 음의 사이클이 있을 때 해당 사이클이 목표 지점에 영향을 미칠 수 있는지 확인
				for(int i: minusCycle) {
					if(dfs(i, end)) return true;
				}
			}
			// 음의 가중치가 없다면 거짓
			return false;
		}
		
		// 현재 정점 기준으로 Relaxation 수행한 개수를 리턴
		public int relaxation(Vertex curr) {
			int changed = 0;
			int v, cost;
			Vertex next;
			for(Entry<Integer, Integer> entry: curr.edges.entrySet()) {
				v = entry.getKey();
				cost = entry.getValue();
				
				next = V[v];
				// Relaxation
				if(next.cost > curr.cost + cost) {
					next.cost = curr.cost + cost;
					// 탐색이 완료되었는지 확인하기 위해 변화된 개수 체크
					changed++;
				}
			}
			
			return changed;
		}
		
		// DFS로 목표 지점에 도달할 수 있는지 체크
		public boolean dfs(int curr, int target) {
			Vertex u = V[curr];
			u.visited = true;
			
			// 현재 연결된 정점 중에 목표 정점이 존재하면 true
			for(int v: u.edges.keySet()) {
				if(v == target) return true;
			}
			
			// 목표 정점이 존재하지 않는다면 DFS 수행
			for(int v: u.edges.keySet()) {
				// 이미 방문한 정점은 생략
				if(V[v].visited) continue;
				if(dfs(v, target)) return true;
			}
			
			return false;
		}
	}
	
}
