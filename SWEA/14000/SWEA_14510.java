import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 14510번 나무 높이
 * 문제 분류: 구현, 그리디 알고리즘?, 정수론?
 * @author Giwon
 */
public class SWEA_14510 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st;
		final int T = Integer.parseInt(br.readLine());
		int N, max;
		int[] tree;
		int two, one;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 나무 개수
			N = Integer.parseInt(br.readLine());
			// 나무 높이 입력
			max = 0;
			st = new StringTokenizer(br.readLine());
			tree = new int[N];
			for(int i = 0; i < N; i++) {
				tree[i] = Integer.parseInt(st.nextToken());
				// 최대 나무 높이 계산
				max = Math.max(max, tree[i]);
			}
			
			// 더 늘려야 할 높이 설정
			two = 0; one = 0;
			for(int i = 0; i < N; i++) {
				// 홀수면 1 개수 추가
				one += (max - tree[i]) % 2;
				two += (max - tree[i]) / 2;
			}
			
			// 1의 개수만큼은 홀수날에 물을 줘야 함
			// 1이 0개면 날짜 수정 없음
			two = Math.max(0, two - Math.max(0, one - 1));
			int count = Math.max(2 * one - 1, 0);
			
			// 4일이면 2가 3개 감소
			count += (two / 3) * 4;
			two %= 3;
			if(two > 0) {
				if(count % 2 == 1) {
					// 짝수부터 시작할 때 (홀수가 1개라도 있을 때)
					if(two == 1) {
						// 짝수 날에 한 번 물 주기
						count++;
					} else if(two == 2) {
						// 짝수 날에 두 번 물 주기
						count += 3;
					}
				} else {
					// 홀수부터 시작할 때 (1이 0개)
					if(two == 1) {
						// 짝수 날에 한 번 물 주기
						count += 2;
					} else if(two == 2) {
						// 홀수 날에 두 번, 짝수 날에 한 번 물 주기
						count += 3;
					}
				}
			}
			
			System.out.println("#" + test_case + " " + count);
		}
		br.close();
	}

}
