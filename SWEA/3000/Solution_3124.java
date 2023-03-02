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
 * SWEA 3124 최소 스패닝 트리
 * 문제 분류: 프림 알고리즘
 * @author Giwon
 */
public class Solution_3124 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int V, E, A, B, C;
		Graph graph;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 정점 수
			V = Integer.parseInt(st.nextToken());
			// 간선 수
			E = Integer.parseInt(st.nextToken());
			
			graph = new Graph(V);
			// 간선 입력
			for(int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				A = Integer.parseInt(st.nextToken());
				B = Integer.parseInt(st.nextToken());
				C = Integer.parseInt(st.nextToken());
				
				graph.add(A, B, C);
			}
			
			// 프림 알고리즘으로 MST 탐색
			System.out.println("#" + test_case + " " + graph.prim());
		}
		
		br.close();
	}
	
	// 간선
	public static class Edge implements Comparable<Edge>{
		int v, w;
		
		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
		
		// 가중치 기반으로 오름차순 정렬
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.w, o.w);
		}
	}
	
	// 정점
	public static class Vertex {
		int weight;
		boolean visited;
		Map<Integer, Integer> edges;
		
		public Vertex() {
			this.weight = Integer.MAX_VALUE;
			this.visited = false;
			edges = new HashMap<>();
		}
		
		public void add(int v, int w) {
			// 중복되는 간선이 있다면 가중치 작은 값만 남기기
			if(edges.containsKey(v)) edges.put(v, Math.min(edges.get(v), w));
			else edges.put(v, w);
		}
	}
	
	public static class Graph {
		// 프림 알고리즘 시작 정점 임의로 1로 설정
		public static final int START = 1;
		int V;
		Vertex[] vertices;
		
		public Graph(int V) {
			this.V = V;
			this.vertices = new Vertex[V + 1];
			
			for(int i = 1; i <= V; i++) {
				vertices[i] = new Vertex();
			}
		}
		
		public void add(int u, int v, int w) {
			// 무향 그래프
			vertices[u].add(v, w);
			vertices[v].add(u, w);
		}
		
		// 프림 알고리즘으로 MST 가중치 합 계산
		public long prim() {
			long weight = 0;
			// 시작 정점 가중치 0으로 설정
			vertices[START].weight = 0;
			// 우선 순위 큐로 최소 가중치 간선 체크
			Queue<Edge> minEdge = new PriorityQueue<>();
			minEdge.offer(new Edge(START, 0));
			Edge curr;
			Vertex currVertex, nextVertex;
			int v, w;
			while(!minEdge.isEmpty()) {
				curr = minEdge.poll();
				currVertex = vertices[curr.v];
				// 이미 방문한 정점은 생략
				if(currVertex.visited) continue;
				// 정점 방문 체크
				currVertex.visited = true;
				// 가중치 추가
				weight += curr.w;
				
				for(Entry<Integer, Integer> edge: currVertex.edges.entrySet()) {
					v = edge.getKey();
					w = edge.getValue();
					nextVertex = vertices[v];
					// 이미 방문한 정점은 생략
					if(nextVertex.visited) continue;
					
					// 가중치 갱신
					if(nextVertex.weight > w) {
						nextVertex.weight = w;
						minEdge.offer(new Edge(v, w));
					}
				}
			}
			
			return weight;
		}
	}
}