import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 18185번 라면 사기 (Small)
 * 문제 분류: 그리디 알고리즘, 슬라이딩 윈도우
 * @author Giwon
 */
public class Main_18185 {
	public static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 라면 공장 개수
		N = Integer.parseInt(br.readLine().trim());
		// 라면 공장에서 구매할 라면 개수 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] A = new int[N];
		for(int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		
		int price = 0, count;
		for(int i = 0; i < N - 2; i++) {
			// 현재 라면 공장에서 살 라면이 없다면 생략
			while(A[i] > 0) {
				if(A[i + 1] <= 0) {
					// 다음 라면 공장에서 살 라면이 없다면 1개만 구매
					price += 3 * A[i];
					A[i] = 0;
				} else if(A[i + 1] > A[i + 2]) {
					// 다음 라면 공장에서 살 라면의 개수가 다다음 라면 공장에서 살 라면 개수보다 크면 2개만 구매
					count = Math.min(A[i], A[i + 1] - A[i + 2]);
					price += 5 * count;
					A[i] -= count;
					A[i + 1] -= count;
				} else {
					// 다음 라면 공장에서 살 라면의 개수가 다다음 라면 공장에서 살 라면 개수보다 작거나 같으면 3개 구매
					count = Math.min(Math.min(A[i], A[i + 1]), A[i + 2]);
					price += 7 * count;
					A[i] -= count;
					A[i + 1] -= count;
					A[i + 2] -= count;
				}
			}
		}
		
		// 마지막 두 개 공장 처리
		int i = N - 2;
		while(A[i] > 0) {
			if(A[i + 1] <= 0) {
				// 다음 라면 공장에서 살 라면이 없다면 1개만 구매
				price += 3 * A[i];
				A[i] = 0;
			} else {
				// 다음 라면 공장에서 살 라면이 있는 경우
				count = Math.min(A[i], A[i + 1]);
				price += 5 * count;
				A[i] -= count;
				A[i + 1] -= count;
			}
		}
		// 마지막 공장
		i = N - 1;
		price += 3 * A[i];
		
		System.out.println(price);
		br.close();
	}
}
