import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 16496번 큰 수 만들기
 * 문제 분류: 그리디 알고리즘
 * @author Giwon
 */
public class Main_16496 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 수의 개수
		final int N = Integer.parseInt(br.readLine().trim());
		// 수 리스트 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 우선순위 큐로 가장 큰 수 추출
		Queue<Number> bestNum = new PriorityQueue<>();
		for(int i = 0; i < N; i++) {
			bestNum.offer(new Number(st.nextToken()));
		}
		
		StringBuilder sb = new StringBuilder();
		// 수가 0으로 시작하는 경우
		if(bestNum.peek().val.charAt(0) == '0') {
			System.out.println(0);
			br.close();
			return;
		}
		
		// 가장 긴 수 표현
		while(!bestNum.isEmpty()) {
			sb.append(bestNum.poll().val);
		}
		
		System.out.println(sb.toString());
		br.close();
	}
	
	public static class Number implements Comparable<Number>{
		String val;
		
		public Number(String val) {
			this.val = val;
		}
		
		@Override
		public int compareTo(Number o) {		
			int len = Math.min(this.val.length(), o.val.length());
			for(int i = 0; i < len; i++) {
				if(this.val.charAt(i) != o.val.charAt(i)) {
					// 맨 앞 숫자 기준으로 내림차순 정렬
					return Integer.compare(o.val.charAt(i), this.val.charAt(i));
				}
			}
			
			Number A, B;
			// 두 수가 완전히 동일하면 0리턴
			if(this.val.length() == o.val.length()) return 0;
			else if(this.val.length() > o.val.length()) {
				// 현재 숫자가 더 긴 경우
				A = new Number(this.val.substring(len));
				B = o;
				
				return A.compareTo(B);
			} else {
				// 비교 숫자가 더 긴 경우
				A = this;
				B = new Number(o.val.substring(len));
				
				return A.compareTo(B);
			}
		}
		
		@Override
		public String toString() {
			return val;
		}
	}
}
