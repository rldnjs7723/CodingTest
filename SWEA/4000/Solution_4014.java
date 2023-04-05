import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 4014번 활주로 건설
 * 문제 분류: 스위핑
 * @author Giwon
 */
public class Solution_4014 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine().trim());
		int N, X;
		int[][] map;
		List<Ground> groundList = new ArrayList<>();
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			// 지도 크기
			N = Integer.parseInt(st.nextToken());
			// 경사로 길이
			X = Integer.parseInt(st.nextToken());
			
			// 지도 상태 입력
			map = new int[N][N];
			for(int row = 0; row < N; row++) {
				st = new StringTokenizer(br.readLine());
				for(int col = 0; col < N; col++) {
					map[row][col] = Integer.parseInt(st.nextToken());
				}
			}
			
			int count = 0;
			Ground curr;
			// 행 별 비교
			for(int row = 0; row < N; row++) {
				groundList.clear();
				curr = Ground.NULL_GROUND;
				for(int col = 0; col < N; col++) {
					// 현재 지형의 높이가 이전 지형의 높이랑 다른 경우
					if(map[row][col] != curr.height) {
						// 리스트에 이전 지형 집어넣기
						groundList.add(curr);
						// 현재 지형으로 이어서 탐색하기
						curr = new Ground(map[row][col]);
					} else {
						// 높이가 같다면 개수 추가
						curr.count++;
					}
				}
				// 마지막 지형 리스트에 추가
				groundList.add(curr);
				// NULL 값 제거
				groundList.remove(0);
				
				// 현재 위치에 활주로를 지을 수 있는지 체크
				if(checkBuildable(X, groundList)) count++;
			}
			
			// 열 별 비교
			for(int col = 0; col < N; col++) {
				groundList.clear();
				curr = Ground.NULL_GROUND;
				for(int row = 0; row < N; row++) {
					// 현재 지형의 높이가 이전 지형의 높이랑 다른 경우
					if(map[row][col] != curr.height) {
						// 리스트에 이전 지형 집어넣기
						groundList.add(curr);
						// 현재 지형으로 이어서 탐색하기
						curr = new Ground(map[row][col]);
					} else {
						// 높이가 같다면 개수 추가
						curr.count++;
					}
				}
				// 마지막 지형 리스트에 추가
				groundList.add(curr);
				// NULL 값 제거
				groundList.remove(0);
				
				// 현재 위치에 활주로를 지을 수 있는지 체크
				if(checkBuildable(X, groundList)) count++;
			}
			
			System.out.println("#" + test_case + " " + count);
		}
		br.close();
	}	
	
	/**
	 * 현재 위치에 활주로를 지을 수 있는지 체크
	 * @param X				경사로 길이
	 * @param groundList	지형 높이별 개수
	 * @return				활주로 건설 가능 여부
	 */
	public static boolean checkBuildable(int X, List<Ground> groundList) {
		int len = groundList.size();
		
		Ground curr, next;
		for(int i = 0; i < len - 1; i++) {
			curr = groundList.get(i);
			next = groundList.get(i + 1);
			
			// 지형의 경사 차이가 2 이상이면 경사로를 만들 수 없음
			if(Math.abs(curr.height - next.height) > 1) return false;
			
			// 현재 지형보다 다음 지형의 높이가 낮은 경우
			if(curr.height > next.height) {
				// 경사로를 지을 수 없다면 false
				if(next.count < X) return false;
				// 경사로 짓기
				next.count -= X;
			} 
			// 현재 지형보다 다음 지형의 높이가 높은 경우
			else if(curr.height < next.height) {
				// 경사로를 지을 수 없다면 false
				if(curr.count < X) return false;
				// 경사로 짓기
				curr.count -= X;
			}
		}
		
		return true;
	}

	public static class Ground {
		public static Ground NULL_GROUND = new Ground(0);
		
		// 현재 지형 높이
		int height;
		// 현재 높이가 연속된 횟수
		int count;
		
		public Ground(int height) {
			this.height = height;
			this.count = 1;
		}

		@Override
		public String toString() {
			return "Ground [height=" + height + ", count=" + count + "]";
		}
	}
}
