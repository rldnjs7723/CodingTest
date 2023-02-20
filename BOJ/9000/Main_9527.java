import java.util.Arrays;
import java.util.Scanner;

/**
 * 백준 9527번 1의 개수 세기
 * 문제 분류: 수학
 * @author GIWON
 */
public class Main_9527 {
	public static final long MAX = 10000000000000000L;
	// 최대 값보다 큰 가장 작은 2^(i + 1) - 1의 Index
	public static final int MAXSIZE = 53;
	public static long[] twoPow, smallCount, oneCount, counts;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		long A = sc.nextLong();
		long B = sc.nextLong();
		
		// 2^i = twoPow[i]
		twoPow = new long[MAXSIZE];
		twoPow[0] = 1;
		for(int i = 1; i < MAXSIZE; i++) {
			twoPow[i] = twoPow[i - 1] * 2;
		}
		
		// 2^i ~ 2^(i + 1) - 1까지의 1의 개수 = smallCount[i]
		smallCount = new long[MAXSIZE];
		smallCount[0] = 1;
		for(int i = 1; i < MAXSIZE; i++) {
			smallCount[i] = smallCount[i - 1] * 2 + twoPow[i - 1];
		}
		
		// 1 ~ 2^(i + 1) - 1 까지의 1의 개수 = oneCount[i]
		oneCount = new long[MAXSIZE];
		oneCount[0] = 1;
		for(int i = 1; i < MAXSIZE; i++) {
			oneCount[i] = oneCount[i - 1] + smallCount[i];
		}
		
		// 2^(i + 1) - 1 = counts[i]
		counts = new long[MAXSIZE];
		counts[0] = 1;
		for(int i = 1; i < MAXSIZE; i++) {
			counts[i] = counts[i - 1] * 2 + 1;
		}
		
		// 1 ~ B까지 1의 개수 - 1 ~ A - 1까지 1의 개수로 A ~ B까지 1의 개수 계산
		System.out.println(countOne(B) - countOne(A - 1));
		sc.close();
	}
	
	// 잘린 개수 계산
	public static long smallCountOne(long target, long iter, int idx) {
		if(target == 0) return 0;
		long count = 0;
		while(twoPow[idx] > target) {
			idx--;
		}
		count += smallCount[idx] + twoPow[idx] * iter;
		target -= twoPow[idx];
		return count + smallCountOne(target, iter + 1, idx);
	}
	
	// 1부터 target까지의 1의 개수 계산
	public static long countOne(long target) {
		if(target == 0) return 0;
		int idx = MAXSIZE - 1;
		long count = 0;
		while(counts[idx] > target) {
			idx--;
		}
		
		count += oneCount[idx];
		target -= counts[idx];
		return count + smallCountOne(target, 0, idx);
	}
}
