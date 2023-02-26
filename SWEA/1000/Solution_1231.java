import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 1231번 중위순회
 * 문제 분류: 자료구조 (트리)
 * @author Giwon
 */
public class Solution_1231 {
	public static final int NULL = -1;
	// 테스트케이스는 10개
	public static final int T = 10;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int N, idx;
		char val;
		Tree tree;
		for(int test_case = 1; test_case <= 10; test_case++) {
			// 정점 개수
			N = Integer.parseInt(br.readLine());
			tree = new Tree(N);
			// 트리 입력
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				idx = Integer.parseInt(st.nextToken());
				val = st.nextToken().charAt(0);
				
				tree.setNode(idx, val);
			}
			
			sb.setLength(0);
			// 루트 정점의 번호는 항상 1
			tree.inOrder(1, sb);
			System.out.println("#" + test_case + " " + sb.toString());
		}

		br.close();
	}

	public static class Tree {
		int N;
		Node[] nodes;
		
		public Tree(int N) {
			this.N = N;
			nodes = new Node[N + 1];
		}
		
		public void setNode(int curr, char val) {
			// 완전 이진 트리이므로 자식 노드는 2i, 2i + 1
			int left = 2 * curr <= N ? 2 * curr : NULL;
			int right = 2 * curr + 1 <= N ? 2 * curr + 1 : NULL;
			nodes[curr] = new Node(val, left, right);
		}
		
		// 중위 순회
		public void inOrder(int idx, StringBuilder sb) {
			Node curr = nodes[idx];
			if(curr.left != NULL) inOrder(curr.left, sb);
			sb.append(curr.val);
			if(curr.right != NULL) inOrder(curr.right, sb);
		}
		
		@Override
		public String toString() {
			return Arrays.toString(nodes);
		}
	}
	
	public static class Node {
		char val;
		int left, right;
		
		public Node(char val, int left, int right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
		
		@Override
		public String toString() {
			return "[ val: " + val + " left: " + left + "right: " + right + " ]";
		}
	}
}
