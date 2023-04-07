import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;


/**
 * 백준 23288번 주사위 굴리기 2
 * 문제 분류: 구현, 시물레이션, Flood Fill
 * @author Giwon
 */
public class Main_23288 {
	public static final int CLOCKWISE = 0, COUNTERCLOCKWISE = 1, REVERSE = 2;
	public static final int UP = 0, DOWN = 1, FORE = 2, BACK = 3, LEFT = 4, RIGHT = 5;
	public static final int EAST = 0, SOUTH = 1, WEST = 2, NORTH = 3;
	public static final int[] dRow = {0, 1, 0, -1};
	public static final int[] dCol = {1, 0, -1, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 세로 크기
		final int N = Integer.parseInt(st.nextToken());
		// 가로 크기
		final int M = Integer.parseInt(st.nextToken());
		// 이동 횟수
		final int K = Integer.parseInt(st.nextToken());
		
		// 지도 입력
		int[][] map = new int[N][M];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < M; col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 주사위 생성
		Dice dice = new Dice();
		// 주사위 이동
		int score = 0;
		for(int i = 0; i < K; i++) {
			score += dice.moveDice(N, M, map);
		}
		
		System.out.println(score);
		br.close();
	}
	
	public static class Coord {
		int row, col;
		int val;
		
		public Coord(int row, int col, int val) {
			this.row = row;
			this.col = col;
			this.val = val;
		}
	}
	
	public static class Dice {
		// 주사위 위치
		int row, col;
		// 주사위 면
		int[] side;
		// 이동 방향
		int dir;
		
		public Dice() {
			// 주사위 위치 초기화
			this.row = 0;
			this.col = 0;
			
			// 주사위 면 초기화
			this.side = new int[6];
			this.side[UP] = 1;
			this.side[BACK] = 2;
			this.side[RIGHT] = 3;
			this.side[LEFT] = 4;
			this.side[FORE] = 5;
			this.side[DOWN] = 6;
			
			// 이동 방향 초기값 동쪽
			this.dir = EAST;
		}
		
		// 현재 이동 방향으로 주사위 이동. 획득한 점수 리턴
		public int moveDice(int N, int M, int[][] map) {
			int R = row + dRow[dir];
			int C = col + dCol[dir];
			
			// 다음 위치가 범위에서 벗어나는 경우
			if(!checkRange(R, N) || !checkRange(C, M)) {
				// 이동 방향 반대로 변경
				rotateDir(REVERSE);
				R = row + dRow[dir];
				C = col + dCol[dir];
			}
			
			// 이동 적용
			row = R;
			col = C;
			
			// 주사위 면 갱신
			int temp = side[UP];
			switch (dir) {
				case EAST:
					side[UP] = side[LEFT];
					side[LEFT] = side[DOWN];
					side[DOWN] = side[RIGHT];
					side[RIGHT] = temp;
					break;
				case WEST:
					side[UP] = side[RIGHT];
					side[RIGHT] = side[DOWN];
					side[DOWN] = side[LEFT];
					side[LEFT] = temp;
					break;
				case NORTH:
					side[UP] = side[FORE];
					side[FORE] = side[DOWN];
					side[DOWN] = side[BACK];
					side[BACK] = temp;
					break;
				case SOUTH:
					side[UP] = side[BACK];
					side[BACK] = side[DOWN];
					side[DOWN] = side[FORE];
					side[FORE] = temp;
					break;
				default:
					break;
			}
			
			// 이동 방향 갱신
			int A = side[DOWN];
			int B = map[row][col];
			
			if(A > B) rotateDir(CLOCKWISE);
			else if(A < B) rotateDir(COUNTERCLOCKWISE);
			
			
			// 연속으로 이동할 수 있는 칸 체크
			C = floodFill(N, M, map);
			
			// 점수 리턴
			return B * C;
		}
		
		// 연속으로 이동할 수 있는 칸 체크
		public int floodFill(int N, int M, int[][] map) {
			boolean[][] visited = new boolean[N][M];
			
			int count = 1;
			// BFS로 탐색
			Queue<Coord> bfsQueue = new ArrayDeque<>();
			// 시작 지점 입력
			bfsQueue.offer(new Coord(row, col, map[row][col]));
			visited[row][col] = true;
			
			int R, C;
			Coord curr;
			while(!bfsQueue.isEmpty()) {
				curr = bfsQueue.poll();
				
				for(int dir = 0; dir < 4; dir++) {
					R = curr.row + dRow[dir];
					C = curr.col + dCol[dir];
					
					// 범위를 벗어난 경우 생략
					if(!checkRange(R, N) || !checkRange(C, M)) continue;
					// 이미 방문한 경우 생략
					if(visited[R][C]) continue;
					// 현재 위치와 수가 다르면 생략
					if(curr.val != map[R][C]) continue;
					
					// 방문 체크
					visited[R][C] = true;
					count++;
					// 다음 위치 탐색
					bfsQueue.offer(new Coord(R, C, curr.val));
				}
			}
			
			return count;
		}
		
		// 이동 방향 회전
		public void rotateDir(int type) {
			if(type == CLOCKWISE) {
				// 시계 방향으로 90도 회전
				dir = (dir + 1) % 4;
			} else if(type == COUNTERCLOCKWISE) {
				// 반시계 방향으로 90도 회전
				dir = (dir - 1 + 4) % 4;
			} else if(type == REVERSE) {
				// 반대 방향으로 회전
				dir = (dir + 2) % 4;
			}
		}
		
		// 범위 체크
		public static boolean checkRange(int target, int N) {
			return 0 <= target && target < N;
		}
	}
}
