import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 백준 1927번 최소 힙
 * 문제 분류: 자료 구조 (우선순위 큐)
 * @author Giwon
 */
public class Main_1927 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		final int N = Integer.parseInt(br.readLine().trim());
		int val;
		Queue<Integer> minHeap = new PriorityQueue<>();
		for(int i = 0; i < N; i++) {
			val = Integer.parseInt(br.readLine().trim());
			
			if(val == 0) {
				val = minHeap.isEmpty() ? 0 : minHeap.poll();
				bw.write(val + "\n");
			} else {
				minHeap.offer(val);
			}
		}
		
		bw.close();
		br.close();
	}
}
