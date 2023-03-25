import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * SWEA 13736번 사탕 분배
 * 문제 분류: 분할 정복을 이용한 거듭제곱, 정수론 (나머지 연산 성질), 구성적
 * @author Giwon
 */
public class Solution_13736 {
	public static final int MAX = (int) 2E9;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		final int T = Integer.parseInt(br.readLine().trim());
		
		long A, B, K;
		long totalCandy, result;
		TreeSet<Exponent> twoPow = new TreeSet<>();
		Exponent binarySearch = new Exponent(0, 0);
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 사탕 개수
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			// 분배 횟수
			K = Integer.parseInt(st.nextToken());
			
			// 전체 사탕 수 (나머지 연산 수행용)
			totalCandy = A + B;
			
			// 지수
			long power = 1;
			// 거듭 제곱 값
			long val = 2;
			// 거듭 제곱 미리 계산
			twoPow.clear();
			while(power <= K) {
				twoPow.add(new Exponent(power, val));
				power *= 2;
				val *= val;
				val %= totalCandy;
			}
			
			// 사탕 분배
			result = Math.min(A, B);
			Exponent curr;
			while(K > 0) {
				binarySearch.power = K;
				curr = twoPow.floor(binarySearch);
				// 사탕 수 계산
				result *= curr.val;
				result %= totalCandy;
				// 분배 수 차감
				K -= curr.power;
			}
			
			sb.setLength(0);
			// 더 작은 사탕 수 출력
			sb.append("#" + test_case + " " + Math.min(result, totalCandy - result));
			System.out.println(sb.toString());
		}
		bw.close();
		br.close();
	}
	
	// 밑이 2인 거듭제곱 저장 클래스
	public static class Exponent implements Comparable<Exponent> {
		long power;
		long val;
		
		public Exponent(long power, long val) {
			super();
			this.power = power;
			this.val = val;
		}

		@Override
		public int compareTo(Exponent o) {
			return Long.compare(this.power, o.power);
		}

		@Override
		public String toString() {
			return "Exponent [power=" + power + ", val=" + val + "]";
		}
	}
}
