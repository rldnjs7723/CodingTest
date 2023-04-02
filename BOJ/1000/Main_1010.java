import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 1010번 다리 놓기
 * 문제 분류: 다이나믹 프로그래밍, 수학 (이항 계수)
 * @author Giwon
 */
public class Main_1010 {
	public static final int MAX = 30;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		// nCr = combination[n][r]
		long[][] combination = new long[MAX + 1][MAX + 1];
		// 이항계수 미리 계산
		for(int n = 0; n <= MAX; n++) {
			for(int r = 0; r <= n; r++) {
				if(r == 0 || r == n) combination[n][r] = 1;
				else combination[n][r] = combination[n - 1][r - 1] + combination[n - 1][r];
			}
		}
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, M;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			// 동쪽에 있는 다리 중에서 서쪽에 있는 다리의 개수만큼 추출
			bw.write(Long.toString(combination[M][N]) + "\n");
		}
		
		bw.close();
		br.close();
	}

}
