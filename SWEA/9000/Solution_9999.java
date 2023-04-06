
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 9999번 광고 시간 정하기
 * 문제 분류: 이분 탐색
 * @author Giwon
 */
public class Solution_9999 {
	public static final int MAX = (int) 1e8;
	
	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("res/2_input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, L;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 광고 시간
			L = Integer.parseInt(br.readLine().trim());
			// 피크 시간대 수
			N = Integer.parseInt(br.readLine().trim());
			
			// 피크 시간대 입력
			int timeSum = 0;
			PeekTime[] time = new PeekTime[N];
			int s, e;
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				s = Integer.parseInt(st.nextToken());
				e = Integer.parseInt(st.nextToken());
				
				time[i] = new PeekTime(s, e, timeSum);
				timeSum += e - s;
			}
			
			// 각 피크 시간대의 시작점을 기준으로 광고 종료 시간 사이에 존재하는 가장 큰 종료 피크 시간대를 탐색
			int target, lastIdx, adTime;
			PeekTime start, end;
			int max = 0;
			for(int i = 0; i < N; i++) {
				start = time[i];
				target = start.s + L;
				
				lastIdx = binarySearch(i, N - 1, target, time);
				end = time[lastIdx];
				
				// 종료 시간대까지의 광고 시간 총합 + 종료 시간대에서의 광고 시간
				adTime = end.timeSum - start.timeSum + end.e - end.s;
				// 종료 시간대 다음 시간대와 시작 시간이 겹치는 경우 해당 시간도 추가
				if(lastIdx + 1 < N && time[lastIdx + 1].s < target) adTime += target - time[lastIdx + 1].s;
				
				// 광고 시간 최댓값 갱신
				max = Math.max(max, adTime);
			}
			
			System.out.println("#" + test_case + " " + max);
		}
		
		br.close();
	}
	
	// 종료 시간이 목표 시간보다 작은 가장 뒤에 위치한 시간대를 탐색
	public static int binarySearch(int start, int end, int target, PeekTime[] time) {
		if(start == end) return start;
		
		int mid = start + (end - start) / 2 + 1;
		if(time[mid].e <= target) {
			// 목표 시간보다 종료 시간이 작은 경우
			return binarySearch(mid, end, target, time);
		} else {
			// 목표 시간보다 종료 시간이 큰 경우
			return binarySearch(start, mid - 1, target, time);
		}
		
	}

	public static class PeekTime {
		int s, e;
		// 현재 시간대 이전까지의 시간 총 합
		int timeSum;
		
		public PeekTime(int s, int e, int timeSum) {
			this.s = s;
			this.e = e;
			this.timeSum = timeSum;
		}

		@Override
		public String toString() {
			return "PeekTime [s=" + s + ", e=" + e + ", timeSum=" + timeSum + "]";
		}
		
	}
}
