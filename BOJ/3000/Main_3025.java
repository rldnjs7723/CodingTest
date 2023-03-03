import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 백준 3025번 돌 던지기
 * 문제 분류: 구현, 시뮬레이션, 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_3025 {
	public static final int BLANK = 0, WALL = 1, ROCK = 2;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		// 보드 행 크기
		final int R = Integer.parseInt(input.split(" ")[0]);
		// 보드 열 크기
		final int C = Integer.parseInt(input.split(" ")[1]);
		
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
		
		// 현재 행, 열 위치에 도달했을 때 다음에 이동하게 될 위치를 기록
		Coord[][] nextLocation = new Coord[R][C];
		
		// 돌을 던진 횟수
		int N = Integer.parseInt(br.readLine().trim());
		
		int row, col, idx;
		Coord loc;
		for(int i = 0; i < N; i++) {
			col = Integer.parseInt(br.readLine().trim()) - 1;
			// 시작점 설정 (항상 제일 윗 칸이 비어있는 경우에만 돌을 던짐)
			row = highest[col];
			// 이전에 기록해둔 위치가 있다면 
			if((loc = nextLocation[row][col]) != null) {
				row = loc.row;
				col = loc.col;
			}
			
			
			
			
			
			
			
			while(true) {
				// 1. 가장 아랫줄인 경우 멈추기
				if(row == R - 1) {
					state[row][col] = ROCK;
					// 바위 쌓아서 높이 갱신
					idx = Collections.binarySearch(highest[col], row);
					if(idx < 0) idx = -(idx + 1);
					// 해당 열의 부분 높이 증가
					highest[col].set(idx, highest[col].get(idx) - 1);
					break;
				}
				// 1. 아랫칸이 벽인 경우 멈추기
				if(state[row + 1][col] == WALL) {
					state[row][col] = ROCK;
					// 바위 쌓아서 높이 갱신
					idx = Collections.binarySearch(highest[col], row);
					if(idx < 0) idx = -(idx + 1);
					// 해당 열의 부분 높이 증가
					highest[col].set(idx, highest[col].get(idx) - 1);
					break;
				}
				
				// 왼쪽과 왼쪽 아래 칸이 비어 있는 경우 왼쪽 아래로 이동
				if(col > 0 && state[row][col - 1] == BLANK && state[row + 1][col - 1] == BLANK) {
					col--;
					idx = Collections.binarySearch(highest[col], row);
					if(idx < 0) idx = -(idx + 1);
					row = highest[col].get(idx) - 1;
					continue;
				}
				
				// 오른쪽과 오른쪽 아래 칸이 비어 있는 경우 오른쪽 아래로 이동
				if(col < C - 1 && state[row][col + 1] == BLANK && state[row + 1][col + 1] == BLANK) {
					col++;
					idx = Collections.binarySearch(highest[col], row);
					if(idx < 0) idx = -(idx + 1);
					row = highest[col].get(idx) - 1;
					continue;
				}
				
				// 움직일 수 없다면 멈추기
				state[row][col] = ROCK;
				// 바위 쌓아서 높이 갱신
				idx = Collections.binarySearch(highest[col], row);
				if(idx < 0) idx = -(idx + 1);
				// 해당 열의 부분 높이 증가
				highest[col].set(idx, highest[col].get(idx) - 1);
				break;
			}
		}
		
		printBoard(state);
		br.close();
	}
	
	public static class Coord {
		int row, col;
		Coord next, prev;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
			this.next = null;
			this.prev = null;
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
	
	// 각 열에서 벽이나 돌이 위치한 곳 기록
	@SuppressWarnings("serial")
	public static class WallRow extends ArrayList<Integer>{}
}
