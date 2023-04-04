import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 15685번 드래곤 커브
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_15685 {
	public static final int RIGHT = 0, UP = 1, LEFT = 2, DOWN = 3;
	public static final int[] dy = {0, -1, 0, 1};
	public static final int[] dx = {1, 0, -1, 0};
	public static final int MAX_GRID = 100;
	public static boolean[][] grid = new boolean[MAX_GRID + 1][MAX_GRID + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 드래곤 커브 개수
		final int N = Integer.parseInt(br.readLine().trim());
		// 드래곤 커브 정보 입력
		int x, y, d, g;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			// 시작 좌표
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			// 방향
			d = Integer.parseInt(st.nextToken());
			// 세대
			g = Integer.parseInt(st.nextToken());
			
			Curve dragonCurve = new Curve(x, y, d, g);
			// 드래곤 커브 그리기
			dragonCurve.move();
		}
		
		// 네 꼭짓점 방문 체크된 정사각형 탐색
		System.out.println(countSquare());
		br.close();
	}
	
	// 네 꼭짓점이 전부 방문 체크된 경우 탐색
	public static int countSquare() {
		int count = 0;
		for(int y = 0; y < MAX_GRID; y++) {
			for(int x = 0; x < MAX_GRID; x++) {
				if(grid[y][x] && grid[y + 1][x] && grid[y][x + 1] && grid[y + 1][x + 1]) count++;
			}
		}
		
		return count;
	}
	
	public static class Curve {
		// 현재 커브 시작점
		int x, y;
		// 현재 커브 세대
		int gen;
		// 현재 커브 방향
		List<Integer> dirs;
		
		public Curve(int x, int y, int dir, int gen) {
			this.x = x;
			this.y = y;
			this.gen = 0;
			this.dirs = new ArrayList<>();
			this.dirs.add(dir);
			
			// 꼭짓점에 방문 체크
			grid[y][x] = true;
			// 지정된 세대의 드래곤 커브 계산
			getDragonCurve(gen);
		}
		
		// 현재 저장되어 있는 방향대로 드래곤 커브 이동
		public void move() {
			int X, Y;
			for(int dir: dirs) {
				// 드래곤 커브는 격자 밖으로 벗어나지 않음
				X = x + dx[dir];
				Y = y + dy[dir];
				
				// 꼭짓점에 방문 체크
				grid[Y][X] = true;
				x = X;
				y = Y;
			}
		}
		
		// 지정된 세대까지 드래곤 커브 갱신하기
		private void getDragonCurve(int gen) {
			for(int i = 1; i <= gen; i++) {
				nextGen();
			}
		}
		
		// 다음 세대의 드래곤 커브 구하기
		private void nextGen() {
			// 이전 커브를 90도 회전한 커브
			List<Integer> rotateDirs = new ArrayList<>();
			for(int dir: dirs) {
				rotateDirs.add(rotateDir(dir));
			}
			
			// 이전 커브의 뒤에 다음 세대의 커브 방향 붙이기
			for(int i = rotateDirs.size() - 1; i >= 0; i--) {
				dirs.add(rotateDirs.get(i));
			}
			
			// 세대 갱신
			gen++;
		}
		
		// 방향을 반시계방향으로 90도 회전
		private static int rotateDir(int dir) {
			return (dir + 1) % 4;
		}
		
	}
}
