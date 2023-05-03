import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 2533번 사회망 서비스 (SNS)
 * 문제 분류: 그리디 알고리즘, DFS
 * @author Giwon
 */
public class Main_2533 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 정점 개수
		final int N = Integer.parseInt(br.readLine().trim());
		
		// 트리 입력
		Tree tree = new Tree(N);
		int u, v;
		for(int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			
			tree.connect(u, v);
		}
		
		// DFS로 얼리 어답터 최소 수 탐색
		System.out.println(tree.dfs());
		br.close();
	}

	public static class Node {
		// 깊이
		int depth;
		// DFS 방문 체크
		boolean visited;
		// 연결된 노드
		List<Node> friends;
		// 부모 노드
		Node parent;
		// 현재 노드 얼리 어답터 여부
		int isEarly;
		// 자식 노드 얼리 어답터 수
		int friendsEarlyCount;
		
		public Node() {
			this.depth = -1;
			this.visited = false;
			this.friends = new ArrayList<>();
			
			this.parent = null;
			this.isEarly = 0;
			this.friendsEarlyCount = 0;
		}
		
		public void connect(Node friend) {
			friends.add(friend);
		}
	}
	
	public static class Tree {
		// 친구 수
		int N;
		// 트리 노드
		Node[] nodes;
		
		public Tree(int N) {
			this.nodes = new Node[N + 1];
			
			for(int i = 1; i <= N; i++) {
				this.nodes[i] = new Node();
			}
		}
		
		public void connect(int u, int v) {
			nodes[u].connect(nodes[v]);
			nodes[v].connect(nodes[u]);
		}
		
		// DFS로 2번 연속으로 얼리 어답터가 아닌 사람이 나오지 않도록 하는 최소 얼리 어답터 수 탐색
		public int dfs() {
			// 1번 노드를 루트 노드로 하여 탐색
			Stack<Node> dfsStack = new Stack<>();
			nodes[1].visited = true;
			nodes[1].depth = 0;
			dfsStack.push(nodes[1]);
			
			Stack<Node> reverseOrder = new Stack<>();
			Node curr;
			while(!dfsStack.isEmpty()) {
				curr = dfsStack.pop();
				
				// 방문 체크
				curr.visited = true;
				// 방문한 노드 역순으로 저장
				reverseOrder.push(curr);
				
				// 자식 노드 탐색
				for(Node friend: curr.friends) {
					// 부모 노드 생략
					if(friend.visited) {
						curr.parent = friend;
						continue;
					}
					
					// 깊이 갱신
					friend.depth = curr.depth + 1;
					dfsStack.push(friend);
				}
			}
			
			// 가장 깊은 노드부터 자신의 부모 노드에 얼리 어답터 수 갱신
			while(!reverseOrder.isEmpty()) {
				curr = reverseOrder.pop();
				if(curr.parent == null) break;
				
				// 자신이 얼리 어답터가 아니라면 부모 노드는 반드시 얼리 어답터
				if(curr.isEarly == 0) curr.parent.isEarly = 1;
				curr.parent.friendsEarlyCount += curr.isEarly + curr.friendsEarlyCount;
			}
			
			return nodes[1].friendsEarlyCount + nodes[1].isEarly;
		}
		
	}
	
}
