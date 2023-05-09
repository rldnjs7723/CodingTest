import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1916번 최소비용 구하기
 * 문제 분류: Dijkstra 알고리즘
 * @author Giwon
 */
public class Main_1916 {
	public static final int MAX = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 도시 개수
		final int N = Integer.parseInt(br.readLine().trim());
		// 버스 개수
		final int M = Integer.parseInt(br.readLine().trim());
		
		// 버스 입력
		Graph graph = new Graph(N);
		int u, v, cost;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			cost = Integer.parseInt(st.nextToken());
			
			graph.putEdge(u, v, cost);
		}
		
		// 최소 비용 계산
		st = new StringTokenizer(br.readLine());
		u = Integer.parseInt(st.nextToken());
		v = Integer.parseInt(st.nextToken());
		System.out.println(graph.dijkstra(u, v));
		
		br.close();
	}
	
	public static class Edge implements Comparable<Edge> {
		Vertex destination;
		int cost;
		
		public Edge(Vertex destination, int cost) {
			this.destination = destination;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	public static class Vertex {
		// 현재 도시 번호
		int index;
		// 현재 위치에 도달하기 위한 비용
		int cost;
		// 간선
		Map<Vertex, Integer> edges;
		
		public Vertex(int index) {
			this.index = index;
			this.cost = MAX;
			this.edges = new HashMap<>();
		}
		
		public void putEdge(Vertex v, int cost) {
			if(!edges.containsKey(v)) edges.put(v, MAX);
			edges.put(v, Math.min(edges.get(v), cost));
		}
	}

	public static class Graph {
		int N;
		Vertex[] vertices;
		
		public Graph(int N) {
			this.N = N;
			vertices = new Vertex[N + 1];
			
			for(int i = 1; i <= N; i++) {
				vertices[i] = new Vertex(i);
			}
		}
		
		public void putEdge(int u, int v, int cost) {
			// 유향 그래프
			vertices[u].putEdge(vertices[v], cost);
		}
		
		public int dijkstra(int start, int end) {
			// 시작 도시 초기화
			Queue<Edge> searchQueue = new PriorityQueue<>();
			vertices[start].cost = 0;
			searchQueue.offer(new Edge(vertices[start], 0));
			
			Edge curr;
			Vertex u, v;
			int cost;
			while(!searchQueue.isEmpty()) {
				curr = searchQueue.poll();
				u = curr.destination;
				// 이미 더 작은 비용으로 방문한 경우 생략
				if(u.cost != curr.cost) continue;
				
				// 도착 도시라면 최소 비용 리턴
				if(u.index == end) return u.cost;
				
				for(Entry<Vertex, Integer> entry: u.edges.entrySet()) {
					v = entry.getKey();
					cost = entry.getValue();

					// 비용이 갱신 가능한 경우 방문
					if(v.cost > u.cost + cost) {
						v.cost = u.cost + cost;
						searchQueue.offer(new Edge(v, v.cost));
					}
				}
			}
			
			return MAX;
		}
	}
}
