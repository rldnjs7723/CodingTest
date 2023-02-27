import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 1068번 트리
 * 문제 분류: 자료 구조 (트리)
 * @author Giwon
 */
public class Main_1068 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 노드 개수
		int N = Integer.parseInt(br.readLine().trim());
		// 간선 입력
		Tree tree = new Tree(N);
		st = new StringTokenizer(br.readLine());
		int parent;
		for(int i = 0; i < N; i++) {
			parent = Integer.parseInt(st.nextToken());
			tree.add(i, parent);
		}
		// 리프 노드 개수 갱신
		tree.updateLeaves();
		
		int remove = Integer.parseInt(br.readLine().trim());
		Node removeNode = tree.getNode(remove);
		Node parentNode = removeNode.parent;
		
		if(parentNode == null) {
			// 루트 노드를 삭제했다면 리프 노드 0개
			System.out.println(0);
		} else if(parentNode.children.size() == 1) {
			// 삭제한 노드의 부모 노드의 자식이 1개라면 부모 노드는 리프 노드가 됨
			System.out.println(tree.root.leaves - removeNode.leaves + 1);
		} else {
			// 삭제한 노드를 루트로 하는 서브 트리의 리프 노드 수만큼 차감
			System.out.println(tree.root.leaves - removeNode.leaves);
		}
		br.close();
	}

	public static class Node {
		int leaves;
		Node parent;
		List<Node> children;
		
		public Node() {
			this.leaves = 0;
			this.parent = null;
			this.children = new ArrayList<>();
		}
		
		// DFS로 리프 노드의 개수 카운트
		public int updateLeaves() {
			// 자신이 리프 노드라면 1개 리턴
			if(children.size() == 0) {
				this.leaves = 1;
				return this.leaves;
			}
			
			int count = 0;
			for(Node child: children) {
				count += child.updateLeaves();
			}
			this.leaves = count;
			return this.leaves;
		}
	}
	
	public static class Tree {
		int N;
		Node root;
		Node[] nodes;
		
		public Tree(int N) {
			this.N = N;
			this.nodes = new Node[N];
			this.root = null;
			
			for(int i = 0; i < N; i++) {
				nodes[i] = new Node();
			}
		}
		
		// 간선 생성
		public void add(int idx, int parent) {
			if(parent == -1) {
				this.root = nodes[idx];
				return;
			}
			
			// 부모 노드 입력
			nodes[idx].parent = nodes[parent];
			// 자식 노드 입력
			nodes[parent].children.add(nodes[idx]);
		}
		
		public Node getNode(int idx) {
			return nodes[idx];
		}
		
		// 리프 노드 개수 갱신
		public void updateLeaves() {
			root.updateLeaves();
		}
	}
}
