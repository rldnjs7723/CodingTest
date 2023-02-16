import java.util.Scanner;

/**
 * 백준 1001번 A-B
 * @author Giwon
 *
 */
class Main_1001 {
	public static void main(String args[]){
		// Scanner로 간편하게 정수, 실수, 문자열 등의 입력을 받을 수 있음
		// 속도가 느리기 때문에 입력의 길이가 길 때는 권장하지 않음 
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        
        System.out.print(A - B);
        sc.close();
    }
}