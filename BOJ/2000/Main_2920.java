import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 2920번 음계
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_2920 {
	public static final int MAX = 8;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int[] num = new int[MAX];
		boolean mixed = false;
		for(int i = 0; i < MAX; i++) {
			num[i] = Integer.parseInt(st.nextToken());
			if(i > 0 && Math.abs(num[i] - num[i - 1]) != 1) mixed = true; 
		}
		
		if(mixed) {
			System.out.println("mixed");
		} else {
			if(num[0] == 1) System.out.println("ascending");
			else System.out.println("descending");
		}
		
		br.close();
	}

}
