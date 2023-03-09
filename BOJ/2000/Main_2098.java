import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 2098번 외판원 순회
 * 문제 분류: 다이나믹 프로그래밍, 비트마스킹
 * @author Giwon
 */
public class Main_2098 {
	public static final int INF = Integer.MAX_VALUE;
	// 도시 개수
	public static int N;
	// 비트마스크 수
	public static int SIZE;
	// 비트마스크 완료
	public static int FULL;
	// 도시 이동 비용
	public static int[][] W;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 도시 개수
		N = Integer.parseInt(br.readLine());
		// i에서 j로 가기 위한 비용 입력
		W = new int[N][N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
				// 갈 수 없는 곳은 비용 무한
				if(W[i][j] == 0) W[i][j] = INF;
			}
		}
		FULL = (1 << N) - 1;
		
		// 현재 위치 i일 때 남은 점들을 최적으로 돌았을 때의 이동 거리
		SIZE = (int) Math.pow(2, N);
		int[][] tsp = new int[N][SIZE];
		for(int i = 0; i < N; i++) {
			Arrays.fill(tsp[i], INF);
		}
		
		
		
		
		br.close();
	}
	
	public static void dfs(int curr, int bitmask) {
		// 모든 위치를 방문한 경우
		if(bitmask == FULL) {
			
		}
		
		
	}
	
}
