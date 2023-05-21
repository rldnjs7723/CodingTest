import java.util.Scanner;

/**
 * 백준 2562번 최댓값
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_2562 {
	public static final int SIZE = 9;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int max = -1;
		int maxIdx = -1;
		int val;
		for(int i = 1; i <= SIZE; i++) {
			val = sc.nextInt();
			if(max < val) {
				max = val;
				maxIdx = i;
			}
		}
		
		System.out.println(max);
		System.out.println(maxIdx);
		sc.close();
	}

}
