import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 10868번 최솟값
 * 문제 분류: Fenwick Tree (Binary Indexed Tree, BIT)
 * 참고: https://tkql.tistory.com/69
 * @author Giwon
 */
public class Main_10868 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		final int N = Integer.parseInt(st.nextToken());
		final int M = Integer.parseInt(st.nextToken());
		
		FenwickTree fenwickTree = new FenwickTree(N);
		for(int i = 1; i <= M; i++) {
			fenwickTree.updateMinVal(i, Integer.parseInt(br.readLine().trim()));
		}
		
		
		bw.close();
		br.close();
	}

	public static class FenwickTree {
		int N;
		// 수열 값
		int[] sequence;
		// 루트 값이 1인 트리
		int[] tree;
		// 루트 값이 n인 트리
		int[] tree2;
		
		public FenwickTree(int N) {
			this.N = N;
			this.sequence = new int[N + 1];
			// Index 1부터 시작하도록 설정
			this.tree = new int[N + 1];
			// Index n부터 시작하도록 설정
			this.tree2 = new int[N + 1];
			
			// 값 초기화
			for(int i = 0; i <= N; i++) {
				this.tree[i] = Integer.MAX_VALUE;
				this.tree2[i] = Integer.MAX_VALUE;
			}
		}
		
		// a, b 사이의 최솟값 리턴
		public int getMinValue(int a, int b) {
			int min = Integer.MAX_VALUE;
			
			int prev, curr;
			prev = a;
			curr = prev + (prev & - prev);
			while(curr <= b) {
				min = Math.min(min, tree2[prev]);
				prev = curr;
				curr = prev + (prev & - prev);
			}
			min = Math.min(min, sequence[prev]);
					
			prev = b;
			curr = prev - (prev & -prev);
			while(curr >= a) {
				min = Math.min(min, tree[prev]);
				prev = curr;
				curr = prev - (prev & -prev);
			}
			
			return min;
		}
		
		// 값 갱신 (정방향, 역방향 한 번씩)
		public void updateMinVal(int i, int val) {
			sequence[i] = val;
			update(i, val);
			update2(i, val);
		}
		
		// 일반적인 펜윅 트리 업데이트
		public void update(int i, int val) {
			while(i <= N) {
				tree[i] = Math.min(tree[i], val);
				i += i & -i;
			}
		}
		
		// 역방향 펜윅 트리 업데이트
		public void update2(int i, int val) {
			while(i > 0) {
				tree[i] = Math.min(tree[i], val);
				i -= i & -i;
			}
		}
		
		
	}
	
}
