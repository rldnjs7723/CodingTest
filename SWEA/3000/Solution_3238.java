import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3238번 이항계수 구하기
 * 문제 분류: 수학 (페르마의 소정리, 뤼카의 정리), 분할 정복을 이용한 거듭제곱
 * @author Giwon
 */
public class Solution_3238 {
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
		long n, r, result;
		int p, N, R;
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
				factorial[i] %= p;
			}
			
			// 뤼카의 정리 적용
			result = 1;
			while(n > 0) {
				N = (int) (n % p);
				R = (int) (r % p);
				
				result *= combination(N, R, p);
				result %= p;
				
				n /= p;
				r /= p;
			}
			
			System.out.println("#" + test_case + " " + result);
		}
		br.close();
	}
	
	// nCr mod p = combination(n, r, p)
	public static long combination(int n, int r, int p) {
		if(n < r) return 0;
		long result = factorial[n];
		
		// nCr = n! / (r! * (n-r)!)
		result *= inverseElement(factorial[r], p);
		result %= p;
		result *= inverseElement(factorial[n - r], p);
		result %= p;
		
		return result;
	}

	// 입력 받은 정수를 DIV로 나눴을 때의 모듈러 역원 계산
	public static long inverseElement(long target, int p) {
		// 페르마의 소정리 
		// target^DIV-2 = target^-1 mod DIV
		long remainCount = p - 2;
		// target^(2^i) = divideAndConquer[i]
		long[] divideAndConquer = new long[MAX + 1];
		divideAndConquer[0] = target;
		for(int i = 1; i <= MAX; i++) {
			divideAndConquer[i] = divideAndConquer[i - 1] * divideAndConquer[i - 1];
			divideAndConquer[i] %= p;
		}
		
		long result = 1;
		int idx = MAX - 1;
		while(remainCount > 0) {
			while(remainCount < twoPow[idx]) {
				idx--;
			}
			
			remainCount -= twoPow[idx];
			result *= divideAndConquer[idx];
			result %= p;
		}
		
		return result;
	}
}
