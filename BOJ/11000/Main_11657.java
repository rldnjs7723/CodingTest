import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 11657번 타임머신
 * 문제 분류: Bellman-Ford
 * @author Giwon
 */
public class Main_11657 {
	public static final long INF = Long.MAX_VALUE;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 정점의 개수
        int N = Integer.parseInt(st.nextToken());
        // 간선의 개수
        int M = Integer.parseInt(st.nextToken());
        
        Graph graph = new Graph(N);
        // 시작 정점 / 도착 정점 / 비용
        int A, B, C;
        for(int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	A = Integer.parseInt(st.nextToken());
        	B = Integer.parseInt(st.nextToken());
        	C = Integer.parseInt(st.nextToken());
        	
        	graph.add(A, B, C);
        }
        
        // 벨만 포드 알고리즘으로 비용 계산
        if(graph.BellmanFord()) System.out.println(-1);
        else graph.printCost();
        
        br.close();
    }
    
    // N <= 500 이므로 인접 행렬 사용
    public static class Graph {
    	public static final int COST = 0;
    	public static final int START = 1;
    	
    	int N;
    	long[][] edges;
    	
    	public Graph(int N) {
    		this.N = N;
    		this.edges = new long[N + 1][N + 1];
    		
    		// 모든 비용 INF로 초기화
    		for(long[] arr: edges) {
    			Arrays.fill(arr, INF);
    		}
		}
    	
    	// 중복 간선 고려하여 최소 비용 간선만 유지
    	public void add(int u, int v, int w) {
    		edges[u][v] = Math.min(edges[u][v], w);
    	}
    	
    	// 벨만 포드 알고리즘으로 비용 계산
    	public boolean BellmanFord() {
    		// 시작 정점 비용 0
    		edges[START][COST] = 0;
    		
    		int count = 0;
    		for(int i = 0; i < N; i++) {
    			count = 0;
    			// 각 정점에서 비용 갱신
    			for(int u = 1; u <= N; u++) {
    				for(int v = 1; v <= N; v++) {
    					// 아직 도달하지 못한 정점이라면 갱신하지 않음
    					if(edges[u][COST] == INF) continue;
    					// 간선이 존재하지 않으면 갱신하지 않음
    					if(edges[u][v] == INF) continue;
    					
    					// 현재 정점 비용 + 간선 비용이 더 작으면 갱신
    					if(edges[v][COST] > edges[u][COST] + edges[u][v]) {
    						edges[v][COST] = edges[u][COST] + edges[u][v];
    						count++;
    					}
    				}
    			}
    			
    			// 갱신된 정점이 없으면 중단
    			if(count == 0) break;
    		}
    		
    		// 한 번 더 갱신했을 때 갱신되는 정점이 있다면 음의 사이클 존재
			for(int u = 1; u <= N; u++) {
				for(int v = 1; v <= N; v++) {
					// 아직 도달하지 못한 정점이라면 갱신하지 않음
					if(edges[u][COST] == INF) continue;
					// 간선이 존재하지 않으면 갱신하지 않음
					if(edges[u][v] == INF) continue;
					
					if(edges[v][COST] > edges[u][COST] + edges[u][v]) {
						return true;
					}
				}
			}
			
			return false;
    	}
    	
    	// 각 정점에 도달하는 비용 갱신
    	public void printCost() {
    		StringBuilder sb = new StringBuilder();
    		
    		for(int i = 2; i <= N; i++) {
    			sb.append(edges[i][COST] == INF ? -1 : edges[i][COST]);
    			sb.append("\n");
    		}
    		
    		System.out.println(sb.toString());
    	}
    }
    
}