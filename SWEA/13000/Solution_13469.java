

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 13469번 메모장 프로그램
 * 문제 분류: 자료구조 (LinkedList)
 * @author Giwon
 */
public class Solution_13469 {

	private final static int CMD_INIT       = 100;
	private final static int CMD_INSERT     = 200;
	private final static int CMD_MOVECURSOR = 300;
	private final static int CMD_COUNT      = 400;
	
	private final static UserSolution usersolution = new UserSolution();
	
	private static void String2Char(char[] buf, String str, int maxLen)
	{
		for (int k = 0; k < str.length(); k++)
			buf[k] = str.charAt(k);
			
		for (int k = str.length(); k <= maxLen; k++)
			buf[k] = '\0';
	}
	
	private static char[] mStr = new char[90001];
	
	private static boolean run(BufferedReader br) throws Exception
	{
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int queryCnt = Integer.parseInt(st.nextToken());
		boolean correct = false;
		
		for (int q = 0; q < queryCnt; q++)
		{
			st = new StringTokenizer(br.readLine(), " ");
			
			int cmd = Integer.parseInt(st.nextToken());
			
			if (cmd == CMD_INIT)
			{
				int H = Integer.parseInt(st.nextToken());
				int W = Integer.parseInt(st.nextToken());
				
				String2Char(mStr, st.nextToken(), 90000);
				
				usersolution.init(H, W, mStr);
				correct = true;
			}
			else if (cmd == CMD_INSERT)
			{
				char mChar = st.nextToken().charAt(0);
				
				usersolution.insert(mChar);
			}
			else if (cmd == CMD_MOVECURSOR)
			{
				int mRow = Integer.parseInt(st.nextToken());
				int mCol = Integer.parseInt(st.nextToken());
				
				char ret = usersolution.moveCursor(mRow, mCol);
				
				char ans = st.nextToken().charAt(0);
				if (ret != ans)
				{
					correct = false;
				}
			}
			else if (cmd == CMD_COUNT)
			{
				char mChar = st.nextToken().charAt(0);
				
				int ret = usersolution.countCharacter(mChar);
				
				int ans = Integer.parseInt(st.nextToken());
				if (ret != ans)
				{
					correct = false;
				}
			}
		}
		return correct;
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
		public static final char NULL = '$';
		public static final int ALPHABET_SIZE = 'z' - 'a' + 1;
		
		// 메모장 높이, 너비
		int H, W;
		// 전체 문자 개수
		int size;
		// 전체 노드 더미 헤드, 1행부터 H행까지 노드 시작점 저장
		Node[] head;
		// 1행부터 H행까지 알파벳 개수 저장
		Alphabet[] alphabetCount;
		// 전체 노드 끝점 저장
		Node tail;
		// 현재 커서 위치
		Cursor cursor;
		
		class Cursor {
			int row, col;
			// 커서 왼쪽, 오른쪽 노드
			Node prev, next;
			
			public Cursor(Node prev, Node next) {
				// 초기화 한 후 커서는 문자열의 첫 번째 문자 왼쪽에 위치
				this.row = 1;
				this.col = 1;
				this.prev = prev;
				this.next = next;
			}
			
			public void moveCursor(int H, int W, Node prev, Node next) {
				this.row = H;
				this.col = W;
				this.prev = prev;
				this.next = next;
			}
		}
		
		class Node {
			char val;
			// LinkedList의 이전 노드, 다음 노드
			Node prev, next;
			// LinkedList의 다음 동일한 문자 노드
			Node nextChar;
			
			public Node(char val) {
				this.val = val;
				this.prev = null;
				this.next = null;
				this.nextChar = null;
			}
			
			@Override
			public String toString() {
				return val + " " + next;
			}
		}
		
		class Alphabet {
			int[] count;
			
			public Alphabet() {
				this.count = new int[ALPHABET_SIZE];
			}
			
			public void addAlphabet(char c) {
				count[c - 'a']++;
			}
			
			public void removeAlphabet(char c) {
				count[c - 'a']--;
			}
			
			public void clear() {
				Arrays.fill(count, 0);
			}
			
			public int getCount(char c) {
				return count[c - 'a'];
			}
		}
		
		// 새 행 생성 여부 판단
		int getNewLine() {
			// -1이면 새 행을 생성하지 않는 경우
			if(size % W != 0) return -1;
			
			// 새 행을 생성해야 하면 해당 행의 Index 리턴
			return size / W + 1;
		}
		
		// 커서 위치 변경
		void setCursor(int row, int col, Node prev, Node next) {
			cursor.row = row;
			cursor.col = col;
			cursor.prev = prev;
			cursor.next = next;	
		}
		
		// 디버깅용 출력
		@Override
		public String toString() {
			return head[0].toString();
		}
		
		
		// O(H * W)
		void init(int H, int W, char mStr[])
		{
			this.H = H;
			this.W = W;
			
//			System.out.println("Init. H: " + H + " W: " + W);
			this.size = 0;
			this.head = new Node[H + 2];
			this.alphabetCount = new Alphabet[H + 2];
			// 더미 Head 노드
			for(int i = 0; i <= H + 1; i++) {
				this.head[i] = new Node(NULL);
				this.alphabetCount[i] = new Alphabet();
			}
			
			// 더미 Tail 노드
			this.tail = new Node(NULL);
			
			// 커서 초기화
			this.head[0].next = this.tail;
			this.tail.prev = this.head[0];
			this.cursor = new Cursor(this.head[0], this.tail);
			
			// 문자 추가 O(H * W)
			int i = 0;
			while(mStr[i] != '\0') {
				insert(mStr[i++]);
			}
			
			// 커서 위치 시작점으로 이동 O(W)
			moveCursor(1, 1);
		}
		
		// O(H + W)
		void insert(char mChar)
		{
			// 새 노드 생성
			Node node = new Node(mChar);
			// 노드 연결
			node.prev = cursor.prev;
			node.next = cursor.next;
			cursor.prev.next = node;
			cursor.next.prev = node;
			
			// 1. 전체 문자 개수 카운트 ++
			size++;
			// 현재 커서가 행의 시작점이라면 시작점 수정
			if(cursor.col == 1) head[cursor.row].next = node;
			// 현재 행의 알파벳 수 수정
			alphabetCount[cursor.row].addAlphabet(mChar);
			
			// 2. 각 행에서 줄바꿈이 일어날 때 각 행의 Head를 가리키는 노드 수정
			// 현재 커서 위치보다 아래에 위치한 행들 전부 줄바꿈 처리 O(H)
			for(int row = cursor.row + 1; row <= H + 1; row++) {
				// 행이 비어있다면 중단
				if(head[row].next == null) break;
				
				// 이전 행의 마지막 값을 현재 행의 시작 값으로 설정
				head[row].next = head[row].next.prev;
				
				// 3. 각 행의 알파벳 개수 수정
				// 이전 행 알파벳 제거
				alphabetCount[row - 1].removeAlphabet(head[row].next.val);
				// 현재 행 알파벳 추가
				alphabetCount[row].addAlphabet(head[row].next.val);
			}

			// 새 행 생성
			int row = getNewLine();
			if(row != -1) {
				head[row].next = tail;
			}
			
//			System.out.println("Insert. Curr: " + head[0].next);
			
			// 커서 오른쪽으로 한 칸 이동 O(W)
			moveCursor(cursor.row, cursor.col + 1);
		}

		// O(W)
		char moveCursor(int mRow, int mCol)
		{
			int row = mRow, col = mCol;
			Node prev = null, next = null;
			
			// 해당 행, 열 오른쪽의 문자가 비어있는 경우
			if((row - 1) * W + col > size) {
				// 마지막 문자 오른쪽으로 이동
				row = size / W + 1;
				col = size % W + 1;
			}
			
			// 행, 열 범위 조정
			if(col > W) {
				row++;
				col -= W;
			} else if(col == 0) {
				row--;
				col += W;
			}
			
			// 각 행의 시작점부터 원하는 열의 노드 탐색
			Node curr = head[row];
			for(int i = 1; i <= col; i++) {
				curr = curr.next;
			}
			prev = curr.prev;
			next = curr;
			setCursor(row, col, prev, next);
			
//			System.out.println("Move Cursor: " + row + " " + col + " val: " + cursor.next.val);
			
			return cursor.next.val;
		}

		// O(H + W)
		int countCharacter(char mChar)
		{
			// 1. 현재 행에서 커서 뒤의 알파벳 개수 카운트 O(W)
			Node curr = cursor.next;
			alphabetCount[0].clear();
			for(int col = cursor.col; col <= W; col++) {
				// 마지막 문자라면 개수 출력
				if(curr.val == NULL) return alphabetCount[0].getCount(mChar);
				alphabetCount[0].addAlphabet(curr.val);
				curr = curr.next;
			}
			
			// 2. 나머지 행에서 알파벳 개수 카운트 O(H)
			int result = alphabetCount[0].getCount(mChar);
			for(int row = cursor.row + 1; row <= H; row++) {
				result += alphabetCount[row].getCount(mChar);
			}
			
//			System.out.println("count: " + result);
			return result;
		}
	}
}