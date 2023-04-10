import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 2383번 점심 식사시간
 * 문제 분류: 구현, 시뮬레이션, 완전 탐색, 자료구조 (우선순위 큐)
 * @author Giwon
 */
public class Solution_2383 {
	public static final int PERSON = 1;
	// 지도 크기
	public static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int[][][] distance;
		int[][] map;
		List<Stair> stair = new ArrayList<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			// 지도 크기
			N = Integer.parseInt(br.readLine().trim());
			// 지도 입력
			stair.clear();
			map = new int[N][N];
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < N; col++) {
					map[row][col] = Integer.parseInt(st.nextToken());
					if(map[row][col] >= 2) {
						stair.add(new Stair(row, col, map[row][col]));
					}
				}
			}
			
			// 각 계단까지의 거리 기록
			distance = new int[2][N][N];
			Stair curr;
			for(int idx = 0; idx < 2; idx++) {
				curr = stair.get(idx);
				for(int row = 0; row < N; row++) {
					for(int col = 0; col < N; col++) {
						distance[idx][row][col] = curr.getDistance(row, col);
					}
				}
			}
			
			// 사람별 계단에 도달하는 거리 체크
			List<Person> people = new ArrayList<>();
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < N; col++) {
					if(map[row][col] == 1) {
						people.add(new Person(distance[0][row][col], distance[1][row][col]));
					}
				}
			}
			
			System.out.println("#" + test_case + " " + getFinishTime(0, 0, people, stair));
		}
		
		br.close();
	}
	
	// 비트마스크에 따라 각각의 사람 내려가도록 시뮬레이션
	public static int simulate(int bitmask, List<Person> people, List<Stair> stair) {
		Stair stair1 = stair.get(0);
		Stair stair2 = stair.get(1);
		
		// 비트마스크에 따라 계단 우선순위 큐에 사람 넣기
		stair1.clear();
		stair2.clear();
		int size = people.size();
		for(int i = 0; i < size; i++) {
			if((bitmask & (1 << i)) > 0) {
				stair2.priorityQueue.offer(people.get(i).distance[1]);
			} else {
				stair1.priorityQueue.offer(people.get(i).distance[0]);
			}
		}
		
		// 시간 1분씩 늘려가며 진행
		int time = 0;
		while(!stair1.isDone() || !stair2.isDone()) {
			// 시간 1초 진행
			stair1.flowTime();
			stair2.flowTime();
			time++;
			
			// 계단 내려갈 수 있는 사람 체크
			stair1.goDown(time);
			stair2.goDown(time);
		}
		
		return time + 1;
	}
	
	
	// 최대 10명의 사람이 계단 1, 2에 도착하는 경우의 수(2^10)를 체크하여 이동 완료하는 최소 시간 계산 
	public static int getFinishTime(int bitmask, int cnt, List<Person> people, List<Stair> stair) {
		int size = people.size();
		// 모든 경우의 수를 체크했다면 시뮬레이션 수행하여 결과 계산
		if(cnt == size) {
			return simulate(bitmask, people, stair);
		}
		
		int min = Integer.MAX_VALUE;
		// 계단 1에 넣기
		min = Math.min(min, getFinishTime(bitmask, cnt + 1, people, stair));
		// 계단 2에 넣기
		min = Math.min(min, getFinishTime(bitmask | (1 << cnt), cnt + 1, people, stair));
		
		return min;
	}
	
	public static class Person {
		// 계단별 거리
		int[] distance;
		
		public Person(int first, int second) {
			distance = new int[2];
			distance[0] = first;
			distance[1] = second;
		}
	}

	public static class Stair {
		// 계단 위치
		int row, col;
		// 계단 길이
		int len;
		// 계단에 들어올 사람 우선순위 큐
		Queue<Integer> priorityQueue;
		// 계단을 내려가고 있는 사람 체크
		List<Integer> people;
		
		public Stair(int row, int col, int len) {
			this.row = row;
			this.col = col;
			this.len = len;
			this.priorityQueue = new PriorityQueue<>();
			this.people = new ArrayList<>();
		}
		
		// 맨해튼 거리 계산
		public int getDistance(int row, int col) {
			return Math.abs(this.row - row) + Math.abs(this.col - col);
		}
		
		// 초기화
		public void clear() {
			priorityQueue.clear();
			people.clear();
		}
		
		// 입력 받은 시간보다 도착 시간이 작은 사람을 내려보낼 수 있다면 내려보내기
		public void goDown(int time) {
			while(people.size() < 3) {
				// 계단에 도착한 사람이 없다면 중단
				if(priorityQueue.isEmpty() || priorityQueue.peek() > time) break;
				// 계단 내려갈 사람 등록
				priorityQueue.poll();
				people.add(len);
			}
		}
		
		// 시간 1분 진행
		public void flowTime() {
			int size = people.size();
			// 시간 1씩 감소
			for(int i = 0; i < size; i++) {
				people.set(i, people.get(i) - 1);
			}
			
			// 계단을 완전히 내려갔다면 제거
			for(int i = people.size() - 1; i >= 0; i--) {
				if(people.get(i) == 0) people.remove(i);
			}
		}
		
		// 모든 사람이 계단을 내려갔는지 체크
		public boolean isDone() {
			return priorityQueue.isEmpty() && people.isEmpty();
		}
	}
	
}
