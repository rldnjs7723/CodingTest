import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 2138번 전구와 스위치
 * 문제 분류: 그리디 알고리즘
 * @author GIWON
 */
public class Main_2138 {
    public static int SAME = 0, DIFF = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        String start = br.readLine().trim();
        String end = br.readLine().trim();
    
        boolean[] state = new boolean[N];
        // 시작이 0인 경우
        for(int i = 1; i < N; i++) {
        	// 이전 전구가 제대로 작동하도록 현재 스위치를 체크
        	if(checkDiff(start, end, i - 1) != checkCount(state, i - 1)) state[i] = true;
        }
        
        // 마지막 전구가 제대로 작동되면 올바른 케이스
        if(checkDiff(start, end, N - 1) == checkCount(state, N - 1)) {
        	System.out.println(getCount(state));
        	br.close();
        	return;
        }
        
        // 시작이 1인 경우
        state = new boolean[N];
        state[0] = true;
        for(int i = 1; i < N; i++) {
        	// 이전 전구가 제대로 작동하도록 현재 스위치를 체크
        	if(checkDiff(start, end, i - 1) != checkCount(state, i - 1)) state[i] = true;
        }
        
        // 마지막 전구가 제대로 작동되면 올바른 케이스
        if(checkDiff(start, end, N - 1) == checkCount(state, N - 1)) {
        	System.out.println(getCount(state));
        } else {
        	System.out.println(-1);
        }
        
        br.close();
    }

    // 현재 Index의 이전 스위치가 켜져있는지 확인
    public static int checkCount(boolean[] state, int idx) {
    	int count = 0;
    	if(idx - 1 >= 0 && state[idx - 1]) count++;
    	if(state[idx]) count++;
    	
    	return count % 2;
    }
    
    // 두 상태에서 특정 위치의 전구의 상태가 같은지 다른지 판단
    public static int checkDiff(String start, String end, int idx) {
        return start.charAt(idx) == end.charAt(idx) ? SAME : DIFF;
    }
    
    // 스위치 상태를 나타내는 boolean 배열에서 몇 개의 스위치를 켰는지 카운트
    public static int getCount(boolean[] state) {
        int count = 0;
        for(boolean light: state) {
        	if(light) count++;
        }
        
        return count;
    }
}