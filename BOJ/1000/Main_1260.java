import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

/**
 * 백준 1260번 DFS와 BFS
 * 문제 분류: 그래프 탐색, DFS, BFS
 * @author Giwon
 */
public class Main_1260 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		String[] inputs = input.split(" ");
		// 정점의 개수
		int N = Integer.parseInt(inputs[0]);
		// 간선의 개수
		int M = Integer.parseInt(inputs[1]);
		// 시작 정점 번호
		int V = Integer.parseInt(inputs[2]);
		
		// 각 정점에서의 간선의 수를 저장한 배열
		int[] size = new int[N + 1];
		// 간선 입력
		int[][] graph = new int[N + 1][N + 1];
		for(int i = 0; i < M; i++) {
			input = br.readLine();
			inputs = input.split(" ");
			// 무향 그래프
			graph[Integer.parseInt(inputs[0])][size[Integer.parseInt(inputs[0])]++] = Integer.parseInt(inputs[1]);
			graph[Integer.parseInt(inputs[1])][size[Integer.parseInt(inputs[1])]++] = Integer.parseInt(inputs[0]);
		}
		
		for(int i = 0; i <= N; i++) {
			if(size[i] > 0) {
				// 방문할 수 있는 정점이 여러 개인 경우 정점 번호가 작은 것부터 방문
				Arrays.sort(graph[i]);
			}
		}
		
		// DFS로 탐색한 후 출력
		dfs(V, N, graph, size);
		// BFS로 탐색한 후 출력
		bfs(V, N, graph, size);
		
		br.close();
	}
	
	// DFS로 탐색
	public static void dfs(int V, int N, int[][] graph, int[] size) {
		StringBuilder builder = new StringBuilder();
		// 각 정점 방문 체크용 배열
		boolean[] visited = new boolean[N + 1];
		
		visited[V] = true;
		builder.append(V);
		Stack<Integer> stack = new Stack<>();
		// 정점 번호가 작은 것을 먼저 탐색해야 하므로 Stack에 역순으로 넣기
		for(int i = 0; i < size[V]; i++) {
			stack.push(graph[V][N - i]);
		}
		
		int curr, next;
		while(!stack.isEmpty()) {
			curr = stack.pop();
			if(visited[curr]) continue;
			
			visited[curr] = true;
			builder.append(" " + curr);
			
			// 각 간선에서 목표 정점을 방문하지 않은 경우에만 Stack에 넣기
			for(int i = 0; i < size[curr]; i++) {
				next = graph[curr][N - i];
				if(!visited[next]) stack.push(next);
			}
		}
		System.out.println(builder.toString());
	}
	
	public static void bfs(int V, int N, int[][] graph, int[] size) {
		StringBuilder builder = new StringBuilder();
		// 각 정점 방문 체크용 배열
		boolean[] visited = new boolean[N + 1];
		
		visited[V] = true;
		builder.append(V);
		Queue<Integer> queue = new ArrayDeque<Integer>();
		// 정점 번호가 작은 것을 먼저 탐색해야 하므로 정렬된 순서대로 Queue에 넣기
		for(int i = N - size[V] + 1; i <= N; i++) {
			queue.offer(graph[V][i]);
		}
		
		int curr, next;
		while(!queue.isEmpty()) {
			curr = queue.poll();
			if(visited[curr]) continue;
			
			visited[curr] = true;
			builder.append(" " + curr);
			
			// 각 간선에서 목표 정점을 방문하지 않은 경우에만 Stack에 넣기
			for(int i = N - size[curr] + 1; i <= N; i++) {
				next = graph[curr][i];
				if(!visited[next]) queue.offer(next);
			}
		}
		System.out.println(builder.toString());
	}
}