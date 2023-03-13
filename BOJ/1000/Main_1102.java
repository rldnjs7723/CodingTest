import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 1102번 발전소
 * 문제 분류: 다이나믹 프로그래밍, 비트마스킹
 * @author SSAFY
 */
public class Main_1102 {
	public static final int INF = Integer.MAX_VALUE / 2 - 1;
	// 발전소 i를 이용하여 발전소 j를 재시작할 때 드는 비용 = COST[i][j]
	public static int[][] COST;
	// 발전소 개수
	public static int N;
	// 비트마스크 수
	public static int SIZE;
	// 켜야 하는 발전소 최소 개수
	public static int P;
	// 발전소 i에서 시작하여 남아있는 발전소를 켜는 최소 비용 = TSP[i][bitmask]
	public static int[][] TSP;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 발전소 개수 입력
		N = Integer.parseInt(br.readLine().trim());
		// 비트마스크 수
		SIZE = (int) Math.pow(2, N);
		
		// 비용 입력
		COST = new int[N][N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				COST[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 비트마스크 입력
		int bitmask = 0;
		String input = br.readLine().trim();
		for(int i = 0; i < N; i++) {
			if(input.charAt(i) == 'Y') bitmask |= (1 << i);
		}
		
		// 켜야 하는 발전소 최소 개수
		P = Integer.parseInt(br.readLine().trim());
		
		// 이미 켜져 있는 발전소의 개수가 더 많다면 비용 0
		if(checkCount(bitmask) >= P) {
			System.out.println(0);
			br.close();
			return;
		}
		
		// 시작 비용 무한으로 초기화
		TSP = new int[N][SIZE];
		for(int i = 0; i < N; i++) {
			Arrays.fill(TSP[i], INF);
		}
		
		int min = INF;
		for(int i = 0; i < N; i++) {
			if((bitmask & (1 << i)) > 0) {
				// 켜져 있는 발전소에서 출발
				doTSP(i, bitmask, 1 << i);
				min = Math.min(min, TSP[i][bitmask]);
			}
		}
		
//		printArr(TSP);
		System.out.println(min == INF ? -1 : min);
		br.close();
	}

	// 현재 켜져 있는 발전소 개수 리턴
	public static int checkCount(int bitmask) {
		int count = 0;
		for(int i = 0; i < N; i++) {
			if((bitmask & 1) > 0) count++;
			bitmask = bitmask >> 1;
		}
		
		return count;
	}
	
	// TSP 문제 풀이
	public static int doTSP(int curr, int bitmask, int visited) {
		// 켜야 하는 발전소 수를 달성했다면 0리턴
		if(checkCount(bitmask) >= P) {
			TSP[curr][bitmask] = 0;
			// 발전소를 더 켤 필요가 없음
			return TSP[curr][bitmask];
		}
		
		// 이미 탐색했었다면 결과 리턴
		if(TSP[curr][bitmask] != INF) return TSP[curr][bitmask];
		
		int next, nextVisit;
		for(int i = 0; i < N; i++) {
			next = bitmask | (1 << i);
			nextVisit = visited | (1 << i);
			// 이미 방문한 발전소는 생략
			if(nextVisit == visited) continue;
			
			// 현재 발전소에서 나머지 발전소를 켜는 최소 비용 갱신
			if(next == bitmask) {
				// 이미 켜져 있는 발전소는 비용 없음
				TSP[curr][bitmask] = Math.min(TSP[curr][bitmask], doTSP(i, next, nextVisit));
			} else {
				TSP[curr][bitmask] = Math.min(TSP[curr][bitmask], doTSP(i, next, nextVisit) + COST[curr][i]);
			}
			
			// 현재 발전소로 되돌아 오는 경우 체크
			TSP[curr][bitmask] = Math.min(TSP[curr][bitmask], doTSP(curr, next, visited) + COST[curr][i]);
		}
		
		return TSP[curr][bitmask];
	}
	
	public static void printArr(int[][] arr) {
		for(int[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
	}
	
}
