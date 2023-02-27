import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 2567번 색종이 - 2
 * 문제 분류: 완전 탐색
 * @author Giwon
 */
public class Main_2567 {
	// 흰색 천의 가로, 세로는 100
	public static final int MAX = 100;
	// 검은색 스카프의 가로, 세로는 10
	public static final int SIZE = 10;
	// boolean을 통해 검은 스카프가 위치한 상태 체크
	public static final boolean WHITE = false, BLACK = true;
	// 사방탐색 방향
	public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	public static final int[] dY = {0, 1, 0, -1};
	public static final int[] dX = {-1, 0, 1, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 흰색 천 (문제에서 제시된 그림과는 X축 기준으로 대칭인 형태)
		boolean[][] white = new boolean[MAX][MAX];
		
		// 검은 스카프의 수
		int N = Integer.parseInt(br.readLine().trim());
		// 검은 스카프 시작 점 입력
		int X, Y;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			X = Integer.parseInt(st.nextToken());
			Y = Integer.parseInt(st.nextToken());
			// 시작점부터 10x10 크기에 검은 색 스카프 표시
			for(int y = 0; y < SIZE; y++) {
				for(int x = 0; x < SIZE; x++) {
					white[y + Y][x + X] = BLACK;
				}
			}
		}
		
		int result = 0;
		// 검은 스카프 주변에 흰색 천이 있다면 둘레로 체크
		int count;
		for(int y = 0; y < MAX; y++) {
			for(int x = 0; x < MAX; x++) {
				// 검은색 천인 경우에만 둘레 계산
				if(white[y][x] == WHITE) continue;
				
				// 사방 탐색으로 주변에 위치한 검은색 천 개수 카운트
				count = 4;
				for(int dir = 0; dir < 4; dir++) {
					X = x + dX[dir];
					Y = y + dY[dir];
					
					// 주변에 위치한 검은 색 스카프 체크
					if(checkRange(X) && checkRange(Y) && white[Y][X] == BLACK) {
						count--;
					}
				}
				// 주변에 흰색 천이 있거나 천의 마지막 부분인 경우 둘레 계산에 포함
				result += count;
			}
		}
		
		System.out.println(result);
		br.close();
	}

	// 사방 탐색 범위 체크
	public static boolean checkRange(int target) {
		return target >= 0 && target < MAX;
	}
	
	// 디버깅용 배열 출력
	public static void printArr(boolean[][] arr) {
		for(boolean[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
	}
}
