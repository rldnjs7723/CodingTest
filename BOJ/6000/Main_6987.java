import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 6987번 월드컵
 * 문제 분류: 백트래킹, 비트마스킹, Next Permutation
 * @author Giwon
 */
public class Main_6987 {
	public static final int SELECT = 1;
	public static long bitmask = 0;
	// 예제 수, 나라 수
	public static final int T = 4, N = 6;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 승, 무, 패 배열
		int[] win = new int[N];
		int[] draw = new int[N];
		int[] lose = new int[N];
		boolean available;
		for(int test_case = 0; test_case < T; test_case++) {
			st = new StringTokenizer(br.readLine());
			
			// 가능한 결과 입력
			available = true;
			for(int i = 0; i < N; i++) {
				win[i] = Integer.parseInt(st.nextToken());
				draw[i] = Integer.parseInt(st.nextToken());
				lose[i] = Integer.parseInt(st.nextToken());
				
				// 승, 무, 패 총합은 무조건 5
				if(win[i] + draw[i] + lose[i] != N - 1) available = false;
			}
			if(!available) {
				sb.append(0 + " ");
				continue;
			}
			
			// 승, 패, 무승부 체크
			bitmask = 0;
			available = check(win, draw, lose, 0, 0);
			for(int i = 0; i < N; i++) {
				// 승, 패, 무승부 수가 남아있으면 불가능
				if(win[i] > 0 || lose[i] > 0 || draw[i] > 0) {
					available = false;
					break;
				}
			}
			sb.append((available ? 1 : 0) + " ");
		}
		System.out.println(sb.toString());
		
		br.close();
	}
	
	// next permutation으로 조합 탐색
	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;
		
		// i 탐색
		int i = n - 1;
		for(i = n - 1; i >= 1; i--) {
			if(arr[i - 1] < arr[i]) break;
		}
		if(i == 0) return false;
		
		// j 탐색
		int temp = arr[i - 1];
		int j = n - 1;
		for(j = n - 1; j >= 0; j--) {
			if(temp < arr[j]) break;
		}
		
		// 위치 교환
		arr[i - 1] =  arr[j];
		arr[j] = temp;
		
		// i부터 정렬
		Arrays.sort(arr, i, n);
		return true;
	}

	public static boolean check(int[] win, int[] draw, int[] lose, int currCountry, int iter) {
		// 현재 비교할 개수만큼의 나라를 뽑아서 탐색
		int[] combination = new int[N - 1];
		int[] A = iter == 0 ? win : draw;
		int[] B = iter == 0 ? lose : draw;
		
		// 개수가 나라의 개수 이상이면 불가능한 조합
		if(A[currCountry] > N - 1) return false;
		Arrays.fill(combination, N - 1 - A[currCountry], N - 1, SELECT);
		
		int target;
		boolean available;
		do {
			available = true;
			for(int i = 0; i < N - 1; i++) {
				target = i < currCountry ? i : i + 1;
				
				if(combination[i] == SELECT) {
					// 비교할 대상의 수가 0이면 값을 뺄 수 없으므로 불가능한 조합
					if(B[target] == 0) {
						available = false;
						break;
					}
					// 이전에 경기한 나라와는 다시 경기할 수 없으므로 선택 불가
					if((bitmask & getBit(currCountry, target)) > 0) {
						available = false;
						break;
					}
				}
			}
			// 불가능하면 다음 조합 탐색
			if(!available) continue;
			
			// 비교할 대상에서 값을 빼기
			for(int i = 0; i < N - 1; i++) {
				target = i < currCountry ? i : i + 1;
				if(combination[i] == SELECT) {
					A[currCountry]--;
					B[target]--;
					bitmask |= getBit(currCountry, target);
				}
			}
			
			// 현재 자신이 마지막 국가면 가능한 조합
			if(currCountry == N - 1) {
				if(iter == 1) return true;
				else {
					// 무승부 체크
					return check(win, draw, lose, 0, iter + 1);
				}
			}
			
			boolean checkResult = check(win, draw, lose, currCountry + 1, iter);
			if(!checkResult) {
				// 현재 조합이 불가능하면 이전 상태로 롤백 후 다른 조합 적용
				for(int i = 0; i < N - 1; i++) {
					target = i < currCountry ? i : i + 1;
					if(combination[i] == SELECT) {
						A[currCountry]++;
						B[target]++;			
						bitmask -= getBit(currCountry, target);
					}
				}
			} else return true;
		} while(nextPermutation(combination));
		
		// 모든 조합을 탐색했을 때 불가능인 경우
		return false;
	}
	
	// 두 나라가 경기를 수행했는지 확인할 비트마스크
	public static long getBit(int A, int B) {
		long result = 0;
		result |= 1L << (A * N + B);
		result |= 1L << (B * N + A);
		return result;
	}
	
}
