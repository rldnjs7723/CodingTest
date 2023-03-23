import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * 백준 10988번 팰린드롬인지 확인하기
 * 문제 분류: 자료구조 (Stack, Queue)
 * @author Giwon
 */
public class Main_10988 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine().trim();
		
		Stack<Character> stack = new Stack<>();
		Queue<Character> queue = new ArrayDeque<>();
		
		// Stack과 Queue에 넣었을 때 나오는 결과가 다르면  펠린드롬이 아님
		int length = input.length();
		for(int i = 0; i < length; i++) {
			stack.push(input.charAt(i));
			queue.offer(input.charAt(i));
		}
		
		for(int i = 0; i < length; i++) {
			if(stack.pop() != queue.poll()) {
				System.out.println(0);
				sc.close();
				return;
			}
			
		}
		
		System.out.println(1);
		sc.close();
	}

}
