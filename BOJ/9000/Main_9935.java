import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * 백준 9935번 문자열 폭발
 * 문제 분류: 스택
 * @author GIWON
 */
public class Main_9935 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine().trim();
		String bomb = br.readLine().trim();
		int N = bomb.length();
		
		Stack<Character> remain = new Stack<>();
		Stack<Character> temp = new Stack<>();
		// 입력 문자열을 스택에 저장
		for(int i = 0; i < input.length(); i++) {
			remain.push(input.charAt(i));
		}
		
		char curr;
		int i = N - 1;
		while(!remain.isEmpty()) {
			curr = remain.pop();
			
			if(curr == bomb.charAt(i)) {
				// 현재까지 stack에서 pop한 결과와 폭탄 문자열이 동일한 경우
				if(i == 0) {
					// i == 0까지 동일하면 동일한 문자열이므로 폭발
					// 임시로 저장해둔 문자열 pop
					for(int k = 0; k < N - 1; k++) {
						temp.pop();
					}
					// 혹시나 이전의 문자열들이 새로운 폭탄을 구성할 수 있으므로 다시 원래 스택에 넣기
					for(int k = 0; k < N - 1; k++) {
						if(temp.isEmpty()) break;
						remain.push(temp.pop());
					}
					// index 초기화
					i = N - 1;
					continue;
				} else {
					// i == 0이 아니면 다음 index 비교
					i--;
				}
			} else {
				// 문자열이 동일하지 않으면 index 초기화
				i = N - 1;
				// 만약 현재 문자가 폭탄 문자열의 마지막과 동일하면 그 다음부터 탐색
				if(curr == bomb.charAt(N - 1)) i--;
			}
			
			// 출력을 위해 임시로 문자열 저장
			temp.push(curr);
		}
		
		StringBuilder sb = new StringBuilder();
		// 뽑은 순서의 역순으로 출력하면 목표 String 출력
		while(!temp.isEmpty()) {
			sb.append(temp.pop());
		}
		// 문자열이 비어있다면 FRULA 출력
		if(sb.length() == 0) System.out.println("FRULA");
		else System.out.println(sb.toString());
		
		br.close();
	}
}
