import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 11280번 2-SAT - 3
 * 문제 분류: 2-SAT, SCC (타잔 알고리즘), Union Find
 * @author Giwon
 */
public class Main_11280 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		// 변수의 개수
		final int N = Integer.parseInt(st.nextToken());
		// 절의 개수
		final int M = Integer.parseInt(st.nextToken());
		
		// CNF 구성
		CNF cnf = new CNF(N);
		int u, v;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			
			cnf.addClause(u, v);
		}
		
		// SCC 구성
		cnf.findSCC();
		
		System.out.println(cnf.canMakeTrue());
		br.close();
	}

	public static class Vertex {
		// 변수 Index
		int index;
		// 명제 u => v (u가 참이면 v도 참이다)
		Set<Vertex> edges;
		// Disjoint Set 구성용
		Vertex parent;
		int depth;
		// 방문 체크
		boolean visited;
		
		public Vertex(int index) {
			this.index = index;
			this.edges = new HashSet<>();
			this.parent = null;
			this.depth = 0;
			this.visited = false;
		}
		
		// 현재 Disjoint Set의 루트 정점 탐색
		public Vertex getRoot() {
			if(this.parent == null || this.parent == this) {
				this.parent = this;
				return this;
			}
			
			this.parent = this.parent.getRoot();
			return this.parent;
		}
		
		// 두 정점을 같은 SCC로 묶기
		public static void unionSCC(Vertex A, Vertex B) {
			// 두 정점이 이미 연결되어 있는 경우는 들어오지 않음 (하나는 무조건 SCC에 포함되지 않은 정점)
			Vertex rootA = A.getRoot();
			Vertex rootB = B.getRoot();
			
			if(rootA.depth >= rootB.depth) {
				rootB.parent = rootA;
				if(rootA.depth == rootB.depth) rootA.depth++;
			} else {
				rootA.parent = rootB;
			}
		}
		
		// 두 정점이 같은 SCC에 속해있는지 체크
		public static boolean checkSCC(Vertex A, Vertex B) {
			return A.getRoot() == B.getRoot();
		}
		
		// dfs로 SCC 연결
		public void dfs(Stack<Vertex> visited) {
			Vertex prev = visited.isEmpty() ? null : visited.peek();
			// 이미 방문한 경우 생략
			if(this.visited) return;
			
			// 방문 체크
			this.visited = true;
			visited.push(this);
			for(Vertex next: edges) {
				// 이미 방문한 경우 생략
				if(next.visited) continue;
				
				// 다음 노드 탐색
				next.dfs(visited);
			}
			
			// 더 이상 방문할 수 있는 노드가 없는 경우
			if(prev == null || !this.edges.contains(prev)) {
				// 부모 정점으로 되돌아갈 수 없다면 Stack에서 자신보다 위에 있는 모든 정점과 SCC 구성
				this.parent = this;
				this.depth++;
				
				Vertex curr;
				while((curr = visited.pop()) != this) {
					// 이미 SCC를 구성한 점은 생략
					if(curr.parent != null) continue;
					Vertex.unionSCC(this, curr);
				}
			}
			
			// 부모 정점으로 리턴
		}
		
		@Override
		public String toString() {
			return Integer.toString(index);
		}
	}
	
	public static class CNF {
		// 변수의 개수
		int N;
		// 불리언 변수
		Vertex[] var;
		// Not인 불리언 변수
		Vertex[] notVar;
		// 항상 참인 변수
		Set<Integer> alwaysTrue;
		
		public CNF(int N) {
			this.N = N;
			this.var = new Vertex[N + 1];
			this.notVar = new Vertex[N + 1];
			this.alwaysTrue = new HashSet<>();
			
			for(int i = 1; i <= N; i++) {
				this.var[i] = new Vertex(i);
				this.notVar[i] = new Vertex(-i);
			}
		}
		
		public Vertex getVar(int idx) {
			if(idx > 0) return var[idx];
			else return notVar[-idx];
		}
		
		// 절 추가
		public void addClause(int u, int v) {
			// u V -u라면 해당 절은 항상 참
			if(u == -v) return;
			
			// u와 v가 같다면 u는 항상 참이어야 하는 명제
			if(u == v) alwaysTrue.add(u);
			
			// 다른 불리언 변수가 반드시 거짓인 경우 현재 변수는 반드시 참이어야 함
			if(alwaysTrue.contains(-u)) alwaysTrue.add(v);
			if(alwaysTrue.contains(-v)) alwaysTrue.add(u);
			
			// -u가 참이면 v도 참이다 (u가 항상 참이 아닌 경우에)
			if(!alwaysTrue.contains(u)) {
				getVar(-u).edges.add(getVar(v));
			} else {
				getVar(-u).edges.remove(getVar(v));
			}
			// -v가 참이면 u도 참이다 (v가 항상 참이 아닌 경우에)
			if(!alwaysTrue.contains(v)) {
				getVar(-v).edges.add(getVar(u));
			} else {
				getVar(-v).edges.remove(getVar(u));
			}
		}
		
		// SCC 관계끼리 DisjointSet 구성
		public void findSCC() {
			Stack<Vertex> visited = new Stack<>();
			
			// 모든 정점에 대해 dfs를 수행하며 SCC 구성
			for(int i = 1; i <= N; i++) {
				getVar(i).dfs(visited);
				getVar(-i).dfs(visited);
			}
		}
		
		public int canMakeTrue() {
			// 자신, not 자신이 모두 반드시 참이어야 한다면 불가능한 CNF
			for(int i = 1; i <= N; i++) {
				if(alwaysTrue.contains(i) && alwaysTrue.contains(-i)) return 0;
			}
			
			// 자신 => not 자신을 가리키는 SCC가 존재하는지 체크
			for(int i = 1; i <= N; i++) {
				// 존재하면 참을 만들 수 없는 CNF
				if(Vertex.checkSCC(getVar(i), getVar(-i))) return 0;
			}
			
			// 존재하지 않으면 참을 만들 수 있는 CNF
			return 1;
		}
	}
	
}
