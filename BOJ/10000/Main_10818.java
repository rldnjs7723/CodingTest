import java.util.Scanner;

/**
 * 백준 10818번 최소, 최대
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_10818 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// 정수 개수
		final int N = sc.nextInt();
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		int val;
		for(int i = 0; i < N; i++) {
			val = sc.nextInt();
			max = Math.max(max, val);
			min = Math.min(min, val);
		}
		
		System.out.println(min + " " + max);
		sc.close();
	}

}
