import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 11049번 행렬 곱셈 순서
 * 문제 분류: 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_11049 {
	public static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 행렬 개수
		N = Integer.parseInt(br.readLine());
		// 행렬 입력
		StringTokenizer st;
		long r, c;
		long[] matrix = new long[N + 1];
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			r = Long.parseLong(st.nextToken());
			c = Long.parseLong(st.nextToken());
			
			if(i == 1) matrix[i - 1] = r;
			matrix[i] = c;
		}
		
		long[][] matmul = new long[N + 1][N + 1];
		for(int len = 2; len <= N; len++) {
			for(int i = 0; i <= N - len; i++) {
				int j = i + len;
				
				// ABC 행렬의 계산 값은 (AB)C 와 A(BC) 중에서 최솟값
				matmul[i][j] = Integer.MAX_VALUE;
				for(int k = i + 1; k < j; k++) {
					matmul[i][j] = Math.min(matmul[i][j], matmul[i][k] + matmul[k][j] + matrix[i] * matrix[k] * matrix[j]);
				}
			}
		}
		
		System.out.println(matmul[0][N]);
		br.close();
	}
}
