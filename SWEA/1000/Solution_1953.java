import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 1953번 탈주범 검거
 * 문제 분류: BFS, 사방 탐색, 구현
 * @author GIWON
 */
public class Solution_1953_남기원 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		// 세로 크기 / 가로 크기 / 멘홀 세로 위치 / 가로 위치 / 소요된 시간
		int N, M, R, C, L;
		Queue<Coord> queue = new ArrayDeque<>();
		// 지하 터널 구조
		int[][] map;
		// 탈주범이 이동한 위치 체크용 객체
		Location fugitive = Location.getInstance();
		// 탈주범 방문 체크용 배열
		Location[][] available;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 터널 세로 크기
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			
			map = new int[N][M];
			// 터널 상태 입력
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < M; col++) {
					map[row][col] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 객체 초기화
			queue.clear();
			fugitive.reset();
			queue.offer(new Coord(R, C, 1));
			available = new Location[N][M];
			available[R][C] = fugitive;
			// BFS로 갈 수 있는 곳 전부 탐색
			int r, c;
			Coord curr, next;
			while(!queue.isEmpty()) {
				curr = queue.poll();
				// BFS이므로 L 시간이 지난 후는 탐색하지 않음
				if(curr.time >= L) break;
				
				// 사방 탐색으로 주변 파이프로 이동할 수 있는지 확인
				for(int dir = 0; dir < 4; dir++) {
					r = curr.row + dRow[dir];
					c = curr.col + dCol[dir];
					
					if(checkRange(r, N) && checkRange(c, M) && available[r][c] == null) {
						next = new Coord(r, c, curr.time + 1);
						if(isConnected(curr, next, map, dir)) {
							fugitive.count++;
							queue.offer(next);
							// 방문 체크
							available[r][c] = fugitive;
						}
					}
				}
			}
			
			bw.write("#" + test_case + " " + fugitive.count + "\n");
		}
		
		bw.close();
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	// 목표 부분의 파이프가 방향과 일치하는지 체크
	public static boolean checkPipe(int dir, int type) {
		switch(dir) {
			case UP:
				if(type == 1 || type == 2 || type == 5 || type == 6) return true;
				else return false;
			case DOWN:
				if(type == 1 || type == 2 || type == 4 || type == 7) return true;
				else return false;
			case LEFT:
				if(type == 1 || type == 3 || type == 4 || type == 5) return true;
				else return false;
			case RIGHT:
				if(type == 1 || type == 3 || type == 6 || type == 7) return true;
				else return false;
			default:
				return false;
		}
	}
	
	// 현재 위치와 목표 위치가 연결되어 있는지 확인하는 메서드
	public static boolean isConnected(Coord start, Coord end, int[][] map, int dir) {
		int startType = map[start.row][start.col];
		int endType = map[end.row][end.col];
		
		switch (startType) {
			// 상, 하, 좌, 우와 연결
			case 1:
				return checkPipe(dir, endType);
			// 상, 하와 연결
			case 2:
				if(dir == UP || dir == DOWN) {
					return checkPipe(dir, endType);
				} else return false;
			// 좌, 우와 연결
			case 3:
				if(dir == LEFT || dir == RIGHT) {
					return checkPipe(dir, endType);
				} else return false;
			// 상, 우와 연결
			case 4:
				if(dir == UP || dir == RIGHT) {
					return checkPipe(dir, endType);
				} else return false;
			// 하, 우와 연결
			case 5:
				if(dir == DOWN || dir == RIGHT) {
					return checkPipe(dir, endType);
				} else return false;
			// 하, 좌와 연결
			case 6:
				if(dir == DOWN || dir == LEFT) {
					return checkPipe(dir, endType);
				} else return false;
			// 상, 좌와 연결
			case 7:
				if(dir == UP || dir == LEFT) {
					return checkPipe(dir, endType);
				} else return false;
			// 벽
			default:
				return false;
		}
	}
	
	// Queue에 넣을 좌표 클래스
	public static class Coord {
		int row, col, time;

		public Coord(int row, int col, int time) {
			this.row = row;
			this.col = col;
			this.time = time;
		}
	}
	
	// 탈주범이 이동할 수 있는 위치 체크용 클래스
	public static class Location {
		int count;
		private static Location instance = new Location(1);

		private Location(int count) {
			this.count = count;
		}
		
		public static Location getInstance() {
			return instance;
		}
		
		// N >= 1이므로 무조건 count는 1부터 시작
		public void reset() {
			this.count = 1;
		}
	}
}
