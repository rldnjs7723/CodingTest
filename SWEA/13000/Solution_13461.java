import java.util.Scanner;

/**
 * SWEA 13461번 이미지 검색하기
 * 문제 분류: 자료구조 (Tree), DFS, 백트래킹
 * @author Giwon
 */
public class Solution_13461 {

	private static int seed = 5;

	private static Scanner sc;
	private static UserSolution user = new UserSolution();
	
	private static final int MAX_N = 10000;
	private static final int MAX_M = 10;

	private static char ori_image_list[][][] = new char[MAX_N][MAX_M][MAX_M];
	private static char bak_image_list[][][] = new char[MAX_N][MAX_M][MAX_M];
	private static char dummy[] = new char[5005];
	private static char bak_image[][] = new char[MAX_M][MAX_M];
	 
	private static int pseudo_rand()
	{
		seed = seed * 214013 + 2531011;
		return (seed >> 16) & 0x7fff;
	}

	static int run(int _score)
	{
		int n = sc.nextInt();
		int m = sc.nextInt();
		seed = sc.nextInt();
		int ratio = sc.nextInt();
		int query_cnt = sc.nextInt();
		
		
		for (int i = 0; i < n; i++)
		{
			for (int y = 0; y < m; y++)
			{
				for (int x = 0; x < m; x++)
				{
					ori_image_list[i][y][x] = 0;
					int v = pseudo_rand() % 100;
					if (v >= ratio)
						ori_image_list[i][y][x] = 1;
					
					bak_image_list[i][y][x] = ori_image_list[i][y][x];
				}
			}
		}

		user.init(n, m, bak_image_list);

		int user_ans, correct_ans;

		for (int query = 0; query < query_cnt; query++)
		{
			int num = pseudo_rand() % n;

			for (int y = 0; y < m; y++)
			{
				for (int x = 0; x < m; x++)
				{
					bak_image[y][x] = ori_image_list[num][y][x];
				}
			}

			int bad_sector_cnt = pseudo_rand() % 2 + 1;

			for (int i = 0; i < bad_sector_cnt; i++)
			{
				int by = pseudo_rand() % m;
				int bx = pseudo_rand() % m;

				bak_image[by][bx] ^= 1;
			}

			user_ans = user.findImage(bak_image);
			correct_ans = sc.nextInt();

			if (user_ans != correct_ans)
				_score = 0;
		}

		return _score;
	}
	
	public static void main(String[] args) throws Exception {
		System.setIn(new java.io.FileInputStream("sample_input.txt"));
		
		sc = new Scanner(System.in);

		int tc = sc.nextInt();
		int score = sc.nextInt();

		for (int t = 1; t <= tc; t++)
		{
			int tc_score = run(score);
			System.out.println("#"+ t + " " + tc_score);
		}

		sc.close();
	}

	
	public static class UserSolution {
		final int MAX_N = 10000;
		final int MAX_M = 10;
		// 불일치하는 픽셀이 2개 이하임이 보장
		final int MAX_DIFF = 2;
		final char NULL_ID = (char) MAX_N + 1;

		class Diff {
			// 다른 이미지
			int id;
			// 다른 이미지 상태
			int count;
			
			public Diff() {
				this(NULL_ID, MAX_DIFF);
			}
			
			public Diff(int id, int count) {
				this.id = id;
				this.count = count;
			}
			
			public Diff min(Diff obj) {
				// 차이가 적은 이미지를 선택
				if(this.count < obj.count) return this;
				else if(this.count > obj.count) return obj;
				// 차이가 같다면 id가 작은 이미지를 선택
				else if(this.id <= obj.id) return this;
				else return obj;
			}
		}
		
		class Node {
			// 현재 깊이에 도달하기 위한 행, 열 값
			int row, col;
			// 이미지 id. 가장 작은 값만 기록
			char id;
			// 이미지 값 0, 1에 따라 이동
			Node[] child;
			
			public Node(int row, int col) {
				this.row = row;
				this.col = col;
				this.id = NULL_ID;
				this.child = new Node[2];
			}
			
			public boolean isLeafNode() {
				return child[0] == null && child[1] == null;
			}
		}
		
		class ImageTree {
			// 이미지 크기
			int M;
			Diff minDiff;
			Node root;
			
			// 트리 구성
			public ImageTree(int N, int M, char[][][] mImageList) {
				root = new Node(-1, -1);
				
				Node curr;
				int val;
				for(int id = 1; id <= N; id++) {
					curr = root;
					for(int row = 0; row < M; row++) {
						for(int col = 0; col < M; col++) {
							val = mImageList[id - 1][row][col];
							if(curr.child[val] == null) {
								curr.child[val] = new Node(row, col);
							}
							curr = curr.child[val];
						}
					}
					
					// 동일한 이미지라면 값이 가장 작은 id만 기록
					if(curr.id == NULL_ID) {
						curr.id = (char) id;
					}
				}
			}
			
			public int getSimilarImage(char[][] mImage) {
				// 최소값을 구하기 위한 초기화
				minDiff = new Diff();
				// DFS로 탐색
				dfsSearch(0, root, mImage);
				return minDiff.id;
			}
			
			// DFS로 이미지 중에서 다른 값의 개수와, 해당 이미지의 id리턴
			// 깊이 최대 100이므로 가능하다고 판단
			private void dfsSearch(int diffCount, Node curr, char[][] mImage) {
				// 리프 노드라면 이미지 차이 저장
				if(curr.id != NULL_ID) {
					minDiff = minDiff.min(new Diff(curr.id, diffCount));
					return;
				}
				
				Node child;
				for(int i = 0; i < 2; i++) {
					child = curr.child[i];
					
					// 해당 분기에 이미지가 존재하지 않으면 생략
					if(child == null) continue;
					
					// 현재 분기와 이미지의 값이 다른 경우
					if(mImage[child.row][child.col] != i) {
						// 이미지 차이가 현재 최솟값보다 크면 생략
						if(diffCount >= minDiff.count) continue;
						dfsSearch(diffCount + 1, child, mImage);
					} else {
						dfsSearch(diffCount, child, mImage);
					}
				}
			}
		}
		
		ImageTree imgTree;
		
		void init(int N, int M, char mImageList[][][])
		{
			imgTree = new ImageTree(N, M, mImageList);
		}
		
		int findImage(char mImage[][])
		{
			return imgTree.getSimilarImage(mImage);
		}
	}

}
