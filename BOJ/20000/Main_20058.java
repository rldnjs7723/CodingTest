import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 백준 20058번 마법사 상어와 파이어스톰
 * 문제 분류: 구현, 시뮬레이션, 분할 정복, Flood Fill, 사방 탐색
 * @author Giwon
 */
public class Main_20058 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	// 2^i = twoPow[i]
	public static final int[] twoPow = {1, 2, 4, 8, 16, 32, 64};
	public static final int BLANK = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 격자 크기
		final int N = Integer.parseInt(st.nextToken());
		final int size = twoPow[N];
		// 마법 시전 수
		final int Q = Integer.parseInt(st.nextToken());
		
		// 얼음 격자 입력
		int[][] state = new int[size][size];
		for(int row = 0; row < size; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < size; col++) {
				state[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 마법 시전
		st = new StringTokenizer(br.readLine());
		int L;
		for(int i = 0; i < Q; i++) {
			// 마법 단계
			L = Integer.parseInt(st.nextToken());
			
			// 격자 회전
			rotateGrid(0, 0, size, twoPow[L], state);
			// 얼음 녹이기
			meltIce(state, size);
		}
		
		// 남은 얼음의 합 계산
		System.out.println(iceAmount(state));
		// 가장 큰 덩어리가 차지하는 칸의 개수 계산
		System.out.println(iceMaxCount(state, size));
		br.close();
	}
	
	// 얼음 덩어리 체크
	public static int iceMaxCount(int[][] state, int size) {
		Ice[][] iceGroup = new Ice[size][size];
		
		// 얼음 덩어리 구분하기
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		Ice curr;
		Coord coord;
		int r, c;
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				// 얼음이 아니라면 생략
				if(state[row][col] == BLANK) continue;
				// 이미 방문한 경우 생략
				if(iceGroup[row][col] != null) continue;
				
				curr = new Ice();
				iceGroup[row][col] = curr;
				
				// BFS로 덩어리 탐색
				bfsQueue.offer(new Coord(row, col));
				while(!bfsQueue.isEmpty()) {
					coord = bfsQueue.poll();
					
					for(int dir = 0; dir < 4; dir++) {
						r = coord.row + dRow[dir];
						c = coord.col + dCol[dir];
						
						// 방문하지 않았고, 얼음이 존재하는 경우 덩어리에 포함
						if(checkRange(r, size) && checkRange(c, size)
								&& state[r][c] != BLANK && iceGroup[r][c] == null) {
							// 방문 체크
							iceGroup[r][c] = curr;
							// 크기 증가
							curr.size++;
							bfsQueue.offer(new Coord(r, c));
						}
					}
				}
			}
		}
		
		// 가장 큰 얼음 덩어리를 찾기 위한 TreeSet
		TreeSet<Ice> duplicate = new TreeSet<>();
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				// 얼음이 아니면 생략
				if(iceGroup[row][col] == null) continue;
				
				duplicate.add(iceGroup[row][col]);
			}
		}
		
		// 얼음 덩어리가 없다면 0 리턴
		if(duplicate.isEmpty()) return 0;
		else return duplicate.first().size;
	}

	// 남은 얼음의 양 체크
	public static int iceAmount(int[][] state) {
		int sum = 0;
		for(int[] inner: state) {
			for(int ice: inner) {
				sum += ice;
			}
		}
		
		return sum;
	}
	
	// 얼음 녹이기
	public static void meltIce(int[][] state, int size) {
		int[][] meltAmount = new int[size][size];
		
		int r, c, count;
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				// 얼음이 아니라면 생략
				if(state[row][col] == BLANK) continue;
				
				// 주변의 얼음 개수 카운트
				count = 0;
				for(int dir = 0; dir < 4; dir++) {
					r = row + dRow[dir];
					c = col + dCol[dir];
					
					// 얼음이 존재하면 카운트 추가
					if(checkRange(r, size) && checkRange(c, size)
							&& state[r][c] > BLANK) count++;
				}
				
				// 인접한 얼음이 3개 미만인 경우 얼음 녹이기
				if(count < 3) {
					meltAmount[row][col] = -1;
				}
			}
		}
		
		// 녹은 얼음 양 반영
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				// 얼음이 아니라면 생략
				if(state[row][col] == BLANK) continue;
				state[row][col] += meltAmount[row][col];
			}
		}
	}
	
	/**
	 * 격자 회전
	 * @param row		시작 행
	 * @param col		시작 열
	 * @param size		현재 크기
	 * @param target	목표 크기
	 * @param state		회전할 전체 배열
	 */
	public static void rotateGrid(int row, int col, int size, int target, int[][] state) {
		// 목표 크기에 도달하면 배열 회전
		if(size == target) {
			int[][] result = new int[size][size];
			
			// 시계 방향으로 90도 회전
			for(int r = 0; r < size; r++) {
				for(int c = 0; c < size; c++) {
					result[r][c] = state[row + size - 1 - c][col + r];
				}
			}
			
			// 회전 결과 반영
			for(int r = 0; r < size; r++) {
				for(int c = 0; c < size; c++) {
					state[row + r][col + c] = result[r][c];
				}
			}
			return;
		}
		
		int half = size / 2;
		// 목표 크기에 도달할 때까지 크기를 절반 씩 줄여서 수행
		rotateGrid(row, col, half, target, state);
		rotateGrid(row, col + half, half, target, state);
		rotateGrid(row + half, col, half, target, state);
		rotateGrid(row + half, col + half, half, target, state);
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	// BFS를 위한 좌표 클래스
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	// 얼음 덩어리를 구분하기 위한 DisjointSet
	public static class Ice implements Comparable<Ice>{
		// 덩어리에 포함된 칸의 개수
		int size;
		
		public Ice() {
			this.size = 1;
		}
		
		// 얼음 개수에 따라 내림차순 정렬
		@Override
		public int compareTo(Ice o) {
			return Integer.compare(o.size, this.size);
		}
	}
}
