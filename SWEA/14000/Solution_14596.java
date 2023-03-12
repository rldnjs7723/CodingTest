import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 14596 섬지키기
 * 문제 분류: Flood Fill, 백트래킹, 완전 탐색
 * @author Giwon
 */
public class Solution_14596 {
	private final static int CMD_INIT					= 1;
	private final static int CMD_NUMBER_OF_CANDIDATE	= 2;
	private final static int CMD_MAX_AREA				= 3;
	
	private final static UserSolution usersolution = new UserSolution();

	private static int[][] mMap = new int[20][20];
	private static int[] mStructure = new int[5];

	private static boolean run(BufferedReader br) throws Exception
	{
		StringTokenizer st;

		int numQuery;
		int N, M, mSeaLevel;
		int userAns, ans;

		boolean isCorrect = false;

		numQuery = Integer.parseInt(br.readLine());

		for (int q = 0; q < numQuery; ++q)
		{
			st = new StringTokenizer(br.readLine(), " ");

			int cmd;
			cmd = Integer.parseInt(st.nextToken());

			switch (cmd)
			{
			case CMD_INIT:
				N = Integer.parseInt(st.nextToken());
				for (int i = 0; i < N; i++)
					for (int j = 0; j < N; j++)
						mMap[i][j] = Integer.parseInt(st.nextToken());
				usersolution.init(N, mMap);
				isCorrect = true;
				break;
			case CMD_NUMBER_OF_CANDIDATE:
				M = Integer.parseInt(st.nextToken());
				for (int i = 0; i < M; i++)
					mStructure[i] = Integer.parseInt(st.nextToken());
				userAns = usersolution.numberOfCandidate(M, mStructure);
				ans = Integer.parseInt(st.nextToken());
				if (userAns != ans)
				{
					isCorrect = false;
				}
				break;
			case CMD_MAX_AREA:
				M = Integer.parseInt(st.nextToken());
				for (int i = 0; i < M; i++)
					mStructure[i] = Integer.parseInt(st.nextToken());
				mSeaLevel = Integer.parseInt(st.nextToken());
				userAns = usersolution.maxArea(M, mStructure, mSeaLevel);
				ans = Integer.parseInt(st.nextToken());
				if (userAns != ans)
				{
					isCorrect = false;
				}
				break;
			default:
				isCorrect = false;
				break;
			}
		}
		return isCorrect;
	}

	public static void main(String[] args) throws Exception
	{
		int TC, MARK;
	
		System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase)
		{
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}
	
	public static class UserSolution
	{
		public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
		public static final int[] dRow = {0, -1, 0, 1};
		public static final int[] dCol = {-1, 0, 1, 0};
		
		// 가라 앉았는지 확인하는 객체는 하나면 충분. Singleton으로 구현 
		public static class Sink {
			private static Sink instance = new Sink();
			private Sink() {super();};
			public static Sink getInstance() {
				return instance;
			}
		};
		
		// BFS에 사용할 좌표 클래스
		public static class Coord {
			int row, col;
			
			public Coord(int row, int col) {
				this.row = row;
				this.col = col;
			}
		}
		
		// 설치할 수 있는 구조물 위치 표현 클래스
		public static class Structure extends Coord{
			// 회전을 몇 번 수행한 구조물을 설치 가능한지 표현
			int type;
			
			public Structure(int row, int col, int type) {
				super(row, col);
				this.type = type;
			}
		}
		
		int N;
		int[][] map;
		// 구조물 설치 가능한 위치 저장한 리스트
		public List<Structure> available;
		
		public void init(int N, int mMap[][])
		{
			// 사방의 모든 바다와 연결하기 위해 영역 전처리
			this.N = N;
			this.map = new int[N + 2][N + 2];
			
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					map[row + 1][col + 1] = mMap[row][col];
				}
			}
			
			this.available = new ArrayList<>();
		}

		public int numberOfCandidate(int M, int mStructure[])
		{
			mStructure = trimArray(M, mStructure);
			available.clear();
			// 180도 회전한 구조물
			int[] rStructure = reverse(mStructure);
			// 가로 방향 탐색
			int sum, i;
			for(int row = 1; row < N + 1; row++) {
				for(int col = 1; col < N + 1 - M + 1; col++) {
					// 설치 가능한지 판단
					sum = mStructure[0] + map[row][col];
					i = 1;
					for(i = 1; i < M; i++) {
						// 높이가 이전과 다르면 구조물을 설치할 수 없는 경우
						if(mStructure[i] + map[row][col + i] != sum) break;
					}
					// 구조물 설치 가능하면 count++
					if(i == M) {
						available.add(new Structure(row, col, 0));
						continue;
					}
					
					// 180도 회전해서 설치 가능한지 판단
					// 구조물의 중심을 기준으로 대칭인 경우에는 확인할 필요가 없음
					if(rStructure == null) continue;
					
					// 설치 가능한지 판단
					sum = rStructure[0] + map[row][col];
					i = 1;
					for(i = 1; i < M; i++) {
						// 높이가 이전과 다르면 구조물을 설치할 수 없는 경우
						if(rStructure[i] + map[row][col + i] != sum) break;
					}
					// 구조물 설치 가능하면 count++
					if(i == M) {
						available.add(new Structure(row, col, 2));
						continue;
					}
				}
			}
			
			// 구조물 길이가 1이라면 가로, 세로가 동일
			if(M == 1) return available.size();
			// 세로 방향 탐색
			for(int row = 1; row < N + 1 - M + 1; row++) {
				for(int col = 1; col < N + 1; col++) {
					// 설치 가능한지 판단
					sum = mStructure[0] + map[row][col];
					i = 1;
					for(i = 1; i < M; i++) {
						// 높이가 이전과 다르면 구조물을 설치할 수 없는 경우
						if(mStructure[i] + map[row + i][col] != sum) break;
					}
					// 구조물 설치 가능하면 count++
					if(i == M) {
						available.add(new Structure(row, col, 1));
						continue;
					}
					
					// 180도 회전해서 설치 가능한지 판단
					// 구조물의 중심을 기준으로 대칭인 경우에는 확인할 필요가 없음
					if(rStructure == null) continue;
					
					// 설치 가능한지 판단
					sum = rStructure[0] + map[row][col];
					i = 1;
					for(i = 1; i < M; i++) {
						// 높이가 이전과 다르면 구조물을 설치할 수 없는 경우
						if(rStructure[i] + map[row + i][col] != sum) break;
					}
					// 구조물 설치 가능하면 count++
					if(i == M) {
						available.add(new Structure(row, col, 3));
						continue;
					}
				}
			}
			
			return available.size();
		}

		public int maxArea(int M, int mStructure[], int mSeaLevel)
		{
			mStructure = trimArray(M, mStructure);
			int max = -1;
			// 설치 가능한 구조물 리스트 갱신
			numberOfCandidate(M, mStructure);
			// 모든 설치 가능한 구조물을 탐색하여 최대 지역 개수 탐색
			for(Structure structure: available) {
				// 구조물 설치
				buildStructure(structure, mStructure);
				// 입력 받은 해수면에 따라 남아 있는 지역 개수 탐색
				max = Math.max(max, getSinkState(mSeaLevel));
				// 구조물 롤백
				rollbackStructure(structure, mStructure);
			}
			
			return max;
		}
		
		private int[] trimArray(int M, int[] mStructure) {
			int[] result = new int[M];
			for(int i = 0; i < M; i++) {
				result[i] = mStructure[i];
			}
			
			return result;
		}
		
		// 현재 지도에 구조물을 설치
		private void buildStructure(Structure structure, int[] mStructure) {
			int M = mStructure.length;
			// 180도 회전 구조물
			int[] rStructure = reverse(mStructure);
			
			switch (structure.type) {
				// 0번 회전한 구조물 (가로)
				case 0:
					for(int i = 0; i < M; i++) {
						this.map[structure.row][structure.col + i] += mStructure[i];
					}
					break;
				// 1번 회전한 구조물 (세로)
				case 1:
					for(int i = 0; i < M; i++) {
						this.map[structure.row + i][structure.col] += mStructure[i];
					}
					break;
				// 2번 회전한 구조물 (가로)
				case 2:
					for(int i = 0; i < M; i++) {
						this.map[structure.row][structure.col + i] += rStructure[i];
					}
					break;
				// 3번 회전한 구조물 (세로)
				case 3:
					for(int i = 0; i < M; i++) {
						this.map[structure.row + i][structure.col] += rStructure[i];
					}
					break;
			}
		}
		
		// 구조물을 설치하기 전 상태로 롤백
		private void rollbackStructure(Structure structure, int[] mStructure) {
			int M = mStructure.length;
			// 180도 회전 구조물
			int[] rStructure = reverse(mStructure);
			
			switch (structure.type) {
				// 0번 회전한 구조물 (가로)
				case 0:
					for(int i = 0; i < M; i++) {
						this.map[structure.row][structure.col + i] -= mStructure[i];
					}
					break;
				// 1번 회전한 구조물 (세로)
				case 1:
					for(int i = 0; i < M; i++) {
						this.map[structure.row + i][structure.col] -= mStructure[i];
					}
					break;
				// 2번 회전한 구조물 (가로)
				case 2:
					for(int i = 0; i < M; i++) {
						this.map[structure.row][structure.col + i] -= rStructure[i];
					}
					break;
				// 3번 회전한 구조물 (세로)
				case 3:
					for(int i = 0; i < M; i++) {
						this.map[structure.row + i][structure.col] -= rStructure[i];
					}
					break;
			}
		}
		
		// 구조물 180도 회전
		private int[] reverse(int[] mStructure) {
			int M = mStructure.length;
			int[] result = new int[M];
			for(int i = 0; i < M; i++) {
				result[M - 1 - i] = mStructure[i];
			}
			
			// 구조물을 180도 회전한 결과가 기존 값과 동일한지 판단
			int i = 0;
			for(i = 0; i < M; i++) {
				if(result[i] != mStructure[i]) break;
			}
			// 동일하면 null 리턴
			if(i == M) return null;
			
			return result;
		}
		
		// 현재 지도 상태에서 해수면이 올라갔을 때 잠기지 않고 남아있는 지역의 개수 리턴
		private int getSinkState(int mSeaLevel) {
			Sink[][] state = new Sink[N + 2][N + 2];
			
			// 바다에서 시작하여 Flood Fill 알고리즘 수행
			int row = 0, col = 0;
			state[row][col] = Sink.getInstance();
			// BFS
			Queue<Coord> queue = new ArrayDeque<>();
			queue.offer(new Coord(row, col));
			Coord curr;
			int r, c;
			while(!queue.isEmpty()) {
				curr = queue.poll();
				
				for(int dir = 0; dir < 4; dir++) {
					r = curr.row + dRow[dir];
					c = curr.col + dCol[dir];
					
					// 현재 높이가 해수면보다 낮고, 방문하지 않은 경우 탐색
					if(checkRange(r) && checkRange(c) && 
							map[r][c] < mSeaLevel && state[r][c] == null) {
						state[r][c] = Sink.getInstance();
						queue.offer(new Coord(r, c));
					}
				}
			}
			
			// 아직 바다에 잠기지 않은 부분 개수 체크
			int count = 0;
			for(row = 1; row < N + 1; row++) {
				for(col = 1; col < N + 1; col++) {
					if(state[row][col] == null) count++;
				}
			}
			
			return count;
		}
		
		// 범위 체크
		private boolean checkRange(int target) {
			return target >= 0 && target < N + 2;
		}
		
		// 디버깅용 배열 출력
		public static void printArr(int[][] arr) {
			for(int[] inner: arr) {
				System.out.println(Arrays.toString(inner));
			}
			System.out.println("-------------");
		}
		public void printArr(Sink[][] arr) {
			StringBuilder sb = new StringBuilder();
			for(int row = 0; row < N + 2; row++) {
				for(int col = 0; col < N + 2; col++) {
					sb.append(arr[row][col] == null ? 'O' : 'X');
				}
				sb.append("\n");
			}
			System.out.print(sb.toString());
			System.out.println("-------------");
		}
	}
}