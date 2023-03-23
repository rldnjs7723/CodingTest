import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 20188번 등산 마니아
 * 문제 분류: 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_20188 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int N = Integer.parseInt(br.readLine().trim());
//		final int N = 300000;
		// 트리 구성
		Tree tree = new Tree(N);
		int A, B;
		for(int i = 1; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
//			A = i;
//			B = i + 1;
			
			tree.addEdge(A, B);
		}
		tree.init();
		
		// 1번 오두막부터 차례대로 탐색
		long result = 0;
		for(int i = 1; i <= N; i++) {
			result += tree.getEdgeCount(i);
		}
		
		System.out.println(result);
		br.close();
	}

	public static class Node implements Comparable<Node>{
		// 오두막 번호
		int idx;
		// 루트 노드에서 현재 노드까지 이르는 간선 수
		int depth;
		// 부모 노드
		Node parent;
		// 자식 노드 (바이너리 트리)
		List<Node> childs;
		// 현재 노드를 루트 노드로 하는 서브 트리의 노드 개수
		int nodeCount;
		// 현재 노드에서 모든 자식노드로 갈 때 거치는 간선의 개수 (다양성)
		int edgeCount; 
		
		public Node(int idx) {
			this.idx = idx;
			this.depth = 0;
			this.parent = null;
			this.childs = new ArrayList<>();
			this.nodeCount = 1;
			this.edgeCount = 0;
		}
		
		public void add(Node child) {
			childs.add(child);
		}
		
		// 부모 노드 설정
		public void setParent(Node parent) {
			this.parent = parent;
			this.remove(parent);
			// 깊이 지정
			this.depth = parent.depth + 1;
		}
		
		private void remove(Node parent) {
			// 부모 노드를 자식 노드에서 제거
			for(int i = 0; i < childs.size(); i++) {
				if(childs.get(i) == parent) {
					childs.remove(i);
					break;
				}
			}
		}
		
		// 깊이에 따라 내림차순 정렬
		@Override
		public int compareTo(Node o) {
			return Integer.compare(o.depth, this.depth);
		}

		@Override
		public String toString() {
			return "Node [idx=" + idx + ", nodeCount=" + nodeCount + ", edgeCount=" + edgeCount + "]";
		}
	}
	
	public static class Tree {
		// 루트 노드는 항상 1
		public static final int ROOT = 1;
		
		int N;
		Node[] nodes;
		
		public Tree(int N) {
			this.N = N;
			this.nodes = new Node[N + 1];
			
			for(int i = 1; i <= N; i++) {
				this.nodes[i] = new Node(i);
			}
		}
		
		public void addEdge(int A, int B) {
			// 둘 중 어떤 노드가 부모 노드인지 모르므로 무향 그래프로 구성
			nodes[A].add(nodes[B]);
			nodes[B].add(nodes[A]);
		}
		
		// 1번 노드를 루트로 설정하여 트리 구성
		public void init() {
			// DFS로 트리 구성 (재귀로 구성하는 경우 터짐)
			Stack<Node> dfs = new Stack<>();
			dfs.push(nodes[ROOT]);
			
			Node curr;
			while(!dfs.isEmpty()) {
				curr = dfs.pop();
				// 현재 노드의 자식 노드 부모 설정
				for(Node child: curr.childs) {
					child.setParent(curr);
					dfs.push(child);
				}
			}
			
			// 가장 깊은 깊이부터 차례대로 자식 노드의 개수 카운트
			Queue<Node> bfs = new PriorityQueue<>();
			for(int i = 2; i <= N; i++) {
				bfs.offer(nodes[i]);
			}
			Node parent;
			while(!bfs.isEmpty()) {
				curr = bfs.poll();
				parent = curr.parent;
				// 부모 노드의 자식 노드 개수 갱신
				parent.nodeCount += curr.nodeCount;
				// 자식까지 도달하기 위한 간선 수 갱신
				parent.edgeCount += curr.nodeCount + curr.edgeCount;
			}
		}
		
		// 현재 노드에서 다른 노드로 가는 간선 개수 (다양성) 계산
		public int getEdgeCount() {
			
			
			
			
			
			
			
			
			Node initial = nodes[idx];
			// 루트 노드에서 현재 노드로 들어오는 경로 제거
			nodes[ROOT].edgeCount -= initial.depth;
//			print();

			Node curr = initial;
			Node parent = initial.parent;
			int result = nodes[ROOT].edgeCount;
			int count = 1;
			while(parent != null) {
				// 다른 자식 노드로 가는 개수만큼 간선 수 추가
				result += count * (parent.nodeCount - curr.nodeCount);
				count++;
				
				// 부모 노드로 이동
				curr = parent;
				parent = curr.parent;
			}
			
			curr = initial;
			while(curr != null) {
				// 노드 개수 감소
				curr.nodeCount--;
				curr = curr.parent;
			}
			
//			System.out.println(result);
			return result;
		}
		
		public void print() {
			System.out.println(Arrays.toString(nodes));
		}
	}
}
