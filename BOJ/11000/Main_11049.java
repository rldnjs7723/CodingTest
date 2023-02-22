import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 11049번 행렬 곱셈 순서
 * 문제 분류: 분할 정복?
 * @author Giwon
 */
public class Main_11049 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 행렬 개수
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		int r, c;
		int[] matrix = new int[N + 1];
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			r = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			
			if(i == 1) matrix[i - 1] = r;
			matrix[i] = c;
		}
		
		System.out.println(Arrays.toString(matrix));
		
		br.close();
	}
	
}
