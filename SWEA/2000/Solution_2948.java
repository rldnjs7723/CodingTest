import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * SWEA 2948번 문자열 교집합
 * 문제 분류: 집합, 해시
 * @author Giwon
 */
public class Solution_2948 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		
		int lenA, lenB;
		Set<String> setA = new HashSet<>(), setB = new HashSet<>();
		Set<String> intersection = new HashSet<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 첫 번째 집합 원소 문자열 크기
			lenA = Integer.parseInt(st.nextToken());
			// 두 번째 집합 원소 문자열 크기
			lenB = Integer.parseInt(st.nextToken());
			
			// 첫 번째 집합 입력
			st = new StringTokenizer(br.readLine());
			setA.clear();
			for(int i = 0; i < lenA; i++) {
				setA.add(st.nextToken());
			}
			
			// 두 번째 집합 입력
			st = new StringTokenizer(br.readLine());
			setB.clear();
			for(int i = 0; i < lenB; i++) {
				setB.add(st.nextToken());
			}
			
			// 교집합 탐색
			intersection.clear();
			for(String element: setA) {
				if(setB.contains(element)) intersection.add(element);
			}
			
			System.out.println("#" + test_case + " " + intersection.size());
		}
		br.close();
	}

}
