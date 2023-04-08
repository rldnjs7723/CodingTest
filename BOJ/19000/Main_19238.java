import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 19238번 스타트 택시
 * 문제 분류: 구현, 시뮬레이션, BFS, 자료구조(우선순위 큐)
 * @author Giwon
 */
public class Main_19238 {
	public static final int BLANK = 0, WALL = 1;
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	// 지도 크기 
	public static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 지도 크기 
		N = Integer.parseInt(st.nextToken());
		// 승객 수 
		final int M = Integer.parseInt(st.nextToken());
		// 시작 연료 양 
		int fuel = Integer.parseInt(st.nextToken());
		
		// 지도 입력 
		int[][] map = new int[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 택시 시작 위치 (0부터 시작하도록 설정)
		st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken()) - 1;
		int C = Integer.parseInt(st.nextToken()) - 1;
		Car texi = new Car(R, C, fuel, map);
		
		// 승객 입력 
		Passenger[][] state = new Passenger[N][N];
		int destR, destC;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 승객 위치, 목적지 위치 (0부터 시작하도록 설정)
			R = Integer.parseInt(st.nextToken()) - 1;
			C = Integer.parseInt(st.nextToken()) - 1;
			destR = Integer.parseInt(st.nextToken()) - 1;
			destC = Integer.parseInt(st.nextToken()) - 1;
			
			state[R][C] = new Passenger(R, C, destR, destC);
		}
		
		// 모든 승객을 태울 때까지 운행
		for(int i = 0; i < M; i++) {
			if(!texi.drive(state)) {
				System.out.println(-1);
				br.close();
				return;
			}
		}
		
		// 남은 연료 출력
		System.out.println(texi.fuel);
		br.close();

	}
	
	public static class Coord {
		int row, col;
		int depth;
		
		public Coord(int row, int col, int depth) {
			super();
			this.row = row;
			this.col = col;
			this.depth = depth;
		}
	}
	
	public static class Passenger implements Comparable<Passenger>{
		// 승객 위치
		int row, col;
		// 목표 지점 승객 위치	
		int destRow, destCol;
		// 승객까지 도달하는 최단거리 
		int distance;
		
		public Passenger(int row, int col, int destRow, int destCol) {
			this.row = row;
			this.col = col;
			this.destRow = destRow;
			this.destCol = destCol;
			this.distance = 0;
		}

		@Override
		public int compareTo(Passenger o) {
			// 최단거리 -> 행 -> 열이 작은 순서대로 정렬 
			if(this.distance != o.distance) return Integer.compare(this.distance, o.distance);
			if(this.row != o.row) return Integer.compare(this.row, o.row);
			return Integer.compare(this.col, o.col);
		}
	}

	public static class Car {
		// 차 위치
		int row, col;
		// 남은 연료 
		int fuel;
		// 지도 상태
		int[][] map;
		
		public Car(int row, int col, int fuel, int[][] map) {
			this.row = row;
			this.col = col;
			this.fuel = fuel;
			this.map = map;
		}
		
		// 범위 체크 
		public static boolean checkRange(int target) {
			return 0 <= target && target < N;
		}
		
		// 택시 운행 이후 성공 여부 리턴
		public boolean drive(Passenger[][] state) {
			// 가장 가까운 승객 탐색
			Passenger closest = getClosest(state);
			// 승객이 없는 경우 리턴 
			if(closest == null) return false;
			// 연료가 부족하면 리턴 
			if(closest.distance > fuel) return false;
			// 승객 태우기
			state[closest.row][closest.col] = null;
			row = closest.row;
			col = closest.col;
			fuel -= closest.distance;
			
			// 목표 지점까지 도달하는 최단 거리 계산 
			int distance = getDistance(closest.destRow, closest.destCol);
			// 연료가 부족하면 리턴
			if(distance > fuel) return false;
			// 운행 완료 
			row = closest.destRow;
			col = closest.destCol;
			fuel += distance;
			
			return true;
		}
		
		// 현재 차 위치에서 목표 위치까지 도달하는 최단 거리 계산
		public int getDistance(int destRow, int destCol) {
			// 방문 체크 
			boolean[][] visited = new boolean[N][N];
			// BFS로 탐색 
			Queue<Coord> bfsQueue = new ArrayDeque<>();
			bfsQueue.offer(new Coord(row, col, 0));
			visited[row][col] = true;
			
			Coord curr;
			int R, C;
			while(!bfsQueue.isEmpty()) {
				curr = bfsQueue.poll();
				
				// 현재 도달한 위치가 목표 위치인 경우 중단 
				if(curr.row == destRow && curr.col == destCol) return curr.depth;
				
				for(int dir = 0; dir < 4; dir++) {
					R = curr.row + dRow[dir];
					C = curr.col + dCol[dir];
					
					// 범위를 벗어나면 생략
					if(!checkRange(R) || !checkRange(C)) continue;
					// 이미 방문한 경우 생략
					if(visited[R][C]) continue;
					// 벽인 경우 생략
					if(map[R][C] == WALL) continue;
					
					// 방문 체크
					visited[R][C] = true;
					// 다음 위치 탐색
					bfsQueue.offer(new Coord(R, C, curr.depth + 1));
				}
			}
			
			// 목표 지점까지 도달하지 못하는 경우 
			return Integer.MAX_VALUE;
		}
		
		// 현재 차 위치에서 승객까지 도달하는 최단 거리 계산 후 가장 가까운 승객 리턴 
		public Passenger getClosest(Passenger[][] state) {
			// 방문 체크 
			boolean[][] visited = new boolean[N][N];
			// 우선순위 큐로 승객 정렬 
			Queue<Passenger> passengers = new PriorityQueue<>();
			// BFS로 탐색 
			Queue<Coord> bfsQueue = new ArrayDeque<>();
			bfsQueue.offer(new Coord(row, col, 0));
			visited[row][col] = true;
			
			Coord curr;
			int R, C;
			while(!bfsQueue.isEmpty()) {
				curr = bfsQueue.poll();
				
				// 현재 도달한 위치에 승객이 있는 경우 우선순위 큐에 추가 
				if(state[curr.row][curr.col] != null) {
					state[curr.row][curr.col].distance = curr.depth;
					passengers.offer(state[curr.row][curr.col]);
				}
				
				for(int dir = 0; dir < 4; dir++) {
					R = curr.row + dRow[dir];
					C = curr.col + dCol[dir];
					
					// 범위를 벗어나면 생략
					if(!checkRange(R) || !checkRange(C)) continue;
					// 이미 방문한 경우 생략
					if(visited[R][C]) continue;
					// 벽인 경우 생략
					if(map[R][C] == WALL) continue;
					
					// 방문 체크
					visited[R][C] = true;
					// 다음 위치 탐색
					bfsQueue.offer(new Coord(R, C, curr.depth + 1));
				}
			}
			
			// 승객이 없는 경우 null 리턴 
			if(passengers.isEmpty()) return null;
			return passengers.poll();
		}
	}
}
