import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 1202번 보석 도둑
 * 문제 분류: 이분 탐색, 그리디 알고리즘, Union Find
 * @author Giwon
 */
public class Main_1202 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 보석 개수. 1 <= N <= 300,000
		int N = Integer.parseInt(st.nextToken());
		// 가방 개수. 1 <= K <= 300,000
		int K = Integer.parseInt(st.nextToken());
		// 보석 입력
		Jewel[] jewels = new Jewel[N];
		int M, V;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			// 무게
			M = Integer.parseInt(st.nextToken());
			// 가격
			V = Integer.parseInt(st.nextToken());
			
			jewels[i] = new Jewel(M, V);
		}
		// 가방 무게 입력
		int[] bags = new int[K];
		int C;
		for(int i = 0; i < K; i++) {
			C = Integer.parseInt(br.readLine());
			bags[i] = C;
		}
		// 이분 탐색을 위해 정렬
		Arrays.sort(bags);
		// 가장 가치가 큰 보석부터 탐색
		Arrays.sort(jewels);
		
		long result = 0;
		// 가방 안에 보석 담기
		Disjoint[] stolen = new Disjoint[K];
		Jewel curr;
		int idx;
		for(int i = 0; i < N; i++) {
			curr = jewels[i];
			// 현재 보석의 무게를 담을 수 있는 최소 크기의 가방 탐색
			idx = Arrays.binarySearch(bags, curr.M);
			if(idx < 0) idx = -(idx + 1);
			
			// 담을 수 있는 가방이 없으면 포기
			if(idx >= K) continue;
			
			// 담을 수 있는 가방이 있는 경우 해당 가방은 사용 처리
			if(stolen[idx] == null) {
				result += curr.V;
				stolen[idx] = new Disjoint(idx);
				// 이전 Index가 이미 사용처리 됐다면 두 집합 합치기
				if(idx > 0 && stolen[idx - 1] != null) Disjoint.union(stolen[idx - 1], stolen[idx]);
				// 이후 Index가 이미 사용처리 됐다면 두 집합 합치기
				if(idx < K - 1 && stolen[idx + 1] != null) Disjoint.union(stolen[idx + 1], stolen[idx]);
				continue;
			}
			
			// 현재 Index가 이미 사용 중인 경우
			idx = stolen[idx].getNextIdx();
			// 담을 수 있는 가방이 없으면 포기
			if(idx >= K) continue;
			
			// 담을 수 있는 가방이 있는 경우 해당 가방은 사용 처리
			if(stolen[idx] == null) {
				result += curr.V;
				stolen[idx] = new Disjoint(idx);
				// 이전 Index가 이미 사용처리 됐다면 두 집합 합치기
				if(idx > 0 && stolen[idx - 1] != null) Disjoint.union(stolen[idx - 1], stolen[idx]);
				// 이후 Index가 이미 사용처리 됐다면 두 집합 합치기
				if(idx < K - 1 && stolen[idx + 1] != null) Disjoint.union(stolen[idx + 1], stolen[idx]);
			}
		}
		
		System.out.println(result);
		br.close();
	}
	
	// Union Find로 사용된 가방 체크하기 위한 클래스
	public static class Disjoint {
		Disjoint parent;
		int rightIdx, height;
		
		public Disjoint(int idx) {
			this.parent = null;
			this.rightIdx = idx;
			this.height = 0;
		}
		
		public int getNextIdx() {
			return this.getParent().rightIdx + 1;
		}
		
		// Union Find를 수행하기 위해 최상위 노드 탐색
		private Disjoint getParent() {
			if(parent == null) return this;
			return parent.getParent();
		}
		
		// 합집합
		public static void union(Disjoint A, Disjoint B) {
			// 무조건 두 값은 서로 다른 집합
			Disjoint parentA = A.getParent();
			Disjoint parentB = B.getParent();
			
			if(parentA.height >= parentB.height) {
				parentA.rightIdx = Math.max(parentA.rightIdx, parentB.rightIdx);
				// 두 집합의 높이가 같을 때 높이 증가
				if(parentA.height == parentB.height) parentA.height++;
				parentB.parent = parentA;
			} else {
				parentB.rightIdx = Math.max(parentA.rightIdx, parentB.rightIdx);
				parentA.parent = parentB;
			}
		}
	}

	public static class Jewel implements Comparable<Jewel>{
		// 무게, 가격
		int M, V;

		public Jewel(int M, int V) {
			this.M = M;
			this.V = V;
		}

		// 보석 가격을 기준으로 정렬 (내림차순)
		@Override
		public int compareTo(Jewel o) {
			return Integer.compare(o.V, this.V);
		}
	}
}
