import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * 백준 12852번 1로 만들기 2
 * 문제 분류: 다이나믹 프로그래밍, 스택
 * @author Giwon
 */
public class Main_12852 {
	public static final int DIV3 = 1, DIV2 = 2, SUB1 = 3;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		int[] operation = new int[N + 1];
		int[] count = new int[N + 1];
		Arrays.fill(count, Integer.MAX_VALUE);
		count[N] = 0;
		
		for(int X = N; X >= 2; X--) {
			// X가 3으로 나누어 떨어지면 3으로 나눔
			if(X % 3 == 0) {
				if(count[X / 3] > count[X] + 1) {
					count[X / 3] = count[X] + 1;
					operation[X / 3] = DIV3;
				}
			}
			
			// X가 2로 나누어 떨어지면 2로 나눔
			if(X % 2 == 0) {
				if(count[X / 2] > count[X] + 1) {
					count[X / 2] = count[X] + 1;
					operation[X / 2] = DIV2;
				}
			}
			
			// X에서 1을 뻄
			if(count[X - 1] > count[X] + 1) {
				count[X - 1] = count[X] + 1;
				operation[X - 1] = SUB1;
			}
		}
		
		// 1부터 가장 짧은 연산을 따라가면서 N까지 탐색
		Stack<Integer> temp = new Stack<>();
		int track = 1;
		temp.push(track);
		while(track < N) {
			switch (operation[track]) {
				case DIV3:
					track *= 3;
					temp.push(track);
					break;
				case DIV2:
					track *= 2;
					temp.push(track);
					break;
				case SUB1:
					track += 1;
					temp.push(track);
					break;
			}
		}
		
		// 문자열 생성
		StringBuilder sb = new StringBuilder();
		while(!temp.isEmpty()) {
			sb.append(temp.pop() + " ");
		}
		
		System.out.println(count[1]);
		System.out.println(sb.toString());
		
		sc.close();
	}

}
