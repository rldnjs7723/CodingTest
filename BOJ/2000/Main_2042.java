import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
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
		
		// 숫자 입력
		long[] num = new long[N];
		for(int i = 0; i < N; i++) {
			num[i] = Long.parseLong(br.readLine());
		}
		
		// 세그먼트 트리 구성
		SegmentTree segTree = new SegmentTree(num);
		segTree.printArr();
		
		br.close();
	}

	// 세그먼트 트리를 저장할 배열 크기 리턴
	public static int getSize(int N) {
		int size = 1;
		while(N >= size) {
			size *= 2;
		}
		
		return size * 2;
	}
	
	public static class SegmentTree {
		int N;
		long[] num;
		BigInteger[] subSum;
		
		public SegmentTree(long[] num) {
			this.N = num.length;
			this.num = num;
			// 세그먼트 트리를 저장할 배열 크기
			int SIZE = getSize(N);
			subSum = new BigInteger[SIZE + 1];
			
			// 세그먼트 트리 구성
			subSum[1] = init(0, N - 1, 1);
		}
		
		// 범위를 절반씩 줄이면서 구간 합 구하기
		public BigInteger init(int start, int end, int idx) {
			// 리프 노드인 경우 현재 값 추가
			if(start == end) {
				subSum[idx] = new BigInteger(Long.toString(num[start]));
				return subSum[idx];
			}
			
			int mid = (start + end) / 2;
			// 왼쪽 자식 노드
			BigInteger left = init(start, mid, idx * 2);
			// 오른쪽 자식 노드
			BigInteger right = init(mid + 1, end, idx * 2 + 1);
			
			// 양쪽 자식 노드 더하고 리턴
			subSum[idx] = left.add(right);
			return subSum[idx];
		}
		
		// 부분합 출력
		public void printArr() {
			System.out.println(Arrays.toString(subSum));
		}
	}
	
}
