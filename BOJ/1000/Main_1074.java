import java.util.Scanner;

/**
 * 백준 1074번 Z
 * 문제 분류: 분할 정복
 * @author GIWON
 */
public class Main_1074 {
	public static final int[] dRow = {0, 0, -1, -1};
	public static final int[] dCol = {0, -1, 0, -1};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 2차원 배열 크기 정수 N
		int N = sc.nextInt();
		// 행
		int r = sc.nextInt();
		// 열
		int c = sc.nextInt();
		// 방문 순서는 0부터 시작하므로 결과 - 1 출력
		System.out.println(zSearch(r, c, (int) Math.pow(2, N)) - 1);
		sc.close();
	}

	// 분할 정복으로 탐색
	public static int zSearch(int r, int c, int size) {
		if(size == 1) return 1;
		// 현재 크기의 절반
		int half = size / 2;
		// z의 4개 순서 중 어디에 위치한지 탐색
		int order = 2 * (r / half) + c / half;
		// 앞의 순서 계산은 미리 수행하고, 분할 정복으로 아래 단계 탐색
		return half * half * order + zSearch(r + dRow[order] * half, c + dCol[order] * half, half);
	}
}
