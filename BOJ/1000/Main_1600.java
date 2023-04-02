import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1600번 말이 되고픈 원숭이
 * 문제 분류: 다이나믹 프로그래밍, BFS
 * @author Giwon
 */
public class Main_1600 {
	public static final int INF = Integer.MAX_VALUE;
	
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	// 4부터는 오른쪽 위부터 차례대로 반시계 방향 순서로 말처럼 이동
	public static final int[] dRow = {0, -1, 0, 1, -2, -1, 1, 2, 2, 1, -1, -2};
	public static final int[] dCol = {-1, 0, 1, 0, 1, 2, 2, 1, -1, -2, -2, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 말처럼 움직일 수 있는 최대 횟수
		final int K = Integer.parseInt(br.readLine().trim());
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 가로 너비
		final int W = Integer.parseInt(st.nextToken());
		// 세로 높이
		final int H = Integer.parseInt(st.nextToken());
		
		// 격자 입력
		int[][] grid = new int[H][W];
		for(int row = 0; row < H; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < W; col++) {
				grid[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 초기값 설정
		State[][] minMove = new State[H][W];
		for(int row = 0; row < H; row++) {
			for(int col = 0; col < W; col++) {
				minMove[row][col] = new State(K);
			}
		}
		// 시작점 초기화
		Arrays.fill(minMove[0][0].count, 0);
		// BFS로 도착점이 있는 위치까지 최소 횟수 탐색
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		bfsQueue.offer(new Coord(0, 0, 0));
		Coord curr;
		int R, C, moveCount, horseMove;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			// 도착지점에 도달한 경우 중단
			if(curr.row == H - 1 && curr.col == W - 1) break;
			
			for(int dir = 0; dir < 12; dir++) {
				R = curr.row + dRow[dir];
				C = curr.col + dCol[dir];
				horseMove = curr.horseMove;
				if(dir >= 4) horseMove++;
				moveCount = minMove[curr.row][curr.col].getCount(curr.horseMove) + 1;
				
				// 말처럼 움직일 수 있는 횟수를 초과하면 생략
				if(horseMove > K) continue;
				// 범위를 벗어나면 생략
				if(!checkRange(R, H) || !checkRange(C, W)) continue;
				// 장애물에 위치하면 생략
				if(grid[R][C] == 1) continue;
				
				if(minMove[R][C].isSmaller(moveCount, horseMove)) {
					// 갱신이 가능한 경우
					minMove[R][C].setCount(moveCount, horseMove);
					bfsQueue.offer(new Coord(R, C, horseMove));
				}
			}
		}

		int result = minMove[H - 1][W - 1].getMin();
		System.out.println(result == INF ? -1 : result);
		br.close();
	}	
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	public static class Coord {
		// 행, 열
		int row, col;
		// 말처럼 이동한 횟수
		int horseMove;
		
		public Coord(int row, int col, int horseMove) {
			this.row = row;
			this.col = col;
			this.horseMove = horseMove;
		}
	}
	
	public static class State {
		// 말처럼 이동한 횟수별 현재 위치에 도달하기 위한 이동 횟수
		int[] count;
		
		public State(int K) {
			count = new int[K + 1];
			Arrays.fill(count, INF);
		}
		
		public int getCount(int horseMove) {
			return count[horseMove];
		}
		
		public void setCount(int moveCount, int horseMove) {
			count[horseMove] = moveCount;
		}
		
		public boolean isSmaller(int moveCount, int horseMove) {
			if(getCount(horseMove) > moveCount) return true;
			return false;
		}
		
		public int getMin() {
			int result = INF;
			for(int val: count) {
				result = Math.min(result, val);
			}
			
			return result;
		}
	}
	
}
