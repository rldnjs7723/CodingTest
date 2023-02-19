import java.util.Arrays;
import java.util.Scanner;

/**
 * 백준 1644번 소수의 연속합
 * 문제 분류: 소수 (에라토스테네스의 체)
 * @author Giwon
 */
public class Main_1644 {
	public static final boolean PRIME = false, NOTPRIME = true;
	public static final int NULL = -1;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 자연수 N
		int N = sc.nextInt();
		// 1부터 N까지의 소수 계산
		boolean[] numbers = new boolean[N + 1];
		eratosthenes(numbers);
		
		int count = 0, sum, curr;
		// 2부터 N까지 연속된 소수를 더하면서 N이 나오는지 카운트
		for(int i = 2; i <= N; i = getNextPrimeNumber(i, numbers)) {
			if(i == NULL) break;
			
			sum = 0;
			curr = i;
			while(sum < N) {
				if(curr == NULL) break;
				sum += curr;
				curr = getNextPrimeNumber(curr, numbers);
			}
			
			if(sum == N) count++;
		}
		
		System.out.println(count);
		
		sc.close();
	}
	
	// 에라토스테네스의 체로 0부터 N까지 소수 판별
	public static void eratosthenes(boolean[] numbers) {
		// 0과 1은 배제
		numbers[0] = NOTPRIME;
		numbers[1] = NOTPRIME;
		
		// 제곱이 범위 안에 포함되는 경우만 소수가 아닌 것을 판별할 수 있음
		for(int i = 2; i * i <= numbers.length; i = getNextPrimeNumber(i, numbers)) {
			if(i == NULL) break;
			
			// 현재 수의 배수는 전부 소수가 아님
			for(int j = 2; i * j < numbers.length; j++) {
				numbers[i * j] = NOTPRIME;
			}
		}
	}
	
	// 현재 수보다 큰 가장 작은 소수를 탐색
	public static int getNextPrimeNumber(int curr, boolean[] numbers) {
		curr++;
		// 범위를 넘어가면 -1 리턴
		if(curr == numbers.length) return NULL;
		while(numbers[curr]) {
			curr++;
			if(curr == numbers.length) return NULL;
		}
		return curr;
	}
}
