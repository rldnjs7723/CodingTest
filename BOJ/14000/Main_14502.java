import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/** 
 * 백준 14502번 연구소
 * 문제 분류: 완전 탐색, Flood Fill, 사방 탐색
 * @author Giwon
 */
public class Main_14502 {
	// 사방 탐색
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	// 상수 정의
	public static final int BLANK = 0, WALL = 1, VIRUS = 2;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		// 지도 세로 크기
		final int N = Integer.parseInt(st.nextToken());
		// 지도 가로 크기
		final int M = Integer.parseInt(st.nextToken());
		
		// 지도 입력
		int[][] map = new int[N][M];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < M; col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		System.out.println(permutation(0, 0, 0, map, N, M));
		br.close();
	}
	
	/**
	 * 순열로 모든 위치에 벽을 세워보고 안전 영역 크기 최댓값 계산
	 * @param R			마지막 위치 행
	 * @param C			마지막 위치 열
	 * @param count		추가한 벽 개수
	 * @param map		지도 상태
	 * @param N			지도 세로 크기
	 * @param M			지도 가로 크기
	 * @return			안전 영역 크기 최댓값
	 */
	public static int permutation(int R, int C, int count, int[][] map, int N, int M) {
		// 벽 3개를 세웠다면 안전 영역 크기 계산
		if(count == 3) {
			return spread(N, M, map);
		}
		
		int currIdx = R * M + C;
		int max = 0;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				int mapIdx = row * M + col;
				// 이미 지나온 영역은 고려하지 않음
				if(mapIdx < currIdx) continue;
				// 현재 위치가 빈칸이 아니라면 생략
				if(map[row][col] != BLANK) continue;
				
				// 현재 위치에 벽 세우기
				map[row][col] = WALL;
				max = Math.max(max, permutation(row, col, count + 1, map, N, M));
				// 벽 없애고 다음 위치 탐색
				map[row][col] = BLANK;
			}
		}
		
		return max;
	}
	
	// 현재 맵 상태에서 바이러스가 주어진 경우 확산 상태 확인 후 안전 영역 넓이 최댓값 리턴ㄴ
	public static int spread(int N, int M, int[][] map) {
		// 확산 상태 저장 배열
		State[][] state = new State[N][M];
		
		// 바이러스 확산
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				// 바이러스가 아니라면 생략
				if(map[row][col] != VIRUS) continue;
				// 이미 방문한 위치라면 생략
				if(state[row][col] != null) continue;
				
				// BFS로 사방 탐색 수행하면서 바이러스 확산 범위 체크
				state[row][col] = new State(VIRUS, 1);
				bfsQueue.clear();
				bfsQueue.offer(new Coord(row, col));
				Coord curr;
				int R, C;
				while(!bfsQueue.isEmpty()) {
					curr = bfsQueue.poll();
					
					for(int dir = 0; dir < 4; dir++) {
						R = curr.row + dRow[dir];
						C = curr.col + dCol[dir];
						
						// 범위를 벗어났다면 생략
						if(!checkRange(R, N) || !checkRange(C, M)) continue;
						// 벽이라면 생략
						if(map[R][C] == WALL) continue;
						// 이미 방문한 위치는 생략
						if(state[R][C] != null) continue;
						
						state[R][C] = state[row][col];
						state[R][C].size++;
						// Queue에 추가
						bfsQueue.offer(new Coord(R, C));
					}
				}
			}
		}
		
		// 안전지대 계산
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				// 빈칸이 아니라면 생략
				if(map[row][col] != BLANK) continue;
				// 이미 방문한 위치라면 생략
				if(state[row][col] != null) continue;
				
				// BFS로 사방 탐색 수행하면서 바이러스 확산 범위 체크
				state[row][col] = new State(BLANK, 1);
				bfsQueue.clear();
				bfsQueue.offer(new Coord(row, col));
				Coord curr;
				int R, C;
				while(!bfsQueue.isEmpty()) {
					curr = bfsQueue.poll();
					
					for(int dir = 0; dir < 4; dir++) {
						R = curr.row + dRow[dir];
						C = curr.col + dCol[dir];
						
						// 범위를 벗어났다면 생략
						if(!checkRange(R, N) || !checkRange(C, M)) continue;
						// 빈칸이 아니라면 생략
						if(map[R][C] != BLANK) continue;
						// 이미 방문한 위치는 생략
						if(state[R][C] != null) continue;
						
						state[R][C] = state[row][col];
						state[R][C].size++;
						// Queue에 추가
						bfsQueue.offer(new Coord(R, C));
					}
				}
			}
		}
		
		// 안전 영역 취합
		Set<State> safeArea = new HashSet<>();
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				// 벽이라면 생략
				if(state[row][col] == null) continue;
				// 바이러스 영역이라면 생략
				if(state[row][col].type == VIRUS) continue;
				
				safeArea.add(state[row][col]);
			}
		}
		
		// 안전 영역 넓이 계산
		int result = 0;
		for(State area: safeArea) {
			result += area.size;
		}
		
		return result;
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	public static class State {
		// 바이러스인지, 안전지대인지 타입 체크
		int type;
		// 영역 크기 카운트
		int size;
		
		public State(int type, int size) {
			this.type = type;
			this.size = size;
		}
	}
}
