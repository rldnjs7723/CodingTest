import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 17144번 미세먼지 안녕!
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_17144 {
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dRow = {0, -1, 0, 1};
	public static final int[] dCol = {-1, 0, 1, 0};
	// 시계방향 회전 순서
	public static final int[] clockwise = {DOWN, RIGHT, UP, LEFT};
	// 반시계방향 회전 순서
	public static final int[] counterClockwise = {UP, RIGHT, DOWN, LEFT};
	// 공기청정기는 항상 1열에 위치
	public static final int cleanerCol = 0;
	public static final int DIV = 5, CLEANER = -1, BLANK = 0;
	// 공기청정기 위, 아래 부분 행 위치
	public static int cleanerTop = -1, cleanerBottom = -1;
	public static int R, C, T;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 행 크기
		R = Integer.parseInt(st.nextToken());
		// 열 크기
		C = Integer.parseInt(st.nextToken());
		// 미세먼지의 양을 확인할 시간
		T = Integer.parseInt(st.nextToken());
		
		// 미세먼지 상태 입력
		int[][] state = new int[R][C];
		for(int row = 0; row < R; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < C; col++) {
				state[row][col] = Integer.parseInt(st.nextToken());
				if(state[row][col] == CLEANER && cleanerTop == -1) {
					// 공기청정기 위, 아래 부분 행 위치 입력
					cleanerTop = row;
					cleanerBottom = row + 1;
				}
			}
		}
		
		// 공기청정기 가동
		cleanAir(T, state);
		// 미세먼지 양 계산
		System.out.println(countDust(state));
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	// 디버깅용 배열 출력
	public static void printArr(int[][] arr) {
		for(int[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
		System.out.println("--------------");
	}
	
	// 미세먼지 양 카운트
	public static int countDust(int[][] state) {
		// 공기청정기 위치 정리
		state[cleanerBottom][cleanerCol] = BLANK;
		state[cleanerTop][cleanerCol] = BLANK;
		
		int result = 0;
		for(int[] inner: state) {
			for(int val: inner) {
				result += val;
			}
		}
		
		return result;
	}
	
	// 공기청정기 가동하기
	public static void cleanAir(int time, int[][] state) {
		for(int i = 0; i < time; i++) {
			// 미세먼지 확산
			diffuse(state);
			// 공기 정화
			clockwiseRotate(state);
			counterClockwiseRotate(state);
		}
	}
	
	// 미세먼지 확산
	public static void diffuse(int[][] state) {
		// 확산되는 미세먼지 양 계산
		int[][] diffuseAmount = new int[R][C];
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				diffuseAmount[row][col] = state[row][col] / DIV;
			}
		}
		
		// 미세먼지 확산
		int amount, r, c;
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				// 확산되는 양이 0이라면 생략
				amount = diffuseAmount[row][col];
				if(amount == 0) continue;
				
				for(int dir = 0; dir < 4; dir++) {
					r = row + dRow[dir];
					c = col + dCol[dir];
					
					// 범위를 벗어난 경우 확산되지 않음
					if(!checkRange(r, R) || !checkRange(c, C)) continue;
					// 공기 청정기 쪽으로는 확산되지 않음
					if(state[r][c] == CLEANER) continue;
					
					// 미세먼지 확산
					state[r][c] += amount;
					state[row][col] -= amount;
				}
			}
		}
	}
	
	// 시계 방향으로 배열 회전 (공기청정기 아래)
	public static void clockwiseRotate(int[][] state) {
		// 공기청정기에서는 깨끗한 공기가 나옴
		state[cleanerBottom][cleanerCol] = BLANK;
		
		Queue<Integer> rotateQueue = new ArrayDeque<>();
		// 배열을 돌릴 범위
		int WIDTH = C - 1, HEIGHT = R - cleanerBottom - 1;
		int[] count = {WIDTH, HEIGHT, WIDTH, HEIGHT};
		int r = cleanerBottom, c = cleanerCol;
		for(int dir: clockwise) {
			for(int i = 0; i < count[dir]; i++) {
				r += dRow[dir];
				c += dCol[dir];

				rotateQueue.offer(state[r][c]);
			}
		}
		
		// 회전
		rotateQueue.poll();
		// 공기청정기 위치 복원
		rotateQueue.offer(-1);
		
		// 회전 결과 반영
		r = cleanerBottom; c = cleanerCol;
		for(int dir: clockwise) {
			for(int i = 0; i < count[dir]; i++) {
				r += dRow[dir];
				c += dCol[dir];
				
				state[r][c] = rotateQueue.poll();
			}
		}
	}
	
	// 반시계 방향으로 배열 회전 (공기청정기 아래)
	public static void counterClockwiseRotate(int[][] state) {
		// 공기청정기에서는 깨끗한 공기가 나옴
		state[cleanerTop][cleanerCol] = BLANK;
		
		Queue<Integer> rotateQueue = new ArrayDeque<>();
		// 배열을 돌릴 범위
		int WIDTH = C - 1, HEIGHT = cleanerTop;
		int[] count = {WIDTH, HEIGHT, WIDTH, HEIGHT};
		int r = cleanerTop, c = cleanerCol;
		for(int dir: counterClockwise) {
			for(int i = 0; i < count[dir]; i++) {
				r += dRow[dir];
				c += dCol[dir];
				
				rotateQueue.offer(state[r][c]);
			}
		}
		
		// 회전
		rotateQueue.poll();
		// 공기청정기 위치 복원
		rotateQueue.offer(-1);
		
		// 회전 결과 반영
		r = cleanerTop; c = cleanerCol;
		for(int dir: counterClockwise) {
			for(int i = 0; i < count[dir]; i++) {
				r += dRow[dir];
				c += dCol[dir];
				
				state[r][c] = rotateQueue.poll();
			}
		}
	}
}
