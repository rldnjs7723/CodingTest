import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 14614번 긴 사다리 게임
 * 문제 분류: 이분 탐색
 * @author Giwon
 */
public class Solution_14614 {
	private final static int CMD_INIT				= 1;
	private final static int CMD_ADD				= 2;
	private final static int CMD_REMOVE				= 3;
	private final static int CMD_NUMBER_OF_CROSS	= 4;
	private final static int CMD_PARTICIPANT		= 5;
	
	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(BufferedReader br) throws Exception
	{
		StringTokenizer st;

		int numQuery;

		int mX, mY, mID;

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
				usersolution.init();
				isCorrect = true;
				break;
			case CMD_ADD:
				mX = Integer.parseInt(st.nextToken());
				mY = Integer.parseInt(st.nextToken());
				usersolution.add(mX, mY);
				break;
			case CMD_REMOVE:
				mX = Integer.parseInt(st.nextToken());
				mY = Integer.parseInt(st.nextToken());
				usersolution.remove(mX, mY);
				break;
			case CMD_NUMBER_OF_CROSS:
				mID = Integer.parseInt(st.nextToken());
				userAns = usersolution.numberOfCross(mID);
				ans = Integer.parseInt(st.nextToken());
				if (userAns != ans)
				{
					isCorrect = false;
				}
				break;
			case CMD_PARTICIPANT:
				mX = Integer.parseInt(st.nextToken());
				mY = Integer.parseInt(st.nextToken());
				userAns = usersolution.participant(mX, mY);
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
		public static final int MAX = 1000000000;
		public static final int LEFT = 0, RIGHT = 1, LAST = -1;
		
		public static Ladder ladder;
		
		// 가로선
		public static class Horizontal {
			// 좌표
			int x, y;
			// 연결된 방향
			int dir;
			
			public Horizontal(int x, int y, int dir) {
				this.x = x;
				this.y = y;
				this.dir = dir;
			}

			@Override
			public String toString() {
				return "Horizontal [x=" + x + ", y=" + y + ", dir=" + dir + "]";
			}
		}
		
		// 세로선
		public static class Vertical {
			// 현재 x좌표
			int x;
			// 가로선
			List<Horizontal> lines;
			
			public Vertical(int x) {
				this.x = x;
				this.lines = new ArrayList<>();
				
				this.lines.add(new Horizontal(x, 0, LAST));
				this.lines.add(new Horizontal(x, MAX, LAST));
			}
			
			public Horizontal get(int index) {
				return lines.get(index);
			}
			
			// 현재 값보다 작거나 같은 가로선의 Index 리턴
			public int getIndex(int target) {
				int size = lines.size();
				return binarySearch(0, size - 1, target);
			}
			
			private int binarySearch(int start, int end, int target) {
				if(start == end) return start;
				
				int mid = start + (end - start) / 2 + 1;
				if(lines.get(mid).y <= target) return binarySearch(mid, end, target);
				else return binarySearch(start, mid - 1, target);
			}
			
			// 가로선 추가
			public void add(Horizontal line) {
				int index = getIndex(line.y) + 1;
				lines.add(index, line);
			}
			
			// 가로선 제거
			public void remove(int y) {
				int index = getIndex(y);
				lines.remove(index);
			}
		}
		
		public static class Ladder {
			// 세로선
			Vertical[] lines;
			
			public Ladder() {
				lines = new Vertical[101];
				
				for(int i = 1; i <= 100; i++) {
					lines[i] = new Vertical(i);
				}
			}
			
			// (mX, mY)와 (mX+1, mY)를 잇는 가로선 추가
			public void add(int mX, int mY) {
				lines[mX].add(new Horizontal(mX, mY, RIGHT));
				lines[mX + 1].add(new Horizontal(mX + 1, mY, LEFT));
			}
			
			// (mX, mY)와 (mX+1, mY)를 잇는 가로선 제거
			public void remove(int mX, int mY) {
				lines[mX].remove(mY);
				lines[mX + 1].remove(mY);
			}
			
			// mID번 참가자가 지나게 되는 가로줄의 개수 리턴
			public int numberOfCross(int mID) {
				// (mID, 0)에서 출발
				int x = mID;
				int y = 0;
				
				// 아래로 내려가기
				Horizontal line;
				int count = 0;
				while((line = lines[x].get(lines[x].getIndex(y) + 1)).dir != LAST) {
					// 가로선 건너기
					x = line.x + (line.dir == LEFT ? -1 : 1);
					y = line.y;
					count++;
				}
				
				return count;
			}
			
			// (mX, mY)를 지나는 참가자의 번호 리턴
			public int participant(int mX, int mY) {
				// (mX, mY)에서 출발
				// 가로줄과 세로줄이 만나는 지점이 아님을 보장
				int x = mX;
				int y = mY;
				
				// 위로 올라가기
				Horizontal line = lines[x].get(lines[x].getIndex(y));
				while(line.dir != LAST) {
					// 가로선 건너기
					x = line.x + (line.dir == LEFT ? -1 : 1);
					y = line.y;
					// 다음 가로선 탐색
					line = lines[x].get(lines[x].getIndex(y) - 1);
				}
				
				return line.x;
			}
		}
		
		public void init()
		{
			ladder = new Ladder();
		}

		public void add(int mX, int mY)
		{
			ladder.add(mX, mY);
		}

		public void remove(int mX, int mY)
		{
			ladder.remove(mX, mY);
		}

		public int numberOfCross(int mID)
		{
			return ladder.numberOfCross(mID);
		}

		public int participant(int mX, int mY)
		{
			return ladder.participant(mX, mY);
		}
	}
}