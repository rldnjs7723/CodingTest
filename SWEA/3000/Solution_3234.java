import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * SWEA 3234번 준환이의 양팔저울
 * 문제 분류: 비트마스킹, DFS
 * @author Giwon
 */
class Solution_3234
{
	public static int LEFT = 1, RIGHT = 2;
	public static int[] factorial = new int[10];
	public static int[] caseResult = new int[10];

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		// 팩토리얼 연산 미리 수행
		factorial[0] = 1;
		for(int i = 1; i < 10; i++) {
			factorial[i] = factorial[i - 1] * i;
		}
		
		// 탐색할 필요가 없는 부분의 수열 가짓수 계산 미리 수행
		for(int i = 0; i < 10; i++) {
			caseResult[i] = calCase(i);
		}
		
		// 무게추
		int[] weights;
		// 무게추 개수 / 무게추들의 총합
		int N, MAX;
		StringTokenizer st;
		for(int test_case = 1; test_case <= T; test_case++)
		{
			// 무게추 개수 입력
			N = Integer.parseInt(br.readLine());
			weights = new int[N];
			MAX = 0;
			
			// 무게추 입력
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i++) {
				weights[i] = Integer.parseInt(st.nextToken());
				MAX += weights[i];
			}
			
			// 0번부터 N - 1번 무게추까지 하나씩 올리면서 DFS 탐색
			int result = 0;
			for(int i = 0; i < N; i++) {
				result += recurCheck(weights, new Order(weights[i], N, i), MAX, N); 
			}
			
			System.out.println("#" + test_case + " " + result);
		}
		
		br.close();
	}
	
	// 더 이상 탐색할 필요가 없는 부분의 수열은 팩토리얼 계산으로 대체
	public static int calCase(int N) {
		return factorial[N] * (int) Math.pow(2, N);
	}
	
	// 재귀로 DFS 탐색 수행
	public static int recurCheck(int[] weights, Order curr, int MAX, int N) {
		// 오른쪽에 올린 무게추의 총합이 왼쪽보다 크면 안 됨
		if(curr.leftSum < curr.rightSum) return 0;
		// 현재 왼쪽에 올린 무게추의 총합이 과반수 이상이면 나머지는 탐색할 필요 없이 수식으로 계산
		if(curr.leftSum * 2 >= MAX) return caseResult[N - curr.count];
		// N개의 무게추를 전부 사용했으면 1개의 경우 완료
		if(curr.count == N) return 1;
		
		int result = 0;
		for(int i = 0; i < N; i++) {
			// 비트마스크로 현재 무게추를 선택했는지 확인
			if(!curr.isContain(i)) {
				// 무게추를 왼쪽에 올리는 경우
				result += recurCheck(weights, new Order(curr, weights[i], i, RIGHT), MAX, N);
				// 무게추를 오른쪽에 올리는 경우
				result += recurCheck(weights, new Order(curr, weights[i], i, LEFT), MAX, N);
			}
		}
		
		return result;
	}
	
	// 무게 추를 올리는 순서에 따른 결과를 저장할 클래스
	public static class Order {
		int count, N;
		int leftSum, rightSum;
		int flag;
		
		Order(int val, int N, int idx) {
			count = 1;
			this.N = N;
			flag = 1 << idx;
			leftSum = val;
			rightSum = 0;
		}
		
		// 이전 순서를 통해 새로운 무게 추를 추가하는 생성자
		Order(Order obj, int val, int idx, int loc) {
			count = obj.count + 1;
			N = obj.N;
			flag = (1 << idx) | obj.flag;
			if(loc == LEFT) {
				leftSum = obj.leftSum + val;
				rightSum = obj.rightSum;
			} else {
				leftSum = obj.leftSum;
				rightSum = obj.rightSum + val;
			}
		}
		
		// 비트마스킹으로 특정 무게 추를 사용했는지 체크
		boolean isContain(int idx) {
			return ((flag >> idx) & 1) == 1 ? true : false;
		}
	}
}