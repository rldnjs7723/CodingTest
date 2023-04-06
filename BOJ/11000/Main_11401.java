import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 11401번 이항 계수 3
 * 문제 분류: 수학 (페르마의 소정리), 분할 정복을 이용한 거듭제곱
 * @author Giwon
 */
public class Main_11401 {
	public static final long DIV = 1000000007;
	public static final int MAX = 31, N_MAX = 4000000;
	public static long[] twoPow = new long[MAX];
	public static long[] factorial = new long[N_MAX + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 2^i = twoPow[i]
		twoPow[0] = 1;
		for(int i = 1; i < MAX; i++) {
			twoPow[i] = twoPow[i - 1] * 2; 
		}
		
		// n! = factorial[n]
		factorial[0] = 1;
		for(int i = 1; i <= N_MAX; i++) {
			factorial[i] = i * factorial[i - 1]; 
			factorial[i] %= DIV;
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());

		// 이항 계수 계산
		System.out.println(combination(N, R));
		br.close();
	}
	
	// nCr = combination(n, r)
	public static long combination(int n, int r) {
		long result = factorial[n];
		
		// nCr = n! / (r! * (n-r)!)
		result *= inverseElement(factorial[r]);
		result %= DIV;
		result *= inverseElement(factorial[n - r]);
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
