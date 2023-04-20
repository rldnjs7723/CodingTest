import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 16903번 수열과 쿼리 20
 * 문제 분류: 트라이
 * @author Giwon
 */
public class Main_16903 {
	// 트라이 최대 깊이
	public static final int MAX_SIZE = 30;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		// 쿼리 수
		final int M = Integer.parseInt(br.readLine().trim());
		// 쿼리 수행
		Trie trie = new Trie();
		// 0이 하나 포함되어 있는 트라이
		trie.putInteger(0);
		int query, x;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			query = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			
			switch (query) {
				case 1:
					trie.putInteger(x);
					break;
				case 2:
					trie.removeInteger(x);
					break;
				case 3:
					bw.write(trie.getXOR(x) + "\n");
					break;
			}
		}
		
		bw.close();
		br.close();
	}
	
	public static class Node {
		// 현재 노드까지 도달한 문자열의 개수
		int count;
		Node[] child;
		
		public Node() {
			this.count = 0;
			this.child = new Node[2];
		}
		
		public Node getChild(int i) {
			if(child[i] == null) child[i] = new Node();
			
			return child[i];
		}
	}
	
	public static class Trie {
		// 트라이 루트 노드
		Node root;
		
		public Trie() {
			this.root = new Node();
		}
		
		public static String toBinaryString(int val) {
			String binary = Integer.toBinaryString(val);
			int len = binary.length();
			
			// 모든 수를 길이가 30인 이진수로 표현
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < MAX_SIZE - len; i++) {
				sb.append(0);
			}
			for(int i = 0; i < len; i++) {
				sb.append(binary.charAt(i) - '0');
			}
			
			return sb.toString();
		}
		
		// 입력 받은 정수 Trie에 추가
		public void putInteger(int val) {
			String binary = toBinaryString(val);

			Node curr = root;
			for(int i = 0; i < MAX_SIZE; i++) {
				curr = curr.getChild(binary.charAt(i) - '0');
				// 개수 추가
				curr.count++;
			}
		}
		
		// 입력 받은 정수 Trie에서 제거
		public void removeInteger(int val) {
			String binary = toBinaryString(val);
			
			Node curr = root;
			for(int i = 0; i < MAX_SIZE; i++) {
				curr = curr.getChild(binary.charAt(i) - '0');
				// 개수 감소
				curr.count--;
			}
		}
		
		// XOR 최댓값 계산
		public long getXOR(int val) {
			String binary = toBinaryString(val);
			
			long xor = 0L;
			int bin, diff;
			Node curr = root;
			for(int i = MAX_SIZE - 1; i >= 0; i--) {
				// 이진수로 표현한 수 중 현재 Index에서의 값
				bin = binary.charAt(MAX_SIZE - 1 - i) - '0';
				diff = bin == 0 ? 1 : 0;
				// 값이 다른 경우가 있다면 XOR 값에 추가
				if(curr.child[diff] != null && curr.child[diff].count > 0) {
					xor += (1 << i);
					curr = curr.child[diff];
				} else {
					curr = curr.child[bin];
				}
			}
			
			return xor;
		}
	}
	
}