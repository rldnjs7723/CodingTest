import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


/**
 * 백준 10942번 팰린드롬?
 * 문제 분류: 다이나믹 프로그래밍 / 완전 탐색? 추가 시간으로 통과
 * @author Giwon
 */
public class Main_10942 {
	public static final int YES = 1, NO = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		// 수열의 크기
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 칠판에 적은 수
		int[] sequence = new int[N];
		for(int i = 0; i < N; i++) {
			sequence[i] = Integer.parseInt(st.nextToken());
		}
		
		// 팰린드롬 판별 객체
		Palindrome palindrome = new Palindrome(sequence);
		int M = Integer.parseInt(br.readLine());
		// 팰린드롬 시작 / 끝 위치
		int S, E;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			S = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			
			bw.write(palindrome.checkPalindrome(S, E) + "\n");
		}
		
		bw.close();
		br.close();
	}
	
	// 팰린드롬 판별 클래스
	public static class Palindrome {
		int[] sequence;
		boolean[][] isPalindrome;
		
		public Palindrome(int[] sequence) {
			this.sequence = sequence;
			this.isPalindrome = new boolean[sequence.length][sequence.length];
			
			// 모든 수의 팰린드롬 체크
			checkPalindrome();
		}
		
		// 팰린드롬인지 확인
		public int checkPalindrome(int S, int E) {
			return isPalindrome[S - 1][E - 1] ? YES : NO;
		}
		
		private void checkPalindrome() {
			// 팰린드롬의 길이를 정해두고 길이별로 다이나믹 프로그래밍으로 풀이
			int E;
			// l은 팰린드롬의 길이
			for(int l = 1; l <= sequence.length; l++) {
				// S는 시작점
				for(int S = 0; S < sequence.length - l + 1; S++) {
					// E는 끝점
					E = S + l - 1;
					if(l == 1) {
						// 길이가 1이라면 항상 팰린드롬
						isPalindrome[S][E] = true;
						continue;
					} else if(l == 2 && sequence[S] == sequence[E]) {
						// 길이가 2라면 두 수가 같을 때 팰린드롬
						isPalindrome[S][E] = true;
						continue;
					}
					
					// 자신보다 크기가 2 작은 범위가 팰린드롬이고, 현재 양 끝의 수가 같아야 팰린드롬
					if(isPalindrome[S + 1][E - 1] && sequence[S] == sequence[E]) 
						isPalindrome[S][E] = true;
				}
			}
		}
		
		// 완전탐색 코드
		// N <= 2000이고, O(N^2) 정도이므로 시간이 충분하다고 판단
//		private void checkPalindrome() {
//			// 모든 경우의 수를 탐색하며 팰린드롬인지 확인
//			int left, right;
//			for(int i = 0; i < sequence.length; i++) {
//				// 홀수 탐색
//				left = i; right = i;
//				
//				while(checkRange(left) && checkRange(right)) {
//					// 왼쪽 값과 오른쪽 값을 비교했을 때 같은 값이 계속 유지되면 팰린드롬
//					if(sequence[left] == sequence[right]) {
//						isPalindrome[left][right] = true;
//						left--;
//						right++;
//					} else break;
//				}
//				
//				// 짝수 탐색
//				left = i; right = i + 1;
//				
//				while(checkRange(left) && checkRange(right)) {
//					// 왼쪽 값과 오른쪽 값을 비교했을 때 같은 값이 계속 유지되면 팰린드롬
//					if(sequence[left] == sequence[right]) {
//						isPalindrome[left][right] = true;
//						left--;
//						right++;
//					} else break;
//				}
//			}
//		}
//		
//		// 범위 체크
//		private boolean checkRange(int target) {
//			return target >= 0 && target < sequence.length;
//		}
		
	}
}
