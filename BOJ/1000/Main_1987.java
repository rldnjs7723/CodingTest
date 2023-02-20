import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 1987번 알파벳
 * 문제 분류: DFS, 사방탐색, 백트래킹, 비트마스킹
 * @author Giwon
 */
public class Main_1987 {
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3, NONE = -1;
	public static final int[] dRow = {0, -1, 0, 1};
	public static final int[] dCol = {-1, 0, 1, 0};
	
	public static void main(String[] args) throws IOException {
		// try ~ with 구문으로 BufferedReader 자동으로 close
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String input = br.readLine();
			// 행 크기
			int R = Integer.parseInt(input.split(" ")[0]);
			// 열 크기
			int C = Integer.parseInt(input.split(" ")[1]);
			
			// 알파벳 26개를 비트마스크로 표현하기 위해 정수 입력
			// A = 0 ~ Z = 25
			int[][] board = new int[R][C];
			for(int row = 0; row < R; row++) {
				input = br.readLine().trim();
				for(int col = 0; col < C; col++) {
					board[row][col] = input.charAt(col) - 'A';
				}
			}
			
			// 사방 탐색을 위한 방향 기록
			int[][] direction = new int[R][C];
			// 백트래킹을 위한 이동한 방향 기록
			int[][] prev = new int[R][C];
			
			// 시작점 초기화
			int row = 0, col = 0, r, c;
			int count = 1, max = 1;
			// 비트마스크로 방문한 알파벳 체크
			int bitmask = 1 << board[row][col];
			while(direction[0][0] < 4 || !(row == 0 && col == 0)) {
				// 모든 방향을 다 탐색했으면 돌아가기
				if(direction[row][col] == 4) {
					r = row + dRow[prev[row][col]];
					c = col + dCol[prev[row][col]];
					
					// 방향 초기화
					direction[row][col] = LEFT;
					// 비트마스크에서 빼기
					bitmask -= 1 << board[row][col];
					
					// 카운트 빼기
					count--;
					
					// 이동
					row = r;
					col = c;
					continue;
				}
				
				r = row + dRow[direction[row][col]];
				c = col + dCol[direction[row][col]];
				
				// 체크하지 않은 알파벳이 있는 경우 탐색
				if(checkRange(r, R) && checkRange(c, C) 
						&& (bitmask & (1 << board[r][c])) == 0) {
					
					// 비트마스크에 다음 방문할 곳 체크
					bitmask |= 1 << board[r][c];
					prev[r][c] = (direction[row][col] + 2) % 4;
					
					// 현재 위치의 방향 다음으로 변경
					direction[row][col]++;
					
					// 이동한 칸 수 카운트
					count++;
					max = Math.max(max, count);
					
					// 이동
					row = r;
					col = c;
				} else {
					// 탐색할 수 없으면 방향 변경
					direction[row][col]++;
				}
			}
			
			System.out.println(max);
		}
	}

	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	
}
