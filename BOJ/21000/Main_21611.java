import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 21611번 마법사 상어와 블리자드
 * 문제 분류: 구현, 시뮬레이션, 자료 구조 (리스트)
 * @author Giwon
 */
public class Main_21611 {
	// 사방 탐색. 방향은 1 ~ 4까지 존재
	public static final int RIGHT = 0, UP = 1, DOWN = 2, LEFT = 3;
	public static final int[] dRow = {0, -1, 1, 0};
	public static final int[] dCol = {1, 0, 0, -1};
	// 회전 방향
	public static final int[] ROTATE = {LEFT, DOWN, RIGHT, UP};
	public static final int BLANK = 0, SHARK = -1;
	// 이동 횟수 증가 간격
	public static final double INTERVAL = 0.5;
	// 격자 크기
	public static int N;
	// 각 행, 열에서의 Index 값
	public static int[][] index;
	// 폭발한 구슬 개수
	public static int[] blastCount = new int[4];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 격자 크기
		N = Integer.parseInt(st.nextToken());
		// 마법 수
		final int M = Integer.parseInt(st.nextToken());
		
		// 격자 상태 입력
		int[][] state = new int[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				state[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		// 상어 위치 설정
		state[N / 2][N / 2] = SHARK;
		
		// 입력 받은 상태를 리스트로 변환
		index = new int[N][N];
		List<Integer> beads = getList(state);
		
		// 마법 입력
		int d, s;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 마법 방향
			d = Integer.parseInt(st.nextToken());
			// 마법 거리
			s = Integer.parseInt(st.nextToken());
			
			// 구슬 부수기
			destroyBeads(d, s, beads);
			// 구슬 폭발시키기
			blastBeads(beads);
			// 구슬 변화시키기
			changeBeads(beads);
		}
		
		// 폭발한 구슬 개수에 따른 결과 계산
		int result = 1 * blastCount[1] + 2 * blastCount[2] + 3 * blastCount[3];
		System.out.println(result);
		br.close();
	}
	
	// 구슬 변화시키기
	public static void changeBeads(List<Integer> beads) {
		int idx = beads.size() - 1;
		// 구슬 종류, 연속된 개수
		int type = -1, count = 0;
		while(!(idx == 0)) {
			// 현재 구슬이 이전 구슬과 같다면 카운트 추가
			if(type == beads.get(idx)) count++;
			else {
				// 이전 구슬과 다르다면 현재 구슬로 변경
				type = beads.get(idx);
				count = 1;
			}
			
			// 다음 구슬이 현재 구슬과 다르다면 변화 적용
			if(beads.get(idx - 1) != type) {
				// 리스트에서 구슬 제거
				for(int i = 0; i < count; i++) {
					beads.remove(idx);
				}
				// 해당 위치에 개수와 구슬 번호 추가
				beads.add(idx, type);
				beads.add(idx, count);
				// 초기화
				count = 0;
				type = -1;
			}
			
			// 다음 구슬 탐색
			idx--;
		}
		
		// 구슬 개수가 N^2 개를 벗어나면 제거하기
		int max = N * N;
		while(beads.size() > max) {
			beads.remove(max);
		}
	}
	
	// 구슬 폭파시키기
	public static void blastBeads(List<Integer> beads) {
		// 구슬 종류, 연속된 개수
		int idx = 0, type = -1, count = 0;
		Queue<Blast> blastQueue = new ArrayDeque<>();
		Blast curr;
		do {
			// 대기열에 있는 폭파시킬 구슬 폭파
			while(!blastQueue.isEmpty()) {
				curr = blastQueue.poll();
				
				// 리스트에서 구슬 제거
				for(int i = 0; i < curr.count; i++) {
					beads.remove(curr.idx);
				}
			}
			
			// 리스트 인덱스 순서를 고려하여 뒤에서부터 탐색
			idx = beads.size() - 1;
			type = -1; count = 0;
			// 상어 위치에 도달할 때까지 구슬이 4개 이상 연속으로 위치한 경우 제거하기
			while(!(idx == 0)) {
				// 현재 구슬이 이전 구슬과 같다면 카운트 추가
				if(type == beads.get(idx)) count++;
				else {
					// 이전 구슬과 다르다면 폭발할 개수에 도달하지 않은 것이므로 초기화
					type = beads.get(idx);
					count = 1;
				}
				
				// 구슬이 연속으로 4개 이상 있고, 다음 구슬이 현재 구슬과 다르다면 폭발
				if(count >= 4 && beads.get(idx - 1) != type) {
					// 폭발한 개수 카운트
					blastCount[type] += count;
					// 대기열에 폭파시킬 구슬 위치, 개수 입력
					blastQueue.offer(new Blast(idx, count));
					// 초기화
					count = 0;
					type = -1;
				}
				
				// 다음 구슬 탐색
				idx--;
			}
			
		} while (!blastQueue.isEmpty());
	}
	
	
	// 얼음 파편으로 구슬 부수기
	public static void destroyBeads(int d, int s, List<Integer> beads) {
		// 1 ~ 4의 방향을 0 ~ 3으로 변경
		d %= 4;
		// 제거할 구슬의 인덱스 기록
		Stack<Integer> beadsIndex = new Stack<>();
		int row = N / 2, col = N / 2;
		for(int i = 0; i < s; i++) {
			row += dRow[d];
			col += dCol[d];
			
			beadsIndex.push(index[row][col]);
		}
		
		// 리스트의 뒤에서부터 구슬 제거
		int curr;
		while(!beadsIndex.isEmpty()) {
			curr = beadsIndex.pop();
			
			// 빈 칸이 아닌 경우 구슬 제거
			if(curr < beads.size()) beads.remove(curr); 
		}
	}
	
	// 입력 받은 상태를 리스트로 변환
	public static List<Integer> getList(int[][] state) {
		List<Integer> result = new ArrayList<>();
		
		int row = N / 2, col = N / 2;
		// 현재 위치의 List에서의 Index
		int idx = 0;
		// 시작 방향은 왼쪽
		int dir = 0;
		double count = 1.0;
		// 0행 0열에 도달할 때까지 수를 리스트에 추가
		while(!(row == 0 && col == 0)) {
			for(int i = 0; i < (int) count; i++) {
				// 각 행, 열의 리스트에서의 인덱스 기록
				index[row][col] = idx++;
				// 리스트에 각 상태 기록
				if(state[row][col] != BLANK) result.add(state[row][col]);
				
				// 마지막 위치에 도달했다면 중단
				if(row == 0 && col == 0) break;
				
				// 다음 위치로 이동
				row += dRow[ROTATE[dir]];
				col += dCol[ROTATE[dir]];
			}
 			
			// 방향 이동
			dir = (dir + 1) % 4;
			// 이동 횟수 증가
			count += INTERVAL;
		}
		
		return result;
	}

	// 배열 출력
	public static void printArr(int[][] arr) {
		for(int[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
	}
	
	public static class Blast {
		int idx, count;
		
		public Blast(int idx, int count) {
			this.idx = idx;
			this.count = count;
		}
	}
}
