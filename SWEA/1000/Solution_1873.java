import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SWEA 1873번 상호의 배틀필드
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Solution_1873 {
	public static final char PLAIN = '.', BRICK = '*', STEEL = '#', WATER = '-',  
			TANKUP = '^', TANKDOWN = 'v', TANKLEFT = '<', TANKRIGHT = '>';
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SHOOT = 4;
	public static final int[] dRow = {-1, 1, 0, 0};
	public static final int[] dCol = {0, 0, -1, 1};
	public static final char[] TANK = {TANKUP, TANKDOWN, TANKLEFT, TANKRIGHT};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringBuilder sb = new StringBuilder();
		String input;
		final int T = Integer.parseInt(br.readLine());
		int H, W, N, R, C;
		char[][] map;
		for(int test_case = 1; test_case <= T; test_case++) {
			input = br.readLine();
			// 행 크기 (높이)
			H = Integer.parseInt(input.split(" ")[0]);
			// 열 크기 (너비)
			W = Integer.parseInt(input.split(" ")[1]);
			// 현재 탱크 위치 (행, 열)
			R = -1;
			C = -1;
			// 게임 맵 입력
			map = new char[H][W];
			for(int row = 0; row < H; row++) {
				input = br.readLine().trim();
				for(int col = 0; col < W; col++) {
					map[row][col] = input.charAt(col);
					if(R == -1 && isTank(map[row][col]) != -1) {
						R = row;
						C = col;
					}
				}
			}
			// 입력 개수
			N = Integer.parseInt(br.readLine());
			// 입력 실행
			input = br.readLine().trim();
			int order, r, c;
			for(int i = 0; i < N; i++) {
				order = parseOrder(input.charAt(i));
				
				if(order < SHOOT) {
					// 탱크 이동
					r = R + dRow[order];
					c = C + dCol[order];
					
					// 범위를 벗어나거나 평지가 아니라면 방향만 바꿈
					if(!checkRange(r, H) || !checkRange(c, W) || map[r][c] != PLAIN) {
						map[R][C] = TANK[order];
						continue;
					}
					
					// 이동
					map[r][c] = TANK[order];
					map[R][C] = PLAIN;
					R = r;
					C = c;
				} else {
					// 포탄 발사
					// 현재 탱크가 바라보는 방향
					int dir = isTank(map[R][C]);
					
					r = R + dRow[dir];
					c = C + dCol[dir];
					// 포탄은 범위를 벗어나거나 벽이 나올 때까지 직진
					while(checkRange(r, H) && checkRange(c, W) &&
							map[r][c] != BRICK && map[r][c] != STEEL) {
						r += dRow[dir];
						c += dCol[dir];
					}
					
					// 벽돌 벽일 때만 평지로 변환
					if(checkRange(r, H) && checkRange(c, W) && 
							map[r][c] == BRICK)	{
						map[r][c] = PLAIN;
					}
				}
			}
			
			// 맵 상태 출력
			sb.setLength(0);
			for(char[] inner: map) {
				for(char state: inner) {
					sb.append(state);
				}
				sb.append("\n");
			}
			
			System.out.print("#" + test_case + " " + sb.toString());
		}
		
		br.close();
	}
	
	// 입력 파싱
	public static int parseOrder(char order) {
		switch (order) {
			case 'U':
				return UP; 
			case 'D':
				return DOWN;
			case 'L':
				return LEFT;
			case 'R':
				return RIGHT;
			case 'S':
				return SHOOT;
			default:
				return -1;
		}
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}

	// 탱크인지 확인하고, 탱크라면 바라보는 방향 리턴
	public static int isTank(char curr) {
		for(int i = 0; i < TANK.length; i++) {
			if(curr == TANK[i]) return i;
		}
		return -1;
	}
}
