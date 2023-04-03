import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 11505번 구간 곱 구하기
 * 문제 분류: 세그먼트 트리
 * @author Giwon
 */
public class Main_11505 {
	public static final int DIV = 1000000007;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 수의 개수
		final int N = Integer.parseInt(st.nextToken());
		// 수 변경 횟수
		final int M = Integer.parseInt(st.nextToken());
		// 구간의 곱을 구하는 횟수
		final int K = Integer.parseInt(st.nextToken());
		
		// 수 입력
		int[] sequence = new int[N];
		for(int i = 0; i < N; i++) {
			sequence[i] = Integer.parseInt(br.readLine().trim());
		}
		// Segment Tree 구성
		SegmentTree segTree = new SegmentTree(N, sequence);
		
		// 전체 쿼리 수
		final int totalQuery = M + K;
		int a, b, c;
		for(int i = 0; i < totalQuery; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			
			if(a == 1) {
				// 수 변경
				segTree.update(0, N - 1, 1, c, b - 1);
			} else if(a == 2) {
				// 구간 곱 계산
				bw.write(segTree.getMul(0, N - 1, 1, b - 1, c - 1) + "\n");
			}
		}
		
		bw.close();
		br.close();
	}

	public static class SegmentTree {
		int N;
		int[] sequence;
		long[] prefixMul;
		
		public SegmentTree(int N, int[] sequence) {
			this.N = N;
			this.sequence = sequence;
			// Segment Tree의 크기 설정
			this.prefixMul = new long[N * 4];
			
			// idx는 1부터 시작하도록 설정
			init(0, N - 1, 1);
		}
		
		// 세그먼트 트리 구성
		private long init(int start, int end, int idx) {
			// 리프 노드 설정
			if(start == end) {
				prefixMul[idx] = sequence[start];
				return prefixMul[idx];
			}
			
			// 구간 곱 계산
			int mid = start + (end - start) / 2;
			long left = init(start, mid, idx * 2);
			long right = init(mid + 1, end, idx * 2 + 1);
			
			prefixMul[idx] = (left * right) % DIV;
			return prefixMul[idx];
		}
		
		// 세그먼트 트리 업데이트
		public long update(int start, int end, int idx, int val, int changeIdx) {
			// 범위에서 벗어난 경우 아무런 작업 수행하지 않음
			if(changeIdx < start || end < changeIdx) return prefixMul[idx];
			
			// 리프 노드 설정 (수정할 위치)
			if(start == end && start == changeIdx) {
				sequence[start] = val;
				prefixMul[idx] = sequence[start];
				return prefixMul[idx];
			}
			
			// 범위가 겹치는 경우 분리
			int mid = start + (end - start) / 2;
			long left = update(start, mid, idx * 2, val, changeIdx);
			long right = update(mid + 1, end, idx * 2 + 1, val, changeIdx);
			
			// 구간 곱 갱신
			prefixMul[idx] = (left * right) % DIV;
			return prefixMul[idx];
		}
		
		// 구간 곱 계산
		public long getMul(int start, int end, int idx, int rangeStart, int rangeEnd) {
			// 범위에서 벗어난 경우 1 리턴
			if(rangeEnd < start || end < rangeStart) return 1;
			
			// 범위에 포함되는 경우 그대로 리턴
			if(rangeStart <= start && end <= rangeEnd) return prefixMul[idx];
			
			// 범위가 겹치는 경우 분리
			int mid = start + (end - start) / 2;
			long left = getMul(start, mid, idx * 2, rangeStart, rangeEnd);
			long right = getMul(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd);
			
			// 구간 곱 계산
			return (left * right) % DIV;
		}
		
	}
}
