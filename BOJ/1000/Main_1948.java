import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1948번 임계경로
 * 문제 분류: 위상 정렬 
 * @author Giwon
 */
public class Main_1948 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 도시의 개수
		int N = Integer.parseInt(br.readLine().trim());
		// 도로의 개수
		int M = Integer.parseInt(br.readLine().trim());
		
		// 도로 입력
		Graph graph = new Graph(N);
		int u, v, cost;
		StringTokenizer st;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 출발 도시
			u = Integer.parseInt(st.nextToken());
			// 도착 도시
			v = Integer.parseInt(st.nextToken());
			// 이동에 걸리는 시간
			cost = Integer.parseInt(st.nextToken());
			
			graph.put(u, v, cost);
		}
		
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		graph.dijkstra(start);
		System.out.println(graph.V[end].cost);
		
		br.close();
	}
	
	public static class Edge implements Comparable<Edge> {
		int v, cost;
		
		public Edge(int v, int cost) {
			this.v = v;
			this.cost = cost;
		}
		
		// 비용 기준 내림차순 정렬
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(o.cost, this.cost);
		}
	}
	
	public static class Vertex {
		public static final int NULL = -1;
		// 출발 도시에서 현재 도시에 도달할 때 필요한 최대 비용
		int cost;
		// 다익스트라 방문 체크
		boolean visited;
		// 진출 간선
		Map<Integer, Integer> incoming;
		Map<Integer, Integer> outgoing;
		
		public Vertex() {
			this.cost = NULL;
			this.visited = false;
			this.incoming = new HashMap<>();
			this.outgoing = new HashMap<>();
		}
		
		// 진입 간선 입력 (진출 간선의 역 방향. 위상 정렬에 사용)
		public void putIncoming(int v, int cost) {
			if(incoming.containsKey(v)) incoming.put(v, Math.max(incoming.get(v), cost));
			else incoming.put(v, cost);
		}
		
		// 진출 간선 입력 (다익스트라에 사용)
		public void putOutgoing(int v, int cost) {
			if(outgoing.containsKey(v)) outgoing.put(v, Math.max(outgoing.get(v), cost));
			else outgoing.put(v, cost);
		}
	}
	
	public static class Graph {
		int N;
		Vertex[] V;
		
		public Graph(int N) {
			this.N = N;
			this.V = new Vertex[N + 1];
			
			for(int i = 1; i <= N; i++) {
				this.V[i] = new Vertex();
			}
		}
		
		// 간선 입력
		public void put(int u, int v, int cost) {
			V[u].putOutgoing(v, cost);
			// 도착 도시를 시작점으로 설정하여 위상 정렬하기 위해 기존의 방향과 반대로 저장
			V[v].putIncoming(u, cost);
		}
		
		// 다익스트라 알고리즘으로 시작 도시에서 각 도시에 도달하는 최대 시간 계산
		public void dijkstra(int start) {
			Queue<Edge> maxEdge = new PriorityQueue<>();
			// 시작 정점 비용 0
			maxEdge.offer(new Edge(start, 0));
			Edge currEdge;
			Vertex curr, next;
			int v, cost;
			while(!maxEdge.isEmpty()) {
				currEdge = maxEdge.poll();
				curr = V[currEdge.v];
				// 이미 방문한 정점은 생략
				if(curr.visited) continue;
				// 정점 비용 갱신
				curr.cost = currEdge.cost;
				
				// 현재 정점 기준으로 Relaxation 수행
				for(Entry<Integer, Integer> entry: curr.outgoing.entrySet()) {
					v = entry.getKey();
					cost = entry.getValue();
					
					next = V[v];
					// 이미 방문한 정점은 생략
					if(next.visited) continue;
					
					// Relaxation
					if(next.cost < curr.cost + cost) {
						next.cost = curr.cost + cost;
						maxEdge.offer(new Edge(v, cost));
					}
				}
			}
		}
	}
	
}
