import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 1238번 Contact
 * 문제 분류: 
 * @author Giwon
 */
public class Solution_1238 {
	public static final int MAX = 100;
	public static final int T = 10;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
			
		Graph graph;
		int N, start, from, to;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 데이터 길이
			N = Integer.parseInt(st.nextToken()) / 2;
			// 시작점
			start = Integer.parseInt(st.nextToken());
			
			// 그래프 입력
			graph = new Graph();
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i++) {
				from = Integer.parseInt(st.nextToken());
				to = Integer.parseInt(st.nextToken());
				
				graph.add(from, to);
			}
			
			System.out.println("#" + test_case + " " + graph.bfs(start));
		}
		
		br.close();
	}
	
	// 간선 저장
	@SuppressWarnings("serial")
	public static class Edge extends ArrayList<Integer>{
		int depth, idx;
		boolean visited;
		
		public Edge(int idx) {
			super();
			this.idx = idx;
			this.depth = 0;
			this.visited = false;
		}
	}
	
	public static class Graph {
		Edge[] E;
		
		public Graph() {
			E = new Edge[MAX + 1];
			
			for(int i = 1; i <= MAX; i++) {
				E[i] = new Edge(i);
			}
		}
		
		public void add(int from, int to) {
			// 유향 그래프
			E[from].add(to);
		}
		
		// bfs로 가장 마지막에 연락 받는 사람 중 가장 나중에 연락을 받는 사람  
		public int bfs(int start) {
			int maxDepth = 0, maxIdx = 0;
			
			Queue<Edge> queue = new ArrayDeque<>();
			queue.offer(E[start]);
			Edge curr, next;
			while(!queue.isEmpty()) {
				curr = queue.poll();
				
				// 현재 정점에 인접한 정점 중 방문하지 않은 다른 정점을 탐색
				for(int idx: curr) {
					next = E[idx];
					// 방문 했다면 생략
					if(next.visited) continue;
					
					// 방문하지 않았다면 깊이 갱신
					next.visited = true;
					next.depth = curr.depth + 1;
					
					if(next.depth >= maxDepth) {
						if(next.depth > maxDepth) {
							// 깊이가 다르면 현재 정점을 기록
							maxDepth = next.depth;
							maxIdx = next.idx;
						} else {
							// 깊이가 같으면 가장 큰 번호를 기록
							maxIdx = Math.max(maxIdx, next.idx);
						}
					}
					
					queue.offer(next);
				}
			}
			
			return maxIdx;
		}
	}
	
}
