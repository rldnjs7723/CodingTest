import java.util.Scanner;

/**
 * 백준 1157번 단어 공부
 * 문제 분류: 브루트포스
 * @author Giwon
 */
public class Main_1157 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		
		final int SIZE = 'z' - 'a';
		int[] count = new int[SIZE + 1];
		char curr;
		for(int i = 0; i < input.length(); i++) {
			curr = input.charAt(i);
			
			if(curr > 'Z') {
				// 소문자 카운트
				count[curr - 'a']++;
			} else {
				// 대문자 카운트
				count[curr - 'A']++;
			}
		}
		
		// 가장 많이 사용된 알파벳이 여러 개라면 Index -1로 기록
		int idx = -1;
		int maxCount = 0;
		for(int i = 0; i <= SIZE; i++) {
			if(maxCount == count[i]) idx = -1;
			else if(maxCount < count[i]) {
				idx = i;
				maxCount = count[i];
			}
		}
		
		System.out.println(idx == -1 ? "?" : (char) ('A' + idx));
		sc.close();
	}
	
}
