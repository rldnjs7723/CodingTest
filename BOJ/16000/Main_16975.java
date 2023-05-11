import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 16975번 수열과 쿼리 21
 * 문제 분류: 느리게 갱신되는 세그먼트 트리
 * @author Giwon
 */
public class Main_16975 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		// 수열 크기
		final int N = Integer.parseInt(br.readLine().trim());
		// 수열 입력
		int[] sequence = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			sequence[i] = Integer.parseInt(st.nextToken());
		}
		
		// 세그먼트 트리 츠기화
		SegmentTree segmentTree = new SegmentTree(N, sequence);
		
		// 쿼리 개수
		final int M = Integer.parseInt(br.readLine().trim());
		// 쿼리 입력
		int type, rangeStart, rangeEnd, k;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			type = Integer.parseInt(st.nextToken());
			
			if(type == 1) {
				rangeStart = Integer.parseInt(st.nextToken());
				rangeEnd = Integer.parseInt(st.nextToken());
				k = Integer.parseInt(st.nextToken());
				segmentTree.addValue(rangeStart, rangeEnd, k);
			} else if(type == 2) {
				k = Integer.parseInt(st.nextToken());
				bw.write(segmentTree.get(k) + "\n");
			}
		}
				
		br.close();
		bw.close();
	}

	public static class SegmentTree {
		int N;
		long[] sum;
		long[] lazy;
		long result;
		
		public SegmentTree(int N, int[] sequence) {
			this.N = N;
			this.sum = new long[N * 4];
			this.lazy = new long[N * 4];
			
			init(0, N - 1, 1, sequence);
		}
		
		private long init(int start, int end, int idx, int[] sequence) {
			if(start == end) {
				sum[idx] = sequence[start];
				return sum[idx];
			}
			
			// 리프 노드가 아니라면 왼쪽, 오른쪽 자식 노드 값 합산
			int mid = start + (end - start) / 2;
			long left = init(start, mid, idx * 2, sequence);
			long right = init(mid + 1, end, idx * 2 + 1, sequence);
			sum[idx] = left + right;
			
			return sum[idx];
		}
		
		private void updateLazy(int start, int end, int idx) {
			if(lazy[idx] != 0) {
				int count = end - start + 1;
				sum[idx] += lazy[idx] * count;
				
				if(start != end) {
					// 리프 노드가 아닌 경우 자식 노드에 Lazy 전파
					lazy[idx * 2] += lazy[idx];
					lazy[idx * 2 + 1] += lazy[idx];
				}
				
				lazy[idx] = 0;
			}
		}
		
		public void addValue(int rangeStart, int rangeEnd, int val) {
			// Index 1부터 시작하게 설정
			update(0, N - 1, 1, rangeStart - 1, rangeEnd - 1, val);
		}
		
		private long update(int start, int end, int idx, int rangeStart, int rangeEnd, int val) {
			// Lazy하게 갱신
			updateLazy(start, end, idx);
			
			// 범위에서 벗어나는 경우 그대로 리턴
			if(rangeEnd < start || end < rangeStart) return sum[idx];
			
			// 범위에 포함되는 경우
			if(rangeStart <= start && end <= rangeEnd) {
				int count = end - start + 1;
				sum[idx] += val * count;
				
				if(start != end) {
					// 자식 노드 Lazy 전파
					lazy[idx * 2] += val;
					lazy[idx * 2 + 1] += val;
				}
				
				return sum[idx];
			}
			
			// 범위가 겹치는 경우
			int mid = start + (end - start) / 2;
			long left = update(start, mid, idx * 2, rangeStart, rangeEnd, val);
			long right = update(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd, val);
			sum[idx] = left + right;
			
			return sum[idx];
		}
		
		public long get(int index) {
			// Index 1부터 시작하게 설정
			getValue(0, N - 1, 1, index - 1);
			return result;
		}
		
		private void getValue(int start, int end, int idx, int target) {
			// Lazy하게 갱신
			updateLazy(start, end, idx);
			
			// 범위에서 벗어나는 경우 생략
			if(end < target || target < start) return;
			
			// 범위가 일치하는 경우 결과값에 저장
			if(start == end && start == target) {
				result = sum[idx];
				return;
			}
			
			// 포함하는 경우
			int mid = start + (end - start) / 2;
			// 왼쪽 자식
			getValue(start, mid, idx * 2, target);
			// 오른쪽 자식
			getValue(mid + 1, end, idx * 2 + 1, target);
		}
	}
}
