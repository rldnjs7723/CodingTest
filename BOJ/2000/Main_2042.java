import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 2042번 구간 합 구하기
 * 문제 분류: 세그먼트 트리
 * @author Giwon
 */
public class Main_2042 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 수의 개수
		final int N = Integer.parseInt(st.nextToken());
		// 수의 변경이 일어나는 횟수
		final int M = Integer.parseInt(st.nextToken());
		// 구간의 합을 구하는 횟수
		final int K = Integer.parseInt(st.nextToken());
		// 연산 수
		final int OP = M + K;
		
		// 숫자 입력
		long[] num = new long[N];
		for(int i = 0; i < N; i++) {
			num[i] = Long.parseLong(br.readLine());
		}
		
		// 세그먼트 트리 구성
		SegmentTree segTree = new SegmentTree(num);
		
		// 연산 입력
		StringBuilder sb = new StringBuilder();
		int a, b;
		for(int i = 0; i < OP; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			
			if(a == 1) {
				// 숫자 바꾸기
				b = Integer.parseInt(st.nextToken());
				long c = Long.parseLong(st.nextToken());
				
				segTree.updateNum(b, c);
			} else {
				// 구간합 출력하기
				b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				sb.append(segTree.getSum(b, c) + "\n");
			}
		}
		
		System.out.println(sb.toString());
		br.close();
	}

	public static class SegmentTree {
		int N;
		long[] num;
		long[] subSum;
		
		public SegmentTree(long[] num) {
			this.N = num.length;
			this.num = num;
			// 세그먼트 트리를 저장할 배열 크기
			int SIZE = N * 4;
			subSum = new long[SIZE];
			
			// 세그먼트 트리 구성
			subSum[1] = init(0, N - 1, 1);
		}
		
		// 범위를 절반씩 줄이면서 구간 합 구하기
		public long init(int start, int end, int idx) {
			// 리프 노드인 경우 현재 값 추가
			if(start == end) {
				subSum[idx] = num[start];
				return subSum[idx];
			}
			
			int mid = (start + end) / 2;
			// 왼쪽 자식 노드
			long left = init(start, mid, idx * 2);
			// 오른쪽 자식 노드
			long right = init(mid + 1, end, idx * 2 + 1);
			
			// 양쪽 자식 노드 더하고 리턴
			subSum[idx] = left + right;
			return subSum[idx];
		}
		
		// 구간 사이의 합 계산
		public long getSum(int sumStart, int sumEnd) {
			// 0번 Index부터 시작하도록 설정
			return calculateSum(0, N - 1, 1, sumStart - 1, sumEnd - 1);
		}
		
		/**
		 * 구간 사이의 합을 계산
		 * @param start			현재 구간합에 포함된 수의 시작점
		 * @param end			현재 구간합에 포함된 수의 끝점
		 * @param idx			현재 구간합 Index
		 * @param sumStart		계산할 구간합 시작점
		 * @param sumEnd		계산할 구간합 끝점
		 * @return				구간 합
		 */
		private long calculateSum(int start, int end, int idx, int sumStart, int sumEnd) {
			// 범위를 벗어난 경우 더할 필요 없음
			if(end < sumStart || sumEnd < start) return 0;
			// 범위 안에 완전히 포함된 경우 그대로 더하기
			if(sumStart <= start && end <= sumEnd) return subSum[idx];
			
			// 범위가 겹친 경우 범위를 절반으로 줄여서 더하기
			int mid = (start + end) / 2;
			// 왼쪽 자식 노드
			long left = calculateSum(start, mid, idx * 2, sumStart, sumEnd);
			// 오른쪽 자식 노드
			long right = calculateSum(mid + 1, end, idx * 2 + 1, sumStart, sumEnd);
			
//			System.out.println(left.add(right));
			return left + right;
		}
		
		// 특정 Index의 수를 바꾸기
		public void updateNum(int idx, long val) {
			// 0번 Index부터 시작하도록 설정
			idx--;
			// 차이 계산
			long diff = val - num[idx];
			// 수 갱신
			num[idx] = val;
			
			// 부분합 갱신
			updateSum(0, N - 1, 1, idx, diff);
		}
		
		/**
		 * 특정 Index의 수를 바꿨을 때의 부분합 갱신하기
		 * @param start			구간 시작
		 * @param end			구간 끝
		 * @param idx			구간합 Index
		 * @param changeIdx		바꾼 숫자 Index
		 * @param diff			숫자 차이
		 */
		private void updateSum(int start, int end, int idx, int changeIdx, long diff) {
			// 범위를 벗어나면 생략
			if(changeIdx < start || end < changeIdx) return;

			// 현재 구간합 갱신
			subSum[idx] += diff;
			// 리프 노드는 생략
			if(start == end) return;
			
			// 자식 노드도 갱신
			int mid = (start + end) / 2;
			// 왼쪽 자식 노드
			updateSum(start, mid, idx * 2, changeIdx, diff);
			// 오른쪽 자식 노드
			updateSum(mid + 1, end, idx * 2 + 1, changeIdx, diff);
		}
		
		// 부분합 출력
		public void printArr() {
			System.out.println(Arrays.toString(subSum));
		}
	}
	
}
