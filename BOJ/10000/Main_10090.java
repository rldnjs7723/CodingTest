import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 10090번 Counting Inversions
 * 문제 분류: 세그먼트 트리
 * @author Giwon
 */
public class Main_10090 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 수열 길이
		final int n = Integer.parseInt(br.readLine().trim());
		// 수열 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] sequence = new int[n];
		for(int i = 0; i < n; i++) {
			sequence[i] = Integer.parseInt(st.nextToken());
		}
		
		// 세그먼트 트리 구성
		SegmentTree segTree = new SegmentTree(n, sequence);
		
		System.out.println(segTree.inversionCount);
		br.close();
	}

	public static class SegmentTree {
		int N;
		int[] sequence;
		long[] lineCount;
		long inversionCount;
		
		public SegmentTree(int N, int[] sequence) {
			this.N = N;
			this.sequence = sequence;
			// 세그먼트 트리 크기 초기화
			this.lineCount = new long[N * 4];
			this.inversionCount = 0;
			
			for(int val: sequence) {
				inversionCount += init(val);
			}
		}
		
		public long init(int val) {
			// 수열의 모든 수는 1 줄여서 표현
			val--;
			
			// Inversion이 발생한 횟수 계산
			long count = getInversionCount(0, N - 1, 1, val, N - 1);
			// 현재 수가 나타났음을 표시
			updateLineCount(0, N - 1, 1, val);
			
			return count;
		}
		
		// 자신보다 Index가 작은 수 중, 수열에서 먼저 나타난 숫자의 개수를 카운트
		public long getInversionCount(int start, int end, int idx, int rangeStart, int rangeEnd) {
			// 범위에서 벗어난 경우 제외
			if(end < rangeStart || rangeEnd < start) return 0;
			// 범위에 완전히 포함되는 경우 그대로 리턴
			if(rangeStart <= start && end <= rangeEnd) return lineCount[idx];
			
			// 범위가 겹치는 경우 분할
			int mid = start + (end - start) / 2;
			long left = getInversionCount(start, mid, idx * 2, rangeStart, rangeEnd);
			long right = getInversionCount(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd);
			
			return left + right;
		}
		
		// 현재 수가 이미 나타났음을 표시
		public long updateLineCount(int start, int end, int idx, int val) {
			// 범위에 현재 수를 포함하지 않는 경우 그대로 리턴
			if(val < start || end < val) return lineCount[idx];
			
			// 리프 노드 처리
			if(start == end) {
				lineCount[idx] = 1;
				return lineCount[idx];
			}
			
			// 범위가 겹치는 경우 갱신
			int mid = start + (end - start) / 2;
			long left = updateLineCount(start, mid, idx * 2, val);
			long right = updateLineCount(mid + 1, end, idx * 2 + 1, val);
			
			lineCount[idx] = left + right;
			return lineCount[idx];
		}
		
		
	}
	
}
