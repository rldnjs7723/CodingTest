
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * 백준 1541번 잃어버린 괄호
 * 문제 분류: 그리디 알고리즘
 * @author Giwon
 */
public class Main_1541 {
	public static final int ADD = -1, SUB = -2;
	// 연산자 집합
	public static final Set<Character> OPERATOR = new HashSet<>(Arrays.asList('-', '+')); 

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		// 연산자만 모아 놓을 리스트
		List<Operator> operators = new ArrayList<>();
		// 수식 저장할 리스트
		List<Operator> sequence = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		char curr;
		Operator temp;
		for(int i = 0; i < input.length(); i++) {
			curr = input.charAt(i);
			if(OPERATOR.contains(curr)) {
				// 연산자인 경우
				// 이전까지의 숫자를 기록
				sequence.add(new Operator(sb.toString()));
				sb.setLength(0);
				
				// 현재 연산자 기록
				temp = new Operator(curr);
				sequence.add(temp);
				operators.add(temp);
			} else {
				// 숫자인 경우
				// 현재 숫자 붙이기
				sb.append(curr);
			}
		}
		// 마지막 숫자 기록
		sequence.add(new Operator(sb.toString()));
		
		// 더하기 계산 전부 수행하기
		int len = operators.size();
		for(int i = 0; i < len; i++) {
			if(operators.get(i).val == ADD) operators.get(i).calculate(sequence);
		}
		
		// 빼기 계산 전부 수행하기
		for(int i = 0; i < len; i++) {
			if(operators.get(i).val == SUB) operators.get(i).calculate(sequence);
		}
		
		System.out.println(sequence.get(0).val);
		sc.close();
	}
	
	public static class Operator {
		int val;
		
		public Operator(int val) {
			this.val = val;
		}
		
		public Operator(char op) {
			if(op == '+') this.val = ADD;
			else this.val = SUB;
		}
		
		public Operator(String num) {
			this.val = Integer.parseInt(num);
		}
		
		// 수식에서의 위치를 탐색
		public int findIdx(List<Operator> sequence) {
			for(int i = 0; i < sequence.size(); i++) {
				if(sequence.get(i) == this) return i;
			}
			
			return -1;
		}
		
		public List<Operator> calculate(List<Operator> sequence) {
			int idx = findIdx(sequence);
			int A = sequence.get(idx - 1).val;
			int B = sequence.get(idx + 1).val;
			int result = this.val == ADD ? A + B : A - B;
			
			// 계산 끝난 연산자 제거
			for(int i = 0; i < 3; i++) {
				sequence.remove(idx - 1);
			}
			
			// 계산 결과 입력
			sequence.add(idx - 1, new Operator(result));
			
			return sequence;
		}
		
		@Override
		public String toString() {
			if(val == ADD) return "+";
			if(val == SUB) return "-";
			return Integer.toString(val);
		}
	}
}
