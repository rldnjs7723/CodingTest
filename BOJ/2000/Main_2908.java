import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 백준 2908번 상수
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_2908 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		StringTokenizer st = new StringTokenizer(sc.nextLine());
		StringBuilder sb = new StringBuilder();
		sb.append(st.nextToken());
		sb.reverse();
		final int A = Integer.parseInt(sb.toString());
		
		sb.setLength(0);
		sb.append(st.nextToken());
		sb.reverse();
		final int B = Integer.parseInt(sb.toString());
		
		System.out.println(Math.max(A, B));
		sc.close();
	}

}
