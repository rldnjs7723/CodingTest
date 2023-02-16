import java.util.Scanner;

/**
 * 백준 1008번 A/B
 * @author Giwon
 *
 */
public class Main_1008 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// int로 받는 경우 소수로 형변환을 해줘야 계산이 제대로 되므로 처음부터 double 형으로 받기
		double A = sc.nextDouble();
		double B = sc.nextDouble();
		
		// 실수 출력할 때 자리수 설정
		System.out.printf("%.12f", A / B);
		sc.close();
	}
}