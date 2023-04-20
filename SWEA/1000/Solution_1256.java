import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * SWEA 1256번 K번째 접미어
 * 문제 분류: 트라이
 * @author Giwon
 */
public class Solution_1256 {
	public static final char ROOT = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int T = Integer.parseInt(br.readLine().trim());
		int K;
		String input;
		for(int test_case = 1; test_case <= T; test_case++) {
			K = Integer.parseInt(br.readLine().trim());
			input = br.readLine().trim();
			
			Trie trie = new Trie(input);
			System.out.println("#" + test_case + " " + trie.getSuffix(K));
		}
		br.close();
	}

	public static class Node {
		// 현재 문자
		char val;
		// 부모 노드
		Node parent;
		// 자식 노드
		Map<Character, Node> childs;
		// 리프 노드 여부
		boolean isLeaf;
		
		public Node(char val, Node parent) {
			this.val = val;
			this.parent = parent;
			// 정렬된 상태 유지하기 위해 TreeMap으로 관리
			this.childs = new TreeMap<>();
			this.isLeaf = false;
		}
		
		// 리프 노드에 자식 노드 추가하기
		public void put(char val) {
			Node node;
			for(Node child: childs.values()) {
				// 리프 노드라면 자식 노드에 추가
				if(child.isLeaf) {
					node = new Node(val, this);
					node.isLeaf = true;
					child.childs.put(val, node);
					child.isLeaf = false;
					continue;
				}
				
				// 리프 노드가 아니라면 재귀적으로 리프 노드 탐색
				child.put(val);				
			}
		}
	}
	
	public static class Trie {
		// 문자열 길이
		int len;
		Node root;
		
		public Trie(String input) {
			this.root = new Node(ROOT, null);
			
			this.len = input.length();
			for(int i = 0; i < this.len; i++) {
				root.put(input.charAt(i));
			}
		}
		
		// 현재 리프 노드의 문자열 리턴
		public String getString(Node curr) {
			StringBuilder sb = new StringBuilder();
			while(curr != root) {
				sb.append(curr.val);
				curr = curr.parent;
			}
			
			return sb.reverse().toString();
		}
		
		// idx번 째 접미사 리턴
		public String getSuffix(int idx) {
			// 문자열 길이보다 길면 none 리턴
			if(len < idx) return "none";
			System.out.println(len + " " + idx);
			
			// DFS로 idx번 째 문자열 탐색
			int count = 0;
			Stack<Node> dfsStack = new Stack<>();
			dfsStack.push(root);
			
			Node curr;
			while(!dfsStack.isEmpty()) {
				curr = dfsStack.pop();
				
				// 현재 리프 노드인 경우
				if(curr.childs.isEmpty()) {
					count++;
					if(count == idx) {
						return getString(curr);
					}
					continue;
				}
				
				// 모든 자식 노드 입력
				for(Node child: curr.childs.values()) {
					dfsStack.push(child);
				}
			}
			
			return "none";
		}
	}
}
