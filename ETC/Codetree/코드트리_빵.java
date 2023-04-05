import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 삼성 SW 역량테스트 2022 하반기 오후 1번 문제
 * 문제 분류: BFS, 메모이제이션, 구현, 시뮬레이션
 * @author Giwon
 */
public class 코드트리_빵 {
	public static final int UP = 0, LEFT = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dRow = {-1, 0, 0, 1};
	public static final int[] dCol = {0, -1, 1, 0};
	public static final int BLANK = 0, CAMP = 1, BLOCKED = 2;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 격자 크기
		final int N = Integer.parseInt(st.nextToken());
		// 사람 수
		final int M = Integer.parseInt(st.nextToken());
		
		// 격자 정보 입력
		int[][] state = new int[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				state[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 편의점 정보 입력
		Coord[] store = new Coord[M + 1];
		int x, y;
		for(int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			// 행 좌표. 0부터 시작하도록 -1
			x = Integer.parseInt(st.nextToken()) - 1;
			// 열 좌표. 0부터 시작하도록 -1
			y = Integer.parseInt(st.nextToken()) - 1;
			
			store[i] = new Coord(x, y, 0); 
		}
		
		// 1분부터 시작
		int time = 1;
		// 움직일 사람 리스트
		List<Person> people = new ArrayList<>();
		do {
			// 1. 격자에 위치한 사람들 이동
			int nextDir;
			for(Person person: people) {
				nextDir = getNextDirection(person, person.store, state, N);
				person.move(nextDir);
			}
			
			// 2. 편의점에 도착한 경우 해당 위치 봉쇄
			Person person;
			for(int i = people.size() - 1; i >= 0; i--) {
				person = people.get(i);
				if(person.checkDone()) {
					state[person.row][person.col] = BLOCKED;
					// 이동할 리스트에서 제거
					people.remove(i);
				}
			}
			
			// 3. t <= m을 만족하는 경우 t번 사람 베이스 캠프로 이동
			Coord target, camp;
			if(time <= M) {
				target = store[time];
				// 현재 편의점에서 가장 가까운 캠프 탐색
				camp = getClosestCamp(target, state, N);
				// 사람 추가
				person = new Person(camp.row, camp.col, target);
				people.add(person);
				// 현재 위치 봉쇄
				state[person.row][person.col] = BLOCKED;
			}
			
			// 시간 증가
			time++;
		} while (!people.isEmpty());
		
		System.out.println(time - 1);
		br.close();
	}
	
	// 현재 위치에서 목표 지점으로 도달하는 최단 경로에서 바로 다음 이동할 방향 탐색
	public static int getNextDirection(Person person, Coord target, int[][] state, int N) {
		// 목표 지점까지 도달하기 위한 방향 기록용 배열
		int[][] dirs = new int[N][N];
		for(int[] inner: dirs) {
			Arrays.fill(inner, -1);
		}
		
		// BFS로 목표 위치까지 이동
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		bfsQueue.offer(new Coord(person.row, person.col, 0));
		Coord curr;
		int R, C;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			// 현재 위치가 목표 위치라면 중단
			if(curr.row == target.row && curr.col == target.col) break;
			
			for(int dir = 0; dir < 4; dir++) {
				R = curr.row + dRow[dir];
				C = curr.col + dCol[dir];
				
				// 범위를 벗어난 경우 생략
				if(!checkRange(R, N) || !checkRange(C, N)) continue;
				// 도달할 수 없는 곳은 생략
				if(state[R][C] == BLOCKED) continue;
				// 이미 방문한 곳은 생략
				if(dirs[R][C] != -1) continue;
				
				// 현재 위치에 가장 먼저 도달한 이전 방향 기록
				dirs[R][C] = dir;
				// 다음 위치 탐색
				bfsQueue.offer(new Coord(R, C, curr.depth + 1));
			}
		}
		
		// 목표 위치에서 역으로 시작 지점까지 돌아가기
		int dir = -1;
		R = target.row;
		C = target.col;
		while(R != person.row || C != person.col) {
			dir = dirs[R][C];
			R -= dRow[dir];
			C -= dCol[dir];
		}
		
		// 마지막 방향 리턴
		return dir;
	}
	
	// 편의점에서 가장 가까운 베이스캠프 위치 탐색
	public static Coord getClosestCamp(Coord store, int[][] state, int N) {
		// 방문 체크용
		boolean[][] visited = new boolean[N][N];
		
		// BFS로 캠프 위치 탐색
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		bfsQueue.offer(store);
		Coord curr;
		int R, C;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			// 현재 위치가 베이스 캠프라면 해당 위치 리턴
			if(state[curr.row][curr.col] == CAMP) {
				return new Coord(curr.row, curr.col, 0);
			}
			
			// 방향 순서가 위, 왼쪽, 오른쪽, 아래이므로 가장 먼저 만나는 Base Camp가 최단거리이고 행, 열도 가장 작음
			for(int dir = 0; dir < 4; dir++) {
				R = curr.row + dRow[dir];
				C = curr.col + dCol[dir];
				
				// 범위를 벗어난 경우 생략
				if(!checkRange(R, N) || !checkRange(C, N)) continue;
				// 도달할 수 없는 곳은 생략
				if(state[R][C] == BLOCKED) continue;
				// 이미 방문한 곳은 생략
				if(visited[R][C]) continue;
				
				// 방문 체크
				visited[R][C] = true;
				// 다음 위치 탐색
				bfsQueue.offer(new Coord(R, C, curr.depth + 1));
			}
		}
		
		return null;
	}
	
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}
	
	public static class Coord {
		int row, col;
		// 탐색 깊이
		int depth;
		
		public Coord(int row, int col, int depth) {
			this.row = row;
			this.col = col;
			this.depth = depth;
		}

		@Override
		public String toString() {
			return "Coord [row=" + row + ", col=" + col + ", depth=" + depth + "]";
		}
	}
	
	public static class Person {
		// 현재 위치
		int row, col;
		// 편의점 위치
		Coord store;
		
		public Person(int row, int col, Coord store) {
			this.row = row;
			this.col = col;
			this.store = store;
		}
		
		// 주어진 방향으로 이동
		public void move(int dir) {
			this.row += dRow[dir];
			this.col += dCol[dir];
		}
		
		// 목표한 편의점에 도착했는지 체크
		public boolean checkDone() {
			if(this.row == store.row && this.col == store.col) return true;
			else return false;
		}

		@Override
		public String toString() {
			return "Person [row=" + row + ", col=" + col + ", store=" + store + "]";
		}
	}

}
