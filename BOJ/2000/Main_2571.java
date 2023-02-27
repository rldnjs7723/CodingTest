import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 2571번 색종이 - 3
 * 문제 분류: 완전 탐색
 * @author Giwon
 */
public class Main_2571 {
	// 흰색 도화지 크기는 100, 검은색 종이 크기는 10
	public static final int N = 100, SIZE = 10;
	public static final boolean WHITE = false, BLACK = true;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean[][] paper = new boolean[N][N];
		// 검은색 종이 입력
		StringTokenizer st;
		int K = Integer.parseInt(br.readLine().trim());
		int R, C;
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			// 열 위치
			C = Integer.parseInt(st.nextToken());
			// 행 위치
			R = Integer.parseInt(st.nextToken());
			
			// 색종이가 도화지 밖으로 나가는 경우는 없다.
			for(int row = 0; row < SIZE; row++) {
				for(int col = 0; col < SIZE; col++) {
					paper[R + row][C + col] = BLACK;
				}
			}
		}
		
		// 최대 검은색 직사각형 넓이 탐색
		int max = 0;
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				// 현재 위치가 검은색 종이인 경우
				if(paper[row][col] == BLACK) {
					max = Math.max(max, maxArea(row, col, paper));
				}
			}
		}
		
		System.out.println(max);
		br.close();
	}

	// 현재 행, 열을 왼쪽 위 꼭짓점으로 두는 직사각형 넓이의 최댓값을 구하는 메서드
	public static int maxArea(int row, int col, boolean[][] paper) {
		int width, height, max = 0;
		int R, C, i;
		
		// 높이 탐색 후 너비 탐색
		R = row + 1;
		while(checkRange(R) && paper[R][col] == BLACK) {
			R++;
		}
		height = R - row;
		
		// 너비를 조금씩 줄여가며 탐색
		i = height;
		C = col + 1;
		while(i != 0 && checkRange(C)) {
			while(checkRange(C)) {
				// 다음 열에 해당하는 영역이 전부 검은색 종이인지 확인
				i = 0;
				for(i = 0; i < height; i++) {
					// 하얀색 종이라면 break;
					if(paper[row + i][C] == WHITE) break;
				}
				if(i == height) C++;
				else break;
			}
			width = C - col;
			max = Math.max(max, height * width);
			height = i;
		}
		
		return max;
	}
	
	// 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < N;
	}
	
	// 도화지 출력
	public static void printArr(boolean[][] paper) {
		StringBuilder sb = new StringBuilder();
		for(boolean[] inner: paper) {
			for(boolean val: inner) {
				sb.append(val ? "■" : "□");
			}
			sb.append("\n");
		}
		System.out.print(sb.toString());
	}
}
