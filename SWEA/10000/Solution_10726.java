import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 10726번 이진수 표현
 * 문제 분류: 이진수 표현
 * @author Giwon
 */
public class Solution_10726 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int TC = Integer.parseInt(br.readLine());
		StringTokenizer st;
		int N, M;
		String binary;
		boolean check;
		for(int test_case = 1; test_case <= TC; test_case++) {
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			// 정수 M을 이진수로 표현
			binary = Integer.toBinaryString(M);
			
			check = true;
			if(binary.length() < N) {
				check = false;
			} else {
				for(int i = 0; i < N; i++) {
					// 이진수 맨 뒤부터 N개의 비트 확인
					if((binary.charAt(binary.length() - 1 - i) - '0') == 0) {
						check = false;
						break;
					}
				}
			}
			
			
			System.out.println("#" + test_case + " " + (check ? "ON" : "OFF"));
		}
		
		br.close();
	}

}
