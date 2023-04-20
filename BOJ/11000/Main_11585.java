import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 11585번 속타는 저녁 메뉴
 * 문제 분류: KMP
 * @author Giwon
 */
public class Main_11585 {
	public static final char ROOT = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 원형 룰렛 칸 수
		final int N = Integer.parseInt(br.readLine().trim());
		// 문자열 길이
		final int SIZE = 2 * N;
		
		// 문자열 char 배열로 저장
		char[] patternArr = new char[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			patternArr[i] = st.nextToken().charAt(0);
		}
		
		// 문자열 구성 
		StringBuilder sb = new StringBuilder();
		// 동일한 문자열 2번 반복
		for(int iter = 0; iter < 2; iter++) {
			for(int i = 0; i < N; i++) {
				sb.append(patternArr[i]);
			}
		}
		String roullete = sb.toString();
		
		System.out.println(roullete);
		
		
		br.close();
	}
	
	public static class Node {
		// 현재 위치 값
		char val;
		// 다음 노드
		Node next;
		// 실패 시 이동할 노드
		Node fail;
		
		public Node(char val) {
			this.val = val;
			this.next = null;
			this.fail = null;
		}
		
		// 다음 노드 생성
		public Node putNode(char val) {
			this.next = new Node(val);
			return this.next;
		}
	}
	
	// 패턴 문자열
	public static class Pattern {
		// 문자열 길이
		int N;
		Node root;
		
		public Pattern(char[] patternArr, int N) {
			this.N = N;
			this.root = new Node(ROOT);
			
			Node curr = this.root;
			for(int i = 0; i < N; i++) {
				curr = curr.putNode(patternArr[i]);
			}
			
		}
		
		
	}

}
