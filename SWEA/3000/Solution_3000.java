import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 3000번 중간값 구하기
 * 문제 분류: 자료 구조 (힙)
 * @author Giwon
 */
public class Solution_3000 {
	public static final int DIV = 20171109;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, A, X, Y;
		Note note;
		int sum;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 공책에 쓰는 횟수
			N = Integer.parseInt(st.nextToken());
			// 공책에 처음에 쓸 수
			A = Integer.parseInt(st.nextToken());
			note = new Note(A);
			
			// 공책에 자연수 쓰고 중간값 받기
			sum = 0;
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				X = Integer.parseInt(st.nextToken());
				Y = Integer.parseInt(st.nextToken());
				
				sum += note.getMedian(X, Y);
				// 나머지만 남기기
				sum %= DIV;
			}
			
			System.out.println("#" + test_case + " " + sum);
		}
		br.close();
	}

	public static class Note {
		int median;
		// 최대, 최소를 빠르게 찾기 위한 최대, 최소 힙
		Queue<Integer> minHeap, maxHeap;
		
		public Note(int val) {
			// 시작 중간값 설정
			this.median = val;
			// 현재 중간값보다 큰 값을 넣을 힙
			this.minHeap = new PriorityQueue<>();
			// 현재 중간값보다 작은 값을 넣을 힙
			this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
		}
		
		// 수 기록하기
		public void put(int val) {
			if(val <= median) maxHeap.offer(val);
			else minHeap.offer(val);
		}
		
		// 두 힙 균형 맞추기
		public void balance() {
			if(minHeap.size() > maxHeap.size()) {
				maxHeap.offer(median);
				median = minHeap.poll();
			} else if(minHeap.size() < maxHeap.size()) {
				minHeap.offer(median);
				median = maxHeap.poll();
			}
		}
		
		public int getMedian(int val1, int val2) {
			// 두 수 추가
			put(val1);
			put(val2);
			
			// 두 힙 균형 맞추고 중간값 업데이트
			while(minHeap.size() != maxHeap.size()) {
				balance();
			}
			
			return median;
		}
	}
}
