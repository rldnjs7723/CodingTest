import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 2458번 키 순서
 * 문제 분류: Floyd-Warshall 알고리즘
 * @author Giwon
 */
public class Main_2458 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		// 학생 수
		final int N = Integer.parseInt(st.nextToken());
		// 키 비교 수
		final int M = Integer.parseInt(st.nextToken());
		
		// 키 비교 간선 입력
		Graph graph = new Graph(N);
		int a, b;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			
			graph.addEdge(a - 1, b - 1);
		}
		
		// 플로이드-워셜로 도달할 수 있는지 체크
		graph.floydWarshall();
		System.out.println(graph.getCount());
		
		br.close();
	}
	
	public static class Graph {
		int N;
		int[][] reachable;
		
		public Graph(int N) {
			this.N = N;
			this.reachable = new int[N][N];
		}
		
		// 간선 추가
		public void addEdge(int u, int v) {
			// DAG
			reachable[u][v] = 1;
		}
		
		// 플로이드-워셜 알고리즘
		public void floydWarshall() {
			for(int k = 0; k < N; k++) {
				for(int i = 0; i < N; i++) {
					for(int j = 0; j < N; j++) {
						// i에서 k를 거쳐 j로 갈 수 있다면 갱신
						if(reachable[i][k] == 1 && reachable[k][j] == 1) {
							reachable[i][j] = 1;
						}
					}
				}
			}
		}
		
		// 키를 확실하게 알 수 있는 경우 카운트
		public int getCount() {
			int count = 0, sum;
			for(int target = 0; target < N; target++) {
				sum = 0;
				for(int i = 0; i < N; i++) {
					// 다른 점에서 현재 점으로 도달할 수 있는 경우
					sum += reachable[i][target];
					// 현재 점에서 다른 점으로 도달할 수 있는 경우
					sum += reachable[target][i];
				}
				
				if(sum == N - 1) count++;
			}
			
			return count;
		}
	}
}
