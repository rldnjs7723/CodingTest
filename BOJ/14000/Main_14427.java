
/**
 * 백준 14427번 수열과 쿼리 15
 * 문제 분류: Fenwick Tree (Binary Indexed Tree, BIT)
 * @author Giwon
 */
public class Main_14427 {

	public static void main(String[] args) {
		

	}

	public static class FenwickTree {
		int N;
		int[] sequence;
		int[] tree;
		
		public FenwickTree(int N, int[] sequence) {
			this.N = N;
			this.sequence = sequence;
			// Index 1부터 시작하도록 설정
			this.tree = new int[N + 1];
			
			
		}
		
		public void init() {
			
		}
		
		public void update(int i) {
			
		}
	}
	
}
