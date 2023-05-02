import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 9318번 위성 사진
 * 문제 분류: 좌표 압축
 * @author Giwon
 */
public class Main_9318 {
	public static final int X = 0, Y = 1;
	public static final int MAX = 1000000;
	public static final int SIZE = 10;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, x1, y1, x2, y2;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 사진 개수
			N = Integer.parseInt(br.readLine().trim());
			// 사진 입력
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				x1 = Integer.parseInt(st.nextToken());
				y1 = Integer.parseInt(st.nextToken());
				x2 = Integer.parseInt(st.nextToken());
				y2 = Integer.parseInt(st.nextToken());
				
				Picture picture = new Picture(x1, y1, x2, y2);
			}
			
			
			
			
		}
		
		
		br.close();
	}
	
	public static class Picture {
		// 방향별 꼭짓점 좌표
		int[] SW, SE, NW, NE;
		
		public Picture(int x1, int y1, int x2, int y2) {
			this.SW = new int[] {x1, y1};
			this.NE = new int[] {x2, y2};
			this.SE = new int[] {x2, y1};
			this.NW = new int[] {x1, y2};	
		}
		
	}

	// 10 x 10 영역
	public static class Region {
		// 현재 영역의 크기
		int size;
		// 하위 영역 10 x 10
		Region[][] inner;
		
	}
	
	
	public static class Earth {
		
	}
	
}
