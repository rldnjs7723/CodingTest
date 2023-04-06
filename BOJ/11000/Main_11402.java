import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 11402번 이항 계수 4
 * 문제 분류: 수학 (페르마의 소정리, 뤼카의 정리), 분할 정복을 이용한 거듭제곱
 * @author Giwon
 */
public class Main_11402 {
	public static final int MAX = 31;
	public static long[] twoPow = new long[MAX];
	public static long[] factorial;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 2^i = twoPow[i]
		twoPow[0] = 1;
		for(int i = 1; i < MAX; i++) {
			twoPow[i] = twoPow[i - 1] * 2; 
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		long N = Long.parseLong(st.nextToken());
		long K = Long.parseLong(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		long result = 0;
		int n, r;
		
		// n! = factorial[n]
		factorial = new long[M];
		factorial[0] = 1;
		for(int i = 1; i < M; i++) {
			factorial[i] = i * factorial[i - 1]; 
			factorial[i] %= M;
		}
		
		// 뤼카의 정리 적용
		result = 1;
		while(N > 0) {
			n = (int) (N % M);
			r = (int) (K % M);
			
			result *= combination(n, r, M);
			result %= M;
			
			N /= M;
			K /= M;
		}
		
		System.out.println(result);
		br.close();
	}
	
	// nCr mod p = combination(n, r, p)
	public static long combination(int n, int r, int p) {
		// 뤼카의 정리에 따라 n < r이라면 nCr은 0
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
