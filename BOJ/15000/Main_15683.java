import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 15683번 감시
 * 문제 분류: 완전 탐색, 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_15683 {
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dRow = {0, -1, 0, 1};
	public static final int[] dCol = {-1, 0, 1, 0};
	public static final int BLANK = 0, WALL = 6;
	// CCTV 타입에 따른 감시 방향 체크
	public static final int[][][] monitor = {{},
			// 1번 카메라
			{{LEFT}, {UP}, {RIGHT}, {DOWN}},
			// 2번 카메라
			{{LEFT, RIGHT}, {UP, DOWN}},
			// 3번 카메라
			{{LEFT, UP}, {UP, RIGHT}, {RIGHT, DOWN}, {DOWN, LEFT}},
			// 4번 카메라
			{{DOWN, LEFT, UP}, {LEFT, UP, RIGHT}, {UP, RIGHT, DOWN}, {RIGHT, DOWN, LEFT}},
			// 5번 카메라
			{{LEFT, UP, RIGHT, DOWN}}
	};
	public static int N, M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 행 크기
		N = Integer.parseInt(st.nextToken());
		// 열 크기
		M = Integer.parseInt(st.nextToken());
		// CCTV는 최대 8개
		List<CCTV> cctv = new ArrayList<>();
		// 사무실 상태 입력
		int[][] state = new int[N][M];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < M; col++) {
				state[row][col] = Integer.parseInt(st.nextToken());
				if(state[row][col] > BLANK && state[row][col] < WALL) cctv.add(new CCTV(row, col, state[row][col]));
			}
		}
		
		System.out.println(minBlindSpot(0, cctv, state));
		br.close();
	}
	
	// DFS로 각 CCTV 회전 상태 바꿔가면서 최소 사각지대 개수 체크
	public static int minBlindSpot(int idx, List<CCTV> cctv, int[][] state) {
		// 모든 CCTV의 방향을 정했으면 사각지대 개수 확인
		if(idx == cctv.size()) return checkBlindSpot(state);
		
		//각 CCTV 방향 바꿔가며 DFS 탐색
		int min = Integer.MAX_VALUE;
		CCTV curr = cctv.get(idx);
		for(int dir = 0; dir < monitor[curr.type].length; dir++) {
			// 범위 표시
			monitoring(idx, dir, cctv, state);
			// 다음 CCTV 방향 설정 후 탐색 수행
			min = Math.min(min, minBlindSpot(idx + 1, cctv, state));
			// 탐색 끝나면 롤백
			rollback(idx, dir, cctv, state);
		}
		
		return min;
	}
	
	/**
	 * CCTV 감시 영역 표시
	 * @param idx		cctv index
	 * @param dir		바라보는 방향
	 * @param cctv		cctv 개수
	 * @param state
	 */
	public static void monitoring(int idx, int dir, List<CCTV> cctv, int[][] state) {
		CCTV curr = cctv.get(idx);
		// 현재 카메라 방향, 종류에 따라 감시 범위 표시
		int checker = getChecker(idx);
		int r, c;
		for(int d: monitor[curr.type][dir]) {
			r = curr.row + dRow[d];
			c = curr.col + dCol[d];
			
			// CCTV는 벽 통과 불가능, 다른 CCTV는 통과 가능
			while(checkRange(r, N) && checkRange(c, M) && state[r][c] != WALL) {
				if(state[r][c] == BLANK) state[r][c] = checker;
				
				r += dRow[d];
				c += dCol[d];
			}
		}
	}
	
	public static void rollback(int idx, int dir, List<CCTV> cctv, int[][] state) {
		CCTV curr = cctv.get(idx);
		// 현재 카메라 방향, 종류에 따라 감시 범위 롤백
		int checker = getChecker(idx);
		int r, c;
		for(int d: monitor[curr.type][dir]) {
			r = curr.row + dRow[d];
			c = curr.col + dCol[d];
			
			// CCTV는 벽 통과 불가능, 다른 CCTV는 통과 가능
			while(checkRange(r, N) && checkRange(c, M) && state[r][c] != WALL) {
				if(state[r][c] == checker) state[r][c] = BLANK;
				
				r += dRow[d];
				c += dCol[d];
			}
		}
	}
	
	// 사각 지대 개수 카운트
	public static int checkBlindSpot(int[][] state) {
		int count = 0;
		for(int[] inner: state) {
			for(int val: inner) {
				if(val == BLANK) count++;
			}
		}
		
		return count;
	}
	

	// CCTV 위치 저장용 클래스
	public static class CCTV {
		int row, col, type;
		
		public CCTV(int row, int col, int type) {
			this.row = row;
			this.col = col;
			this.type = type;
		}
	}
	
	public static int getChecker(int idx) {
		return idx + 7;
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
}
