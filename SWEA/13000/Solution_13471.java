
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 13471번 생산 시스템
 * 문제 분류: 구현, 시뮬레이션, 자료구조(Queue, Priority Queue, HashMap)
 * @author Giwon
 */
public class Solution_13471 {

	private final static int CMD_INIT = 1;
	private final static int CMD_REQUEST = 2;
	private final static int CMD_STATUS = 3;

	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(BufferedReader br) throws Exception {
		int q = Integer.parseInt(br.readLine());

		int l, m, timestamp, pid, mline, eid, mtime;
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			cmd = Integer.parseInt(st.nextToken());
			switch (cmd) {
				case CMD_INIT:
					l = Integer.parseInt(st.nextToken());
					m = Integer.parseInt(st.nextToken());
					usersolution.init(l, m);
					okay = true;
					break;
				case CMD_REQUEST:
					timestamp = Integer.parseInt(st.nextToken());
					pid = Integer.parseInt(st.nextToken());
					mline = Integer.parseInt(st.nextToken());
					eid = Integer.parseInt(st.nextToken());
					mtime = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.request(timestamp, pid, mline, eid, mtime);
					if (ret != ans)
						okay = false;
					break;
				case CMD_STATUS:
					timestamp = Integer.parseInt(st.nextToken());
					pid = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.status(timestamp, pid);
					if (ret != ans)
						okay = false;
					break;
				default:
					okay = false;
					break;
			}
		}
		return okay;
	}

	public static void main(String[] args) throws Exception {
		int TC, MARK;

		System.setIn(new java.io.FileInputStream("sample_input.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}

	public static class UserSolution {
		// 제품 생산 상태 상수
		final int NEVER = 0, WAITING = 1, PRODUCING = 2, DONE = 3;
		
		@SuppressWarnings("serial")
		class ProduceLine extends ArrayDeque<Product> {}
		
		class Product implements Comparable<Product> {
			// 현재 제품 생산 상태
			int status;
			// 제품 번호
			int pId;
			// 제품 생산 라인
			int mLine;
			// 제품 생산에 필요한 장비 번호
			int eId;
			// 제품 생산이 완료되기까지 남은 시간
			int remainTime;
			
			public Product(int status, int pId, int mLine, int eId, int mTime) {
				this.status = status;
				this.pId = pId;
				this.mLine = mLine;
				this.eId = eId;
				this.remainTime = mTime;
			}

			// 제품 생산이 완료되기까지 남은 시간 기준으로 정렬
			@Override
			public int compareTo(Product o) {
				return Integer.compare(this.remainTime, o.remainTime);
			}

			@Override
			public String toString() {
				return "Product [pId=" + pId + ", eId=" + eId + ", remainTime=" + remainTime + "]";
			}
		}
		
		// 현재 시간
		int currTimeStamp;
		// 생산 라인 관리
		ProduceLine[] lineStatus;
		// 장비 사용 상태 관리
		boolean[] equipmentStatus;
		// 현재 생산 중인 제품들 관리
		Queue<Product> producingPQ;
		// 생산 요청 관리
		Map<Integer, Product> products;
		
		// 입력 받은 tStamp보다 시간이 작은 경우 해당 tStamp까지 시간 흐르게 하기
		void flowTime(int tStamp) {
			int amount;
			while(currTimeStamp < tStamp) {
				// 1. 생산 가능 제품 체크
				checkAvailable();
				
				// 2. 생산 중인 제품 중 가장 빨리 끝나는 제품의 남은 시간만큼 생산
				amount = producingPQ.isEmpty() ? tStamp - currTimeStamp : producingPQ.peek().remainTime;
				amount = Math.min(amount, tStamp - currTimeStamp);
				produce(amount);
				
				// 3. PQ에 남아있는 제품 중 완료된 제품 제거
				while(!producingPQ.isEmpty()) {
					if(producingPQ.peek().status != DONE) break;
					producingPQ.poll();
				}
			}	
		}
		
		// 입력된 시간만큼 제품 생산
		void produce(int amount) {
			// 모든 생산 라인 둘러보면서 제품 생산에 남은 시간 감소
			for(ProduceLine curr: lineStatus) {
				// 생산 라인이 비어있으면 생략
				if(curr.isEmpty()) continue;
				// 생산 라인이 대기 중이라면 생략
				if(curr.peek().status == WAITING) continue;
				
				// 제품 생산 진행
				curr.peek().remainTime -= amount;
				
				// 제품 생산이 완료된 경우
				if(curr.peek().remainTime <= 0) {
					// 생산 상태 업데이트
					curr.peek().status = DONE;
					// 사용하던 장비 반환
					equipmentStatus[curr.peek().eId] = false;
					// 생산 라인에서 제거
					curr.poll();
				}
			}
			
			// 시간 업데이트
			currTimeStamp += amount;
		}
		
		// 모든 생산 라인 둘러보면서 현재 바로 생산을 시작할 수 있는 제품이 있는지 확인
		void checkAvailable() {
			for(ProduceLine curr: lineStatus) {
				// 생산 라인이 비어있으면 생략
				if(curr.isEmpty()) continue;
				// 생산 라인이 이미 생산 중이라면 생략
				if(curr.peek().status == PRODUCING) continue;
				// 필요한 장비가 사용 중이라면 생략
				if(equipmentStatus[curr.peek().eId]) continue;
				
				// 생산 상태 업데이트
				curr.peek().status = PRODUCING;
				// 장비 사용 체크
				equipmentStatus[curr.peek().eId] = true;
				// 생산 중인 제품 우선순위 큐에 추가
				producingPQ.offer(curr.peek());
			}
		}
		
		void init(int L, int M) {
			// 초기화 함수 호출 시 시각은 0
			currTimeStamp = 0;
			// 생산 라인 Queue로 관리
			lineStatus = new ProduceLine[L];
			for(int i = 0; i < L; i++) {
				lineStatus[i] = new ProduceLine();
			}
			// 장비 개수는 최대 500개이므로 boolean 배열로 관리
			equipmentStatus = new boolean[M];
			// 생산 중인 제품들은 PQ로 관리
			producingPQ = new PriorityQueue<>();
			// 생산 요청 HashMap으로 관리
			products = new HashMap<>();
		}

		int request(int tStamp, int pId, int mLine, int eId, int mTime) {
			// 요청이 들어온 시간까지 시간 흐르게 하기
			flowTime(tStamp);
			
			// 새로운 생산 요청 생성
			Product produceRequest = new Product(WAITING, pId, mLine, eId, mTime);
			// 생산 라인에 추가
			lineStatus[mLine].offer(produceRequest);
			// 생산 상태 확인 HashMap에 추가
			products.put(pId, produceRequest);
			
			// 생산 가능한 제품이 있는지 확인
			checkAvailable();
			
			// 현재 생산 라인에서 생산 중인 제품 ID 반환
			int producingPid = lineStatus[mLine].peek().status == PRODUCING ? lineStatus[mLine].peek().pId : -1;
			return producingPid;
		}

		int status(int tStamp, int pId) {
			// 요청이 들어온 시간까지 시간 흐르게 하기
			flowTime(tStamp);
			// 생산 가능한 제품이 있는지 확인
			checkAvailable();
			
			// HashMap에 저장된 제품의 상태 확인
			if(products.containsKey(pId)) {
				return products.get(pId).status;
			} else return NEVER;
		}
	}
}
