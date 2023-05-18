import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 백준 1467번 수 지우기
 * 문제 분류: 그리디
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
			int[] removeCount = new int[10];
			int len = input.length();
			int val;
			for(int i = 0; i < len; i++) {
				val = input.charAt(i) - '0';
				removeCount[val]++;
			}
			
			Node curr, next;
			// 모든 숫자를 제거해야 하는 경우
			for(val = 1; val <= 9; val++) {
				if(count[val] != removeCount[val]) continue;
				
				curr = head.next;
				// 항상 주어진 숫자를 전부 지울 수 있음
				while(removeCount[val] > 0) {
					next = curr.next;
					if(curr.val == val) {
						curr.remove();
						removeCount[val]--;
						count[val]--;
					}
					curr = next;
				}
			}
			
			int[] remainCount = new int[10];
			for(val = 1; val <= 9; val++) {
				remainCount[val] = count[val] - removeCount[val];
			}
			System.out.println(Arrays.toString(remainCount));
			
			// 자신보다 큰 수의 마지막 위치 탐색
			for(val = 9; val >= 1; val--) {
				Node bigger = head;
				curr = head;
				while((curr = curr.next).val < 10) {
					if(curr.val > val) {
						bigger = curr;
					}
				}
				
				curr = bigger;
				while((curr = curr.next).val < 10) {
					if(curr.val == val) {
						if(remainCount[val] > 0) {
							remainCount[val]--;
						} else {
							// 남겨둘 수 있는 숫자 이외에 전부 제거
							curr.remove();
						}
					}
				}
				
				curr = bigger;
				while(curr.val < 10 && (curr = curr.prev).val < 10) {
					if(curr.val == val) {
						if(remainCount[val] > 0) {
							remainCount[val]--;
						} else {
							// 남겨둘 수 있는 숫자 이외에 전부 제거
							curr.remove();
						}
					}
				}
			}
			
			
			
			
//			Node last;
//			for(val = 9; val >= 1; val--) {
//				while(removeCount[val] > 0) {
//					curr = head;
//					last = head;
//					while((curr = curr.next).val < 10) {
//						// 다른 수라면 생략
//						if(curr.val != val) {
//							continue;
//						}
//						
//						// 자신보다 뒤에 위치한 숫자가 자신보다 크면 삭제
//						if(curr.next.val > curr.val) {
//							curr.remove();
//							removeCount[val]--;
//							count[val]--;
//							last = null;
//							break;
//						}
//						
//						// 삭제 할 가장 뒤에 위치한 수 탐색
//						if(curr.val == val) {
//							last = curr;
//						}
//					}
//					
//					// 맨 뒤의 수 제거
//					if(last != null) {
//						last.remove();
//						removeCount[val]--;
//						count[val]--;
//					}
//				}
//			}
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
