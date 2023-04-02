import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 10950번 A + B - 3
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_10950 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		final int T = Integer.parseInt(br.readLine().trim());
		int A, B;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			sb.append((A + B) + "\n");
		}
		
		System.out.println(sb.toString());
		br.close();
	}

}
