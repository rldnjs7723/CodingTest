import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * 백준 17353번 하늘에서 떨어지는 1, 2, ..., R-L+1개의 별
 * 문제 분류: 느리게 갱신되는 세그먼트 트리
 * @author Giwon
 */
public class Main_17353 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		// 별이 떨어지는 점의 개수
		final int N = Integer.parseInt(br.readLine().trim());
		// 별 개수 입력
		st = new StringTokenizer(br.readLine());
		long[] sequence = new long[N];
		for(int i = 0; i < N; i++) {
			sequence[i] = Long.parseLong(st.nextToken());
		}
		
		// 세그먼트 트리 초기화
		SegmentTree segmentTree = new SegmentTree(N, sequence);
		
//		System.out.println(Arrays.toString(segmentTree.sum));
		
		// 쿼리의 개수
		final int Q = Integer.parseInt(br.readLine().trim());
		// 쿼리 입력
		int T, L, R, X;
		for(int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			T = Integer.parseInt(st.nextToken());
			
			if(T == 1) {
				L = Integer.parseInt(st.nextToken());
				R = Integer.parseInt(st.nextToken());
				
				segmentTree.update(0, N - 1, 1, L - 1, R - 1);
			} else if(T == 2) {
				X = Integer.parseInt(st.nextToken());
				
				bw.write(segmentTree.getSum(0, N - 1, 1, X - 1) + "\n");
			}
			
//			System.out.println("sum : " + Arrays.toString(segmentTree.sum));
//			System.out.println("lazy: " + Arrays.toString(segmentTree.lazy));
//			System.out.println("lazyCount: " + Arrays.toString(segmentTree.lazyCount));
		}
		
		bw.close();
		br.close();
	}

	public static class SegmentTree {
		// 위치의 개수
		int N;
		// 별의 개수 합
		long[] sum;
		// 이후에 방문 시 업데이트 해야할 값
		long[] lazy;
		// Lazy하게 갱신 할 횟수 카운트
		long[] lazyCount;
		
		public SegmentTree(int N, long[] sequence) {
			this.N = N;
			this.sum = new long[4 * N];
			this.lazy = new long[4 * N];
			this.lazyCount = new long[4 * N];
			
			init(0, N - 1, 1, sequence);
		}
		
		public long init(int start, int end, int idx, long[] sequence) {
			// 리프 노드
			if(start == end) {
				sum[idx] = sequence[start];
				return sum[idx];
			}
			
			int mid = start + (end - start) / 2;
			long left = init(start, mid, idx * 2, sequence);
			long right = init(mid + 1 ,end, idx * 2 + 1, sequence);
			sum[idx] = left + right;
			
			return sum[idx];
		}
		
		public void updateLazy(int start, int end, int idx) {
			if(lazyCount[idx] > 0) {
				long count = end - start + 1;
				// 첫 항이 lazy[idx], 공차가 lazyCount[idx]인 등차수열의 합
				sum[idx] += count * (2 * lazy[idx] + (count - 1) * lazyCount[idx]) / 2;
				if(start != end) {
					int mid = start + (end - start) / 2;
					lazy[idx * 2] += lazy[idx];
					lazyCount[idx * 2] += lazyCount[idx];
					lazy[idx * 2 + 1] += lazy[idx] + (mid - start + 1) * lazyCount[idx];
					lazyCount[idx * 2 + 1] += lazyCount[idx];
				}
				
				lazy[idx] = 0; 
				lazyCount[idx] = 0;
			}
		}
		
		public long update(int start, int end, int idx, int rangeStart, int rangeEnd) {
			updateLazy(start, end, idx);
			// 범위에서 벗어난 경우 그대로 리턴
			if(rangeEnd < start || end < rangeStart) return sum[idx];
			
			// 범위에 포함되는 경우 값 갱신
			if(rangeStart <= start && end <= rangeEnd) {
				long count = end - start + 1;
				long val = start - rangeStart + 1;
				int mid = start + (end - start) / 2;
				sum[idx] += count * (count + 1) / 2 + (val - 1) * count;
				
				// lazy 값 갱신
				if(start != end) {
					lazy[idx * 2] += val;
					lazyCount[idx * 2]++;
					lazy[idx * 2 + 1] += val + mid - start + 1;
					lazyCount[idx * 2 + 1]++;
				}
				
				return sum[idx];
			}

			// 범위가 겹치는 경우
			int mid = start + (end - start) / 2;
			// 왼쪽 자식 갱신
			long left = update(start, mid, idx * 2, rangeStart, rangeEnd);
			// 오른쪽 자식 갱신
			long right = update(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd);
			sum[idx] = left + right;
			return sum[idx];
		}
		
		public long getSum(int start, int end, int idx, int X) {
			updateLazy(start, end, idx);
			// 범위에서 벗어난 경우 0 리턴
			if(X < start || end < X) return 0;
			
			// 원하는 위치에 도달한 경우 그대로 리턴
			if(start == end) return sum[idx];
			
			int mid = start + (end - start) / 2;
			return getSum(start, mid, idx * 2, X) + getSum(mid + 1, end, idx * 2 + 1, X);
		}
	}
}
