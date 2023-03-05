import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 19539번 사과나무
 * 문제 분류: 그리디 알고리즘
 * @author Giwon
 */
public class Main_19539 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 사과나무 개수
		int N = Integer.parseInt(br.readLine());
		// 목표 나무 높이 입력
		st = new StringTokenizer(br.readLine());
		int h, oneCount = 0, twoCount = 0;
		for(int i = 0; i < N; i++) {
			h = Integer.parseInt(st.nextToken());
			
			// 1만큼 높일 횟수
			oneCount += h % 2;
			// 2만큼 높일 횟수
			twoCount += h / 2;
		}
		
		// 1만큼 성장시켜야 하는 나무 전부 성장시키기
		twoCount -= oneCount;
		// 2만큼 성장시켜야 하는 나무의 개수가 1만큼 성장시켜야 하는 나무 개수보다 작으면 불가능
		if(twoCount < 0) twoCount = 1;
		// 6만큼 성장시켜야 하는 경우 전부 성장시키기
		twoCount %= 3;
		
		// 성장시켜야 하는 나무가 더 있다면 실패
		System.out.println(twoCount == 0 ? "YES" : "NO");
		br.close();
	}
}
