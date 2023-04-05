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
 * 백준 1175번 배달
 * 문제 분류: 비트마스킹, BFS
 * @author Giwon
 */
public class Main_1175 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	// 선물은 무조건 2개
	public static final int FULL = 0b11;
	public static final int INF = Integer.MAX_VALUE;
	public static final int BLANK = 0, TARGET = 1, BLOCKED = 2;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 세로 크기
		final int N = Integer.parseInt(st.nextToken());
		// 가로 크기
		final int M = Integer.parseInt(st.nextToken());
		
		// 교실 입력
		String input;
		int[][] state = new int[N][M];
		// 민식이 좌표
		Coord start = new Coord(-1, -1, -1, 0);
		// 선물 배달할 좌표 리스트
		List<Coord> targets = new ArrayList<>();
		for(int row = 0; row < N; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < M; col++) {
				switch (input.charAt(col)) {
					case 'S':
						state[row][col] = BLANK;
						// 민식이 위치 입력
						start.row = row;
						start.col = col;
						break;
					case 'C':
						state[row][col] = TARGET;
						// 배달 좌표 리스트에 추가
						targets.add(new Coord(row, col, -1, 0));
						break;
					case '#':
						// 이동할 수 없는 곳
						state[row][col] = BLOCKED;
						break;
					case '.':
						// 자유롭게 이동할 수 있는 곳
						state[row][col] = BLANK;
						break;
					default:
						break;
				}
			}
		}
		
		// 선물 배달에 걸리는 시간 탐색
		System.out.println(getTime(start, targets, state, N, M));
		br.close();
	}
	
	// 선물 배달에 걸리는 시간 탐색
	public static int getTime(Coord start, List<Coord> targets, int[][] state, int N, int M) {
		// 이전에 들어왔던 방향, 선물 배달한 영역에 따라 다르게 방문 체크
		int[][][][] moveCount = new int[N][M][4][FULL + 1];
		// 초기값 설정
		for(int[][][] row: moveCount) {
			for(int[][] coord: row) {
				for(int[] dir: coord) {
					Arrays.fill(dir, INF);
				}
			}
		}
		
		// BFS로 시간 탐색
		Queue<Coord> bfsQueue = new ArrayDeque<>();
		// 네 방향 모두에서 갈 수 있도록 처리
		for(int dir = 0; dir < 4; dir++) {
			// 시작 위치 설정
			moveCount[start.row][start.col][dir][0] = 0;
			// 큐에 추가
			bfsQueue.offer(new Coord(start.row, start.col, dir, 0));
		}
		
		Coord curr;
		int R, C;
		while(!bfsQueue.isEmpty()) {
			curr = bfsQueue.poll();
			
			// 모든 위치에 배달 완료한 경우 시간 리턴
			if(curr.bitmask == FULL) return moveCount[curr.row][curr.col][curr.prevDir][curr.bitmask];
			
			for(int dir = 0; dir < 4; dir++) {
				// 이전에 이동한 방향이라면 생략
				if(curr.prevDir == dir) continue;
				
				R = curr.row + dRow[dir];
				C = curr.col + dCol[dir];
				
				// 범위를 벗어나면 생략
				if(!checkRange(R, N) || !checkRange(C, M)) continue;
				// 다음 위치로 이동할 수 없다면 생략
				if(state[R][C] == BLOCKED) continue;
				
				
				Coord next = new Coord(R, C, dir, curr.bitmask);
				// 다음 위치가 배달해야 하는 곳이라면 비트마스크 갱신
				if(state[R][C] == TARGET) next.bitmask |= (1 << getIndex(next, targets));
				
				// 최소 이동 시간 갱신이 가능하면 이동
				if(moveCount[next.row][next.col][next.prevDir][next.bitmask] > 
						moveCount[curr.row][curr.col][curr.prevDir][curr.bitmask] + 1) {
					moveCount[next.row][next.col][next.prevDir][next.bitmask] = moveCount[curr.row][curr.col][curr.prevDir][curr.bitmask] + 1;
					bfsQueue.offer(next);
				}
			}
		}
		
		return -1;
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}
	
	// 리스트에서 선물 배달할 장소의 Index 추출
	public static int getIndex(Coord curr, List<Coord> targets) {
		// 배달 목표는 반드시 2개
		Coord target;
		for(int i = 0; i < 2; i++) {
			target = targets.get(i);
			if(target.row == curr.row && target.col == curr.col) return i;
		}
		
		return -1;
	}

	public static class Coord {
		int row, col;
		// 배달 완료한 부분의 Index 비트마스킹
		int bitmask;
		// 현재 좌표에 도달했을 때의 이동 방향
		int prevDir;
		
		public Coord(int row, int col, int dir, int bitmask) {
			this.row = row;
			this.col = col;
			this.bitmask = bitmask;
			this.prevDir = dir;
		}
	}
}
