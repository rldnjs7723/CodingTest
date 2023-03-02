import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 13072번 병사관리
 * 문제 분류: 자료구조 (LinkedList), 구현
 * @author Giwon
 */
public class Solution_13072
{
	private final static int CMD_INIT				= 1;
	private final static int CMD_HIRE				= 2;
	private final static int CMD_FIRE				= 3;
	private final static int CMD_UPDATE_SOLDIER		= 4;
	private final static int CMD_UPDATE_TEAM		= 5;
	private final static int CMD_BEST_SOLDIER		= 6;
	static int testcase;
	
	private final static UserSolution usersolution = new UserSolution();
	
	private static boolean run(BufferedReader br) throws Exception
	{
		StringTokenizer st;
		
		int numQuery;

		int mID, mTeam, mScore, mChangeScore;
	
		int userAns, ans;
	
		boolean isCorrect = false;

		numQuery = Integer.parseInt(br.readLine());
		
		for (int q = 0; q < numQuery; ++q)
		{
			st = new StringTokenizer(br.readLine(), " ");
			
			int cmd;
			cmd = Integer.parseInt(st.nextToken());

			switch(cmd)
			{
			case CMD_INIT:
				usersolution.init();
				isCorrect = true;
				break;
			case CMD_HIRE:
				mID = Integer.parseInt(st.nextToken());
				mTeam = Integer.parseInt(st.nextToken());
				mScore = Integer.parseInt(st.nextToken());
				usersolution.hire(mID, mTeam, mScore);
				break;
			case CMD_FIRE:
				mID = Integer.parseInt(st.nextToken());
				usersolution.fire(mID);
				break;
			case CMD_UPDATE_SOLDIER:
				mID = Integer.parseInt(st.nextToken());
				mScore = Integer.parseInt(st.nextToken());
				usersolution.updateSoldier(mID, mScore);
				break;
			case CMD_UPDATE_TEAM:
				mTeam = Integer.parseInt(st.nextToken());
				mChangeScore = Integer.parseInt(st.nextToken());
				usersolution.updateTeam(mTeam, mChangeScore);
				break;
			case CMD_BEST_SOLDIER:
				mTeam = Integer.parseInt(st.nextToken());
				userAns = usersolution.bestSoldier(mTeam);
				ans = Integer.parseInt(st.nextToken());
				if (userAns != ans) {
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
	
		System.setIn(new java.io.FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (testcase = 1; testcase <= TC; ++testcase)
		{
			int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}
	
	public static class UserSolution
	{	
		 // 팀 정보 저장용 클래스
		 static class Team {
			 int mTeam, mScore;
			 Soldier head, tail;
			 
			 public Team(int mTeam, int mScore) {
				 this.mTeam = mTeam;
				 this.mScore = mScore;
				 // 더미 헤드 설정
				 this.head = new Soldier(mTeam);
				 this.tail = this.head;
			 }
			 
			 public void add(int mID, int mTeam) {
				 Soldier prev = this.tail;
				 // 신병 생성
				 Soldier recruit = new Soldier(mID, mTeam, prev, null);
				 idArr[mID] = recruit;
				 // 마지막 원소의 다음 병사 설정
				 prev.next = recruit;
				 // 마지막 원소 변경
				 this.tail = recruit;
			 }
			 
			 // updateTeam 수행하기 위한 메서드. 다른 Team을 현재 Team에 병합
			 public void update(Team other) {
				 // 크기가 0이라면 아무 일도 일어나지 않음
				 if(other.isEmpty()) return;
				 
				 // 다른 팀의 첫 번째 노드를 현재 팀의 tail 뒤에 붙임
				 this.tail.next = other.head.next;
				 other.head.next.prev = this.tail;
				 // 다른 팀의 tail이 현재 팀의 tail이 됨
				 this.tail = other.tail;
				 // 다른 팀의 크기는 0인 리스트가 됨
				 other.head.next = null;
				 other.tail = other.head;
			 }
			 
			 // updateTeam 수행
			 public static void update(int mTeam, int mChangeScore) {
				 // 수정한 점수가 0이라면 아무 일도 일어나지 않음
				 if(mChangeScore == 0) return;
				 
				 // 수정한 점수가 양수일 때
				 if(mChangeScore > 0) {
					 // 점수가 5가 될 팀
					 for(int i = 4; i >= 5 - mChangeScore; i--) {
						 teamArr[mTeam][5].update(teamArr[mTeam][i]);
					 }
					 // 나머지는 mChangeScore만큼 점수 상승
					 for(int i = 4 - mChangeScore; i >= 1; i--) {
						 teamArr[mTeam][i + mChangeScore].update(teamArr[mTeam][i]);
					 }
				 } else {
					 // 수정한 점수가 음수일 때
					// 점수가 1이 될 팀
					 for(int i = 2; i <= 1 - mChangeScore; i++) {
						 teamArr[mTeam][1].update(teamArr[mTeam][i]);
					 }
					 // 나머지는 mChangeScore만큼 점수 감소
					 for(int i = 2 - mChangeScore; i <= 5; i++) {
						 teamArr[mTeam][i + mChangeScore].update(teamArr[mTeam][i]);
					 }
				 }
			 }
			 
			 public static int best(int mTeam) {
				 Team maxTeam = null;
				 // 최소 한 명의 병사가 있음을 보장
				 for(int i = 5; i >= 1; i--) {
					 // 가장 점수가 높은 팀을 선택
					 if(!teamArr[mTeam][i].isEmpty()) {
						 maxTeam = teamArr[mTeam][i];
						 break;
					 }
				 }
				 
				 Soldier curr = maxTeam.head.next;
				 Soldier max = curr;
				 while(curr != null) {
					 // 현재 병사의 mID가 더 크면 더 큰 병사 리턴
					 if(curr.compare(max)) max = curr;
					 curr = curr.next;
				 }
				 
				 return max.mID;
			 }
			 
			 public boolean isEmpty() {
				 return this.head.next == null;
			 }
		 }
		
		// 병사 정보 저장용 클래스
		static class Soldier{
			int mID, mTeam;
			// 팀 연결용
			Soldier prev, next;

			public Soldier(int mTeam) {
				this.mID = -1;
				this.mTeam = mTeam;
				this.prev = null;
				this.next = null;
			}
			
			public Soldier(int mID, int mTeam, Soldier prev, Soldier next) {
				this.mID = mID;
				this.mTeam = mTeam;
				this.prev = prev;
				this.next = next;
			}
			
			public void delete() {
				 // 팀의 연결 갱신
				 Soldier prev = this.prev;
				 Soldier next = this.next;
				 prev.next = next;
				 if(next != null) {
					 next.prev = prev;
				 } else {
					 // 마지막 원소를 삭제했다면 tail 갱신
					 Team team;
					 int i = 0;
					 for(i = 1; i <= 5; i++) {
						 team = teamArr[this.mTeam][i];
						 if(team.tail == this) {
							 team.tail = prev;
							 break;
						 }
					 }
				 }
				 // 병사 삭제
				 idArr[mID] = null;
			 }
			
			public boolean compare(Soldier obj) {
				return this.mID > obj.mID;
			}
		}
		
		// 병사 id 기준으로 저장한 배열
		static Soldier[] idArr;
		// 팀 별 병사 관리 배열
		static Team[][] teamArr;
		
		public void init()
		{
			// 1 <= mID <= 100000 
			idArr = new Soldier[100001];
			// 1 <= mTeam <= 5 && mScore가 1점인 병사부터 5점인 병사를 나눠서 관리할 배열
			teamArr = new Team[6][6];
			for(int mTeam = 1; mTeam <= 5; mTeam++) {
				for(int mScore = 1; mScore <= 5; mScore++) {
					teamArr[mTeam][mScore] = new Team(mTeam, mScore);
				}
			}
		}
		
		public void hire(int mID, int mTeam, int mScore)
		{
			// 병사 고용
			teamArr[mTeam][mScore].add(mID, mTeam);
		}
		
		public void fire(int mID)
		{
			// 병사 해고
			idArr[mID].delete();;
		}

		public void updateSoldier(int mID, int mScore)
		{
			int mTeam = idArr[mID].mTeam;
			// 병사 삭제 후 새로 생성
			fire(mID);
			hire(mID, mTeam, mScore);
		}

		public void updateTeam(int mTeam, int mChangeScore)
		{
			// 팀 점수 수정
			Team.update(mTeam, mChangeScore);
		}
		
		public int bestSoldier(int mTeam)
		{
			// 평판 점수가 높은 병사 고유번호 반환
			return Team.best(mTeam);
		}
	}
}
