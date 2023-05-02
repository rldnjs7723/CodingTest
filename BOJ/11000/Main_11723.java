import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 백준 11723번 집합
 * 문제 분류: 비트마스킹
 * @author Giwon
 */
public class Main_11723 {
	public static final int MAX = 20;
	public static final int FULL = (1 << MAX) - 1;
	public static final int INIT = 0;
	public static final String ADD = "add", REMOVE = "remove", CHECK = "check", TOGGLE = "toggle", ALL = "all", EMPTY = "empty";
	public static int bitmaskSet;
	public static BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		final int N = Integer.parseInt(br.readLine().trim());
		String type;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			type = st.nextToken();
			
			int x, bit;
			if(type.equals(ALL)) {
				bitmaskSet = FULL;
			} else if(type.equals(EMPTY)) {
				bitmaskSet = INIT;
			} else {
				x = Integer.parseInt(st.nextToken()) - 1;
				bit = 1 << x;
				switch (type) {
					case ADD:
						bitmaskSet |= bit;
						break;
					case REMOVE:
						bitmaskSet &= (~bit);
						break;
					case CHECK:
						int result = (bitmaskSet & bit) > 0 ? 1 : 0;
						bw.write(result + "\n");
						break;
					case TOGGLE:
						bitmaskSet ^= bit;
						break;
					default:
						break;
				}
			}
		}
		
		bw.close();
		br.close();
	}
	
}
