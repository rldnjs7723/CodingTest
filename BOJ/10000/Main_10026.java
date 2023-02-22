

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

/**
 * 백준 10026번 적록색약
 * 문제 분류: BFS, 사방 탐색, 구현, UnionFind
 * @author Giwon
 */
public class Main_10026 {
	public static final int B = 0, R = 1, G = 2, RG = 3;
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	public static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		// 그림 크기
		N = Integer.parseInt(br.readLine());
		// 그림 입력
		String input;
		int[][] picture = new int[N][N];
		for(int row = 0; row < N; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < N; col++) {
				if(input.charAt(col) == 'B') picture[row][col] = B;
				else if(input.charAt(col) == 'R') picture[row][col] = R;
				else picture[row][col] = G;
			}
		}
		
		// 중복 구역 탐색용 Set
		HashSet<Color> duplicate = new HashSet<>();
		Queue<Coord> queue = new ArrayDeque<>();
		// 인접한 색상 저장용 배열
		Color[][] normal = new Color[N][N];
		// BFS로 인접한 색상 탐색
		int r, c;
		Coord curr;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				if(normal[row][col] == null) {
					normal[row][col] = new Color(picture[row][col]);
					
					queue.offer(new Coord(row, col));
					while(!queue.isEmpty()) {
						curr = queue.poll();
						
						for(int dir = 0; dir < 4; dir++) {
							r = curr.row + dRow[dir];
							c = curr.col + dCol[dir];
							
							// 주변의 행, 열 중에서 탐색하지 않았고 색이 같은 경우 탐색 수행
							if(checkRange(r) && checkRange(c)
									&& normal[r][c] == null && picture[r][c] == normal[row][col].color) {
								queue.offer(new Coord(r, c));
								normal[r][c] = normal[row][col];
							}
						}
					}
				}
			}
		}
		// 구역 개수 탐색
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				duplicate.add(normal[row][col]);
			}
		}
		sb.append(duplicate.size() + " ");
		
		// 적록색약 탐색
		duplicate.clear();
		queue.clear();
		Color[][] weak = new Color[N][N];
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				if(weak[row][col] == null) {
					weak[row][col] = new Color(picture[row][col] > 0 ? RG : B);
					
					queue.offer(new Coord(row, col));
					while(!queue.isEmpty()) {
						curr = queue.poll();
						
						for(int dir = 0; dir < 4; dir++) {
							r = curr.row + dRow[dir];
							c = curr.col + dCol[dir];
							
							// 주변의 행, 열 중에서 탐색하지 않았고 색이 같은 경우 탐색 수행
							if(checkRange(r) && checkRange(c)
									&& weak[r][c] == null && weak[row][col].equal(picture[r][c])) {
								queue.offer(new Coord(r, c));
								weak[r][c] = weak[row][col];
							}
						}
					}
				}
			}
		}
		// 구역 개수 탐색
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				duplicate.add(weak[row][col]);
			}
		}
		sb.append(duplicate.size());
		
		System.out.println(sb.toString());
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	
	// 행, 열 저장용 클래스
	public static class Coord {
		int row, col;

		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	// 색 저장용 클래스
	public static class Color {
		int color;
		
		public Color(int color) {
			this.color = color;
		}
		
		public boolean equal(int color) {
			if(this.color == B) return this.color == color;
			else return color > 0;
		}
	}
	
}
