import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 14865번 곡선 자르기
 * 문제 분류: 자료구조 (스택, 우선순위 큐)
 * @author Giwon
 */
public class Main_14865 {
	public static final int HORIZONTAL = 0, VERTICALUP = 1, VERTICALDOWN = 2;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine().trim());
		// 초기 시작점 입력
		int initialX, initialY;
		StringTokenizer st = new StringTokenizer(br.readLine());
		initialX = Integer.parseInt(st.nextToken());
		initialY = Integer.parseInt(st.nextToken());
		// 선분 시작, 끝 점
		int startX, startY, endX, endY;
		endX = initialX;
		endY = initialY;
		// 선분 정렬을 위한 우선순위 큐
		Queue<Line> sortedLine = new PriorityQueue<>();
		// 선분 입력
		List<Line> lines = new ArrayList<>();
		Line curr, temp = null;
		for(int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			// 이전 선분의 끝점은 현재 선분의 시작점
			startX = endX;
			startY = endY;
			// 끝점 입력
			endX = Integer.parseInt(st.nextToken());
			endY = Integer.parseInt(st.nextToken());
			
			curr = new Line(startX, startY, endX, endY);
			// X축과 교차하는 선분만 저장
			if(curr.crossX()) {
				// 페어 지정
				if(curr.direction == VERTICALDOWN) {
					if(lines.size() == 0) temp = curr;
					else {
						curr.pair = lines.get(lines.size() - 1);
						lines.get(lines.size() - 1).pair = curr;
					}
				}
				lines.add(curr);
				// 우선순위 큐를 통해 수직인 선분 중 X축과 교차하는 선분을 X 좌표에 따라 정렬
				sortedLine.offer(curr);
			}
		}
		// 시작점과 종점 연결
		startX = endX;
		startY = endY;
		endX = initialX;
		endY = initialY;
		curr = new Line(startX, startY, endX, endY);
		// X축과 교차하는 선분만 저장
		if(curr.crossX()) {
			// 페어 지정
			if(curr.direction == VERTICALDOWN) {
				if(lines.size() == 0) temp = curr;
				else {
					curr.pair = lines.get(lines.size() - 1);
					lines.get(lines.size() - 1).pair = curr;
				}
			}
			lines.add(curr);
			// 우선순위 큐를 통해 수직인 선분 중 X축과 교차하는 선분을 X 좌표에 따라 정렬
			sortedLine.offer(curr);
		}
		// 짝이 없는 선분 매칭해주기
		if(temp != null) {
			temp.pair = lines.get(lines.size() - 1);
			lines.get(lines.size() - 1).pair = temp;
		}
		
		// 다른 봉우리에 의해 포함되지 않는 봉우리 개수 Stack으로 탐색
		Stack<Line> stack = new Stack<>();
		// 다른 봉우리에 포함되지 않는 봉우리 개수, 다른 봉우리를 포함하지 않는 봉우리 개수
		int independentCount = 0, notIncludeCount = 0;
		while(!sortedLine.isEmpty()) {
			curr = sortedLine.poll();
			
			if(!stack.isEmpty()) {
				if(curr.pair == stack.peek()) {
					// 두 선분이 짝인 경우에만 pop
					temp = stack.pop();
					// 다른 선분을 포함한 경우 카운트
					if(!curr.include && !temp.include) notIncludeCount++;
					// 다른 선분에 포함된 경우 카운트
					if(!curr.included && !temp.included) independentCount++;
				} else {
					// 두 선분이 짝이 아닌 경우
					// 이전에 stack에 포함된 선분은 다른 봉우리를 포함
					stack.peek().include = true;
					// 현재 선분은 다른 봉우리에 포함된 경우
					curr.included = true;
					stack.push(curr);
				}
			} else {
				stack.push(curr);
			}
		}
		
		System.out.println(independentCount + " " + notIncludeCount);
		br.close();
	}

	// 선분 클래스
	public static class Line implements Comparable<Line> {
		// 선분 시작, 끝 점
		int startX, startY, endX, endY;
		// 선분 방향 (수직, 수평)
		int direction;
		// 다른 봉우리를 포함하는 봉우리인지 체크
		boolean include, included;
		Line pair;
		
		public Line(int startX, int startY, int endX, int endY) {
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
			
			this.direction = startX != endX ? HORIZONTAL : (startY < endY ? VERTICALUP : VERTICALDOWN);
			this.include = false;
			this.included = false;
			this.pair = null;
		}
		
		// X축과 선분이 교차하는지 체크
		public boolean crossX() {
			// 수평 선분은 생략
			if(direction == HORIZONTAL) return false;
			int min = Math.min(startY, endY);
			int max = Math.max(startY, endY);
			// 두 y값이 각각 0보다 작거나 0보다 큰 경우라면 X축과 교차하는 것
			return min <= 0 && max > 0; 
		}

		// x 좌표 기준으로 오름차순 정렬
		@Override
		public int compareTo(Line o) {
			return Integer.compare(this.startX, o.startX);
		}
		
		// 디버깅용
		@Override
		public String toString() {
			return "( " + startX + ", " + startY + " ) -> ( " + endX + ", " + endY + " )";
		}
	}
}
