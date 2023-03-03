import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 2115번 벌꿀채취
 * 문제 분류: 완전 탐색, 슬라이딩 윈도우, Next Permutation
 * @author Giwon
 */
public class Solution_2115 {
	public static int N, M, C;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 테스트 케이스 개수
		final int T = Integer.parseInt(br.readLine().trim());
		
		int[][] state;
		List<Honey> honey = new ArrayList<>();
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
			int windowSum;
			for(int len = 1; len <= M; len++) {				
				for(int row = 0; row < N; row++) {
					// 슬라이딩 윈도우 시작점
					windowSum = 0;
					for(int i = 0; i < len; i++) {
						windowSum += state[row][i];
					}
					honey.add(new Honey(row, 0, len - 1, windowSum));
					
					for(int col = len; col <= N - len; col++) {
						windowSum -= state[row][col - len];
					}
				}
			}
			
		}
		
		br.close();
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
		// 벌꿀 양
		int val;
		
		public Honey(int row, int startC, int endC, int val) {
			this.row = row;
			this.startC = startC;
			this.endC = endC;
			this.val = val;
		}
		
		// 벌꿀 선택이 겹치는지 체크
		public boolean isCross(Honey obj) {
			// 시작점이 겹치면 참
			if(this.startC <= obj.startC && this.endC >= obj.startC) return true;
			// 끝점이 겹치면 참
			if(this.startC <= obj.endC && this.endC >= obj.endC) return true;
			return false;
		}
	}
	
}
