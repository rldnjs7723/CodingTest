import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

/**
 * 백준 16946번 벽 부수고 이동하기 4
 * 문제 분류: BFS, 사방 탐색
 * @author Giwon
 */
public class Main_16946 {
	public static final int BLANK = 0, WALL = 1;
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		// 행 크기
		int N = Integer.parseInt(input.split(" ")[0]);
		// 열 크기
		int M = Integer.parseInt(input.split(" ")[1]);
		// 맵 입력
		int[][] map = new int[N][M];
		for(int row = 0; row < N; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < M; col++) {
				map[row][col] = input.charAt(col) - '0';
			}
		}
		// 빈 칸끼리 이어주는 배열
		Blank[][] zeroCount = new Blank[N][M];
		// 빈 칸끼리는 전부 연결
		Queue<Coord> queue = new ArrayDeque<>();
		Coord curr;
		int r, c;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				// 현재 빈칸을 세지 않은 곳만 탐색
				if(map[row][col] == BLANK && zeroCount[row][col] == null) {
					zeroCount[row][col] = new Blank();
					queue.clear();
					queue.offer(new Coord(row, col));
					
					// BFS로 주변의 모든 빈칸과 연결
					while(!queue.isEmpty()) {
						curr = queue.poll();
						
						for(int dir = 0; dir < 4; dir++) {
							r = curr.row + dRow[dir];
							c = curr.col + dCol[dir];
							
							if(checkRange(r, N) && checkRange(c, M) 
									&& map[r][c] == BLANK && zeroCount[r][c] == null) {
								zeroCount[r][c] = zeroCount[row][col];
								zeroCount[row][col].count++;
								queue.offer(new Coord(r, c));
							}
						}
					}
				}
			}
		}
		
		HashSet<Blank> adder = new HashSet<>();
		int[][] result = new int[N][M];
		// 벽 주변의 모든 빈칸의 개수를 더하기
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				if(map[row][col] == 1) {
					adder.clear();
					result[row][col] = 1;
					
					for(int dir = 0; dir < 4; dir++) {
						r = row + dRow[dir];
						c = col + dCol[dir];
						
						if(checkRange(r, N) && checkRange(c, M) 
								&& map[r][c] == BLANK) {
							adder.add(zeroCount[r][c]);
						}
					}
					
					for(Blank blank: adder) {
						result[row][col] += blank.count;
					}
				}
			}
		}
		
		printArr(result);
		br.close();
	}
	
	public static void printArr(int[][] arr) {
		StringBuilder sb = new StringBuilder();
		for(int[] inner: arr) {
			for(int val: inner) {
				sb.append(val % 10);
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
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
	
	// 빈 칸끼리 이어주기 위한 클래스
	public static class Blank {
		int count;
		
		public Blank() {
			this.count = 1;
		}
		
		public void increase() {
			count++;
		}
	}
}
