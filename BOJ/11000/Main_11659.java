import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 11659번 구간 합 구하기 4
 * 문제 분류: Fenwick Tree (Binary Indexed Tree, BIT)
 * @author Giwon
 */
public class Main_11659 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 수의 개수
		final int N = Integer.parseInt(st.nextToken());
		// 쿼리 개수
		final int M = Integer.parseInt(st.nextToken());
		
		// 수열 입력
		int[] sequence = new int[N + 1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			sequence[i] = Integer.parseInt(st.nextToken());
		}

		FenwickTree tree = new FenwickTree(N, sequence);
		// 쿼리 입력
		int i, j;
		for(int q = 0; q < M; q++) {
			st = new StringTokenizer(br.readLine());
			i = Integer.parseInt(st.nextToken());
			j = Integer.parseInt(st.nextToken());
			
			bw.write((tree.sum(j) - tree.sum(i - 1)) + "\n");
		}
		
		bw.close();
		br.close();
	}
	
	public static class FenwickTree {
		int N;
		int[] sequence;
		int[] tree;
		
		public FenwickTree(int N, int[] sequence) {
			this.N = N;
			this.sequence = sequence;
			this.tree = new int[N + 1];
			
			for(int i = 1; i <= N; i++) {
				update(i, sequence[i]);
			}
		}
		
		// 1 ~ i까지의 합 계산
		public int sum(int i) {
			int result = 0;
			while(i > 0) {
				result += tree[i];
				// 마지막 1의 값을 빼는 방식으로 수행
				i -= i & -i;
			}
			
			return result;
		}
		
		public void update(int i, int diff) {
			// 수를 담당하는 구간 전부 갱신
			while(i <= N) {
				tree[i] += diff;
				// 마지막 1의 값을 더하는 방식으로 수행
				i += i & -i;
			}
		}
		
	}
}
