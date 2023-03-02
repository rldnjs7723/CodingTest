import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1753번 최단경로 
 * 문제 분류: Dijkstra 알고리즘
 * @author Giwon
 */
public class Main_1753 {
	public static final int INF = Integer.MAX_VALUE;
	public static final String INFINITE = "INF\n";
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 정점 개수
        int V = Integer.parseInt(st.nextToken());
        // 간선 개수
        int E = Integer.parseInt(st.nextToken());
        // 시작점 Index
        int K = Integer.parseInt(br.readLine());
        Graph graph = new Graph(V);
        
        int u, v, w;
        for(int i = 0; i < E; i++) {
        	st = new StringTokenizer(br.readLine());
        	// 시작 정점
        	u = Integer.parseInt(st.nextToken());
        	// 종료 정점
        	v = Integer.parseInt(st.nextToken());
        	// 간선 가중치
        	w = Integer.parseInt(st.nextToken());
        	
        	graph.add(u, v, w);
        }
        // 시작 정점 기준으로 다익스트라 알고리즘으로 탐색 수행
        graph.dijkstra(K);

        // 가중치 출력
        for(int i = 1; i <= V; i++) {
        	w = graph.getWeight(i);
        	if(w == INF) bw.write(INFINITE);
        	else bw.write(w + "\n");
        }
        
        bw.close();
        br.close();
    }
    
    // 우선순위 큐에 넣을 간선 클래스
    public static class Edge implements Comparable<Edge> {
    	// 목표 정점 v, 목표 가중치 w
    	int v, w;
    	
    	public Edge(int v, int w) {
    		this.v = v;
    		this.w = w;
		}
    	
    	// 가중치 오름차순으로 정렬
    	@Override
    	public int compareTo(Edge o) {
    		return Integer.compare(this.w, o.w);
    	}
    }
    
    // 정점 클래스
    public static class Vertex {
    	int w;
    	boolean visited;
    	// 현재 정점을 시작으로 하는 간선
    	Map<Integer, Integer> edges;
    	
    	public Vertex() {
    		// 시작 가중치 무한으로 초기화
    		this.w = Integer.MAX_VALUE;
    		this.visited = false;
    		this.edges = new HashMap<>();
		}
    	
    	// 간선 추가
    	public void add(int v, int w) {
    		// 여러 개의 간선 중 가장 가중치가 작은 간선만 남기기
    		if(edges.containsKey(v)) edges.put(v, Math.min(edges.get(v), w));
    		else edges.put(v, w);
    	}
    }
    
    // 그래프 클래스
    public static class Graph {
    	int V;
    	Vertex[] vertices;
    	
    	public Graph(int V) {
			this.V = V;
			vertices = new Vertex[V + 1];
			
			for(int i = 1; i <= V; i++) {
				vertices[i] = new Vertex();
			}
		}
    	
    	// 간선 입력
    	public void add(int u, int v, int w) {
    		vertices[u].add(v, w);
    	}
    	
    	public int getWeight(int u) {
    		return vertices[u].w;
    	}
    	
    	// 시작점과 다른 모든 정점 사이의 최단 경로 가중치를 다익스트라 알고리즘으로 계산
    	public void dijkstra(int start) {
    		// 최소 가중치 간선을 구하기 위한 우선순위 큐
    		Queue<Edge> minEdge = new PriorityQueue<>();
    		// 시작 정점 가중치 0으로 초기화
    		vertices[start].w = 0;
    		minEdge.offer(new Edge(start, 0));
    		Edge curr;
    		Vertex currV, nextV;
    		int v, w;
    		while(!minEdge.isEmpty()) {
    			curr = minEdge.poll();
    			currV = vertices[curr.v];
    			// 이미 방문한 정점은 생략
    			if(currV.visited) continue;
    			// 방문 체크
    			currV.visited = true;

    			// 다른 정점의 가중치 갱신
    			for(Entry<Integer, Integer> edge: currV.edges.entrySet()) {
    				// 간선의 종점
    				v = edge.getKey();
    				// 간선의 가중치
    				w = edge.getValue();
    				// 다음 정점
    				nextV = vertices[v];
    				// 이미 방문한 정점은 생략
    				if(nextV.visited) continue;
    				
    				// 가중치 갱신
    				if(nextV.w > currV.w + w) {
    					nextV.w = currV.w + w;
    					minEdge.offer(new Edge(v, nextV.w));
    				}
    			}
    		}
    	}
    }
}