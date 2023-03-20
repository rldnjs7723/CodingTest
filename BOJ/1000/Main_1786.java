import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 백준 1786번 찾기
 * 문제 분류: 문자열, KMP 알고리즘
 * @author Giwon
 */
public class Main_1786 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		String pattern = br.readLine();
		// 접두사, 접미사 일치 길이 테이블
		int[] table = makeTable(pattern);
		
		int size = input.length();
		int patternSize = pattern.length();
		// 현재 일치하는 길이
		int j = 0;
		// 일치하는 부분의 시작점 리스트 저장
		List<Integer> sameIndex = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			while(j > 0 && input.charAt(i) != pattern.charAt(j)) {
				j = table[j - 1];
			}
			
			if(input.charAt(i) == pattern.charAt(j)) {
				j++;
				if(j == patternSize) {
					j = table[j - 1];
					sameIndex.add(i - patternSize + 1);
				}
			}
		}
		
		// 일치하는 부분 개수
		System.out.println(sameIndex.size());
		// 일치하는 부분 시작점
		StringBuilder sb = new StringBuilder();
		for(int idx: sameIndex) {
			sb.append((idx + 1) + " ");
		}
		System.out.println(sb.toString());
		br.close();
	}

	// 접두사, 접미사 일치 길이 테이블 계산
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
