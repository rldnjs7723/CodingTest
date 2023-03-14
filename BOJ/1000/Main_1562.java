import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 1562번 계단 수
 * 문제 분류: 다이나믹 프로그래밍, 비트마스크, 정수론
 * @author SSAFY
 */
public class Main_1562 {
	// 정답을 나눌 수 (10억)
	public static final long DIV = (long) 1E9;
	// 계단 수 길이
	public static int N;
	// 3차원 DP에 사용할 비트필드
	public static long[][][] bitField;
	// 10개의 수를 전부 가지는 경우의 비트
	public static final int FULL = 1024 - 1;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 계단 수 길이 입력
		N = Integer.parseInt(br.readLine().trim());

		// 3차원 DP에 사용할 비트필드
		bitField = new long[N + 1][10][1024];
		// 길이 1일 때 각각의 개수 1로 초기화 (0으로 시작할 수는 없음)
		for(int num = 1; num < 10; num++) {
			bitField[1][num][1 << num] = 1;
		}
		
		// 길이 1부터 N - 1까지 다이나믹 프로그래밍 수행
		for(int length = 1; length < N; length++) {
			for(int num = 0; num < 10; num++) {
				for(int bitmask = 1; bitmask <= FULL; bitmask++) {
					bitFieldDP(num, bitmask, length);
				}
			}
		}
		
		System.out.println(getCount(N));
		br.close();
	}
	
	// 길이가 1 ~ N일 때의 모든 결과 값을 더한 값 출력
	public static void printSum() {
		long sum = 0;
		for(int i = 1; i <= N; i++) {
			sum += getCount(i);
		}
		
		System.out.println(sum);
	}
	
	// 0 ~ 9 사이의 수가 모두 등장하는 길이 length인 계단 수 개수 카운트
	public static long getCount(int length) {
		long count = 0;
		for(int num = 0; num < 10; num++) {
			count += bitField[length][num][FULL];
			count %= DIV;
		}
		
		return count;
	}

	// 비트 필드를 이용한 다이나믹 프로그래밍
	public static void bitFieldDP(int curr, int bitmask, int length) {
		int next;
		for(int i: new int[] {curr - 1, curr + 1}) {
			// 0 ~ 9 사이의 수가 아니면 생략
			if(i < 0 || i > 9) continue;
			
			// 계단 수를 이루도록 숫자 하나 추가한 후 개수 계산
			next = bitmask | (1 << i);
			bitField[length + 1][i][next] += bitField[length][curr][bitmask];
			// 나머지만 남기기
			bitField[length + 1][i][next] %= DIV;
		}
	}
	
}
