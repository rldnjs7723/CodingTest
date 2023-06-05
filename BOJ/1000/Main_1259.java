import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * 백준 1259번 팰린드롬수
 * 문제 분류: 자료구조 (Stack, Queue)
 * @author Giwon
 */
public class Main_1259 {
	public static final String YES = "yes", NO = "no";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		boolean palindrome;
		Stack<Character> stack = new Stack<>();
		Queue<Character> queue = new ArrayDeque<>();
		while(true) {
			input = br.readLine().trim();
			if(Integer.parseInt(input) == 0) break;
			
			stack.clear();
			queue.clear();
			
			for(int i = 0; i < input.length(); i++) {
				stack.push(input.charAt(i));
				queue.offer(input.charAt(i));
			}
			
			palindrome = true;
			while(!stack.isEmpty()) {
				if(stack.pop() != queue.poll()) {
					palindrome = false;
					break;
				}
			}
			
			if(palindrome) System.out.println(YES);
			else System.out.println(NO);
		}
		
		br.close();
	}

}
