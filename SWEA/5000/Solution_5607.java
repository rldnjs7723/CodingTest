import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 5607번 조합
 * 문제 분류: 수학 (페르마의 소정리), 분할 정복을 이용한 거듭제곱
 * @author Giwon
 */
public class Solution_5607 {
	public static final long DIV = 1234567891;
	public static final int MAX = 31;
	public static long[] twoPow = new long[MAX];
	public static long[] factorial;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 2^i = twoPow[i]
		twoPow[0] = 1;
		for(int i = 1; i < MAX; i++) {
			twoPow[i] = twoPow[i - 1] * 2; 
		}
		
		final int T = Integer.parseInt(br.readLine().trim());
		long n, r;
		int p;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			n = Long.parseLong(st.nextToken());
			r = Long.parseLong(st.nextToken());
			p = Integer.parseInt(st.nextToken());
			
			// n! = factorial[n]
			factorial = new long[p + 1];
			factorial[0] = 1;
			for(int i = 1; i <= p; i++) {
				factorial[i] = i * factorial[i - 1]; 
				factorial[i] %= DIV;
			}
			
			System.out.println("#" + test_case + " " + combination(n, r, p));
		}
		br.close();
	}
	
	// n! % DIV 리턴
	public static long getFactorial(long n, int p) {
		if(n >= p) return 1;
		else return factorial[(int) n];
	}
	
	// nCr mod p = combination(n, r, p)
	public static long combination(int n, int r, int p) {
		long result = getFactorial(n, p);
		
		// nCr = n! / (r! * (n-r)!)
		result *= inverseElement(getFactorial(r, p));
		result %= DIV;
		result *= inverseElement(getFactorial(n - r, p));
		result %= DIV;
		
		return result;
	}

	// 입력 받은 정수를 DIV로 나눴을 때의 모듈러 역원 계산
	public static long inverseElement(long target) {
		// 페르마의 소정리 
		// target^DIV-2 = target^-1 mod DIV
		long remainCount = DIV - 2;
		// target^(2^i) = divideAndConquer[i]
		long[] divideAndConquer = new long[MAX + 1];
		divideAndConquer[0] = target;
		for(int i = 1; i <= MAX; i++) {
			divideAndConquer[i] = divideAndConquer[i - 1] * divideAndConquer[i - 1];
			divideAndConquer[i] %= DIV;
		}
		
		long result = 1;
		int idx = MAX - 1;
		while(remainCount > 0) {
			while(remainCount < twoPow[idx]) {
				idx--;
			}
			
			remainCount -= twoPow[idx];
			result *= divideAndConquer[idx];
			result %= DIV;
		}
		
		return result;
	}
}
