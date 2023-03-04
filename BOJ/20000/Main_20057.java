import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 20057번 마법사 상어와 토네이도
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_20057 {
	public static final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	public static final int[] dRow = {0, 1, 0, -1};
	public static final int[] dCol = {-1, 0, 1, 0};

	// 모래가 퍼져나가는 범위
	public static final int RANGE = 5;
	// 모래가 퍼져나가는 위치와 행, 열 차이
	public static final int[][] dRowSand = {
			{-2, -2, -2, -2, -2},
			{-1, -1, -1, -1, -1},
			{0, 0, 0, 0, 0},
			{1, 1, 1, 1, 1},
			{2, 2, 2, 2, 2}
	};
	public static final int[][] dColSand = {
			{-2, -1, 0, 1, 2},
			{-2, -1, 0, 1, 2},
			{-2, -1, 0, 1, 2},
			{-2, -1, 0, 1, 2},
			{-2, -1, 0, 1, 2}
	};
	// 알파 좌표
	public static final int[] alphaRow = {2 + dRow[LEFT], 2 + dRow[DOWN], 2 + dRow[RIGHT], 2 + dRow[UP]};
	public static final int[] alphaCol = {2 + dCol[LEFT], 2 + dCol[DOWN], 2 + dCol[RIGHT], 2 + dCol[UP]};
	// 왼쪽으로 이동할 때 모래가 퍼져나가는 비율
	public static final double[][] LEFTRATIO = {
			{0, 0, 0.02, 0, 0},
			{0, 0.1, 0.07, 0.01, 0},
			{0.05, 0, 0, 0, 0},
			{0, 0.1, 0.07, 0.01, 0},
			{0, 0, 0.02, 0, 0}
	};
	// 4개 방향의 모래가 퍼져나가는 비율
	public static double[][][] SANDRATIO = new double[4][RANGE][RANGE];
	
	// 이동 횟수 증가 간격
	public static final double INTERVAL = 0.5;
	// 격자 크기
	public static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 모래가 퍼져나가는 비율 설정
		SANDRATIO[0] = LEFTRATIO;
		for(int dir = 1; dir < 4; dir++) {
			SANDRATIO[dir] = counterClockwiseRotate(SANDRATIO[dir - 1]);
		}
		
		// 격자 크기
		N = Integer.parseInt(br.readLine().trim());
		int totalSand = 0;
		// 격자 상태 입력
		int[][] state = new int[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				state[row][col] = Integer.parseInt(st.nextToken());
				// 전체 모래 양
				totalSand += state[row][col];
			}
		}
		
		// 토네이도 시작 행, 열, 방향
		int row = N / 2, col = N / 2, dir = LEFT;
		double count = 1.0;
		// 토네이도 이동
		while(!(row == 0 && col == 0)) {
			for(int i = 0; i < (int) count; i++) {
				row += dRow[dir];
				col += dCol[dir];

				// 확산
				spread(row, col, dir, state);
				
				// 왼쪽 위에 도착하면 종료
				if(row == 0 && col == 0) break;
			}
			
			// 이동 방향 변경
			dir = (dir + 1) % 4;
			// 이동 횟수 증가
			count += INTERVAL;
		}
		
		// 나간 모래의 양 계산
		System.out.println((totalSand - sandAmount(state)));
		br.close();
	}

	// 남아 있는 모래의 양 체크
	public static int sandAmount(int[][] state) {
		int sum = 0;
		for(int[] inner: state) {
			for(int amount: inner) {
				sum += amount;
			}
		}
		
		return sum;
	}
	
	// 모래 확산
	public static void spread(int row, int col, int dir, int[][] state) {
		int[][] amount = new int[RANGE][RANGE];
		int curr = state[row][col];
		// 알파로 남은 모래 전부 이동
		amount[alphaRow[dir]][alphaCol[dir]] = curr;
		
		int dR, dC, sand;
		double ratio;
		for(int r = 0; r < RANGE; r++) {
			for(int c = 0; c < RANGE; c++) {
				ratio = SANDRATIO[dir][r][c];
				// 모래가 확산되지 않는 영역은 생략
				if(ratio <= 0) continue;
				
				// 확산된 모래의 양 계산
				sand = (int) (curr * ratio);
				// 확산된 위치에 모래 추가
				amount[r][c] += sand;
				// 확산된 양만큼 알파 차감
				amount[alphaRow[dir]][alphaCol[dir]] -= sand;
			}
		}
		
		// 현재 위치의 모든 모래가 확산
		state[row][col] = 0;
		// 확산된 모래 양 반영
		for(int r = 0; r < RANGE; r++) {
			for(int c = 0; c < RANGE; c++) {
				dR = dRowSand[r][c];
				dC = dColSand[r][c];
				
				// 확산된 위치가 격자 밖이라면 생략
				if(!checkRange(row + dR) || !checkRange(col + dC)) continue;
				
				// 모래 추가
				state[row + dR][col + dC] += amount[r][c];
			}
		}
	}
	
	// 디버깅용 배열 출력
	public static void printArr(double[][] arr) {
		for(double[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
		System.out.println("------------");
	}
	
	// 방향 배열을 반시계 방향으로 90도 회전
	public static double[][] counterClockwiseRotate(double[][] arr) {
		double[][] result = new double[RANGE][RANGE];
		for(int row = 0; row < RANGE; row++) {
			for(int col = 0; col < RANGE; col++) {
				result[row][col] = arr[col][RANGE - 1 - row];
			}
		}
		
		return result;
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
}
