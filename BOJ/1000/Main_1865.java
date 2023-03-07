import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 1865번 웜홀
 * 문제 분류: Bellman-Ford
 * @author Giwon
 */
public class Main_1865 {
	public static final int INF = Integer.MAX_VALUE / 2 - 1;
	public static final String YES = "YES\n", NO = "NO\n";
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        StringTokenizer st;
        int TC = Integer.parseInt(br.readLine());
        int N, M, W, S, E, T;
        Graph graph;
        for(int test_case = 0; test_case < TC; test_case++) {
        	st = new StringTokenizer(br.readLine());
        	N = Integer.parseInt(st.nextToken());
        	M = Integer.parseInt(st.nextToken());
        	W = Integer.parseInt(st.nextToken());
        	
        	graph = new Graph(N);
        	// 도로 정보 입력
        	for(int road = 0; road < M; road++) {
        		st = new StringTokenizer(br.readLine());
            	S = Integer.parseInt(st.nextToken());
            	E = Integer.parseInt(st.nextToken());
            	T = Integer.parseInt(st.nextToken());
            	
            	// 도로는 무향
            	graph.put(S, E, T);
            	graph.put(E, S, T);
        	}
        	
        	// 웜홀 정보 입력
        	for(int hole = 0; hole < W; hole++) {
        		st = new StringTokenizer(br.readLine());
            	S = Integer.parseInt(st.nextToken());
            	E = Integer.parseInt(st.nextToken());
            	T = Integer.parseInt(st.nextToken());
            	
            	// 웜홀은 음의 가중치
            	graph.put(S, E, -T);
        	}
        	
        	bw.write(graph.BellmanFord() ? YES : NO);
        }
        
        bw.close();
        br.close();
    }
    
    // N은 500이 최대이므로 인접행렬로 구현
    public static class Graph {
    	public static final int COST = 0;
    	// N: 정점 개수, COUNT: 현재 정점의 간선 개수를 저장한 Index
    	int N, COUNT;
    	int[][] edges;
    	
    	public Graph(int N) {
    		this.N = N;
    		this.edges = new int[N + 2][N + 2];
    		this.COUNT = N + 1;
    		
    		for(int[] edge: edges) {
    			Arrays.fill(edge, INF);
    			edge[COUNT] = 0;
    		}
		}
    	
    	// 간선이 존재하는 정점 탐색
    	public int hasEdge() {
    		for(int i = 1; i <= N; i++) {
    			if(edges[i][COUNT] > 0) return i;
    		}
    		return -1;
    	}
    	
    	// 각 정점 비용 무한으로 그래프 초기화
    	public void initialize() {
    		for(int[] edge: edges) {
    			edge[COST] = INF;
    		}
    	}
    	
    	// 간선 추가
    	public void put(int u, int v, int w) {
    		// 간선의 개수 체크
    		if(edges[u][v] == INF) edges[u][COUNT]++;
    		// 두 정점에 여러 개의 간선이 있는 경우 가장 작은 값만 기록
    		edges[u][v] = Math.min(edges[u][v], w);
    	}
    	
    	// 시작 정점으로 돌아오는 음의 사이클이 존재하는지 확인
    	public boolean BellmanFord() {
    		// 간선이 존재하는 정점을 시작 정점으로 설정
    		int start = hasEdge();
    		
    		// 각 정점 비용 초기화
    		initialize();
    		// 시작 정점 비용 0
    		if(start != -1) edges[start][COST] = 0;
    		
    		int changed = 0;
    		for(int i = 0; i < N; i++) {
    			// 각 정점별로 비용 갱신 후 갱신된 정점의 개수 반환
    			changed = updateCost();
    			// 갱신된 정점이 없다면 갱신 완료
    			if(changed == 0) break;
    		}
    		
    		// 전체 갱신 이후에도 계속 갱신이 가능하면 음의 사이클이 존재
    		changed = updateCost();
    		if(changed != 0) return true;
    		else return false;
    	}
    	
    	// 각 정점별로 비용 갱신 후 갱신된 정점의 개수 반환
    	public int updateCost() {
    		int changed = 0;
    		int[] edge, curr;
    		for(int u = 1; u <= N; u++) {
				edge = edges[u];
				
				// 간선이 없다면 다음 정점으로 이동
				if(edge[COUNT] == 0) continue;
				// 각 정점에서 다른 정점으로 가는 비용 갱신
				for(int v = 1; v <= N; v++) {
					curr = edges[v];
					if(curr[COST] > edges[u][v] + edge[COST]) {
						curr[COST] = edges[u][v] + edge[COST];
						changed++;
					}
				}
			}
    		
    		return changed;
    	}
    }
    
}