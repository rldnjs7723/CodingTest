import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 19585번 전설
 * 문제 분류: 트라이, HashSet
 * @author Giwon
 */
public class Main_19585 {
	public static final int SIZE = 'z' - 'a' + 1;
	public static final String YES = "Yes", NO = "No";
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 색상 수
		final int C = Integer.parseInt(st.nextToken());
		// 닉네임 수
		final int N = Integer.parseInt(st.nextToken());
		
		Checker checker = new Checker();
		// 색상 입력
		for(int i = 0; i < C; i++) {
			checker.putColor(br.readLine().trim());
		}
		
		// 닉네임 입력
		for(int i = 0; i < N; i++) {
			checker.putNickname(br.readLine().trim());
		}
		
		// 팀명 검사
		final int Q = Integer.parseInt(br.readLine().trim());
		for(int i = 0; i < Q; i++) {
			bw.write(checker.searchName(br.readLine().trim()) + "\n");
		}
		
		bw.close();
		br.close();
	}

	public static class Node {
		// 리프 노드인지 확인
		boolean isLeaf;
		// 자식 노드
		Node[] childs;
		
		public Node() {
			this.childs = new Node[SIZE];
		}
		
		public Node putChar(char c) {
			if(childs[c - 'a'] == null) childs[c - 'a'] = new Node();
			return childs[c - 'a'];
		}
		
		public Node getNode(char c) {
			return childs[c - 'a'];
		}
	}
	
	public static class Trie {
		// 루트 노드
		Node root;
		
		public Trie() {
			this.root = new Node();
		}
		
		// 문자열 입력
		public void putString(String input) {
			int len = input.length();
			Node curr = root;
			for(int i = 0; i < len; i++) {
				curr = curr.putChar(input.charAt(i));
			}
			
			// 리프 노드로 체크
			curr.isLeaf = true;
		}
	}
	
	public static class Checker {
		Trie colorTrie;
		Set<String> nicknameSet;
		
		public Checker() {
			this.colorTrie = new Trie();
			this.nicknameSet = new HashSet<>();
		}
		
		public void putColor(String color) {
			colorTrie.putString(color);
		}
		
		public void putNickname(String nickname) {
			nicknameSet.add(nickname);
		}
		
		public String searchName(String teamname) {
			// 현재 팀 이름이 존재하는지 탐색
			Node color = colorTrie.root;
			
			// 길이는 1000을 넘지 않음
			int len = Math.min(teamname.length(), 1000);
			String sub;
			for(int i = 0; i < len; i++) {
				color = color.getNode(teamname.charAt(i));
				if(color == null) break;
				if(color.isLeaf) {
					sub = teamname.substring(i + 1);
					if(nicknameSet.contains(sub)) return YES;
				}
			}
			
			return NO;
		}
	}
}
