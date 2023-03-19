//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.StringTokenizer;
//
///**
// * SWEA 14613번 리스트 복사
// * 문제 분류: Union Find
// * @author Giwon
// */
//public class Solution_14613 {
//	private final static int CMD_INIT			= 100;
//	private final static int CMD_MAKE_LIST		= 200;
//	private final static int CMD_COPY_LIST		= 300;
//	private final static int CMD_UNDATE_ELEMENT	= 400;
//	private final static int CMD_ELEMENT		= 500;
//	
//	private final static UserSolution usersolution = new UserSolution();
//	
//	private static int mSeed;
//	private static int pseudo_rand()
//	{
//		mSeed = mSeed * 214013 + 2531011;
//		return (mSeed >> 16) & 0x7FFF;
//	}
//
//	private static char[] mName = new char[21];
//	private static char[] mDest = new char[21];
//	private static char[] mSrc = new char[21];
//	private static int[] mListValue = new int[200000];
//
//	private static void generateName(char[] name, int seed)
//	{
//		mSeed = seed;
//		int name_len = pseudo_rand() % 20 + 1;
//		for (int i = 0; i < name_len; ++i)
//		{
//			name[i] = (char)(pseudo_rand() % 26 + 'a');
//		}
//		name[name_len] = '\0';
//	}
//
//	private static int generateList(int[] listValue, int seed)
//	{
//		mSeed = seed;
//		int length = pseudo_rand() << 15;
//		length = (length + pseudo_rand()) % 200000 + 1;
//		for (int i = 0; i < length; ++i)
//		{
//			listValue[i] = pseudo_rand();
//		}
//		return length;
//	}
//
//	private static boolean run(BufferedReader br) throws Exception
//	{
//		StringTokenizer st;
//
//		int numQuery;
//
//		int seed;
//		int mIndex, mValue, mCopy, mLength;
//		int userAns, ans;
//		boolean isCorrect = false;
//
//		numQuery = Integer.parseInt(br.readLine());
//
//		for (int q = 0; q < numQuery; ++q)
//		{
//			st = new StringTokenizer(br.readLine(), " ");
//
//			int cmd;
//			cmd = Integer.parseInt(st.nextToken());
//
//			switch (cmd)
//			{
//			case CMD_INIT:
//				usersolution.init();
//				isCorrect = true;
//				break;
//			case CMD_MAKE_LIST:
//				seed = Integer.parseInt((st.nextToken()));
//				generateName(mName, seed);
//				seed = Integer.parseInt((st.nextToken()));
//				mLength = generateList(mListValue, seed);
//				usersolution.makeList(mName, mLength, mListValue);
//				break;
//			case CMD_COPY_LIST:
//				seed = Integer.parseInt((st.nextToken()));
//				generateName(mDest, seed);
//				seed = Integer.parseInt((st.nextToken()));
//				generateName(mSrc, seed);
//				mCopy = Integer.parseInt((st.nextToken()));
//				usersolution.copyList(mDest, mSrc, (mCopy != 0));
//				break;
//			case CMD_UNDATE_ELEMENT:
//				seed = Integer.parseInt((st.nextToken()));
//				generateName(mName, seed);
//				mIndex = Integer.parseInt((st.nextToken()));
//				mValue = Integer.parseInt((st.nextToken()));
//				usersolution.updateElement(mName, mIndex, mValue);
//				break;
//			case CMD_ELEMENT:
//				seed = Integer.parseInt((st.nextToken()));
//				generateName(mName, seed);
//				mIndex = Integer.parseInt((st.nextToken()));
//				userAns = usersolution.element(mName, mIndex);
//				ans = Integer.parseInt((st.nextToken()));
//				if (userAns != ans)
//				{
//					isCorrect = false;
//				}
//				break;
//			default:
//				isCorrect = false;
//				break;
//			}
//		}
//		return isCorrect;
//	}
//
//	public static void main(String[] args) throws Exception
//	{
//		int TC, MARK;
//	
//		System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
//		
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
//		
//		TC = Integer.parseInt(st.nextToken());
//		MARK = Integer.parseInt(st.nextToken());
//
//		for (int testcase = 1; testcase <= TC; ++testcase)
//		{
//			int score = run(br) ? MARK : 0;
//            System.out.println("#" + testcase + " " + score);
//		}
//
//		br.close();
//	}
//	
//	public static class UserSolution
//	{
//		public static final boolean DEEP = true, SHALLOW = false;
//		// 주소 카운트
//		int addressCount;
//		// 수정횟수 카운트
//		int updateCount;
//		Map<String, Integer> address;
//		Map<Integer, OnDemandCopyArray> arrs;
//		
//		public class UpdateLog {
//			// 수정된 숫자
//			int val;
//			// 수정된 시간
//			int time;
//			// 수정본 이전
//			UpdateLog next;
//			
//			public UpdateLog(int val, int time, UpdateLog next) {
//				this.val = val;
//				this.time = time;
//				this.next = next;
//			}
//			
//			public UpdateLog(int val) {
//				this(val, 0, null);
//			}
//		}
//		
//		public class CopyArray {
//			// 복사한 시간
//			int time;
//			// Shallow Copy / Deep Copy 여부
//			boolean type;
//			
//			public CopyArray(int time, boolean type) {
//				this.time = time;
//				this.type = type;
//			}
//		}
//		
//		// 값이 변경되지 않았다면 Shallow Copy한 배열의 값을 참조.
//		// 값이 변경되었다면 변경된 값을 참조
//		public class OnDemandCopyArray {
//			// 배열 길이
//			int length;
//			// 각 배열이 언제 Copy 되었는지 기록
//			Map<Integer, CopyArray> copyTime;
//			// 배열 각 Index 별 Update Log 기록
//			UpdateLog[] values;
//
//			public OnDemandCopyArray(char mName[], int mLength, int mListValue[]) {
//				// 초기 배열 이름
//				String name = charArrayToString(mName);
//				int addr = address.get(name);
//				
//				// 배열 길이
//				length = mLength;
//				// 각 배열이 언제 Copy 되었는지 기록
//				copyTime = new HashMap<>();
//				copyTime.put(addr, new CopyArray(0, DEEP));
//				
//				// 배열 생성
//				values = new UpdateLog[length];
//				for(int i = 0; i < length; i++) {
//					values[i] = new UpdateLog(mListValue[i]);
//				}
//				
//				// 배열 연결
//				arrs.put(addr, this);
//			}
//			
//			/**
//			 * 배열 복사
//			 * @param mDest		목적 배열 이름
//			 * @param mSrc		복사할 배열 이름
//			 * @param type		Deep Copy 1 / Shallow Copy 2
//			 */
//			public void copy(char[] mDest, char[] mSrc, boolean mCopy) {
//				int destAddr = address.get(charArrayToString(mDest));
//				// 근원 배열 찾기
//				int srcAddr = findSource(address.get(charArrayToString(mSrc)));
//				
//				if(mCopy == SHALLOW) {
//					// Shallow Copy
//					
//					
//					shallowCopyAddress.put(destAddr, srcAddr);
//				} else if(mCopy == DEEP) {
//					// Deep Copy
//					deepCopyAddress.put(destAddr, srcAddr);
//					
//					// Source 배열에서 Dest 배열로 갈 수 있도록 설정
//					if(!deepCopyChild.containsKey(srcAddr)) {
//						deepCopyChild.put(srcAddr, new ArrayList<>());
//					}
//					deepCopyChild.get(srcAddr).add(destAddr);
//				}
//				
//				// 배열 연결
//				arrs.put(destAddr, this);
//			}
//			
//			// Shallow Copy에서 복사한 값의 근원 찾기
//			public int findSource(int addr) {
//				if(!shallowCopyAddress.containsKey(addr)) return addr;
//				
//				// 마지막으로 참조하는 Shallow Copy 가리키도록 하기
//				shallowCopyAddress.put(addr, findSource(shallowCopyAddress.get(addr)));
//				return shallowCopyAddress.get(addr);
//			}
//			
//			// Deep Copy에서 복사한 근원 값 찾기
//			public int findValue(int addr, int idx) {
//				// 근원 값이라면 현재 값 리턴
//				if(!deepCopyAddress.containsKey(addr)) {
//					return values[idx].get(addr);
//				}
//				// 이미 값을 수정했다면 해당 값 리턴
//				if(values[idx].containsKey(addr)) return values[idx].get(addr);
//				
//				// 값을 수정하지 않았다면 DeepCopy 목표가 되는 값 리턴
//				// 중간에 참조하는 값 복사할 수 있는 경우 복사해놓기
//				values[idx].put(addr, findValue(deepCopyAddress.get(addr), idx));
//				return values[idx].get(addr);
//			}
//			
//			// 특정 Index의 값을 수정
//			public void updateElement(char[] mName, int idx, int val) {
//				// 근원 배열 찾기
//				int srcAddr = findSource(address.get(charArrayToString(mName)));
//				// 수정하기 이전 값 저장
//				int temp = findValue(srcAddr, idx);
//				
//				// 값 수정
//				values[idx].put(srcAddr, val);
//				
//				// 현재 근원 배열을 Deep Copy로 참조하는 경우 새로 갱신해주기
//				if(deepCopyChild.containsKey(srcAddr)) {
//					for(int child: deepCopyChild.get(srcAddr)) {
//						if(!values[idx].containsKey(child)) {
//							values[idx].put(child, temp);
//						}
//					}
//				}
//			}
//			
//			// 특정 Index의 값을 리턴
//			public int getElement(char[] mName, int idx) {
//				// 근원 배열 찾기
//				int addr = findSource(address.get(charArrayToString(mName)));
//				// 값 찾기
//				return findValue(addr, idx);
//			}
//		}
//		
//		public static String charArrayToString(char[] name) {
//			StringBuilder sb = new StringBuilder();
//			for (int i=0;name[i]!=0;i++){
//	        	sb.append(name[i]);
//	        }
//			
//			return sb.toString();
//		}
//		
//		public void init()
//		{
//			// 주소 맵 초기화
//			address = new HashMap<>();
//			addressCount = 0;
//			// Copy를 연결할 HashMap 초기화
//			arrs = new HashMap<>();
//		}
//
//		public void makeList(char mName[], int mLength, int mListValue[])
//		{
//			String name = charArrayToString(mName);
//			address.put(name, addressCount++);
//			new OnDemandCopyArray(mName, mLength, mListValue);
//		}
//
//		public void copyList(char mDest[], char mSrc[], boolean mCopy)
//		{
//			int srcAddr = address.get(charArrayToString(mSrc));
//			String name = charArrayToString(mDest);
//			address.put(name, addressCount++);
//			OnDemandCopyArray arr = arrs.get(srcAddr);
//			arr.copy(mDest, mSrc, mCopy);
//		}
//
//		public void updateElement(char mName[], int mIndex, int mValue)
//		{
//			int addr = address.get(charArrayToString(mName));
//			OnDemandCopyArray arr = arrs.get(addr);
//			arr.updateElement(mName, mIndex, mValue);
//		}
//
//		public int element(char mName[], int mIndex)
//		{
//			int addr = address.get(charArrayToString(mName));
//			OnDemandCopyArray arr = arrs.get(addr);
//			return arr.getElement(mName, mIndex);
//		}
//	}
//}