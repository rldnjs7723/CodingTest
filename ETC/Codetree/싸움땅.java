import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 삼성 SW 역량테스트 2022하반기 오전 1번 문제
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class 싸움땅 {
	public static final int CLOCKWISE = 0, REVERSE = 1;
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	public static final int[] dRow = {-1, 0, 1, 0};
	public static final int[] dCol = {0, 1, 0, -1};
	// 격자 크기
	public static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		// 플레이어 수
		final int M = Integer.parseInt(st.nextToken());
		// 라운드 수
		final int K = Integer.parseInt(st.nextToken());
		
		// 격자 상태 입력
		State[][] state = new State[N][N];
		Gun gun;
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				state[row][col] = new State();
				// 총 추가
				gun = new Gun(Integer.parseInt(st.nextToken()));
				state[row][col].gunQueue.offer(gun);
			}
		}
		
		// 플레이어 입력
		List<Player> players = new ArrayList<>();
		Player player;
		int x, y, d, s;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 행 좌표 (0부터 시작하도록 설정)
			x = Integer.parseInt(st.nextToken()) - 1;
			// 열 좌표 (0부터 시작하도록 설정)
			y = Integer.parseInt(st.nextToken()) - 1;
			// 초기 방향
			d = Integer.parseInt(st.nextToken());
			// 초기 능력치
			s = Integer.parseInt(st.nextToken());
			player = new Player(i, x, y, d, s);
			
			// 리스트에 추가
			players.add(player);
			// 격자에 추가
			state[x][y].player = player;
		}
		
		// 라운드 진행
		for(int round = 0; round < K; round++) {
			for(Player p: players) {
				p.move(state);
			}
		}
		
		// 플레이어 포인트 출력
		StringBuilder sb = new StringBuilder();
		for(Player p: players) {
			sb.append(p.point + " ");
		}
		
		System.out.println(sb.toString());
		br.close();
	}
	
	// 디버깅용 배열 출력
	public static void printArr(State[][] state) {
		for(State[] inner: state) {
			System.out.println(Arrays.toString(inner));
		}
		System.out.println("---------------");
	}
	
	// 현재 좌표 총, 플레이어 상태
	public static class State {
		// 총 상태
		Queue<Gun> gunQueue;
		// 현재 좌표에 위치한 플레이어
		Player player;
		
		public State() {
			this.gunQueue = new PriorityQueue<>();
			this.player = null;
		}

		@Override
		public String toString() {
			return "State [player=" + player + "]";
		}
	}
	
	public static class Gun implements Comparable<Gun> {
		int damage;
		
		public Gun(int damage) {
			this.damage = damage;
		}

		// 공격력 내림차순으로 정렬
		@Override
		public int compareTo(Gun o) {
			return Integer.compare(o.damage, this.damage);
		}

		@Override
		public String toString() {
			return "Gun [damage=" + damage + "]";
		}
	}
	
	public static class Player {
		public static final int WIN = 0, LOSE = 1;
		public static final Gun UNEQUIP = new Gun(0);
		
		// 플레이어 번호
		int idx;
		// 플레이어 좌표
		int row, col;
		// 이동 방향
		int dir;
		// 초기 능력치
		int attr;
		// 현재 가지고 있는 총
		Gun gun;
		// 현재까지 얻은 포인트
		int point;
		
		public Player(int idx, int row, int col, int dir, int attr) {
			this.idx = idx;
			this.row = row;
			this.col = col;
			this.dir = dir;
			this.attr = attr;
			this.point = 0;
			// 처음에는 총이 없음
			this.gun = UNEQUIP;
		}
		
		public void move(State[][] state) {
			int R = row + dRow[dir];
			int C = col + dCol[dir];
			
			// 1-1. 범위를 벗어나면 반대 방향으로 이동
			if(!checkRange(R) || !checkRange(C)) {
				rotate(REVERSE);
				R = row + dRow[dir];
				C = col + dCol[dir];
			}
			
			// 1-1. 본인이 향하고 있는 방향대로 한 칸 이동
			state[row][col].player = null;
			row = R;
			col = C;
			
			// 2-2-1. 이동한 위치에 적이 있다면 싸우기
			if(state[row][col].player != null) {
				Player[] fightResult = fight(state[R][C].player);
				// 진 플레이어 도망
				fightResult[LOSE].flee(state);
				
				state[row][col].player = fightResult[WIN];
				// 2-2-3. 이긴 플레이어는 가장 좋은 총 가지기
				fightResult[WIN].getBestGun(state);
			}
			
			// 2-1. 플레이어가 없다면 현재 위치에서 가장 좋은 총 집기
			getBestGun(state);
			// 현재 위치에 좌표 등록
			state[row][col].player = this;
		}
		
		// 진 플레이어 도망
		public void flee(State[][] state) {
			// 2-2-2. 진 플레이어는 현재 위치에 총 내려 놓기
			dropGun(state);
			
			int R = row + dRow[dir];
			int C = col + dCol[dir];
			// 2-2-2. 다음 위치가 빈 칸일 때까지 시계 방향으로 회전
			while(!checkRange(R) || !checkRange(C) || state[R][C].player != null) {
				rotate(CLOCKWISE);
				R = row + dRow[dir];
				C = col + dCol[dir];
			}
			
			// 이동
			row = R;
			col = C;
			state[row][col].player = this;
			// 2-2-2. 도망친 칸에 총이 있다면 가장 좋은 총 획득
			getBestGun(state);
		}
		
		// 현재 위치에서 가장 좋은 총 가져가기
		public void getBestGun(State[][] state) {
			state[row][col].gunQueue.offer(gun);
			gun = state[row][col].gunQueue.poll();
		}
		
		// 현재 위치에 총 내려놓기
		public void dropGun(State[][] state) {
			state[row][col].gunQueue.offer(gun);
			gun = UNEQUIP;
		}
		
		// 두 플레이어 전투. 승자는 0번, 패자는 1번에 입력
		public Player[] fight(Player opponent) {
			int point = Math.abs(this.attr + this.gun.damage - opponent.attr - opponent.gun.damage);
			
			// 2-2-1. 초기 능력치 + 총의 공격력으로 승패 결정
			if(this.attr + this.gun.damage > opponent.attr + opponent.gun.damage) {
				// 승자 포인트 추가
				this.point += point;
				return new Player[] {this, opponent};
			} else if(this.attr + this.gun.damage < opponent.attr + opponent.gun.damage) {
				// 승자 포인트 추가
				opponent.point += point;
				return new Player[] {opponent, this};
			} else {
				// 두 사람의 전투력이 같은 경우 기본 능력치가 높은 사람이 승
				if(this.attr >= opponent.attr) {
					return new Player[] {this, opponent};
				} else {
					return new Player[] {opponent, this};
				}
			}
		}
		
		// 이동 방향 회전
		public void rotate(int type) {
			// 오른쪽으로 90도 회전
			if(type == CLOCKWISE) {
				dir = (dir + 1) % 4;
			}
			// 반대로 회전
			else if(type == REVERSE) {
				dir = (dir + 2) % 4;
			}
		}
		
		// 범위 체크
		public static boolean checkRange(int target) {
			return 0 <= target && target < N;
		}

		@Override
		public String toString() {
			return "Player [idx=" + idx + ", dir=" + dir + ", attr=" + attr + ", gun=" + gun + ", point=" + point + "]";
		}
	}

}
