import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 11438번 LCA 2
 * 문제 분류: LCA (다이나믹 프로그래밍)
 * @author Giwon
 */
public class Main_11438 {
	// 최대 깊이 값 계산
	public static int MAX_DEPTH;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 노드 개수
		final int N = Integer.parseInt(br.readLine().trim());
		// 최대 깊이 값 계산
		MAX_DEPTH = log2(N + 1);
		
		// 트리 입력
		Tree tree = new Tree(N);
		int A, B;
		for(int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			tree.add(A, B);
		}
		
		// 각 노드의 조상 노드 계산
		tree.updateStatus();

		// 정점 쌍 개수
		final int M = Integer.parseInt(br.readLine().trim());
		// LCA 탐색할 정점 쌍 입력
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			sb.append(tree.getLCA(A, B) + "\n");
		}
		
		System.out.println(sb.toString());
		br.close();
	}

	public static int log2(int num) {
		int count = -1;
		while(num > 0) {
			num /= 2;
			count++;
		}
		
		return count;
	}
	
	public static class Node {
		int idx, depth;
		int[] parents;
		Set<Integer> childs;
		
		public Node(int idx) {
			this.idx = idx;
			this.depth = 0;
			this.parents = new int[MAX_DEPTH + 1];
			this.childs = new HashSet<>();
		}
		
		public void updateStatus(int parent, Node[] nodes) {
			if(parent > 0) {
				// 부모 노드 설정
				this.parents[0] = parent;
				// 현재 노드의 깊이는 부모 노드의 깊이 + 1
				this.depth = nodes[parents[0]].depth + 1;
				// 부모 노드를 자식 노드 집합에서 제거
				this.childs.remove(parent);
			}
			
			// 현재 노드의 조상 노드 DP로 계산
			int maxDepth = log2(this.depth);
			for(int i = 1; i <= maxDepth; i++) {
				// 현재 노드의 2^i번째 조상 = 2^i-1 번째 조상의 2^i-1 번째 조상
				this.parents[i] = nodes[parents[i - 1]].parents[i - 1];
			}
			
			// 모든 자식 노드에 대해서 반복
			for(int child: childs) {
				nodes[child].updateStatus(this.idx, nodes);
			}
		}
		
		@Override
		public String toString() {
			return Arrays.toString(parents);
		}
	}
	
	public static class Tree {
		public static final int ROOT = 1;
		
		int N;
		Node[] nodes;
		
		public Tree(int N) {
			this.N = N;
			this.nodes = new Node[N + 1];
			
			// 노드 번호는 1부터 N까지 존재
			for(int i = 1; i <= N; i++) {
				this.nodes[i] = new Node(i);
			}
		}
		
		// 노드 간선 추가
		public void add(int A, int B) {
			// 자식 노드 추가
			// 둘 중 누가 부모 노드인지 모르기 때문에 둘 다 추가
			nodes[A].childs.add(B);
			nodes[B].childs.add(A);
		}
	
		// 트리 구성 후 깊이와 조상 노드 업데이트
		public void updateStatus() {
			nodes[ROOT].updateStatus(-1, nodes);
		}
		
		// 두 노드의 LCA 계산
		public int getLCA(int A, int B) {
			Node big = nodes[A].depth >= nodes[B].depth ? nodes[A] : nodes[B];
			Node small = nodes[A].depth >= nodes[B].depth ? nodes[B] : nodes[A];			
			
			// 두 노드의 깊이 맞추기
			int depthDiff;
			while(big.depth > small.depth) {
				depthDiff = big.depth - small.depth;
				big = nodes[big.parents[log2(depthDiff)]];
			}
			
			// 두 노드의 LCA 찾기
			int nextParent = log2(big.depth);
			while(big.idx != small.idx) {
				while(big.parents[nextParent] == small.parents[nextParent]) {
					if(nextParent == 0) break;
					nextParent--;
				}
				
				big = nodes[big.parents[nextParent]];
				small = nodes[small.parents[nextParent]];
			}
			
			return big.idx;
		}
	}
}
