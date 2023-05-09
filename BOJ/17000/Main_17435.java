import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 17435번 합성함수와 쿼리
 * 문제 분류: 분할 정복, 희소 배열
 * @author Giwon
 */
public class Main_17435 {
	public static final int MAX_N = 500000;
	public static final int MAX_DEPTH = 18;
	public static int[] twoPow;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		// 2의 제곱수 미리 계산
		twoPow = new int[MAX_DEPTH + 1];
		twoPow[0] = 1;
		for(int i = 1; i <= MAX_DEPTH; i++) {
			twoPow[i] = twoPow[i - 1] * 2;
		}
		
		// 수의 범위
		final int m = Integer.parseInt(br.readLine().trim());
		// f_2^i (x) = func[x][i]
		int[][] func = new int[m + 1][MAX_DEPTH + 1];
		// 정수 매핑 입력
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= m; i++) {
			func[i][0] = Integer.parseInt(st.nextToken());
		}
		
		// 정수 매핑 미리 계산
		for(int n = 1; n <= MAX_DEPTH; n++) {
			for(int i = 1; i <= m; i++) {
				func[i][n] = func[func[i][n - 1]][n - 1];
			}
		}
		
		// 쿼리의 개수
		final int Q = Integer.parseInt(br.readLine().trim());
		int n, x, log;
		for(int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			
			while(n > 0) {
				log = log2(n);
				x = func[x][log];
				n -= twoPow[log];
			}
			
			bw.write(x + "\n");
		}
		
		bw.close();
		br.close();
	}
	
	public static int log2(int target) {
		int cnt = -1;
		while(target > 0) {
			target /= 2;
			cnt++;
		}
		
		return cnt;
	}
	
}
