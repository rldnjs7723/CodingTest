import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 1395번 스위치
 * 문제 분류: 느리게 갱신되는 세그먼트 트리
 * @author Giwon
 */
public class Main_1395 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 스위치 개수
		final int N = Integer.parseInt(st.nextToken());
		// 일 개수
		final int M = Integer.parseInt(st.nextToken());
		
		// 세그먼트 트리 초기화
		SegmentTree segmentTree = new SegmentTree(N);
		
		// 일 입력
		int O, S, T;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			O = Integer.parseInt(st.nextToken());
			// Index 1부터 시작하도록 설정
			S = Integer.parseInt(st.nextToken()) - 1;
			T = Integer.parseInt(st.nextToken()) - 1;
			
			if(O == 0) {
				// 스위치 반전
				segmentTree.update(0, N - 1, 1, S, T);
			} else if(O == 1) {
				// 켜진 스위치 개수 카운트
				bw.write(segmentTree.getCount(0, N - 1, 1, S, T) + "\n");
			}
		}
		
		bw.close();
		br.close();
	}

	public static class SegmentTree {
		// 켜져 있는 스위치 개수
		int[] count;
		// 현재 구간 스위치 갱신 여부
		boolean[] lazy;
		
		public SegmentTree(int N) {
			// 초기 모든 스위치는 꺼져있는 상태
			this.count = new int[4 * N];
			this.lazy = new boolean[4 * N];			
		}
		
		private void updateLazy(int start, int end, int idx) {
			if(lazy[idx]) {
				int totalCount = end - start + 1;
				count[idx] = totalCount - count[idx];
				
				// 자식 노드 Lazy 갱신
				if(start != end) {
					lazy[idx * 2] = !lazy[idx * 2];
					lazy[idx * 2 + 1] = !lazy[idx * 2 + 1];
				}
				
				lazy[idx] = false;
			}
		}
		
		// 스위치 상태 반전
		public int update(int start, int end, int idx, int rangeStart, int rangeEnd) {
			// 느리게 갱신
			updateLazy(start, end, idx);
			
			// 범위가 겹치지 않는 경우 그대로 리턴
			if(rangeEnd < start || end < rangeStart) return count[idx];
			
			// 범위에 포함되는 경우 스위치 반전
			if(rangeStart <= start && end <= rangeEnd) {
				int totalCount = end - start + 1;
				count[idx] = totalCount - count[idx];
				
				// 자식 노드 Lazy 갱신
				if(start != end) {
					lazy[idx * 2] = !lazy[idx * 2];
					lazy[idx * 2 + 1] = !lazy[idx * 2 + 1];
				}
				
				return count[idx];
			}
			
			// 범위가 겹치는 경우
			int mid = start + (end - start) / 2;
			int left = update(start, mid, idx * 2, rangeStart, rangeEnd);
			int right = update(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd);
			count[idx] = left + right;
			
			return count[idx];
		}
		
		// 켜진 스위치 개수 카운트
		public int getCount(int start, int end, int idx, int rangeStart, int rangeEnd) {
			// 느리게 갱신
			updateLazy(start, end, idx);
			
			// 범위가 겹치지 않는 경우 0 리턴
			if(rangeEnd < start || end < rangeStart) return 0;
			// 범위에 포함되는 경우 그대로 리턴
			if(rangeStart <= start && end <= rangeEnd) return count[idx];
			
			// 범위가 겹치는 경우
			int mid = start + (end - start) / 2;
			int left = getCount(start, mid, idx * 2, rangeStart, rangeEnd);
			int right = getCount(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd);
			
			return left + right;
		}
	}
}
