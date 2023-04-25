import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 2887번 행성 터널
 * 문제 분류: MST(크루스칼 알고리즘), 정렬, 스위핑 알고리즘
 * @author Giwon
 */
public class Main_2887 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 좌표 수
		final int N = Integer.parseInt(br.readLine().trim());
		int x, y, z;
		Planet[] planets = new Planet[N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			z = Integer.parseInt(st.nextToken());
			
			planets[i] = new Planet(x, y, z);
		}
		
		// 간선 비용 기준으로 정렬
		Queue<Edge> edges = new PriorityQueue<>();
		// x좌표 기준으로 정렬
		Arrays.sort(planets, new Comparator<Planet>() {
			@Override
			public int compare(Planet o1, Planet o2) {
				return Integer.compare(o1.x, o2.x);
			}
		});
		// 가장 가까운 간선끼리 연결
		for(int i = 0; i < N - 1; i++) {
			edges.offer(new Edge(planets[i + 1].x - planets[i].x, planets[i], planets[i + 1]));
		}
		
		// y좌표 기준으로 정렬
		Arrays.sort(planets, new Comparator<Planet>() {
			@Override
			public int compare(Planet o1, Planet o2) {
				return Integer.compare(o1.y, o2.y);
			}
		});
		// 가장 가까운 간선끼리 연결
		for(int i = 0; i < N - 1; i++) {
			edges.offer(new Edge(planets[i + 1].y - planets[i].y, planets[i], planets[i + 1]));
		}
		
		// z좌표 기준으로 정렬
		Arrays.sort(planets, new Comparator<Planet>() {
			@Override
			public int compare(Planet o1, Planet o2) {
				return Integer.compare(o1.z, o2.z);
			}
		});
		// 가장 가까운 간선끼리 연결
		for(int i = 0; i < N - 1; i++) {
			edges.offer(new Edge(planets[i + 1].z - planets[i].z, planets[i], planets[i + 1]));
		}
		
		// 모든 간선 연결하기
		long result = 0;
		Edge curr;
		while(!edges.isEmpty()) {
			curr = edges.poll();
			result += Planet.union(curr);
		}
		
		System.out.println(result);
		br.close();
	}
	
	public static class Edge implements Comparable<Edge> {
		int cost;
		Planet u, v;
		
		public Edge(int cost, Planet u, Planet v) {
			super();
			this.cost = cost;
			this.u = u;
			this.v = v;
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.cost, o.cost);
		}
	}

	public static class Planet {
		int x, y, z;
		// Disjoint Set 구성용
		Planet parent;
		int depth;

		public Planet(int x, int y, int z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.parent = null;
			this.depth = 0;
		}
		
		// 부모 노드 탐색
		public Planet getRoot() {
			// 부모 노드가 없다면 자신이 루트 노드
			if(this.parent == null) return this;
			
			this.parent = this.parent.getRoot();
			return this.parent;
		}
		
		// 두 행성 연결에 필요한 비용 리턴
		public static int union(Edge edge) {
			Planet uRoot = edge.u.getRoot();
			Planet vRoot = edge.v.getRoot();
			
			// 이미 연결 되어 있다면 0 리턴
			if(uRoot == vRoot) return 0;
			
			if(uRoot.depth >= vRoot.depth) {
				vRoot.parent = uRoot;
				if(uRoot.depth == vRoot.depth) uRoot.depth++;
			} else {
				uRoot.parent = vRoot;
			}
			
			return edge.cost;
		}
	}
}
