import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * 백준 7432번 디스크 트리
 * 문제 분류: 트라이
 * @author Giwon
 */
public class Main_7432 {
	public static BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		// 전체 경로 개수
		final int N = Integer.parseInt(br.readLine().trim());
		// 경로 입력
		Trie trie = new Trie();
		for(int i = 0; i < N; i++) {
			trie.putString(br.readLine().trim());
		}
		// 경로 출력
		trie.printDirectory();
		
		bw.close();
		br.close();
	}
	
	public static class Node implements Comparable<Node>{
		int depth;
		String name;
		Map<String, Node> child;
		
		public Node(String name, int depth) {
			this.name = name;
			this.depth = depth;
			this.child = new TreeMap<>();
		}
		
		public void putString(String input) {
			int firstIdx = findFirst(input);
			if(firstIdx == -1) {
				if(!child.containsKey(input)) child.put(input, new Node(input, depth + 1));
				return;
			}
			
			String curr = input.substring(0, firstIdx);
			String next = input.substring(firstIdx + 1, input.length());
			
			if(!child.containsKey(curr)) child.put(curr, new Node(curr, depth + 1));
			// 자식 노드에 재귀적으로 추가
			child.get(curr).putString(next);
		}
		
		// 현재 경로 이름 출력
		public void printName() throws IOException {
			if(depth != -1) bw.write(this + "\n");
			
			// 자식 노드도 재귀적으로 출력
			for(Node node: child.values()) {
				node.printName();
			}
		}
		
		// 첫 번째 \ 위치 탐색
		public int findFirst(String input) {
			int len = input.length();
			for(int i = 0; i < len; i++) {
				if(input.charAt(i) == '\\') return i;
			}
			
			return -1;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < depth; i++) {
				sb.append(" ");
			}
			sb.append(name);
			
			return sb.toString();
		}
		
		// 이름 기준으로 정렬
		@Override
		public int compareTo(Node o) {
			return this.name.compareTo(o.name);
		}
	}
	
	public static class Trie {
		Node root;
		
		public Trie() {
			this.root = new Node(null, -1);
		}
		
		// 문자열 입력
		public void putString(String input) {
			root.putString(input);
		}
		
		// 디렉토리 출력
		public void printDirectory() throws IOException {
			root.printName();
		}
	}
	
}
