import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;


/**
 * 백준 23290번 마법사 상어와 복제
 * 문제 분류: 구현, 시뮬레이션, 완전 탐색
 * @author Giwon
 */
public class Main_23290 {
	// 8방 탐색. 1 ~ 8으로 입력
	public static final int SW = 0, W = 1, NW = 2, N = 3, NE = 4, E = 5, SE = 6, S = 7;
	public static final int[] dRow = {1, 0, -1, -1, -1, 0, 1, 1};
	public static final int[] dCol = {-1, -1, -1, 0, 1, 1, 1, 0};
	// 4방 탐색. 상어 이동 사전순
	public static final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
	public static final int[] dRowShark = {-1, 0, 1, 0};
	public static final int[] dColShark = {0, -1, 0, 1};	
	// 냄새 지속 시간 (두 번 후 연습까지 유효)
	public static final int smellDuration = 3;
	public static final int maxMove = 3;
	// 격자 크기 4 x 4
	public static final int SIZE = 4;
	// 상어 위치
	public static int sRow, sCol;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 물고기의 수
		final int M = Integer.parseInt(st.nextToken());
		// 마법 연습 횟수
		final int S = Integer.parseInt(st.nextToken());
		
		// 물고기 상태 초기화
		Fish[][] state = new Fish[SIZE][SIZE];
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				state[row][col] = new Fish(row, col);
			}
		}
		
		// 물고기 위치, 방향 입력
		int row, col, dir;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 행
			row = Integer.parseInt(st.nextToken()) - 1;
			// 열
			col = Integer.parseInt(st.nextToken()) - 1;
			// 방향
			dir = Integer.parseInt(st.nextToken());
			
			state[row][col].addFish(dir);
		}
		
		// 상어 위치 입력
		st = new StringTokenizer(br.readLine());
		sRow = Integer.parseInt(st.nextToken()) - 1;
		sCol = Integer.parseInt(st.nextToken()) - 1;
		state[sRow][sCol].shark = true;
		
		// S번 마법 연습
		for(int i = 0; i < S; i++) {
			// 복제 마법 시전
			copyFish(state);
			// 물고기 이동
			moveFish(state);
			// 상어 이동
			applyShark(state);
			// 냄새 제거
			removeSmell(state);
			// 복제 마법 완료
			applyCopy(state);
		}
		
		// 남아 있는 모든 물고기 수 출력
		System.out.println(getFishAmount(state));
		br.close();
	}
	
	// 남아 있는 모든 물고기 수 리턴
	public static int getFishAmount(Fish[][] state) {
		int sum = 0;
		for(Fish[] inner: state) {
			for(Fish fish: inner) {
				sum += fish.getFinal();
			}
		}
		
		return sum;
	}
	
	// 1. 모든 물고기에 복제 마법 시전
	public static void copyFish(Fish[][] state) {
		for(Fish[] inner: state) {
			for(Fish fish: inner) {
				fish.copyFish();
			}
		}
	}
	
	// 2. 모든 물고기 한 칸 이동
	public static void moveFish(Fish[][] state) {
		for(Fish[] inner: state) {
			for(Fish fish: inner) {
				fish.move(state);
			}
		}
	}

	// 3. 상어가 연속하여 3칸 이동 후 제거한 물고기 수 리턴
	public static int applyShark(Fish[][] state) {
		Queue<SharkMove> moves = new PriorityQueue<>();
		// 완전 탐색으로 최적의 이동 체크
		moveShark(new SharkMove(sRow, sCol), 0, state, moves);
		SharkMove optimal = moves.poll();
		
		// 상어 이동
		state[sRow][sCol].shark = false;
		int sum = 0, amount;
		for(int dir: optimal.dirs) {
			sRow += dRowShark[dir];
			sCol += dColShark[dir];
			
			amount = state[sRow][sCol].removeFish();
			sum += amount;
		}
		state[sRow][sCol].shark = true;
		
		return sum;
	}
	
	/**
	 * 상어의 최적 움직임을 중복 가능한 순열 완전 탐색으로 체크
	 * @param curr		현재까지의 상어 움직임
	 * @param bitmask	방문 체크를 위한 비트마스크
	 * @param state		물고기 위치 상태
	 * @param moves		상어 움직임 정렬을 위한 우선순위 큐
	 */
	public static void moveShark(SharkMove curr, int bitmask, Fish[][] state, Queue<SharkMove> moves) {
		// 상어 이동 횟수가 3번이 되었다면 우선순위 큐로 정렬
		if(curr.idx == maxMove) {
			moves.offer(curr);
			return;
		}
		
		int r, c;
		Fish currFish;
		for(int dir = 0; dir < 4; dir++) {
			r = curr.row + dRowShark[dir];
			c = curr.col + dColShark[dir];
			
			// 격자를 벗어나면 다음 방향 탐색
			if(!checkRange(r) || !checkRange(c)) continue;
			
			currFish = state[r][c];
			// 이미 방문한 행, 열은 물고기 수를 추가하지 않음
			if((bitmask & getBitmask(r, c)) > 0) {
				moveShark(new SharkMove(curr, 0, dir), bitmask, state, moves);
			} else {
				moveShark(new SharkMove(curr, currFish.getAmount(), dir), bitmask | getBitmask(r, c), state, moves);
			}
		}
	}
	
	// 방문 체크를 위한 비트마스크 계산
	public static int getBitmask(int row, int col) {
		return 1 << (row * SIZE + col);
	}
	
	// 4. 두 번 전 연습에서 생긴 물고기 냄새 없애기
	public static void removeSmell(Fish[][] state) {
		for(Fish[] inner: state) {
			for(Fish fish: inner) {
				fish.removeSmell();
			}
		}
	}
	
	// 5. 1에서 사용한 복제 마법 완료하기
	public static void applyCopy(Fish[][] state) {
		for(Fish[] inner: state) {
			for(Fish fish: inner) {
				fish.applyCopy();
			}
		}
	}
		
	// 45도 반시계 회전한 방향
	public static int nextDirection(int dir) {
		dir--;
		if(dir < 0) dir += 8;
		return dir;
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < SIZE;
	}
	
	public static class SharkMove implements Comparable<SharkMove> {
		// 마지막 상어 위치
		int row, col;
		// 상어가 제거한 물고기 수 기록
		int size;
		// 상어가 이동한 방향 기록
		int[] dirs;
		// 상어 이동 방향 기록 인덱스
		int idx;

		public SharkMove(int row, int col) {
			this.row = row;
			this.col = col;
			this.size = 0;
			this.dirs = new int[maxMove];
			this.idx = 0;
		}
		
		// 이전 이동을 기준으로 방향 이동
		public SharkMove(SharkMove prev, int size, int dir) {
			this.row = prev.row + dRowShark[dir];
			this.col = prev.col + dColShark[dir];
			this.size = prev.size + size;
			this.dirs = new int[maxMove];
			this.idx = prev.idx;
			
			for(int i = 0; i < prev.idx; i++) {
				this.dirs[i] = prev.dirs[i];
			}
			
			this.dirs[idx++] = dir;
		}
		
		// 우선순위 큐로 자동 정렬
		@Override
		public int compareTo(SharkMove o) {
			// 제거한 물고기 수에 따라 내림차순 정렬
			if(this.size != o.size) return Integer.compare(o.size, this.size);
			// 이동한 방향에 따라 오름차순 정렬
			if(this.dirs[0] != o.dirs[0]) return Integer.compare(this.dirs[0], o.dirs[0]);
			if(this.dirs[1] != o.dirs[1]) return Integer.compare(this.dirs[1], o.dirs[1]);
			if(this.dirs[2] != o.dirs[2]) return Integer.compare(this.dirs[2], o.dirs[2]);
			
			return 0;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int dir: dirs) {
				sb.append(dir);
			}
			
			return "[ move: " + sb.toString() + " , amount: " + this.size + " ]";
		}
	}
	
	public static class Fish {
		int row, col;
		// 각 방향 별 물고기 마리 수 저장
		int[] amount;
		// 복제 마법으로 추가할 물고기 수 저장
		int[] copy;
		// 현재 마법이 종료되면 위치할 물고기 마리 수 저장
		int[] temp;
		// 상어 존재 여부
		boolean shark;
		// 물고기 냄새 존재 여부
		int smell;
		
		public Fish(int row, int col) {
			this.row = row;
			this.col = col;
			this.amount = new int[8];
			this.copy = new int[8];
			this.temp = new int[8];
			this.shark = false;
			this.smell = 0;
		}
		
		// 물고기 위치 입력
		public void addFish(int dir) {
			// 1 ~ 8로 입력된 방향을 0 ~ 7로 변환
			dir %= 8;
			this.amount[dir]++;
		}
		
		// 현재 행, 열에 최종적으로 존재하는 물고기 전체 마리 수 리턴
		public int getFinal() {
			int sum = 0;
			for(int val: amount) {
				sum += val;
			}
			
			return sum;
		}
		
		// 현재 행, 열에 존재하는 물고기 전체 마리 수 리턴
		public int getAmount() {
			int sum = 0;
			for(int val: temp) {
				sum += val;
			}
			
			return sum;
		}
		
		// 1. 모든 물고기에게 복제 마법 시전
		public void copyFish() {
			for(int dir = 0; dir < 8; dir++) {
				this.copy[dir] = this.amount[dir]; 
			}
		}
		
		// 2. 모든 물고기가 한 칸 이동
		public void move(Fish[][] state) {
			boolean success = false;
			int r, c, d;
			for(int dir = 0; dir < 8; dir++) {
				d = dir;
				
				do {
					r = this.row + dRow[d];
					c = this.col + dCol[d];
					
					// 격자의 범위를 벗어나지 않고
					// 상어가 존재하지 않으며
					// 물고기의 냄새가 없다면 이동
					if(checkRange(r) && checkRange(c) &&
							!state[r][c].shark &&
							state[r][c].smell <= 0) {
						state[r][c].temp[d] += this.amount[dir];
						success = true;
						break;
					}
					
					// 다음 방향 탐색
					d = nextDirection(d);
					
				} while (d != dir);
				
				// 다음에 이동할 방향을 찾지 못 했다면 남은 모든 물고기도 방향을 찾지 못하는 경우
				if(!success) {
					// 제자리에 머무르기
					for(int i = 0; i < 8; i++) {
						this.temp[dir] = this.amount[dir]; 
					}
					return;
				}
			}
		}
		
		// 3. 현재 행, 열에 위치한 모든 물고기 제거
		public int removeFish() {
			int sum = 0;
			for(int dir = 0; dir < 8; dir++) {
				sum += this.temp[dir];
				this.temp[dir] = 0;
			}
			
			// 제외되는 모든 물고기는 냄새를 남김
			if(sum > 0) this.smell = smellDuration;
			return sum;
		}
		
		// 4. 현재 위치에서의 물고기 냄새 없애기
		public void removeSmell() {
			if(smell > 0) smell--;
		}
		
		// 5. 1에서 사용한 복제 마법 완료
		public void applyCopy() {
			for(int dir = 0; dir < 8; dir++) {
				this.amount[dir] = this.temp[dir] + this.copy[dir];
				this.temp[dir] = 0;
				this.copy[dir] = 0;
			}
		}
	}
}
