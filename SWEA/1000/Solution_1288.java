import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SWEA 1288번 새로운 불면증 치료법
 * 문제 분류: 비트마스킹, 완전 탐색
 * @author Giwon
 */
public class Solution_1288 {
	public static final int FULL = (1 << 10) - 1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int T = Integer.parseInt(br.readLine());
		int bitmask, curr, temp, N;
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			bitmask = 0;
			curr = 0;
			do {
				curr += N;
				temp = curr;
				// 현재 수의 모든 자릿수를 돌아가면서 비트마스킹
				while(temp > 0) {
					bitmask |= 1 << (temp % 10);
					temp /= 10;
				}
				// 모든 수를 봤을 때의 비트마스크와 비교했을 때 다르면 N을 한번 더 더해서 진행
			} while ((bitmask ^ FULL) > 0);
			
			System.out.println("#" + test_case + " " + curr);
		}
		br.close();
	}

}
