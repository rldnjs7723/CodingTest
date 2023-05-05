import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * 백준 14725번 개미굴
 * 문제 분류: 트라이
 * @author Giwon
 */
public class Main_14725 {
	public static final String PREFIX = "--";
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 먹이 정보 개수
		final int N = Integer.parseInt(br.readLine().trim());
		// 먹이 정보 입력
		Trie trie = new Trie();
		for(int i = 0; i < N; i++) {
			trie.putString(br.readLine().trim());
		}
		
		// 먹이 정보 출력
		trie.printTrie();
		
		br.close();
	}
	
	public static class Node {
		int depth;
		String food;
		Map<String, Node> childs;
		
		public Node(int depth, String food) {
			this.depth = depth;
			this.food = food;
			// 사전순 정렬을 위한 TreeMap 사용
			this.childs = new TreeMap<>(Collections.reverseOrder());
		}
		
		public Node putFood(String food) {
			if(!childs.containsKey(food)) childs.put(food, new Node(depth + 1, food));
			return childs.get(food);
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < depth; i++) {
				sb.append(PREFIX);
			}
			sb.append(food);
			
			return sb.toString();
		}
	}
	
	public static class Trie {
		Node root;
		
		public Trie() {
			this.root = new Node(-1, "");
		}
		
		// 먹이 정보 입력
		public void putString(String input) {
			StringTokenizer st = new StringTokenizer(input);
			final int K = Integer.parseInt(st.nextToken());
			
			Node curr = root;
			for(int i = 0; i < K; i++) {
				curr = curr.putFood(st.nextToken());
			}
		}
		
		public void printTrie() throws IOException {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
			
			// DFS로 경로 출력
			Stack<Node> dfsStack = new Stack<>();
			// 자식 노드 Stack에 넣기
			for(Node child: root.childs.values()) {
				dfsStack.push(child);
			}
			
			Node curr;
			while(!dfsStack.isEmpty()) {
				curr = dfsStack.pop();
				
				// 현재 노드 출력
				bw.write(curr + "\n");
				
				// 자식 노드 Stack에 넣기
				for(Node child: curr.childs.values()) {
					dfsStack.push(child);
				}
			}
			
			
			bw.close();
		}
	}
}
