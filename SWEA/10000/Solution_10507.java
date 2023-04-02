

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 10507번 영어 공부
 * 문제 분류: 이분 탐색 (파라미터 탐색)
 * @author Giwon
 */
public class Solution_10507 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int n, p;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 영어 공부 한 날의 수
			n = Integer.parseInt(st.nextToken());
			// 추가로 체크할 수 있는 날의 수
			p = Integer.parseInt(st.nextToken());
			
			// 영어 공부 한 날 입력
			st = new StringTokenizer(br.readLine());
			int[] sequence = new int[n];
			for(int i = 0; i < n; i++) {
				sequence[i] = Integer.parseInt(st.nextToken());
			}
			
			// 각 Index별 연결할 수 있는 가장 먼 Index 탐색
			int max = 0;
			int start, end;
			for(int i = 0; i < n; i++) {
				start = i;
				end = parametricSearch(i, start, n - 1, p, sequence);
				max = Math.max(max, countLength(start, end, p, sequence));
			}
			
			System.out.println("#" + test_case + " " + max);
		}
		
		br.close();
	}
	
	// 두 Index를 연결했을 때의 최대 길이 계산
	public static int countLength(int start, int end, int p, int[] sequence) {
		int remainP = p - countP(start, end, sequence);
		return sequence[end] - sequence[start] + 1 + remainP;
	}
	
	// 두 Index를 연결하기 위해 사용해야 하는 p의 개수 계산
	public static int countP(int start, int end, int[] sequence) {
		// 두 수 사이에 위치한 날짜의 개수 - 두 수 사이에 위치한 영어 공부한 날짜의 개수
		return sequence[end] - sequence[start] - 1 - (end - start - 1);
	}
	
	// 현재 Index의 수와 연결할 수 있는 가장 먼 Index 탐색
	public static int parametricSearch(int idx, int start, int end, int p, int[] sequence) {
		if(start == end) {
			return start;
		}
		
		int mid = start + (end - start) / 2 + 1;
		int count = countP(idx, mid, sequence);
		// 현재 사용한 p의 개수가 전체 p의 수보다 작거나 같으면 그보다 더 큰 수 탐색
		if(count <= p) return parametricSearch(idx, mid, end, p, sequence);
		else return parametricSearch(idx, start, mid - 1, p, sequence);
	}
}
