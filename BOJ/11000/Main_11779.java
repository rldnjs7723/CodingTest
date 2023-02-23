import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 11779번 최소비용 구하기 2
 * 문제 분류: Dijkstra 알고리즘
 * @author Giwon
 */
public class Main_11779 {
	public static final long INF = Long.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 도시의 개수
		int n = Integer.parseInt(br.readLine());
		// 버스의 개수
		int m = Integer.parseInt(br.readLine());
		
		Graph graph = new Graph(n);
		// 간선 입력
		int u, v, w;
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			graph.addEdge(u, v, w);
		}
		
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		long cost, count;
		// 다익스트라 알고리즘으로 최단경로 탐색
		graph.dijkstra(start);
		// 목표 정점에서부터 역순으로 이전 정점 탐색
		Stack<Integer> temp = new Stack<>();
		Vertex curr = graph.getVertex(end);
		// 최소 비용 저장
		cost = curr.weight;
		while(curr.idx != start) {
			temp.push(curr.idx);
			curr = curr.prev;
		}
		// 시작 정점 포함
		temp.push(curr.idx);
		// 경로에 포함된 도시 개수 저장
		count = temp.size();
		
		// 정점 방문 순서 문자열 구성
		StringBuilder sb = new StringBuilder();
		while(!temp.isEmpty()) {
			sb.append(temp.pop() + " ");
		}
		
		System.out.println(cost);
		System.out.println(count);
		System.out.println(sb.toString());
		br.close();
	}

	// 간선 정보 저장 클래스
	@SuppressWarnings("serial")
	public static class Vertex extends HashMap<Integer, Integer> implements Comparable<Vertex>{
		// 도시의 번호는 1부터 시작
		int idx;
		long weight;
		// 이전에 최소 가중치 갱신한 정점 저장용
		Vertex prev;
		
		// 정점 시작 가중치 무한으로 초기화
		public Vertex(int idx) {
			super();
			this.idx = idx;
			this.weight = INF;
			this.prev = null;
		}

		// 우선 순위 큐 활용을 위한 Comparable 인터페이스 구현
		// 오름차순 정렬
		@Override
		public int compareTo(Vertex o) {
			return Long.compare(this.weight, o.weight);
		}
	}
	
	public static class Graph {
		Vertex[] V;
		int n;
		
		public Graph(int n) {
			this.n = n;
			this.V = new Vertex[n];
			
			for(int i = 0; i < n; i++) {
				V[i] = new Vertex(i + 1);
			}
		}
		
		// 간선 추가
		public void addEdge(int u, int v, int w) {
			// 유향 그래프 ( u -> v )
			if(getVertex(u).containsKey(v)) {
				// 간선은 최소 가중치를 가지는 것만 저장
				getVertex(u).put(v, Math.min(getVertex(u).get(v), w));
			} else {
				getVertex(u).put(v, w);
			}
		}
		
		public Vertex getVertex(int u) {
			return V[u - 1];
		}
		
		public void dijkstra(int start) {
			// 시작 정점 가중치 0
			getVertex(start).weight = 0;
			// 시작 정점 대기열에 추가
			Queue<Vertex> minWeight = new PriorityQueue<>();
			minWeight.offer(getVertex(start));
			
			// 현재 정점에서 다른 모든 정점으로 가는 가중치 갱신
			Vertex curr, target;
			while(!minWeight.isEmpty()) {
				curr = minWeight.poll();
				
				for(Entry<Integer, Integer> edge: curr.entrySet()) {
					target = getVertex(edge.getKey());
					
					// 목표 정점의 가중치가 현재 정점의 가중치 + 간선의 가중치보다 큰 경우
					if(target.weight > curr.weight + edge.getValue()) {
						// 가중치 갱신
						target.weight = curr.weight + edge.getValue();
						// 갱신한 정점 기록
						target.prev = curr;
						// 정점 대기열에 추가
						minWeight.offer(target);
					}
				}
			}
		}
	}
}
