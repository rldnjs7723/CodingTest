import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 3363번 동전
 * 문제 분류: 자료구조 (Set), 많은 조건 분기
 * @author Giwon
 */
public class Main_3363 {
	public static final int MAX_COMPARE = 3;
	public static final int MAX_COIN = 12;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 모든 동전이 모조품일 수 있다고 가정
		Set<Integer> bigger = new HashSet<>();
		Set<Integer> smaller = new HashSet<>();
		for(int i = 1; i <= MAX_COIN; i++) {
			bigger.add(i);
			smaller.add(i);
		}
		
		Set<Integer> left = new HashSet<>();
		Set<Integer> right = new HashSet<>();
		char type;
		for(int i = 0; i < MAX_COMPARE; i++) {
			left.clear();
			right.clear();
			
			type = parseInput(br.readLine(), left, right);
			
			// 부등호의 방향 <로 고정
			if(type == '>') {
				Set<Integer> temp = left;
				left = right;
				right = temp;
				type = '<';
			}
			
			if(type == '=') {
				// 저울의 양쪽이 같다면 전부 제대로 된 동전
				bigger.removeAll(left);
				bigger.removeAll(right);
				smaller.removeAll(left);
				smaller.removeAll(right);
			} else {
				// 비교에 등장하지 않은 모든 동전은 제대로 된 동전
				for(int coin = 1; coin <= MAX_COIN; coin++) {
					if(!left.contains(coin) && !right.contains(coin)) {
						bigger.remove(coin);
						smaller.remove(coin);
					}
				}
				
				// 왼쪽에 위치한 동전은 무거울 수 없음
				bigger.removeAll(left);
				// 오른쪽에 위치한 동전은 가벼울 수 없음
				smaller.removeAll(right);
			}
		}
		
		if(bigger.size() == 0 && smaller.size() == 0) {
			// 모순되는 경우
			System.out.println("impossible");
		} else if(bigger.size() + smaller.size() > 1) {
			// 불충분한 경우
			System.out.println("indefinite");
		} else {
			if(bigger.size() == 1) {
				// 무거운 경우
				for(int coin: bigger) {
					System.out.println(coin + "+");
				}
			} else if(smaller.size() == 1) {
				// 가벼운 경우
				for(int coin: smaller) {
					System.out.println(coin + "-");
				}
			}
		}
		
		br.close();
	}
	
	// 입력 파싱
	public static char parseInput(String input, Set<Integer> left, Set<Integer> right) {
		StringTokenizer st = new StringTokenizer(input);
		
		char type = 0;
		String token;
		while(st.hasMoreTokens()) {
			token = st.nextToken();
			switch (token) {
				case "=":
				case "<":
				case ">":
					type = token.charAt(0);
					break;
				default:
					if(type == 0) {
						left.add(Integer.parseInt(token));
					} else {
						right.add(Integer.parseInt(token));
					}
					
					break;
			}
		}
		
		return type;
	}
	
}
