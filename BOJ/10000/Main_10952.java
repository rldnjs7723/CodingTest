import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 10952번 A + B - 5
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_10952 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st;
		String input;
		int A, B;
		while((input = br.readLine()) != null) {
			st = new StringTokenizer(input);
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			if(A == 0 && B == 0) break;
			bw.write((A + B) + "\n");
		}
		
		bw.close();
		br.close();
	}

}
