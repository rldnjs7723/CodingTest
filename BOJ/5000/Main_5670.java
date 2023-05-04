import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 백준 5670번 휴대폰 자판
 * 문제 분류: 트라이
 * @author Giwon
 */
public class Main_5670 {
	public static final int MAX = (int) 1E5;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		String[] words = new String[MAX];
		while((input = br.readLine()) != null) {
			int N = Integer.parseInt(input.trim());
			
			// 트라이 구성
			Trie trie = new Trie();
			for(int i = 0; i < N; i++) {
				words[i] = br.readLine().trim();
				trie.putWord(words[i]);
			}
			
			// 입력 횟수 합산
			double sum = 0;
			for(int i = 0; i < N; i++) {
				sum += trie.getCount(words[i]);
			}
			
			System.out.printf("%.2f\n", sum / N);
		}
		
		br.close();
	}

	public static class Count {
		int val;
		
		public Count() {
			this.val = 0;
		}
	}
	
	public static class Node {
		// 존재하는 단어인지 체크
		boolean isLeaf;
		// 자식 노드
		Map<Character, Node> childs;
		
		public Node() {
			this.isLeaf = false;
			this.childs = new HashMap<>();
		}
		
		public Node putChar(char c) {
			if(!childs.containsKey(c)) childs.put(c, new Node());
			return childs.get(c);
		}
		
		public Node getChar(char c, Count count, boolean prev) {
			// 이전 위치가 단어이거나, 다른 알파벳으로 시작하는 단어가 존재하는 경우 직접 입력
			if(prev || childs.size() > 1) {
				count.val++;
			}
			
			return childs.get(c);
		}
	}
	
	public static class Trie {
		Node root;
		
		public Trie() {
			this.root = new Node();
			// 첫 글자는 반드시 사용자가 입력
			this.root.isLeaf = true;
		}
		
		// 단어 입력
		public void putWord(String word) {
			int len = word.length();
			Node curr = root;
			for(int i = 0; i < len; i++) {
				curr = curr.putChar(word.charAt(i));
			}
			
			// 리프 노드로 체크
			curr.isLeaf = true;
		}
		
		// 단어 입력에 필요한 횟수 카운트
		public int getCount(String word) {
			Count count = new Count();
			int len = word.length();
			
			Node curr = root;
			for(int i = 0; i < len; i++) {
				curr = curr.getChar(word.charAt(i), count, curr.isLeaf);
			}
			
			return count.val;
		}
	}
	
}
