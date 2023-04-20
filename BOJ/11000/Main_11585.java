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
	public static final int MAX = 1000000;
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
		Pattern pattern = new Pattern(patternArr, N);
		// Fail 함수 구성
		pattern.findFail();
		
		// 문자열 구성 
		StringBuilder sb = new StringBuilder();
		st = new StringTokenizer(br.readLine());
		// 동일한 문자열 2번 반복
		for(int i = 0; i < N; i++) {
			sb.append(st.nextToken().charAt(0));
		}
		sb.append(sb.toString());
		String roullete = sb.toString();
		
		// KMP로 패턴과 동일한 문자열 탐색
		int count = 0;
		Node curr = pattern.root;
		// 문자열 로딩
		for(int i = 0; i < N - 1; i++) {
			curr = pattern.getNext(curr, roullete.charAt(i));
		}
		for(int i = N - 1; i < SIZE - 1; i++) {
			curr = pattern.getNext(curr, roullete.charAt(i));
			// 리프 노드라면 패턴과 일치하는 경우
			if(curr.next == null) count++; 
		}
		
		int GCD = getGCD(count, N);
		System.out.println((count / GCD) + "/" + (N / GCD));
		br.close();
	}
	
	// 최대 공약수 계산
	public static int getGCD(int A, int B) {
		for(int i = Math.min(MAX, A); i >= 1; i++) {
			if(A % i == 0 && B % i == 0) return i;
		}
		
		return 1;
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

		@Override
		public String toString() {
			return "Node [val=" + val + ", fail=" + fail.val + "]";
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
		
		// Fail 시 이동할 노드 탐색
		public void findFail() {
			// 루트 노드의 Fail은 루트 노드
			root.fail = root;
			
			Node curr = root;
			Node next, fail;
			for(int i = 0; i < N; i++) {
				next = curr.next;
				
				if(curr == root) {
					// 루트 노드의 자식 노드의 Fail은 루트 노드
					next.fail = root;
				} else {
					fail = curr.fail;
					while(fail != root && fail.next.val != next.val) {
						fail = fail.fail;
					}
					if(fail.next.val == next.val) fail = fail.next;
					
					next.fail = fail;
				}
				
				curr = next;
			}
		}
		
		// 입력 받은 노드의 다음 노드 탐색
		public Node getNext(Node curr, char val) {
			while(curr != root && (curr.next == null || curr.next.val != val)) {
				curr = curr.fail;
			}
			if(curr.next.val == val) curr = curr.next;
			
			return curr;
		}
		
	}

}
