import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 백준 1467번 수 지우기
 * 문제 분류: 그리디, 자료 구조 (LinkedList)
 * @author Giwon
 */
public class Main_1467 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 숫자 입력
		String input = br.readLine().trim();
		Number number=  new Number(input);
		
		// 지울 숫자 입력
		input = br.readLine().trim();
		number.remove(input);
		
		System.out.println(number);
		br.close();
	}
	
	public static class Node {
		int val;
		Node prev, next;
		
		public Node(int val, Node prev, Node next) {
			this.val = val;
			this.prev = prev;
			this.next = next;
		}
		
		public Node(int val) {
			this(val, null, null);
		}
		
		// 현재 노드 제거
		public void remove() {
			prev.next = next;
			if(next != null) next.prev = prev;
		}
		
		// 뒤에 숫자 추가
		public Node add(int val) {
			next = new Node(val, this, null);
			return next;
		}
	}
	
	public static class Number {
		// LinkedList 형태로 수 저장
		Node head, tail;
		// 남아 있는 숫자 개수
		int[] count;
		int[] removeCount;
		
		public Number(String input) {
			int N = input.length();
			this.count = new int[10];
			this.head = new Node(100);
			
			int val;
			Node curr = head;
			for(int i = 0; i < N; i++) {
				val = input.charAt(i) - '0';
				curr = curr.add(val);
				// 숫자 카운트
				count[val]++;
			}
			
			this.tail = curr.add(100);
		}
		
		// 주어진 숫자 지우기
		public void remove(String input) {
			// 제거 할 숫자 카운트
			removeCount = new int[10];
			int len = input.length();
			int val;
			for(int i = 0; i < len; i++) {
				val = input.charAt(i) - '0';
				removeCount[val]++;
			}

			// 앞에서부터 자기보다 작은 수 제거할 수 있으면 계속 제거
			int[] remainCount = new int[10];
			Node curr = head;
			while((curr = curr.next).val < 10) {
				// 더 이상 남길 수 없다면 현재 수 제거
				if(remainCount[curr.val] == count[curr.val] - removeCount[curr.val]) {
					removeCount[curr.val]--;
					count[curr.val]--;
					curr.remove();
					continue;
				}
				
				while(curr.prev.val < curr.val && removeCount[curr.prev.val] > 0) {
					removeCount[curr.prev.val]--;
					count[curr.prev.val]--;
					remainCount[curr.prev.val]--;
					curr.prev.remove();
				}
				remainCount[curr.val]++;
			}
			
//			System.out.println(Arrays.toString(removeCount));
		}
		
		// 남은 숫자 출력하기
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			Node curr = head;
			while((curr = curr.next).val < 10) {
				sb.append(curr.val);
			}
			
			return sb.toString();
		}
		
	}

}
