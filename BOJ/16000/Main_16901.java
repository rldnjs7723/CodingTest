import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 16901번 XOR MST
 * 문제 분류: 트라이, MST
 * @author Giwon
 */
public class Main_16901 {
	// 트라이 최대 깊이
	public static final int MAX_SIZE = 30;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 정점 개수
		final int N = Integer.parseInt(br.readLine().trim());
		
		// Trie에 모든 정점 정수 저장
		Trie trie = new Trie();
		int[] vertices = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			vertices[i] = Integer.parseInt(st.nextToken());
			trie.putInteger(vertices[i]);
		}
		
		// MST를 구성하는 정점
		Set<Integer> mst = new HashSet<>();
		long mstCost = 0L;
		// 시작점 초기화
		int start = vertices[0];
		mst.add(start);
		trie.removeInteger(start);
		
		// 우선 순위 큐로 가장 비용이 작은 간선 탐색
		Queue<Edge> minCostEdge = new PriorityQueue<>();
		Edge curr = trie.getMinimum(start);
		minCostEdge.offer(curr);
		// N - 1개의 간선을 연결할 때까지 다른 정점과 연결
		int edgeCount = 0;
		while(edgeCount < N - 1) {
			while(mst.contains(minCostEdge.peek().end)) {
				curr = minCostEdge.poll();
				minCostEdge.offer(trie.getMinimum(curr.start));
			}
			
			// 현재 연결되지 않은 정점으로 향하는 가장 비용이 적은 간선
			curr = minCostEdge.poll();
			// 정점 연결
			mstCost += curr.cost;
			mst.add(curr.end);
			// trie에서 정점 제거
			trie.removeInteger(curr.end);
			// 간선 개수 갱신
			edgeCount++;
			if(edgeCount == N - 1) break;
			// 현재 시작점에서의 최소 비용 간선 다시 탐색
			minCostEdge.offer(trie.getMinimum(curr.start));
			// 현재 종점에서의 최소 비용 간선 다시 탐색
			minCostEdge.offer(trie.getMinimum(curr.end));
		}
		
		System.out.println(mstCost);
		br.close();
	}
	
	public static class Edge implements Comparable<Edge>{
		int start, end;
		long cost;
		
		public Edge(int start, int end, long cost) {
			this.start = start;
			this.end = end;
			this.cost = cost;
		}

		// 비용이 작은 순서대로 정렬
		@Override
		public int compareTo(Edge o) {
			return Long.compare(this.cost, o.cost);
		}

		@Override
		public String toString() {
			return "Edge [start=" + start + ", end=" + end + ", cost=" + cost + "]";
		}
	}
	
	public static class Node {
		// 현재 노드까지 도달한 문자열의 개수
		int count;
		Node[] child;
		
		public Node() {
			this.count = 0;
			this.child = new Node[2];
		}
		
		public Node getChild(int i) {
			if(child[i] == null) child[i] = new Node();
			
			return child[i];
		}
	}
	
	public static class Trie {
		// MST 비용
		long cost;
		// 트라이 루트 노드
		Node root;
		
		public Trie() {
			this.root = new Node();
			this.cost = 0L;
		}
		
		public static String toBinaryString(int val) {
			String binary = Integer.toBinaryString(val);
			int len = binary.length();
			
			// 모든 수를 길이가 30인 이진수로 표현
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < MAX_SIZE - len; i++) {
				sb.append(0);
			}
			for(int i = 0; i < len; i++) {
				sb.append(binary.charAt(i) - '0');
			}
			
			return sb.toString();
		}
		
		public static int toNumber(String binary) {
			int result = 0;
			for(int i = 0; i < MAX_SIZE; i++) {
				if(binary.charAt(MAX_SIZE - 1 - i) == '1') result += (1 << i);
			}
			
			return result;
		}
		
		// 입력 받은 정수 Trie에 추가
		public void putInteger(int val) {
			String binary = toBinaryString(val);

			Node curr = root;
			for(int i = 0; i < MAX_SIZE; i++) {
				curr = curr.getChild(binary.charAt(i) - '0');
				// 개수 추가
				curr.count++;
			}
		}
		
		// 입력 받은 정수 Trie에서 제거
		public void removeInteger(int val) {
			String binary = toBinaryString(val);
			
			Node curr = root;
			for(int i = 0; i < MAX_SIZE; i++) {
				curr = curr.getChild(binary.charAt(i) - '0');
				// 개수 감소
				curr.count--;
			}
		}
		
		// 현재 입력 받은 정수와 연결하는 간선 가중치 최솟값 계산
		public Edge getMinimum(int val) {
			String binary = toBinaryString(val);
			
			StringBuilder sb = new StringBuilder();
			long xor = 0L;
			int bin;
			Node curr = root;
			for(int i = MAX_SIZE - 1; i >= 0; i--) {
				// 이진수로 표현한 수 중 현재 Index에서의 값
				bin = binary.charAt(MAX_SIZE - 1 - i) - '0';
				// 값이 다른 경우 XOR 값에 추가
				if(curr.child[bin] == null || curr.child[bin].count == 0) {
					xor += (1 << i);
					sb.append(bin == 0 ? 1 : 0);
					curr = curr.child[bin == 0 ? 1 : 0];
				} else {
					sb.append(bin);
					curr = curr.child[bin];
				}
			}
			
			return new Edge(val, toNumber(sb.toString()), xor);
		}
	}
	
}