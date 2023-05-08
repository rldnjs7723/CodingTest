import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 백준 1938번 통나무 옮기기
 * 문제 분류: BFS 
 * @author Giwon
 */
public class Main_1938 {
	public static final int MAX = Integer.MAX_VALUE;
	public static final int BLANK = 0, PILLAR = 1, TARGET = 2, DESTINATION = 3;
	public static final int HORIZONTAL = 0, VERTICAL = 1;
	public static final int UP = 0, UPRIGHT = 1, RIGHT = 2, DOWNRIGHT = 3, DOWN = 4, DOWNLEFT = 5, LEFT = 6, UPLEFT = 7, ROTATE = 8;
	public static final int[] dRow = {-1, -1, 0, 1, 1, 1, 0, -1, 0};
	public static final int[] dCol = {0, 1, 1, 1, 0, -1, -1, -1, 0};
	public static final int[] MOVE_ENUM = {LEFT, RIGHT, UP, DOWN, ROTATE};
	// 한 변의 길이
	public static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 한 변의 길이
		N = Integer.parseInt(br.readLine().trim());
		// 상태 입력
		int[][] map = new int[N][N];
		String input;
		for(int row = 0; row < N; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < N; col++) {
				switch (input.charAt(col)) {
					case 'B':
						map[row][col] = TARGET;
						break;
					case 'E':
						map[row][col] = DESTINATION;
						break;
					case '0':
						map[row][col] = BLANK;
						break;
					case '1':
						map[row][col] = PILLAR;
						break;
					default:
						break;
				}
			}
		}
		
		// 시작 위치, 종료 위치 찾기
		Location start = null, end = null;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				
				// 왼쪽, 오른쪽이 시작점인 경우
				if(checkLocation(row, col, LEFT, TARGET, map) && 
						checkLocation(row, col, RIGHT, TARGET, map)) {
					start = new Location(row, col, HORIZONTAL);
					continue;
				}
				
				// 왼쪽, 오른쪽이 종점인 경우
				if(checkLocation(row, col, LEFT, DESTINATION, map) && 
						checkLocation(row, col, RIGHT, DESTINATION, map)) {
					end = new Location(row, col, HORIZONTAL);
					continue;
				}
				
				// 위, 아래가 시작점인 경우
				if(checkLocation(row, col, UP, TARGET, map) && 
						checkLocation(row, col, DOWN, TARGET, map)) {
					start = new Location(row, col, VERTICAL);
					continue;
				}
				
				// 위, 아래가 종점인 경우
				if(checkLocation(row, col, UP, DESTINATION, map) && 
						checkLocation(row, col, DOWN, DESTINATION, map)) {
					end = new Location(row, col, VERTICAL);
					continue;
				}
			}
		}
		
		// 현재 위치, 방향에 도달하기 위한 최소 이동횟수
		int[][][] moveCount = new int[N][N][2];
		// 초기값 설정
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				for(int dir = 0; dir < 2; dir++) {
					moveCount[row][col][dir] = MAX;
				}
			}
		}
		// 시작 위치는 이동횟수 0
		moveCount[start.row][start.col][start.dir] = 0;
		
		// BFS로 탐색
		Queue<Location> bfsQueue = new ArrayDeque<>();
		bfsQueue.offer(start);
		
		Location curr;
		int R, C, dir;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			// 목표 위치까지 이동한 경우 중단 (BFS이므로 최적)
			if(curr.equalLocation(end)) break;
			
			// 5개의 기본 동작 수행
			for(int move: MOVE_ENUM) {
				// 이동할 수 있는지 체크
				if(curr.checkMoveable(move, map)) {
					R = curr.row + dRow[move];
					C = curr.col + dCol[move];
					dir = move == ROTATE ? (curr.dir + 1) % 2 : curr.dir;
					
					// 목표 지점의 이동 횟수를 줄일 수 있다면 이동
					if(moveCount[R][C][dir] > moveCount[curr.row][curr.col][curr.dir] + 1) {
						moveCount[R][C][dir] = moveCount[curr.row][curr.col][curr.dir] + 1;
						// Queue에 추가
						bfsQueue.offer(new Location(R, C, dir));
					}
				}
			}
		}
		
		System.out.println(moveCount[end.row][end.col][end.dir] == MAX ? 0 : moveCount[end.row][end.col][end.dir]);
		br.close();
	}
	
	/**
	 * 현재 위치에서 입력한 방향에 목표가 존재하는지 체크
	 * @param row
	 * @param col
	 * @param dir
	 * @param target
	 * @return			target의 존재 여부
	 */
	public static boolean checkLocation(int row, int col, int dir, int target, int[][] map) {
		int R = row + dRow[dir];
		int C = col + dCol[dir];
		
		// 범위를 벗어나는 경우 존재하지 않음
		if(!checkRange(R) || !checkRange(C)) return false;
		
		return map[R][C] == target;
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return 0 <= target && target < N;
	}
	
	public static boolean checkRange(int row, int col, int dir) {
		int R = row + dRow[dir];
		int C = col + dCol[dir];
		
		return checkRange(R) && checkRange(C);
	}
	
	public static class Location {
		int dir;
		int row, col;
		
		public Location(int row, int col, int dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		public boolean checkMoveable(int move, int[][] map) {
			
			// 회전
			if(move == ROTATE) {
				for(int i = 0; i < ROTATE; i++) {
					// 범위에서 벗어나면 이동 불가
					if(!checkRange(row, col, i)) return false;
					// 주변에 하나라도 기둥이 존재하면 회전 불가능
					if(checkLocation(row, col, i, PILLAR, map)) return false;
				}
				return true;
			} 
			
			int R, C;
			// 왼쪽으로 이동
			if(move == LEFT) {
				if(dir == HORIZONTAL) {
					// 현재 가로 방향인 경우
					// 왼쪽 두 칸이 비어있을 때만 이동 가능
					R = row;
					C = col + dCol[LEFT] * 2;
					
					return checkRange(R) && checkRange(C) && map[R][C] != PILLAR;
				} else {
					// 현재 세로 방향인 경우
					// 왼쪽 세 방향이 전부 비어있을 때만 이동 가능
					int[] dirEnum = {UPLEFT, LEFT, DOWNLEFT};
					
					for(int checkDir: dirEnum) {
						// 범위에서 벗어나면 이동 불가
						if(!checkRange(row, col, checkDir)) return false;
						// 하나라도 기둥이 존재하면 이동 불가
						if(checkLocation(row, col, checkDir, PILLAR, map)) return false;
					}
				}
			} 
			// 오른쪽으로 이동
			else if(move == RIGHT) {
				if(dir == HORIZONTAL) {
					// 현재 가로 방향인 경우
					// 오른쪽 두 칸이 비어있을 때만 이동 가능
					R = row;
					C = col + dCol[RIGHT] * 2;
					
					return checkRange(R) && checkRange(C) && map[R][C] != PILLAR;
				} else {
					// 현재 세로 방향인 경우
					// 오른쪽 세 방향이 전부 비어있을 때만 이동 가능
					int[] dirEnum = {UPRIGHT, RIGHT, DOWNRIGHT};
					
					for(int checkDir: dirEnum) {
						// 범위에서 벗어나면 이동 불가
						if(!checkRange(row, col, checkDir)) return false;
						// 하나라도 기둥이 존재하면 이동 불가
						if(checkLocation(row, col, checkDir, PILLAR, map)) return false;
					}
				}
			}
			// 위쪽으로 이동
			else if(move == UP) {
				if(dir == HORIZONTAL) {
					// 현재 가로 방향인 경우
					// 위쪽 세 방향이 전부 비어있을 때만 이동 가능
					int[] dirEnum = {UPLEFT, UP, UPRIGHT};
					
					for(int checkDir: dirEnum) {
						// 범위에서 벗어나면 이동 불가
						if(!checkRange(row, col, checkDir)) return false;
						// 하나라도 기둥이 존재하면 이동 불가
						if(checkLocation(row, col, checkDir, PILLAR, map)) return false;
					}
				} else {
					// 현재 세로 방향인 경우
					// 위쪽 두 칸이 비어있을 때만 이동 가능
					R = row + dRow[UP] * 2;
					C = col;
					
					return checkRange(R) && checkRange(C) && map[R][C] != PILLAR;
				}
			}
			// 아래쪽으로 이동
			else if(move == DOWN) {
				if(dir == HORIZONTAL) {
					// 현재 가로 방향인 경우
					// 아래쪽 세 방향이 전부 비어있을 때만 이동 가능
					int[] dirEnum = {DOWNLEFT, DOWN, DOWNRIGHT};
					
					for(int checkDir: dirEnum) {
						// 범위에서 벗어나면 이동 불가
						if(!checkRange(row, col, checkDir)) return false;
						// 하나라도 기둥이 존재하면 이동 불가
						if(checkLocation(row, col, checkDir, PILLAR, map)) return false;
					}
				} else {
					// 현재 세로 방향인 경우
					// 아래쪽 두 칸이 비어있을 때만 이동 가능
					R = row + dRow[DOWN] * 2;
					C = col;
					
					return checkRange(R) && checkRange(C) && map[R][C] != PILLAR;
				}
			}
			
			return true;
		}
		
		public boolean equalLocation(Location target) {
			return this.row == target.row && this.col == target.col && this.dir == target.dir;
		}
	}
}
