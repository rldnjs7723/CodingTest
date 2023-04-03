import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 1263번 사람 네트워크2
 * 문제 분류: Floyd-Warshall 알고리즘
 * @author Giwon
 */
public class Solution_1263 {
	public static final int INF = Integer.MAX_VALUE / 2 - 1;

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine());
		int N;
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 사람 수
			N = Integer.parseInt(st.nextToken());
			
			// 인접 행렬 입력
			int[][] adjMatrix = new int[N][N];
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					adjMatrix[i][j] = Integer.parseInt(st.nextToken());
					if(i != j && adjMatrix[i][j] == 0) adjMatrix[i][j] = INF; 
				}
			}
			
			// 플로이드-워셜 알고리즘 적용
			floydWarshall(N, adjMatrix);
			
			int sum, min = INF;
			for(int[] inner: adjMatrix) {
				sum = 0;
				for(int val: inner) {
					sum += val;
				}
				
				min = Math.min(min, sum);
			}
			
			System.out.println("#" + test_case + " " + min);
		}
		br.close();
	}

	public static void floydWarshall(int N, int[][] adjMatrix) {
		for(int k = 0; k < N; k++) {
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					// i에서 k를 거쳐 j로 가는 경로 갱신
					if(adjMatrix[i][j] > adjMatrix[i][k] + adjMatrix[k][j]) {
						adjMatrix[i][j] = adjMatrix[i][k] + adjMatrix[k][j];
					}
				}
			}
		}
	}
	
}
