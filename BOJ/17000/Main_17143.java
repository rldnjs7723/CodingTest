import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 백준 17143번 낚시왕
 * 문제 분류: 구현, 시뮬레이션, 자료구조 (우선순위 큐)
 * @author Giwon
 */
public class Main_17143 {
	public static final int UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
	public static final int[] dRow = {0, -1, 1, 0, 0};
	public static final int[] dCol = {0, 0, 0, 1, -1};	
	public static final int[] reverse = {0, DOWN, UP, LEFT, RIGHT};
	public static int R, C;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 행 크기
		R = Integer.parseInt(st.nextToken());
		// 열 크기
		C = Integer.parseInt(st.nextToken());
		// 상어의 수
		int M = Integer.parseInt(st.nextToken());
		
		// 상어 입력
		Shark[][] state = new Shark[R][C];
		int r, c, s, d, z;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 상어 행 (1부터 시작)
			r = Integer.parseInt(st.nextToken()) - 1;
			// 상어 열 (1부터 시작)
			c = Integer.parseInt(st.nextToken()) - 1;
			// 상어 속력
			s = Integer.parseInt(st.nextToken());
			// 상어 이동 방향
			d = Integer.parseInt(st.nextToken());
			// 상어 크기
			z = Integer.parseInt(st.nextToken());
			
			state[r][c] = new Shark(r, c, s, d, z);
		}
		// 크기가 가장 큰 상어를 남기기 위한 우선순위 큐 생성
		Room[][] rooms = new Room[R][C];
		for(int row = 0; row < R; row++) {
			for(int col = 0; col < C; col++) {
				rooms[row][col] = new Room();
			}
		}
		
		// 낚시왕 이동
		int result = 0;
		for(int col = 0; col < C; col++) {
			for(int row = 0; row < R; row++) {
				// 현재 열에서 가장 위에 있는 상어 잡기
				if(state[row][col] != null) {
					result += state[row][col].z;
					state[row][col] = null;
					break;
				}
			}
			
			// 상어 이동
			Shark.move(state, rooms);
		}
		
		System.out.println(result);
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	// 같은 공간에 위치한 상어 중 하나만 남기기 위한 방
	@SuppressWarnings("serial")
	public static class Room extends PriorityQueue<Shark> {}
	
	// 상어 클래스
	public static class Shark implements Comparable<Shark> {
		int r, c, s, d, z;
		
		public Shark(int r, int c, int s, int d, int z) {
			// 상어 행
			this.r = r;
			// 상어 열
			this.c = c;
			// 상어 속력
			// 각각 (행/열 크기 - 1) * 2 만큼 이동하면 제자리로 이동한 것이므로 나머지 연산
			if(d == UP || d == DOWN) {
				this.s = s % (2 * (R - 1));
			} else if(d == LEFT || d == RIGHT) {
				this.s = s % (2 * (C - 1));
			}
			// 상어 이동 방향
			this.d = d;
			// 상어 크기
			this.z = z;
		}
		
		// 상어 크기 내림차순 정렬
		@Override
		public int compareTo(Shark o) {
			return Integer.compare(o.z, this.z);
		}
		
		// 상어 이동
		public static void move(Shark[][] state, Room[][] rooms) {
			Shark curr;
			int r, c, count;
			for(int row = 0; row < R; row++) {
				for(int col = 0; col < C; col++) {
					// 상어가 현재 행, 열에 없다면 생략
					if(state[row][col] == null) continue;
					curr = state[row][col];
					
					count = 0;
					// 총 이동 횟수를 채울 때까지 계속 이동
					while(count < curr.s) {
						r = curr.r + dRow[curr.d];
						c = curr.c + dCol[curr.d];
						
						if(checkRange(r, R) && checkRange(c, C)) {
							// 다음 위치가 유효한 범위라면 이동
							curr.r = r;
							curr.c = c;
							count++;
						} else {
							// 다음 위치가 유효한 범위가 아니라면 방향을 바꾸기
							curr.d = reverse[curr.d];
						}
					}
					
					// 방에 상어 추가
					rooms[curr.r][curr.c].offer(curr);
				}
			}
			
			// 우선순위 큐로 하나의 상어만 남기기
			for(int row = 0; row < R; row++) {
				for(int col = 0; col < C; col++) {
					if(rooms[row][col].size() == 0) state[row][col] = null;
					else {
						state[row][col] = rooms[row][col].poll();
						rooms[row][col].clear();
					}
				}
			}
		}
		
		// 디버깅용
		@Override
		public String toString() {
			return Integer.toString(this.z);
		}
		public static void printArr(Shark[][] state) {
			for(Shark[] inner: state) {
				System.out.println(Arrays.toString(inner));
			}
			System.out.println("---------------------");
		}
	}
}
