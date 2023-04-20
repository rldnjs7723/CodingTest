import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 10538번 빅 픽쳐
 * 문제 분류: 트라이, 해싱, KMP, 아호-코라식, 오토마타
 * @author Giwon
 */
public class Main_10538 {
	public static final int O = 0, X = 1, NOTMATCH = 0, ROOT = -1;
	public static int idx = 1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 그림 높이
		final int hp = Integer.parseInt(st.nextToken());
		// 그림 너비
		final int wp = Integer.parseInt(st.nextToken());
		// 걸작 높이
		final int hm = Integer.parseInt(st.nextToken());
		// 걸작 너비
		final int wm = Integer.parseInt(st.nextToken());
		
		// 그림으로 트라이 구성
		Aho_Corasick ahoCorasick = new Aho_Corasick(wp);
		int[] hashPicture = new int[hp];
		String input;
		for(int row = 0; row < hp; row++) {
			input = br.readLine().trim();
			// 그림 해시 배열로 변경
			hashPicture[row] = ahoCorasick.putString(input);
		}
		
//		System.out.println(Arrays.toString(hashPicture));
		
		// 아호-코라식 Fail 노드 탐색
		ahoCorasick.findFail();
		
		// KMP 테이블 구성
		int[] table = makeTable(hashPicture);
		
//		System.out.println(Arrays.toString(table));
		
		// 걸작 입력
		Node curr;
		int idx;
		int[][] hashMasterpiece = new int[hm][wm];
		for(int row = 0; row < hm; row++) {
			
			curr = ahoCorasick.root;
			input = br.readLine().trim();
			for(int col = 0; col < wm; col++) {
				idx = input.charAt(col) == 'o' ? O : X;
				curr = Aho_Corasick.nextNode(curr, idx);
				// 걸작 해시 배열로 변경
				hashMasterpiece[row][col] = curr.hash; 
			}
		}
		
//		for(int[] inner: hashMasterpiece) {
//			System.out.println(Arrays.toString(inner));
//		}
		
		// KMP로 열 탐색
		int count = 0;
		for(int col = 0; col < wm; col++) {
			// 매 열마다 새로 탐색
			int j = 0;
			for(int row = 0; row < hm; row++) {
				// 일치하는 부분까지 돌아가기
				while(j > 0 && hashMasterpiece[row][col] != hashPicture[j]) {
					j = table[j - 1];
				}
				
				if(hashMasterpiece[row][col] == hashPicture[j]) {
					j++;
					if (j == hp) {
						j = table[j - 1];
						count++;
					}
				}
			}
		}
		
		System.out.println(count);
		br.close();
	}
	
	// 접두사, 접미사 일치 길이 테이블 계산
	public static int[] makeTable(int[] pattern) {
		int size = pattern.length;
		int[] table = new int[size];

		int j = 0;
		for(int i = 1; i < size; i++) {
			while(j > 0 && pattern[i] != pattern[j]) {
				j = table[j - 1];
			}

			if(pattern[i] == pattern[j]) {
				table[i] = ++j;
			}
		}

		return table;
	}
	
	public static class Node {
		// 리프 노드인 경우 현재 문자열 해싱
		int hash;
		// 현재 위치 값
		int val;
		// 자식 노드
		Node child[];
		// 현재 노드에서 다음 노드가 없는 경우 가야하는 노드
		Node fail;
		// 디버깅용
//		String temp;
		
		public Node(int val) {
			this.hash = NOTMATCH;
			this.val = val;
			// 이진 트라이
			this.child = new Node[2];
			this.fail = null;
//			temp = "";
		}
		
		public Node connectChild(int idx) {
			if(child[idx] == null) child[idx] = new Node(idx);
//			child[idx].temp = temp + child[idx].val;
			
			return child[idx];
		}
		
		// 리프 노드인지 판별
		public boolean isLeaf() {
			return hash > NOTMATCH;
		}
		
		// 디버깅용
//		@Override
//		public String toString() {
//			return temp;
//		}
	}

	public static class Aho_Corasick {
		// 비교 문자열 길이 (그림의 너비)
		int width;
		Node root;
		
		public Aho_Corasick(int width) {
			this.width = width;
			this.root = new Node(ROOT);
		}
		
		// 찾을 그림의 행을 입력
		public int putString(String row) {
			Node curr = root;
			int val;
			for(int i = 0; i < width; i++) {
				val = row.charAt(i) == 'o' ? O : X;
				curr = curr.connectChild(val);
			}
			
			// 아직 등록되지 않은 문자열이라면 해싱
			if(curr.hash == NOTMATCH) {
				curr.hash = idx++;
			}
			
			// 해시 값 리턴
			return curr.hash;
		}
		
		// BFS를 통해 트라이 노드의 Fail 노드 구성
		public void findFail() {
			Queue<Node> bfsQueue = new ArrayDeque<>();
			// 루트 노드에서 fail 하면 루트 노드로 이동
			root.fail = root;
			bfsQueue.offer(root);
			
			Node curr = null, next, fail;
			while(!bfsQueue.isEmpty()) {
				curr = bfsQueue.poll();
				
				// 리프 노드인 경우 중단
				if(curr.isLeaf()) break;
				
				// 이진 트라이
				for(int idx = 0; idx < 2; idx++) {
					next = curr.child[idx];
					// Fail인 경우 생략
					if(next == null) continue;
					
					// 루트 노드의 자식이 실패한 경우 반드시 루트로 이동
					if(curr == root) next.fail = root;
					else {
						// Fail 노드 탐색
						fail = curr.fail;
						// 현재 노드에서 실패했을 때 이동하는 노드에서 다음 위치가 존재할 때까지 올라가기
						while(fail != root && fail.child[idx] == null) {
							fail = fail.fail;
						}
						if(fail.child[idx] != null) fail = fail.child[idx];
						
						next.fail = fail;
					}
					
					// 자식 노드 Queue에 넣기
					bfsQueue.offer(next);
				}
			}
			
		}
		
		// 현재 위치에서 Index와 맞는 다음 노드 반환
		public static Node nextNode(Node curr, int idx) {
			// KMP 처럼 다음 Index 맞는 부분 탐색
			while(curr.val != ROOT && curr.child[idx] == null) {
				curr = curr.fail;
			}
			if(curr.child[idx] != null) curr = curr.child[idx];
			
			return curr;
		}
	}
}
