import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 1806번 부분합
 * 문제 분류: 투포인터, 슬라이딩 윈도우
 * @author Giwon
 */
public class Main_1806 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 수열 길이
		int N = Integer.parseInt(st.nextToken());
		// 부분합 목표
		int S = Integer.parseInt(st.nextToken());
		
		// 수열 입력
		st = new StringTokenizer(br.readLine());
		int[] sequence = new int[N];
		for(int i = 0; i < N; i++) {
			sequence[i] = Integer.parseInt(st.nextToken()); 
		}
		
		// 투 포인터로 이동하며 최소 길이 
		int min = Integer.MAX_VALUE;
		int sum = 0;
		int i = 0, j = 0;
		while(i < N && j < N) {
			sum += sequence[j];
			// 부분합이 목표치에 도달한 경우
			while(sum >= S) {
				// 길이 갱신
				min = Math.min(min, getLength(i, j));
				
				sum -= sequence[i];
				i++;
			}
			j++;
		}
		
		System.out.println(min == Integer.MAX_VALUE ? 0 : min);
		br.close();
	}

	public static int getLength(int i, int j) {
		return j - i + 1;
	}
}
