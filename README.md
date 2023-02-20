# Shell 파일 사용법

1. ./execute.sh <사이트 타입> <문제 번호>
2. <사이트 타입> = BOJ: B, SWEA: S, Programmers: P
3. <문제 번호> = 숫자로 그대로 입력

# 자바 코딩테스트 팁

1. 입력 / 출력 값이 Overflow가 발생하는지 항상 수학적으로 계산하여 int 대신 long을 사용할 필요가 있는지 고려해야 함
2. 시간 복잡도는 1억 번의 연산을 1초로 넉넉하게 두고 풀기

## 이클립스 사용법

1. Ctrl + F11: 실행
2. Ctrl + Space: 자동완성.
3. sysout 입력 후 Ctrl + Space를 누르면 System.out.println()이 입력 [(2558)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2558.java)

## 자바 기본

1. 입력의 크기가 작을 때, Scanner를 사용하면 정수, 실수, 문자열 등의 입력을 간단하게 받을 수 있다.  
   정수: [(1000)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1000.java)  
   실수: [(1008)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1008.java)
2. 실수를 출력할 때 C에서와 같이 printf를 사용하여 실수의 자리수를 설정 가능 [(1008)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1008.java)

   ```
   System.out.printf("%.12f", A / B);
   ```

## 알면 도움되는 메서드 / 클래스 변수

### Integer

1. Integer.MAX_VALUE / MIN_VALUE : 정수의 최대, 최소 값
2. Integer.toBinaryString() : 정수를 이진수로 변환. 비트마스킹 디버깅에 유용
3. Integer.compare() : compareTo 메서드 Override 할 때 유용 [(20366)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/20000/Main_20366.java)

### Arrays

1. Arrays.fill()
2. Arrays.sort(). 배열을 정렬. 새로 정의한 클래스에 적용하려면 Comparable 인터페이스 구현 필요.  
   정렬할 배열의 범위를 지정할 수도 있음 sort(배열, 시작 idx, 끝 idx); (끝 idx 미포함) [(20366)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/20000/Main_20366.java)

# 알고리즘

## Bitmasking (비트마스킹)

1. 비트 선언할 때 0b0000과 같이 이진수로 숫자를 입력할 수 있다.

## Greedy Algorithm [(그리디 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Greedy.md)

1. 여러 경우 중 하나를 선택할 때 그 순간에 최적이라고 생각되는 것을 선택하는 알고리즘.  
   1-1. 탐욕적 선택이 최적해로 갈 수 있음이 증명된 경우에만 사용  
   1-2. 최적 부분 구조: 탐욕적 선택 이후 동일한 하위 문제를 해결해야 하는 구조에 사용
2. `한 번 선택한 것을 번복하지 않음`
3. 규칙을 찾지 못하고 너무 복잡하게 해결하는 경우 일정 조건을 완전 탐색으로 제한하고 그리디로 접근하는 방식도 좋음 [(2138)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2138.java)

## Sliding Window [(슬라이딩 윈도우)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/SlidingWindow.md)

1. 고정 사이즈의 윈도우가 이동하면서 윈도우 내에 있는 데이터로 문제를 푸는 방식

## Two Pointers [(투포인터 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/TwoPointers.md)

1. index 값을 가지는 i, j를 사용하여, 일정 조건에 따라 `i와 j를 증감 연산자로` 이동시켜 불필요한 경우의 수를 제거하는 알고리즘. [(16472)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16472.java)
2. 슬라이딩 윈도우와 비슷하지만, 구간의 너비가 가변적이라는 차이점이 존재. [(Two Pointers)](https://butter-shower.tistory.com/226)

## Next Permutation [(Next Permutation)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/NextPermutation.md)

1. 현 순열을 기준으로 사전 순으로 다음 순열 생성해주는 알고리즘
2. 방문 체크를 수행하는 기존 순열보다 빠르지만, `nPn 문제에만 적용 가능`
3. 조합의 경우 `nCr을 계산하는 문제에서 활용 가능.` [(20366)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/20000/Main_20366.java)
4. 알고리즘 구현 방법  
   4-1. 배열을 오름차순으로 정렬한 후 시작 (가장 작은 순열)
   4-2. 뒤쪽부터 탐색하며 교환 위치 탐색 (i – 1), 꼭대기(i)의 앞이 교환 자리
   4-3. 뒤쪽부터 탐색하며 교환 위치와 교환할 큰 값 위치 탐색 (j)
   4-4. 두 위치 i - 1, j의 값 교환
   4-5. 꼭대기 위치 i부터 맨 뒤까지 오름차순 정렬

   ```
   public static boolean nextPermutation(int[] arr) {
   	int n = arr.length;

   	// i 탐색
   	int i = n - 1;
   	for(i = n - 1; i >= 1; i--) {
   		if(arr[i - 1] < arr[i]) break;
   	}
   	if(i == 0) return false;

   	// 바꿀 위치 탐색
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
   ```
