import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 21610번 마법사 상어와 비바라기
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_21610 {
	// 8방 탐색. 방향은 1 ~ 8까지 입력
	public static final int SW = 0, W = 1, NW = 2, N = 3, NE = 4, E = 5, SE = 6, S = 7;
	public static final int[] dRow = {1, 0, -1, -1, -1, 0, 1, 1};
	public static final int[] dCol = {-1, -1, -1, 0, 1, 1, 1, 0};
	// 대각선 방향
	public static final int[] DIAGONAL = {SW, NW, NE, SE};
	// 격자 크기
	public static int SIZE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 격자 크기
		SIZE = Integer.parseInt(st.nextToken());
		// 이동 명령 수
		int M = Integer.parseInt(st.nextToken());
		
		// 물 저장량 입력
		int[][] state = new int[SIZE][SIZE];
		for(int row = 0; row < SIZE; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < SIZE; col++) {
				state[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 시작 구름 설정
		List<Cloud> clouds = new ArrayList<>();
		clouds.add(new Cloud(SIZE - 1, 0));
		clouds.add(new Cloud(SIZE - 1, 1));
		clouds.add(new Cloud(SIZE - 2, 0));
		clouds.add(new Cloud(SIZE - 2, 1));
		
		// 명령 입력
		int d, s;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 방향
			d = Integer.parseInt(st.nextToken());
			// 거리
			s = Integer.parseInt(st.nextToken());
			
			// 마법 시전
			magic(d, s, clouds, state);
		}
		
		// 남은 물의 양 계산
		System.out.println(countWater(state));
		br.close();
	}

	// 남의 물의 양 계산
	public static int countWater(int[][] state) {
		int sum = 0;
		for(int[] inner: state) {
			for(int amount: inner) {
				sum += amount;
			}
		}
		
		return sum;
	}
	
	// 마법 시전
	public static void magic(int d, int s, List<Cloud> clouds, int[][] state) {
		boolean[][] visited = new boolean[SIZE][SIZE];

		for(Cloud cloud: clouds) {
			// 구름 이동
			cloud.move(d, s);
			// 비 내리기
			state[cloud.row][cloud.col]++;
			// 구름이 사라진 위치 기록
			visited[cloud.row][cloud.col] = true;
		}
		
		// 물복사버그로 증가한 물의 양
		int[][] amount = new int[SIZE][SIZE];
		int r, c;
		for(Cloud cloud: clouds) {
			// 대각선 방향을 탐색하여 물복사버그 수행
			for(int dir: DIAGONAL) {
				r = cloud.row + dRow[dir];
				c = cloud.col + dCol[dir];
				
				// 경계를 넘은 경우는 세지 않음
				if(!checkRange(r) || !checkRange(c)) continue;
				
				// 물이 존재한 수만큼 추가
				if(state[r][c] > 0) amount[cloud.row][cloud.col]++;
			}
		}
		// 증가한 물의 양 반영
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				state[row][col] += amount[row][col];
			}
		}
		
		// 구름 생성
		clouds.clear();
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				// 구름이 사라졌던 칸은 생략
				if(visited[row][col]) continue;
				
				// 물의 양이 2 이상이면 구름 생성
				if(state[row][col] >= 2) {
					state[row][col] -= 2;
					clouds.add(new Cloud(row, col));
				}
			}
		}
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < SIZE;
	}
	
	// 배열 출력
	public static void printArr(int[][] state) {
		for(int[] inner: state) {
			System.out.println(Arrays.toString(inner));
		}
		System.out.println("--------------");
	}
	
	public static class Cloud {
		int row, col;
		
		public Cloud(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		// 구름 이동
		public void move(int d, int s) {
			// 1 ~ 8까지의 방향을 0 ~ 7로 변경
			d %= 8;

			// 입력 받은 속도만큼 이동 
			this.row = (this.row + dRow[d] * s) % SIZE;
			this.col = (this.col + dCol[d] * s) % SIZE;
			// 경계를 넘어서 반대로 이동 가능
			if(this.row < 0) this.row += SIZE;
			if(this.col < 0) this.col += SIZE;
		}
	}
}
