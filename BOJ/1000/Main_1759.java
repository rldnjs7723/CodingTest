import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 1759번 암호 만들기
 * 문제 분류: 완전 탐색 (조합), Next Permutation
 * @author Giwon
 */
public class Main_1759 {
	public static final int SELECT = 1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 암호 길이
		int L = Integer.parseInt(st.nextToken());
		// 문자 개수
		int C = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		Character[] alphabet = new Character[C];
		for(int i = 0; i < C; i++) {
			alphabet[i] = st.nextToken().charAt(0);
		}
		// 오름차순 암호를 위해 정렬
		Arrays.sort(alphabet);
		// 조합을 저장한 배열
		int[] combination = new int[C];
		Arrays.fill(combination, C - L, C, SELECT);
		
		// 사전순 출력을 위한 Stack
		Stack<String> lexicographic = new Stack<>();
		// 모음 탐색용 Set
		Set<Character> vowel = new HashSet<>();
		vowel.add('a');
		vowel.add('e');
		vowel.add('i');
		vowel.add('o');
		vowel.add('u');
		//모음, 자음 개수 카운트
		int vowelCount, consonantCount;
		StringBuilder sb = new StringBuilder();
		do {
			vowelCount = 0;
			consonantCount = 0;
			sb.setLength(0);
			// 조합에 따라 문자열 생성
			for(int i = 0; i < C; i++) {
				if(combination[i] == SELECT) {
					sb.append(alphabet[i]);
					if(vowel.contains(alphabet[i])) vowelCount++;
					else consonantCount++;
				}
			}
			sb.append("\n");
			// 모음 1개, 자음 2개 이상으로 이루어진 경우에만 출력
			if(vowelCount >= 1 && consonantCount >= 2) lexicographic.push(sb.toString());;
		} while (nextPermutation(combination));
		
		// 출력
		while(!lexicographic.isEmpty()) {
			bw.write(lexicographic.pop());
		}
		
		bw.close();
		br.close();
	}

	// N <= 15이므로 최대 조합 수인 15C7이 6435개 밖에 안 되어 충분한 시간복잡도로 판단
	// NP로 조합 탐색
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
