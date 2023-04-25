import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 백준 18870번 좌표 압축
 * 문제 분류: 정렬, 자료구조(TreeSet, Map)
 * @author Giwon
 */
public class Main_18870 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		// 좌표 수
		final int N = Integer.parseInt(br.readLine().trim());
		// 좌표 입력
		int[] coordArr = new int[N];
		Set<Integer> coords = new TreeSet<>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			coordArr[i] = Integer.parseInt(st.nextToken());
			coords.add(coordArr[i]);
		}
		
		Map<Integer, Integer> coordMap = new HashMap<>();
		int idx = 0;
		for(int val: coords) {
			if(!coordMap.containsKey(val)) coordMap.put(val, idx++);
		}
		
		for(int val: coordArr) {
			bw.write(coordMap.get(val) + " ");
		}
		
		bw.close();
		br.close();
	}

}
