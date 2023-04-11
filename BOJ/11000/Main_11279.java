import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 백준 11279번 최대 힙
 * 문제 분류: 자료구조 (우선순위 큐)
 * @author Giwon
 */
public class Main_11279 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = Integer.parseInt(br.readLine());
		Heap maxHeap = new Heap(N);
		int input;
		for(int i = 0; i < N; i++) {
			input = Integer.parseInt(br.readLine());
			
			if(input == 0) {
				bw.write(maxHeap.poll() + "\n");
			} else {
				maxHeap.offer(input);
			}
		}
		
		bw.flush();
		br.close();
		bw.close();
	}
	
	// 힙 직접 구현
	public static class Heap {
		int[] arr;
		int size;
		
		Heap(int N) {
			// Index 1부터 시작하도록 구현
			arr = new int[N + 1];
			size = 0;
		}
		
		// 부모 노드 Index
		private int parentIdx(int curr) {
			return curr / 2;
		}
		
		// 왼쪽 자식 노드 Index
		private int leftChild(int curr) {
			return curr * 2;
		}
		
		// 오른쪽 자식 노드 Index
		private int rightChild(int curr) {
			return curr * 2 + 1;
		}
		
		private void goUp(int curr) {
			while(curr > 1) {
				int next = parentIdx(curr);
				int temp = arr[next];
				if(temp < arr[curr]) {
					arr[next] = arr[curr];
					arr[curr] = temp;
					curr = next;
				} else break;
			}
		}
		
		public void offer(int val) {
			arr[++size] = val;
			goUp(size);
		}
		
		private void goDown(int curr) {		
			int leftIdx = leftChild(curr);
			int rightIdx = Math.min(size, rightChild(curr));
			
			int left, right;
			while(leftIdx <= size) {
				left = arr[leftIdx];
				right = arr[rightIdx];
				
				if(left >= right && arr[curr] < left) {
					arr[leftIdx] = arr[curr];
					arr[curr] = left;
					curr = leftIdx;
					leftIdx = leftChild(curr);
					rightIdx = Math.min(size, rightChild(curr));
				} else if(left < right && arr[curr] < right) {
					arr[rightIdx] = arr[curr];
					arr[curr] = right;
					curr = rightIdx;
					leftIdx = leftChild(curr);
					rightIdx = Math.min(size, rightChild(curr));
				} else break;
			}
		}
		
		public int poll() {
			if(size == 0) return 0;
			
			int first = arr[1];
			arr[1] = arr[size--];
			goDown(1);
			return first;
		}
	}
}