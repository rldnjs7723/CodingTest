import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 14510번 나무 높이
 * 문제 분류: 그리디 알고리즘
 * @author Giwon
 */
public class SWEA_14510 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st;
		final int T = Integer.parseInt(br.readLine());
		int N, max;
		int[] tree;
		List<Integer> toWater = new ArrayList<Integer>();
		for(int test_case = 1; test_case <= T; test_case++) {
			// 나무 개수
			N = Integer.parseInt(br.readLine());
			// 나무 높이 입력
			max = 0;
			st = new StringTokenizer(br.readLine());
			tree = new int[N];
			for(int i = 0; i < N; i++) {
				tree[i] = Integer.parseInt(st.nextToken());
				// 최대 나무 높이 계산
				max = Math.max(max, tree[i]);
			}
			
			// 더 늘려야 할 높이 설정
			toWater.clear();
			for(int i = 0; i < N; i++) {
				if(tree[i] < max) toWater.add(max - tree[i]);
			}
			
			int count = 0;
			while(!toWater.isEmpty()) {
				System.out.println(toWater);
				
				if(count % 2 == 0) {
					// 홀수 날에는 키가 1 자람
					// 홀수에 우선적으로 물을 주기
					int i = 0;
					for(i = 0; i < toWater.size(); i++) {
						if(toWater.get(i) % 2 == 1) {
							toWater.set(i, toWater.get(i) - 1);
							if(toWater.get(i) == 0) toWater.remove(i);
							break;
						}
					}
					// 홀수가 없다면 짝수에 물 주기
					if(i == toWater.size()) {
						if(toWater.size() > 1) {
							toWater.set(1, toWater.get(1) - 1);
							if(toWater.get(1) == 0) toWater.remove(1);
						}
					}
					count++;
				} else {
					// 짝수 날에는 키가 2 자람
					// 전부 1이 아니라면 물을 주기 (확인하는 과정 시간 복잡도 문제 가능성)
					for(int i = 0; i < toWater.size(); i++) {
						if(toWater.get(i) > 1) {
							toWater.set(i, toWater.get(i) - 2);
							if(toWater.get(i) == 0) toWater.remove(i);
							break;
						}
					}
					count++;
				}
			}
			
			System.out.println("#" + test_case + " " + count);
		}
		br.close();
	}

}
