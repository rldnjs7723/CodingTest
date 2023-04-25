import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 백준 2150번 Strongly Connected Component
 * 문제 분류: Strongly Connected Component (코사라주 알고리즘)
 * @author Giwon
 */
public class Main_2150 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 정점 개수
		final int V = Integer.parseInt(st.nextToken());
		// 간선 개수
		final int E = Integer.parseInt(st.nextToken());
		
		// 간선 입력
		Graph graph = new Graph(V);
		int A, B;
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			graph.putEdge(A, B);
		}
		
		// SCC 탐색
		Queue<SCC> sccList = graph.findScc();
		int size = sccList.size();
		bw.write(size + "\n");
		SCC curr;
		for(int i = 0; i < size; i++) {
			curr = sccList.poll();
			
			for(int val: curr) {
				bw.write(val + " ");
			}
			bw.write(-1 + "\n");
		}
		
		bw.close();
		br.close();
	}
	
	// Strongly Connected Component
	@SuppressWarnings("serial")
	public static class SCC extends TreeSet<Integer> implements Comparable<SCC>{

		@Override
		public int compareTo(SCC o) {
			return Integer.compare(this.first(), o.first());
		}
	}
	
	public static class Vertex {
		// 현재 정점 번호
		int index;
		// 간선
		Set<Integer> edges;
		// 역방향 간선
		Set<Integer> reverse;
		// 방문 체크
		boolean visited;
		// scc를 구성했는지 여부 체크
		boolean finished;
		
		public Vertex(int index) {
			this.index = index;
			this.edges = new HashSet<>();
			this.reverse = new HashSet<>();
			this.visited = false;
			this.finished = false;
		}
		
		public void putEdge(int v) {
			edges.add(v);
		}
		
		public void putReverse(int v) {
			reverse.add(v);
		}
		
		public void dfs(Vertex[] vertices, Stack<Integer> finished) {
			// 이미 방문한 경우 생략
			if(visited) return;
			// 방문 체크
			visited = true;
			
			Vertex next;
			for(int v: edges) {
				next = vertices[v];
				// 이미 방문한 경우 생략
				if(next.visited) continue;
				
				// 자식 정점 방문
				next.dfs(vertices, finished);
			}
			
			// 탐색이 끝나면 Stack에 저장
			finished.push(this.index);
		}
	}
	
	public static class Graph {
		public static final int ROOT = 1;
		
		// 정점 개수
		int V;
		// 정점
		Vertex[] vertices;
		
		public Graph(int V) {
			this.V = V;
			this.vertices = new Vertex[V + 1];
			
			for(int i = 1; i <= V; i++) {
				this.vertices[i] = new Vertex(i);
			}
		}
		
		public void putEdge(int u, int v) {
			vertices[u].putEdge(v);
			// SCC 체크를 위한 역방향 간선 추가
			vertices[v].putReverse(u);
		}
		
		public Queue<SCC> findScc() {
			Queue<SCC> sccList = new PriorityQueue<>();
			
			// 방문한 정점 Stack
			Stack<Integer> finished = new Stack<>();
			// dfs 탐색
			Stack<Integer> dfsStack = new Stack<>();
			for(int i = 1; i <= V; i++) {
				vertices[i].dfs(vertices, finished);
			}
			
			// 가장 마지막에 완료된 정점부터 역순으로 돌아가며 체크
			int currIdx;
			Vertex curr, next;
			while(!finished.isEmpty()) {
				currIdx = finished.pop();
				curr = vertices[currIdx];
				// 이미 scc를 구성한 경우 생략
				if(curr.finished) continue;
				// SCC 구성
				SCC scc = new SCC();
				
				// 역방향 그래프 dfs 탐색하여 SCC 구성
				dfsStack.add(curr.index);
				while(!dfsStack.isEmpty()) {
					currIdx = dfsStack.pop();
					curr = vertices[currIdx];
					
					// 방문 체크
					curr.finished = true;
					// SCC에 포함
					scc.add(curr.index);
					
					// 자식 정점 체크
					for(int nextIdx: curr.reverse) {
						next = vertices[nextIdx];
						// 이미 방문한 경우 생략
						if(next.finished) continue;
						
						// 자식 정점 Stack에 추가
						dfsStack.push(next.index);
					}
				}
				
				// SCC 리스트에 추가
				sccList.add(scc);
			}
			
			return sccList;
		}
	}
	
	
}
