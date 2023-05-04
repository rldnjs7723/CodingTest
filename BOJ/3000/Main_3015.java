import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * 백준 3015번 오아시스 재결합
 * 문제 분류: 스택
 * @author Giwon
 */
public class Main_3015 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 사람 수
		final int N = Integer.parseInt(br.readLine().trim());
		// 키 입력
		Person[] people = new Person[N];
		for(int i = 0; i < N; i++) {
			people[i] = new Person(Long.parseLong(br.readLine().trim()));
		}
		
		Stack<Person> heightStack = new Stack<>();
		Person curr, top;
		for(int i = 0; i < N; i++) {
			curr = people[i];
			while(!heightStack.isEmpty() && heightStack.peek().height < curr.height) {
				top = heightStack.pop();
				
				// 현재 가장 키가 작은 사람보다 키가 크거나 같은 사람은 볼 수 있는 사람을 공유
				if(!heightStack.isEmpty()) {
					if(heightStack.peek().height == top.height) {
						// 키가 같은 경우
						heightStack.peek().taller += top.taller;
					} else {
						heightStack.peek().smaller += top.taller;
					}
				}
				
				// 현재 가장 키가 작은 사람과 현재 사람은 서로 볼 수 있음
				top.taller++;
			}
			// 현재 사람보다 키가 크거나 같은 사람과 현재 사람은 서로 볼 수 있음
			if(!heightStack.isEmpty()) {
				if(heightStack.peek().height == curr.height) {
					// 키가 같은 경우
					heightStack.peek().taller++;
				} else {
					heightStack.peek().smaller++;
				}
			}
			
			heightStack.push(curr);
		}
		
		// Stack Flush
		while(!heightStack.isEmpty()) {
			top = heightStack.pop();
			if(!heightStack.isEmpty()) {
				if(heightStack.peek().height == top.height) {
					// 키가 같은 경우
					heightStack.peek().taller += top.taller;
				} else {
					heightStack.peek().smaller += top.taller;
				}
			}
		}
		
		long sum = 0;
		for(int i = 0; i < N; i++) {
//			System.out.println(people[i]);
			sum += people[i].smaller + people[i].taller;
		}
		
		System.out.println(sum);
		br.close();
	}

	public static class Person {
		// 키
		long height;
		// 뒤에 위치한 사람 중 자신보다 크거나 같은 볼 수 있는 사람 수
		int taller;
		// 뒤에 위치한 사람 중 자신보다 작은 볼 수 있는 사람 수
		int smaller;
		
		public Person(long height) {
			this.height = height;
			this.taller = 0;
			this.smaller = 0;
		}

		@Override
		public String toString() {
			return "Person [height=" + height + ", taller=" + taller + ", smaller=" + smaller + "]";
		}
		
	}
}
