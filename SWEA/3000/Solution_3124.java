import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 3124 최소 스패닝 트리
 * 문제 분류: 크루스칼 알고리즘
 * @author Giwon
 */
public class Solution_3124 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int V, E, A, B, C;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 정점 수
			V = Integer.parseInt(st.nextToken());
			// 간선 수
			E = Integer.parseInt(st.nextToken());
			
			// MakeSet
			DisjointSet[] set = new DisjointSet[V + 1];
			for(int i = 1; i <= V; i++) {
				set[i] = new DisjointSet();
			}
			// 간선 정렬을 위한 우선순위 큐
			Queue<Edge> queue = new PriorityQueue<>();
			// 간선 입력
			for(int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				A = Integer.parseInt(st.nextToken());
				B = Integer.parseInt(st.nextToken());
				C = Integer.parseInt(st.nextToken());
				
				queue.offer(new Edge(A, B, C));
			}
			
			// 크루스칼 알고리즘으로 MST 탐색
			System.out.println("#" + test_case + " " + Kruskal(set, queue));
		}
		
		br.close();
	}
	
	public static long Kruskal(DisjointSet[] set, Queue<Edge> queue) {
		long weight = 0;
		Edge curr;
		while(!queue.isEmpty()) {
			// 최소 가중치인 간선을 선택
			curr = queue.poll();
			// 간선을 이루는 두 정점이 다른 집합인 경우에만 연결하고 가중치 추가
			if(DisjointSet.UnionSet(set[curr.u], set[curr.v])) weight += curr.w;
		}
		
		return weight;
	}
	
	public static class Edge implements Comparable<Edge> {
		int u, v, w;

		public Edge(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}

		// 가중치에 따라 오름차순으로 정렬
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.w, o.w);
		}
	}
	
	public static class DisjointSet {
		int rank;
		DisjointSet parent;
		
		// MakeSet
		public DisjointSet() {
			this.rank = 0;
			this.parent = null;
		}
		
		// FindSet
		public DisjointSet findSet() {
			// 부모 노드가 없다면 자신이 루트 노드
			if(this.parent == null) return this;
			// Path Compression
			this.parent = this.parent.findSet();
			return this.parent;
		}
		
		// UnionSet
		public static boolean UnionSet(DisjointSet A, DisjointSet B) {
			DisjointSet rootA = A.findSet();
			DisjointSet rootB = B.findSet();
			
			// 두 집합이 동일한 집합인 경우 합칠 수 없다.
			if(rootA == rootB) return false;
			
			// Rank를 이용한 Union
			if(rootA.rank >= rootB.rank) {
				rootB.parent = rootA;
				// 두 집합의 rank가 같은 경우에만 크기 증가
				if(rootA.rank == rootB.rank) rootA.rank++;
			} else {
				rootA.parent = rootB;
			}
			return true;
		}
	}
}