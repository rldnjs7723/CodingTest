import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * SWEA 7701번 염라대왕의 이름 정렬
 * 문제 분류: 집합 (TreeSet), 구현 (Comparator)
 * @author Giwon
 */
public class Solution_7701 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		final int T = Integer.parseInt(br.readLine().trim());
		
		int N;
		String input;
		// TreeSet에 새로 정의한 String Comparator 지정
		Set<String> nameList = new TreeSet<>(new StringComparator());
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine().trim());
			nameList.clear();
			
			// TreeSet에 넣어 자동 정렬
			for(int i = 0; i < N; i++) {
				input = br.readLine().trim();
				nameList.add(input);
			}
			
			bw.write("#" + test_case + "\n");
			for(String name: nameList) {
				bw.write(name + "\n");
			}
		}
		
		bw.close();
		br.close();
	}

	public static class StringComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			if(o1.length() != o2.length()) {
				return Integer.compare(o1.length(), o2.length());
			}
			
			return o1.compareTo(o2);
		}
		
	}
}
