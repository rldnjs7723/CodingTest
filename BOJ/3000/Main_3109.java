import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 3109번 빵집
 * 문제 분류: 사방 탐색, 그리디 알고리즘, 백트래킹
 * @author Giwon
 */
public class Main_3109 {
	public static final int BLANK = 0;
	public static final int WALL = 1;
	public static final int PIPE = 2;
	public static final int VISITED = 3;
	
	public static final int INIT = 0, UP = 1, RIGHT = 2, DOWN = 3, BACK_DOWN = 4, BACK_LEFT = 5, BACK_UP = 6;
	public static final int[] dRow = {Integer.MIN_VALUE, -1, 0, 1, 1, 0, -1};
	public static final int[] dCol = {Integer.MIN_VALUE, 1, 1, 1, -1, -1, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		// 행 크기
		int R = Integer.parseInt(input.split(" ")[0]);
		// 열 크기
		int C = Integer.parseInt(input.split(" ")[1]);
		
		// 빵집 근처 상태 입력
		int[][] map = new int[R][C];
		for(int row = 0; row < R; row++) {
			input = br.readLine();
			
			for(int col = 0; col < C; col++) {
				switch(input.charAt(col)) {
					case '.': 
						map[row][col] = BLANK;
						break;
					case 'x':
						map[row][col] = WALL;
						break;
					default:
						break;
				}
			}
		}
		
		// 파이프를 하나씩 생성할 수 있는지 확인
		int result = 0;
		Pipe curr;
		// 모든 행에서 파이프를 생성할 수 있는지 체크
		// 파이프는 위에서 생성된 경우가 최적해에 포함되는 파이프임을 보장함
		for(int row = 0; row < R; row++) {
			curr = new Pipe(row, R, C);
			// 파이프가 제일 오른쪽에 도달할 때까지 탐색
			while(!curr.isDone()) {
				// 더 이상 다음 열로 이동할 수 없다면 제외
				if(!curr.goNextForward(map)) break;
			}
			// 파이프를 설치할 수 있다면 설치
			if(curr.isDone()) {
				result++;
				curr.buildPipe(map);
			}
		}
		
		System.out.println(result);
		
		br.close();
	}
	
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	public static class Pipe {
		int initial, row, col, idx, R, C;
		int[] dirs;
		
		Pipe(int row, int R, int C) {
			this.initial = row;
			this.row = row;
			this.col = 0;
			this.R = R;
			this.C = C;
			this.dirs = new int[C - 1];
			this.idx = 0;
		}
		
		// 현재 파이프가 맵의 가장 오른쪽 열에 도착했는지 확인
		public boolean isDone() {
			return col == C - 1;
		}
		
		// 현재 위치에 파이프를 설치 할 수 있다고 체크 후 다음 위치로 이동
		public boolean goForward(int dir, int map[][]) {
			int r = row + dRow[dir];
			int c = col + dCol[dir];
			
			if(checkRange(r, R) && map[r][c] == BLANK) {
				this.row = r;
				this.col = c;
				this.dirs[idx++] = dir;
				map[r][c] = VISITED;
				
				return true;
			}
			
			this.dirs[idx] = dir;
			return false;
		}
		
		// 오른쪽 위 -> 오른쪽 -> 오른쪽 아래 순서로 방향 설정
		public int nextDir() {
			return dirs[idx] == DOWN ? -1 : dirs[idx] + 1;
		}
		
		// 다음 열로 이동 가능한지 확인
		public boolean goNextForward(int map[][]) {
			int dir = nextDir();
			if(dir == -1) return goBackward();
			
			goForward(dir, map);
			return true;
		}
		
		// 다음 열로 이동할 수 없어 뒤로 되돌아가기
		public boolean goBackward() {
			if(col == 0) return false;
			
			dirs[idx] = INIT;
			idx--;
			this.row += dRow[dirs[idx] + 3];
			this.col += dCol[dirs[idx] + 3];
			
			return true;
		}
		
		// 현재 빵집 주변 상태에 파이프를 설치헀다고 표시
		public void buildPipe(int map[][]) {
			int r = initial;
			int c = 0;
			
			map[r][c] = PIPE;
			for(int i = 0; i < C - 1; i++) {
				r += dRow[dirs[i]];
				c += dCol[dirs[i]];
				map[r][c] = PIPE;
			}
		}
	}
}