import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 백준 13023번 ABCDE
 * 문제 분류: 그래프 탐색, DFS
 * @author Giwon
 */
public class Main_13023 {
	// DFS로 탐색할 최대 깊이
	public static final int TARGET = 4;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 사람 수
		int N = Integer.parseInt(st.nextToken());
		// 간선 수
		int M = Integer.parseInt(st.nextToken());
		
		// 간선 입력
		Graph graph = new Graph(N);
		int a, b;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			
			graph.connect(a, b);
		}
		
		System.out.println(graph.checkFriendship() ? 1 : 0);
		br.close();
	}

	// 친구 관계 저장할 정점
	@SuppressWarnings("serial")
	public static class Vertex extends ArrayList<Integer>{
		boolean visited;
		
		public Vertex() {
			super();
			this.visited = false;
		}
	}
	
	// 친구 관계 저장할 그래프
	public static class Graph {
		int N;
		Vertex[] V;
		
		public Graph(int N) {
			this.N = N;
			this.V = new Vertex[N];
			
			for(int i = 0; i < N; i++) {
				V[i] = new Vertex();
			}
		}
		
		// 간선 추가
		public void connect(int a, int b) {
			// 중복되는 입력은 없다.
			// 친구 관계는 무향 그래프
			V[a].add(b);
			V[b].add(a);
		}
		
		// 모든 사람 돌아가면서 답이 나올 때까지 DFS 탐색
		public boolean checkFriendship() {
			boolean check;
			for(int i = 0; i < N; i++) {
				V[i].visited = true;
				check = checkDepth(i, 0);
				// 깊이 4인 관계를 찾았다면 true
				if(check) return true;
				// 방문 체크 롤백
				V[i].visited = false;
			}
			// 깊이 4인 관계가 없다면 false
			return false;
		}
		
		// 재귀적으로 DFS 탐색 수행
		private boolean checkDepth(int curr, int depth) {
			// DFS로 탐색한 깊이가 4가 되면 ABCDE의 관계
			if(depth == TARGET) return true;
			
			Vertex v = V[curr];
			boolean check;
			// 방문하지 않은 모든 친구 탐색
			for(int i: v) {
				if(!V[i].visited) {
					V[i].visited = true;
					check = checkDepth(i, depth + 1);
					// 이번에 탐색한 경로가 맞다면 true 리턴
					if(check) return true;
					// 방문 체크 롤백
					V[i].visited = false;
				}
			}
			// 깊이가 4인 경우가 없다면 false 리턴
			return false;
		}
	}
}
