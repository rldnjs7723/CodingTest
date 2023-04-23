import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 11658번 구간 합 구하기 3
 * 문제 분류: 다차원 세그먼트 트리
 * @author Giwon
 */
public class Main_11658 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 표의 크기
		final int N = Integer.parseInt(st.nextToken());
		// 연산의 수
		final int M = Integer.parseInt(st.nextToken());
		
		// 표 입력
		int[][] table = new int[N][N];
		for(int row = 0; row < N; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < N; col++) {
				table[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 세그먼트 트리 초기화
		SegmentTree segmentTree = new SegmentTree(N, table);
		
		// 쿼리 수행
		int w, x1, y1, x2, y2, c;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			w = Integer.parseInt(st.nextToken());
			x1 = Integer.parseInt(st.nextToken());
			y1 = Integer.parseInt(st.nextToken());
			
			if(w == 0) {
				c = Integer.parseInt(st.nextToken());
				segmentTree.updateTable(x1, y1, c);
			} else if(w == 1) {
				x2 = Integer.parseInt(st.nextToken());
				y2 = Integer.parseInt(st.nextToken());
				bw.write(segmentTree.getSum(x1, x2, y1, y2) + "\n");
			}
		}
		
		bw.close();
		br.close();
	}
	
	public static class SegmentTree {
		// 표의 크기
		int N;
		// 현재 표에 저장된 값
		int[][] table;
		// 다차원 트리
		int[][] sum;
		
		public SegmentTree(int N, int[][] table) {
			this.N = N;
			this.table = table;
			this.sum = new int[4 * N][4 * N];
			
			init(0, N - 1, 1);
		}

		private int[] init(int rowStart, int rowEnd, int rowIdx) {
			// 리프 노드인 경우
			if(rowStart == rowEnd) {
				rowInit(rowStart, rowIdx, 0, N - 1, 1);
				return sum[rowIdx];
			}
			
			// 양쪽 자식 노드 값 더하기
			int mid = rowStart + (rowEnd - rowStart) / 2;
			int[] left = init(rowStart, mid, rowIdx * 2);
			int[] right = init(mid + 1, rowEnd, rowIdx * 2 + 1);
			for(int i = 0; i < 4 * N; i++) {
				sum[rowIdx][i] = left[i] + right[i];
			}
			
			return sum[rowIdx];
		}
		
		private int rowInit(int row, int rowIdx, int colStart, int colEnd, int colIdx) {
			// 리프 노드인 경우
			if(colStart == colEnd) {
				sum[rowIdx][colIdx] = table[row][colStart];
				return sum[rowIdx][colIdx];
			}
			
			// 양쪽 자식 노드 값 더하기
			int mid = colStart + (colEnd - colStart) / 2;
			int left = rowInit(row, rowIdx, colStart, mid, colIdx * 2);
			int right = rowInit(row, rowIdx, mid + 1, colEnd, colIdx * 2 + 1);
			sum[rowIdx][colIdx] = left + right;
			
			return sum[rowIdx][colIdx];
		}
		
		public void updateTable(int targetRow, int targetCol, int val) {
			// Index 0부터 시작하도록 변경
			update(0, N - 1, 1, targetRow - 1, targetCol - 1, val);
		}
		
		// 갱신된 가장 하단의 colIdx 리턴
		private int update(int rowStart, int rowEnd, int rowIdx, int targetRow, int targetCol, int val) {
			// 범위에서 벗어나는 경우 생략
			if(targetRow < rowStart || rowEnd < targetRow) return -1;
			
			// 해당 행인 경우
			if(rowStart == rowEnd) {
				return updateRow(rowIdx, 0, N - 1, 1, targetRow, targetCol, val);
			}
			
			// 양쪽 자식 노드 값 더하기
			int mid = rowStart + (rowEnd - rowStart) / 2;
			int idx = -1;
			idx = Math.max(idx, update(rowStart, mid, rowIdx * 2, targetRow, targetCol, val));
			idx = Math.max(idx, update(mid + 1, rowEnd, rowIdx * 2 + 1, targetRow, targetCol, val));
			int[] left = sum[rowIdx * 2];
			int[] right = sum[rowIdx * 2 + 1];
			// 갱신해야 하는 부모 노드 전부 갱신
			int i = idx;
			while(i > 0) {
				sum[rowIdx][i] = left[i] + right[i];
				i /= 2;
			}
			
			return idx;
		}
		
		// 갱신된 가장 하단의 colIdx 리턴
		private int updateRow(int rowIdx, int colStart, int colEnd, int colIdx, int targetRow, int targetCol, int val) {
			// 범위에서 벗어나는 경우 생략
			if(targetCol < colStart || colEnd < targetCol) {
				return -1;
			}
			
			// 해당 열인 경우
			if(colStart == colEnd) {
				table[targetRow][targetCol] = val;
				sum[rowIdx][colIdx] = val;
				return colIdx;
			}
			
			// 양쪽 자식 노드 값 더하기
			int mid = colStart + (colEnd - colStart) / 2;
			int idx = -1;
			idx = Math.max(idx, updateRow(rowIdx, colStart, mid, colIdx * 2, targetRow, targetCol, val));
			idx = Math.max(idx, updateRow(rowIdx, mid + 1, colEnd, colIdx * 2 + 1, targetRow, targetCol, val));
			sum[rowIdx][colIdx] = sum[rowIdx][colIdx * 2] + sum[rowIdx][colIdx * 2 + 1];
			
			return idx;
		}
		
		public int getSum(int targetRowStart, int targetRowEnd, int targetColStart, int targetColEnd) {
			// Index 1부터 시작하도록 변경
			return sum(0, N - 1, 1, targetRowStart - 1, targetRowEnd - 1, targetColStart - 1, targetColEnd - 1);
		}
		
		private int sum(int rowStart, int rowEnd, int rowIdx, int targetRowStart, int targetRowEnd, int targetColStart, int targetColEnd) {
			// 범위에서 벗어나는 경우 0 리턴
			if(targetRowEnd < rowStart || rowEnd < targetRowStart) return 0;
			
			// 범위에 포함되는 경우 열 범위 맞춘 값 리턴
			if(targetRowStart <= rowStart && rowEnd <= targetRowEnd) {
				return sumRow(rowIdx, 0, N - 1, 1, targetColStart, targetColEnd);
			}
			
			// 범위가 겹치는 경우
			int mid = rowStart + (rowEnd - rowStart) / 2;
			int left = sum(rowStart, mid, rowIdx * 2, targetRowStart, targetRowEnd, targetColStart, targetColEnd);
			int right = sum(mid + 1, rowEnd, rowIdx * 2 + 1, targetRowStart, targetRowEnd, targetColStart, targetColEnd);
			return left + right;
		}
		
		private int sumRow(int rowIdx, int colStart, int colEnd, int colIdx, int targetColStart, int targetColEnd) {
			// 범위에서 벗어나는 경우 0 리턴
			if(targetColEnd < colStart || colEnd < targetColStart) return 0;
			
			// 범위에 포함되는 경우 그대로 리턴
			if(targetColStart <= colStart && colEnd <= targetColEnd) return sum[rowIdx][colIdx];
			
			// 범위가 겹치는 경우
			int mid = colStart + (colEnd - colStart) / 2;
			int left = sumRow(rowIdx, colStart, mid, colIdx * 2, targetColStart, targetColEnd);
			int right = sumRow(rowIdx, mid + 1, colEnd, colIdx * 2 + 1, targetColStart, targetColEnd);
			return left + right;
		}
		
		// 디버깅용
		public void print() {
			for(int[] inner: sum) {
				System.out.println(Arrays.toString(inner));
			}
		}
	}

}
