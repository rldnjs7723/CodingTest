import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/** 
 * 백준 15686번 치킨 배달
 * 문제 분류: 조합 완전 탐색. Next Permutation
 * @author GIWON
 */
public class Main_15686 {
	public static final int HOME = 1, CHICKEN = 2;
	public static final int EXIST = 1;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 행, 열 크기
		int N = Integer.parseInt(st.nextToken());
		// 폐업시키지 않을 치킨집 수
		int M = Integer.parseInt(st.nextToken());
		
		// 도시 위치 입력
		List<Chicken> franchise = new ArrayList<>();
		int[][] city = new int[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				city[row][col] = Integer.parseInt(st.nextToken());
				// 치킨집은 따로 저장해두기
				if(city[row][col] == CHICKEN) {
					franchise.add(new Chicken(row, col));
				}
			}
		}
		
		// 치킨집 수
		int K = franchise.size();
		// next permutation으로 조합 구하기 위한 배열 선언
		int[] combination = new int[K];
		for(int i = 0; i < M; i++) {
			combination[K - 1 - i] = EXIST;
		}
		
		int min = Integer.MAX_VALUE, currMin, distance;
		do {
			distance = 0;
			// 조합대로 치킨집을 폐업하지 않았을 때 각 가정에서의 치킨 거리 총합 계산
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					// 가정일 경우 치킨 거리 계산
					if(city[row][col] == HOME) {
						currMin = Integer.MAX_VALUE;
						
						for(int i = 0; i < K; i++) {
							// 현재 존재하는 치킨집 대상으로 치킨 거리 계산
							if(combination[i] == EXIST) {
								currMin = Math.min(currMin, franchise.get(i).distance(row, col));
							}
						}
						// 치킨 거리 합산
						distance += currMin;
					}
				}
			}
			min = Math.min(distance, min);
		} while (nextPermutation(combination));
		
		System.out.println(min);
		
		br.close();
	}
	
	// 디버깅용 배열 출력
	public static void printArr(int[][] arr) {
		for(int[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
	}
	
	// N <= 50이고, O(N^2) 정도의 시간 복잡도가 나오므로 완전 탐색으로 풀이
	// permutation 최대 13CM 정도. N^2에 비하면 미미한 수치
	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;
		
		// i 탐색
		int i = n - 1;
		for(i = n - 1; i >= 1; i--) {
			if(arr[i - 1] < arr[i]) break;
		}
		if(i == 0) return false;
		
		// 바꿀 위치 탐색
		int temp = arr[i - 1];
		int j = n - 1;
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
	
	// 치킨집
	public static class Chicken {
		int row, col;
		
		public Chicken(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		// 치킨 거리 계산
		public int distance(int row, int col) {
			return Math.abs(this.row - row) + Math.abs(this.col - col);
		}
	}
}
