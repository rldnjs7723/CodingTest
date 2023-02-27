import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * SWEA 3289번 서로소 집합
 * 문제 분류: Union Find
 * @author Giwon
 */
class Solution_3289
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		DisjointSet[] tree;
		int n, m, o, a, b;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 집합의 개수
			n = Integer.parseInt(st.nextToken());
			// 연산의 개수
			m = Integer.parseInt(st.nextToken());
			
			// MakeSet
			tree = new DisjointSet[n + 1];
			for(int i = 1; i <= n; i++) {
				tree[i] = new DisjointSet();
			}
			
			// 연산 입력
			sb.setLength(0);
			for(int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());
				// 연산
				o = Integer.parseInt(st.nextToken());
				// 대상 집합
				a = Integer.parseInt(st.nextToken());
				b = Integer.parseInt(st.nextToken());
				
				if(o == 0) {
					// 합집합
					DisjointSet.unionSet(tree[a], tree[b]);
				} else {
					// 두 원소가 같은 집합인지 확인
					sb.append(DisjointSet.isSameSet(tree[a], tree[b]) ? 1 : 0);
				}
			}
			
			System.out.println("#" + test_case + " " + sb.toString());
		}
		
		br.close();
	}
	
	public static class DisjointSet {
		int height;
		DisjointSet parent;
		
		// MakeSet
		public DisjointSet() {
			this.height = 0;
			this.parent = null;
		}
		
		// FindSet
		public DisjointSet findSet() {
			if(this.parent == null) return this;
			// Path Compression
			this.parent = this.parent.findSet();
			return this.parent;
		}
		
		// UnionSet
		public static void unionSet(DisjointSet A, DisjointSet B) {
			DisjointSet rootA = A.findSet();
			DisjointSet rootB = B.findSet();
			
			// 루트 노드가 같으면 같은 집합
			if(rootA == rootB) return;
			
			// Rank를 이용한 Union
			if(rootA.height >= rootB.height) {
				rootB.parent = rootA;
				// 두 집합의 높이가 같으면 높이 증가
				if(rootA.height == rootB.height) rootA.height++;
			} else {
				rootA.parent = rootB;
			}
		}
		
		// 두 집합이 같은 집합인지 확인
		public static boolean isSameSet(DisjointSet A, DisjointSet B) {
			return A.findSet() == B.findSet();
		}
	}
}