import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * SWEA 13084번 AI 로봇
 * 문제 분류: 자료 구조 (TreeMap, TreeSet)
 * @author Giwon
 */
class Solution_13084
{
    private final static int CALL_JOB				= 100;
    private final static int RETURN_JOB				= 200;
    private final static int BROKEN					= 300;
    private final static int REPAIR 				= 400;
    private final static int CHECK					= 500;

    private static UserSolution usersolution = new UserSolution();
    
    private static int run (BufferedReader br, int score) throws Exception 
    {
        int N, Q;
        int wIDCnt = 1;
        int cTime, mNum, rID, wID, mOpt;
        int res = -1, ans;
        
        N = Integer.parseInt(br.readLine());
        usersolution.init(N);

        Q = Integer.parseInt(br.readLine());
        
        while (Q-- > 0)
        {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int cmd = Integer.parseInt(st.nextToken());

            switch (cmd)
    		{
            case CALL_JOB:
                cTime = Integer.parseInt(st.nextToken());
                mNum = Integer.parseInt(st.nextToken());
                mOpt = Integer.parseInt(st.nextToken());
                res = usersolution.callJob(cTime, wIDCnt, mNum, mOpt);       
                ans = Integer.parseInt(st.nextToken());
                if (ans != res)
                    score = 0;
                wIDCnt++;
                break;
            case RETURN_JOB:
                cTime = Integer.parseInt(st.nextToken());
                wID = Integer.parseInt(st.nextToken());
                usersolution.returnJob(cTime, wID);
                break;
            case BROKEN:
                cTime = Integer.parseInt(st.nextToken());
                rID = Integer.parseInt(st.nextToken());
                usersolution.broken(cTime, rID);
                break;
            case REPAIR:
                cTime = Integer.parseInt(st.nextToken());
                rID = Integer.parseInt(st.nextToken());
                usersolution.repair(cTime, rID);
                break;
            case CHECK:
                cTime = Integer.parseInt(st.nextToken());
                rID = Integer.parseInt(st.nextToken());
                res = usersolution.check(cTime, rID);
                ans = Integer.parseInt(st.nextToken());;
                if (ans != res)
                    score = 0;
                break;
            default:
                score = 0;
                break;
            }       
        }

        return score;
    }
    
    public static void main(String[] args) throws Exception
    {
        System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(line.nextToken());
        int Ans = Integer.parseInt(line.nextToken());
        
        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            System.out.println("#" + testcase + " " + run(br, Ans));
        }
        
        br.close();
    }
    
    public static class UserSolution
    {
    	public static final int MAX_WORK = 50001;
    	
    	// 로봇 객체 매핑
    	public static Robot[] robotMap;
    	// 로봇 트레이닝 센터
    	public static Center center;
    	// 작업 객체 매핑
    	public static Work[] works;
    	
    	public static class Robot implements Comparable<Robot>{
    		// 로봇 고유 번호
    		int rID;
    		// 현재 투입된 작업
    		int wID;
    		// 고장 여부
    		boolean broken;
    		// 학습하지 못한 총 시간
    		int stopTime;
    		
    		public Robot(int rID) {
    			this.rID = rID;
    			this.wID = -1;
    			this.broken = false;
			}
    		
    		// 로봇 상태 확인
    		public int check(int cTime) {
    			if(broken) return 0;
    			else if(wID != -1) return -wID;
    			else return getIntelligence(cTime);
    		}
    		
    		// 로봇 수리
    		public void repair(int cTime) {
    			// 고장 나지 않은 경우 무시
    			if(!broken) return;
    			
    			// 수리
    			this.broken = false;
    			// 지능지수 0으로 설정
    			this.stopTime = cTime;
    			// 센터로 복귀
    			center.put(this);
    		}
    		
    		// 현재 로봇의 지능 지수 리턴
    		public int getIntelligence(int cTime) {
    			return cTime - stopTime;
    		}
    		
    		// 지능 지수가 높고, 고유 번호가 작은 순서대로 정렬
    		@Override
    		public int compareTo(Robot o) {
    			if(this.stopTime != o.stopTime) return Integer.compare(this.stopTime, o.stopTime);
    			return Integer.compare(this.rID, o.rID);
    		}

			@Override
			public String toString() {
				return "[rID=" + rID + "]";
			}
    	}
    	
    	public static class AI{
    		// 학습하지 못한 총 시간
    		int stopTime;
    		// 우선 순위 큐로 로봇 정렬
    		Queue<Robot> robotQueue;

    		public AI(int stopTime) {
				this.stopTime = stopTime;
				this.robotQueue = new PriorityQueue<>();
			}
    		
    		// 로봇 넣기
    		public void put(Robot robot) {
    			robotQueue.offer(robot);
    		}
    		
    		// 로봇 작업에 투입
    		public Robot get(int wID) {
    			Robot robot = robotQueue.poll();
    			robot.wID = wID;
    			
    			return robot;
    		}
    		
    		public boolean isEmpty() {
    			return robotQueue.isEmpty();
    		}

			@Override
			public String toString() {
				return "AI [robotQueue=" + robotQueue + "]";
			}
    	}
    	
    	public static class Center {
    		// 로봇 대기열
    		TreeMap<Integer, AI> robotPool;
    		
    		public Center(AI robots) {
				robotPool = new TreeMap<>();
				robotPool.put(robots.stopTime, robots);
			}
    		
    		// 높은 지능 / 낮은 지능 우선 방식으로 투입할 로봇 리턴
    		public Robot get(int wID, int mOpt) {
    			Entry<Integer, AI> entry = mOpt == 0 ? robotPool.firstEntry() : robotPool.lastEntry();
    			Robot robot = entry.getValue().get(wID);
    			// 마지막 로봇이었다면 Map에서 삭제
    			if(entry.getValue().isEmpty()) robotPool.remove(entry.getKey());
    			
    			return robot;
    		}
    		
    		// 로봇 추가
    		public void put(Robot robot) {
    			if(!robotPool.containsKey(robot.stopTime)) {
    				robotPool.put(robot.stopTime, new AI(robot.stopTime));
    			}
    			robotPool.get(robot.stopTime).put(robot);
    		}
    	}
    	
    	public static class Work {
    		// 작업 고유 번호
    		int wID;
    		// 작업 시작 시간
    		int startTime;
    		// 작업에 투입된 로봇
    		TreeSet<Robot> robots;
    		
    		public Work(int wID, int cTime) {
				this.wID = wID;
				this.startTime = cTime;
				this.robots = new TreeSet<>();
			}
    		
    		// 로봇 투입
    		public void put(Robot robot) {
    			robots.add(robot);
    		}
    		
    		// 고장난 로봇 제거
    		public void broken(Robot robot) {
    			robot.broken = true;
    			robot.wID = -1;
    			robots.remove(robot);
    		}
    		
    		// 작업 완료 후 로봇 복귀
    		public void finishJob(int cTime) {
    			for(Robot robot: robots) {
    				// 작업 완료
    				robot.wID = -1;
    				// 학습하지 못 한 시간 추가
    				robot.stopTime += cTime - startTime;
    				// 센터로 복귀
    				center.put(robot);
    			}
    		}

			@Override
			public String toString() {
				return "Work [wID=" + wID + ", startTime=" + startTime + ", robots=" + robots + "]";
			}
    	}
    	
    	public void init(int N)
    	{
    		// 로봇 객체 생성
    		robotMap = new Robot[N + 1];
    		AI robots = new AI(0);
    		for(int i = 1; i <= N; i++) {
    			robotMap[i] = new Robot(i);
    			robots.put(robotMap[i]);
    		}
    		// 트레이닝 센터 생성
    		center = new Center(robots);
    		
    		// 작업 배열 생성
    		works = new Work[MAX_WORK];
    	}

    	public int callJob(int cTime, int wID, int mNum, int mOpt)
    	{
    		// 투입된 로봇의 고유 번호 총합 계산
    		int sum = 0;
    		works[wID] = new Work(wID, cTime);
    		Work work = works[wID];
    		Robot robot;
    		for(int i = 0; i < mNum; i++) {
    			robot = center.get(wID, mOpt);
    			sum += robot.rID;
    			// 작업에 투입
    			work.put(robot);
    		}
    		
    		return sum;
    	}

    	public void returnJob(int cTime, int wID)
    	{
    		Work work = works[wID];
    		// 작업 완료
    		work.finishJob(cTime);
    		works[wID] = null;
    	}

    	public void broken(int cTime, int rID)
    	{
    		Robot robot = robotMap[rID];
    		// 이미 고장 난 경우 무시
    		if(robot.broken) return;
    		// 작업 중이 아닌 경우 무시
    		if(robot.wID == -1) return;
    		
    		// 로봇 고장
    		works[robot.wID].broken(robot);
    	}

    	public void repair(int cTime, int rID)
    	{
    		// 로봇 수리
    		robotMap[rID].repair(cTime);
    	}

    	public int check(int cTime, int rID)
    	{
    		// 로봇 상태 확인
    		return robotMap[rID].check(cTime);
    	}
    }
}