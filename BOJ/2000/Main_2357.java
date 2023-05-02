import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 2357번 최솟값과 최댓값
 * 문제 분류: 세그먼트 트리
 * @author Giwon
 */
public class Main_2357 {
	public static final int MIN = 0, MAX = 1;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 정수 개수
		final int N = Integer.parseInt(st.nextToken());
		// 쿼리 개수
		final int M = Integer.parseInt(st.nextToken());
		
		// 세그먼트 트리 구성
		int[] sequence = new int[N];
		for(int i = 0; i < N; i++) {
			sequence[i] = Integer.parseInt(br.readLine().trim());
		}
		SegmentTree segmentTree = new SegmentTree(N, sequence);
		
		// 최대, 최소 출력
		int[] result;
		int a, b;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken()) - 1;
			b = Integer.parseInt(st.nextToken()) - 1;
			
			result = segmentTree.getValue(0, N - 1, 1, a, b);
			bw.write(result[MIN] + " " + result[MAX] + "\n");
		}
		
		bw.close();
		br.close();
	}
	
	public static class SegmentTree {
		int N;
		int[] minValue;
		int[] maxValue;
		
		public SegmentTree(int N, int[] sequence) {
			this.N = N;
			this.minValue = new int[4 * N];
			this.maxValue = new int[4 * N];
			
			init(0, N - 1, 1, sequence);
		}
		
		public void init(int start, int end, int idx, int[] sequence) {
			// 리프 노드
			if(start == end) {
				minValue[idx] = sequence[start];
				maxValue[idx] = sequence[start];
				
				return;
			}
			
			int mid = start + (end - start) / 2;
			init(start, mid, idx * 2, sequence);
			init(mid + 1, end, idx * 2 + 1, sequence);
			// 최대, 최소 갱신
			minValue[idx] = Math.min(minValue[idx * 2], minValue[idx * 2 + 1]);
			maxValue[idx] = Math.max(maxValue[idx * 2], maxValue[idx * 2 + 1]);
		}
		
		public int[] getValue(int start, int end, int idx, int rangeStart, int rangeEnd) {
			// 범위에서 벗어난 경우
			if(rangeEnd < start || end < rangeStart) {
				return new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE};
			}
			
			// 범위에서 포함되는 경우 그대로 리턴
			if(rangeStart <= start && end <= rangeEnd) {
				return new int[] {minValue[idx], maxValue[idx]};
			}
			
			int mid = start + (end - start) / 2;
			int[] left = getValue(start, mid, idx * 2, rangeStart, rangeEnd);
			int[] right = getValue(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd);
			return new int[] {Math.min(left[MIN], right[MIN]), Math.max(left[MAX], right[MAX])};
		}
	}
}
