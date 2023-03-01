import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 18186번 라면 사기 (Large)
 * 문제 분류: 그리디 알고리즘, 슬라이딩 윈도우
 * @author Giwon
 */
public class Main_18186 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 라면 공장 개수
		int N = Integer.parseInt(st.nextToken());
		// 라면 하나 구매할 때 가격
		long B = Integer.parseInt(st.nextToken());
		// 라면 여러 개 구매할 때 추가 비용
		long C = Integer.parseInt(st.nextToken());
		// 라면 공장에서 구매할 라면 개수 입력
		st = new StringTokenizer(br.readLine());
		// 입력 크기가 10^6이므로 오버플로우 가능성이 있어 long으로 저장
		long[] A = new long[N];
		for(int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		// 개수별 구매 가격
		long[] buyPrice = {0L, B, B + C, B + 2 * C};
		
		
		long price = 0, count;
		for(int i = 0; i < N - 2; i++) {
			// 현재 라면 공장에서 살 라면이 없다면 생략
			while(A[i] > 0) {
				// B가 C보다 작거나 같으면 무조건 라면은 1개만 구매
				if(B <= C) {
					price += buyPrice[1] * A[i];
					A[i] = 0;
					continue;
				}
				
				if(A[i + 1] <= 0) {
					// 다음 라면 공장에서 살 라면이 없는 경우 1개만 구매
					// i번 공장에서 정확하게 Ai개의 라면을 사야 하므로 1 0 1인 상황에서 3개 사는 것은 불가능
					price += buyPrice[1] * A[i];
					A[i] = 0;
				} else if(A[i + 1] > A[i + 2]) {
					// 다음 라면 공장에서 살 라면의 개수가 다다음 라면 공장에서 살 라면 개수보다 크면 2개만 구매
					count = Math.min(A[i], A[i + 1] - A[i + 2]);
					price += buyPrice[2] * count;
					A[i] -= count;
					A[i + 1] -= count;
				} else {
					// 다음 라면 공장에서 살 라면의 개수가 다다음 라면 공장에서 살 라면 개수보다 작거나 같으면 3개 구매
					count = Math.min(Math.min(A[i], A[i + 1]), A[i + 2]);
					price += buyPrice[3] * count;
					A[i] -= count;
					A[i + 1] -= count;
					A[i + 2] -= count;
				}
			}
		}
		
		// 마지막 두 개 공장 처리
		int i = N - 2;
		while(A[i] > 0) {
			// B가 C보다 작거나 같으면 무조건 라면은 1개만 구매
			if(B <= C) {
				price += buyPrice[1] * A[i];
				A[i] = 0;
				continue;
			}
			
			if(A[i + 1] <= 0) {
				// 다음 라면 공장에서 살 라면이 없는 경우 1개만 구매
				price += buyPrice[1] * A[i];
				A[i] = 0;
			} else {
				// 다음 라면 공장에서 살 라면이 있는 경우
				count = Math.min(A[i], A[i + 1]);
				price += buyPrice[2] * count;
				A[i] -= count;
				A[i + 1] -= count;
			}
		}
		// 마지막 공장
		i = N - 1;
		price += buyPrice[1] * A[i];
		
		System.out.println(price);
		br.close();
	}
}
