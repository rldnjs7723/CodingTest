import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 15961번 회전 초밥
 * 문제 분류: 자료구조 (Queue, Map), 슬라이딩 윈도우
 * @author Giwon
 */
public class Main_15961 {
	public static Map<Integer, Integer> numCount = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 접시의 수
		final int N = Integer.parseInt(st.nextToken());
		// 초밥 가짓수
		final int d = Integer.parseInt(st.nextToken());
		// 연속해서 먹을 접시 수
		final int k = Integer.parseInt(st.nextToken());
		// 쿠폰 번호
		final int c = Integer.parseInt(st.nextToken());
		
		// 쿠폰으로 받은 초밥 추가
		putType(c);
		
		// 접시 입력
		Queue<Integer> plates = new ArrayDeque<>();
		for(int i = 0; i < N; i++) {
			plates.offer(Integer.parseInt(br.readLine().trim()));
		}
		
		// 연속해서 먹은 접시 번호 기록
		Queue<Integer> sequence = new ArrayDeque<>();
		// 시작 k개 만큼의 접시 먹기
		int num;
		for(int i = 0; i < k; i++) {
			// 다음에 먹을 초밥
			num = plates.poll();
			// 먹은 접시에 추가
			sequence.offer(num);
			// 먹은 초밥 종류 추가
			putType(num);
			// 맨 뒤에 복원
			plates.offer(num);
		}
		
		// 먹은 초밥 최대 가짓수
		int max = numCount.size();
		// 다른 조합 구성
		for(int i = 1; i < N; i++) {
			// 다음에 먹을 초밥
			num = plates.poll();
			// 이전에 가장 먼저 먹었던 접시 제외
			removeType(sequence.poll());
			// 먹은 접시에 추가
			sequence.offer(num);
			// 먹은 초밥 종류 추가
			putType(num);
			// 맨 뒤에 복원
			plates.offer(num);
			
			// 최대 가짓수 갱신
			max = Math.max(max, numCount.size());
		}
		
		System.out.println(max);
		br.close();
	}

	// 번호에 해당하는 값 Map에 추가
	public static void putType(int num) {
		if(!numCount.containsKey(num)) {
			numCount.put(num, 0);
		}
		
		numCount.put(num, numCount.get(num) + 1);
	}
	
	// 번호에 해당하는 값 Map에서 제거
	public static void removeType(int num) {
		numCount.put(num, numCount.get(num) - 1);
		
		if(numCount.get(num) == 0) numCount.remove(num);
	}
	
}
