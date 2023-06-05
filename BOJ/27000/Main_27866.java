import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 27866번 문자와 문자열
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_27866 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine().trim();
		int index = Integer.parseInt(br.readLine().trim());
		
		System.out.println(input.charAt(index - 1));
		br.close();
	}
}
