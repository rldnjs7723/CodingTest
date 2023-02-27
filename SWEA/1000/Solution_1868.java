import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SWEA 1868번 파핑파핑 지뢰찾기
 * 문제 분류: 완전 탐색, BFS
 * @author Giwon
 */
public class Solution_1868 {
	public static final int NW = 0, N = 1, NE = 2, E = 3, SE = 4, S = 5, SW = 6, W = 7;
	public static final int MINE = -1;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		int T = Integer.parseInt(br.readLine().trim());
		int N;
		int[][] state;
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine().trim());
			state = new int[N][N];
			
			input = br.readLine().trim();
			
		}
//		int[][] state = new int[][];
		
		
		br.close();
	}
	
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
}
