import java.util.Queue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * SWEA 1249번 보급로
 * 문제 분류: Dijkstra 알고리즘
 * @author Giwon
 */
class Solution_1249 {
	static int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	static int[] dRow = {0, -1, 0, 1};
	static int[] dCol = {-1, 0, 1, 0};
	static int INF = Integer.MAX_VALUE;
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int T = Integer.parseInt(br.readLine().trim());
		int[][] map, totalMap;
		Queue<Coord> checkCoord = new LinkedList<>();
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N = Integer.parseInt(br.readLine().trim());
			int row, col;
			String input;

			// 지도 정보 입력
			map = new int[N][N];
			totalMap = new int[N][N];
			for(row = 0; row < N; row++) {
				input = br.readLine().trim();
				for(col = 0; col < N; col++) {
					map[row][col] = input.charAt(col) - '0';
					totalMap[row][col] = INF;
				}
			}
			
			// 시작 지점은 좌측 상단 (0행 0열)
			totalMap[0][0] = 0;
			checkCoord.clear();
			checkCoord.add(new Coord(0, 0));
			
			// 사방 탐색 수행
			Coord curr;
			while(!checkCoord.isEmpty()) {
				curr = checkCoord.poll();
				
				for(int i = 0; i < 4; i++) {
					row = curr.row + dRow[i];
					col = curr.col + dCol[i];
					
					// 다음 위치로 이동하는 총 복구 시간이 해당 위치에 기록되어 있는 복구 시간보다 짧으면 시간 갱신
					if(checkRange(N, row) && checkRange(N, col)) {
						if(totalMap[row][col] > totalMap[curr.row][curr.col] + map[row][col]) {
							totalMap[row][col] = totalMap[curr.row][curr.col] + map[row][col];
							checkCoord.add(new Coord(row, col));
						}
					}
				}
			}
			
			System.out.println("#" + test_case + " " + totalMap[N-1][N-1]);
		}
		
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int N, int target) {
		return target >= 0 && target < N;
	}
	
	public static class Coord {
		int row, col;
		
		Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
}