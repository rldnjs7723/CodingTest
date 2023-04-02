import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 4991번 로봇 청소기
 * 문제 분류: 비트 필드를 이용한 다이나믹 프로그래밍, BFS
 * @author Giwon
 */
public class Main_4991 {
	public static final char CLEAN = '.', DIRTY = '*', FURNITURE = 'x', CLEANER = 'o';
	public static final int INF = Integer.MAX_VALUE;
	
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		while(true) {
			st = new StringTokenizer(br.readLine());
			// 가로 크기
			final int w = Integer.parseInt(st.nextToken());
			// 세로 크기
			final int h = Integer.parseInt(st.nextToken());
			
			// 0, 0이 입력되면 중단
			if(w == 0 && h == 0) break;
			
			// 좌표를 통해 더러운 위치의 Index 기록
			int[][] dirtyIndex = new int[h][w];
			for(int[] inner: dirtyIndex) {
				Arrays.fill(inner, -1);
			}
			
			// 방 상태 입력
			String input;
			int R = -1, C = -1, idx = 0;
			char[][] state = new char[h][w];
			for(int row = 0; row < h; row++) {
				input = br.readLine().trim();
				for(int col = 0; col < w; col++) {
					state[row][col] = input.charAt(col);
					if(state[row][col] == CLEANER) {
						// 청소기 시작 좌표 기록
						R = row;
						C = col;
						state[row][col] = CLEAN;
					} else if(state[row][col] == DIRTY) {
						// 더러운 위치 좌표 Index 기록
						dirtyIndex[row][col] = idx++;
					}
				}
			}
			
			final int FULL = (int) Math.pow(2, idx) - 1;
			// 각 위치에 도달하는 최소 횟수 저장
			int[][][] costDP = new int[h][w][FULL + 1];
			// 초기값 설정
			for(int row = 0; row < h; row++) {
				for(int col = 0; col < w; col++) {
					Arrays.fill(costDP[row][col], INF);
				}
			}
			// 시작 위치 비용 0
			costDP[R][C][0] = 0;
			// BFS로 최소 횟수 계산
			Queue<Coord> bfsQueue = new ArrayDeque<>();
			Coord curr = new Coord(R, C, 0);
			bfsQueue.offer(curr);
			int nextBit;
			while(!bfsQueue.isEmpty()) {
				curr = bfsQueue.poll();
				
				// 모든 위치를 청소했다면 중단
				if(curr.bitmask == FULL) break;
				
				for(int dir = 0; dir < 4; dir++) {
					R = curr.row + dRow[dir];
					C = curr.col + dCol[dir];
					
					// 범위를 벗어난 경우 생략
					if(!checkRange(R, h) || !checkRange(C, w)) continue;
					// 가구가 위치한 곳은 생략
					if(state[R][C] == FURNITURE) continue;
					
					nextBit = curr.bitmask;
					// 다음 위치가 더러운 곳이라면 비트마스크 추가
					if(state[R][C] == DIRTY) {
						idx = dirtyIndex[R][C];
						nextBit |= (1 << idx);
					}
					
					// 이동 횟수를 갱신할 수 있는 경우만 이동
					if(costDP[R][C][nextBit] > costDP[curr.row][curr.col][curr.bitmask] + 1) {
						costDP[R][C][nextBit] = costDP[curr.row][curr.col][curr.bitmask] + 1;
						bfsQueue.offer(new Coord(R, C, nextBit));
					}
				}
			}
			
			if(curr.bitmask == FULL) {
				bw.write(costDP[curr.row][curr.col][FULL] + "\n");
			} else {
				// 이동할 수 없는 더러운 칸이 있는 경우
				bw.write(-1 + "\n");
			}
		}
		
		bw.close();
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}

	public static class Coord {
		// 위치 행, 열
		int row, col;
		// 청소된 위치 상태
		int bitmask;
		
		public Coord(int row, int col, int bitmask) {
			super();
			this.row = row;
			this.col = col;
			this.bitmask = bitmask;
		}
	}
}
