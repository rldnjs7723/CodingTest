import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 백준 8958번 OX퀴즈
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_8958 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int T = Integer.parseInt(br.readLine().trim());
		
		String input;
		for(int test_case = 1; test_case <= T; test_case++) {
			input = br.readLine().trim();
			
			int count = 0;
			int result = 0;
			for(int i = 0; i < input.length(); i++) {
				if(input.charAt(i) == 'O') result += ++count;
				else count = 0;
			}
			
			System.out.println(result);
		}
		
		br.close();
	}

}
