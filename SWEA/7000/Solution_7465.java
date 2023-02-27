import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * SWEA 7465번 창용 마을 무리의 개수
 * 문제 분류: Union Find
 * @author Giwon
 */
public class Solution_7465 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		// 사람 수, 관계 수
		int N, M, A, B;
		DisjointSet[] tree;
		Set<DisjointSet> duplicate = new HashSet<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 사람 수
			N = Integer.parseInt(st.nextToken());
			// 관계 수
			M = Integer.parseInt(st.nextToken());
			
			// MakeSet
			tree = new DisjointSet[N + 1];
			for(int i = 1; i <= N; i++) {
				tree[i] = new DisjointSet(i);
			}
			
			// 관계 입력
			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				A = Integer.parseInt(st.nextToken());
				B = Integer.parseInt(st.nextToken());
				// UnionSet
				DisjointSet.unionSet(tree[A], tree[B]);
			}
			
			// 중복된 관계 탐색 (루트 노드의 총 개수 카운트)
			duplicate.clear();
			for(int i = 1; i <= N; i++) {
				duplicate.add(tree[i].findSet());
			}
			
			System.out.println("#" + test_case + " " + duplicate.size());
		}
		
		br.close();
	}

	public static class DisjointSet {
		// 현재 번호
		int val;
		// 현재 트리의 높이
		int height;
		// 부모 노드
		DisjointSet parent;
		
		// MakeSet
		public DisjointSet(int val) {
			this.val = val;
			this.height = 0;
			this.parent = null;
		}
		
		// FindSet
		public DisjointSet findSet() {
			// 부모 노드가 없다면 자신이 루트 노드
			if(this.parent == null) return this;
			// Path Compression
			this.parent = this.parent.findSet();
			return this.parent;
		}
		
		// UnionSet
		public static void unionSet(DisjointSet A, DisjointSet B) {
			DisjointSet rootA = A.findSet();
			DisjointSet rootB = B.findSet();
			
			// 두 집합의 루트 노드가 같으면 이미 같은 집합
			if(rootA == rootB) return;
			
			// Rank를 사용한 Union
			if(rootA.height >= rootB.height) {
				rootB.parent = rootA;
				// 두 집합의 높이가 같은 경우에만 높이가 증가
				if(rootA.height == rootB.height) rootA.height++;
			} else {
				rootA.parent = rootB;
			}
		}
	}
}
