import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 23894번 합성함수와 쿼리 2
 * 문제 분류: 희소 배열, 분할 정복
 * @author Giwon
 */
public class Main_23894 {
	// 합성 최대값
	public static final int MAX = (int) 1E9;
	// log2(합성 최대값)
	public static final int SIZE = 29;
	public static int[] twoPow;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		twoPow = new int[SIZE + 1];
		twoPow[0] = 1;
		for(int i = 1; i <= SIZE; i++) {
			twoPow[i] = twoPow[i - 1] * 2;
		}
		
		// 수 범위
		final int N = Integer.parseInt(br.readLine().trim());
		// 초기화
		Function[] function = new Function[N + 1];
		for(int i = 1; i <= N; i++) {
			function[i] = new Function(i);
		}
		// 매핑 입력
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			function[i].mapping[0] = function[Integer.parseInt(st.nextToken())];
			if(i != 1 && function[i].mapping[0].x == 1) function[i].maxDepth = Math.min(function[i].maxDepth, 0);
		}
		
		// 2의 제곱번 합성 결과 계산
		for(int m = 1; m <= SIZE; m++) {
			for(int i = 1; i <= N; i++) {
				function[i].mapping[m] = function[i].mapping[m - 1].mapping[m - 1];
				// 유효한 범위 설정
				if(i != 1 && function[i].mapping[m].x == 1) function[i].maxDepth = Math.min(function[i].maxDepth, m);
				if(i != 1 && function[i].mapping[m - 1].maxDepth < m - 1) function[i].maxDepth = Math.min(function[i].maxDepth, m - 1);
			}
		}
		
		// 쿼리 입력
		final int Q = Integer.parseInt(br.readLine().trim());
		int type, m, x;
		for(int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			type = Integer.parseInt(st.nextToken());
			
			if(type == 1) {
				// 값 변경
				x = Integer.parseInt(st.nextToken());
				updateFunction(x, function);
			} else if(type == 2) {
				// 값 출력
				m = Integer.parseInt(st.nextToken());
				x = Integer.parseInt(st.nextToken());
				bw.write(function[x].getValue(m) + "\n");
			}
		}
		
		bw.close();
		br.close();
	}
	
	// f(1) 값 x로 변경
	public static void updateFunction(int x, Function[] function) {
		function[1].mapping[0] = function[x];
		for(int m = 1; m <= SIZE; m++) {
			function[1].mapping[m] = function[function[1].mapping[m - 1].getValue(1 << (m - 1))];
		}
	}

	public static int log2(int target) {
		int cnt = -1;
		while(target > 0) {
			target /= 2;
			cnt++;
		}
		
		return cnt;
	}
	
	public static class Function {
		// 현재 입력 값
		int x;
		// 2^i 번 합성했을 때의 입력값
		Function[] mapping;
		int maxDepth;
		
		public Function(int x) {
			this.x = x;
			this.mapping = new Function[SIZE + 1];
			this.maxDepth = SIZE;
		}
		
		public int getValue(int m) {
			Function curr = this;
			int log;
			while(m > 0) {
				log = Math.min(log2(m), curr.maxDepth);
				m -= twoPow[log];
				curr = curr.mapping[log];
			}
			
			return curr.x;
		}

		@Override
		public String toString() {
			return "Function [x=" + x + ", maxDepth=" + maxDepth + "]";
		}
		
		public void printMapping() {
			System.out.println(Arrays.toString(mapping));
		}
	}
}
