import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 1761번 정점들의 거리
 * 문제 분류: LCA
 * @author Giwon
 */

public class Main_1761 {
	public static int MAXDEPTH;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		// 정점 개수
		final int N = Integer.parseInt(br.readLine().trim());
		
		// 정점 입력
		Tree tree = new Tree(N);
		int u, v, distance;
		for(int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			distance = Integer.parseInt(st.nextToken());
			
			tree.connect(u, v, distance);
		}
		
		// 각 정점의 부모 노드 정보 갱신
		tree.updateStatus();
		
		// 두 노드 사이의 거리 게산
		final int M = Integer.parseInt(br.readLine().trim());
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			
			bw.write(tree.lca(u, v) + "\n");
		}
		
		bw.close();
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
		// 정점 번호
		int idx, depth, maxDepth;
		// 2^i 부모 노드
		Node[] parent;
		// 2^i 부모 노드와의 거리
		int[] distance;
		// 연결된 노드
		Map<Node, Integer> children;
		// 방문 체크
		boolean visited;
		
		public Node(int idx) {
			this.idx = idx;
			this.depth = 0;
			this.maxDepth = 0;
			this.parent = new Node[MAXDEPTH + 1];
			this.distance = new int[MAXDEPTH + 1];
			this.children = new HashMap<>();
			this.visited = false;
		}
		
		public void updateStatus(Stack<Node> dfsStack) {
			// 방문 체크
			visited = true;
			
			// 루트 노드가 아닌 경우 조상 노드 정보 갱신
			if(parent[0] != null) {
				for(int i = 1; i <= maxDepth; i++) {
					parent[i] = parent[i - 1].parent[i - 1];
					distance[i] = distance[i - 1] + parent[i - 1].distance[i - 1];
				}
			}
			
			// 자식 노드 정보 갱신 후 Stack에 추가
			Node child;
			int dist;
			for(Entry<Node, Integer> entry: children.entrySet()) {
				child = entry.getKey();
				dist = entry.getValue();
				// 자신의 부모 노드는 생략
				if(child.visited) continue;
				
				// 깊이 갱신
				child.depth = this.depth + 1;
				child.maxDepth = log2(child.depth);
				// 부모 노드 갱신
				child.parent[0] = this;
				child.distance[0] = dist;
				
				// Stack에 추가
				dfsStack.push(child);
			}
		}
	}
	
	public static class Tree {
		// 1번 노드를 루트 노드로 설정
		public static final int ROOT = 1;
		
		// 노드 개수
		int N;
		Node[] nodes;
		
		public Tree(int N) {
			this.N = N;
			// 희소 배열 최대 크기 설정
			MAXDEPTH = log2(N);
			
			this.nodes = new Node[N + 1];
			
			for(int i = 1; i <= N; i++) {
				this.nodes[i] = new Node(i);
			}
		}
		
		// 두 정점 연결
		public void connect(int u, int v, int distance) {
			// 부모 노드를 모르니 무향 그래프로 처리
			nodes[u].children.put(nodes[v], distance);
			nodes[v].children.put(nodes[u], distance);
		}
		
		// DFS를 통해 부모 노드 정보 갱신
		public void updateStatus() {
			Stack<Node> dfsStack = new Stack<>();
			// 1번 노드를 루트 노드로 설정
			dfsStack.push(nodes[ROOT]);
			
			Node curr;
			while(!dfsStack.isEmpty()) {
				curr = dfsStack.pop();
				curr.updateStatus(dfsStack);
			}
		}
		
		// 두 노드가 최소 공통 조상에 도달하는 거리 계산
		public int lca(int A, int B) {
			int distance = 0;
			
			Node nodeA = nodes[A];
			Node nodeB = nodes[B];
			
			// 두 노드의 깊이 맞추기
			Node bigger = nodeA.depth >= nodeB.depth ? nodeA : nodeB; 
			Node smaller = nodeA.depth >= nodeB.depth ? nodeB : nodeA;
			
			int depthDiff;
			while(bigger.depth > smaller.depth) {
				depthDiff = log2(bigger.depth - smaller.depth);
				// 거리 합산
				distance += bigger.distance[depthDiff];
				// 부모 노드로 이동
				bigger = bigger.parent[depthDiff];
			}
			
			// 두 노드가 동일할 때까지 조상 노드로 이동
			int next;
			while(bigger != smaller) {
				next = bigger.maxDepth;
				while(next > 0 && bigger.parent[next] == smaller.parent[next]) {
					next--;
				}
				
				// 거리 합산
				distance += bigger.distance[next];
				distance += smaller.distance[next];
				// 부모 노드로 이동
				bigger = bigger.parent[next];
				smaller = smaller.parent[next];
			}
			
			return distance;
		}
		
	}
}
