import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 백준 17471번 게리맨더링
 * 문제 분류: Union Find, 완전 탐색, 그래프 탐색, 비트마스킹
 * @author Giwon
 */
public class Main_17471 {
	public static final int A = 1, B = 2, NULL = Integer.MAX_VALUE;
	public static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 구역의 개수
		N = Integer.parseInt(br.readLine());
		// 인구 수 입력
		int[] population = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			population[i] = Integer.parseInt(st.nextToken());
		}
		
		// 인접한 구역 입력
		Ward[] adjacent = new Ward[N];
		int size;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			adjacent[i] = new Ward();
			
			size = Integer.parseInt(st.nextToken());
			for(int j = 0; j < size; j++) {
				adjacent[i].add(Integer.parseInt(st.nextToken()) - 1);
			}
		}
		
		// 모든 구역 포함 / 미포함 완전 탐색으로 수행하며 인구 수 차이 계산
		int result = subSet(0, 0, 0, population, adjacent);
		System.out.println(result == NULL ? -1 : result);
		br.close();
	}
	
	// N <= 10에서, 2^N 최대 1024개의 경우의 수이므로 부분조합 완전탐색 수행
	public static int subSet(int curr, int bitmaskA, int bitmaskB, int[] population, Ward[] adjacent) {
		// 모든 구역을 선택한 경우 인구 수 차이 계산
		if(curr == N) return getPopulation(bitmaskA, bitmaskB, population, adjacent);
		
		int min = NULL;
		// 현재 Index의 구역을 A에 포함하기
		min = Math.min(min, subSet(curr + 1, bitmaskA | (1 << curr), bitmaskB, population, adjacent));
		// 현재 Index의 구역을 B에 포함하기
		min = Math.min(min, subSet(curr + 1, bitmaskA, bitmaskB | (1 << curr), population, adjacent));
		return min;
	}
	
	/**
	 * 두 선거구가 비트마스크로 주어졌을 때 선거구의 유효성 검사와 인구수 차이를 리턴
	 * @param bitmaskA		A 선거구 구역 비트마스크
	 * @param bitmaskB		B 선거구 구역 비트마스크
	 * @param population		각 구역별 인구 수 배열
	 * @param adjacent		각 구역별 인접한 구역 리스트
	 * @return						불가능한 방법인 경우 Integer.MAX_VALUE 리턴, 가능한 방법이면 인구수 차이 리턴
	 */
	public static int getPopulation(int bitmaskA, int bitmaskB, int[] population, Ward[] adjacent) {
		// 둘 중 하나의 선거구만 존재하면 불가능한 방법
		if(bitmaskA == 0 || bitmaskB == 0) return NULL;
		
		// 선거구 번호 기록
		int[] wardNum = new int[N];
		// 비트마스크에 해당하는 두 개의 선거구 결정
		for(int i = 0; i < N; i++) {
			if((bitmaskA & 1) > 0) wardNum[i] = A;
			bitmaskA = bitmaskA >> 1;
		}
		for(int i = 0; i < N; i++) {
			if((bitmaskB & 1) > 0) wardNum[i] = B;
			bitmaskB = bitmaskB >> 1;
		}
		
		DisjointSet[] set = new DisjointSet[N];
		Queue<Integer> queue = new ArrayDeque<>();
		int curr;
		for(int i = 0; i < N; i++) {
			if(set[i] == null) {
				set[i] = new DisjointSet(wardNum[i]);
				
				// BFS로 인접한 동일한 선거구와 연결
				queue.clear();
				queue.offer(i);
				while(!queue.isEmpty()) {
					curr = queue.poll();
					
					for(int next: adjacent[curr]) {
						if(set[next] == null && wardNum[next] == wardNum[i]) {
							set[next] = set[i];
							queue.offer(next);
						}
					}
				}	
			}
		}
		
		// 선거구 개수가 2개인지 확인
		Set<DisjointSet> duplicate = new HashSet<>();
		for(int i = 0; i < N; i++) {
			duplicate.add(set[i]);
		}
		// 선거구 개수가 2개가 아니라면 불가능한 방법
		if(duplicate.size() != 2) return NULL;
		
		// 선거구 개수가 2개라면 두 선거구의 인구수 차이를 계산
		int populationA = 0, populationB = 0;
		for(int i = 0; i < N; i++) {
			if(wardNum[i] == A) populationA += population[i];
			else populationB += population[i];
		}
		
		return Math.abs(populationA - populationB);
	}
	
	// 선거구 정보
	@SuppressWarnings("serial")
	public static class Ward extends ArrayList<Integer>{}
	
	// 선거구 집합
	public static class DisjointSet {
		int ward;
		
		public DisjointSet(int ward) {
			this.ward = ward;
		}
	}
}
