import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 27652번 AB
 * 문제 분류: 트라이
 * @author Giwon
 */
public class Main_27652 {
	public static final int SIZE = 'z' - 'a' + 1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 쿼리 개수
		final int Q = Integer.parseInt(br.readLine().trim());
		// 정방향으로 문자열 저장
		Trie A = new Trie();
		// 역방향으로 문자열 저장
		Trie B = new Trie();
		
		String input;
		int len, caseA, caseB, result;
		for(int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			input = st.nextToken();
			
			if(input.equals("add")) {
				// add
				input = st.nextToken();
				
				if(input.equals("A")) {
					input = st.nextToken();
					A.addString(input);
				} else if(input.equals("B")) {
					// 문자열 역으로 저장
					input = st.nextToken();
					sb.setLength(0);
					sb.append(input);
					sb.reverse();
					B.addString(sb.toString());
				}
			} else if(input.equals("delete")) {
				// delete
				input = st.nextToken();
				
				if(input.equals("A")) {
					input = st.nextToken();
					A.deleteString(input);
				} else if(input.equals("B")) {
					// 문자열 역으로 제거
					input = st.nextToken();
					sb.setLength(0);
					sb.append(input);
					sb.reverse();
					B.deleteString(sb.toString());
				}
			} else if(input.equals("find")) {
				// find
				input = st.nextToken();
				sb.setLength(0);
				sb.append(input);
				sb.reverse();
				
				int[] countA = A.getString(input);
				int[] countB = B.getString(sb.toString());
				
				result = 0;
				len = input.length();
				// A에서 찾을 접두사 길이
				for(int aLen = 1; aLen < len; aLen++) {
					caseA = countA[aLen];
					caseB = countB[len - aLen];
					
					result += caseA * caseB;
				}
				
				bw.write(result + "\n");
			}
		}
		
		bw.close();
		br.close();
	}

	public static class Node {
		int count;
		Node[] child;
		
		public Node() {
			this.count = 0;
			this.child = new Node[SIZE];
		}
		
		public Node addChar(char c) {
			if(child[c - 'a'] == null) child[c - 'a'] = new Node();
			child[c - 'a'].count++;
			
			return child[c - 'a'];
		}
		
		public Node deleteChar(char c) {
			child[c - 'a'].count--;
			
			return child[c - 'a'];
		}
		
		public Node getChar(char c) {
			return child[c - 'a'];
		}
	}
	
	public static class Trie {
		Node head;
		
		public Trie() {
			this.head = new Node();
		}
		
		// 문자열 트라이 안에 저장
		public void addString(String input) {
			int len = input.length();
			Node curr = head;
			for(int i = 0; i < len; i++) {
				curr = curr.addChar(input.charAt(i));
			}
		}
		
		// 문자열 트라이 안에서 제거
		public void deleteString(String input) {
			int len = input.length();
			Node curr = head;
			for(int i = 0; i < len; i++) {
				curr = curr.deleteChar(input.charAt(i));
			}
		}
		
		// 입력 받은 문자열 접두사 길이별 개수 배열 리턴
		public int[] getString(String input) {
			int len = input.length();
			
			int[] count = new int[len + 1];
			Node curr = head;
			for(int i = 0; i < len; i++) {
				if(curr != null) curr = curr.getChar(input.charAt(i));
				count[i + 1] = curr == null ? 0 : curr.count;
			}
			
			return count;
		}
	}
	
}
