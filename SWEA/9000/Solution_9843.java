import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * SWEA 9843번 촛불 이벤트
 * 문제 분류: 이분 탐색 (파라미터 탐색)
 * @author Giwon
 */
public class Solution_9843 {
	public static final long MAX = 1414213563L;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		final int T = Integer.parseInt(br.readLine().trim());
		long N, result;
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Long.parseLong(br.readLine().trim());
			
			result = binarySearch(1, MAX, N);
			bw.write("#" + test_case + " ");
			if(result * (result + 1) / 2 == N) {
				bw.write(result + "\n");
			} else {
				bw.write(-1 + "\n");
			}
		}
		
		bw.close();
		br.close();
	}

	// k(k+1)/2를 만족하는 수 이분 탐색
	public static long binarySearch(long start, long end, long target) {
		if(start == end) return start;
		
		long mid = start + (end - start) / 2;
		long val = mid * (mid + 1) / 2;
		if(val < target) {
			return binarySearch(mid + 1, end, target);
		} else {
			return binarySearch(start, mid, target);
		}
		
	}
}
