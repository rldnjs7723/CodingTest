import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * SWEA 14611번 계산 게임
 * 문제 분류: 자료 구조 (Deque)
 * @author Giwon
 */
class Solution_14611 {
    private static BufferedReader br;
    private static UserSolution usersolution = new UserSolution();

    private final static int CMD_INIT = 100;
    private final static int CMD_PUT = 200;
    private final static int CMD_FIND = 300;
    private final static int CMD_CHANGE = 400;

    private final static int MAX_CARD_NUM = 5;
    private final static int MAX_RET_NUM = 4;

    private final static int numbers[] = new int[MAX_CARD_NUM];
    private final static int ret_numbers[] = new int[MAX_RET_NUM];
    private final static int ans_numbers[] = new int[MAX_RET_NUM];

    private static boolean run() throws Exception {

        StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");

        int query_num = Integer.parseInt(stdin.nextToken());
        int ret, ans;
        boolean ok = false;

        for (int q = 0; q < query_num; q++) {
            stdin = new StringTokenizer(br.readLine(), " ");
            int query = Integer.parseInt(stdin.nextToken());

            if (query == CMD_INIT) {
                int joker = Integer.parseInt(stdin.nextToken());
                for (int i = 0; i < MAX_CARD_NUM; i++)
                    numbers[i] = Integer.parseInt(stdin.nextToken());
                usersolution.init(joker, numbers);
                ok = true;
            } else if (query == CMD_PUT) {
                int dir = Integer.parseInt(stdin.nextToken());
                for (int i = 0; i < MAX_CARD_NUM; i++)
                    numbers[i] = Integer.parseInt(stdin.nextToken());
                usersolution.putCards(dir, numbers);
            } else if (query == CMD_FIND) {
                int num, Nth;
                num = Integer.parseInt(stdin.nextToken());
                Nth = Integer.parseInt(stdin.nextToken());
                ans = Integer.parseInt(stdin.nextToken());
                ret = usersolution.findNumber(num, Nth, ret_numbers);
                if (ans != ret) {
                    ok = false;
                }
                if (ans == 1) {
                    for (int i = 0; i < MAX_RET_NUM; i++) {
                        ans_numbers[i] = Integer.parseInt(stdin.nextToken());
                        if (ans_numbers[i] != ret_numbers[i]) {
                            ok = false;
                        }
                    }
                }
            } else if (query == CMD_CHANGE) {
                int value = Integer.parseInt(stdin.nextToken());
                usersolution.changeJoker(value);
            }

        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

        // System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }

        br.close();
    }
    
    public static class UserSolution {
    	// 합을 20으로 나눈 나머지 계산
    	public static final int DIV = 20;
    	public static final int CARD_SIZE = 4;
    	public static final int JOKER = -1;
    	// 카드를 넣을 방향
    	public static final int LEFT = 0, RIGHT = 1;
    	
    	// 나열한 4장의 카드 정보
    	public static class CardSet {
    		// 4장의 카드 합
    		int sum;
    		int[] cards;
    		int idx;
    		
    		public CardSet() {
    			this.sum = 0;
    			this.cards = new int[CARD_SIZE];
    			this.idx = 0;
    		}
    		
    		public CardSet(int[] cards) {
				this.sum = 0;
				this.cards = cards;
				
				for(int val: cards) {
					sum += val;
				}
				sum %= DIV;
			}
    		
    		public void put(int val) {
    			this.cards[idx++] = val;
    			sum += val;
    			sum %= DIV;
    		}
    	}
    	
    	// 
    	@SuppressWarnings("serial")
		public static class SumDeque extends ArrayDeque<CardSet> {
    		// mNth 번째 CardSet 리턴
    		CardSet get(int mNth) {
    			// mNth 번째 결과가 존재하지 않으면 null 리턴
    			if(mNth > this.size()) return null;
    			
    			Iterator<CardSet> iter = this.iterator();
    			CardSet curr = null;
    			for(int i = 0; i < mNth; i++) {
    				curr = iter.next();
    			}
    			
    			return curr;
    		}
    	}
    	
    	public static class Table {
    		// 현재 테이블에서 Joker의 값
    		int joker;
    		// CardSet의 총 합이 0 ~ 19인 경우를 저장할 Deque 배열 
    		SumDeque[] deques;
    		
    		public Table(int joker) {
				this.joker = joker;
				this.deques = new SumDeque[DIV];
				
				for(int i = 0; i < DIV; i++) {
					this.deques[i] = new SumDeque();
				}
			}
    	}
    	
    	@SuppressWarnings("serial")
		public static class EndQueue extends ArrayDeque<Integer> {
    		// 현재 4개의 카드 중에서 조커 개수
    		int jokerCount;
    		
    		public EndQueue() {
				super();
				this.jokerCount = 0;
			}
    		
    		@Override
    		public boolean offerFirst(Integer e) {
    			if(e == JOKER) jokerCount++;
    			return super.offerFirst(e);
    		}
    		
    		@Override
    		public boolean offerLast(Integer e) {
    			if(e == JOKER) jokerCount++;
    			return super.offerLast(e);
    		}
    		
    		@Override
    		public Integer pollFirst() {
    			if(this.peekFirst() == JOKER) jokerCount--;
    			return super.pollFirst();
    		}
    		
    		@Override
    		public Integer pollLast() {
    			if(this.peekFirst() == JOKER) jokerCount--;
    			return super.pollLast();
    		}
    		
    		
    		public void put(int mNumber, int mDir) {
    			if(mDir == LEFT) {
    				this.pollLast();
    				this.offerFirst(mNumber);
    				
    				// 조커가 없는 경우
    				if(jokerCount == 0) {
    					CardSet cards = new CardSet();
    					
    					Iterator<Integer> iter = this.iterator();
    					while(iter.hasNext()) {
    						cards.put(iter.next());
    					}
    					
    					for(int val = 0; val < DIV; val++) {
    						table[val].deques[cards.sum].offerFirst(cards);
    					}
    				} 
    				// 조커가 하나라도 있는 경우
    				else {
    					// 조커 값 0 ~ 19일 때를 각각 계산하여 Table에 저장
        				int temp;
        				for(int val = 0; val < DIV; val++) {
        					CardSet cards = new CardSet();
        					
        					Iterator<Integer> iter = this.iterator();
        					while(iter.hasNext()) {
        						temp = iter.next();
        						cards.put(temp == JOKER ? val : temp);
        					}
        					
        					table[val].deques[cards.sum].offerFirst(cards);
        				}
    				}
    				
    				
    			} else if(mDir == RIGHT) {
    				this.pollFirst();
    				this.offerLast(mNumber);
    				
    				// 조커가 없는 경우
    				if(jokerCount == 0) {
    					CardSet cards = new CardSet();
    					
    					Iterator<Integer> iter = this.iterator();
    					while(iter.hasNext()) {
    						cards.put(iter.next());
    					}
    					
    					for(int val = 0; val < DIV; val++) {
    						table[val].deques[cards.sum].offerLast(cards);
    					}
    				}
    				// 조커가 하나라도 있는 경우
    				else {
    					// 조커 값 0 ~ 19일 때를 각각 계산하여 Table에 저장
        				int temp;
        				for(int val = 0; val < DIV; val++) {
        					CardSet cards = new CardSet();
        					
        					Iterator<Integer> iter = this.iterator();
        					while(iter.hasNext()) {
        						temp = iter.next();
        						cards.put(temp == JOKER ? val : temp);
        					}
        					
        					table[val].deques[cards.sum].offerLast(cards);
        				}
    				}
    				
    			}
    		}
    		
    	}
    	
    	// 조커 값에 따라 사용할 테이블 클래스
    	public static Table[] table;
    	// 현재 조커의 값
    	public static int jokerValue;
    	// 전체 테이블에서 왼쪽, 오른쪽에 위치한 4개의 카드 저장
    	public static EndQueue[] endQueue;
    	
        void init(int mJoker, int mNumbers[]) {
        	// 조커 값은 1 ~ 30이 가능하지만 20으로 나누기 때문에 실제 값은 0 ~ 20
        	table = new Table[DIV];
        	for(int i = 0; i < DIV; i++) {
        		table[i] = new Table(i);
        	}
        	
        	jokerValue = mJoker % DIV;
        	endQueue = new EndQueue[2];
        	for(int i = 0; i < 2; i++) {
        		endQueue[i] = new EndQueue();
        	}
        	
        	for(int i = 0; i < 4; i++) {
        		endQueue[LEFT].offerFirst(mNumbers[i]);
        	}
        	
        	endQueue[RIGHT].offerLast(0);
        	for(int i = 0; i < 3; i++) {
        		endQueue[RIGHT].offerLast(mNumbers[i]);
        	}
        	endQueue[RIGHT].put(mNumbers[3], RIGHT);
        	endQueue[RIGHT].put(mNumbers[4], RIGHT);
        }

        void putCards(int mDir, int mNumbers[]) {
        }

        int findNumber(int mNum, int mNth, int ret[]) {
            return -1;
        }
        
        void changeJoker(int mValue) {
        	jokerValue = mValue % DIV;
        }
    }
}