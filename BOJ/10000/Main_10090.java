import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 10090번 Counting Inversions
 * 문제 분류: 세그먼트 트리
 * @author Giwon
 */
public class Main_10090 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 수열 길이
		final int n = Integer.parseInt(br.readLine().trim());
		
		
		
		br.close();
	}

	public static class SegmentTree {
		int N;
		int[] sequence;
		long[] lineCount;
		
		public SegmentTree(int N, int[] sequence) {
			this.N = N;
			this.sequence = sequence;
			this.lineCount = new long[N * 4];
			
			
		}
		
//		public long init() {
//			
//		}
	}
	
}
