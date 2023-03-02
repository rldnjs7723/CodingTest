import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 5656번 벽돌 깨기
 * 문제 분류: 구현, 시뮬레이션, 완전 탐색
 * @author Giwon
 */
public class Solution_5656 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	public static final int BLANK = 0; 
	public static int N, H, W;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 테스트 케이스 수
		final int T = Integer.parseInt(br.readLine().trim());
		int[][] state;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 사용할 수 있는 벽돌 개수
			N = Integer.parseInt(st.nextToken());
			// 격자 너비
			W = Integer.parseInt(st.nextToken());
			// 격자 높이
			H = Integer.parseInt(st.nextToken());
			
			// 전체 벽돌 수
			int count = 0;
			// 벽돌 상태 입력
			state = new int[H][W];
			for(int row = 0; row < H; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < W; col++) {
					state[row][col] = Integer.parseInt(st.nextToken());
					if(state[row][col] > BLANK) count++; 
				}
			}
			
			// 전체 벽돌 수에서 최대한 부순 벽돌 수를 빼서 결과 출력
			System.out.println("#" + test_case + " " + (count - permutation(0, new Brick(state))));
		}
		
		br.close();
	}
	
	// 중복 가능한 순열로 모든 경우의 수를 탐색
	public static int permutation(int iter, Brick curr) {
		// 사용할 수 있는 구슬을 모두 사용했다면 부순 벽돌 수 리턴
		if(iter == N) return curr.count;
		
		int max = 0;
		// 모든 열을 탐색하여 부술 수 있는 벽돌 수 확인
		for(int col = 0; col < W; col++) {
			max = Math.max(max, permutation(iter + 1, new Brick(col, curr)));
		}
		
		return max;
	}
	
	// 빈 공간이 위치하지 않도록 블록을 아래로 당기기
	public static void gravity(int[][] state) {
		int curr, r;
		
		for(int row = H - 2; row >= 0; row--) {
			for(int col = 0; col < W; col++) {
				curr = state[row][col];
				// 아랫 부분에서 더 이상 빈 칸이 아닌 곳까지 탐색
				r = row + 1;
				for(r = row + 1; r < H; r++) {
					if(state[r][col] != BLANK) break;
				}
				
				state[row][col] = BLANK;
				state[r - 1][col] = curr;
			}
		}
	}
	
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static class Brick {
		int count;
		int[][] state;
		
		public Brick(int[][] state) {
			this.state = arrayCopy(state);
			this.count = 0;
		}
		
		public Brick(int col, Brick prev) {
			this.state = arrayCopy(prev.state);
			this.count = destroy(col) + prev.count;
		}
		
		// 벽돌 부수기
		public int destroy(int col) {
			// 부순 벽돌 수
			int count = 0;
			int row = 0;
			for(row = 0; row < H; row++) {
				if(state[row][col] != BLANK) break;
			}
			// 해당 열에 부술 벽돌이 없다면 0개
			if(row == H) return 0;
			
			// 부술 벽돌이 있다면 BFS를 통해 연쇄적으로 부수기
			Coord curr = new Coord(row, col);
			Queue<Coord> queue = new ArrayDeque<>();
			queue.offer(curr);
			int range, r, c;
			while(!queue.isEmpty()) {
				curr = queue.poll();
				range = state[curr.row][curr.col] - 1;
				// 이미 빈 칸이라면 생략
				if(range < 0) continue;
				// 현재 위치 부수기
				state[curr.row][curr.col] = BLANK;
				// 부순 블록 개수 추가
				count++;
				
				// 네 방향에 위치한 모든 블록 부수기
				for(int dir = 0; dir < 4; dir++) {
					r = curr.row;
					c = curr.col;
					for(int i = 0; i < range; i++) {
						r += dRow[dir];
						c += dCol[dir];
						
						if(checkRange(r, H) && checkRange(c, W)) {
							// 현재 위치가 아직 부숴지지 않았다면 대기열에 추가
							if(state[r][c] > BLANK) {
								queue.offer(new Coord(r, c));
							}
						} else {
							// 범위에서 벗어났다면 중단
							break;
						}
					}
				}
			}
			
			// 모든 벽돌을 부쉈다면 아래로 내리기
			gravity(state);
			return count;
		}
	}
	
	// 배열 복사
	public static int[][] arrayCopy(int[][] arr) {
		int[][] copy = new int[H][W];
		for(int i = 0; i < H; i++) {
			copy[i] = Arrays.copyOf(arr[i], W);
		}
		return copy;
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
		System.out.println("-------------------");
	}
}
