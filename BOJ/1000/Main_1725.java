import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * 백준 1725번 히스토그램
 * 문제 분류: 자료구조 (스택)
 * @author Giwon
 */
public class Main_1725 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		final int N = Integer.parseInt(br.readLine().trim());
		
		Stack<Histogram> heights = new Stack<>();
		int height, area;
		int max = 0;
		Histogram temp;
		for(int i = 0; i < N; i++) {
			height = Integer.parseInt(br.readLine().trim());
			if(heights.isEmpty()) {
				heights.push(new Histogram(i, height));
				continue;
			}
			
			
			// 현재 막대의 높이가 가장 높은 경우
			if(heights.peek().height < height) {
				heights.push(new Histogram(i, height));
				continue;
			} 
			
			// 현재 막대의 높이가 이전보다 작은 경우
			while(heights.peek().height > height) {
				temp = heights.pop();
				// 최대 넓이 갱신
				area = temp.height * (i - temp.idx);
				max = Math.max(max, area);
				
				if(heights.isEmpty() || heights.peek().height < height) {
					heights.push(new Histogram(temp.idx, height));
					break;
				}
			}
			
			// 현재 막대의 높이가 이전과 같으면 아무 행동도 하지 않음
		}
		
		// 마지막 Index에서 높이 비우기
		int i = N;
		while(!heights.isEmpty()) {
			temp = heights.pop();
			// 최대 넓이 갱신
			area = temp.height * (i - temp.idx);
			max = Math.max(max, area);
		}
		
		System.out.println(max);
		br.close();
	}

	public static class Histogram {
		int idx, height;
		
		public Histogram(int idx, int height) {
			this.idx = idx;
			this.height = height;
		}
	}
	
}
