import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * SWEA 5643번 키 순서
 * 문제 분류: 위상 정렬 / Floyd-Warshall 알고리즘
 * @author Giwon
 */

public class Solution_5643 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, M;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 학생 수
			N = Integer.parseInt(br.readLine().trim());
			// 키 비교 수
			M = Integer.parseInt(br.readLine().trim());
			
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
			System.out.println("#" + test_case + " " + graph.getCount());
		}
		
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


// 위상 정렬 풀이
//public class Solution_5643 {
//
//	public static void main(String[] args) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		StringTokenizer st;
//		
//		final int T = Integer.parseInt(br.readLine().trim());
//		int N, M;
//		for(int test_case = 1; test_case <= T; test_case++) {
//			// 학생 수
//			N = Integer.parseInt(br.readLine().trim());
//			// 키 비교 수
//			M = Integer.parseInt(br.readLine().trim());
//			
//			// 키 비교 간선 입력
//			Graph graph = new Graph(N);
//			int a, b;
//			for(int i = 0; i < M; i++) {
//				st = new StringTokenizer(br.readLine());
//				a = Integer.parseInt(st.nextToken());
//				b = Integer.parseInt(st.nextToken());
//				
//				graph.addEdge(a, b);
//			}
//			
//			// 위상 정렬 수행
//			graph.topologicalSort();
//			// 위상 정렬 역방향으로 키가 작은 학생 수 전파
//			graph.reversePropagation();
////			graph.print();
//			
//			System.out.println("#" + test_case + " " + graph.getCount());
//		}
//		
//		br.close();
//	}
//
//	public static class Height {
//		// 학생 번호
//		int idx;
//		// 자신보다 키가 작은 사람 수
//		int incomingEdgeCount;
//		// 자신보다 키가 작은 사람을 연결한 간선
//		List<Integer> incomingEdge;
//		// 자신보다 키가 큰 사람을 연결한 간선
//		List<Integer> outgoingEdge;
//		// 자신보다 키가 작은 사람들의 번호 저장 집합
//		Set<Integer> incomingSet;
//		// 자신보다 키가 큰 사람들의 번호 저장 집합
//		Set<Integer> outgoingSet;
//		
//		public Height(int idx) {
//			this.idx = idx;
//			this.incomingEdgeCount = 0;
//			this.incomingEdge = new ArrayList<>();
//			this.outgoingEdge = new ArrayList<>();
//			this.incomingSet = new HashSet<>();
//			this.outgoingSet = new HashSet<>();
//		}
//
//		@Override
//		public String toString() {
//			return "Height [count=" + (incomingSet.size() + outgoingSet.size()) + "]";
//		}
//	}
//	
//	public static class Graph {
//		public static final int NORMAL = 0, REVERSE = 1;
//		
//		int N;
//		Height[] vertex;
//		// 위상 정렬한 결과
//		List<Height> topologicalOrder;
//		
//		public Graph(int N) {
//			this.N = N;
//			this.vertex = new Height[N + 1];
//			for(int i = 1; i <= N; i++) {
//				this.vertex[i] = new Height(i);
//			}
//			this.topologicalOrder = new ArrayList<>();
//		}
//		
//		// 간선 추가
//		public void addEdge(int u, int v) {
//			vertex[u].outgoingEdge.add(v);
//			vertex[u].outgoingSet.add(v);
//			vertex[v].incomingEdgeCount++;
//			// 위상 정렬 역방향으로 전파하기 위한 간선 추가
//			vertex[v].incomingEdge.add(u);
//			vertex[v].incomingSet.add(u);
//		}
//		
//		// 위상 정렬 수행
//		public void topologicalSort() {
//			// 위상 정렬 수행할 노드 저장
//			Queue<Height> sortQueue = new ArrayDeque<>();
//			// 진입 간선이 없는 모든 노드 입력
//			for(int i = 1; i <= N; i++) {
//				if(vertex[i].incomingEdgeCount == 0) sortQueue.offer(vertex[i]);
//			}
//			
//			Height curr, next;
//			while(!sortQueue.isEmpty()) {
//				curr = sortQueue.poll();
//				
//				for(int idx: curr.outgoingEdge) {
//					next = vertex[idx];
//					next.incomingEdgeCount--;
//					// 자신보다 키가 작은 학생 번호 기록
//					for(int smaller: curr.incomingSet) {
//						next.incomingSet.add(smaller);
//					}
//					
//					// 진입 간선이 더 이상 없다면 다음 위상 정렬에 사용
//					if(next.incomingEdgeCount == 0) sortQueue.offer(next);
//				}
//				
//				topologicalOrder.add(curr);
//			}
//		}
//		
//		// 위상 정렬 역방향으로 키가 큰 학생 번호 전파
//		public void reversePropagation() {
//			Height curr, next;
//			for(int i = topologicalOrder.size() - 1; i >= 1; i--) {
//				curr = topologicalOrder.get(i);
//				
//				for(int idx: curr.incomingEdge) {
//					next = vertex[idx];
//					// 자신보다 키가 큰 학생 번호 기록
//					for(int taller: curr.outgoingSet) {
//						next.outgoingSet.add(taller);
//					}
//				}
//			}
//		}
// 		
//		// 키가 몇 번째인지 알 수 있는 학생 수 카운트
//		public int getCount() {
//			int count = 0;
//			for(int i = 1; i <= N; i++) {
//				// 키 비교를 한 학생 수 = 자신보다 키가 큰 사람 수 + 키가 작은 사람 수
//				if(vertex[i].incomingSet.size() + vertex[i].outgoingSet.size() == N - 1) count++;
//			}
//			
//			return count;
//		}
//		
//		public void print() {
//			System.out.println(Arrays.toString(vertex));
//		}
//	}
//}