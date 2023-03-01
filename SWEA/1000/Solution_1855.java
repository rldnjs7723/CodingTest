import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * SWEA 1855 영준이의 진짜 BFS
 * 문제 분류: LCA (Lowest Common Ancester)
 * @author Giwon
 */
public class Solution_1855 {
	public static int MAXDEPTH;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 테스트 케이스 수
		int T = Integer.parseInt(br.readLine().trim());
		int N;
		Tree tree;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 정점 개수
			N = Integer.parseInt(br.readLine().trim());
			MAXDEPTH = log2(N);
			// 각 정점의 부모 노드 입력
			tree = new Tree(N);
			st = new StringTokenizer(br.readLine());
			// i번째로 주어지는 수가 i-1번 노드의 부모
			for(int i = 2; i <= N; i++) {
				tree.add(Integer.parseInt(st.nextToken()), i);
			}
			// 각 정점의 깊이, 조상 노드 갱신
			tree.updateStatus();
			// BFS에 사용된 간선의 개수 카운트
			System.out.println("#" + test_case + " " + tree.bfs());
		}
		
		br.close();
	}
	
	public static int log2(int target) {
		int count = -1;
		while(target > 0) {
			target /= 2;
			count++;
		}
		
		return count;
	}

	public static class Node {
		int idx, depth, maxDepth;
		int[] parent;
		List<Integer> children;
		
		public Node(int idx) {
			this.idx = idx;
			this.depth = 0;
			this.parent = new int[MAXDEPTH + 1];
			this.children = new ArrayList<>();
		}
		
		public void updateStatus(Node[] nodes, Stack<Integer> stack) {
			Node parentNode = nodes[parent[0]];
			if(parentNode != null) {
				// 현재 깊이는 부모 노드의 깊이 + 1
				this.depth = parentNode.depth + 1;
				this.maxDepth = log2(this.depth);
				
				// DP로 조상 노드 정보 갱신
				for(int i = 1; i <= maxDepth; i++) {
					// 현재 노드의 2^i 번째 조상은 2^i-1 번째 조상의 2^i-1번째 조상
					parent[i] = nodes[parent[i - 1]].parent[i - 1];
				}
			}
			
			// 자식 노드 정렬 (자식 노드를 작은 번호부터 순서대로 탐색)
			Collections.sort(children);
			
			// 자식 노드들도 정보 갱신
			for(int child: children) {
				// N <= 100,000 이므로 재귀로 구현하면 Stack 메모리가 터질 수 있음
				stack.push(child);
			}
		}
	}
	
	public static class Tree {
		public static final int ROOT = 1;
		int N;
		Node[] nodes;
		
		public Tree(int N) {
			this.N = N;
			this.nodes = new Node[N + 1];
			
			for(int i = 1; i <= N; i++) {
				nodes[i] = new Node(i);
			}
		}
		
		// 부모 노드, 자식 노드 연결
		public void add(int parent, int child) {
			nodes[parent].children.add(child);
			nodes[child].parent[0] = parent;
		}
		
		// 각 노드의 깊이, 조상 노드 정보 갱신
		public void updateStatus() {
			// DFS를 통해 정보 갱신
			Stack<Integer> stack = new Stack<>();
			stack.push(1);
			Node curr;
			while(!stack.isEmpty()) {
				curr = nodes[stack.pop()];
				curr.updateStatus(nodes, stack);
			}
		}
		
		// 두 노드의 최소 공통 조상 리턴
		public Node lca(int A, int B) {
			Node nodeA = nodes[A];
			Node nodeB = nodes[B];
			
			// 두 노드의 높이 맞추기
			int depthDiff;
			if(nodeA.depth >= nodeB.depth) {
				while(nodeA.depth > nodeB.depth) {
					depthDiff = log2(nodeA.depth - nodeB.depth);
					nodeA = nodes[nodeA.parent[depthDiff]];
				}
			} else {
				while(nodeA.depth < nodeB.depth) {
					depthDiff = log2(nodeB.depth - nodeA.depth);
					nodeB = nodes[nodeB.parent[depthDiff]];
				}
			}
			
			// 공통 조상 찾기
			int next;
			while(nodeA != nodeB) {
				next = nodeA.maxDepth;
				// 부모가 같지 않을 때까지 최대 노드부터 탐색
				while(nodeA.parent[next] == nodeB.parent[next]) {
					if(next == 0) break;
					next--;
				}
				
				nodeA = nodes[nodeA.parent[next]];
				nodeB = nodes[nodeB.parent[next]];				
			}
			
			return nodeA;
		}
		
		// 두 노드 사이에 위치한 간선의 개수 카운트
		public int edgeCount(int A, int B) {
			Node nodeA = nodes[A];
			Node nodeB = nodes[B];
			Node lca = lca(A, B);
			
			return (nodeA.depth - lca.depth) + (nodeB.depth - lca.depth);
		}
		
		// BFS에서 거쳐가는 간선의 개수 리턴
		public long bfs() {
			long result = 0;
			// 루트 노드부터 탐색
			Queue<Integer> queue = new ArrayDeque<>(); 
			queue.offer(ROOT);
			int curr, prev = -1;
			Node currNode;
			while(!queue.isEmpty()) {
				curr = queue.poll();
				// 이전 노드에서 현재 노드까지 도달하기 위한 간선 수 카운트
				if(prev != -1) result += edgeCount(prev, curr);
				
				// BFS를 위해 Queue에 자식 노드 추가
				currNode = nodes[curr];
				for(int child: currNode.children) {
					queue.offer(child);
				}
				prev = curr;
			}
			
			return result;
		}
	}
}
