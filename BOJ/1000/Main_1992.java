

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 1992번 쿼드트리
 * 문제 분류: 분할 정복
 * @author Giwon
 */
public class Main_1992 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		String input;
		int[][] data = new int[N][N];
		for(int row = 0; row < N; row++) {
			input = br.readLine().trim();
			for(int col = 0; col < N; col++) {
				data[row][col] = input.charAt(col) - '0';
			}
		}
		
		System.out.println(compress(data, N, 0, 0));
		br.close();
	}
	
	/**
	 * 분할 정복을 통해 각각의 영역을 압축
	 * @param data		영상 배열
	 * @param size		분할된 영상 크기
	 * @param r			시작점 행
	 * @param c			시작점 열
	 * @return				압축한 문자열
	 */
	public static String compress(int[][] data, int size, int r, int c) {
		StringBuilder sb = new StringBuilder();
		
		// 전체 1과 0의 개수 세기
		int zero = 0, one = 0;
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				if(data[r + row][c + col] == 0) zero++;
				else one++;
			}
		}
		
		int pow = size * size;
		int half = size / 2;
		// 모든 수가 0이면 0
		if(zero == pow) return "0";
		// 모든 수가 1이면 1
		else if(one == pow) return "1";
		else {
			sb.append("(");
			// z 순서대로 합치기
			sb.append(compress(data, half, r, c));
			sb.append(compress(data, half, r, c + half));
			sb.append(compress(data, half, r + half, c));
			sb.append(compress(data, half, r + half, c + half));
			sb.append(")");
			return sb.toString();
		}
	}

}
