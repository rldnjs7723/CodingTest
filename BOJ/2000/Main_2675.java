import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 2675번 문자열 반복
 * 문제 분류: 문자열, 구현
 * @author Giwon
 */
public class Main_2675 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		final int T = Integer.parseInt(br.readLine().trim());
		int R, len;
		String input;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			
			// 반복 횟수
			R = Integer.parseInt(st.nextToken());
			input = st.nextToken();
			len = input.length();
			
			sb.setLength(0);
			char curr;
			for(int i = 0; i < len; i++) {
				curr = input.charAt(i);
				for(int iter = 0; iter < R; iter++) {
					sb.append(curr);
				}
			}
			
			bw.write(sb.toString() + "\n");
		}
		bw.close();
		br.close();
	}

}
