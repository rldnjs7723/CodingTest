import java.util.Scanner;

/**
 * 백준 2753번 윤년
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_2753 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		final int year = sc.nextInt();
		// 4의 배수, 100의 배수, 400의 배수 여부
		boolean[] divResult = new boolean[3];
		if(year % 4 == 0) divResult[0] = true; 
		if(year % 100 == 0) divResult[1] = true;
		if(year % 400 == 0) divResult[2] = true;
		
		// 4의 배수 && 100의 배수가 아닌 경우
		// 400의 배수인 경우
		if((divResult[0] && !divResult[1]) || divResult[2]) System.out.println(1);
		else System.out.println(0);
		
		sc.close();
	}

}
