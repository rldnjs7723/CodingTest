import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
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
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        
        int K = Integer.parseInt(br.readLine());
        Vertex[] graph = new Vertex[V + 1];
        for(int i = 0; i <= V; i++) {
        	graph[i] = new Vertex(i);
        }
        
        int u, v, w;
        for(int i = 0; i < E; i++) {
        	st = new StringTokenizer(br.readLine());
        	u = Integer.parseInt(st.nextToken());
        	v = Integer.parseInt(st.nextToken());
        	w = Integer.parseInt(st.nextToken());
        	
        	graph[u].add(v, w);
        }
        
        // 시작 정점은 가중치 0
        graph[K].weight = 0;
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.offer(graph[K]);
        Vertex curr;
        while(!queue.isEmpty()) {
        	curr = queue.poll();
        	// 가장 가중치가 작은 정점에서 가중치 갱신
        	curr.updateWeight(graph, queue);
        }
        
        // 가중치 출력
        for(int i = 1; i <= V; i++) {
        	if(graph[i].weight == INF) bw.write(INFINITE);
        	else bw.write(graph[i].weight + "\n");
        }
        
        bw.close();
        br.close();
    }
    
    // 간선
    @SuppressWarnings("serial")
	public static class Edge extends HashMap<Integer, Integer>{}
    
    // 정점별 간선 리스트
	@SuppressWarnings("serial")
	public static class Vertex implements Comparable<Vertex>{
		// 정점에 도달하는 weight 총합
		int u, weight;
		Edge edges;
		
		public Vertex(int u) {
			super();
			this.u = u;
			this.weight = INF;
			this.edges = new Edge();
		}
		
		public void add(int v, int w) {
			// 같은 정점으로 가는 간선이라면 짧은 간선만 저장
			if(edges.containsKey(v)) edges.put(v, Math.min(edges.get(v), w));
			else edges.put(v, w);
		}
		
		// 가중치 갱신
		public void updateWeight(Vertex[] graph, PriorityQueue<Vertex> queue) {
			Vertex curr;
			// 각 정점에 도달하는 가중치가 현재 간선 거쳐서 가는 가중치보다 클 경우 가중치 값 갱신
			for(Entry<Integer, Integer> edge: edges.entrySet()) {
				curr = graph[edge.getKey()];
				
				if(curr.weight > edge.getValue() + this.weight) {
					curr.weight = edge.getValue() + this.weight;
					queue.offer(curr);
				}
			}
		}

		@Override
		public int compareTo(Vertex obj) {
			return Integer.compare(this.weight, obj.weight);
		}
	}
}