import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * 백준 3025번 돌 던지기
 * 문제 분류: 구현, 시뮬레이션, 스택, 메모이제이션
 * @author Giwon
 */
public class Main_3025 {
	public static final int BLANK = 0, WALL = 1, ROCK = 2;
	public static final int UP = 0, LEFT = 1, RIGHT = -1;
	public static int R, C;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		// 보드 행 크기
		R = Integer.parseInt(input.split(" ")[0]);
		// 보드 열 크기
		C = Integer.parseInt(input.split(" ")[1]);
		
		// 각 열에서 가장 높은 위치 저장 (돌이 가장 먼저 떨어질 곳)
		int[] highest = new int[C];
		Arrays.fill(highest, R - 1);
		
		// 보드 초기 상태 입력
		int[][] state = new int[R][C];
		for(int row = 0; row < R; row++) {
			input = br.readLine();
			for(int col = 0; col < C; col++) {
				state[row][col] = input.charAt(col) == '.' ? BLANK : WALL;
				if(state[row][col] == WALL) highest[col] = Math.min(highest[col], row - 1);
			}
		}
		
		// 현재 열 위치에 도달했을 때 다음에 이동하게 될 위치를 기록
		Location[] nextLocation = new Location[C];
		for(int col = 0; col < C; col++) {
			nextLocation[col] = new Location();
			nextLocation[col].push(new Coord(highest[col], col, null));
		}
		
		// 돌을 던진 횟수
		int N = Integer.parseInt(br.readLine().trim());
		
		int row, col, startCol;
		Coord curr;
		for(int i = 0; i < N; i++) {
			startCol = Integer.parseInt(br.readLine().trim()) - 1;
			curr = nextLocation[startCol].peek();
			
			// 시작점 설정 (항상 제일 윗 칸이 비어있는 경우에만 돌을 던짐)
			row = curr.row;
			col = curr.col;
			
			while(true) {
				// 1. 가장 아랫줄인 경우 멈추기
				if(row == R - 1) {
					state[row][col] = ROCK;
					// 스택 갱신
					updateStack(row, col, state, nextLocation);
					break;
				}
				// 1. 아랫칸이 벽인 경우 멈추기
				if(state[row + 1][col] == WALL) {
					state[row][col] = ROCK;
					// 스택 갱신
					updateStack(row, col, state, nextLocation);
					break;
				}
				
				// 왼쪽과 왼쪽 아래 칸이 비어 있는 경우 왼쪽 아래로 이동
				if(col > 0 && state[row][col - 1] == BLANK && state[row + 1][col - 1] == BLANK) {
					col--;
					// 아래가 벽이나 마지막이 아닐 때까지 내려가기
					while(row < R - 1 && state[row + 1][col] != WALL) {
						row++;
					}
					nextLocation[startCol].push(new Coord(row, col, nextLocation[startCol].peek()));
					continue;
				}
				
				// 오른쪽과 오른쪽 아래 칸이 비어 있는 경우 오른쪽 아래로 이동
				if(col < C - 1 && state[row][col + 1] == BLANK && state[row + 1][col + 1] == BLANK) {
					col++;
					// 아래가 벽이나 마지막이 아닐 때까지 내려가기
					while(row < R - 1 && state[row + 1][col] != WALL) {
						row++;
					}
					nextLocation[startCol].push(new Coord(row, col, nextLocation[startCol].peek()));
					continue;
				}
				
				// 움직일 수 없다면 멈추기
				state[row][col] = ROCK;
				// 스택 갱신
				updateStack(row, col, state, nextLocation);
				break;
			}
		}
		
		printBoard(state);
		br.close();
	}
	
	// 현재 행, 열 위치에 최종으로 들어오는 모든 열의 스택 갱신
	public static void updateStack(int row, int col, int[][] state, Location[] nextLocation) {
		// 현재 위치의 오른쪽이 돌인 경우
		if(col < C - 1 && state[row][col + 1] == ROCK) {
			// 현재 위치에 오른쪽으로 들어온 모든 열을 이전으로 롤백
			for(int c = 0; c < C; c++) {
				if(nextLocation[c].peek().isEqual(row, col, RIGHT)) {
					nextLocation[c].pop();
				}
			}
		}
		
		// 현재 위치의 왼쪽이 돌인 경우
		if(col > 0 && state[row][col - 1] == ROCK) {
			// 현재 위치에 왼쪽으로 들어온 모든 열을 이전으로 롤백
			for(int c = 0; c < C; c++) {
				if(nextLocation[c].peek().isEqual(row, col, LEFT)) {
					nextLocation[c].pop();
				}
			}
		}
		
		// 현재 위치에 최종으로 도달하는 모든 열의 행을 갱신
		for(int c = 0; c < C; c++) {
			if(nextLocation[c].peek().isEqual(row, col)) {
				nextLocation[c].peek().row--;
			}
		}
	}
	
//	
//	public static class DisjointSet {
//		Location prevLocation;
//		DisjointSet parent;
//		
//		public DisjointSet() {
//			
//			this.parent = null;
//		}
//		
//		// 
//		public Location getLocation() {
//			if(this.parent == null) return this.prevLocation;
//			
//			return this.parent.getLocation();
//		}
//	}
//	
	@SuppressWarnings("serial")
	public static class Location extends Stack<Coord>{}
	
	public static class Coord {
		// 좌표 행, 열 위치
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		@Override
		public String toString() {
			return "[ row: " + row + " col: " + col + " ]";
		}
		
		public boolean isEqual(int row, int col) {
			return this.row == row && this.col == col;
		}
		
		public boolean isEqual(Coord prev) {
			return this.isEqual(prev.row, prev.col);
		}
		
	}
	
	// 보드 출력
	public static void printBoard(int[][] state) {
		StringBuilder sb = new StringBuilder();
		for(int[] inner: state) {
			for(int val: inner) {
				switch (val) {
					case BLANK:
						sb.append('.');
						break;
					case WALL:
						sb.append('X');
						break;
					case ROCK:
						sb.append('O');
						break;
				}
			}
			sb.append("\n");
		}
		
		System.out.print(sb.toString());
	}
}
