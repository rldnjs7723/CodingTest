import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 17472번 다리 만들기 2
 * 문제 분류: Union Find, MST(Kruskal 알고리즘)
 * @author Giwon
 */
public class Main_17472 {
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	public static final int[] dRow = {0, 0, -1, 1};
	public static final int[] dCol = {-1, 1, 0, 0};
	public static final int SEA = 0, GROUND = 1;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		final int N = Integer.parseInt(st.nextToken());
		final int M = Integer.parseInt(st.nextToken());
		
		// 지도 입력
		int[][] map = new int[N][M];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < M; col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// Flood Fill로 섬끼리 연결
		Island[][] state = new Island[N][M];
		List<Island> islandList = findIsland(N, M, map, state);
		
		// 다리 탐색
		findBridge(N, M, state);
		
		// 모든 다리 우선순위 큐에 추가
		Queue<Bridge> kruskalQueue = new PriorityQueue<>();
		int u, v, w;
		for(Island island: islandList) {
			u = island.idx;
			for(Entry<Integer, Integer> entry: island.bridge.entrySet()) {
				v = entry.getKey();
				w = entry.getValue();
				
				kruskalQueue.offer(new Bridge(u, v, w));
			}
		}
		
		int result = 0;
		// 다리 연결하여 MST 구성
		Bridge curr;
		while(!kruskalQueue.isEmpty()) {
			curr = kruskalQueue.poll();
			result += Island.unionIsland(islandList.get(curr.u), islandList.get(curr.v), curr.w);
		}
		
		// 모든 섬이 연결되지 않은 경우 -1 출력
		System.out.println(Island.checkUnion(islandList) ? result : -1);
		br.close();
	}
	
	// 범위 체크
	public static boolean checkRange(int target, int N) {
		return 0 <= target && target < N;
	}
	
	// 다리 길이 계산
	public static int getDistance(int r1, int c1, int r2, int c2) {
		return Math.abs(r1 - r2) + Math.abs(c1 - c2) - 1;
	}
	
	// 각 좌표에서 시작하여 다른 섬까지 연결할 수 있는 다리 탐색
	public static void findBridge(int N, int M, Island[][] state) {
		Island currIsland;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				// 바다라면 생략
				if(state[row][col] == null) continue;
				// 현재 섬
				currIsland = state[row][col];
				
				// 사방 탐색으로 연결할 수 있는 가장 가까운 섬 탐색
				int R, C;
				for(int dir = 0; dir < 4; dir++) {
					R = row + dRow[dir];
					C = col + dCol[dir];
					
					// 바다가 아닐 때까지 이동
					while(checkRange(R, N) && checkRange(C, M)
							&& state[R][C] == null) {
						R += dRow[dir];
						C += dCol[dir];
					}
					
					// 범위를 벗어나면 생략
					if(!checkRange(R, N) || !checkRange(C, M)) continue;
					// 현재 섬과 동일하면 생략
					if(state[R][C] == currIsland) continue;
					
					// 다리 길이 계산
					int len = getDistance(row, col, R, C);
					// 다리 길이가 2보다 짧으면 생략
					if(len < 2) continue;
					
					// 연결할 수 있는 다리 갱신
					currIsland.setBridge(state[R][C].idx, len);
				}
			}
		}
	}
	
	/**
	 * Flood Fill로 섬끼리 연결
	 * @param N			섬 세로 크기
	 * @param M			섬 가로 크기
	 * @param map		지도 정보
	 * @param state		섬 상태 저장할 배열
	 * @return			섬 리스트 리턴
	 */
	public static List<Island> findIsland(int N, int M, int[][] map, Island[][] state) {
		List<Island> islandList = new ArrayList<>();
		int idx = 0;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				// 바다라면 생략
				if(map[row][col] == SEA) continue;
				// 이미 방문한 경우 생략
				if(state[row][col] != null) continue;
				// 섬 초기화
				state[row][col] = new Island(idx++);
				islandList.add(state[row][col]);
				
				// 연결된 땅 탐색
				int R, C;
				Queue<Coord> queue = new ArrayDeque<>();
				queue.offer(new Coord(row, col));
				Coord curr;
				while(!queue.isEmpty()) {
					curr = queue.poll();
					
					for(int dir = 0; dir < 4; dir++) {
						R = curr.row + dRow[dir];
						C = curr.col + dCol[dir];
						
						// 범위를 벗어나면 생략
						if(!checkRange(R, N) || !checkRange(C, M)) continue;
						// 바다라면 생략
						if(map[R][C] == SEA) continue;
						// 이미 방문한 경우 생략
						if(state[R][C] != null) continue;
						
						// 섬에 포함
						state[R][C] = state[row][col];
						// 주변 땅 탐색
						queue.offer(new Coord(R, C));
					}
				}
			}
		}
		
		return islandList;
	}
	
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static class Bridge implements Comparable<Bridge>{
		int u, v, w;
		
		public Bridge(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}
		
		// 다리 길이가 짧은 순서대로 정렬
		@Override
		public int compareTo(Bridge o) {
			return Integer.compare(this.w, o.w);
		}
	}

	public static class Island {
		// 섬의 번호
		int idx;
		// 다른 섬까지 지을 수 있는 다리의 최소 길이
		Map<Integer, Integer> bridge;
		// Union Find용 
		Island parent;
		int height; 
		
		public Island(int idx) {
			this.idx = idx;
			this.bridge = new HashMap<>();
			this.parent = null;
			this.height = 1;
		}
		
		public void setBridge(int idx, int len) {
			if(!bridge.containsKey(idx)) {
				bridge.put(idx, Integer.MAX_VALUE);
			}
			// 다른 섬까지 연결할 때 필요한 최소 길이만 저장
			if(bridge.get(idx) > len) bridge.put(idx, len);
		}

		// Union Find 조상 노드 탐색
		public Island findRoot() {
			// 부모 노드가 없다면 루트 노드
			if(parent == null) return this;
			
			// 루트 노드 갱신
			this.parent = parent.findRoot();
			return this.parent;
		}
		
		// 모든 섬이 연결되어 있는지 체크
		public static boolean checkUnion(List<Island> islandList) {
			Island root = islandList.get(0).findRoot();
			
			for(Island island: islandList) {
				if(island.findRoot() != root) return false;
			}
			return true;
		}

		// 두 섬 다리로 연결. 연결한 다리의 길이를 리턴
		public static int unionIsland(Island i1, Island i2, int len) {
			Island p1 = i1.findRoot();
			Island p2 = i2.findRoot();
			
			// 이미 연결되어 있다면 생략
			if(p1 == p2) return 0;
			
			// 높이가 더 높은 Island를 루트 노드로 설정
			if(p1.height >= p2.height) {
				p2.parent = p1;
				if(p1.height == p2.height) p1.height++;
			} else {
				p1.parent = p2;
			}
			
			return len;
		}
		
		@Override
		public String toString() {
			return "Island [idx=" + idx + ", bridge=" + bridge + "]";
		}
		
	}
}
