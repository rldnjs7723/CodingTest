import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 4485번 녹색 옷 입은 애가 젤다지?
 * 문제 분류: 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_4485 {
	public static final int INF = Integer.MAX_VALUE;
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N;
		int[][] cave, costDP;
		int test_case = 0;
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		// 동굴 크기
		while((N = Integer.parseInt(br.readLine().trim())) > 0) {
			// 테스트 케이스 구분
			test_case++;
			// 동굴 상태 입력
			cave = new int[N][N];
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < N; col++) {
					cave[row][col] = Integer.parseInt(st.nextToken());
				}
			}
			
			// DP로 각 위치에 도달하는 최소 비용 계산
			costDP = new int[N][N];
			for(int[] inner: costDP) {
				Arrays.fill(inner, INF);
			}
			// 시작점 비용
			costDP[0][0] = cave[0][0];
			// BFS로 한 칸씩 움직이며 최소 비용 갱신
			bfsQueue.clear();
			bfsQueue.offer(new Coord(0, 0));
			Coord curr;
			int R, C;
			while(!bfsQueue.isEmpty()) {
				curr = bfsQueue.poll();
				
				for(int dir = 0; dir < 4; dir++) {
					R = curr.row + dRow[dir];
					C = curr.col + dCol[dir];
					
					// 범위를 벗어난 경우 생략
					if(!checkRange(R, N) || !checkRange(C, N)) continue;
					
					// 다음 위치에 도달하기 위한 비용 갱신
					if(costDP[R][C] > costDP[curr.row][curr.col] + cave[R][C]) {
						costDP[R][C] = costDP[curr.row][curr.col] + cave[R][C];
						bfsQueue.offer(new Coord(R, C));
					}
				}
			}
			
			System.out.println("Problem " + test_case + ": " + costDP[N - 1][N - 1]);
		}
		
		br.close();
	}
	
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
}
