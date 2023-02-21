import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 13501번 수열 편집
 * 문제 분류: 리스트, 구현
 * @author Giwon
 */
public class Solution_13501 {
	public static final int I = 0, D = 1, C = 2;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int T = Integer.parseInt(br.readLine());
		int N, M, L, type, a, b;
		StringTokenizer st;
		List<Integer> list = new ArrayList<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			list.clear();
			st = new StringTokenizer(br.readLine());
			// 수열의 길이
			N = Integer.parseInt(st.nextToken());
			// 추가 횟수
			M = Integer.parseInt(st.nextToken());
			// 출력할 인덱스 번호
			L = Integer.parseInt(st.nextToken());
			
			// 수열 입력
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i++) {
				list.add(Integer.parseInt(st.nextToken()));
			}
			// 명령어 수행
			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				type = parseType(st.nextToken());
				
				if(type == I) {
					// 삽입
					a = Integer.parseInt(st.nextToken());
					b = Integer.parseInt(st.nextToken());
					
					list.add(a, b);
				} else if(type == D) {
					// 제거
					a = Integer.parseInt(st.nextToken());
					
					list.remove(a);
				} else if(type == C) {
					// 수정
					a = Integer.parseInt(st.nextToken());
					b = Integer.parseInt(st.nextToken());
					
					list.set(a, b);
				}
			}
			
			int result = list.size() <= L ? -1 : list.get(L);
			System.out.println("#" + test_case + " " + result);
		}
		
		br.close();
	}

	// 명령어 파싱
	public static int parseType(String input) {
		switch (input) {
			case "I":
				return I; 
			case "D":
				return D; 
			case "C":
				return C; 
			default:
				return -1;
		}
	}
}
