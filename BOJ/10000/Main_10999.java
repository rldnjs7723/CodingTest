import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 10999번 구간 합 구하기 2
 * 문제 분류: 느리게 갱신되는 세그먼트 트리
 * @author Giwon
 */
public class Main_10999 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 수의 개수
		final int N = Integer.parseInt(st.nextToken());
		// 수의 변경이 일어나는 횟수
		final int M = Integer.parseInt(st.nextToken());
		// 구간의 합을 구하는 횟수
		final int K = Integer.parseInt(st.nextToken());
		// 쿼리 수
		final int Q = M + K;
		
		// 수열 입력
		long[] sequence = new long[N];
		for(int i = 0; i < N; i++) {
			sequence[i] = Long.parseLong(br.readLine().trim());
		}
		
		// 세그먼트 트리 초기화
		SegmentTree segmentTree = new SegmentTree(N, sequence);
		int a, b, c;
		long d;
		for(int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			
			if(a == 1) {
				// 값 갱신
				d = Long.parseLong(st.nextToken());
				segmentTree.updateRange(0, N - 1, 1, b - 1, c - 1, d);
			} else if(a == 2) {
				// 구간 합 계산
				bw.write(segmentTree.getSum(0, N - 1, 1, b - 1, c - 1) + "\n");
			}
		}
		
		bw.close();
		br.close();
	}
	
	public static class SegmentTree {
		// 수의 개수
		int N;
		// 구간 합
		long[] sum;
		// 각 노드에 도착했을 때 갱신해야 하는 값 
		long[] lazy;
		
		public SegmentTree(int N, long[] sequence) {
			this.N = N;
			this.sum = new long[N * 4];
			this.lazy = new long[N * 4];
			
			init(0, N - 1, 1, sequence);
		}
		
		// 초기 수열 입력
		public long init(int start, int end, int idx, long[] sequence) {
			if(start == end) {
				sum[idx] = sequence[start];
				return sum[idx];
			}
			
			int mid = start + (end - start) / 2;
			sum[idx] = init(start, mid, idx * 2, sequence) + init(mid + 1, end, idx * 2 + 1, sequence);
			return sum[idx];
		}
		
		// 현재 노드를 갱신하지 않은 경우 갱신
		public void updateLazy(int start, int end, int idx) {
			if(lazy[idx] != 0) {
				sum[idx] += (end - start + 1) * lazy[idx];
				// 자식 노드 lazy 갱신
				if(start < end) {
					lazy[idx * 2] += lazy[idx];
					lazy[idx * 2 + 1] += lazy[idx];
				}
				
				lazy[idx] = 0;
			}
		}
		
		public long updateRange(int start, int end, int idx, int rangeStart, int rangeEnd, long val) {
			// 아직 갱신하지 않은 노드인 경우 값 갱신
			updateLazy(start, end, idx);
			// 범위에서 벗어나는 경우 그대로 리턴
			if(rangeEnd < start || end < rangeStart) return sum[idx];
			
			// 범위에 포함되는 경우 자식 노드는 lazy하게 갱신
			if(rangeStart <= start && end <= rangeEnd) {
				sum[idx] += (end - start + 1) * val;
				// 자식 노드 lazy 갱신
				if(start < end) {
					lazy[idx * 2] += val;
					lazy[idx * 2 + 1] += val;
				}
				
				return sum[idx];
			}
			
			// 범위가 겹치는 경우 양쪽 재귀적으로 탐색
			int mid = start + (end - start) / 2;
			sum[idx] = updateRange(start, mid, idx * 2, rangeStart, rangeEnd, val) + updateRange(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd, val);
			
			return sum[idx];
		}
		
		public long getSum(int start, int end, int idx, int rangeStart, int rangeEnd) {
			// 아직 갱신하지 않은 노드인 경우 값 갱신
			updateLazy(start, end, idx);
			// 범위에서 벗어나는 경우 0 리턴
			if(rangeEnd < start || end < rangeStart) return 0L;
			// 범위에 포함되는 경우 그대로 리턴
			if(rangeStart <= start && end <= rangeEnd) return sum[idx];
			
			// 범위가 겹치는 경우 양쪽 재귀적으로 탐색
			int mid = start + (end - start) / 2;
			return getSum(start, mid, idx * 2, rangeStart, rangeEnd) + getSum(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd); 
		}
		
	}
}
