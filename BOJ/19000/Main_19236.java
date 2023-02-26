import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 19236번 청소년 상어
 * 문제 분류: 구현, 시뮬레이션, 비트마스킹, 완전 탐색
 * @author Giwon
 */
public class Main_19236 {
	// 4x4 크기의 공간
	public static final int SIZE = 4;
	public static final int TOTALDIR = 8;
	// 45도 반시계 회전한 방향 순서
	public static final int NE = 0, N = 1, NW = 2, W = 3, SW = 4, S = 5, SE = 6, E = 7;
	public static final int[] dRow = {-1, -1, -1, 0, 1, 1, 1, 0};
	public static final int[] dCol = {1, 0, -1, -1, -1, 0, 1, 1};
	public static int BLANK, NULL = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 물고기 번호, 방향 입력
		int[][] id = new int[SIZE][SIZE];
		int[][] dir = new int[SIZE][SIZE];
		for(int row = 0; row < SIZE; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < SIZE; col++) {
				id[row][col] = Integer.parseInt(st.nextToken());
				dir[row][col] = Integer.parseInt(st.nextToken()) % 8;
			}
		}
		
		BLANK = id[0][0];
		// 상어 위치
		int R = 0, C = 0; 
		// 상어 방향
		int D = dir[R][C];
		long eatBitmask = 1L << (id[R][C] - 1);

		// 상어가 먹은 물고기 번호 총합 출력
		System.out.println(moveShark(R, C, D, eatBitmask, idToBitmask(id), dirToBitmask(dir)));
		br.close();
	}
	
	// 먹은 물고기 번호 합산
	public static int eatSum(long eatBitmask) {
		int sum = 0;
		for(int i = 1; i <= 16; i++) {
			if((eatBitmask & 1) > 0) sum += i;
			eatBitmask = eatBitmask >> 1;
		}
		return sum;
	}
	
	/**
	 * 완전 탐색으로 상어 위치 변경해가며 물고기 먹기
	 * @param R				상어 위치 (행)
	 * @param C				상어 위치 (열)
	 * @param D				상어 방향
	 * @param eatBitmask	상어가 먹은 물고기 번호 비트마스크
	 * @param idBitmask		물고기 번호 비트마스크
	 * @param dirBitmask	물고기 방향 비트마스크
	 * @return				상어가 먹은 물고기 번호 총합의 최댓값
	 */
	public static int moveShark(int R, int C, int D, long eatBitmask, long idBitmask, long dirBitmask) {	
		int[][] id = bitmaskToId(idBitmask);
		int[][] dir = bitmaskToDir(dirBitmask);

		// 물고기 먼저 이동
		moveFish(R, C, eatBitmask, id, dir);
		long nextId = idToBitmask(id);
		long nextDir = dirToBitmask(dir);
		
		int r, c, count = 0, max = 0, temp;
		r = R + dRow[D];
		c = C + dCol[D];
		while(checkRange(r) && checkRange(c)) {
			if(id[r][c] != BLANK) {
				// 물고기가 존재하는 경우 해당 위치로 이동하여 물고기를 먹고 다음 방향 탐색
				temp = moveShark(r, c, dir[r][c], eatBitmask | (1 << (id[r][c] - 1)), (nextId - getIdBit(r, c, id[r][c])) | getIdBit(r, c, BLANK), nextDir);
				max = Math.max(temp, max);
				count++;
			}
			
			r += dRow[D];
			c += dCol[D];
		}
		
		// 더 이상 이동할 수 없다면 지금까지 먹은 물고기 번호 합산하여 리턴
		if(count == 0) return eatSum(eatBitmask);
		return max;
	}
	
	
	/**
	 * 모든 물고기 순서대로 이동시키기
	 * @param R				상어 위치 (행)
	 * @param C				상어 위치 (열)
	 * @param eatBitmask	상어가 먹은 물고기 번호 비트마스크
	 * @param id			물고기 번호 배열
	 * @param dir			물고기 방향 배열
	 */
	public static void moveFish(int R, int C, long eatBitmask, int[][] id, int[][] dir) {
		int row = 0, col = 0;
		int r, c, d;
		int firstDirection;
		for(int ID = 1; ID <= 16; ID++) {
			// 이미 먹은 물고기 번호는 생략
			if((eatBitmask & (1 << (ID - 1))) > 0) continue;

			// 현재 물고기 위치 탐색
			for(row = 0; row < SIZE; row++) {
				for(col = 0; col < SIZE; col++) {
					if(ID == id[row][col]) break;
				}
				if(col != SIZE) break;
			}
			
			// 처음 방향 기록
			firstDirection = dir[row][col];
			d = firstDirection;
			r = row + dRow[d];
			c = col + dCol[d];
			
			// 벽이 아니거나 상어가 아닐 때까지 방향 변환
			while(!checkRange(r) || !checkRange(c) || (R == r && C == c)) {
				d = counterClockwise(d);
				r = row + dRow[d];
				c = col + dCol[d];
				
				// 처음 방향으로 돌아왔다면 이동할 수 있는 공간이 없는 경우
				if(d == firstDirection) break;
			}
			
			// 이동할 수 있는 방향이 없다면 이동하지 않음
			if(!checkRange(r) || !checkRange(c) || (R == r && C == c)) continue;
			
			// 위치 교환
			id[row][col] = id[r][c];
			dir[row][col] = dir[r][c];
			id[r][c] = ID;
			dir[r][c] = d;
		}
	}
	
	// 비트마스크롤 물고기 번호 배열로 변환
	public static int[][] bitmaskToId(long bitmask) {
		int[][] id = new int[SIZE][SIZE];
		long checkBit = 0b1111;
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				// 번호는 1 ~ 16까지
				id[row][col] = (int) ((bitmask & checkBit) + 1);
				bitmask = bitmask >> 4;
			}
		}
		return id;
	}
	// 비트마스크를 물고기 방향 배열로 변환
	public static int[][] bitmaskToDir(long bitmask) {
		int[][] dir = new int[SIZE][SIZE];
		long checkBit = 0b111;
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				// 방향은 0 ~ 7까지
				dir[row][col] = (int) (bitmask & checkBit);
				bitmask = bitmask >> 3;
			}
		}
		return dir;
	}
	
	// 물고기 번호 배열을 비트마스크로 변환
	// 총 16개의 위치를 4개의 비트로 만들어야 하므로 long 자료형 사용
	public static long idToBitmask(int[][] id) {
		long bitmask = 0L;
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				bitmask |= getIdBit(row, col, id[row][col]);
			}
		}
		return bitmask;
	}
	// 물고가 방향 배열을 비트마스크로 변환
	public static long dirToBitmask(int[][] dir) {
		long bitmask = 0L;
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				bitmask |= getDirBit(row, col, dir[row][col]);
			}
		}
		return bitmask;
	}
	
	// 물고기 위치를 비트로 변환
	public static long getIdBit(int row, int col, int id) {
		long bitmask = id - 1;
		return bitmask << (row * 4 + col) * 4;
	}
	// 물고기 방향을 비트로 변환
	public static long getDirBit(int row, int col, int dir) {
		long bitmask = dir;
		return bitmask << (row * 4 + col) * 3;
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < SIZE;
	}

	// 현재 방향에서 45도 반시계 방향 회전한 방향을 리턴
	public static int counterClockwise(int dir) {
		return (dir + 1) % TOTALDIR;
	}
	
	// 디버깅용 배열 출력
	public static void printArr(int[][] arr) {
		for(int[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
	}
	public static void printState(int[][] id, int[][] dir) {
		printArr(id);
		System.out.println("--------------");
		printArr(dir);
		System.out.println("______________");
	}
}
