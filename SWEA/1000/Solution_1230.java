import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 1230번 암호문3
 * 문제 분류: 리스트, 구현
 * @author Giwon
 */
public class Solution_1230 {
	public static final int T = 10;
	public static final int I = 0, D = 1, A = 2;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N, M, type, x, y, s;
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		List<Integer> list = new ArrayList<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			list.clear();
			// 원본 암호문 길이
			N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			// 원본 암호문 입력
			for(int i = 0; i < N; i++) {
				list.add(Integer.parseInt(st.nextToken()));
			}
			// 명령어의 개수
			M = Integer.parseInt(br.readLine());
			
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < M; i++) {
				type = parseType(st.nextToken());
				
				if(type == I) {
					// 삽입
					x = Integer.parseInt(st.nextToken()) - 1;
					y = Integer.parseInt(st.nextToken());
					
					for(int k = 1; k <= y; k++) {
						s = Integer.parseInt(st.nextToken());
						list.add(x + k, s);
					}
				} else if(type == D) {
					// 삭제
					x = Integer.parseInt(st.nextToken()) - 1;
					y = Integer.parseInt(st.nextToken());
					
					for(int k = 0; k < y; k++) {
						list.remove(x + 1);
					}
				} else if(type == A) {
					// 추가
					y = Integer.parseInt(st.nextToken());
					
					for(int k = 0; k < y; k++) {
						s = Integer.parseInt(st.nextToken());
						list.add(s);
					}
				}
			}
			
			sb.setLength(0);
			sb.append("#" + test_case);
			for(int i = 0; i < 10; i++) {
				sb.append(" " + list.get(i));
			}
			sb.append("\n");
			
			bw.write(sb.toString());
		}
		
		bw.close();
		br.close();
	}

	public static int parseType(String input) {
		switch (input) {
			case "I":
				return I;
			case "D":
				return D;
			case "A":
				return A;
			default:
				return -1;
		}
	}
}
