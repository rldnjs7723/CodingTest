import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 백준 4038번 단어가 등장하는 횟수
 * 문제 분류: KMP
 * @author Giwon
 */
public class Solution_4038 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int T = Integer.parseInt(br.readLine());
		String B, S;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 문자열 입력
			B = br.readLine();
			S = br.readLine();
			int lenB = B.length();
			int lenS = S.length();
			
			// 테이블 구성
			int[] table = makeTable(S);
			
			// 패턴 매칭
			int count = 0;
			int j = 0;
			for(int i = 0; i < lenB; i++) {
				while(j > 0 && B.charAt(i) != S.charAt(j)) {
					j = table[j - 1];
				}
				
				if(B.charAt(i) == S.charAt(j)) {
					j++;
					if(j == lenS) {
						j = table[j - 1];
						count++;
					}
				}
			}
			
			System.out.println("#" + test_case + " " + count);
		}
		
		br.close();
	}

	// 접두사, 접미사 겹치는 길이 테이블 구성
	public static int[] makeTable(String pattern) {
		int size = pattern.length();
		int[] table = new int[size];
		int j = 0;
		for(int i = 1; i < size; i++) {
			while(j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
				j = table[j - 1];
			}
			
			if(pattern.charAt(i) == pattern.charAt(j)) {
				table[i] = ++j;
			}
		}
		
		return table;
	}
}
