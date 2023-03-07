import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * SWEA 5658번 보물상자 비밀번호
 * 문제 분류: 자료 구조 (List, Set)
 * @author Giwon
 */
class Solution_5658 {
    public static final int MAXDIGIT = 7;
    // 7개 각 자리수에 곱할 정수
    public static int[] digits = new int[MAXDIGIT];
    
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final int T = Integer.parseInt(br.readLine().trim());

		// 최대 16^7이므로 정수로 표현 가능
		digits[0] = 1;
        for(int i = 1; i < MAXDIGIT; i++) {
            digits[i] = digits[i - 1] * 16;
        }

        int N, SIZE, K;
        String input;
        List<Character> boxNum = new ArrayList<>();
        // 비밀번호 내림차순으로 정렬
        Set<Integer> sortedSet = new TreeSet<>(Collections.reverseOrder());
        Integer[] result;
		for(int test_case = 1; test_case <= T; test_case++) {
			input = br.readLine().trim();
			// 보물상자에 적힌 숫자 길이
			N = Integer.parseInt(input.split(" ")[0]);
			// 비밀번호로 사용할 위치
			K = Integer.parseInt(input.split(" ")[1]);
			
			// 비밀번호 길이
			SIZE = N / 4;
            
            // 보물상자에 적힌 숫자 입력
			input = br.readLine().trim();
			boxNum.clear();
            for(int i = 0; i < N; i++) {
            	boxNum.add(input.charAt(i));
            }
            
            // 가능한 비밀번호 탐색
            sortedSet.clear();
            int password;
            // 비밀번호 길이만큼 회전
            for(int i = 0; i < SIZE; i++) {
            	for(int iter = 0; iter < 4; iter++) {
            		password = 0;
            		for(int digit = 0; digit < SIZE; digit++) {
            			// 비밀번호 입력
            			password += digits[SIZE - 1 - digit] * charToInt(boxNum.get(SIZE * iter + digit));
            		}
            		sortedSet.add(password);
            	}
            	
            	// 회전
            	rotateList(boxNum);
            }
            
            result = sortedSet.toArray(new Integer[sortedSet.size()]);
            System.out.println("#" + test_case + " " + result[K - 1]);
		}
		
		br.close();
	}
	
	// 리스트 회전
	public static void rotateList(List<Character> list) {
		list.add(list.remove(0));
	}
	
	// 16진수를 10진수 정수로 변환
	public static int charToInt(char val) {
		if(val > '9') return (val - 'A') + 10;
		else return val - '0';
	}
}