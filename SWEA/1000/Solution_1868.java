import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * SWEA 1868번 파핑파핑 지뢰찾기
 * 문제 분류: 완전 탐색, BFS, Union Find (Flood Fill)
 * @author Giwon
 */
public class Solution_1868 {
	public static final int NW = 0, N = 1, NE = 2, E = 3, SE = 4, S = 5, SW = 6, W = 7;
	public static final int[] dRow = {-1, -1, -1, 0, 1, 1, 1, 0};
	public static final int[] dCol = {-1, 0, 1, 1, 1, 0, -1, -1};
	public static final int MINE = -1;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		int T = Integer.parseInt(br.readLine().trim());
		int N;
		int[][] state;
		Object[][] disjointSet;
		Queue<Coord> queue = new ArrayDeque<>();
		Set<Object> duplicate = new HashSet<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			// 표 크기
			N = Integer.parseInt(br.readLine().trim());
			// 지뢰 상태 체크
			state = new int[N][N];
			for(int row = 0; row < N; row++) {
				input = br.readLine().trim();
				for(int col = 0; col < N; col++) {
					state[row][col] = input.charAt(col) == '*' ? MINE : 0;
				}
			}
			// 주변 지뢰 개수 체크
			int r, c;
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					// 현재 행, 열에 지뢰가 있다면 생략
					if(state[row][col] == MINE) continue; 
					
					// 8방 탐색으로 지뢰 개수 세기
					for(int dir = 0; dir < 8; dir++) {
						r = row + dRow[dir];
						c = col + dCol[dir];
						// 주변에 지뢰가 있다면 개수 ++
						if(checkRange(r, N) && checkRange(c, N) && state[r][c] == MINE) 
							state[row][col]++;
					}
				}
			}
			// 0으로 표시되는 영역 묶기
			disjointSet = new Object[N][N];
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					// 주변에 지뢰가 존재하면 생략
					if(state[row][col] != 0) continue; 
					// 이미 탐색한 부분은 생략
					if(disjointSet[row][col] != null) continue;
					// 방문 체크
					disjointSet[row][col] = new Object();
					
					// BFS로 주변에 위치한 0 탐색
					queue.clear();
					queue.offer(new Coord(row, col));
					Coord curr;
					while(!queue.isEmpty()) {
						curr = queue.poll();
						
						// 8방 탐색
						for(int dir = 0; dir < 8; dir++) {
							r = curr.row + dRow[dir];
							c = curr.col + dCol[dir];
							
							if(checkRange(r, N) && checkRange(c, N) && disjointSet[r][c] == null) {
								// 방문 체크
								disjointSet[r][c] = disjointSet[row][col];
								// 주변에 0이 없는 경우에만 추가로 BFS 탐색
								if(state[r][c] == 0) queue.offer(new Coord(r, c));
							}
						}
					}
				}
			}
			
			// 0으로 밝힐 수 없는 영역 방문 체크 하기
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					// 지뢰는 생략
					if(state[row][col] == MINE) continue; 
					// 이미 탐색한 부분은 생략
					if(disjointSet[row][col] != null) continue;
					// 방문 체크
					disjointSet[row][col] = new Object();
				}
			}
			
			// 클릭 횟수 카운트
			duplicate.clear();
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					if(disjointSet[row][col] != null) duplicate.add(disjointSet[row][col]);
				}
			}
			
			System.out.println("#" + test_case + " " + duplicate.size());
		}
		br.close();
	}
	
	// 좌표 나타내는 클래스
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
}
