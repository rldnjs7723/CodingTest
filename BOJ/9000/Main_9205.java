import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 9205번 맥주 마시면서 걸어가기
 * 문제 분류: Dijkstra 알고리즘 
 * @author Giwon
 */
public class Main_9205 {
	public static final int MAX_BEER = 20;
	public static final int PER_BEER_DISTANCE = 50;
	public static final int MAX_DISTANCE = MAX_BEER * PER_BEER_DISTANCE;
	public static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int t = Integer.parseInt(br.readLine().trim());
		int n;
		StringTokenizer st;
		Coord[] coords;
		for(int test_case = 1; test_case <= t; test_case++) {
			// 편의점 개수
			n = Integer.parseInt(br.readLine().trim());
			// 좌표 입력
			coords = new Coord[n + 2];
			int x, y;
			for(int i = 0; i < n + 2; i++) {
				st = new StringTokenizer(br.readLine());
				x = Integer.parseInt(st.nextToken());
				y = Integer.parseInt(st.nextToken());
				coords[i] = new Coord(x, y);
			}
			
			Graph graph = new Graph(n + 2);
			// 간선 구성하기
			int distance;
			for(int i = 0; i < n + 2; i++) {
				for(int j = i + 1; j < n + 2; j++) {
					// 맨해튼 거리 계산
					distance = coords[i].getDistance(coords[j]);
					// 맥주를 마시면서 걸을 수 있는 최대 거리 (1000m)를 벗어나면 생략
					if(distance > MAX_DISTANCE) continue;
					
					graph.addEdge(i, j, distance);
				}
			}
			
			System.out.println(graph.dijkstra());
		}
		
		br.close();
	}

	public static class Edge implements Comparable<Edge> {
		int v;
		int cost;
		
		
		public Edge(int v, int cost) {
			super();
			this.v = v;
			this.cost = cost;
		}

		// 비용이 작은 순서대로 정렬
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	@SuppressWarnings("serial")
	public static class Vertex extends HashMap<Integer, Integer>{
		int cost;
		boolean visited;
		
		public Vertex() {
			super();
			this.cost = INF;
			this.visited = false;
		}
	}
	
	public static class Graph {
		int N;
		Vertex[] vertices;
		
		public Graph(int N) {
			this.N = N;
			this.vertices = new Vertex[N];
			
			for(int i = 0; i < N; i++) {
				this.vertices[i] = new Vertex();
			}
		}
		
		public void addEdge(int u, int v, int cost) {
			// 무향 그래프
			vertices[u].put(v, cost);
			vertices[v].put(u, cost);
		}
		
		public String dijkstra() {
			final int START = 0, END = N - 1;
			
			// 우선 순위 큐를 통해 가중치가 가장 작은 간선 선택
			Queue<Edge> pQueue = new PriorityQueue<>();
			// 시작 정점 비용 0으로 설정
			vertices[START].cost = 0;
			pQueue.offer(new Edge(0, 0));
			Edge curr;
			int v, cost;
			while(!pQueue.isEmpty()) {
				curr = pQueue.poll();
				
				// 이미 방문한 경우 생략
				if(vertices[curr.v].visited) continue;
				// 방문 체크
				vertices[curr.v].visited = true;
				
				for(Entry<Integer, Integer> edge: vertices[curr.v].entrySet()) {
					v = edge.getKey();
					cost = edge.getValue();
					
					// 이미 방문한 경우 생략
					if(vertices[v].visited) continue;
					
					// 나머지 정점 갱신이 가능한 경우 Queue에 넣기
					if(vertices[v].cost > curr.cost + cost) {
						vertices[v].cost = curr.cost + cost;
						pQueue.offer(new Edge(v, vertices[v].cost));
					}
				}
			}
			
			// 방문이 불가능하면 sad
			if(vertices[END].cost == INF) return "sad";
			// 방문이 가능하면 happy 리턴
			else return "happy";
		}
	}
	
	public static class Coord {
		int x, y;
		
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		// 맨해튼 거리 계산
		public int getDistance(Coord obj) {
			return Math.abs(this.x - obj.x) + Math.abs(this.y - obj.y);
		}
	}
}
