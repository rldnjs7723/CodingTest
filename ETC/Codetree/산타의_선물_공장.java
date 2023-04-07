import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 삼성 SW 역량테스트 2022 하반기 오전 2번 문제
 * 문제 분류: 구현, 자료구조 (Doubly LinkedList, HashMap, HashSet), Union Find
 * @author Giwon
 */
public class 산타의_선물_공장 {
	// id-물건 매핑
	public static Map<Integer, Box> boxMap;
	// 벨트 수
	public static int beltNum;
	// 컨베이어 벨트 배열
	public static Belt[] belts;
	// 고장난 컨베이어 벨트 기록
	public static Set<Integer> brokenBelt;
	// 출력용
	public static BufferedWriter bw;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		final int q = Integer.parseInt(br.readLine().trim());
		for(int i = 0; i < q; i++) {
			parser(br.readLine());
		}
		
		find(51060488);
		
		Box temp = belts[1].head;
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		StringBuilder sb4 = new StringBuilder();
		
		sb.append("Debug: ");
		while(temp.next != null) {
			sb.append(temp.id + " ");
			sb2.append(temp.b_num + " ");
			sb3.append(temp.next.id + " ");
			temp = temp.next;
		}
		sb.append(temp.id + " ");
		sb2.append(temp.b_num + " ");
		bw.write(sb.toString() + "\n");
		bw.write(sb2.toString() + "\n");
		bw.write(sb3.toString() + "\n");
		
		while(temp.prev != null) {
			sb4.append(temp.id + " ");
			temp = temp.prev;
		}
		sb4.append(temp.id + " ");
		bw.write(sb4.toString() + "\n");
		
		bw.close();
		br.close();
	}
	
	public static void parser(String input) throws IOException {
		StringTokenizer st = new StringTokenizer(input);
		int type = Integer.parseInt(st.nextToken());
		
		int n, m, w_max, r_id, f_id, b_num;
		int[] idArr, wArr;
		switch (type) {
			case 100:
				n = Integer.parseInt(st.nextToken());
				m = Integer.parseInt(st.nextToken());
				idArr = new int[n];
				wArr = new int[n];
				for(int i = 0; i < n; i++) {
					idArr[i] = Integer.parseInt(st.nextToken());
				}
				for(int i = 0; i < n; i++) {
					wArr[i] = Integer.parseInt(st.nextToken());
				}
				
				init(n, m, idArr, wArr);
				break;

			case 200: 
				w_max = Integer.parseInt(st.nextToken());
				
				bw.write(unload(w_max) + "\n");
				break;
				
			case 300:
				r_id = Integer.parseInt(st.nextToken());
				
				bw.write(remove(r_id) + "\n");
				break;
			
			case 400:
				f_id = Integer.parseInt(st.nextToken());
				
//				bw.write(find(f_id) + "\n");
				break;
				
			case 500:
				b_num = Integer.parseInt(st.nextToken());
				
				bw.write(malfunction(b_num) + "\n");
				break;
			default:
				break;
		}
	}
	
//	public static void getBug() {
//		if(boxMap == null) return;
//		
//		for(Box box: boxMap.values()) {
//			if(box.prev == null || box.next == null) {
//				System.out.println("BUG");
//			}
//		}
//	}
	
	// 1. 공장 설립
	public static void init(int n, int m, int[] idArr, int[] wArr) {
		boxMap = new HashMap<>();
		brokenBelt = new HashSet<>();
		beltNum = m;
		belts = new Belt[m + 1];
		for(int i = 1; i <= m; i++) {
			belts[i] = new Belt(i);
		}
		
		// 물건 입력
		int size = n / m;
		int id, w;
		Box box;
		for(int b_num = 1; b_num <= m; b_num++) {
			for(int i = 0; i < size; i++) {
				id = idArr[(b_num - 1) * size + i];
				w = wArr[(b_num - 1) * size + i];
				box = new Box(id, w, b_num);
				// Map에 추가
				boxMap.put(id, box);
				// 컨베이어 벨트에 추가
				belts[b_num].offer(box);
			}
		}
	}
	
	// 2. 물건 하차
	public static long unload(int w_max) {
		long totalWeight = 0;
		Box first;
		Belt belt;
		for(int i = 1; i <= beltNum; i++) {
			// 고장난 벨트인 경우 생략
			if(brokenBelt.contains(i)) continue;
			belt = belts[i];
			
			first = belt.poll();
			// 물건이 없는 벨트는 생략
			if(first == null) continue;
			// 가장 앞에 위치한 물건 무게가 w_max 보다 크면 맨 뒤로 보내기
			if(first.w > w_max) {
				belt.offer(first);
				continue;
			}
			
			// w_max 이하라면 하차
			totalWeight += first.w;
			// Map에서 제거
			boxMap.remove(first.id);
		}
		
		return totalWeight;
	}
	
	// 3. 물건 제거
	public static int remove(int r_id) {
		// 물건이 없다면 -1 출력
		if(!boxMap.containsKey(r_id)) return -1;
		
		// 연결 해제
		Box target = boxMap.get(r_id);
		target.prev.next = target.next;
		target.next.prev = target.prev;
		
		// Map에서 제거
		boxMap.remove(r_id);
		
		return r_id;
	}
	
	// 4. 물건 확인
	public static int find(int f_id) throws IOException {
		// 물건이 없다면 -1 출력
		if(!boxMap.containsKey(f_id)) return -1;
		
		// 물건이 위치한 벨트 탐색
		Box first = boxMap.get(f_id);
		Belt belt = belts[first.b_num];
		Box last = belt.tail.prev;
		
		// 벨트 앞에 전부 다 이동
		belt.head.next = first;
		first.prev = belt.head;
		belt.tail.prev = first.prev;
		first.prev.next = belt.tail;
		
//		// 연결 해제
//		Box last = belt.tail.prev;
//		Box prevFirst = belt.head.next;
//		target.prev.next = belt.tail;
//		belt.tail.prev = target.prev;
//
//		// 벨트 앞에 전부 다 이동
//		belt.head.next = target;
//		prevFirst.prev = last;
//		target.prev = belt.head;
//		last.next = prevFirst;
		
		return belt.id;
	}
	
	// 5. 벨트 고장
	public static int malfunction(int b_num) {
		// 이미 고장난 벨트인 경우 -1 출력
		if(brokenBelt.contains(b_num)) return -1;
		
		// 고장난 벨트
		Belt src = belts[b_num];
		// 오른쪽부터 대체할 벨트 탐색
		Belt dest = null;
		for(int i = b_num + 1; i <= beltNum; i++) {
			if(!brokenBelt.contains(i)) {
				dest = belts[i];
				break;
			}
		}
		
		// 모든 벨트가 망가지는 경우는 없음
		if(dest == null) {
			for(int i = 1; i < b_num; i++) {
				if(!brokenBelt.contains(i)) {
					dest = belts[i];
					break;
				}
			}
		}
		
		// 대체할 벨트로 모든 물건 이동
		if(src.head.next != src.tail) {
			// 고장난 벨트에 물건이 있는 경우에만 이동
			dest.tail.prev.next = src.head.next;
			src.head.next.prev = dest.head;
			dest.tail.prev = src.tail.prev;
			src.tail.prev.next = dest.tail;
		}
		
		// 현재 벨트로의 모든 매핑을 대체할 벨트로 변경
		for(int i = 1; i <= beltNum; i++) {
			if(belts[i] == src) belts[i] = dest;
		}
		
		// 고장난 벨트에 추가
		brokenBelt.add(b_num);
		
		return b_num;
	}

	public static class Box {
		// 물건 고유번호
		int id;
		// 벨트 번호
		int b_num;
		// 물건 무게
		int w;
		// 이전 노드, 다음 노드
		Box prev, next;
		
		public Box(int id, int w, int b_num) {
			this.id = id;
			this.w = w;
			this.b_num = b_num;
			this.prev = null;
			this.next = null;
		}
	}
	
	public static class Belt {
		// 컨베이어 벨트 번호
		int id;
		// 더미 head, tail 노드
		Box head, tail;
		
		public Belt(int id) {
			this.id = id;
			// 더미 head, tail 노드 초기화
			this.head = new Box(-1, -1, id);
			this.tail = new Box(-1, -1, id);
			this.head.next = this.tail;
			this.tail.prev = this.head;
		}
		
		// 물건 맨 뒤에 추가
		public void offer(Box box) {
			box.prev = tail.prev;
			box.next = tail;
			tail.prev.next = box;
			tail.prev = box;
		}
		
		// 맨 앞에 위치한 물건 리턴
		public Box poll() {
			// 상자가 없다면 null 리턴
			if(head.next == tail) return null;
			
			// 맨 앞에 위치한 물건 연결 해제
			Box first = head.next;
			head.next = first.next;
			first.next.prev = head;
			first.prev = null;
			first.next = null;
			
			return first;
		}
	}
	
}
