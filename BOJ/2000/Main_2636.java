import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 2636번 치즈
 * 문제 분류: Flood Fill 
 * @author Giwon
 */
public class Main_2636 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	public static final int AIR = 0, CHEESE = 1;
	public static final Adjacent adjacent = Adjacent.getInstance();
	public static Coord[][] coordinate;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 세로 크기
		final int N = Integer.parseInt(st.nextToken());
		// 가로 크기
		final int M = Integer.parseInt(st.nextToken());
		
		// 좌표 객체 미리 생성
		coordinate = new Coord[N][M];
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				coordinate[row][col] = new Coord(row, col);
			}
		}
		
		// 치즈 상태 입력
		int[][] cheese = new int[N][M];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < M; col++) {
				cheese[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 공기와 인접한 영역 계산
		Adjacent[][] state = checkAdjacentAir(N, M, cheese);
		int prev = 0, remainCheese;
		int count = 0;
		// 치즈 녹이기
		while((remainCheese = meltCheese(N, M, cheese, state)) > 0) {
			// 녹은 횟수 카운트
			count++;
			// 공기와 인접한 영역 다시 계산
			state = checkAdjacentAir(N, M, cheese);
			// 녹기 전의 치즈 수 기록
			prev = remainCheese;
		}

		System.out.println(count);
		System.out.println(prev);
		br.close();
	}
	
	// 치즈 녹이기. 녹기 전에 남아있던 치즈 개수를 리턴
	public static int meltCheese(int N, int M, int[][] cheese, Adjacent[][] state) {
		int count = 0;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				if(cheese[row][col] == CHEESE) {
					count++;
					// 공기와 인접해 있던 치즈는 녹이기
					if(state[row][col] != null) {
						cheese[row][col] = AIR;
					}
				}
			}
		}
		
		return count;
	}
	
	// 공기와 인접한 부분 체크
	public static Adjacent[][] checkAdjacentAir(int N, int M, int[][] cheese) {
		Adjacent[][] state = new Adjacent[N][M];
		// 시작점 초기화 (가장자리는 항상 비어있음)
		state[0][0] = adjacent;
		// bfs로 현재 위치에 인접한 영역 탐색
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		bfsQueue.offer(coordinate[0][0]);
		Coord curr;
		int R, C;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			for(int dir = 0; dir < 4; dir++) {
				R = curr.row + dRow[dir];
				C = curr.col + dCol[dir];
				
				// 범위를 벗어나면 생략
				if(!checkRange(R, N) || !checkRange(C, M)) continue;
				// 이미 방문한 곳은 생략
				if(state[R][C] != null) continue;
				
				state[R][C] = adjacent;
				// 이동한 곳이 공기라면 이어서 주변 탐색
				if(cheese[R][C] == AIR) bfsQueue.offer(coordinate[R][C]);
			}
		}
		
		return state;
	}
	
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}
	
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}
	}

	// 공기와 인접한 부분 표현 클래스
	public static class Adjacent {
		private static Adjacent instance = new Adjacent();
		private Adjacent() {};
		public static Adjacent getInstance() {
			return instance;
		}
	}
}
