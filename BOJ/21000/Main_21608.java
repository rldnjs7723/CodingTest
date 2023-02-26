import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 21608번 상어 초등학교
 * 문제 분류: 완전 탐색, 구현
 * @author Giwon
 */
public class Main_21608 {
	public static final int BLANK = 0;
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dRow = {0, -1, 0, 1};
	public static final int[] dCol = {-1, 0, 1, 0};
	// 주변 친구 수에 따른 만족도
	public static final int[] SCORE = {0, 1, 10, 100, 1000};
	public static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		int total = N * N;
		
		// 좌석 초기화
		Seat[][] classroom = new Seat[N][N];
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				classroom[row][col] = new Seat(row, col);
			}
		}
		
		// 학생 번호 순서대로 좌석 지정
		BestFriend[] favorite = new BestFriend[total + 1];
		int studentId;
		Seat best;
		for(int student = 0; student < total; student++) {
			st = new StringTokenizer(br.readLine());
			studentId = Integer.parseInt(st.nextToken());
			// 가장 친한 4명의 친구 번호
			favorite[studentId] = new BestFriend();
			for(int i = 0; i < 4; i++) {
				favorite[studentId].add(Integer.parseInt(st.nextToken()));
			}
			
			// 우선순위 큐의 맨 앞에 있는 좌석이 최적의 좌석
			best = getBestSeat(classroom, favorite[studentId]);
			best.studentId = studentId;
		}
		
		// 지정된 좌석대로 점수 체크
		System.out.println(getScore(classroom, favorite));
		br.close();
	}
	
	/**
	 * 현재 교실 상태에서 최적의 자리를 리턴
	 * @param classroom		교실 상태 2차원 배열
	 * @param bf			친한 친구 4명에 대한 Set
	 * @return				최적의 자리 리턴
	 */
	public static Seat getBestSeat(Seat[][] classroom, BestFriend bf) {
		Queue<Seat> bestSeat = new PriorityQueue<>();
		
		Seat curr;
		int r, c;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				curr = classroom[row][col];
				// 빈 자리가 아니라면 다음 자리 탐색
				if(curr.studentId != BLANK) continue;
				
				// 주변 네 방향을 확인하여 친구, 빈칸의 수 확인
				curr.clear();
				for(int dir = 0; dir < 4; dir++) {
					r = curr.row + dRow[dir];
					c = curr.col + dCol[dir];
					
					if(checkRange(r) && checkRange(c)) {
						if(classroom[r][c].studentId == BLANK) curr.blanks++;
						else if(bf.contains(classroom[r][c].studentId)) curr.friends++;
					}
				}
				// 우선순위 큐에 넣어 자동 정렬
				bestSeat.offer(curr);
			}
		}
		
		return bestSeat.poll();
	}
	
	/**
	 * 좌석에 따른 만족도 체크
	 * @param classroom		교실 상태 2차원 배열
	 * @param favorite		각 학생별 친한 친구 4명에 대한 Set 배열
	 * @return				만족도 총합 리턴
	 */
	public static int getScore(Seat[][] classroom, BestFriend[] favorite) {
		int score = 0;
		int r, c, count;
		BestFriend bf;
		for(Seat[] seats: classroom) {
			for(Seat curr: seats) {
				count = 0;
				bf = favorite[curr.studentId];
				// 주변 네 방향을 확인하여 친구 수 확인
				for(int dir = 0; dir < 4; dir++) {
					r = curr.row + dRow[dir];
					c = curr.col + dCol[dir];
					
					if(checkRange(r) && checkRange(c) &&
							bf.contains(classroom[r][c].studentId))
						count++;
				}
				score += SCORE[count];
			}
		}
		
		return score;
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	
	// 가장 좋아하는 친구 4명 확인용 클래스
	@SuppressWarnings("serial")
	public static class BestFriend extends HashSet<Integer> {}
	
	public static class Seat implements Comparable<Seat>{
		int row, col, friends, blanks, studentId;
		
		public Seat(int row, int col) {
			this.row = row;
			this.col = col;
			this.friends = 0;
			this.blanks = 0;
			this.studentId = BLANK;
		}
		
		public void clear() {
			this.friends = 0;
			this.blanks = 0;
		}

		// 우선 순위 큐 사용을 위한 Comparable 인터페이스 구현
		@Override
		public int compareTo(Seat o) {
			// 주변의 친구들 수가 다르면 내림 차순으로 정렬
			if(this.friends != o.friends) return Integer.compare(o.friends, this.friends);
			// 빈 칸의 수가 다르면 내림 차순으로 정렬
			if(this.blanks != o.blanks) return Integer.compare(o.blanks, this.blanks);
			// 자리의 행 수가 다르면 오름 차순으로 정렬
			if(this.row != o.row) return Integer.compare(this.row, o.row);
			// 자리의 열 수가 다르면 오름 차순으로 정렬
			return Integer.compare(this.col, o.col);
		}
		
		@Override
		public String toString() {
			return Integer.toString(studentId);
		}
	}
}
