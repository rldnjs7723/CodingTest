import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * SWEA 1248번 공통조상
 * 문제 분류: LCA (Lowest Common Ancestor), 자료구조 (트리)
 * @author Giwon
 */
public class Solution_1248 {
	public static final int MAX = 10001;
	public static final int MAXDEPTH = log2(MAX);
	public static final int NULL = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine().trim());
		int V, E, A, B;
		Tree tree;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			tree = new Tree(V);
			st = new StringTokenizer(br.readLine());
			// 간선 입력
			int P, C;
			for(int i = 0; i < E; i++) {
				P = Integer.parseInt(st.nextToken());
				C = Integer.parseInt(st.nextToken());
				
				tree.add(P, C);
			}
			// 트리 깊이, 크기, 조상 정보 갱신
			tree.updateStatus();
			
			Node small = tree.getNode(A).depth >= tree.getNode(B).depth ? tree.getNode(A) : tree.getNode(B);
			Node big = small == tree.getNode(A) ? tree.getNode(B) : tree.getNode(A);

			// 두 수의 깊이 맞추기
			small = matchDepth(small, big, tree);
			// 공통 조상 찾기
			while(small.parent[0] != big.parent[0]) {
				small = small.parent[0];
				big = big.parent[0];
			}
			Node lca = small.parent[0];
			
			System.out.println("#" + test_case + " " + lca.idx + " " + lca.size);
		}
		
		br.close();
	}
	
	public static class Node {
		int idx, depth, size;
		Node[] parent;
		Children children;
		
		public Node(int idx) {
			this.idx = idx;
			this.depth = 0;
			this.size = 1;
			this.parent = new Node[MAXDEPTH];
			this.children = new Children();
		}
		
		public void add(Node C) {
			children.add(C);
		}
		
		public int updateStatus(int depth) {
			// 깊이 갱신
			this.depth = depth;
			// 다이나믹 프로그래밍으로 2^i번 째 조상 미리 계산
			int maxDepth = log2(this.depth);
			for(int j = 1; j <= maxDepth; j++) {
				// 현재 노드의 2^j 번째 조상은 2^j-1 번째 조상의 2^j-1 번째 조상
				parent[j] = parent[j - 1].parent[j - 1];
			}
			
			// 자식 노드 수 카운트
			int count = 1;
			for(Node child: children) {
				count += child.updateStatus(depth + 1);
			}
			// 크기 갱신
			this.size = count;
			return count;
		}
	}
	
	@SuppressWarnings("serial")
	public static class Children extends ArrayList<Node>{}
	
	public static class Tree {
		Node[] nodes;
		
		public Tree(int V) {
			nodes = new Node[V + 1];
			
			for(int i = 0; i <= V; i++) {
				nodes[i] = new Node(i);
			}
		}
		
		public void add(int P, int C) {
			nodes[P].add(nodes[C]);
			nodes[C].parent[0] = nodes[P];
		}
		
		public Node getNode(int idx) {
			return nodes[idx];
		}
		
		// 각 노드의 깊이, 크기 갱신
		public void updateStatus() {
			// 루트 노드는 항상 1
			nodes[1].updateStatus(0);
		}
	}
	
	// 두 노드의 깊이 맞추기
	public static Node matchDepth(Node small, Node big, Tree tree) {
		int depthDiff;
		while(small.depth > big.depth) {
			depthDiff = log2(small.depth - big.depth);
			small = small.parent[depthDiff];
		}
		
		return small;
	}

	public static int log2(int target) {
		int count = -1;
		while(target > 0) {
			target /= 2;
			count++;
		}
		return count;
	}
}
