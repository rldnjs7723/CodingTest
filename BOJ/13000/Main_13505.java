import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 13505번 두 수 XOR
 * 문제 분류: 트라이
 * @author Giwon
 */
public class Main_13505 {
	public static final int MAX_SIZE = 30;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 정점 개수
		final int N = Integer.parseInt(br.readLine().trim());
		
		// Trie에 모든 정수 저장
		Trie trie = new Trie();
		int[] vertices = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			vertices[i] = Integer.parseInt(st.nextToken());
			trie.putInteger(vertices[i]);
		}
		
		// 각 정수와 다른 모든 정수를 비교하여 XOR 최댓값 계산
		long max = 0L;
		for(int i = 0; i < N; i++) {
			// XOR 최댓값 탐색
			max = Math.max(max, trie.getMaximum(vertices[i]));
		}
		
		System.out.println(max);
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
		
		// 현재 입력 받은 정수와 XOR한 최댓값 계산
		public long getMaximum(int val) {
			String binary = toBinaryString(val);

			long xor = 0L;
			int bin;
			Node curr = root;
			for(int i = MAX_SIZE - 1; i >= 0; i--) {
				// 이진수로 표현한 수 중 현재 Index의 반대 값
				bin = binary.charAt(MAX_SIZE - 1 - i) - '0';
				bin = bin == 0 ? 1 : 0;
				// 값이 다른 경우가 있다면 XOR 값에 추가
				if(curr.child[bin] != null) {
					xor += (1 << i);
					curr = curr.child[bin];
				} else {
					curr = curr.child[bin == 0 ? 1 : 0];
				}
			}
			
			return xor;
		}
	}
}
