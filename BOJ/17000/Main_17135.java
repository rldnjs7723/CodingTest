import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 백준 17135번 캐슬 디펜스
 * 문제 분류: 완전 탐색 (조합), Next Permutation, 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_17135 {
	// 궁수는 3명
	public static final int MAXARCHER = 3;
	public static final int BLANK = 0, ENEMY = 1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 행 크기
		int N = Integer.parseInt(st.nextToken());
		// 열 크기
		int M = Integer.parseInt(st.nextToken());
		// 공격 거리
		int D = Integer.parseInt(st.nextToken());
		
		// 맵 상태 입력
		int[][] map = new int[N][M];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < M; col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// N <= 15이므로 완전 탐색으로 풀 수 있을 것이라 판단 (최대 455 개의 조합)
		int[] combination = new int[M];
		Arrays.fill(combination, M - 3, M, ENEMY);
		// 궁수 배열
		Archer[] archers = new Archer[MAXARCHER];
		for(int i = 0; i < MAXARCHER; i++) {
			archers[i] = new Archer();
		}
		// 궁수가 쏠 수 있는지 확인할 우선 순위 큐
		Shootable[] priority = new Shootable[MAXARCHER];
		for(int i = 0; i < MAXARCHER; i++) {
			priority[i] = new Shootable();
		}
		
		int count, distance, kill, max = 0;
		int[][] state = new int[N][M];
		Enemy curr;
		do {
			// 시작 상태 복사
			for(int row = 0; row < N; row++) {
				for(int col = 0; col < M; col++) {
					state[row][col] = map[row][col];
				}
			}
			
			// 궁수 배치
			count = 0;
			for(int col = 0; col < M; col++) {
				if(combination[col] == ENEMY) {
					archers[count++].setCoord(N, col);
					if(count == MAXARCHER) break;
				}
			}
			
			kill = 0;
			for(int turn = N - 1; turn >= 0; turn--) {
				// 매 턴마다 쏠 수 있는 적을 확인
				for(Shootable prior: priority) {
					prior.clear();
				}
				
				for(int row = 0; row <= turn; row++) {
					for(int col = 0; col < M; col++) {
						if(state[row][col] == ENEMY) {
							for(int i = 0; i < MAXARCHER; i++) {
								distance = archers[i].getDistance(row, col);
								// 쏠 수 있는 적인 경우 우선순위 큐에 저장
								if(distance <= D) {
									priority[i].offer(new Enemy(row, col, distance));
								}
							}
						}
					}
				}
				
				// 우선 순위에 따라 가장 가까운 적 처치
				for(Shootable prior: priority) {
					if(prior.size() > 0) {
						curr = prior.poll();
						if(state[curr.row][curr.col] == ENEMY) {
							state[curr.row][curr.col] = BLANK;
							kill++;
						}
					}
				}
				
				// 턴이 끝난 뒤 궁수를 한 칸 위로 이동
				for(int i = 0; i < MAXARCHER; i++) {
					archers[i].goUp();
				}
			}
			
			// 최대 적 처치 수 갱신
			max = Math.max(max, kill);
		} while (nextPermutation(combination));
		
		System.out.println(max);
		br.close();
	}
	
	// NP로 다음 조합 계산
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
	
	@SuppressWarnings("serial")
	public static class Shootable extends PriorityQueue<Enemy>{}
	
	public static class Enemy implements Comparable<Enemy> {
		int row, col, distance;
		
		public Enemy(int row, int col, int distance) {
			this.row = row;
			this.col = col;
			this.distance = distance;
		}
		
		// 우선순위 큐 활용을 위한 Comparable 인터페이스 구현
		@Override
		public int compareTo(Enemy o) {
			// 거리가 같다면 열이 더 작은 값을 선택
			if(this.distance == o.distance) return Integer.compare(this.col, o.col);
			// 거리가 더 작은 값을 선택
			else return Integer.compare(this.distance, o.distance);
		}
		
		// 행, 열이 같으면 같다
		@Override
		public boolean equals(Object obj) {
			if(obj == null) return false;
			if(!(obj instanceof Enemy)) return false;
			
			Enemy o = (Enemy) obj;
			return this.row == o.row && this.col == o.col;
		}
	}
	
	public static class Archer {
		int row, col;

		public Archer() {
			this.row = -1;
			this.col = -1;
		}
		
		// 위치 설정
		public void setCoord(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		// 적을 아래로 내리는 대신 3명이 최대인 궁수를 위로 올리기
		public void goUp() {
			// 행, 열 값이 음수여도 계산 가능해야 함
			this.row--;
		}
		
		// 적과의 맨해튼 거리 계산
		public int getDistance(int row, int col) {
			return Math.abs(this.row - row) + Math.abs(this.col - col);
		}
		
		// 디버깅용 문자열 출력
		@Override
		public String toString() {
			return "[ row: " + row + " col: " + col + " ]";
		}
	}
}
