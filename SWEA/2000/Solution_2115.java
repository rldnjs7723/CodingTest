import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 2115번 벌꿀채취
 * 문제 분류: 완전 탐색, 슬라이딩 윈도우, Next Permutation
 * @author Giwon
 */
public class Solution_2115 {
	public static final int SELECT = 1;
	public static int N, M, C;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 테스트 케이스 개수
		final int T = Integer.parseInt(br.readLine().trim());
		
		int[][] state;
		List<Honey> honey = new ArrayList<>();
		List<Integer> slidingWindow = new ArrayList<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 벌통 크기
			N = Integer.parseInt(st.nextToken());
			// 선택할 수 있는 벌통 개수
			M = Integer.parseInt(st.nextToken());
			// 꿀을 채취할 수 있는 최대 양
			C = Integer.parseInt(st.nextToken());
			
			// 벌통 상태 입력
			state = new int[N][N];
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < N; col++) {
					state[row][col] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 가능한 모든 벌통 체크
			honey.clear();
			int len = M;
			for(int row = 0; row < N; row++) {
				// 슬라이딩 윈도우 시작점
				slidingWindow.clear();
				for(int i = 0; i < len; i++) {
					slidingWindow.add(state[row][i]);
				}
				honey.add(new Honey(row, 0, len - 1, slidingWindow));
				
				for(int col = len; col < N; col++) {
					slidingWindow.remove(0);
					slidingWindow.add(state[row][col]);
					
					honey.add(new Honey(row, col - len + 1, col, slidingWindow));
				}
			}
			
			// 벌꿀통 선택한 경우 중 2개 선택
			int[] combination = new int[honey.size()];
			Arrays.fill(combination, honey.size() - 2, honey.size(), SELECT);
			Honey A, B;
			int max = 0;
			do {
				int[] selected = selectedIndex(combination);
				A = honey.get(selected[0]);
				B = honey.get(selected[1]);
				
				// 두 경우에서 벌꿀통을 선택한 범위가 겹치면 다음 조합 탐색
				if(A.isCross(B)) continue;
				
				// 겹치지 않는다면 최대 수익 갱신
				max = Math.max(max, A.profit + B.profit);
			} while (nextPermutation(combination));
			
			System.out.println("#" + test_case + " " + max);
		}
		
		br.close();
	}
	
	// 조합 중에서 선택된 Index 리턴
	public static int[] selectedIndex(int[] combination) {
		int[] result = new int[2];
		int idx = 0;
		for(int i = 0; i < combination.length; i++) {
			if(combination[i] == SELECT) {
				result[idx++] = i;
				if(idx == 2) return result;
			}
		}
		return result;
	}
	
	// 2개의 벌통 선택 경우를 선택하기 위해 NP 사용
	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;
		// i 탐색
		int i = 0;
		for(i = n - 1; i >= 1; i--) {
			if(arr[i - 1] < arr[i]) break;
		}
		if(i == 0) return false;
		
		// j 탐색
		int temp = arr[i - 1];
		int j = 0;
		for(j = n - 1; j >= 0; j--) {
			if(temp < arr[j]) break;
		}
		
		// 위치 교환
		arr[i - 1] = arr[j];
		arr[j] = temp;
		
		// i부터 정렬
		Arrays.sort(arr, i, n);
		return true;
	}
	
	public static class Honey {
		// 벌통 시작, 끝 위치
		int row, startC, endC;
		// 벌꿀 양을 통해 얻은 수익
		int profit;
		
		public Honey(int row, int startC, int endC, List<Integer> honeyAmount) {
			this.row = row;
			this.startC = startC;
			this.endC = endC;
			this.profit = maxProfit(0, 0, honeyAmount);
		}
		
		// 모든 부분 집합을 탐색하여 최대 수익 계산
		public int maxProfit(int idx, int bitmask, List<Integer> honeyAmount) {
			int N = honeyAmount.size();
			// 모든 벌꿀을 선택했다면 수익 계산
			if(idx == N) {
				int amount = 0, profit = 0;
				for(int i = 0; i < N; i++) {
					if((bitmask & 1) > 0) {
						amount += honeyAmount.get(i);
						profit += honeyAmount.get(i) * honeyAmount.get(i);
					}
					bitmask = bitmask >> 1;
				}
				// 채취할 수 있는 양을 벗어나면 0 리턴
				if(amount > C) return 0;
				// 채취할 수 있는 양이라면 수익 리턴
				return profit;
			}
			
			int max = 0;
			// 현재 Index 벌꿀 채취하는 경우
			max = Math.max(max, maxProfit(idx + 1, bitmask | (1 << idx), honeyAmount));
			// 채취하지 않는 경우
			max = Math.max(max, maxProfit(idx + 1, bitmask, honeyAmount));
			
			return max;
		}
		
		// 벌꿀 선택이 겹치는지 체크
		public boolean isCross(Honey obj) {
			// 행이 다르면 겹치지 않음
			if(this.row != obj.row) return false;
			
			// 시작점이 겹치면 참
			if(this.startC <= obj.startC && this.endC >= obj.startC) return true;
			// 끝점이 겹치면 참
			if(this.startC <= obj.endC && this.endC >= obj.endC) return true;
			return false;
		}
		
		@Override
		public String toString() {
			return "[ row: " + row + " col: " + startC + " ~ " + endC + " profit: " + profit +  " ]";
		}
	}
}
