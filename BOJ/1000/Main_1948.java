import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1948번 임계경로
 * 문제 분류: 위상 정렬, DAG에서의 최장 경로
 * @author Giwon
 */
public class Main_1948 {
	public static final int INF = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 도시의 개수
		int N = Integer.parseInt(br.readLine().trim());
		// 도로의 개수
		int M = Integer.parseInt(br.readLine().trim());
		
		// 도로 입력
		Graph graph = new Graph(N);
		int u, v, cost;
		StringTokenizer st;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 출발 도시
			u = Integer.parseInt(st.nextToken());
			// 도착 도시
			v = Integer.parseInt(st.nextToken());
			// 이동에 걸리는 시간
			cost = Integer.parseInt(st.nextToken());
			
			graph.put(u, v, cost);
		}
		
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		// 위상 정렬로 DAG에서의 최장 경로 탐색
		graph.topologicalSort(start);
		// 역방향으로 위상 정렬 수행하면서 최장 경로를 이루는 간선 체크
		graph.reverseTopologicalSort(end);
		
		// 최장 경로 길이 출력
		System.out.println(graph.V[end].cost);
		// 최장 경로를 이루는 간선 개수 출력
		System.out.println(graph.countLongest());
		br.close();
	}
	
	public static class Edge implements Comparable<Edge> {
		int v, cost;
		
		public Edge(int v, int cost) {
			this.v = v;
			this.cost = cost;
		}
		
		// 비용 기준 오름차순 정렬
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	public static class Vertex {
		public static final int NULL = -1;
		// 출발 도시에서 현재 도시에 도달할 때 필요한 최대 비용
		int cost;
		// 진입 간선
		Map<Integer, Integer> incoming;
		// 진입 간선 개수
		int incomingCount;
		// 진입 간선에서 최장 경로를 이루는 간선 여부
		Map<Integer, Boolean> longestEdge;
		// 진출 간선
		Map<Integer, Integer> outgoing;
		
		public Vertex() {
			this.cost = INF;
			this.incomingCount = 0;
			this.incoming = new HashMap<>();
			this.longestEdge = new HashMap<>();
			this.outgoing = new HashMap<>();
		}
		
		// 진입 간선 입력 (진출 간선의 역 방향. 위상 정렬에 사용)
		public void putIncoming(int v, int cost) {
			// 가장 가중치 값이 큰 값만 남기기
			if(incoming.containsKey(v)) incoming.put(v, Math.max(incoming.get(v), cost));
			else {
				incoming.put(v, cost);
				incomingCount++;
				longestEdge.put(v, false);
			}
		}
		
		// 최장 경로를 이루는 간선이라 표시
		public void setLongest(int v) {
			longestEdge.put(v, true);
		}
		
		// 방문 체크 확인용
		public boolean getLongest(int v) {
			return longestEdge.get(v);
		}
		
		// 진출 간선 입력 (최장 경로 탐색에 사용)
		public void putOutgoing(int v, int cost) {
			// 가장 가중치 값이 작은 값만 남기기
			if(outgoing.containsKey(v)) outgoing.put(v, Math.min(outgoing.get(v), cost));
			else outgoing.put(v, cost);
		}
		
		@Override
		public String toString() {
			return Integer.toString(cost);
		}
	}
	
	public static class Graph {
		int N;
		Vertex[] V;
		
		public Graph(int N) {
			this.N = N;
			this.V = new Vertex[N + 1];
			
			for(int i = 1; i <= N; i++) {
				this.V[i] = new Vertex();
			}
		}
		
		// 간선 입력
		public void put(int u, int v, int cost) {
			// 음의 가중치로 바꿔서 최단 경로 문제로 변경
			V[u].putOutgoing(v, -cost);
			// 도착 도시를 시작점으로 설정하여 위상 정렬하기 위해 기존의 방향과 반대로 저장
			V[v].putIncoming(u, cost);
		}
		
		// 위상 정렬을 통해 시작 도시에서 각 도시까지의 최단 거리 계산
		public void topologicalSort(int start) {
			int u, v, cost;
			// BFS로 위상 정렬 수행
			Queue<Integer> sortedVertex = new ArrayDeque<>();
			Queue<Integer> bfsQueue = new ArrayDeque<>();
			bfsQueue.offer(start);
			while(!bfsQueue.isEmpty()) {
				u = bfsQueue.poll();
				sortedVertex.offer(u);
				
				// 진출 간선에 연결된 모든 정점에 대해 진입 간선 제거
				for(int vertex: V[u].outgoing.keySet()) {
					V[vertex].incomingCount--;
					if(V[vertex].incomingCount == 0) bfsQueue.offer(vertex);
				}
			}
			
			// 시작 정점 비용 0으로 초기화
			V[start].cost = 0;
			// 위상 정렬된 순서로 Relaxation 수행
			while(!sortedVertex.isEmpty()) {
				u = sortedVertex.poll();
				
				for(Entry<Integer, Integer> entry: V[u].outgoing.entrySet()) {
					v = entry.getKey();
					cost = entry.getValue();
					
					// Relaxation
					V[v].cost = Math.min(V[v].cost, V[u].cost + cost);
				}
			}
			
			// 모든 정점의 비용 양수로 변환
			for(u = 1; u <= N; u++) {
				V[u].cost = -V[u].cost;
			}
		}
		
		
		// 역 방향으로 위상 정렬을 수행하면서 최장 경로를 이루는 간선 표시
		public void reverseTopologicalSort(int end) {
			// DFS로 위상 정렬 수행
			Stack<Integer> dfsStack = new Stack<>();
			dfsStack.push(end);
			
			// 재귀로 수행하면 정점 수가 1만 개 이므로 스택 메모리가 터질 가능성 존재
			int u, v, cost;
			while(!dfsStack.isEmpty()) {
				u = dfsStack.pop();
				
				for(Entry<Integer, Integer> entry: V[u].incoming.entrySet()) {
					v = entry.getKey();
					cost = entry.getValue();
					
					// 이미 탐색한 경로는 생략
					if(V[u].getLongest(v)) continue;
					
					// 현재 간선을 통해 이동한 경로가 최장 경로를 이루면 체크 후 반복하여 탐색
					if(V[v].cost + cost == V[u].cost) {
						V[u].setLongest(v);
						dfsStack.push(v);
					}
				}
			}
		}
		
		// 최장 경로를 이루는 간선 수 카운트
		public int countLongest() {
			int count = 0;
			for(int u = 1; u <= N; u++) {
				for(boolean val: V[u].longestEdge.values()) {
					if(val) count++;
				}
			}
			
			return count;
		}
		
		@Override
		public String toString() {
			return Arrays.toString(V);
		}
	}
	
}
