import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * 백준 13913번 숨바꼭질 4
 * 문제 분류: BFS
 * @author Giwon
 */
public class Main_13913 {
	public static final int MAX = 100000;
	public static final int START = -1;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 수빈이 위치
		int N = sc.nextInt();
		// 동생 위치
		int K = sc.nextInt();
		
		// 각 위치에 도달하기 전에 어떤 위치에 있었는지 저장하는 배열
		int[] prev = new int[MAX + 1];
		// 각 위치에 도달하는 시간 저장 배열
		int[] time = new int[MAX + 1];
		Arrays.fill(time, Integer.MAX_VALUE);
		
		// 수빈이 시작 지점 초기화
		prev[N] = START;
		time[N] = 0;
		
		// BFS로 탐색
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(N);
		int curr, next;
		while(!queue.isEmpty()) {
			curr = queue.poll();
			
			// BFS이므로 가장 먼저 도착한 경우가 가장 시간이 짧은 경우
			if(curr == K) break;
			
			// 순간이동
			next = curr * 2;
			if(next <= MAX) {
				// 현재 이동하는 것이 더 빠르면 갱신
				if(time[next] > time[curr] + 1) {
					time[next] = time[curr] + 1;
					prev[next] = curr;
					queue.offer(next);
				}
			}
			
			// 뒤로 이동
			next = curr - 1;
			if(next >= 0) {
				// 현재 이동하는 것이 더 빠르면 갱신
				if(time[next] > time[curr] + 1) {
					time[next] = time[curr] + 1;
					prev[next] = curr;
					queue.offer(next);
				}
			}
			
			// 앞으로 이동
			next = curr + 1;
			if(next <= MAX) {
				// 현재 이동하는 것이 더 빠르면 갱신
				if(time[next] > time[curr] + 1) {
					time[next] = time[curr] + 1;
					prev[next] = curr;
					queue.offer(next);
				}
			}
		}
		
		// 이동 경로 기록
		Stack<Integer> temp = new Stack<>();
		// 수빈이의 시작지점이 될 때까지 역순으로 탐색
		int currLoc = K;
		while(currLoc != START) {
			temp.push(currLoc);
			currLoc = prev[currLoc];
		}
		
		// 이동 경로 문자열 생성
		StringBuilder sb = new StringBuilder();
		while(!temp.isEmpty()) {
			sb.append(temp.pop() + " ");
		}
		
		// 동생 찾는 시간
		System.out.println(time[K]);
		// 이동 경로
		System.out.println(sb.toString());
		sc.close();
	}
}
