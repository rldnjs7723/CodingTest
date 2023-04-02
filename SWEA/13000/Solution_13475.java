import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 13475번 동선 추적
 * 문제 분류: 구현, 시뮬레이션
 * @author Giwon
 */
public class Solution_13475 {
	private static BufferedReader br;
	private static UserSolution usersolution = new UserSolution();

	private final static int INIT = 100;
	private final static int ADD_PLACE = 200;
	private final static int REMOVE_PLACE = 300;
	private final static int CONTACT_TRACING = 400;
	private final static int DISINFECT_PLACES = 500;

	private static boolean run() throws Exception {
		boolean ret = false;
		int cmd;
		int pID, uID, r, c, visitNum;
		int moveInfo[] = new int[100];
		int visitList[] = new int[100];
		int ans;

		StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");

		int queryCnt = Integer.parseInt(stdin.nextToken());

		for (int q = 1; q <= queryCnt; q++) {
			stdin = new StringTokenizer(br.readLine(), " ");

			cmd = Integer.parseInt(stdin.nextToken());

			switch (cmd) {
			case INIT:
				usersolution.init();
				ret = true;
				break;
			case ADD_PLACE:
				pID = Integer.parseInt(stdin.nextToken());
				r = Integer.parseInt(stdin.nextToken());
				c = Integer.parseInt(stdin.nextToken());
				usersolution.addPlace(pID, r, c);
				break;
			case REMOVE_PLACE:
				pID = Integer.parseInt(stdin.nextToken());
				usersolution.removePlace(pID);
				break;
			case CONTACT_TRACING:
				uID = Integer.parseInt(stdin.nextToken());
				visitNum = Integer.parseInt(stdin.nextToken());
				stdin = new StringTokenizer(br.readLine(), " ");
				for (int i = 0; i < visitNum; i++)
					moveInfo[i] = Integer.parseInt(stdin.nextToken());
				usersolution.contactTracing(uID, visitNum, moveInfo, visitList);
				stdin = new StringTokenizer(br.readLine(), " ");
				for (int i = 0; i < visitNum; i++) {
					ans = Integer.parseInt(stdin.nextToken());
					if (visitList[i] != ans)
						ret = false;
				}
				break;
			case DISINFECT_PLACES:
				uID = Integer.parseInt(stdin.nextToken());
				usersolution.disinfectPlaces(uID);
				break;
			default:
				ret = false;
				break;
			}
		}

		return ret;
	}

	public static void main(String[] args) throws Exception {
		int T, MARK;

		System.setIn(new java.io.FileInputStream("sample_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");
		T = Integer.parseInt(stinit.nextToken());
		MARK = Integer.parseInt(stinit.nextToken());

		for (int tc = 1; tc <= T; tc++) {
			int score = run() ? MARK : 0;
			System.out.println("#" + tc + " " + score);
		}

		br.close();
	}
	
	public static class UserSolution {
		final int USER_MAX = 1000;
		final int PLACE_MAX = 50000;
		final int MAP_MAX = 10000;
		// 감염자 이동 방향 팔방 탐색
		final int N = 0, NE = 1, E = 2, SE = 3, S = 4, SW = 5, W = 6, NW = 7;
		// 각 방향 탐색별 
		final int ROW = 0, COL = 1, DIAGONAL_M = 2, DIAGONAL_P = 3;
		
		// uID별 감염자 기록
		User[] infector;
		// pID별 장소 기록
		Place[] placeMap;
		// 각 행에 위치한 장소 기록
		PlaceList[] RowList;
		// 각 열에 위치한 장소 기록
		PlaceList[] ColList;
		// 대각선별 \ 방향에 위치한 장소 기록 (r - c)가 같은 경우
		PlaceList[] DiagonalListM;
		// 대각선별 / 방향에 위치한 장소 기록 (r + c)가 같은 경우
		PlaceList[] DiagonalListP;
		
		
		
		// 일렬로 존재하는 장소는 최대 10개
		@SuppressWarnings("serial")
		class PlaceList extends LinkedList<Place> {
			
			// pID를 통해 객체 제거
			void removePID(int pID) {
				Iterator<Place> iter = this.iterator();
				
				Place curr;
				while(iter.hasNext()) {
					curr = iter.next();
					
					if(curr.pID == pID) {
						iter.remove();
						break;
					}
				}
			}
			
			// 주어진 위치, 방향 기준 가장 가까우면서 방문 가능한 장소 리턴
			Place getPlace(int row, int col, int dir) {
				Iterator<Place> iter = this.iterator();
				
				Queue<Place> available = new PriorityQueue<>();
				Place curr;
				while(iter.hasNext()) {
					curr = iter.next();
					
					// 현재 위치라면 생략
					if(curr.r == row && curr.c == col) continue;
					// 방문이 불가능한 경우 생략
					if(curr.infected) continue;
					// 반대 방향인 경우 생략
					if(curr.getDirection(row, col) != dir) continue;
					
					// 같은 방향인 경우 거리 계산 후 PQ로 정렬
					curr.setDistance(row, col);
					available.offer(curr);
				}
				
				// 가장 가까운 장소 리턴
				return available.poll();
			}
		}
		
		class User {
			// 감염자 이동 경로 pID 배열
			int[] trace;
			// 다음 장소를 넣을 Index
			int idx;
			
			public User(int visitNum) {
				trace = new int[visitNum];
				idx = 0;
			}
			
			// 장소 pID 추가
			public void addPlace(int pID) {
				trace[idx++] = pID;
			}
		}
		
		class Place implements Comparable<Place>{
			// 장소 ID
			int pID;
			// 장소 위치
			int r, c;
			// 감염 여부
			boolean infected;
			// 입력 받은 위치에서의 맨해튼 거리
			int distance;
			
			public Place(int pID, int r, int c) {
				this.pID = pID;
				this.r = r;
				this.c = c;
				this.infected = false;
			}
			
			// 입력 위치 기준으로 8개의 방향 중 어떤 방향에 위치했는지 리턴
			public int getDirection(int row, int col) {
				if(r == row) {
					// 행이 같은 경우
					// 열 값이 더 크면 동쪽
					if(c > col) return E;
					// 작으면 서쪽
					else return W;
				} else if(c == col) {
					// 열이 같은 경우
					// 행 값이 더 크면 남쪽
					if(r > row) return S;
					// 작으면 북쪽
					else return N;
				} else if(r > row) {
					// 행 값이 더 큰 경우
					// 열 값이 더 크면 남동쪽
					if(c > col) return SE;
					// 작으면 남서쪽
					else return SW;
				} else {
					// 행 값이 더 작은 경우
					// 열 값이 더 크면 북동쪽
					if(c > col) return NE;
					// 작으면 북서쪽
					else return NW;
				}
			}
			
			// 맨해튼 거리 계산
			public void setDistance(int row, int col) {
				distance = Math.abs(r - row) + Math.abs(c - col);
			}

			// Priority Queue를 통해 가까운 장소를 방문
			@Override
			public int compareTo(Place o) {
				return Integer.compare(this.distance, o.distance);
			}

			@Override
			public String toString() {
				return "Place [pID=" + pID + ", r=" + r + ", c=" + c + "]";
			}
		}
		
		// 이동 방향에 따른 탐색 방향 리턴
		int getDirection(int heading) {
			if(heading == E || heading == W) {
				// 가로 방향
				return ROW;
			} else if(heading == N || heading == S) {
				// 세로 방향
				return COL;
			} else if(heading == NW || heading == SE) {
				// 대각선 \ 방향
				return DIAGONAL_M;
			} else if(heading == NE || heading == SW){
				// 대각선 / 방향
				return DIAGONAL_P;
			}
			return -1;
		}
		
		// 현재 위치, 방향에 따른 장소 리스트 리턴
		PlaceList getList(int row, int col, int dir) {
			switch (dir) {
				case ROW:
					return RowList[row];
				case COL:
					return ColList[col];
				case DIAGONAL_M:
					return DiagonalListM[row - col + MAP_MAX];
				case DIAGONAL_P:
					return DiagonalListP[row + col];
				default:
					return null;
			}
		}
		
		void init() {
			// 1 ~ 1000범위
			infector = new User[USER_MAX + 1];
			
			// 1 ~ 50000범위
			placeMap = new Place[PLACE_MAX + 1];
			
			// 0 ~ 9999 범위
			RowList = new PlaceList[MAP_MAX];
			ColList = new PlaceList[MAP_MAX];
			for(int i = 0; i < MAP_MAX; i++) {
				RowList[i] = new PlaceList();
				ColList[i] = new PlaceList();
			}
			
			// 1 ~ 19999범위
			// 참조 시에는 (r - c + MAP_MAX)로 참조
			DiagonalListM = new PlaceList[MAP_MAX * 2];
			// 0 ~ 19998범위
			// 참조 시에는 (r + c)로 참조
			DiagonalListP = new PlaceList[MAP_MAX * 2];
			for(int i = 0; i < MAP_MAX * 2; i++) {
				DiagonalListM[i] = new PlaceList();
				DiagonalListP[i] = new PlaceList();
			}
		}

		// O(1)
		void addPlace(int pID, int r, int c) {
			Place place = new Place(pID, r, c);
			// pID 매핑
			placeMap[pID] = place;
			// 행 위치 추가
			getList(r, c, ROW).add(place);
			// 열 위치 추가
			getList(r, c, COL).add(place);
			// 대각선 \ 방향 위치 추가
			getList(r, c, DIAGONAL_M).add(place);
			// 대각선 / 방향 위치 추가
			getList(r, c, DIAGONAL_P).add(place);
		}

		// O(1)
		void removePlace(int pID) {
			Place place = placeMap[pID];
			int r = place.r;
			int c = place.c;
			
			// pID 매핑 해제
			placeMap[pID] = null;
			// 행 위치에서 제거
			getList(r, c, ROW).removePID(pID);
			// 열 위치에서 제거
			getList(r, c, COL).removePID(pID);
			// 대각선 \ 방향 위치에서 제거
			getList(r, c, DIAGONAL_M).removePID(pID);
			// 대각선 / 방향 위치에서 제거
			getList(r, c, DIAGONAL_P).removePID(pID);
		}

		void contactTracing(int uID, int visitNum, int moveInfo[], int visitList[]) {
			User user = infector[uID] = new User(visitNum);
			
			// 시작 지점
			Place start = placeMap[moveInfo[0]];
			int row = start.r;
			int col = start.c;
			// 시작 지점 경로에 추가
			user.addPlace(start.pID);
			
			int dir;
			PlaceList candidate;
			Place visited;
			for(int i = 1; i < visitNum; i++) {
				// 이동 방향
				dir = moveInfo[i];
				// 현재 위치, 방향에 따라 방문이 가능한 장소 리스트 구하기
				candidate = getList(row, col, getDirection(dir));
				// 방문한 장소 탐색
				visited = candidate.getPlace(row, col, dir);
				
				// 방문한 장소 경로에 추가
				user.addPlace(visited.pID);
				// 위치 이동
				row = visited.r;
				col = visited.c;
			}
			
			// 방문 경로 저장
			for(int i = 0; i < visitNum; i++) {
				visitList[i] = user.trace[i];
				// 방문한 지점 방문 불가능하게 수정
				placeMap[visitList[i]].infected = true;
			}
		}

		// O(visitNum)
		void disinfectPlaces(int uID) {
			User user = infector[uID];
			
			for(int pID: user.trace) {
				if(placeMap[pID] != null) {
					placeMap[pID].infected = false;
				}
			}
		}
	}
}