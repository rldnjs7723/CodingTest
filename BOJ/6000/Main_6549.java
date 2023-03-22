import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 6549번 히스토그램에서 가장 큰 직사각형
 * 문제 분류: 자료구조 (스택)
 * @author Giwon
 */
public class Main_6549 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st;
		String input;
		int N;
		long currHeight, area, max;
		Stack<Histogram> heights = new Stack<>();
		Histogram temp;
		while((input = br.readLine()) != null) {
			st = new StringTokenizer(input);
			N = Integer.parseInt(st.nextToken());
			if(N == 0) break;
			
			max = 0;
			heights.clear();
			for(int i = 0; i < N; i++) {
				currHeight = Integer.parseInt(st.nextToken());
				if(heights.isEmpty()) {
					heights.push(new Histogram(i, currHeight));
					continue;
				}
				
				
				// 현재 막대의 높이가 가장 높은 경우
				if(heights.peek().height < currHeight) {
					heights.push(new Histogram(i, currHeight));
					continue;
				} 
				
				// 현재 막대의 높이가 이전보다 작은 경우
				while(heights.peek().height > currHeight) {
					temp = heights.pop();
					// 최대 넓이 갱신
					area = temp.height * (i - temp.idx);
					max = Math.max(max, area);
					
					if(heights.isEmpty() || heights.peek().height < currHeight) {
						heights.push(new Histogram(temp.idx, currHeight));
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
			
			
			bw.write(Long.toString(max) + "\n");
		}
		
		bw.close();
		br.close();
	}

	public static class Histogram {
		int idx;
		long height;
		
		public Histogram(int idx, long height) {
			this.idx = idx;
			this.height = height;
		}
	}
}
