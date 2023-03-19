import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 18405번 경쟁적 전염
 * 문제 분류: Flood Fill, 자료 구조(우선순위 큐)
 * @author Giwon
 */
public class Main_18405 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	public static final Virus NULL = new Virus(0) ;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 시험관 크기
		final int N = Integer.parseInt(st.nextToken());
		// 바이러스 종류
		final int K = Integer.parseInt(st.nextToken());
		
		// Disjoint Set 구성을 위한 초기화
		Virus[] virusType = new Virus[K + 1];
		virusType[0] = NULL;
		for(int i = 1; i <= K; i++) {
			virusType[i] = new Virus(i);
		}
		
		// BFS를 위한 우선순위 큐
		Queue<Coord> pQueue = new PriorityQueue<>();
		// 바이러스 상태 입력
		Virus[][] state = new Virus[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				state[row][col] = virusType[Integer.parseInt(st.nextToken())];
				if(state[row][col].type > 0) {
					pQueue.offer(new Coord(row, col, 0, state[row][col].type));
				}
			}
		}
		
		st = new StringTokenizer(br.readLine());
		final int S = Integer.parseInt(st.nextToken());
		final int X = Integer.parseInt(st.nextToken());
		final int Y = Integer.parseInt(st.nextToken());
		
		Coord curr;
		int r, c;
		while(!pQueue.isEmpty()) {
			curr = pQueue.poll();
			// 원하는 시간에 도달했다면 종료.
			if(curr.time >= S) break;
			
			// 사방탐색
			for(int dir = 0; dir < 4; dir++) {
				r = curr.row + dRow[dir];
				c = curr.col + dCol[dir];
				
				// 범위를 벗어난 경우 생략
				if(!checkRange(r, N) || !checkRange(c, N)) continue;
				
				// 현재 위치에 바이러스가 존재하지 않는다면 증식
				if(state[r][c] == NULL) {
					state[r][c] = state[curr.row][curr.col];
					pQueue.offer(new Coord(r, c, curr.time + 1, state[r][c].type));
				}
			}
		}
		
		System.out.println(state[X - 1][Y - 1].type);
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return target >= 0 && target < N;
	}
	
	// 우선순위 큐를 이용하여 BFS 수행을 위한 클래스
	public static class Coord implements Comparable<Coord>{
		// 추가된 시간
		int time;
		// 바이러스 종류
		int virus;
		// 바이러스 위치
		int row, col;
		
		public Coord(int row, int col, int time, int virus) {
			this.row = row;
			this.col = col;
			this.time = time;
			this.virus = virus;
		}

		@Override
		public int compareTo(Coord o) {
			// 추가된 시간이 다르면 먼저 생성된 바이러스부터 증식
			if(this.time != o.time) return Integer.compare(this.time, o.time);
			// 추가된 시간이 같으면 번호가 낮은 바이러스부터 증식
			if(this.virus != o.virus) return Integer.compare(this.virus, o.virus);
			// 둘 다 같다면 아무 바이러스 먼저 수행해도 문제 없음
			return 0;
		}
	}
	
	// Disjoint Set 구성을 위한 클래스
	public static class Virus {
		int type;
		
		public Virus(int type) {
			this.type = type;
		}
	}
}

