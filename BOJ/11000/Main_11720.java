import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 11720번 숫자의 합
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_11720 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int N = Integer.parseInt(br.readLine().trim());
		String input = br.readLine().trim();
		int result = 0;
		for(int i = 0; i < N; i++) {
			result += input.charAt(i) - '0';
		}
		
		System.out.println(result);
		br.close();
	}

}
