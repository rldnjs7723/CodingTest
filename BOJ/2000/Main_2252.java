import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 2252번 줄 세우기
 * 문제 분류: 위상 정렬
 * @author Giwon
 */
public class Main_2252 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 학생 수
		final int N = Integer.parseInt(st.nextToken());
		// 키 비교한 횟수
		final int M = Integer.parseInt(st.nextToken());
		
		// 키 비교 입력
		int u, v;
		Graph graph = new Graph(N);
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			
			graph.put(u, v);
		}
		
		// 위상 정렬 결과를 문자열로 변환
		StringBuilder sb = new StringBuilder();
		Queue<Integer> result = graph.topologicalSort();
		while(!result.isEmpty()) {
			sb.append(result.poll() + " ");
		}
		
		System.out.println(sb.toString());
		br.close();
	}

	public static class Student {
		// 학생 번호
		int num;
		// 진입 간선 개수
		int incoming;
		// 진출 간선
		Set<Integer> outgoing;
		
		public Student(int num) {
			this.num = num;
			this.incoming = 0;
			this.outgoing = new HashSet<>();
		}
	}
	
	public static class Graph {
		int N;
		Student[] V;
		
		public Graph(int N) {
			this.N = N;
			// 학생 번호는 1부터 N까지 존재
			V = new Student[N + 1];
			for(int i = 1; i <= N; i++) {
				V[i] = new Student(i);
			}
		}
		
		public void put(int u, int v) {
			V[u].outgoing.add(v);
			V[v].incoming++;
		}
		
		// 위상 정렬 수행
		public Queue<Integer> topologicalSort() {
			// 위상 정렬 결과
			Queue<Integer> result = new ArrayDeque<>();
			
			// 위상 정렬 시작점 탐색
			Queue<Integer> sortQueue = new ArrayDeque<>();
			for(int u = 1; u <= N; u++) {
				// 진입 간선 수가 0인 경우.
				if(V[u].incoming == 0) sortQueue.offer(u);
			}
			// DAG가 아니라면 위상 정렬은 불가능
			if(sortQueue.isEmpty()) return null;
			
			// 모든 정점을 포함할 때까지 위상 정렬 수행
			int u;
			while(!sortQueue.isEmpty()) {
				u = sortQueue.poll();
				result.offer(u);
				
				for(int v: V[u].outgoing) {
					// 현재 시작 정점을 진입 간선으로 가지는 경우 삭제
					V[v].incoming--;
					// 만약 방금 삭제한 간선이 마지막 진입 간선이면 정렬 큐에 포함
					if(V[v].incoming == 0) sortQueue.offer(v);
				}
			}
			
			return result;
		}
	}
}
