import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 17136번 색종이 붙이기
 * 문제 분류: 백트래킹
 * @author Giwon
 */
public class Main_17136 {
	public static final int INF = Integer.MAX_VALUE;
	// 색종이 크기
	public static final int N = 10;
	public static int min = INF;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int[][] paper = new int[N][N];
		// 종이 상태 입력
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				paper[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 색종이 크기별 붙일 수 있는 좌표 탐색
		AvailableLocation[] available = new AvailableLocation[6];
		for(int i = 1; i <= 5; i++) {
			available[i] = new AvailableLocation();
		}
		
		for(int size = 1; size <= 5; size++) {
			for(int row = 0; row <= N - size; row++) {
				for(int col = 0; col <= N - size; col++) {
					if(checkAvailable(row, col, size, paper)) available[size].add(new Coord(row, col));
				}
			}
		}
		
		// 각 크기별 색종이 5개
		int[] remainCount = new int[6];
		Arrays.fill(remainCount, 5);
		
		coverPaper(5, -1, 0, remainCount, available, paper);
		int result = min;
		if(result == INF) result = -1;
		System.out.println(result);
		br.close();
	}
	
	// 모든 좌표 둘러보면서 색종이 붙이기 / 붙이지 않기 선택
	public static void coverPaper(int prevSize, int prevIdx, int totalCount, int[] remainCount, AvailableLocation[] available, int[][] paper) {
		int currSize = prevSize;
		int currIdx = prevIdx + 1;
		// 붙일 수 있는 색종이 크기 탐색
		while(currSize > 1 && available[currSize].size() <= currIdx) {
			currSize = currSize - 1;
			currIdx = 0;
		}
		
		// 마지막 색종이 크기인 경우
		if(currSize == 1) {
			int one = countOne(paper);
			if(one <= 5) {
				min = Math.min(min, totalCount + one);
			}
			return;
		}
		
		// 남아 있는 색종이로 1을 전부 제거할 수 없다면 리턴
		if(countOne(paper) > removableCount(remainCount, currSize)) return;
		
		Coord curr;
		while(currIdx < available[currSize].size() && remainCount[currSize] > 0) {
			curr = available[currSize].get(currIdx);
			// 현재 좌표에 색종이 붙이기
			if(checkAvailable(curr.row, curr.col, currSize, paper)) {
				// 최소 색종이 수보다 더 붙여야 한다면 리턴
				if(totalCount >= min) return;
				
				attach(curr.row, curr.col, currSize, paper);
				// 남은 색종이 수 줄이기
				remainCount[currSize]--;
				// 재귀적으로 탐색
				coverPaper(currSize, currIdx, totalCount + 1, remainCount, available, paper);
				// 붙이지 않은 상태로 롤백
				detach(curr.row, curr.col, currSize, paper);
				remainCount[currSize]++;
			}
			
			currIdx++;
		}
		
		// 현재 크기 아무것도 붙이지 않은 상태에서 다음 단계 탐색
		coverPaper(currSize - 1, -1, totalCount, remainCount, available, paper);
	}
	
	// 현재 남아있는 색종이로 제거할 수 있는 1의 개수 카운트
	public static int removableCount(int[] remainCount, int maxSize) {
		int count = 0;
		for(int size = maxSize; size >= 1; size--) {
			count += remainCount[size] * size * size;
		}
		
		return count;
	}
	
	
	// 남아 있는 1 개수 세기
	public static int countOne(int[][] paper) {
		int count = 0;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				if(paper[row][col] == 1) count++;
			}
		}
		
		return count;
	}
	
	/**
	 * 현재 좌표에 색종이를 붙일 수 있는지 확인
	 * @param row		색종이를 붙일 왼쪽 위 꼭짓점 좌표 행
	 * @param col		색종이를 붙일 왼쪽 위 꼭짓점 좌표 열
	 * @param size		붙일 색종이 크기
	 * @param paper		종이 상태
	 * @return			색종이 붙일 수 있는지 여부
	 */
	public static boolean checkAvailable(int row, int col, int size, int[][] paper) {
		// 색종이를 붙일 영역 체크
		for(int dR = 0; dR < size; dR++) {
			for(int dC = 0; dC < size; dC++) {
				// 값이 0이라면 색종이를 붙일 수 없음
				if(paper[row + dR][col + dC] == 0) return false;
			}
		}
		
		return true;
	}

	// 현재 좌표에 색종이 붙이기
	public static void attach(int row, int col, int size, int[][] paper) {
		for(int dR = 0; dR < size; dR++) {
			for(int dC = 0; dC < size; dC++) {
				paper[row + dR][col + dC] = 0;
			}
		}
	}
	
	// 현재 좌표에서 색종이 떼기
	public static void detach(int row, int col, int size, int[][] paper) {
		for(int dR = 0; dR < size; dR++) {
			for(int dC = 0; dC < size; dC++) {
				paper[row + dR][col + dC] = 1;
			}
		}
	}
	
	public static class Coord {
		int row, col;
		
		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	@SuppressWarnings("serial")
	public static class AvailableLocation extends ArrayList<Coord> {}

}
