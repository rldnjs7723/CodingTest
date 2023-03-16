import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 14428번 수열과 쿼리 16
 * 문제 분류: 세그먼트 트리
 * @author Giwon
 */
public class Main_14428 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 수열 길이
		final int N = Integer.parseInt(br.readLine().trim());
		// 수열 입력
		int[] sequence = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			sequence[i] = Integer.parseInt(st.nextToken());
		}
		
		// 세그먼트 트리 구성
		SegmentTree segTree = new SegmentTree(sequence);
		
		// 쿼리 개수
		final int M = Integer.parseInt(br.readLine().trim());
		// 쿼리 입력
		StringBuilder sb = new StringBuilder();
		int query, a, b;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 쿼리 타입
			query = Integer.parseInt(st.nextToken());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			
			if(query == 1) {
				// 값 바꾸기
				segTree.updateSequence(a, b);
			} else if(query == 2) {
				// 가장 작은 값의 인덱스 출력
				sb.append(segTree.getMinIndex(a, b) + "\n");
			}
		}
		
		System.out.println(sb.toString());
		br.close();
	}

	
	public static class SegmentTree {
		public static final int NULL = -1;
		
		int N;
		int[] sequence;
		int[] minValue;
		
		public SegmentTree(int[] sequence) {
			this.N = sequence.length;
			this.sequence = sequence;
			
			// SegmentTree 크기
			int SIZE = N * 4;
			minValue = new int[SIZE];
			
			// 세그먼트 트리 구성
			init(0, N - 1, 1);
		}
		
		/**
		 * 세그먼트 트리 구성
		 * @param start			범위 시작
		 * @param end			범위 끝
		 * @param idx			SegmentTree 내부의 Index
		 * @return
		 */
		public int init(int start, int end, int idx) {
			// 리프 노드라면 현재 수가 최솟값
			if(start == end) {
				minValue[idx] = start;
				return minValue[idx];
			}
			
			// 범위를 절반씩 줄여가며 범위 내의 수열 중 가장 작은 값의 Index 기록
			int mid = (start + end) / 2;
			// 왼쪽 자식
			int left = init(start, mid, idx * 2);
			// 오른쪽 자식
			int right = init(mid + 1, end, idx * 2 + 1);
			
			// 더 작은 값의 인덱스를 리턴
			if(sequence[left] > sequence[right]) minValue[idx] = right;
			else if(sequence[left] < sequence[right]) minValue[idx] = left; 
			// 양쪽의 최솟값이 같다면 인덱스가 작은 것을 리턴
			else minValue[idx] = Math.min(left, right);
			
			return minValue[idx];
		}
		
		// 수열 내의 숫자 변경
		public void updateSequence(int changeIdx, int changeVal) {
			// Index 0부터 시작하도록 설정
			update(0, N - 1,1, changeIdx - 1, changeVal);
		}
		
		/**
		 * 수열 내의 숫자 변경
		 * @param start				범위 시작
		 * @param end				범위 끝
		 * @param idx				SegmentTree 내부의 Index
		 * @param changeIdx		변경할 숫자의 Index
		 * @param changeVal		변경할 숫자
		 */
		private int update(int start, int end, int idx, int changeIdx, int changeVal) {
			if(start == end && start == changeIdx) {
				
				// 같은 Index라면 숫자 변경
				sequence[changeIdx] = changeVal;
				// 리프 노드의 최솟값은 변하지 않음
				return minValue[idx];
			}
			
			// 범위에서 벗어났다면 최솟값 변하지 않음
			if(changeIdx < start || end < changeIdx) return minValue[idx];
			
			// 범위가 겹치는 경우 최솟값 갱신
			int mid = (start + end) / 2;
			// 왼쪽 자식
			int left = update(start, mid, idx * 2, changeIdx, changeVal);
			// 오른쪽 자식
			int right = update(mid + 1, end, idx * 2 + 1, changeIdx, changeVal);
			
			// 더 작은 값의 인덱스를 리턴
			if(sequence[left] > sequence[right]) minValue[idx] = right;
			else if(sequence[left] < sequence[right]) minValue[idx] = left; 
			// 양쪽의 최솟값이 같다면 인덱스가 작은 것을 리턴
			else minValue[idx] = Math.min(left, right);
			
			return minValue[idx];
		}
		
		// 주어진 범위 내에서 수열의 최솟값 Index 리턴
		public int getMinIndex(int rangeStart, int rangeEnd) {
			// Index 0부터 시작하도록 설정
			return getMinIndex(0, N - 1, 1, rangeStart - 1, rangeEnd - 1) + 1;
		}
		
		/**
		 * 주어진 범위 내에서 수열의 최솟값 Index 리턴
		 * @param start					현재 SegmentTree 노드의 범위 시작
		 * @param end					현재 SegmentTree 노드의 범위 끝
		 * @param idx					현재 SegmentTree 노드의 Index
		 * @param rangeStart			최솟값 찾을 범위 시작
		 * @param rangeEnd			최솟값 찾을 범위 끝
		 * @return							범위 내 수열의 최솟값 Index
		 */
		private int getMinIndex(int start, int end, int idx, int rangeStart, int rangeEnd) {
			// 범위에서 벗어났다면 -1 리턴
			if(rangeEnd < start || end < rangeStart) return NULL;
			// 범위가 포함되면 현재 최소 Index 리턴
			if(rangeStart <= start && end <= rangeEnd) return minValue[idx];
			
			// 범위가 겹친다면 왼쪽, 오른쪽 자식에서 최솟값 Index 받아오기
			int mid = (start + end) / 2;
			// 왼쪽 자식
			int left = getMinIndex(start, mid, idx * 2, rangeStart, rangeEnd);
			// 오른쪽 자식
			int right = getMinIndex(mid + 1, end, idx * 2 + 1, rangeStart, rangeEnd);
			
			// 왼쪽 자식이 범위에서 벗어났다면 오른쪽 자식 Index가 최소
			if(left == NULL) return right;
			// 오른쪽 자식이 범위에서 벗어났다면 왼쪽 자식 Index가 최소
			if(right == NULL) return left;
			
			// 더 작은 값의 인덱스를 리턴
			if(sequence[left] > sequence[right]) return right;
			else if(sequence[left] < sequence[right]) return left; 
			// 양쪽의 최솟값이 같다면 인덱스가 작은 것을 리턴
			else return Math.min(left, right);
		}
		
		// SegmentTree 출력
		public void printArr() {
			System.out.println("현재 수열: " + Arrays.toString(sequence));
			System.out.println(Arrays.toString(minValue));
		}
	}
}
