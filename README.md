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

1. 입력의 크기가 작을 때, Scanner를 사용하면 정수, 실수 등의 입력을 간단하게 받을 수 있다.  
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
3. Arrays.binarySearch(). 이진 탐색으로 특정 값이 존재하는 (들어가야 하는) Index를 찾는 메서드 [(이진 탐색)](#binary-search-이진-탐색)

# 알고리즘

## 목차

1. [Math (수학)](#math-수학)
2. [Prime Number (소수)](#prime-number-소수)
3. [Recursive (재귀)](#recursive-재귀)
4. [Brute Force (완전 탐색, 순열, 조합)](#brute-force-완전-탐색-순열-조합)
5. [Next Permutation (다음 순열)](#next-permutation-다음-순열)
6. [Sliding Window (슬라이딩 윈도우)](#sliding-window-슬라이딩-윈도우)
7. [Two Pointers (투포인터 알고리즘)](#two-pointers-투포인터-알고리즘)
8. [Data Structure (스택, 큐, 트리)](#data-structure-스택-큐-트리)
9. [Direction Search (사방 탐색, 팔방 탐색)](#direction-search-사방-탐색-팔방-탐색)
10. [Depth First Search (깊이 우선 탐색)](#depth-first-search-깊이-우선-탐색)
11. [Breadth First Search (너비 우선 탐색)](#breadth-first-search-너비-우선-탐색)
12. [Bitmasking (비트마스킹)](#bitmasking-비트마스킹)
13. [Greedy Algorithm (그리디 알고리즘)](#greedy-algorithm-그리디-알고리즘)
14. [Divide and Conquer (분할 정복)](#divide-and-conquer-분할-정복)
15. [Binary Search (이진 탐색)](#binary-search-이진-탐색)
16. [Backtracking (백트래킹)](#backtracking-백트래킹)
17. [String (문자열)](#string-문자열)
18. [Graph (그래프 탐색 알고리즘)](#graph-그래프-탐색-알고리즘)

## Math [(수학)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Math.md)

## Prime Number [(소수)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/PrimeNumber.md)

1. N의 약수 중 가장 큰 것은 N/2보다 작거나 같음
2. 숫자 N이 소수가 아니라면 root(N)을 기준으로 대칭 구조이므로 root(N)보다 작거나 같은 수로 나누어 떨어지면 소수가 아님
3. 에라토스테네스의 체 [(1644)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1644.java)  
   3-1. N까지의 수 중에서 소수를 판별하기 위해, 2부터 시작하여 각각의 소수의 배수인 값들을 전부 소수가 아니라고 처리하는 방법  
   3-2. 위 소수의 2번 특성을 활용하여 다음과 같이 제곱이 범위 N보다 작거나 같을 경우에만 소수가 아닌 수를 배제하면 됨

   ```
   // 에라토스테네스의 체로 0부터 N까지 소수 판별
   public static void eratosthenes(boolean[] numbers) {
   	// 0과 1은 배제
   	numbers[0] = NOTPRIME;
   	numbers[1] = NOTPRIME;

   	// 제곱이 범위 안에 포함되는 경우만 소수가 아닌 것을 판별할 수 있음
   	for(int i = 2; i * i <= numbers.length; i = getNextPrimeNumber(i, numbers)) {
   		if(i == NULL) break;

   		// 현재 수의 배수는 전부 소수가 아님
   		for(int j = 2; i * j < numbers.length; j++) {
   			numbers[i * j] = NOTPRIME;
   		}
   	}
   }
   ```

## Recursive [(재귀)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Recursive.md)

## Brute Force [(완전 탐색, 순열, 조합)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/BruteForce.md)

## Next Permutation [(다음 순열)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/NextPermutation.md)

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

## Sliding Window [(슬라이딩 윈도우)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/SlidingWindow.md)

1. 고정 사이즈의 윈도우가 이동하면서 윈도우 내에 있는 데이터로 문제를 푸는 방식

## Two Pointers [(투포인터 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/TwoPointers.md)

1. index 값을 가지는 i, j를 사용하여, 일정 조건에 따라 `i와 j를 증감 연산자로` 이동시켜 불필요한 경우의 수를 제거하는 알고리즘. [(16472)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16472.java)
2. 슬라이딩 윈도우와 비슷하지만, 구간의 너비가 가변적이라는 차이점이 존재. [(Two Pointers)](https://butter-shower.tistory.com/226)

## Data Structure [(스택, 큐, 트리)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DataStructure.md)

1. 비트 선언할 때 0b1111과 같이 이진수로 숫자를 입력할 수 있다.

## Direction Search [(사방 탐색, 팔방 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DirectionSearch.md)

## Depth First Search [(깊이 우선 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DepthFirstSearch.md)

## Breadth First Search [(너비 우선 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/BreadthFirstSearch.md)

## Bitmasking [(비트마스킹)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Bitmasking.md)

## Greedy Algorithm [(그리디 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Greedy.md)

1. 여러 경우 중 하나를 선택할 때 그 순간에 최적이라고 생각되는 것을 선택하는 알고리즘.  
   1-1. 탐욕적 선택이 최적해로 갈 수 있음이 증명된 경우에만 사용  
   1-2. 최적 부분 구조: 탐욕적 선택 이후 동일한 하위 문제를 해결해야 하는 구조에 사용
2. `한 번 선택한 것을 번복하지 않음`
3. 규칙을 찾지 못하고 너무 복잡하게 해결하는 경우 일정 조건을 완전 탐색으로 제한하고 그리디로 접근하는 방식도 좋음 [(2138)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2138.java)

## Divide and Conquer [(분할 정복)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DevideAndConquer.md)

1. 1-1. 분할: 해결할 문제를 여러 작은 부분으로 나눔  
   1-2. 정복: 나눈 작은 문제를 각각 해결  
   1-3. 통합: 필요하면 해결된 해답을 모음
2. 시간 복잡도를 줄이기 위해 `현재의 문제를 크기가 작은 문제 여러 개로 분할하여 계산할 수 있는지 확인해볼 필요가 있다.` [(1799)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1799.java)

## Binary Search [(이진 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/BinarySearch.md)

1. 자료의 가운데에 있는 항목의 키 값과 비교하여 다음 검색의 위치를 결정하고 검색을 계속 진행하는 방식
2. `자료가 정렬된 상태에서만 가능`
3. 이진 탐색 도중에 다른 작업을 수행하지 않고, 특정 값이 위치할 Index를 찾으려고 한다면 Arrays.binarySearch() 메서드를 사용해도 된다.  
   3-1. Arrays.binarySearch()에서 목표 값을 못 찾는 경우 해당 값이 위치해야 할 Index를 음수로 만든 후 -1을 더한다. (2번 인덱스에 있어야 했다면 -3을 반환)  
   3-2. Arrays.binarySearch(배열, fromIndex, toIndex, key)로 범위 지정도 가능

## Backtracking [(백트래킹)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Backtracking.md)

1. 해를 찾는 도중, 해가 아니라서 막히면 되돌아가서 다시 해를 찾아가는 기법
2. 되돌아 갈 때 이전 상태를 반복하지 않도록 하는 장치와 현재 선택했던 상태를 지우는 방법을 구현해주어야 한다. [(1799)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1799.java) [(2239)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2239.java)

## String [(문자열)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/String.md)

1. Longest Common Subsequence (LCS) [(9252)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9252.java)  
   1-1. 다이나믹 프로그래밍을 통해 계산하는 알고리즘의 일종  
   1-2. 각 문자열의 길이만큼 행, 열을 가지는 2차원 배열을 설정한 뒤 각 문자열에 해당하는 알파벳이 동일하다면 대각선 방향(행 - 1, 열 - 1)의 값에 1을 더한다.  
   1-3. 문자열에 해당하는 알파벳이 다르다면 왼쪽(열 - 1)과 위(행 - 1)의 값 중 더 큰 값을 현재 행, 열의 값으로 설정한다.  
   1-4. 이 때 LCS의 길이는 각 문자열의 길이를 Index로 하는 위치에 기록된 값이며, 연산 도중 대각선, 왼쪽, 위를 기록해두었다면 역순으로 탐색하면서 대각선으로 이동한 경우의 문자를 추출한다면 LCS 자체를 출력할 수도 있다.

   ```
   // LCS 길이 저장
   	int[][] lcsCount = new int[lenA + 1][lenB + 1];
   	// LCS 구성할 때 이동 방향 저장
   	int[][] lcsDirection = new int[lenA + 1][lenB + 1];
   	for(int i = 0; i < lenA; i++) {
   		for(int j = 0; j < lenB; j++) {
   			if(A.charAt(i) == B.charAt(j)) {
   				// 현재 알파벳이 동일하면 대각선 방향 + 1
   				lcsCount[i + 1][j + 1] = lcsCount[i][j] + 1;
   				lcsDirection[i + 1][j + 1] = DIAGONAL;
   			} else {
   				// 알파벳이 다르면 왼쪽과 위의 값중 더 큰 값을 할당
   				if(lcsCount[i + 1][j] >= lcsCount[i][j + 1]) {
   					lcsCount[i + 1][j + 1] = lcsCount[i + 1][j];
   					lcsDirection[i + 1][j + 1] = LEFT;
   				} else {
   					lcsCount[i + 1][j + 1] = lcsCount[i][j + 1];
   					lcsDirection[i + 1][j + 1] = UP;
   				}
   			}
   		}
   	}
   ```

## Graph [(그래프 탐색 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Graph.md)
