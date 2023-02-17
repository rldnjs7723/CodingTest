import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 백준 20366번 같이 눈사람 만들래?
 * 문제 분류: 슬라이딩 윈도우, Next Permutation (조합 응용)
 * @author GIWON
 */
public class Main_20366 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		int[] snow = new int[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			snow[i] = Integer.parseInt(st.nextToken());
		}
		
		// 조합을 위해 두 개의 값만 1로 설정
		int[] combination = new int[N];
		for(int i = 0; i < 2; i++) {
			combination[N - 1 - i] = 1;
		}
		
		HashMap<Integer, SnowMan> snowMan = new HashMap<>();
		int[] curr;
		int height;
		do {
			// 계산된 조합 추출
			curr = findOne(combination);
			// 조합대로 눈사람을 만들었을 때 높이
			height = snow[curr[0]] + snow[curr[1]];
			if(snowMan.containsKey(height)) {
				// 이미 동일한 키의 눈사람이 존재한다면 중복된 눈덩이를 썼는지 체크
				snowMan.get(height).add(curr[0], curr[1]);
				// 동일한 키를 가진 눈 사람을 만들 수 있다면 차이의 최솟값은 0
				if(snowMan.get(height).count == 2) {
					System.out.println(0);
					return;
				}
			} else {
				// 눈사람 높이 추가
				snowMan.put(height, new SnowMan(curr[0], curr[1], snow));
			}
			
		} while (nextPermutation(combination));
		
		// 높이 별 눈사람 정렬
		SnowMan[] heights = new SnowMan[snowMan.size()];
		heights = snowMan.values().toArray(heights);
		Arrays.sort(heights);
		
		// 높이가 같은 눈사람이 있었다면 이전에 0을 출력했을 것
		int min = Integer.MAX_VALUE;
		// 너비가 2인 슬라이딩 윈도우로 높이 차이가 가장 작은 조합 탐색
		for(int i = 0; i < heights.length - 1; i++) {
			min = Math.min(min, heights[i + 1].height - heights[i].height);
		}
		System.out.println(min);
		
		br.close();
	}
	
	// 배열 안에서 1의 개수가 적힌 위치를 반환
	public static int[] findOne(int[] arr) {
		int size = 0;
		int[] result = new int[2];
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == 1) {
				result[size++] = i;
				if(size == 2) return result;
			}
		}
		return result;
	}
	
	// Next Permutation으로 조합 계산
	// N <= 600 이므로 nC2가 크게 문제되지 않을 것이라 판단
	public static boolean nextPermutation(int[] input) {
		int n = input.length;
		
		// i 계산
		int i = n - 1;
		for(i = n - 1; i >= 1; i--) {
			if(input[i - 1] < input[i]) break;
		}
		// 순열 맨 뒤의 값이면 마지막
		if(i == 0) return false;
		
		// 교환 대상 탐색
		int temp = input[i - 1];
		int j = n - 1;
		for(j = n - 1; j >= 0; j--) {
			if(temp < input[j]) break;
		}
		
		// 위치 교환
		input[i - 1] = input[j];
		input[j] = temp;
		
		// i부터 다시 정렬
		Arrays.sort(input, i, n);
		return true;
	}
	
	public static class SnowMan implements Comparable<SnowMan> {
		int height, count, idx1, idx2;
		
		// 처음으로 현재 높이의 눈사람을 만들 때 사용했던 index 설정
		public SnowMan(int idx1, int idx2, int[] snow) {
			this.height = snow[idx1] + snow[idx2];
			this.count = 1;
			this.idx1 = idx1;
			this.idx2 = idx2;
		}
		
		public void add(int idx1, int idx2) {
			// 이미 같은 높이의 눈 사람을 2개 만들 수 있다면 고려할 필요 없음
			if(count > 1) return;
			
			// 같은 눈 덩이를 골랐다면 만들 수 없음
			if(this.idx1 == idx1) return;
			if(this.idx1 == idx2) return;
			if(this.idx2 == idx1) return;
			if(this.idx2 == idx2) return;
			
			// 다른 눈 덩이로 만들었다면 높이가 중복되어도 됨
			count++;
		}
		
		// 눈사람을 높이 순으로 정렬
		@Override
		public int compareTo(SnowMan obj) {
			return Integer.compare(this.height, obj.height);
		}
		
		@Override
		public String toString() {
			return Integer.toString(height);
		}
	}
	
}
