import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * SWEA 2930번 힙
 * 문제 분류: 자료 구조 (힙)
 * @author Giwon
 */
public class Solution_2930 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, A, B;
		String[] input;
		StringBuilder sb = new StringBuilder();
		// 최대 힙 생성
		Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); 
		for(int test_case = 1; test_case <= T; test_case++) {
			// 연산 개수
			N = Integer.parseInt(br.readLine().trim());
			
			// 연산 입력
			maxHeap.clear();
			sb.setLength(0);
			for(int i = 0; i < N; i++) {
				input = br.readLine().split(" ");
				A = Integer.parseInt(input[0]);
				
				if(A == 1) {
					// 입력이 두 개 있는 경우 힙에 값 입력
					B = Integer.parseInt(input[1]);
					maxHeap.offer(B);
				} else if(A == 2){
					// 입력이 하나만 있는 경우 최대 값 출력 후 삭제
					if(maxHeap.isEmpty()) sb.append(" " + -1);
					else sb.append(" " + maxHeap.poll());
				}
			}
			
			System.out.println("#" + test_case + sb.toString());
		}
		br.close();
	}
}
