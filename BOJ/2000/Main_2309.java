import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 백준 2309번 일곱 난쟁이
 * 문제 분류: 조합론, 완전 탐색
 * @author Giwon
 */
public class Main_2309 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int[] height = new int[9];
		for(int i = 0; i < 9; i++) {
			height[i] = Integer.parseInt(br.readLine().trim());
		}
		
		int[] combination = {0, 0, 1, 1, 1, 1, 1, 1, 1};
		
		do {
			int sum = 0;
			for(int i = 0; i < 9; i++) {
				if(combination[i] == 1) {
					sum += height[i];
				}
			}
			
			if(sum == 100) break;
		} while (nextPermutation(combination));
		
		int[] dwarf = new int[7];
		int size = 0;
		for(int i = 0; i < 9; i++) {
			if(combination[i] == 1) {
				dwarf[size++] = height[i];
			}
		}
		Arrays.sort(dwarf);
		
		for(int i = 0; i < 7; i++) {
			System.out.println(dwarf[i]);
		}
		br.close();
	}

	// NP로 9C7 조합 탐색
	public static boolean nextPermutation(int[] arr) {
		int n = arr.length;
		// i 탐색
		int i = n - 1;
		for(i = n - 1; i >= 1; i--) {
			if(arr[i - 1] < arr[i]) break;
		}
		if(i == 0) return false;
		
		// j 탐색
		int temp = arr[i - 1];
		int j = n - 1;
		for(j = n - 1; j >= 0; j--) {
			if(temp < arr[j]) break;
		}
		
		// 위치 교환
		arr[i - 1] = arr[j];
		arr[j] = temp;
		
		// i부터 정렬
		Arrays.sort(arr, i, n);
		return true;
	}
}
