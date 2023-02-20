import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 2805번 나무 자르기
 * 문제 분류: 이진 탐색 (이분 탐색)
 * @author GIWON
 */
public class Main_2805 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 나무의 수
		int N = Integer.parseInt(st.nextToken());
		// 가져 갈 나무의 길이 (이후 계산에서 오버플로우 가능성이 있어 Long으로 입력)
		long M = Long.parseLong(st.nextToken());
		
		// 나무의 높이
		st = new StringTokenizer(br.readLine());
		long[] woods = new long[N];
		for(int i = 0; i < N; i++) {
			woods[i] = Long.parseLong(st.nextToken());
		}
		// 이진 탐색을 위한 정렬
		Arrays.sort(woods);
		
		// 이진 탐색으로 찾은 나무의 Index
		int idx = binarySearch(M, 0, N - 1, woods);
		// 현재 이진 탐색으로 찾은 나무의 높이 이상의 나무 개수
		int count = N - idx;
		long height = woods[idx];
		long length = woodLength(idx, woods);
		while(length < M) {
			// 절단기 높이를 1 줄이면 해당 위치보다 높은 곳에 위치한 나무들의 개수만큼 길이가 추가
			height--;
			length += count;
		}
		System.out.println(height);
		br.close();
	}
	
	// 특정 Index의 나무의 높이를 기준으로 잘랐을 때 가져 갈 수 있는 나무의 길이 계산
	public static long woodLength(int idx, long[] woods) {
		long sum = 0;
		long ref = woods[idx];
		for(int i = idx + 1; i < woods.length; i++) {
			sum += woods[i] - ref;
		}
		return sum;
	}
	
	// 이진 탐색의 경우 시간 복잡도 O(N log N)이므로 N <= 1000000에서 충분하다고 판단
	public static int binarySearch(long M, int start, int end, long[] woods) {
		if(start == end) return start;
		
		int mid = (start + end) / 2;
		long length = woodLength(mid, woods);
		if(length == M) return mid;
		// 가져 갈 길이가 넘친다면 더 높은 곳에 절단기를 위치하기
		else if(length > M) return binarySearch(M, mid + 1, end, woods);
		// 가져 갈 길이가 부족하면 더 낮은 곳에 절단기를 위치하기
		else return binarySearch(M, start, mid, woods);
	}
}
