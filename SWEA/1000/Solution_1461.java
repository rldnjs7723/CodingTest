import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 1461번 프로세서 연결하기
 * 문제 분류: 백트래킹, DFS, 사방탐색
 * @author Giwon
 */
public class Solution_1461 {
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, NONE = 4;
	public static final int[] dRow = {-1, 0, 1, 0, 0};
	public static final int[] dCol = {0, 1, 0, -1, 0};
	public static final int BLANK = 0, CORE = 1;
	public static int N, wireLength, coreCount;
	public static int coreMax, lengthMin;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 테스트 케이스 개수
		int T = Integer.parseInt(br.readLine().trim());
		int[][] state;
		List<Core> cores = new ArrayList<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			// 멕시노스 크기
			N = Integer.parseInt(br.readLine().trim());
			// 멕시노스 코어 상태 입력
			state = new int[N][N];
			cores.clear();
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < N; col++) {
					state[row][col] = Integer.parseInt(st.nextToken());
					// 가장 자리에 있는 코어는 건드릴 필요가 없으므로 1 ~ N - 2까지만 탐색
					if(state[row][col] == CORE &&
							row > 0 && row < N - 1 &&
							col > 0 && col < N - 1) 
						cores.add(new Core(row, col));  
				}
			}
			// 전선 최단 길이 탐색
			wireLength = 0;
			coreCount = 0;
			coreMax = 0;
			lengthMin = Integer.MAX_VALUE;
			checkMinLength(0, cores, state);
			System.out.println("#" + test_case + " " + lengthMin);
		}
		br.close();
	}
	
	// 디버깅용 배열 출력
	public static void printArr(int[][] state) {
		for(int[] inner: state) {
			System.out.println(Arrays.toString(inner));
		}
	}
	
	// 백트래킹 + DFS로 전선 연결 수행
	public static void checkMinLength(int idx, List<Core> cores, int[][] state) {
		if(idx == cores.size()) {
			// 모든 코어의 연결을 마쳤으면 최대 코어 수와 최대 전선 길이 갱신
			if(coreMax < coreCount) {
				coreMax = coreCount;
				lengthMin = wireLength;
			} else if(coreMax == coreCount) {
				lengthMin = Math.min(lengthMin, wireLength);
			}
			return;
		}
		
		// 상 -> 우 -> 하 -> 좌 -> 연결 안 함 순서로 진행
		Core curr = cores.get(idx);
		int targetLen, connectedLen;
		for(int dir = 0; dir <= NONE; dir++) {
			// 전선을 연결하지 않는 경우 다음 코어 탐색
			if(dir == NONE) {
				checkMinLength(idx + 1, cores, state);
				continue;
			}
			
			targetLen = targetLength(curr.row, curr.col, dir);
			connectedLen = connectWire(idx, dir, cores, state);

			// 전선을 끝까지 연결할 수 없는 경우 롤백 후 다음 방향 탐색
			if(targetLen != connectedLen) {
				rollbackWire(idx, dir, cores, state);
				continue;
			}
			
			// 전선이 연결된 경우 다음 코어 탐색
			coreCount++;
			wireLength += connectedLen;
			checkMinLength(idx + 1, cores, state);
			
			// 탐색이 끝나면 롤백 후 다음 방향 탐색
			coreCount--;
			wireLength -= connectedLen;
			rollbackWire(idx, dir, cores, state);
		}
		
	}
	
	// 코어 위치 저장 클래스
	public static class Core {
		int row, col;
		
		public Core(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	
	/**
	 * 전선 연결
	 * @param idx		시작 코어 index
	 * @param dir		전선 연결 방향
	 * @param cores		코어 위치 저장 리스트
	 * @param state		프로세서 상태 2차원 배열
	 * @return			연결한 전선의 개수
	 */
	public static int connectWire(int idx, int dir, List<Core> cores, int[][] state) {
		Core curr = cores.get(idx);
		// 연결한 전선 개수
		int count = 0;
		int r = curr.row + dRow[dir];
		int c = curr.col + dCol[dir];
		
		int wire = getWire(idx);
		while(checkRange(r) && checkRange(c) && state[r][c] == BLANK) {
			// 전선 연결
			state[r][c] = wire;
			count++;
			// 다음 위치로 이동
			r += dRow[dir];
			c += dCol[dir];
		}
		
		return count;
	}
	
	// 위 connectWire의 결과와 비교했을 때 전선이 제대로 연결된 경우를 확인하기 위한 전선 길이를 반환
	public static int targetLength(int row, int col, int dir) {
		switch (dir) {
			case UP:
				return row;
			case RIGHT:
				return (N - 1) - col;
			case DOWN:
				return (N - 1) - row;
			case LEFT:
				return col;
			default:
				return -1;
		}
	}
	
	// 전선 연결 상태 이전으로 롤백
	public static void rollbackWire(int idx, int dir, List<Core> cores, int[][] state) {
		Core curr = cores.get(idx);
		
		int r = curr.row + dRow[dir];
		int c = curr.col + dCol[dir];
		
		int wire = getWire(idx);
		while(checkRange(r) && checkRange(c) && state[r][c] == wire) {
			// 전선 끊기
			state[r][c] = BLANK;
			// 다음 위치로 이동
			r += dRow[dir];
			c += dCol[dir];
		}
	}
	
	public static int getWire(int idx) {
		return idx + 2;
	}
}
