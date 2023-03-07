import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 17281번 ⚾ (야구)
 * 문제 분류: 구현, 시뮬레이션, 완전 탐색, Next Permutation
 * @author Giwon
 */
public class Main_17281 {
	// 타자 결과
	public static final int OUT = 0, SINGLE = 1, DOUBLE = 2, TRIPLE = 3, HOMERUN = 4;
	public static final int MAXPLAYER = 9, MAXOUT = 3, MAXBASE = 4;
	public static int[] team = {1, 2, 3, 4, 5, 6, 7, 8, 9};
	// 이닝 수
	public static int N;
	public static int[][] inningResult;
	// 현재 이닝
	public static int currInning;
	// 현재 베이스 상태
	public static boolean[] base = {true, false, false, false};
	// 현재 아웃 카운트
	public static int outCount;
	// 현재 타순
	public static int playerIdx;
	// 현재 점수
	public static int score;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 이닝 수
		N = Integer.parseInt(br.readLine());
		// 이닝 별 선수 결과 입력
		inningResult = new int[N][MAXPLAYER];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int player = 0; player < MAXPLAYER; player++) {
				inningResult[i][player] = Integer.parseInt(st.nextToken());
			}
		}
		
		int max = 0;
		do {
			// 1번 선수가 4번 타자가 아니라면 생략
			if(team[3] != 1) continue;
			
			// 1번 타자부터 시작
			playerIdx = 0;
			// 0점부터 시작
			score = 0;
			
			// N개의 이닝 수행
			for(int i = 0; i < N; i++) {
				currInning = i;
				// 이닝 시작
				startInning();
				
				// 3아웃이 될 때까지 타자 시뮬레이션
				while(outCount < MAXOUT) {
					hitter();
				}
			}
			
			// 최고 점수 기록
			max = Math.max(max, score);
		} while (nextPermutation(team));
		
		System.out.println(max);
		br.close();
	}
	
	// 이닝 시작하면서 변수 초기화
	public static void startInning() {
		// 각 루 초기화
		Arrays.fill(base, false);
		// 홈에는 주자가 있다고 설정
		base[0] = true;
		// 아웃 카운트 초기화
		outCount = 0;
	}
	
	// 타자 시뮬레이션
	public static void hitter() {
		int result = getResult();

		if(result == OUT) {
			// 아웃
			// 아웃 카운트 추가
			outCount++;
		} else {
			// 타격
			// 베이스에 주자가 있었다면 점수 추가
			for(int i = MAXBASE - result; i < MAXBASE; i++) {
				if(base[i]) {
					score++;
					base[i] = false;
				}
			}
			// 타자 설정
			base[0] = true;
			
			// 이전 베이스에 위치한 주자 불러오기
			for(int i = MAXBASE - 1; i >= result; i--) {
				base[i] = base[i - result];
				base[i - result] = false;
			}
			// 타자 설정
			base[0] = true;
		}
		
		// 타순 변경
		playerIdx = (playerIdx + 1) % MAXPLAYER;
	}
	
	// 현재 이닝에서의 현재 타석 선수 결과 리턴
	public static int getResult() {
		return inningResult[currInning][team[playerIdx] - 1];
	}
	
	// NP로 모든 타자들의 타순 설정
	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;
		// i 탐색
		int i = n - 1;
		for(i = n - 1; i >= 1; i--) {
			if(arr[i - 1] < arr[i]) break;
		}
		if(i == 0) return false;
		
		// j 탐색
		int temp = arr[i - 1];
		int j = n - 1;
		for(j = n - 1; j >= 0; j--) {
			if(temp < arr[j]) break;
		}
		
		// 위치 교환
		arr[i - 1] = arr[j];
		arr[j] = temp;
		
		// i부터 정렬
		Arrays.sort(arr, i, n);
		return true;
	}
	
}
