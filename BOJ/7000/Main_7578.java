import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 백준 7578번 공장
 * 문제 분류: 분할 정복, Merge Sort
 * @author Giwon
 */
public class Main_7578 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int N = Integer.parseInt(br.readLine().trim());
		Map<Integer, Integer> idxMap = new HashMap<>();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			idxMap.put(Integer.parseInt(st.nextToken()), i);
		}
		
		// A의 Index에 B에서의 Index를 저장
		st = new StringTokenizer(br.readLine());
		int[] moveIndex = new int[N];
		for(int i = 0; i < N; i++) {
			moveIndex[i] = idxMap.get(Integer.parseInt(st.nextToken()));
		}
		
		// 각 Index를 비교했을 때 A에서의 Index가 더 큰 경우를 MergeSort를 수행하며 카운트
		System.out.println(mergeSort(0, N - 1, moveIndex));
		br.close();
	}
	
	public static long merge(int start, int end, int[] arr) {
		if(start == end) return 0;
		
		// 병합 정렬한 결과 저장할 배열
		int[] result = new int[end - start + 1];
		int mid = start + (end - start) / 2;
		
		// 병합
		long count = 0;
		int leftSize = mid - start + 1;
		int i = 0, j = 0;
		while(i + start <= mid && j + mid + 1 <= end) {
			
			if(arr[i + start] >= arr[j + mid + 1]) {
				result[i + j] = arr[j++ + mid + 1];
				// 오른쪽 배열의 원소보다 큰 왼쪽 배열의 원소 개수를 카운트 
				count += leftSize - i;
			} else {
				result[i + j] = arr[i++ + start];
			}
		}
		
		// 남은 부분 정렬 
		while(i + start <= mid) {
			result[i + j] = arr[i++ + start];
		}
		while(j + mid + 1 <= end) {
			result[i + j] = arr[j++ + mid + 1];
		}
		
		// 병합 정렬한 결과 배열에 반영
		int len = end - start + 1;
		for(int k = 0; k < len; k++) {
			arr[start + k] = result[k];
		}
		
		return count;
	}
	
	// 병합 정렬
	public static long mergeSort(int start, int end, int[] arr) {
		if(start == end) return 0;
		
		int mid = start + (end - start) / 2;
		
		long left = mergeSort(start, mid, arr);
		long right = mergeSort(mid + 1, end, arr);
		
		long count = merge(start, end, arr);
		
		return count + left + right;
	}
}
