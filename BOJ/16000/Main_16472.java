import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 백준 16472번 고냥이
 * 문제 분류: 투 포인터
 * @author Giwon
 */
public class Main_16472 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Alphabet dict = new Alphabet();
		
		int N = Integer.parseInt(br.readLine());
		String input = br.readLine();
		
		int count = 0, max = 0;
		char curr;
		// 투 포인터
		int i = 0, j = 0;
		while(j < input.length()) {
			curr = input.charAt(j);
			// 현재 알파벳이 처음 나타난 경우
			if(dict.get(curr) == 0) {
				if(dict.getTotalAlphabet() < N) {
					// 지금까지 인식한 알파벳이 최대 개수보다 적으면 추가
					dict.put(curr, dict.get(curr) + 1);
					count++;
					// 최대 길이 갱신
					max = Math.max(max, count);
					// 다음 Index로 이동
					j++;
				} else {
					// 최대 알파벳 개수에 도달했다면 하나의 알파벳이 전부 사라질 때까지 삭제
					while(dict.getTotalAlphabet() == N) {
						count--;
						dict.put(input.charAt(i), dict.get(input.charAt(i)) - 1);
						i++;
					}
				}
			} else {
				// 이미 인식한 알파벳이라면 길이 추가
				dict.put(curr, dict.get(curr) + 1);
				count++;
				// 최대 길이 갱신
				max = Math.max(max, count);
				// 다음 Index로 이동
				j++;
			}
		}
		
		System.out.println(max);
		
		br.close();
	}

	// 알파벳을 저장할 HashMap
	@SuppressWarnings("serial")
	public static class Alphabet extends HashMap<Character, Integer>{
		// a부터 z까지 입력
		public Alphabet() {
			for(int i = 0; i <= 'z' - 'a'; i++) {
				this.put((char) ('a' + i), 0);
			}
		}
		
		public int getTotalAlphabet() {
			int count = 0;
			for(int val: this.values()) {
				if(val > 0) count++;
			}
			
			return count;
		}
	}
}
