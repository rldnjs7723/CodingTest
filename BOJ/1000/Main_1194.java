import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1194번 달이 차오른다, 가자.
 * 문제 분류: 비트 필드를 이용한 다이나믹 프로그래밍, BFS
 * @author Giwon
 */
public class Main_1194 {
	public static final int INF = Integer.MAX_VALUE;
	public static final int FULL = 0b111111;
	public static final char BLANK = '.', WALL = '#', START = '0', EXIT = '1';
	
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 세로 크기
		final int N = Integer.parseInt(st.nextToken());
		// 가로 크기
		final int M = Integer.parseInt(st.nextToken());
		
		// 미로 입력
		String input;
		char[][] maze = new char[N][M];
		int R = -1, C = -1;
		for(int row = 0; row < N; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < M; col++) {
				maze[row][col] = input.charAt(col);
				if(maze[row][col] == START) {
					R = row;
					C = col;
					maze[row][col] = BLANK;
				}
			}
		}
		
		// r행 c열에서 열쇠 상태가 bitmask인 경우 해당 위치에 도달하는 최소 이동 횟수 = moveCountDP[r][c][bitmask]
		int[][][] moveCountDP = new int[N][M][FULL + 1];
		// 초기값 설정
		for(int[][] inner: moveCountDP) {
			for(int[] arr: inner) {
				Arrays.fill(arr, INF);
			}
		}
		// 시작 위치 이동 횟수 0
		moveCountDP[R][C][0] = 0;
		
		// BFS로 미로 탐색
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		bfsQueue.offer(new Coord(R, C, 0));
		Coord curr;
		int nextBit;
		int result = -1;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			// 현재 위치가 미로의 출구라면 중단
			if(maze[curr.row][curr.col] == EXIT) {
				result = moveCountDP[curr.row][curr.col][curr.bitmask];
				break;
			}
			
			for(int dir = 0; dir < 4; dir++) {
				R = curr.row + dRow[dir];
				C = curr.col + dCol[dir];
				
				// 범위를 벗어난 경우 생략
				if(!checkRange(R, N) || !checkRange(C, M)) continue;
				// 벽인 경우 생략
				if(maze[R][C] == WALL) continue;
				// 문인데 열쇠가 없는 경우 생략
				if(isDoor(maze[R][C]) && !hasKey(curr.bitmask, maze[R][C])) continue;
				
				nextBit = curr.bitmask;
				// 열쇠라면 비트마스크에 열쇠 추가
				if(isKey(maze[R][C])) nextBit = addKey(nextBit, maze[R][C]);
				
				// 다음 위치로 가는 최소 횟수를 갱신할 수 있다면 이동
				if(moveCountDP[R][C][nextBit] > moveCountDP[curr.row][curr.col][curr.bitmask] + 1) {
					moveCountDP[R][C][nextBit] = moveCountDP[curr.row][curr.col][curr.bitmask] + 1;
					bfsQueue.offer(new Coord(R, C, nextBit));
				}
			}
		}
		
		System.out.println(result);
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}
	
	// 현재 위치에 있는 값이 문인지 확인
	public static boolean isDoor(char loc) {
		return 'A' <= loc && loc <= 'F';
	}
	
	// 현재 위치에 있는 값이 열쇠인지 확인
	public static boolean isKey(char loc) {
		return 'a' <= loc && loc <= 'f';
	}
	
	// 열쇠 습득한 경우 비트마스크 리턴
	public static int addKey(int bitmask, char key) {
		return bitmask | (1 << (key - 'a'));
	}
	
	// 문에 맞는 열쇠가 있는지 체크
	public static boolean hasKey(int bitmask, char door) {
		return (bitmask & (1 << (door - 'A'))) > 0;
	}
	
	public static class Coord {
		// 위치 행, 열
		int row, col;
		// 열쇠 상태 비트마스크
		int bitmask;
		
		public Coord(int row, int col, int bitmask) {
			this.row = row;
			this.col = col;
			this.bitmask = bitmask;
		}
	}
}
