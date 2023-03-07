import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 1005번 ACM Craft
 * 문제 분류: 위상 정렬 (DFS), 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_1005 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, K, W;
		Graph graph;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			
			// 건물의 개수
			N = Integer.parseInt(st.nextToken());
			// 건설 규칙 총 개수
			K = Integer.parseInt(st.nextToken());
			
			// 건설 시간 입력
			graph = new Graph(N);
			st = new StringTokenizer(br.readLine());
			for(int u = 1; u <= N; u++) {
				graph.setTime(u, Integer.parseInt(st.nextToken()));
			}
			
			// 건설 순서 입력
			int u, v;
			for(int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				u = Integer.parseInt(st.nextToken());
				v = Integer.parseInt(st.nextToken());
				
				graph.put(u, v);
			}
			
			// 위상 정렬 시작 정점
			W = Integer.parseInt(br.readLine().trim());
			
			// 위상 정렬 수행
			sb.setLength(0);
			sb.append(graph.topologicalSort(W));
			sb.append("\n");
			
			bw.write(sb.toString());
		}
		
		bw.close();
		br.close();
	}

	public static class Building {
		public static final int NULL = -1;
		
		// 건물 번호
		int num;
		// 건설시간
		int time;
		// 현재 건물까지의 총 건설 시간
		int total;
		// 진출 간선
		List<Integer> outgoing;
		
		public Building(int num) {
			this.num = num;
			this.time = 0;
			this.total = NULL;
			this.outgoing = new ArrayList<>();
		}
		
		// 재귀적으로 위상정렬 수행
		public int topologicalSort(Building[] V) {
			// 이전에 계산해둔 값이 있다면 재사용
			if(this.total != NULL) return total;
			
			int max = 0;
			for(int v: this.outgoing) {
				max = Math.max(max, V[v].topologicalSort(V));
			}
			
			this.total = max + time;
			return max + time;
		}
	}
	
	public static class Graph {
		// 건물 개수
		int N;
		Building[] V;
		
		public Graph(int N) {
			this.N = N;
			// 건물 번호는 1번부터 N번까지 존재
			this.V = new Building[N + 1];
			
			for(int i = 1; i <= N; i++) {
				V[i] = new Building(i);
			}
		}
		
		// 건설 시간 설정
		public void setTime(int u, int time) {
			V[u].time = time;
		}
		
		// 진출 간선 연결
		public void put(int u, int v) {
			// 위상 정렬을 역순으로 수행하기 위해 반대로 연결
			V[v].outgoing.add(u);
		}
		
		// 시작 정점 기준 DFS로 위상 정렬 수행
		public int topologicalSort(int W) {
			return V[W].topologicalSort(V);
		}
	}
}
