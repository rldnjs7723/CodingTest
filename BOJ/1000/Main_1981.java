import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1981번 배열에서 이동
 * 문제 분류: 다이나믹 프로그래밍, BFS
 * @author Giwon
 */
public class Main_1981 {
	public static final int INF = Integer.MAX_VALUE;
	public static final int MAX = 200;
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 배열 크기
		final int N = Integer.parseInt(br.readLine().trim());
		// 배열 입력
		int[][] arr = new int[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				arr[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// R행 C열에서 최솟값이 M일 때의 최댓값 = dp[R][C][M]
		int[][][] dp = new int[N][N][MAX + 1];
		for(int[][] innerRow: dp) {
			for(int[] innerCol: innerRow) {
				Arrays.fill(innerCol, INF);
			}
		}
		
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		// 시작점 설정
		Coord curr = new Coord(0, 0, arr[0][0], arr[0][0]);
		dp[0][0][arr[0][0]] = arr[0][0];
		bfsQueue.offer(curr);
		// BFS로 (n, n)까지 이동하는 경로 탐색
		int R, C, nextVal, nextMin, nextMax;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			// 자신이 갱신했던 값과 다르면 생략
			if(curr.max != dp[curr.row][curr.col][curr.min]) continue;
			
			for(int dir = 0; dir < 4; dir++) {
				R = curr.row + dRow[dir];
				C = curr.col + dCol[dir];
				
				// 범위를 벗어난 경우 생략
				if(!checkRange(R, N) || !checkRange(C, N)) continue;
				
				nextVal = arr[R][C];
				nextMin = Math.min(curr.min, nextVal);
				nextMax = Math.max(dp[curr.row][curr.col][curr.min], nextVal);
				
				// 최대 - 최소 값을 줄이는 경우 이동
				if(nextMax < dp[R][C][nextMin]) {
					dp[R][C][nextMin] = nextMax;
					bfsQueue.offer(new Coord(R, C, nextMin, nextMax));
				}
			}
		}
		
		// (n, n)까지 이동하는 최대 - 최소 값 계산
		int min = INF;
		for(int i = 0; i <= MAX; i++) {
			min = Math.min(min, dp[N - 1][N - 1][i] - i);
		}
		
		System.out.println(min);
		br.close();
	}
	
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}
	
	public static class Coord {
		int row, col;
		int min, max;
		
		public Coord(int row, int col, int min, int max) {
			this.row = row;
			this.col = col;
			this.min = min;
			this.max = max;
		}

		@Override
		public String toString() {
			return "Coord [row=" + row + ", col=" + col + ", min=" + min + "]";
		}
	}

}
