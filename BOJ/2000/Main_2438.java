import java.util.Scanner;

/**
 * 백준 2438번 별 찍기 - 1
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_2438 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		final int N = sc.nextInt();
		for(int i = 1; i <= N; i++) {
			for(int j = 1; j <= i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
		
		sc.close();
	}

}
