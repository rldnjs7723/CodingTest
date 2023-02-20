import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 12015번 가장 긴 증가하는 부분 수열2
 * 문제 분류: 이분 탐색, LIS (Longest Increasing Subsequence) 알고리즘
 * @author Giwon
 */
public class Main_12015 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 수열 크기
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 전체 수열 입력
		int[] sequence = new int[N];
		for(int i = 0; i < N; i++) {
			sequence[i] = Integer.parseInt(st.nextToken());
		}
		
		// 수열 각 위치별 최대 부분 수열 길이
		int[] sequenceCount = new int[N];
		// 현재 최장 subsequence 기록
		int[] subsequence = new int[N];
		int size = 1, idx;
		subsequence[0] = sequence[0];
		for(int i = 1; i < N; i++) {
			idx = findIdx(sequence[i], 0, size - 1, subsequence);
			// 자신보다 작은 값이 없으면 새로 추가
			if(idx == size) subsequence[size++] = sequence[i];
			// 현재 Index 값이 자신보다 크면 그 값을 교체
			else subsequence[idx] = sequence[i];
			// Sequence의 길이는 자신이 들어간 Index + 1
			sequenceCount[i] = idx + 1;
		}
		
		// 최장 subsequence 길이 출력
		System.out.println(size);
		
		br.close();
	}
	
	// 이분 탐색으로 현재 수를 넣을 수 있는 위치 탐색
	public static int findIdx(int target, int start, int end, int[] sequence) {
		int mid = (start + end) / 2;
		 
		if(sequence[mid] == target) return mid;
		else if(sequence[mid] > target) {
			// 마지막 위치의 값이 자신보다 크면 그 값을 교체
			if(start == end) return start;
			return findIdx(target, start, mid, sequence);
		} else {
			// 자신보다 작은 값이 없으면 새로 추가
			if(start == end) return end + 1;
			return findIdx(target, mid + 1, end, sequence);
		}
	}
}
