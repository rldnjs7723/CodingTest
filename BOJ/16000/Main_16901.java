import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 16901번 XOR MST
 * 문제 분류: 트라이, MST, 분할 정복
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
		
		System.out.println(trie.getMSTCost());
		br.close();
	}
	
	public static class Node {
		// 현재 노드의 이진수 값
		int val;
		// 이진수로 나타낸 경우 현재 노드의 위치
		int binaryIdx;
		// 현재 노드까지 도달한 문자열의 개수
		int count;
		// 자식 노드
		Node[] child;
		
		public Node(int binaryIdx, int val) {
			this.binaryIdx = binaryIdx;
			this.val = val;
			this.count = 0;
			this.child = new Node[2];
		}
		
		public Node getChild(int i) {
			if(child[i] == null) child[i] = new Node(binaryIdx - 1, i);
			
			return child[i];
		}
		
		// 현재 노드의 왼쪽 노드와 오른쪽 노드를 연결한 비용을 리턴
		public long connect() {
			long result = 0L;
			
			// 리프 노드는 연결 비용 0
			if(binaryIdx == 0) return 0L;
			
			// 왼쪽 자식 노드가 없다면 오른쪽 자식 노드 비용 리턴
			if(child[0] == null) return child[1].connect();
			// 오른쪽 자식 노드가 없다면 왼쪽 자식 노드 비용 리턴
			if(child[1] == null) return child[0].connect();
			
			// 왼쪽 노드 연결 비용 계산
			result += child[0].connect();
			// 오른쪽 노드 연결 비용 계산
			result += child[1].connect();
			
			// 왼쪽 노드와 오른쪽 노드를 각각 루트로 하는 트라이 비교하여 xor 최솟값 계산
			long minCost = 1 << (binaryIdx - 1);
			minCost += compareTrie(child[0], child[1]);
			
			return result + minCost;
		}
		
		// 왼쪽 노드와 오른쪽 노드의 Trie 비교. xor 값 리턴
		public static long compareTrie(Node left, Node right) {
			int binaryIdx = left.binaryIdx;
			if(binaryIdx == 0) return 0;
			
			// 왼쪽 노드와 오른쪽 노드의 자식 노드 수 카운트
			int leftCount = 0;
			int rightCount = 0;
			
			if(left.child[0] != null) leftCount++;
			if(left.child[1] != null) leftCount++;
			if(right.child[0] != null) rightCount++;
			if(right.child[1] != null) rightCount++;
			
			// 둘 다 자식 노드가 1개인 경우
			int leftIdx, rightIdx;
			if(leftCount == 1 && rightCount == 1) {
				leftIdx = left.child[0] == null ? 1 : 0;
				rightIdx = right.child[0] == null ? 1 : 0;
				
				if(leftIdx == rightIdx) {
					// 값이 같다면 그대로 리턴
					return compareTrie(left.child[leftIdx], right.child[rightIdx]);
				} else {
					// 값이 다르다면 XOR 값 추가
					return (1L << (binaryIdx - 1)) + compareTrie(left.child[leftIdx], right.child[rightIdx]);
				}
			}
			
			// 왼쪽 자식 노드만 1개인 경우
			if(leftCount == 1) {
				leftIdx = left.child[0] == null ? 1 : 0;
				return compareTrie(left.child[leftIdx], right.child[leftIdx]);
			}
			
			// 오른쪽 자식 노드만 1개인 경우
			if(rightCount == 1) {
				rightIdx = right.child[0] == null ? 1 : 0;
				return compareTrie(left.child[rightIdx], right.child[rightIdx]);
			}
			
			// 둘 다 자식 노드가 있는 경우
			// 양쪽 비교 후 최솟값 리턴
			return Math.min(compareTrie(left.child[0], right.child[0]), compareTrie(left.child[1], right.child[1]));
		}
		
	}
	
	public static class Trie {
		// 트라이 루트 노드
		Node root;
		
		public Trie() {
			this.root = new Node(MAX_SIZE, -1);
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
		
		// MST 구성 비용 계산
		public long getMSTCost() {
			return root.connect();
		}
	}
	
}