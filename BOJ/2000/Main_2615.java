import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 2615번 오목
 * 문제 분류: 완전 탐색, 비트마스킹
 * @author Giwon
 */
public class Main_2615 {
	public static final int RIGHT = 0, DOWN = 1, RIGHTDOWN = 2, LEFTDOWN = 3;
	public static final int[] dRow = {0, 1, 1, 1};
	public static final int[] dCol = {1, 0, 1, -1};
	// 오른쪽, 아래, 오른쪽아래, 오른쪽 위 전부 탐색 한 경우
	public static final int FULL = 0b1111;
	// 바둑판 크기
	public static final int N = 19;
	// 흑돌 1, 백돌 2
	public static final int BLANK = 0, BLACK = 1, WHITE = 2;

	public static void main(String[] args) throws IOException {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			StringTokenizer st;
			// 바둑판 입력
			int[][] board = new int[N][N];
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < N; col++) {
					board[row][col] = Integer.parseInt(st.nextToken());
				}
			}
			
			int[][] bitmask = new int[N][N];
			int r, c, count, curr;
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					// 돌이 놓여져 있고 탐색을 전부 수행하지 않았을 경우 탐색
					if(board[row][col] > BLANK && !isChecked(bitmask[row][col])) {
						curr = board[row][col];
						
						for(int dir = RIGHT; dir <= LEFTDOWN; dir++) {
							// 이미 탐색했던 방향이라면 탐색하지 않음
							if((bitmask[row][col] & 1 << dir) > 0) continue;
							
							// 방향 비트마스킹
							bitmask[row][col] |= 1 << dir;
							// 시작 돌 개수 1개
							count = 1;
							r = row + dRow[dir];
							c = col + dCol[dir];
							while(checkRange(r) && checkRange(c) && board[r][c] == curr) {
								// 돌 수 카운트
								count++;
								// 방향 비트마스킹
								bitmask[r][c] |= 1 << dir;
								
								// 이동
								r += dRow[dir];
								c += dCol[dir];
							}
							
							// 오목이 완성된 경우
							if(count == 5) {
								System.out.println(curr);
								if(dir == LEFTDOWN) {
									// 가장 왼쪽에 위치한 돌의 행, 열을 출력해야 함
									r -= dRow[dir];
									c -= dCol[dir];
									System.out.println((r + 1) + " " + (c + 1));
								} else {
									System.out.println((row + 1) + " " + (col + 1));
								}
								
								return;
							}
						}
					}
				}
			}
			
			// 승부가 결정되지 않은 경우 0 출력
			System.out.println(0);
		}
		
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}

	// 현재 오른쪽, 아래, 오른쪽 아래를 전부 탐색했는지 확인
	public static boolean isChecked(int bitmask) {
		return (bitmask ^ FULL) == 0;
	}
}
