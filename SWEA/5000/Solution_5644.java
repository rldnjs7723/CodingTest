import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * SWEA 5644번 무선 충전
 * 문제 분류: 구현, 자료 구조
 * @author Giwon
 */
public class Solution_5644 {
	public static final int STAY = 0, UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
	public static final int[] dRow = {0, -1, 0, 1, 0};
	public static final int[] dCol = {0, 0, 1, 0, -1};
	// 지도 크기
	public static final int N = 10;
	// A 시작점
	public static final int startA = 1;
	// B 시작점
	public static final int startB = 10;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine());
		int M, A;
		int[] moveA, moveB;
		int rowA, colA, rowB, colB;
		int row, col, coverage, performance;
		BatteryCharger[] BC; 
		// 성능 상위 2개의 배터리를 저장할 배열
		BatteryCharger[] bcArrA, bcArrB;
		// 접근 가능한 배터리 정렬하기 위해 TreeSet 활용
		Set<BatteryCharger> batteryA = new TreeSet<>(Collections.reverseOrder());
		Set<BatteryCharger> batteryB = new TreeSet<>(Collections.reverseOrder());
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			
			// 총 이동 시간
			M = Integer.parseInt(st.nextToken());
			// BC의 개수
			A = Integer.parseInt(st.nextToken());
			
			// A 이동 정보 입력
			moveA = new int[M + 1];
			st = new StringTokenizer(br.readLine());
			for(int i = 1; i <= M; i++) {
				moveA[i] = Integer.parseInt(st.nextToken());
			}
			
			// B 이동 정보 입력
			moveB = new int[M + 1];
			st = new StringTokenizer(br.readLine());
			for(int i = 1; i <= M; i++) {
				moveB[i] = Integer.parseInt(st.nextToken());
			}
			
			// BC 정보 입력
			BC = new BatteryCharger[A];
			for(int i = 0; i < A; i++) {
				st = new StringTokenizer(br.readLine());
				// X
				col = Integer.parseInt(st.nextToken());
				// Y
				row = Integer.parseInt(st.nextToken());
				// 범위
				coverage = Integer.parseInt(st.nextToken());
				// 충전량
				performance = Integer.parseInt(st.nextToken());
				
				BC[i] = new BatteryCharger(row, col, coverage, performance);
			}
			
			// 이동하면서 체크
			int result = 0;
			rowA = startA; colA = startA;
			rowB = startB; colB = startB;
			for(int time = 0; time <= M; time++) {
				rowA += dRow[moveA[time]];
				colA += dCol[moveA[time]];
				rowB += dRow[moveB[time]];
				colB += dCol[moveB[time]];
				
				// A의 위치에서 접근 가능한 배터리 탐색
				batteryA.clear();
				for(BatteryCharger bc: BC) {
					if(bc.accessible(rowA, colA)) batteryA.add(bc);
				}
				bcArrA = toArray(batteryA);
				// B의 위치에서 접근 가능한 배터리 탐색
				batteryB.clear();
				for(BatteryCharger bc: BC) {
					if(bc.accessible(rowB, colB)) batteryB.add(bc);
				}
				bcArrB = toArray(batteryB);
				
				// A, B가 모두 접속할 배터리가 없는 경우
				if(batteryA.size() == 0 && batteryB.size() == 0) continue;
				
				// A만 접속할 배터리가 없는 경우
				if(batteryA.size() == 0) {
					result += bcArrB[0].performance;
					continue;
				}
				// B만 접속할 배터리가 없는 경우
				if(batteryB.size() == 0) {
					result += bcArrA[0].performance;
					continue;
				}
				
				// A, B에서 접속할 배터리가 적어도 하나는 있는 경우
				// A, B가 겹치지 않는 경우
				if(bcArrA[0] != bcArrB[0]) {
					result += bcArrA[0].performance;
					result += bcArrB[0].performance;
					continue;
				}
				// A, B가 겹치는 경우
				// 둘 다 접속할 배터리가 하나만 있는 경우
				if(batteryA.size() == 1 && batteryB.size() == 1) {
					// 하나의 배터리만 사용
					result += bcArrA[0].performance;
					continue;
				}
				// A만 접속할 배터리가 하나만 있는 경우
				if(batteryA.size() == 1) {
					result += bcArrA[0].performance;
					result += bcArrB[1].performance;
					continue;
				}
				// B만 접속할 배터리가 하나만 있는 경우
				if(batteryB.size() == 1) {
					result += bcArrB[0].performance;
					result += bcArrA[1].performance;
					continue;
				}
				// 둘 다 접속할 배터리가 2개 이상인 경우
				result += bcArrA[0].performance;
				result += Math.max(bcArrA[1].performance, bcArrB[1].performance);
			}
			
			System.out.println("#" + test_case + " " + result);
		}
		
		br.close();
	}
	
	// Set에서 상위 두 개의 BatteryCharget를 추출
	public static BatteryCharger[] toArray(Set<BatteryCharger> set) {
		BatteryCharger[] result = new BatteryCharger[2];
		int size = 0;
		for(BatteryCharger bc: set) {
			result[size++] = bc;
			if(size == 2) break;
		}
		return result;
	}
	
	public static class BatteryCharger implements Comparable<BatteryCharger>{
		int row, col, coverage, performance;

		public BatteryCharger(int row, int col, int coverage, int performance) {
			this.row = row;
			this.col = col;
			this.coverage = coverage;
			this.performance = performance;
		}
		
		// 현재 행, 열에서 접근 가능한지 판별
		public boolean accessible(int row, int col) {
			return getDistance(row, col) <= coverage;
		}
		
		// 행, 열에 따른 맨해튼 거리 계산
		private int getDistance(int row, int col) {
			return Math.abs(this.row - row) + Math.abs(this.col - col);
		}

		// performance 순으로 비교
		@Override
		public int compareTo(BatteryCharger o) {
			return Integer.compare(this.performance, o.performance);
		}
		
		// 디버깅용
		@Override
		public String toString() {
			return " [ row: " + row + " col: " + col + " C: " + coverage + " P: " + performance + " ]";
		}
	}
}
