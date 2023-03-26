import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 백준 11003번 최솟값 찾기
 * 문제 분류: 자료구조 (Deque)
 * @author Giwon
 */
public class Main_11003 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		final int N = Integer.parseInt(st.nextToken());
		final int L = Integer.parseInt(st.nextToken());
		
		Deque<Value> deque = new ArrayDeque<>();
		// 숫자 입력
		int A;
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			A = Integer.parseInt(st.nextToken());
			while(!deque.isEmpty()) {
				// 맨 뒤의 숫자가 Index를 벗어났다면 poll
				if(deque.getLast().idx <= i - L) {
//					System.out.println("1: " + deque.pollLast());
					deque.removeLast();
					continue;
				}
				
				// 맨 뒤의 숫자가 현재 수보다 크거나 같다면 poll
				if(deque.getLast().val >= A) {
//					System.out.println("2: " + deque.pollLast());
					deque.removeLast();
					continue;
				}
				
				// 맨 앞의 숫자가 Index를 벗어났다면 poll
				if(deque.getFirst().idx <= i - L) {
//					System.out.println("3: " + deque.poll());
					deque.removeFirst();
					continue;
				}
				
				break;
			}
			
			// 현재 숫자 맨 뒤에 넣기
			deque.offer(new Value(i, A));
//			System.out.println(deque);
			
			// 최솟값 입력
			bw.write(deque.getFirst().toString());
			bw.write(" ");
		}
		
		bw.close();
		br.close();
	}

	public static class Value{
		int idx;
		int val;
		
		public Value(int idx, int val) {
			super();
			this.idx = idx;
			this.val = val;
		}
		
		@Override
		public String toString() {
			return Integer.toString(val);
		}
	}
	
}