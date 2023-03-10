import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

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
		Set<Integer> coordX = new TreeSet<>();
		Set<Integer> coordY = new TreeSet<>();
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			
			checker.add(new Coord(x, y));
			coordX.add(x);
			coordY.add(y);
		}

		// 각 개수별 최솟값
		minCount = new int[N + 1];
		// 무한으로 초기화
		Arrays.fill(minCount, Integer.MAX_VALUE);
		// 점 1개가 모이는 것은 0번 이동
		minCount[1] = 0;
		
		// 한 곳에 모일 위치 좌표 체크
		// 1. 각 체커의 좌표 x, y 값 기록
		Integer[] xArr = coordX.toArray(new Integer[coordX.size()]);
		Integer[] yArr = coordY.toArray(new Integer[coordY.size()]);
		// 2. 각 체커 x, y 값의 중점 추가
		for(int i = 0; i < xArr.length - 2; i++) {
			coordX.add((xArr[i] + xArr[i + 1]) / 2);
		}
		for(int i = 0; i < yArr.length - 2; i++) {
			coordY.add((yArr[i] + yArr[i + 1]) / 2);
		}
		
		xArr = coordX.toArray(new Integer[coordX.size()]);
		yArr = coordY.toArray(new Integer[coordY.size()]);
		
		// 모든 x, y 쌍에 대해서 탐색 수행
		Queue<Coord> closest = new PriorityQueue<>();
		int count;
		// 선택된 모든 정점에 대하여 가장 가까운 점을 하나씩 포함하며 이동 횟수 계산
		for(int xi = 0; xi < xArr.length; xi++) {
			for(int yi = 0; yi < yArr.length; yi++) {
				count = 0;
				
				// 모든 체커를 현재 좌표로 이동시킬 때의 이동 횟수 계산
				setDistance(xArr[xi], yArr[yi], checker);
				// 이동 횟수에 따라 정렬
				closest.clear();
				for(int i = 0; i < N; i++) {
					closest.offer(checker.get(i));
				}
				
				// K개의 체커 모으기
				for(int K = 1; K <= N; K++) {
					count += closest.poll().distance;
					minCount[K] = Math.min(minCount[K], count);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int K = 1; K <= N; K++) {
			sb.append(minCount[K] + " ");
		}
		
		System.out.println(sb.toString());
		br.close();
	}
	
	// 모든 체커를 하나의 좌표로 이동시킬 때의 이동 횟수 계산
	public static void setDistance(int x, int y, List<Coord> checker) {
		for(Coord coord: checker) {
			coord.setDistance(x, y);
		}
	}

	public static class Coord implements Comparable<Coord>{
		int x, y;
		int distance;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
			this.distance = 0;
		}
		
		//멘해튼 거리 계산
		public void setDistance(Coord target) {
			this.distance = Math.abs(target.x - this.x) + Math.abs(target.y - this.y);
		}
		public void setDistance(int x, int y) {
			this.distance = Math.abs(x - this.x) + Math.abs(y - this.y);
		}
		
		public static Coord getMiddle(Coord A, Coord B) {
			return new Coord((A.x + B.x) / 2, (A.y + B.y) / 2);
		}
		
		// 거리 기준 오름차순 정렬
		@Override
		public int compareTo(Coord o) {
			return Integer.compare(this.distance, o.distance);
		}
		
		@Override
		public String toString() {
			return "[ x: " + x + " y: " + y + " ]";
		}
	}
}
