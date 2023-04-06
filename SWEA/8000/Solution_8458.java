import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 8458번 원점으로 집합
 * 문제 분류: 이분 탐색
 * @author Giwon
 */
public class Solution_8458 {
	public static final long EVEN = 0, ODD = 1;
	public static final long MAX = (long) 2e9;
	public static final int UPPER_BOUND = 63247;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, x, y;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 좌표 개수
			N = Integer.parseInt(br.readLine().trim());
			
			long[] distance = new long[N];
			long max = 0;
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				x = Integer.parseInt(st.nextToken());
				y = Integer.parseInt(st.nextToken());
				
				distance[i] = Math.abs(x) + Math.abs(y);
				max = Math.max(max, distance[i]);
			}
			
			// 가장 멀리 위치한 점이 이동해야 하는 횟수 이분 탐색으로 계산
			long maxMove = binarySearch(0, UPPER_BOUND, max);
			// 이동해야 하는 횟수만큼 이동하면 이동하는 거리 계산
			long moved = maxMove * (maxMove + 1) / 2;
			boolean done = true;
			for(int i = 0; i < N; i++) {
				// 가장 큰 값이 이동해야 하는 거리만큼 이동
				distance[i] -= moved;
				// 가장 큰 값이 정확히 나누어 떨어지는지 확인
				if(distance[i] > 0) done = false;
				// 나머지 연산을 위해 양수로 변경
				distance[i] = Math.abs(distance[i]);
				// 남은 거리의 홀, 짝 판단
				distance[i] %= 2;
			}
			
			// 남은 거리가 전부 홀수인지, 짝수인지 체크
			long type = distance[0];
			boolean diff = false;
			for(int i = 0; i < N; i++) {
				if(type != distance[i]) {
					// 남은 거리의 홀, 짝이 다른 경우가 있다면 모든 점을 원점으로 모으는 것이 불가능
					diff = true;
					break;
				}
			}
			
			// 모든 점을 원점으로 이동할 수 없는 경우
			if(diff) {
				System.out.println("#" + test_case + " " + -1);
				continue;
			}
			
			// 가장 큰 값이 정확이 나누어 떨어졌다면 현재 이동한 횟수가 정답
			if(done) {
				System.out.println("#" + test_case + " " + maxMove);
				continue;
			}
			
			long nextMoveType = (maxMove + 1) % 2;
			if(nextMoveType == type) {
				// 짝수-짝수 또는 홀수-홀수인 경우
				System.out.println("#" + test_case + " " + (maxMove + 1));
				continue;
			} else if(nextMoveType == ODD && type == EVEN) {
				// 짝수번 이동해야 하는데 다음 이동은 홀수번인 경우
				System.out.println("#" + test_case + " " + (maxMove + 3));
				continue;
			} else {
				// 홀수번 이동해야 하는데 다음 이동은 짝수번인 경우
				System.out.println("#" + test_case + " " + (maxMove + 2));
				continue;
			}
		}
		
		br.close();
	}

	// 이분 탐색을 통해 현재 좌표를 원점으로 이동하기 위해 필요한 움직임 최솟값 계산
	public static long binarySearch(long start, long end, long distance) {
		if(start == end) return start;
		
		long mid = start + (end - start) / 2 + 1;
		long movedDistance = mid * (mid + 1) / 2;
		if(movedDistance > distance) return binarySearch(start, mid - 1, distance);
		else return binarySearch(mid, end, distance);
	}
}
