import java.util.Scanner;

/**
 * 백준 2739번 구구단
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_2739 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		final int N = sc.nextInt();
		for(int i = 1; i <= 9; i++) {
			System.out.println(N + " * " + i + " = " + (N * i));
		}
		
		sc.close();
	}

}
