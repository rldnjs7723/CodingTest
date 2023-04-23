import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * SWEA 14618번 끝말잇기2
 * 문제 분류: 자료구조 (TreeSet, PriorityQueue, Trie)
 * @author Giwon
 */
public class Solution_14618 {
	private static BufferedReader br;
    private static final UserSolution userSolution = new UserSolution();

    private final static int MAX_M = 50000;
    private final static int MAX_LEN = 11;

    private static final char[][] mWords = new char[MAX_M][MAX_LEN];

    private static boolean run() throws Exception
    {
        StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");
        boolean ok = true;
        int N = Integer.parseInt(stdin.nextToken());
        int M = Integer.parseInt(stdin.nextToken());

        for (int i = 0; i < M; i++)
        {
            String word = br.readLine();
            Arrays.fill(mWords[i], (char)0);
            word.getChars(0, word.length(), mWords[i], 0);
        }

        userSolution.init(N, M, mWords);

        int cnt = Integer.parseInt(br.readLine());

        for (int i = 0; i < cnt; i++)
        {
            stdin = new StringTokenizer(br.readLine(), " ");
            int mID, ret, ans;
            char mCh;

            mID = Integer.parseInt(stdin.nextToken());
            mCh = stdin.nextToken().charAt(0);
            ret = userSolution.playRound(mID, mCh);
            ans = Integer.parseInt(stdin.nextToken());
            if (ret != ans)
            {
                ok = false;
            }
        }

        return ok;
    }

    public static void main(String[] args) throws Exception
    {
        System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");

        int T, MARK;
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++)
        {
            int score = run() ? MARK : 0;
            System.out.printf("#%d %d\n", tc, score);
        }

        br.close();
    }

    public static class UserSolution {
    	public static final int SIZE = 'z' - 'a' + 1;
    	
    	// 탈락하지 않은 플레이어 저장용 TreeSet
    	TreeSet<Integer> players; 
    	// 사용할 수 있는 단어 리스트
    	public static Dictionary availableWords;
    	// 사용한 단어 저장할 트라이
    	public static Trie usedWords;
    	// 라운드 시작 전 추가할 단어
    	public static Set<String> tempWords;
    	
    	@SuppressWarnings("serial")
		public static class Word extends PriorityQueue<String>{}
    	
    	public static class Dictionary {
    		Word[] words;
    		
    		public Dictionary() {
				this.words = new Word[SIZE];
				
				for(int i = 0; i < SIZE; i++) {
					this.words[i] = new Word();
				}
			}
    		
    		// 단어 추가
    		public void putString(char[] mWord) {
    			String input = charArrayToString(mWord);
    			words[mWord[0] - 'a'].add(input);
    		}
    		
    		public void putString(String input) {
    			words[input.charAt(0) - 'a'].add(input);
    		}
    		
    		// 특정 알파벳으로 시작하는 사전순으로 가장 빠른 단어 리턴
    		public String getString(char mCh, Trie usedWords) {
    			int idx = mCh - 'a';
    			while(!words[idx].isEmpty() && !usedWords.checkString(words[idx].peek())) {
    				words[idx].poll();
    			}
    			
    			if(words[idx].isEmpty()) return null;
    			return words[idx].poll();
    		}
    	}
    	
    	
    	// 트라이 노드
    	public static class Node {
    		// 현재 위치에 단어가 존재하는지 체크
    		boolean leaf;
    		Node[] child;
    		
    		public Node() {
				leaf = false;
				child = new Node[SIZE];
			}
    		
    		// 트라이 구성용
    		public Node getNext(char c) {
    			if(child[c - 'a'] == null) child[c - 'a'] = new Node();
    			return child[c - 'a'];
    		}
    	}
    	
    	// 사용한 단어 저장할 클래스
    	public static class Trie {
    		Node root;
    		
    		public Trie() {
				this.root = new Node();
			}
    		
    		// 문자열 입력
    		public void putString(char[] mWord) {
    			Node curr = root;
    			int i = 0;
    			while(mWord[i] != '\0') {
    				curr = curr.getNext(mWord[i++]);
    			}
    			curr.leaf = true;
    		}
    		
    		public void putString(String input) {
    			int len = input.length();
    			
    			Node curr = root;
    			for(int i = 0; i < len; i++) {
    				curr = curr.getNext(input.charAt(i));
    			}
    			curr.leaf = true;
    		}
    		
    		// 중복된 단어인지 체크. 중복이 아닌 경우 true 리턴
    		public boolean checkString(String input) {
    			int len = input.length();
    			
    			Node curr = root;
    			for(int i = 0; i < len; i++) {
    				if(curr == null) return true;
    				curr = curr.child[input.charAt(i) - 'a'];
    			}
    			if(curr != null && curr.leaf) return false;
    			else return true;
    		}
    		
    	}
    	
    	public static String charArrayToString(char[] word) {
    		StringBuilder sb = new StringBuilder();
    		int i = 0;
    		while(word[i] != '\0') {
    			sb.append(word[i++]);
    		}
    		
    		return sb.toString();
    	}
    	
        public void init(int N, int M, char[][] mWords)
        {
        	players = new TreeSet<>();
        	availableWords = new Dictionary();
        	usedWords = new Trie();
        	tempWords = new HashSet<>();
        	
        	// 플레이어 입력
        	for(int i = 1; i <= N; i++) {
        		players.add(i);
        	}
        	
        	// 단어 입력
        	for(int i = 0; i < M; i++) {
        		availableWords.putString(mWords[i]);
        	}
        }

        public int playRound(int mID, char mCh)
        {
        	// 라운드 시작 전 이전 라운드에서 사용한 단어 뒤집어서 추가
        	StringBuilder sb = new StringBuilder();
        	String temp;
        	for(String word: tempWords) {
        		sb.setLength(0);
        		sb.append(word);
        		temp = sb.reverse().toString();
        		
        		if(usedWords.checkString(temp)) availableWords.putString(temp);
        	}
        	tempWords.clear();
        	
        	int currPlayer = mID;
        	String word;
        	while((word = availableWords.getString(mCh, usedWords)) != null) {
        		// 선택한 단어 트라이에 추가
        		usedWords.putString(word);
        		// 선택한 단어 뒤집어서 넣기 위해 임시로 추가
        		tempWords.add(word);
        		
        		// 현재 단어의 마지막 문자를 시작 문자로 교체
        		mCh = word.charAt(word.length() - 1);
        		// 다음 참가자 선택
        		if(players.higher(currPlayer) == null) {
        			currPlayer = players.first();
        		} else {
        			currPlayer = players.higher(currPlayer);
        		}
        	}
        	
        	// 마지막 플레이어 탈락
        	players.remove(currPlayer);
            return currPlayer;
        }
    }
}
