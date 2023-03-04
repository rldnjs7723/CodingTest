import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 20056번 마법사 상어와 파이어볼
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_20056 {
	public static final int N = 0, NE = 1, E = 2, SE = 3, S = 4, SW = 5, W = 6, NW = 7;
	public static final int[] dRow = {-1, -1, 0, 1, 1, 1, 0, -1};
	public static final int[] dCol = {0, 1, 1, 1, 0, -1, -1, -1};
	public static final int DIV = 5;
	// 격자 크기
	public static int SIZE;
	public static final Set<Integer> EVEN = new HashSet<>(Arrays.asList(0, 2, 4, 6));
	public static final Set<Integer> ODD = new HashSet<>(Arrays.asList(1, 3, 5, 7));
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		SIZE = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
	
		// 파이어볼을 합치기 위한 Queue 배열
		FireballQueue[][] state = new FireballQueue[SIZE][SIZE];
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				state[row][col] = new FireballQueue();
			}
		}
		// 파이어볼 상태 입력
		FireballQueue queue = new FireballQueue();
		int r, c, m, d, s;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 행 (입력은 1부터 시작)
			r = Integer.parseInt(st.nextToken()) - 1;
			// 열 (입력은 1부터 시작)
			c = Integer.parseInt(st.nextToken()) - 1;
			// 질량
			m = Integer.parseInt(st.nextToken());
			// 방향
			d = Integer.parseInt(st.nextToken());
			// 속력
			s = Integer.parseInt(st.nextToken());
			
			queue.offer(new Fireball(r, c, m, s, d));
		}
		
		// 파이어볼 K번 이동
		Fireball.moveFireballs(K, state, queue);
		// 남은 파이어볼 질량 계산 
		System.out.println(Fireball.getMass(queue));
		br.close();
	}
	
	
	@SuppressWarnings("serial")
	public static class FireballQueue extends ArrayDeque<Fireball>{}
	
	public static class Fireball {
		// 파이어볼 행, 열, 질량, 방향, 속력
		int r, c, m, d, s;

		public Fireball(int r, int c, int m, int d, int s) {
			this.r = r;
			this.c = c;
			this.m = m;
			this.d = d;
			this.s = s;
		}
		
		// 파이어볼 이동
		public void move(FireballQueue[][] state) {
			// 속력은 격자 크기만큼 이동하면 제자리
			this.r = (this.r + dRow[d] * (s % SIZE)) % SIZE;
			this.c = (this.c + dCol[d] * (s % SIZE)) % SIZE;
			// 음수는 양수로 변환
			if(this.r < 0) this.r += SIZE;
			if(this.c < 0) this.c += SIZE;
			
			state[this.r][this.c].offer(this);
		}
		
		// 파이어볼 합치기
		public static void combineFireball(FireballQueue[][] state, FireballQueue queue) {
			int mSum, sSum, count;
			FireballQueue curr;
			Fireball fireball;
			Set<Integer> dirs = new HashSet<>();
			for(int row = 0; row < SIZE; row++) {
				for(int col = 0; col < SIZE; col++) {
					curr = state[row][col];
					// 비어 있는 위치는 생략
					if(curr.isEmpty()) continue;
					// 파이어볼이 하나만 있다면 나뉘지 않음
					if(curr.size() == 1) {
						queue.offer(curr.poll());
						continue;
					}
					
					// 2개 이상의 파이어볼이 있는 칸
					count = curr.size();
					mSum = 0;
					sSum = 0;
					dirs.clear();
					// 파이어볼 합치기
					while(!curr.isEmpty()) {
						fireball = curr.poll();
						// 질량 합산
						mSum += fireball.m;
						// 속력 합산
						sSum += fireball.s;
						// 방향 취합
						dirs.add(fireball.d);
					}
					
					// 질량 계산
					mSum /= 5;
					// 질량이 0인 파이어볼은 소멸
					if(mSum == 0) continue;
					// 속력 계산
					sSum /= count;
			
					// 파이어볼 나누기
					if(EVEN.containsAll(dirs) || ODD.containsAll(dirs)) {
						// 방향이 전부 짝수이거나 홀수인 경우
						// 방향 0, 2, 4, 6
						for(int dir: EVEN) {
							queue.offer(new Fireball(row, col, mSum, dir, sSum));
						}
					} else {
						// 방향 1, 3, 5, 7
						for(int dir: ODD) {
							queue.offer(new Fireball(row, col, mSum, dir, sSum));
						}
					}
				}
			}
		}
		
		// 모든 파이어볼을 K번 움직이기
		public static void moveFireballs(int K, FireballQueue[][] state, FireballQueue queue) {
			for(int i = 0; i < K; i++) {
				// 파이어볼이 없다면 중단
				if(queue.isEmpty()) break;
				// 파이어볼 이동
				while(!queue.isEmpty()) {
					queue.poll().move(state);
				}
				// 파이어볼 합치기
				combineFireball(state, queue);
			}
		}
		
		// 남아있는 파이어볼 질량 합 계산
		public static int getMass(FireballQueue queue) {
			int mSum = 0;
			while(!queue.isEmpty()) {
				mSum += queue.poll().m;
			}
			
			return mSum;
		}
	}
}
