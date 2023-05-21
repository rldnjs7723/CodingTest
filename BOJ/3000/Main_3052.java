import java.util.Scanner;

/**
 * 백준 3052번 나머지
 * 문제 분류: 구현 
 * @author Giwon
 */
public class Main_3052 {
	public static final int DIV = 42;
	public static final int MAX = 10;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		boolean[] remain = new boolean[DIV];
		int val;
		for(int i = 0; i < MAX; i++) {
			val = sc.nextInt();
			remain[val % DIV] = true;
		}
		
		int count = 0;
		for(int i = 0; i < DIV; i++) {
			if(remain[i]) count++;
		}
		
		System.out.println(count);
		sc.close();
	}

}
