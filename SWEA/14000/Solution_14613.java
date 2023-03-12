import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * SWEA 14613번 리스트 복사
 * 문제 분류: 
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
		// 값이 변경되지 않았다면 Shallow Copy한 배열의 값을 참조.
		// 값이 변경되었다면 변경된 값을 참조
		public static class OnDemandCopyArray {
			// Deep Copy 대상
			OnDemandCopyArray mSrc;
			// 변경된 값 저장
			int[] mDest;
			// 현재 값이 변경되었는지 체크
			boolean[] changed; 
			
			public OnDemandCopyArray(OnDemandCopyArray mSrc, int[] mDest) {
				this.mSrc = mSrc;
				this.mDest = mDest;
				this.changed = new boolean[mDest.length];
			}
			
			// 특정 Index의 값을 수정
			public void updateElement(int idx, int val) {
				changed[idx] = true;
				mDest[idx] = val;
			}
			
			// 특정 Index의 값을 리턴
			public int getElement(int idx) {
				return changed[idx] ? mDest[idx] : mSrc.getElement(idx);
			}
		}
		
		Map<String, OnDemandCopyArray> deepCopyMap;
		
		public String charArrayToString(char[] name) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < name.length; i++) {
				if(name[i] == '\0') break;
				sb.append(name[i]);
			}
			
			return sb.toString();
		}
		
		public void init()
		{
			// Deep Copy 배열을 저장할 HashMap 초기화
			deepCopyMap = new HashMap<>();
		}

		public void makeList(char mName[], int mLength, int mListValue[])
		{
			// 새로운 배열을 생성
			int[] mDest = new int[mLength];
			for(int i = 0; i < mLength; i++) {
				mDest[i] = mListValue[i];
			}
			OnDemandCopyArray arr = new OnDemandCopyArray(null, mDest);
			// 새로 만든 배열이므로 온전히 자신만 참조하도록 설정
			Arrays.fill(arr.changed, true);
			
			// 배열을 Map에 등록
			deepCopyMap.put(charArrayToString(mName), arr);
		}

		public void copyList(char mDest[], char mSrc[], boolean mCopy)
		{
			if(mCopy) {
				// Deep Copy
				OnDemandCopyArray srcArray = deepCopyMap.get(charArrayToString(mSrc));
				int[] destArr = new int[srcArray.mDest.length];
				deepCopyMap.put(charArrayToString(mDest), new OnDemandCopyArray(srcArray, destArr));
			} else {
				// Shallow Copy
				// 다른 배열 그대로 참조하도록 설정
				deepCopyMap.put(charArrayToString(mDest), deepCopyMap.get(charArrayToString(mSrc)));
			}
		}

		public void updateElement(char mName[], int mIndex, int mValue)
		{
			deepCopyMap.get(charArrayToString(mName)).updateElement(mIndex, mValue);
		}

		public int element(char mName[], int mIndex)
		{
			return deepCopyMap.get(charArrayToString(mName)).getElement(mIndex);
		}
	}
}