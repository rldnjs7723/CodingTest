import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class UserSolution
{
	// 병사 id 기준으로 저장한 배열
	Soldier[] idArr;
	// 팀 별 병사 관리 배열
	Team[] teamArr;
	
	 // 팀 정보 저장용 클래스
	 class Team {
		 int mTeam;
		 Soldier head, tail;
		 int[] teamUpdate;
		 int updateCount;
		 
		 public Team(int mTeam) {
			 this.mTeam = mTeam;
			 // 더미 헤드 설정
			 this.head = new Soldier(mTeam);
			 this.tail = this.head;
			 // 팀 업데이트 버퍼링 (10만 회 이하)
			 this.teamUpdate = new int[100000];
			 updateCount = 0;
		}
		 
		 public void add(int mID, int mTeam, int mScore) {
			 Soldier prev = this.tail;
			 // 신병 생성
			 Soldier recruit = new Soldier(mID, mTeam, mScore, prev, null, updateCount);
			 idArr[mID] = recruit;
			 // 마지막 원소의 다음 병사 설정
			 prev.next = recruit;
			 // 마지막 원소 변경
			 this.tail = recruit;
		 }
		 
		 public void delete(int mID) {
			 Soldier target = idArr[mID];
			 // 팀의 연결 갱신
			 Soldier prev = target.prev;
			 Soldier next = target.next;
			 prev.next = next;
			 if(next != null) {
				 next.prev = prev;
			 } else {
				 // 마지막 원소를 삭제했다면 tail 갱신
				 this.tail = prev;
			 }
			 // 병사 삭제
			 idArr[mID] = null;
		 }
		 
		 // updateTeam 버퍼링
		 public void update(int mChangeScore) {
			 teamUpdate[updateCount++] = mChangeScore;
		 }
		 
		 public int best() {
			 Soldier curr = this.head.next;
			 // 소속 병사가 한 명 이상 고용되어 있음이 보장
			 Soldier max = curr;
			 int mChangeScore, resultScore;
			 while(curr != null) {
				 // 점수 계산
				 resultScore = curr.mScore;
				// updateTeam 버퍼 Flush
				 for(int i = curr.lastIdx; i < updateCount; i++) {
					 mChangeScore = teamUpdate[i];
					 resultScore += mChangeScore;
					 // 점수 최대 5
					 resultScore = Math.min(5, resultScore);
					 // 점수 최소 1
					 resultScore = Math.max(1, resultScore);
				 }
				 
				 // 점수 업데이트
				 curr.update(resultScore);
				 
				 // 평판 점수가 같으면 고유번호가 가장 큰 병사의 고유번호 반환
				 if(curr.compare(max)) max = curr;
				 curr = curr.next;
			 }
			 
			 return max.mID;
		 }
	 }
	
	// 병사 정보 저장용 클래스
	class Soldier{
		int mID, mTeam, mScore, lastIdx;
		// 팀 연결용
		Soldier prev, next;

		public Soldier(int mTeam) {
			this.mID = -1;
			this.mTeam = mTeam;
			this.mScore = -1;
			this.prev = null;
			this.next = null;
		}
		
		public Soldier(int mID, int mTeam, int mScore, Soldier prev, Soldier next, int lastIdx) {
			this.mID = mID;
			this.mTeam = mTeam;
			this.mScore = mScore;
			this.prev = prev;
			this.next = next;
			this.lastIdx = lastIdx;
		}
		
		// 병사 점수 업데이트
		public void update(int mScore) {
			this.mScore = mScore;
			this.lastIdx = teamArr[this.mTeam].updateCount;
		}
		
		// 현재 병사가 더 크면 true
		public boolean compare(Soldier obj) {
			if(this.mScore == obj.mScore) {
				return this.mID > obj.mID;
			} else {
				return this.mScore > obj.mScore;
			}
		}
	}
	
	public void init()
	{
		// 1 <= mID <= 100000 
		idArr = new Soldier[100001];
		// 1 <= mTeam <= 5
		teamArr = new Team[6];
		for(int i = 1; i <= 5; i++) {
			teamArr[i] = new Team(i);
		}
	}
	
	public void hire(int mID, int mTeam, int mScore)
	{
		// 병사 고용
		teamArr[mTeam].add(mID, mTeam, mScore);
	}
	
	public void fire(int mID)
	{
		// 병사 해고
		teamArr[idArr[mID].mTeam].delete(mID);
	}

	public void updateSoldier(int mID, int mScore)
	{
		// 병사 점수 수정
		idArr[mID].update(mScore);
	}

	public void updateTeam(int mTeam, int mChangeScore)
	{
		// 팀 점수 수정
		teamArr[mTeam].update(mChangeScore);
	}
	
	public int bestSoldier(int mTeam)
	{
		// 평판 점수가 높은 병사 고유번호 반환
		return teamArr[mTeam].best();
	}
}

class Solution_13072
{
	private final static int CMD_INIT				= 1;
	private final static int CMD_HIRE				= 2;
	private final static int CMD_FIRE				= 3;
	private final static int CMD_UPDATE_SOLDIER		= 4;
	private final static int CMD_UPDATE_TEAM		= 5;
	private final static int CMD_BEST_SOLDIER		= 6;
	
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
					System.out.println(usersolution.idArr[userAns].mScore);
					System.out.println(usersolution.idArr[userAns].lastIdx);
					System.out.println(usersolution.idArr[ans].mScore);
					System.out.println(usersolution.idArr[ans].lastIdx);
					System.out.println("wrong answer: " + userAns + " " + ans);
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

		for (int testcase = 1; testcase <= TC; ++testcase)
		{
			int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}
}
