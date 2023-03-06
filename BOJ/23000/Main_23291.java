import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 23291번 어항 정리
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Main_23291 {
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dRow = {0, -1, 0, 1};
	public static final int[] dCol = {-1, 0, 1, 0};
	public static final int BLANK = 0;
	public static int N, K, BOTTOM;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 어항의 수
		N = Integer.parseInt(st.nextToken());
		// 목표 물고기 수 차이
		K = Integer.parseInt(st.nextToken());
		// 어항 바닥 Index
		BOTTOM = N - 1;
		// 어항 입력
		int[][] state = new int[N][N];
		st = new StringTokenizer(br.readLine());
		for(int col = 0; col < N; col++) {
			state[BOTTOM][col] = Integer.parseInt(st.nextToken());
		}
		
		// 물고기 최대, 최소 수 차이가 K 이하가 될 때까지 어항 정리 반복
		int count = 0;
		// 8. 물고기 최대 수, 최소 수 차이 계산
		while(maxMinDiff(state) > K) {
			// 1. 물고기 추가
			addFish(state);
			// 2. 왼쪽의 어항 90도 회전 후 오른쪽의 어항 위에 놓기
			levitation90(state);
			// 3. 물고기 수 조절
			distributeFish(state);
			// 4. 어항 일렬로 나열하기
			serialize(state);
			// 5. 왼쪽의 어항 180도 회전 후 오른쪽의 어항 위에 놓기
			levitation180(state);
			// 6. 물고기 수 조절
			distributeFish(state);
			// 7. 어항 일렬로 나열하기
			serialize(state);
			
			// 어항 정리 횟수 추가
			count++;
		}
		
		System.out.println(count);
		br.close();
	}
	
	// 1. 물고기의 수가 가장 적은 어항에 한 마리씩 넣기
	public static void addFish(int[][] state) {
		// 최소 개수 탐색
		int min = Integer.MAX_VALUE;
		for(int col = 0; col < N; col++) {
			min = Math.min(min, state[BOTTOM][col]);
		}
		
		// 최소 개수인 어항에 물고기 추가
		for(int col = 0; col < N; col++) {
			if(state[BOTTOM][col] == min) state[BOTTOM][col]++;
		}
	}
	
	// 2. 시계 방향 90도 회전 공중 부양 작업 수행
	public static void levitation90 (int[][] state) {
		int width, height = 1;
		int start = 0, end = 0;
		// 올릴 위치의 끝점이 범위를 벗어나지 않는 경우에만 수행
		while(checkRange(end + height)) {
			// 배열 회전
			rotate90(start, end, height, state);
			// 다음 범위 설정
			width = height;
			height = end - start + 2;
			start = end + 1;
			end += width;
		}
	}
	
	/**
	 * 2. 배열을 시계방향으로 90도 회전 후 오른쪽 어항 위에 쌓기
	 * @param start			회전할 배열 시작 열
	 * @param end			회전할 배열 종료 열
	 * @param height		회전할 배열 높이
	 * @param state			회전을 적용할 어항 상태
	 */
	public static void rotate90(int start, int end, int height, int[][] state) {
		// 배열 추출
		int width = end - start + 1;
		int[][] result = new int[height][width];
		int r, c;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				r = BOTTOM - height + 1 + row;
				c = start + col;
				result[row][col] = state[r][c];
				state[r][c] = BLANK;
			}
		}
		
		// 배열 회전
		result = clockwiseRotate(result);
		
		// 어항 이동
		moveFishbowl(end + 1, 1, result, state);
	}
	
	// 3. 물고기의 수 조절하기
	public static void distributeFish(int[][] state) {
		int[][] amount = new int[N][N];
		
		int r, c;
		int curr, d;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				// 비어 있는 칸은 생략
				if(state[row][col] == BLANK) continue;
				// 현재 어항의 물고기 수
				curr = state[row][col];
				
				// 사방 탐색으로 자신보다 적은 물고기 수를 가진 어항 찾기
				for(int dir = 0; dir < 4; dir++) {
					r = row + dRow[dir];
					c = col + dCol[dir];
					
					// 범위를 벗어난 경우 다음 방향 탐색
					if(!checkRange(r) || !checkRange(c)) continue;
					// 빈 칸은 고려하지 않기
					if(state[r][c] == BLANK) continue;
					// 자신보다 크거나 같은 어항은 고려하지 않기
					if(curr <= state[r][c]) continue;
					
					// 보내줄 물고기 수
					d = (curr - state[r][c]) / 5;
					// 물고기 보내주기
					amount[row][col] -= d;
					amount[r][c] += d;
				}
			}
		}
		
		// 보내준 물고기 수 반영
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				state[row][col] += amount[row][col];
			}
		}
	}
	
	// 4. 어항을 바닥에 일렬로 놓기
	public static void serialize(int[][] state) {
		Queue<Integer> sequence = new ArrayDeque<>();
		int row;
		for(int col = 0; col < N; col++) {
			row = BOTTOM;
			// 빈 칸이 아닌 경우에 큐에 추가
			while(checkRange(row) && state[row][col] != BLANK) {
				sequence.offer(state[row][col]);
				// 방문한 위치의 어항 제거
				state[row][col] = BLANK;
				row--;
			}
		}
		
		// 큐로 일렬로 놓은 어항 다시 배치
		for(int col = 0; col < N; col++) {
			state[BOTTOM][col] = sequence.poll();
		}
	}
	
	// 5. 시계 방향 180도 회전 공중 부양 작업 수행
	public static void levitation180(int[][] state) {
		int width = N / 2;
		int start = 0, end = width - 1, height = 1;
		// 첫 번째 회전 수행
		rotate180(start, end, height, state);
		
		// 두 번째 회전 수행
		width = width / 2;
		start = end + 1;
		end = end + width;
		height = 2;
		rotate180(start, end, height, state);
	}
	
	/**
	 * 5. 배열을 시계방향으로 180도 회전 후 오른쪽 어항 위에 쌓기
	 * @param start			회전할 배열 시작 열
	 * @param end			회전할 배열 종료 열
	 * @param height		회전할 배열 높이
	 * @param state			회전을 적용할 어항 상태
	 */
	public static void rotate180(int start, int end, int height, int[][] state) {
		// 배열 추출
		int width = end - start + 1;
		int[][] result = new int[height][width];
		int r, c;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				r = BOTTOM - height + 1 + row;
				c = start + col;
				result[row][col] = state[r][c];
				state[r][c] = BLANK;
			}
		}
		
		// 배열 회전
		result = clockwiseRotate(result);
		result = clockwiseRotate(result);
		
		// 어항 이동
		moveFishbowl(end + 1, height, result, state);
	}
	
	// 8. 물고기가 가장 많이 들어있는 어항과 가장 적게 들어있는 어항의 물고기 수 차이 계산
	public static int maxMinDiff(int[][] state) {
		int max = 0, min = Integer.MAX_VALUE;
		for(int col = 0; col < N; col++) {
			max = Math.max(max, state[BOTTOM][col]);
			min = Math.min(min, state[BOTTOM][col]);
		}
		
		return max - min;
	}
	
	// 입력 받은 배열을 시계 방향 90도 회전
	public static int[][] clockwiseRotate(int[][] arr) {
		int width = arr.length;
		int height = arr[0].length;
		
		int[][] result = new int[height][width];
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				result[row][col] = arr[width - 1 - col][row];
			}
		}
		
		return result;
	}
	
	// 왼쪽의 어항을 오른쪽 어항의 위에 놓기
	public static void moveFishbowl(int start, int targetHeight, int[][] fishBowl, int[][] state) {
		int height = fishBowl.length;
		int width = fishBowl[0].length;
		int r, c;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				r = BOTTOM - targetHeight - height + 1 + row;
				c = start + col;
				state[r][c] = fishBowl[row][col];
			}
		}
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	
	// 배열 출력
	public static void printArr(int[][] state) {
		for(int[] inner: state) {
			System.out.println(Arrays.toString(inner));
		}
		System.out.println("----------------");
	}
}
