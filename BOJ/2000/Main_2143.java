import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * 백준 2143번 두 배열의 합
 * 문제 분류: 투 포인터
 * @author Giwon
 */
public class Main_2143 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		// A 배열 길이
		final int n = Integer.parseInt(br.readLine().trim());
		// A 배열 입력
		int[] A = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		
		// B 배열 길이
		final int m = Integer.parseInt(br.readLine().trim());
		// B 배열 입력
		int[] B = new int[m];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < m; i++) {
			B[i] = Integer.parseInt(st.nextToken());
		}
		
		// 정렬된 상태를 유지하기 위해 TreeMap 사용
		Map<Integer, Long> mapA = new TreeMap<>();
		Map<Integer, Long> mapB = new TreeMap<>();
		
		// 각각 Sliding Window로 부 배열 합 계산
		calculateSum(n, A, mapA);
		calculateSum(m, B, mapB);
		
		Integer[] valA = mapA.keySet().toArray(new Integer[0]);
		Integer[] valB = mapB.keySet().toArray(new Integer[0]);
		int lenA = mapA.size();
		int lenB = mapB.size();
		
		// 투 포인터를 통해 A의 부분합과 B의 부분합의 합을 계산
		long result = 0;
		int i = 0, j = lenB - 1;
		while(i < lenA && j >= 0) {
			if(valA[i] + valB[j] >= T) {
				if(valA[i] + valB[j] == T) {
					// 경우의 수 계산
					result += mapA.get(valA[i]) * mapB.get(valB[j]);
				}
				// T보다 크면 값을 줄이기
				j--;
			} else {
				// T보다 작으면 값을 늘리기
				i++;
			}
		}
		
		System.out.println(result);
		br.close();
	}
	
	// 부분합 계산
	public static void calculateSum(int n, int[] arr, Map<Integer, Long> map) {
		for(int start = 0; start < n; start++) {
			int sum = 0;
			for(int end = start; end < n; end++) {
				sum += arr[end];
				putMap(sum, map);
			}
		}
	}

	// Map의 값 증가시키기
	public static void putMap(int val, Map<Integer, Long> map) {
		if(!map.containsKey(val)) {
			map.put(val, 0L);
		}
		
		map.put(val, map.get(val) + 1);
	}
	
}