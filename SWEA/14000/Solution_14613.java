import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * SWEA 14613번 리스트 복사
 * 문제 분류: Union Find, 자료구조 (LinkedList, HashMap)
 * @author Giwon
 */
public class Solution_14613 {
	private final static int CMD_INIT			= 100;
	private final static int CMD_MAKE_LIST		= 200;
	private final static int CMD_COPY_LIST		= 300;
	private final static int CMD_UNDATE_ELEMENT	= 400;
	private final static int CMD_ELEMENT		= 500;
	
	private final static UserSolution usersolution = new UserSolution();
	
	private static int mSeed;
	private static int pseudo_rand()
	{
		mSeed = mSeed * 214013 + 2531011;
		return (mSeed >> 16) & 0x7FFF;
	}

	private static char[] mName = new char[21];
	private static char[] mDest = new char[21];
	private static char[] mSrc = new char[21];
	private static int[] mListValue = new int[200000];

	private static void generateName(char[] name, int seed)
	{
		mSeed = seed;
		int name_len = pseudo_rand() % 20 + 1;
		for (int i = 0; i < name_len; ++i)
		{
			name[i] = (char)(pseudo_rand() % 26 + 'a');
		}
		name[name_len] = '\0';
	}

	private static int generateList(int[] listValue, int seed)
	{
		mSeed = seed;
		int length = pseudo_rand() << 15;
		length = (length + pseudo_rand()) % 200000 + 1;
		for (int i = 0; i < length; ++i)
		{
			listValue[i] = pseudo_rand();
		}
		return length;
	}

	private static boolean run(BufferedReader br) throws Exception
	{
		StringTokenizer st;

		int numQuery;

		int seed;
		int mIndex, mValue, mCopy, mLength;
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
			case CMD_MAKE_LIST:
				seed = Integer.parseInt((st.nextToken()));
				generateName(mName, seed);
				seed = Integer.parseInt((st.nextToken()));
				mLength = generateList(mListValue, seed);
				usersolution.makeList(mName, mLength, mListValue);
				break;
			case CMD_COPY_LIST:
				seed = Integer.parseInt((st.nextToken()));
				generateName(mDest, seed);
				seed = Integer.parseInt((st.nextToken()));
				generateName(mSrc, seed);
				mCopy = Integer.parseInt((st.nextToken()));
				usersolution.copyList(mDest, mSrc, (mCopy != 0));
				break;
			case CMD_UNDATE_ELEMENT:
				seed = Integer.parseInt((st.nextToken()));
				generateName(mName, seed);
				mIndex = Integer.parseInt((st.nextToken()));
				mValue = Integer.parseInt((st.nextToken()));
				usersolution.updateElement(mName, mIndex, mValue);
				break;
			case CMD_ELEMENT:
				seed = Integer.parseInt((st.nextToken()));
				generateName(mName, seed);
				mIndex = Integer.parseInt((st.nextToken()));
				userAns = usersolution.element(mName, mIndex);
				ans = Integer.parseInt((st.nextToken()));
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
	
		System.setIn(new java.io.FileInputStream("sample_input.txt"));
		
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
		int count = 0;
		
		final boolean DEEP = true, SHALLOW = false;
		final int NULL = -1;
		Map<String, OnDemandCopyArray> address;
		
		public class CopyInfo {
			// 복사된 배열 ID;
			int id;
			// 복사한 부모 배열 ID
			int parentID;
			// 복사한 시간
			int timestamp;
			
			public CopyInfo(int id, int parentID, int timestamp) {
				this.id = id;
				this.parentID = parentID;
				this.timestamp = timestamp;
			}

			@Override
			public String toString() {
				return "CopyInfo [id=" + id + ", parentID=" + parentID + ", timestamp=" + timestamp + "]";
			}
		}
		
		public class UpdateInfo {
			// 수정한 배열 ID
			int id;
			// 수정된 숫자
			int val;
			// 수정된 시간
			int timestamp;
			// 이전 수정 기록 가리키는 포인터
			UpdateInfo next;
			
			public UpdateInfo(int id, int val, int timestamp, UpdateInfo next) {
				this.id = id;
				this.val = val;
				this.timestamp = timestamp;
				this.next = next;
			}

			@Override
			public String toString() {
				return "UpdateInfo [id=" + id + ", val=" + val + ", timestamp=" + timestamp + ", next=" + next + "]";
			}
		}
		
		// 값이 변경되지 않았다면 Shallow Copy한 배열의 값을 참조.
		// 값이 변경되었다면 변경된 값을 참조
		public class OnDemandCopyArray {
			// 배열 길이
			int length;
			// 현재 배열을 기준으로 Deep Copy를 수행한 배열 개수
			int maxID;
			// 문자열 이름과 Deep Copy를 수행한 부모 배열 매핑
			Map<String, Integer> copyMap;
			// Deep Copy를 수행한 배열의 Timestamp 기록
			Map<Integer, CopyInfo> copyInfoMap;
			// 현재 배열을 기준으로 Copy한 횟수 + Update한 횟수
			int time;
			// 배열에서 값 수정한 정보 LinkedList로 기록
			UpdateInfo[] updateLog;
			
			public OnDemandCopyArray(char mName[], int mLength, int mListValue[]) {
				// 초기 배열 이름
				String name = charArrayToString(mName);
				
				// 배열 길이
				length = mLength;
				
				// 각 배열에 id 부여
				int currID = maxID = 0;
				copyMap = new HashMap<>();
				copyMap.put(name, maxID++);
				
				// 각 배열이 언제 Copy 되었는지 기록
				time = 0;
				copyInfoMap = new HashMap<>();
				copyInfoMap.put(currID, new CopyInfo(currID, NULL, time++));
				int currTime = time;
				
				// 배열 생성
				updateLog = new UpdateInfo[length];
				for(int i = 0; i < length; i++) {
					updateLog[i] = new UpdateInfo(currID, mListValue[i], currTime, null);
				}
				
				// 배열 연결
				address.put(name, this);
			}
			
			/**
			 * 배열 복사
			 * @param mDest		목적 배열 이름
			 * @param mSrc		복사할 배열 이름
			 * @param type		Deep Copy 1 / Shallow Copy 2
			 */
			public void copy(char[] mDest, char[] mSrc, boolean mCopy) {
				String destName = charArrayToString(mDest);
				String srcName = charArrayToString(mSrc);
				// 복사할 배열의 ID
				int srcID = copyMap.get(srcName);
				
				if(mCopy == SHALLOW) {
					// Shallow Copy
					copyMap.put(destName, srcID);
				} else if(mCopy == DEEP) {
					// Deep Copy
					int currTime = time++;
					int currID = maxID++;
					
					// 새로운 ID에 매핑
					copyMap.put(destName, currID);
					// 새로운 ID 복제한 Timestamp 기록
					copyInfoMap.put(currID, new CopyInfo(currID, srcID, currTime));
				}
				
				// 배열 연결
				address.put(destName, this);
			}
			
			// 특정 Index의 값을 수정
			public void updateElement(char[] mName, int mIndex, int mValue) {
				String name = charArrayToString(mName);
				int id = copyMap.get(name);
				int timestamp = time++;
				
				// 수정 로그 기록
				updateLog[mIndex] = new UpdateInfo(id, mValue, timestamp, updateLog[mIndex]);
			}
			
			// 특정 Index의 값을 리턴
			public int getElement(char[] mName, int mIndex) {
//				System.out.println(count++);
				
				String name = charArrayToString(mName);
				int id = copyMap.get(name);
				CopyInfo copyinfo = copyInfoMap.get(id);
				
				// 자신이 복제된 Timestamp 이후에 자신 값을 수정한 부분이 존재하면 가장 최근 값을 선택
				UpdateInfo log = updateLog[mIndex];
				while(log != null && log.timestamp > copyinfo.timestamp) {
					if(log.id == id) return log.val;
					log = log.next;
				}
				
				// 부모 배열들의 ID를 담은 HashSet 구성
				Set<Integer> parentID = new TreeSet<>(Collections.reverseOrder());
				List<CopyInfo> parentCopyInfo = new ArrayList<>();
				while(copyinfo.parentID != NULL) {
					parentID.add(copyinfo.parentID);
					copyinfo = copyInfoMap.get(copyinfo.parentID);
					parentCopyInfo.add(copyinfo);
				}
				
				// 자신이 복제된 Timestamp 이전의 값들 중 자신보다 ID가 작은 배열들의 최신 수정 내역을 파악
				int[] recent = new int[maxID];
				Arrays.fill(recent, NULL);
				boolean available;
				while(log != null) {
					if(recent[log.id] == NULL) {
						available = true;
						for(CopyInfo info: parentCopyInfo) {
							// 현재 log보다 부모 배열이거나 같은 배열이라면 중단
							if(info.id <= log.id) break;
							
							// 부모 배열 중에서 Deep Copy를 더 먼저 한 경우는 생략
							if(info.timestamp < log.timestamp) {
								available = false;
								break;
							}
						}
						
						if(available) recent[log.id] = log.val;
					}
					log = log.next;
				}
				
				// 수정 내역 중 ID가 가장 큰 배열의 값을 반환
				for(int idx: parentID) {
					if(recent[idx] != NULL) return recent[idx];
				}
				
				return NULL;
			}
		}
		
		// 이름 문자열로 변환
		public static String charArrayToString(char[] mName) {
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while(mName[i] != '\0') {
				sb.append(mName[i++]);
			}
			
			return sb.toString();
		}
		
		public void init()
		{
			// 주소 맵 초기화
			address = new HashMap<>();
		}

		public void makeList(char mName[], int mLength, int mListValue[])
		{
			new OnDemandCopyArray(mName, mLength, mListValue);
		}

		public void copyList(char mDest[], char mSrc[], boolean mCopy)
		{
			String name = charArrayToString(mSrc);
			address.get(name).copy(mDest, mSrc, mCopy);
		}

		public void updateElement(char mName[], int mIndex, int mValue)
		{
			String name = charArrayToString(mName);
			address.get(name).updateElement(mName, mIndex, mValue);
		}

		public int element(char mName[], int mIndex)
		{
			String name = charArrayToString(mName);
			return address.get(name).getElement(mName, mIndex);
		}
	}
}