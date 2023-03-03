import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 1626번 두 번째로 작은 스패닝 트리
 * 문제 분류: LCA, MST (크루스칼 알고리즘)
 * @author Giwon
 */
public class Main_1626 {
	public static final int NULL = -1;
	// 최대 깊이
	public static int MAXDEPTH; 

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		final int N = Integer.parseInt(st.nextToken());
		final int M = Integer.parseInt(st.nextToken());
		MAXDEPTH = log2(N);
		
		// MakeSet
		DisjointSet[] set = new DisjointSet[N + 1];
		for(int i = 1; i <= N; i++) {
			set[i] = new DisjointSet();
		}
		// 간선 정렬을 위한 우선순위 큐
		Queue<Edge> queue = new PriorityQueue<>();
		// 간선 입력
		List<Edge> E = new ArrayList<>();
		int u, v, w;
		Edge curr;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			curr = new Edge(u, v, w);
			queue.offer(curr);
			E.add(curr);
		}
		
		// MST 구성
		Tree tree = new Tree(N);
		Kruskal(set, queue, tree);
		// 첫 번째 MST를 만들 수 없다면 -1 출력
		if(!isSpanningTree(set)) {
			System.out.println(-1);
			br.close();
			return;
		}
		
		// 새로운 간선을 사용했을 때 가중치가 가장 작은 값이 두 번째로 작은 스패닝 트리
		long min = Long.MAX_VALUE;
		for(Edge edge: E) {
			min = Math.min(min, tree.newMST(edge.u, edge.v, edge.w));
		}
		
		System.out.println(min == Long.MAX_VALUE ? -1 : min);
		br.close();
	}
	
	// log2를 취한 값 리턴
	public static int log2(int target) {
		int count = -1;
		while(target > 0) {
			target /= 2;
			count++;
		}
		return count;
	}
	
	public static class Node {
		int idx, depth, weight, maxDepth;
		// 조상 노드 다이나믹 프로그래밍으로 기록
		Node[] parents;
		Map<Integer, Integer> children;
		
		public Node(int idx) {
			this.idx = idx;
			this.depth = 0;
			this.weight = -1;
			this.parents = new Node[MAXDEPTH + 1];
			this.children = new HashMap<>();
		}
		
		public void add(int v, int w) {
			children.put(v, w);
		}
		
		// 부모 노드 기준으로 재구성
		public void setParent(Node parent, Node[] nodes) {
			if(parent != null) {
				// LCA 알고리즘 적용
				this.parents[0] = parent;
				// 부모에게 가는 간선 가중치 기록
				this.weight = parent.children.get(this.idx);
				// 현재 깊이는 부모 노드의 깊이 + 1
				this.depth = parent.depth + 1;
				
				this.maxDepth = log2(depth);
				for(int i = 1; i <= maxDepth; i++) {
					// 현재 노드의 2^i 번째 조상은 2^i-1 번째 조상의 2^i-1 번째 조상
					this.parents[i] = this.parents[i - 1].parents[i - 1];
				}
				
				// 자식 노드 중에 부모 노드가 포함되어 있다면 삭제
				children.remove(parent.idx);
			}
			
			// 모든 자식 노드에 대해서 재귀
			Node child;
			for(int childIdx: children.keySet()) {
				child = nodes[childIdx];
				child.setParent(this, nodes);
			}
		}
	}
	
	public static class Tree {
		// 임의로 1번 노드를 루트로 선정
		public static final int root = 1;
		public static final int NULL = -1;
		int N; 
		// 1 <= w <= 10^9 이므로 오버플로우 가능성 때문에 long으로 설정
		long weight;
		Node[] nodes;
		
		public Tree(int N) {
			this.N = N;
			this.weight = 0;
			this.nodes = new Node[N + 1];
			
			for(int i = 1; i <= N; i++) {
				nodes[i] = new Node(i);
			}
		}
		
		public void add(int u, int v, int w) {
			// 아직 어떤 노드가 부모 노드인지 알 수 없으므로 무향 그래프로 표현
			nodes[u].add(v, w);
			nodes[v].add(u, w);
		}
		
		// 루트 노드를 기준으로 트리 재구성
		public void toTree() {
			nodes[root].setParent(null, nodes);
		}
		
		// 두 노드의 Lowest Common Ancester 탐색
		public Node lca(int u, int v) {
			Node U = nodes[u];
			Node V = nodes[v];
			
			// 두 노드의 높이 맞추기
			int depthDiff;
			if(U.depth > V.depth) {
				while(U.depth != V.depth) {
					depthDiff = U.depth - V.depth;
					U = U.parents[log2(depthDiff)];
				}
			} else if(U.depth < V.depth) {
				while(U.depth != V.depth) {
					depthDiff = V.depth - U.depth;
					V = V.parents[log2(depthDiff)];
				}
			}
			
			// 공통 조상 탐색
			int next;
			while(U != V) {
				next = U.maxDepth;
				while(U.parents[next] == V.parents[next]) {
					if(next == 0) break;
					next--;
				}
				
				U = U.parents[next];
				V = V.parents[next];
			}
			
			return U;
		}
		
		// 특정 조상 노드까지 도달하는 경로 중 현재 추가하려는 간선의 가중치보다 작은 간선을 탐색
		public long maxWeight(Node U, Node parent, int w) {
			Queue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
			while(U != parent) {
				if(U.weight < w && (heap.isEmpty() || heap.peek() < U.weight))
					heap.offer(U.weight);
				U = U.parents[0];
			}
			
			return heap.isEmpty() ? NULL : heap.poll();
		}
		
		// 두 노드를 연결하는 간선 중 최대 가중치 값을 계산
		public long maxWeight(int u, int v, int w) {
			Node parent = this.lca(u, v);
			Node U = nodes[u];
			Node V = nodes[v];
			
			long max = this.maxWeight(U, parent, w);
			max = Math.max(max, this.maxWeight(V, parent, w));
			
			return max;
		}
		
		// 특정 간선을 추가했을 때 mst 가중치 합 계산
		public long newMST(int u, int v, int w) {
			long maxW = maxWeight(u, v, w);
			if(maxW == NULL) return Long.MAX_VALUE; 
			
			long weight = this.weight - maxW;
			return weight + w;
		}
	}
	
	/**
	 * 크루스칼 알고리즘으로 MST 구성 및 가중치 총 합 계산
	 * @param set			서로소집합 배열
	 * @param queue		간선을 정렬한 우선순위 큐
	 * @param mst			구성할 MST
	 */
	public static void Kruskal(DisjointSet[] set, Queue<Edge> queue, Tree tree) {
		Edge curr;
		while(!queue.isEmpty()) {
			curr = queue.poll();
			if(DisjointSet.unionSet(set[curr.u], set[curr.v])) {
				tree.weight += curr.w;
				tree.add(curr.u, curr.v, curr.w);
			}
		}
		
		tree.toTree();
	}
	
	// 현재 그래프가 Spanning Tree가 맞는지 확인
	public static boolean isSpanningTree(DisjointSet[] set) {
		Set<DisjointSet> duplicate = new HashSet<>();
		int N = set.length;
		for(int i = 1; i < N; i++) {
			duplicate.add(set[i].findSet());
		}
		
		return duplicate.size() == 1;
	}
	
	// 간선
	public static class Edge implements Comparable<Edge>{
		int u, v, w;

		public Edge(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}

		// 가중치 기준 오름차순 정렬
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.w, o.w);
		}
	}

	// 서로소 집합
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
		public static boolean unionSet(DisjointSet A, DisjointSet B) {
			DisjointSet rootA = A.findSet();
			DisjointSet rootB = B.findSet();
			
			// 두 집합이 같은 집합이라면 합칠 필요 없음
			if(rootA == rootB) return false;
			
			// rank를 이용한 union
			if(rootA.rank >= rootB.rank) {
				rootB.parent = rootA;
				if(rootA.rank == rootB.rank) rootA.rank++;
			} else {
				rootA.parent = rootB;
			}
			return true;
		}
	}
}
