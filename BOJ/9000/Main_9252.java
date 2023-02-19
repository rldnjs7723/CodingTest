import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * 백준 9252번 LCS 2
 * 문제 분류: LCS (Longest Common Subsequence), 다이나믹 프로그래밍
 * @author Giwon
 */
public class Main_9252 {
	public static final int NONE = 0, LEFT = 1, UP = 2, DIAGONAL = 3;
	public static final int[] dRow = {0, 0, -1, -1};
	public static final int[] dCol = {0, -1, 0, -1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String A = br.readLine().trim();
		String B = br.readLine().trim();
		int lenA = A.length();
		int lenB = B.length();
		
		// LCS 길이 저장
		int[][] lcsCount = new int[lenA + 1][lenB + 1];
		// LCS 구성할 때 이동 방향 저장
		int[][] lcsDirection = new int[lenA + 1][lenB + 1];
		for(int i = 0; i < lenA; i++) {
			for(int j = 0; j < lenB; j++) {
				if(A.charAt(i) == B.charAt(j)) {
					// 현재 알파벳이 동일하면 대각선 방향 + 1
					lcsCount[i + 1][j + 1] = lcsCount[i][j] + 1;
					lcsDirection[i + 1][j + 1] = DIAGONAL;
				} else {
					// 알파벳이 다르면 왼쪽과 위의 값중 더 큰 값을 할당
					if(lcsCount[i + 1][j] >= lcsCount[i][j + 1]) {
						lcsCount[i + 1][j + 1] = lcsCount[i + 1][j];
						lcsDirection[i + 1][j + 1] = LEFT;
					} else {
						lcsCount[i + 1][j + 1] = lcsCount[i][j + 1];
						lcsDirection[i + 1][j + 1] = UP;
					}
				}
			}
		}
			
		int row = lenA, col = lenB, curr;
		// LCS 길이 출력
		bw.write(lcsCount[row][col] + "\n");
		// LCS 탐색
		StringBuilder sb = new StringBuilder();
		while((curr = lcsDirection[row][col]) != 0) {
			// 대각선으로 이동하는 경우에만 동일한 경우이므로 append
			if(curr == DIAGONAL) {
				sb.append(A.charAt(row - 1));
			}
			row += dRow[curr];
			col += dCol[curr];
		}
		// LCS 출력
		sb.reverse();
		bw.write(sb.toString());
		
		bw.close();
		br.close();
	}
	
	// 디버깅용 이차원 배열 출력
	public static void printArr(int[][] arr) {
		for(int[] inner: arr) {
			System.out.println(Arrays.toString(inner));
		}
	}
}
