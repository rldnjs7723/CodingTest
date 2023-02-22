import java.util.Scanner;
import java.util.Stack;

/**
 * SWEA 1247번 최적 경로
 * 문제 분류: 완전 탐색 (시간 제한 20초), DFS
 * @author Giwon
 */
public class Solution_1247 {
	public static final int INF = 2100000000;
	
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		// DFS 탐색용 Stack
		Stack<Route> routes = new Stack<>();
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N = sc.nextInt();
			int startX, startY, endX, endY, x, y;
			Coordinate[] coord = new Coordinate[N];
			
			// 회사 좌표
			startX = sc.nextInt();
			startY = sc.nextInt();
			// 집 좌표
			endX = sc.nextInt();
			endY = sc.nextInt();
			
			// 고객 좌표 입력
			for(int i = 0; i < N; i++) {
				x = sc.nextInt();
				y = sc.nextInt();
				coord[i] = new Coordinate(x, y, false);
			}
			
			// Stack 초기화
			routes.clear();
			routes.push(new Route(startX, startY, coord));
			
			int result = INF;
			Route curr;
			while(!routes.isEmpty()) {
				curr = routes.pop();
				
				// 최솟값보다 커졌다면 중단
				if(curr.totalDist > result) continue;
				// 모든 고객을 방문했다면 최솟값 갱신
				if(curr.checkDone() != -1) {
					if(result > curr.totalDist + distance(curr.x, curr.y, endX, endY)) result = curr.totalDist + distance(curr.x, curr.y, endX, endY) ;
				}
				
				// 아직 방문하지 않은 고객 방문
				for(int i = 0; i < N; i++) {
					if(!curr.checked[i].check) routes.push(new Route(curr, i));
				}
			}
			
			System.out.println("#" + test_case + " " + result);
		}
		
		sc.close();
	}
	
	public static class Route {
		int x, y, totalDist;
		Coordinate[] checked;
		
		Route(int x, int y, Coordinate[] coords) {
			int N = coords.length;
			
			this.x = x;
			this.y = y;
			totalDist = 0;
			checked = new Coordinate[N];
			
			for(int i = 0; i < N; i++) {
				checked[i] = new Coordinate(coords[i].x, coords[i].y, coords[i].check);
			}
		}
		
		// DFS 탐색용 다음 경로 생성자
		Route(Route route, int idx) {
			int N = route.checked.length;
			
			this.x = route.checked[idx].x;
			this.y = route.checked[idx].y;
			this.totalDist = route.totalDist + distance(route.x, route.y, this.x, this.y);
			checked = new Coordinate[N];
			
			for(int i = 0; i < N; i++) {
				checked[i] = new Coordinate(route.checked[i].x, route.checked[i].y, route.checked[i].check);
			}
			checked[idx].check = true;
		}
		
		// 모든 고객을 방문했는지 확인 후, 완료되었다면 최종 거리 리턴
		int checkDone() {
			for(int i = 0; i < checked.length; i++) {
				if(!checked[i].check) return -1;
			}
			return totalDist;
		}
	}
	
	// 좌표 저장용 클래스
	public static class Coordinate {
		int x, y;
		boolean check;
		
		Coordinate(int x, int y, boolean check) {
			this.x = x;
			this.y = y;
			this.check = check;
		}
	}
	
	// 맨해튼 거리 계산
	public static int distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
}