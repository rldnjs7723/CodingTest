import java.io.IOException;
import java.util.Scanner;

/**
 * 백준 10951번 A + B - 4
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_10951 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		
		int A, B;
		while(sc.hasNext()) {
			A = sc.nextInt();
			B = sc.nextInt();
			
			System.out.println(A + B);
		}
		
		sc.close();
	}

}
