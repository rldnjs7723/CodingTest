import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 3055번 탈출
 * 문제 분류: 구현, 시뮬레이션, BFS
 * @author Giwon
 */
public class Main_3055 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	
	public static final int WATER = 0, BLANK = Integer.MAX_VALUE - 1, BEAVER = Integer.MAX_VALUE, ROCK = Integer.MIN_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 행 크기
		final int R = Integer.parseInt(st.nextToken());
		// 열 크기
		final int C = Integer.parseInt(st.nextToken());
		
		// 지도 상태 입력
		String input;
		int[][] state = new int[R][C];
		// 고슴도치 위치 저장용
		Coord hedgehog = new Coord(-1, -1, 0);
		for(int row = 0; row < R; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < C; col++) {
				switch (input.charAt(col)) {
					case '.':
						state[row][col] = BLANK;
						break;
					case '*':
						state[row][col] = WATER;
						break;
					case 'D':
						state[row][col] = BEAVER;
						break;
					case 'S':
						state[row][col] = BLANK;
						// 고슴도치 위치 설정
						hedgehog.row = row;
						hedgehog.col = col;
						break;
					case 'X':
						state[row][col] = ROCK;
						break;
					default:
						break;
				}
			}
		}
		
		// 각 위치에 물이 언제 차게 될지 계산
		flood(R, C, state);
//		printArr(state);
		// 고슴도치 이동
		int moveCount = getMoveCount(R, C, state, hedgehog);
		System.out.println(moveCount == -1 ? "KAKTUS" : moveCount);
		br.close();
	}
	
	// 고슴도치 이동 최소 시간 계산
	public static int getMoveCount(int R, int C, int[][] state, Coord hedgehog) {
		// 방문 체크용
		boolean[][] visited = new boolean[R][C];
		// BFS로 이동할 수 있는 부분 탐색
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		// 고슴도치 시작 위치 설정
		bfsQueue.offer(hedgehog);
		visited[hedgehog.row][hedgehog.col] = true;
		
		Coord curr;
		int minTime = -1;
		int nextRow, nextCol, nextDepth;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			// 비버굴에 도착했다면 중단
			if(state[curr.row][curr.col] == BEAVER) {
				minTime = curr.depth;
				break;
			}
			
			for(int dir = 0; dir < 4; dir++) {
				nextRow = curr.row + dRow[dir];
				nextCol = curr.col + dCol[dir];
				nextDepth = curr.depth + 1;
				
				// 범위를 벗어난 경우 생략
				if(!checkRange(nextRow, R) || !checkRange(nextCol, C)) continue;
				// 이미 방문한 경우 생략
				if(visited[nextRow][nextCol]) continue;
				
				// 다음에 이동할 위치가 물에 잠기지 않는다면 이동
				if(state[nextRow][nextCol] > nextDepth) {
					bfsQueue.offer(new Coord(nextRow, nextCol, nextDepth));
					// 방문 체크
					visited[nextRow][nextCol] = true;
				}
			}
		}
		
		return minTime;
	}
	
	
	// 각 위치에 물이 언제 차게 될지 계산
	public static void flood(int R, int C, int[][] state) {
		// BFS로 물 확산
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		// 모든 물의 위치 Queue에 저장
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				if(state[row][col] == WATER) bfsQueue.offer(new Coord(row, col, 0));
			}
		}
		
		Coord curr;
		int nextRow, nextCol;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			for(int dir = 0; dir < 4; dir++) {
				nextRow = curr.row + dRow[dir];
				nextCol = curr.col + dCol[dir];
				
				// 범위를 벗어난 경우 생략
				if(!checkRange(nextRow, R) || !checkRange(nextCol, C)) continue;
				// 빈칸이 아니라면 생략
				if(state[nextRow][nextCol] != BLANK) continue;
				
				// 현재 위치에 물 추가
				state[nextRow][nextCol] = curr.depth + 1;
				// 다음 위치 탐색
				bfsQueue.offer(new Coord(nextRow, nextCol, state[nextRow][nextCol]));
			}
		}
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}
	
	// 디버깅용 배열 출력
	public static void printArr(int[][] state) {
		for(int[] inner: state) {
			System.out.println(Arrays.toString(inner));
		}
	}
	
	public static class Coord {
		int row, col;
		// BFS 탐색 깊이
		int depth;
		
		public Coord(int row, int col, int depth) {
			this.row = row;
			this.col = col;
			this.depth = depth;
		}

		@Override
		public String toString() {
			return "Coord [row=" + row + ", col=" + col + ", depth=" + depth + "]";
		}
	}
}
