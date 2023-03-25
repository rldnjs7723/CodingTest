import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 16440 제이크와 케이크
 * 문제 분류: 슬라이딩 윈도우
 * @author Giwon
 */
public class Main_16440 {
	// 딸기의 개수 카운트
	public static final char COUNT = 's';
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int N = Integer.parseInt(br.readLine().trim());
		String input = br.readLine().trim();
		
		final int CAKE_SIZE = N / 2;
		final int S_MAX = N / 4;
		
		// 딸기 개수 카운트
		int strawberry = 0;
		for(int i = 0; i < CAKE_SIZE; i++) {
			if(input.charAt(i) == COUNT) strawberry++;
		}
		// 처음 절반만 자르면 되는 경우
		if(strawberry == S_MAX) {
			System.out.println(1);
			System.out.println(CAKE_SIZE);
			br.close();
			return;
		}
		
		// 슬라이딩 윈도우로 하나씩 이동
		int idx = 0;
		for(int i = 0; i < N - CAKE_SIZE; i++) {
			if(input.charAt(i) == COUNT) strawberry--;
			if(input.charAt(i + CAKE_SIZE) == COUNT) strawberry++;
			
			if(strawberry == S_MAX) idx = i + 1;
		}
		
		System.out.println(2);
		System.out.println(idx + " " + (idx + CAKE_SIZE));
		br.close();
	}

}
