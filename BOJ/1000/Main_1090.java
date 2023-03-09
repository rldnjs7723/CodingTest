import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1090번 체커
 * 문제 분류: 완전 탐색
 * @author Giwon
 */
public class Main_1090 {
	public static final int SELECT = 1;
	public static final int X = 0, Y = 1;
	public static int[] minCount;
	// 체커 수
	public static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 체커 수
		N = Integer.parseInt(br.readLine().trim());
		int x, y;
		List<Coord> checker = new ArrayList<>();
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			
			checker.add(new Coord(x, y));
		}

		// 각 개수별 최솟값
		minCount = new int[N + 1];
		// 무한으로 초기화
		Arrays.fill(minCount, Integer.MAX_VALUE);
		// 점 1개가 모이는 것은 0번 이동
		minCount[1] = 0;
		
		// NP로 양 끝점 선택
		int[] combination = new int[N];
		Arrays.fill(combination, N - 2, N, SELECT);;
		
		// 한 위치에 모일 점들 리스트
		List<Coord> group = new ArrayList<>();
		Coord A, B;
		int len;
		do {
			// 양 끝점 추출
			group.clear();
			for(int i = 0; i < N; i++) {
				if(combination[i] == SELECT) {
					group.add(checker.get(i));
				}
			}
			
			A = group.get(0);
			B = group.get(1);
			// 양 끝점 사이에 위치한 점들 카운트
			getMiddle(A, B, checker, group, X);
			// 한 점에 모일 체커 개수
			len = group.size();
			minCount[len] = Math.min(minCount[len], countMove(group));
			
			group.clear();
			group.add(A);
			group.add(B);
			// 양 끝점 사이에 위치한 점들 카운트
			getMiddle(A, B, checker, group, Y);
			// 한 점에 모일 체커 개수
			len = group.size();
			minCount[len] = Math.min(minCount[len], countMove(group));
		} while (nextPermutation(combination));
		
		StringBuilder sb = new StringBuilder();
		for(len = 1; len <= N; len++) {
			sb.append(minCount[len] + " ");
		}
		
		System.out.println(sb.toString());
		br.close();
	}
	
	// 두 양 끝점 사이에 위치한 점 그룹에 추가
	public static void getMiddle(Coord A, Coord B, List<Coord> checker, List<Coord> group, int type) {
		Coord curr;
		for(int i = 0; i < N; i++) {
			curr = checker.get(i);
			// 양 끝점이 선택된 경우 생략
			if(curr == A || curr == B) continue;
			
			// 현재 선택한 점이 양 끝점 사이에 위치하면 카운트
			if(isMiddle(curr, A, B, type)) {
				// 그룹에 추가
				group.add(curr);
			}
		}
	}
	
	// 현재 좌표가 양 끝점 사이에 있는 점인지 체크
	public static boolean isMiddle(Coord target, Coord A, Coord B, int type) {
		int minX = Math.min(A.x, B.x);
		int maxX = Math.max(A.x, B.x);
		int minY = Math.min(A.y, B.y);
		int maxY = Math.max(A.y, B.y);
		
		if(type == X) {
			return minX <= target.x && target.x <= maxX;
		} else {
			return minY <= target.y && target.y <= maxY;
		}
	}
	
	// 50C2로 두 개의 양 끝점 선택
	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;
		// i 탐색
		int i = n - 1;
		for(i = n - 1; i >= 1; i--) {
			if(arr[i - 1] < arr[i]) break;
		}
		if(i == 0) return false;
		
		// j 탐색
		int temp = arr[i - 1];
		int j = n - 1;
		for(j = n - 1; j >= 0; j--) {
			if(temp < arr[j]) break;
		}
		
		// 위치 교환
		arr[i - 1] = arr[j];
		arr[j] = temp;
		
		// i부터 정렬
		Arrays.sort(arr, i, n);
		return true;
	}
	
	// 현재 리스트 내 체커를 한 점에 모을 때 이동횟수의 최솟값 리턴
	public static int countMove(List<Coord> group) {
		MinMaxHeap xHeap = new MinMaxHeap();
		MinMaxHeap yHeap = new MinMaxHeap();

		xHeap.addList(group, X);
		yHeap.addList(group, Y);
		
		return xHeap.getCount() + yHeap.getCount();
	}
	
	public static class MinMaxHeap {
		Queue<Integer> minHeap, maxHeap;
		
		public MinMaxHeap() {
			this.minHeap = new PriorityQueue<>();
			this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
		}
		
		public void clear() {
			minHeap.clear();
			maxHeap.clear();
		}
		
		public void add(int val) {
			minHeap.offer(val);
			maxHeap.offer(val);
		}
		
		public void addList(List<Coord> list, int type) {
			if(type == X) {
				for(Coord coord: list) {
					add(coord.x);
				}
			} else {
				for(Coord coord: list) {
					add(coord.y);
				}
			}
		}
		
		public int getCount() {
			int N = minHeap.size();
			int maxIter = N / 2;
			
			int count = 0;
			for(int i = 0; i < maxIter; i++) {
				count += (maxHeap.poll() - minHeap.poll());
			}
			
			clear();
			return count;
		}
	}

	public static class Coord implements Comparable<Coord>{
		int x, y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		// X좌표 기준으로 오름차순 정렬
		@Override
		public int compareTo(Coord o) {
			return Integer.compare(this.x, o.x);
		}
		
		@Override
		public String toString() {
			return "[ x: " + x + " y: " + y + " ]";
		}
	}
}
