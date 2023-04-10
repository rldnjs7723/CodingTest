import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * SWEA 3316번 동아리실 관리하기
 * 문제 분류: 비트 필드를 이용한 다이나믹 프로그래밍
 * @author Giwon
 */
public class Solution_3316 {
	public static final long DIV = 1000000007L;
	// 4명이 전부 참석한 경우
	public static final int FULL = 0b1111;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int T = Integer.parseInt(br.readLine().trim());
		String input;
		int N;
		for(int test_case = 1; test_case <= T; test_case++) {
			input = br.readLine().trim();
			// 문자열 길이
			N = input.length();
			
			// k일 차에 bitmask에 해당하는 인원이 참석하는 경우의 수 = clubDP[k][bitmask]
			long[][] clubDP = new long[N][FULL + 1];
			// 시작 값 초기화
			Arrays.fill(clubDP[0], 1L);
			// 첫날 A를 포함하지 않는 경우 제외
			for(int bitmask = 0; bitmask <= FULL; bitmask++) {
				if((bitmask & getBit('A')) == 0) clubDP[0][bitmask] = 0;
			}
			// 첫날 참석해야 하는 인원을 포함하지 않는 경우 제외
			for(int bitmask = 0; bitmask <= FULL; bitmask++) {
				if((bitmask & getBit(input.charAt(0))) == 0) clubDP[0][bitmask] = 0;
			}
			
			for(int day = 1; day < N; day++) {
				for(int bitmask = 0; bitmask <= FULL; bitmask++) {
					for(int prevBitmask = 0; prevBitmask <= FULL; prevBitmask++) {
						// 이전 날짜 중 현재 비트마스크와 겹치는 인원이 있는 경우에만 현재 날짜의 경우의 수에 추가
						if((bitmask & prevBitmask) > 0) {
							clubDP[day][bitmask] += clubDP[day - 1][prevBitmask];
							clubDP[day][bitmask] %= DIV;
						}
					}
					
					// 현재 날짜에 참석해야 하는 인원을 포함하지 않는 경우 제외
					if((bitmask & getBit(input.charAt(day))) == 0) clubDP[day][bitmask] = 0;
				}
			}
			
			long result = 0;
			for(int bitmask = 0; bitmask <= FULL; bitmask++) {
				result += clubDP[N - 1][bitmask];
				result %= DIV;
			}
			
			System.out.println("#" + test_case + " " + result);
		}
		
		br.close();
	}

	
	public static int getBit(char c) {
		return 1 << (c - 'A');
	}
}
