import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 삼성 SW 역량테스트 2022 하반기 오후 2번 문제
 * 문제 분류: 자료 구조(Doubly LinkedList, HashMap)
 * @author Giwon
 */
public class 산타의_선물_공장_2 {
	// 선물 Index 1번부터 시작
	public static int lastIndex = 1;
	// 선물 Index로 접근
	public static Map<Integer, Present> presentMap;
	// 벨트 Index로 접근
	public static Belt[] conveyorBelt;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int q = Integer.parseInt(br.readLine().trim());
		for(int i = 0; i < q; i++) {
			parser(br.readLine());
		}
		
		br.close();
	}
	
	// 입력 파싱
	public static void parser(String input) {
		StringTokenizer st = new StringTokenizer(input);
		
		int type = Integer.parseInt(st.nextToken());
		int n, m, m_src, m_dst, p_num, b_num;
		int[] sequence;
		switch (type) {
			case 100:
				n = Integer.parseInt(st.nextToken());
				m = Integer.parseInt(st.nextToken());
				sequence = new int[m];
				for(int i = 0; i < m; i++) {
					sequence[i] = Integer.parseInt(st.nextToken());
				}
				
				init(n, m, sequence);
				break;
			
			case 200:
				m_src = Integer.parseInt(st.nextToken());
				m_dst = Integer.parseInt(st.nextToken());
				
				System.out.println(moveAll(m_src, m_dst));
				break;
			case 300:
				m_src = Integer.parseInt(st.nextToken());
				m_dst = Integer.parseInt(st.nextToken());
				
				System.out.println(swapFirst(m_src, m_dst));
				break;
			case 400:
				m_src = Integer.parseInt(st.nextToken());
				m_dst = Integer.parseInt(st.nextToken());
				
				System.out.println(divideHalf(m_src, m_dst));
				break;
			case 500:
				p_num = Integer.parseInt(st.nextToken());
				
				System.out.println(getPresentInfo(p_num));
				break;
			case 600:
				b_num = Integer.parseInt(st.nextToken());
				
				System.out.println(getBeltInfo(b_num));
				break;
			default:
				break;
		}
	}
	
	
	/**
	 * 1. 공장 설립
	 * @param n 			벨트 수
	 * @param m				선물 수
	 * @param sequence		선물을 올릴 벨트 수열
	 */
	public static void init(int n, int m, int[] sequence) {
		// 선물 Map 생성
		presentMap = new HashMap<>();
		// 벨트 생성
		conveyorBelt = new Belt[n + 1];
		for(int i = 1; i <= n; i++) {
			conveyorBelt[i] = new Belt(i);
		}
		
		// 선물 입력
		for(int beltIdx: sequence) {
			conveyorBelt[beltIdx].addPresent(lastIndex++);
		}
	}
	
	/**
	 * 2. 물건 모두 옮기기
	 * m_src 벨트 위의 물건을 전부 m_dst 벨트 앞으로 이동
	 * @param m_src		시작 벨트
	 * @param m_dst		목표 벨트
	 * @return			m_dst 벨트의 물건 개수
	 */
	public static int moveAll(int m_src, int m_dst) {
		Belt source = conveyorBelt[m_src];
		Belt destination = conveyorBelt[m_dst];
		
		// m_src 벨트에 선물이 없다면 아무것도 옮기지 않음
		if(source.size == 0) return destination.size;
		
		Present sourceFirst = source.getFirst();
		Present sourceLast = source.getLast();
		Present destinationFirst = destination.getFirst();
		
		// 선물 앞, 뒤 연결 갱신
		sourceFirst.prev = destination.head;
		sourceLast.next = destinationFirst;
		destinationFirst.prev = sourceLast;
		
		// m_dst 벨트 갱신
		destination.head.next = sourceFirst;
		destination.size += source.size;
				
		// m_src 벨트 갱신
		source.head.next = source.tail;
		source.tail.prev = source.head;
		source.size = 0;
		
		return destination.size;
	}
	
	/**
	 * 3. 앞 물건만 교체하기
	 * @param m_src		시작 벨트
	 * @param m_dst		목표 벨트
	 * @return			m_dst 벨트의 물건 개수
	 */
	public static int swapFirst(int m_src, int m_dst) {
		Belt source = conveyorBelt[m_src];
		Belt destination = conveyorBelt[m_dst];
		
		Present sourceFirst = source.getFirst();
		Present sourceSecond = sourceFirst.next;
		Present destinationFirst = destination.getFirst();
		Present destinationSecond = destinationFirst.next;
		
		// 양쪽 모두에 선물이 없다면 아무것도 옮기지 않음
		if(source.size == 0 && destination.size == 0) return destination.size;

		// m_src 벨트가 비어있는 경우
		if(source.size == 0) {
			// source 갱신
			source.head.next = destinationFirst;
			source.tail.prev = destinationFirst;
			source.size++;
			
			// 선물 갱신
			destinationFirst.prev = source.head;
			destinationFirst.next = source.tail;
			
			// destination 갱신
			destination.head.next = destinationSecond;
			destinationSecond.prev = destination.head;
			destination.size--;
			
			return destination.size;
		}
		
		// m_dst 벨트가 비어있는 경우
		if(destination.size == 0) {
			// destination 갱신
			destination.head.next = sourceFirst;
			destination.tail.prev = sourceFirst;
			destination.size++;
			
			// 선물 갱신
			sourceFirst.prev = destination.head;
			sourceFirst.next = destination.tail;
			
			// source 갱신
			source.head.next = sourceSecond;
			sourceSecond.prev = source.head;
			source.size--;
			
			return destination.size;
		}
		
		// 양쪽 모두 선물이 있는 경우
		// source 갱신
		source.head.next = destinationFirst;
		sourceSecond.prev = destinationFirst;
		destinationFirst.prev = source.head;
		destinationFirst.next = sourceSecond;
		
		// destination 갱신
		destination.head.next = sourceFirst;
		destinationSecond.prev = sourceFirst;
		sourceFirst.prev = destination.head;
		sourceFirst.next = destinationSecond;
		
		return destination.size;
	}
	
	/**
	 * 4. 물건 나누기
	 * @param m_src		시작 벨트
	 * @param m_dst		목표 벨트
	 * @return			m_dst 벨트의 물건 개수
	 */
	public static int divideHalf(int m_src, int m_dst) {
		Belt source = conveyorBelt[m_src];
		Belt destination = conveyorBelt[m_dst];
		
		Present sourceFirst = source.getFirst();
		Present sourceMiddle = source.getMiddle();
		Present destinationFirst = destination.getFirst();
		// 선물이 1개 이하인 경우 선물을 옮기지 않음
		if(sourceMiddle == null) return destination.size;
		
		Present sourceMiddleSecond = sourceMiddle.next;
		// 선물 앞, 뒤 연결 갱신
		sourceFirst.prev = destination.head;
		sourceMiddle.next = destinationFirst;
		destinationFirst.prev = sourceMiddle;
		
		// m_dst 벨트 갱신
		destination.head.next = sourceFirst;
		destination.size += source.size / 2;
				
		// m_src 벨트 갱신
		source.head.next = sourceMiddleSecond;
		sourceMiddleSecond.prev = source.head;
		source.size -= source.size / 2;
		
		return destination.size;
	}
	
	// 5. 선물 정보 얻기
	public static int getPresentInfo(int p_num) {
		return presentMap.get(p_num).getInfo();
	}
	
	// 6. 벨트 정보 얻기
	public static int getBeltInfo(int b_num) {
		return conveyorBelt[b_num].getInfo();
	}
	
	public static class Present {
		// 선물 번호
		int idx;
		// 이전 노드, 다음 노드
		Present prev, next;
		
		public Present(int idx, Present prev, Present next) {
			this.idx = idx;
			this.prev = prev;
			this.next = next;
		}
		
		// 5. 선물 정보 얻기
		public int getInfo() {
			return prev.idx + 2 * next.idx;
		}

		@Override
		public String toString() {
			return "Present [idx=" + idx + "]";
		}
		
	}
	
	// Doubly Linked List
	public static class Belt {
		// 벨트 번호
		int idx;
		// 벨트에 존재하는 선물 개수
		int size;
		// 더미 Head, Tail 노드
		Present head, tail;
		
		// 리스트 초기화
		public Belt(int idx) {
			this.idx = idx;
			this.size = 0;
			this.head = new Present(-1, null, null);
			this.tail = new Present(-1, this.head, null);
			this.head.next = this.tail;
		}
		
		// 벨트 맨 뒤에 선물 추가
		public void addPresent(int idx) {
			// 선물 생성
			Present present = new Present(idx, tail.prev, tail);
			tail.prev.next = present;
			tail.prev = present;
			// Map에 선물 추가
			presentMap.put(idx, present);
			// 벨트 선물 개수 갱신
			size++;
		}
		
		// 벨트 맨 앞에 위치한 선물 리턴
		public Present getFirst() {
			return head.next;
		}
		
		// 벨트 맨 뒤에 위치한 선물 리턴
		public Present getLast() {
			return tail.prev;
		}
		
		// 벨트 중간에 위치한 선물 리턴
		public Present getMiddle() {
			int location = size / 2;
			if(location == 0) return null;
			
			Present curr = this.head;
			for(int i = 0; i < location; i++) {
				curr = curr.next;
			}
			
			return curr;
		}
		
		// 6. 벨트 정보 얻기
		public int getInfo() {
			return head.next.idx + 2 * tail.prev.idx + 3 * size;
		}

		@Override
		public String toString() {
			return "Belt [idx=" + idx + ", size=" + size + ", head=" + head + ", tail=" + tail + "]";
		}
		
	}
	
}
