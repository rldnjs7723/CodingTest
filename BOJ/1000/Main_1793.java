import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

/**
 * 백준 1793번 타일링
 * 문제 분류: 다이나믹 프로그래밍, 큰 수 연산
 * @author Giwon
 */
public class Main_1793 {
	public static final int MAX = 250;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		BigInteger[] dp = new BigInteger[MAX + 1];
		BigInteger two = new BigInteger("2");
		
		dp[0] = BigInteger.ONE;
		dp[1] = BigInteger.ONE;
		dp[2] = new BigInteger("3");
		for(int i = 3; i <= MAX; i++) {
			dp[i] = dp[i - 1].add(dp[i - 2].multiply(two));
		}
		
		String input;
		while((input = br.readLine()) != null) {
			bw.write(dp[Integer.parseInt(input.trim())].toString() + "\n");
		}
		
		bw.close();
		br.close();
	}

}
