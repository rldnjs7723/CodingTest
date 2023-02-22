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

   ```java
   Scanner sc = new Scanner(System.in);
   int N = sc.nextInt();
   long M = sc.nextLong();

   sc.close;
   ```

2. 실수를 출력할 때 C에서와 같이 printf를 사용하여 실수의 자리수를 설정 가능 [(1008)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1008.java)

   ```java
   System.out.printf("%.12f", A / B);
   ```

3. 기본적으로 입출력 속도는 BufferedReader / BufferedWriter를 사용하는 것이 빠르며, 입력 값을 나눌 때는 StringTokenizer를 사용하고, 출력 문자열 값을 합칠 때는 StringBuilder를 사용하는 것이 좋다. [(1230)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/1000/Solution_1230.java)

   ```java
   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

   StringTokenizer st = new StringTokeninzer(br.readLine());
   int N = Integer.parseInt(st.nextToken());
   StringBuilder sb = new StringBuilder();
   sb.append("#");
   bw.write(sb.toString());

   bw.close; // 자동 flush
   br.close;
   ```

## 알면 도움되는 메서드 / 클래스 변수

### Integer

1. Integer.MAX_VALUE / MIN_VALUE : 정수의 최대, 최소 값
2. Integer.toBinaryString() : 정수를 이진수로 변환. [비트마스킹](#bitmasking-비트마스킹) 디버깅에 유용 [(10726)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/10000/Solution_10726.java)
3. Integer.compare() : compareTo 메서드 Override 할 때 유용 [(20366)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/20000/Main_20366.java)

### Arrays

1. Arrays.fill()
2. Arrays.sort(). 배열을 정렬. 새로 정의한 클래스에 적용하려면 Comparable 인터페이스 구현 필요.  
   정렬할 배열의 범위를 지정할 수도 있음 sort(배열, 시작 idx, 끝 idx); (끝 idx 미포함) [(20366)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/20000/Main_20366.java)
3. Arrays.binarySearch(). 이진 탐색으로 특정 값이 존재하는 (들어가야 하는) Index를 찾는 메서드 [(이진 탐색)](#binary-search-이진-탐색)

# 알고리즘

소스 코드가 들어가 있는 경우는 구현 방식 자체를 외워야 하는 것들

## 목차

1. [Math (수학)](#math-수학)

   1. [Prime Number (소수)](#prime-number-소수)

2. [Brute Force (완전 탐색)](#brute-force-완전-탐색)

   1. [Direction Search (사방 탐색)](#direction-search-사방-탐색)
   2. [Depth First Search (깊이 우선 탐색)](#depth-first-search-깊이-우선-탐색)
   3. [Breadth First Search (너비 우선 탐색)](#breadth-first-search-너비-우선-탐색)
   4. [Permutation (순열)](#permutation-순열)
   5. [Combination (조합)](#combination-조합)

   6. [Next Permutation (다음 순열)](#next-permutation-다음-순열)

3. [Sliding Window (슬라이딩 윈도우)](#sliding-window-슬라이딩-윈도우)
4. [Two Pointers (투포인터 알고리즘)](#two-pointers-투포인터-알고리즘)
5. [Data Structure (자료 구조)](#data-structure-자료-구조)
   1. [Stack (스택)](#stack-스택)
   2. [Queue (큐)](#queue-큐)
   3. [Tree (트리)](#tree-트리)
   4. [Heap (힙)](#heap-힙)
   5. [LinkedList (연결 리스트)](#linkedlist-연결-리스트)
6. [Bitmasking (비트마스킹)](#bitmasking-비트마스킹)
7. [Greedy Algorithm (그리디 알고리즘)](#greedy-algorithm-그리디-알고리즘)
8. [Divide and Conquer (분할 정복)](#divide-and-conquer-분할-정복)

   1. [Binary Search (이진 탐색)](#binary-search-이진-탐색)
   2. [Longest Increasing Subsequence (LIS, 최장 증가 부분 수열)](#longest-increasing-subsequence-lis-최장-증가-부분-수열-참고)

9. [Backtracking (백트래킹)](#backtracking-백트래킹)
10. [Recursive (재귀)](#recursive-재귀)
11. [Dynamic Programming (다이나믹 프로그래밍)](#dynamic-programming-다이나믹-프로그래밍)
12. [String (문자열)](#string-문자열)
    1. [Longest Common Subsequence (LCS)](#longest-common-subsequence-lcs-9252)
13. [Union Find (Disjoint Set)](#union-find-disjoint-set)
14. [Graph (그래프 탐색 알고리즘)](#graph-그래프-탐색-알고리즘)
    1. [Dijkstra (다익스트라 알고리즘)](#dijkstra-다익스트라-알고리즘)
    2. [Bellman-Ford (밸만-포드 알고리즘)](#bellman-ford-밸만-포드-알고리즘)
    3. [Floyd-Warshall (플로이드-워셜 알고리즘)](#floyd-warshall-플로이드-워셜-알고리즘)
    4. [Minimum Spanning Tree (최소 신장 트리)](#minimum-spanning-tree-최소-신장-트리)
       1. [Kruskal (크루스칼 알고리즘)](#1-kruskal-크루스칼-알고리즘)
       2. [Prim (프림 알고리즘)](#2-prim-프림-알고리즘)

# Math [(수학)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Math.md)

1. 일반적으로 수열이 주어졌을 때 규칙을 찾아서 계산 식으로 표현해야 하는 경우가 많다. [(9527)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9527.java)

## Prime Number [(소수)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/PrimeNumber.md)

1. N의 약수 중 가장 큰 것은 N/2보다 작거나 같음
2. 숫자 N이 소수가 아니라면 root(N)을 기준으로 대칭 구조이므로 root(N)보다 작거나 같은 수로 나누어 떨어지면 소수가 아님
3. 에라토스테네스의 체 [(1644)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1644.java)

   1. N까지의 수 중에서 소수를 판별하기 위해, 2부터 시작하여 각각의 소수의 배수인 값들을 전부 소수가 아니라고 처리하는 방법
   2. 위 소수의 2번 특성을 활용하여 다음과 같이 제곱이 범위 N보다 작거나 같을 경우에만 소수가 아닌 수를 배제하면 됨

   ```java
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

# Brute Force [(완전 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/BruteForce.md)

1. 가능한 모든 경우의 수를 탐색하여 최적의 해를 찾는 방법. 시간이 많이 소요됨
2. N의 수가 합리적으로 작은 경우에서 시도 가능. `시간 복잡도 계산 필수`
3. 완전 탐색 문제를 풀 때 항상 고려할 사항
   1. 백트래킹 (가지치기): `확실하게` 아닌 경우만 탐색을 수행하지 않도록 해야 함
   2. 메모이제이션: 동일한 부분에서 연산을 반복하면 기존에 계산한 결과를 저장해두었다가 활용할 필요가 있음

### Direction Search (사방 탐색)

1. 2차원 배열 구조에서 왼쪽, 오른쪽, 위, 아래에 위치한 원소를 탐색하는 방식
2. 왼쪽, 오른쪽, 위, 아래를 숫자로만 표현하면 혼란스럽기 때문에 실수를 방지하기 위해 다음과 같이 상수 선언해서 문제를 해결하는 것을 추천

   ```java
   public static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
   public static final int[] dRow = {0, -1, 0, 1};
   public static final int[] dCol = {-1, 0, 1, 0};
   ```

### Depth First Search (깊이 우선 탐색)

1. [트리 구조](#tree-트리)에서 루트 노드부터 더 이상 자식 노드가 없을 때까지 한 방향으로(왼쪽 자식) 탐색하고, 이후에는 마지막에 만난 갈림길에서 다른 방향으로 탐색을 수행하는 방식
2. 일반적으로 가장 마지막에 발견한 위치를 가장 먼저 탐색하기 때문에 후입 선출 구조인 [Stack](#stack-스택)을 활용하여 구현.
3. 다만 [재귀](#recursive-재귀) 깊이가 너무 깊지 않은 경우 재귀로 구현했을 때의 속도가 더 빠르기 때문에 비트마스킹을 이용한 재귀로 구현하는 것을 추천 [(3234)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/3000/Solution_3234.java)

### Breadth First Search (너비 우선 탐색)

1. [트리 구조](#tree-트리)에서 루트 노드의 자식 노드를 먼저 방문한 후 방문한 자식 노드에서 자식 노드를 차례로 방문하는 방식
2. 일반적으로 같은 높이에 위치한 노드를 먼저 탐색하려면 먼저 발견한 위치를 먼저 탐색해야 하기 때문에 선입 선출 구조인 [Queue](#queue-큐)를 활용해서 구현

### Permutation (순열)

1. 가능한 모든 순서쌍에 대해 재귀나 Stack을 이용하여 DFS를 통한 완전 탐색을 수행

### Combination (조합)

1. 특정 원소의 선택 여부를 통해 부분 집합을 구성하는 문제로 자주 등장
2. 완전 탐색으로 수행할 경우 선택 / 비선택의 경우로 시간 복잡도 O(2^N)

## Next Permutation [(다음 순열)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/NextPermutation.md)

1. 현 순열을 기준으로 사전 순으로 다음 순열 생성해주는 알고리즘
2. 방문 체크를 수행하는 기존 순열보다 빠르지만, `nPn 문제에만 적용 가능`
3. 조합의 경우 `nCr을 계산하는 문제에서 활용 가능.` [(20366)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/20000/Main_20366.java)
4. 알고리즘 구현 방법

   1. 배열을 오름차순으로 정렬한 후 시작 (가장 작은 순열)
   2. 뒤쪽부터 탐색하며 교환 위치 탐색 (i – 1), 꼭대기(i)의 앞이 교환 자리
   3. 뒤쪽부터 탐색하며 교환 위치와 교환할 큰 값 위치 탐색 (j)
   4. 두 위치 i - 1, j의 값 교환
   5. 꼭대기 위치 i부터 맨 뒤까지 오름차순 정렬

   ```java
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

# Sliding Window [(슬라이딩 윈도우)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/SlidingWindow.md)

1. 고정 사이즈의 윈도우가 이동하면서 윈도우 내에 있는 데이터로 문제를 푸는 방식

# Two Pointers [(투포인터 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/TwoPointers.md)

1. index 값을 가지는 i, j를 사용하여, 일정 조건에 따라 `i와 j를 증감 연산자로` 이동시켜 불필요한 경우의 수를 제거하는 알고리즘. [(16472)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16472.java)
2. 슬라이딩 윈도우와 비슷하지만, 구간의 너비가 가변적이라는 차이점이 존재. [(Two Pointers)](https://butter-shower.tistory.com/226)

# Data Structure [(자료 구조)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DataStructure.md)

1. 스택, 큐, 트리, 힙, 리스트와 같은 기본적인 자료 구조는 직접 구현할 수 있어야 B형 문제 풀이를 대비할 수 있음 [(13072)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/13000/Solution_13072.java)

## Stack (스택)

1. 후입 선출 구조 (LIFO, Last-In-First-Out): 마지막에 삽입한 자료를 가장 먼저 꺼냄
2. [DFS](#depth-first-search-깊이-우선-탐색) 구현에 자주 사용

## Queue (큐)

1. 선입 선출 구조 (FIFO, First In First Out): 가장 먼저 삽입된 원소는 가장 먼저 삭제
2. Queue는 인터페이스로 구현되어 있고, 실제 사용할 때는 `ArrayDeque 클래스`를 사용하는 것이 속도 측면에서 좋음
3. [BFS](#breadth-first-search-너비-우선-탐색) 구현에 자주 사용

## Tree (트리)

1. 정의: 모든 정점 사이에 경로가 존재하고 / |E| = |V| – 1인 그래프
2. 위 정의에 따라 사이클이 존재하지 않는 특징을 가짐
3. 용어
   1. 차수: 노드에 연결된 자식 노드의 수 (노드 기준) / 루트 노드의 차수 (트리 기준)
   2. 높이: 루트에서 노드에 이르는 간선의 수 (노드 기준) / 트리에서 노드의 높이 중 가장 큰 값 (트리 기준)
4. 트리 순회
   1. 전위 순회: 부모 노드 -> 왼쪽 자식 -> 오른쪽 자식 순으로 방문
   2. 중위 순회: 왼쪽 자식 -> 부모 노드 -> 오른쪽 자식 순으로 방문
   3. 후위 순회: 왼쪽 자식 -> 오른쪽 자식 -> 부모 노드 순으로 방문

## Heap (힙)

1. 완전 이진 트리 중 자식 노드가 부모 노드보다 작은(최대 힙) / 큰(최소 힙) 조건을 만족하는 트리
2. 일반적으로 최댓값, 최솟값을 빠르게 O(log N) 시간 안에 찾기 위해 사용하는 자료 구조
3. [Dijkstra 알고리즘](#dijkstra-다익스트라-알고리즘)에서 활용하면 탐색 시간을 줄일 수 있음
4. PriorityQueue 클래스로 기본 연산 수행 가능. Comparator를 따로 설정해주면 힙 정렬 상태도 수정 가능
   1. 최소힙: PriorityQueue()
   2. 최대힙: PrioirtyQueue(Collections.reverseOrder())

## Set (집합)

1. 특정한 순서를 가지지 않고 (입력 순서를 유지하지 않고), 중복을 허용하지 않는 자료구조
2. equals와 hashCode 메서드를 override하지 않으면 객체의 주소값을 기준으로 중복을 판단.
3. 자바에서는 대표적으로 HashSet과 TreeSet을 많이 사용
   1. HashSet: O(1) 시간으로 참조하고 싶을 때 사용. 일반적으로 가장 많이 활용
   2. TreeSet: O(log N) 시간 복잡도를 가지지만, Set 내부의 값을 정렬된 상태로 유지하고 싶을 때 사용.  
      Comparable 인터페이스를 통해 compareTo 메서드를 Override 하면 사용자가 정의한 기준으로 정렬을 수행 [(5644)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/5000/Solution_5644.java)

## LinkedList (연결 리스트)

1. 연산 시간 복잡도 비교
   1. Array: 정적, 조회 O(1), 삽입/삭제 O(N)
   2. ArrayList: 동적, 조회 O(1), 삽입/삭제 O(N)
   3. LinkedList: 동적, 조회 O(N), 삽입/삭제 O(1)
2. 기본적으로 A형 알고리즘 문제를 풀 때는 ArrayList를 사용하는 것이 메모리, 시간적인 측면에서 더 좋은 결과를 가져옴.

# Bitmasking [(비트마스킹)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Bitmasking.md)

1. 각각의 비트를 하나의 플래그로 사용하여 상태를 표현할 때 사용
2. 여러 개의 데이터를 압축하여 하나의 정수 자료형으로 나타낼 때 사용
3. 비트 연산자

   1. 비트 추가할 때: | 연산자
   2. 비트 체크할 때: & 연산자
   3. 비트 제거할 때: - 연산자
   4. 비트 반전시킬 때: ~ 연산자
   5. 비트 가득 찬 상태와 비교할 때 XOR 연산자 ^도 가끔 사용

4. 팁
   1. 비트 선언할 때 0b1111과 같이 `이진수로 숫자를 입력할 수 있다.` [(2615)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2615.java)
   2. Integer.toBinaryString()으로 정수 자료형을 이진수로 출력할 수 있다.

# Greedy Algorithm [(그리디 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Greedy.md)

1. 여러 경우 중 하나를 선택할 때 그 순간에 최적이라고 생각되는 것을 선택하는 알고리즘.
   1. 탐욕적 선택이 최적해로 갈 수 있음이 증명된 경우에만 사용
   2. 최적 부분 구조: 탐욕적 선택 이후 동일한 하위 문제를 해결해야 하는 구조에 사용
2. `한 번 선택한 것을 번복하지 않음`
3. 규칙을 찾지 못하고 너무 복잡하게 해결하는 경우 일정 조건을 완전 탐색으로 제한하고 그리디로 접근하는 방식도 좋음 [(2138)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2138.java)

# Divide and Conquer [(분할 정복)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DevideAndConquer.md)

1. 1. 분할: 해결할 문제를 여러 작은 부분으로 나눔
   2. 정복: 나눈 작은 문제를 각각 해결
   3. 통합: 필요하면 해결된 해답을 모음
2. 시간 복잡도를 줄이기 위해 `현재의 문제를 크기가 작은 문제 여러 개로 분할하여 계산할 수 있는지 확인해볼 필요가 있다.` [(1799)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1799.java)

## Binary Search [(이진 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/BinarySearch.md)

1. 자료의 가운데에 있는 항목의 키 값과 비교하여 다음 검색의 위치를 결정하고 검색을 계속 진행하는 방식
2. `자료가 정렬된 상태에서만 가능`
3. 이진 탐색 도중에 다른 작업을 수행하지 않고, 특정 값이 위치할 Index를 찾으려고 한다면 Arrays.binarySearch() 메서드를 사용해도 된다.
   1. Arrays.binarySearch()에서 목표 값을 못 찾는 경우 해당 값이 위치해야 할 Index를 음수로 만든 후 -1을 더한다. (2번 인덱스에 있어야 했다면 -3을 반환)
   2. Arrays.binarySearch(배열, fromIndex, toIndex, key)로 범위 지정도 가능

## Longest Increasing Subsequence (LIS, 최장 증가 부분 수열) [(참고)](https://chanhuiseok.github.io/posts/algo-49/)

1. 이진 탐색 알고리즘의 일종
2. 알고리즘

   1. 각 부분 수열의 길이 값에 해당하는 가장 작은 값들을 기록. 자기 자신이 어떤 위치에 들어가야 하는지 확인할 때 `이분 탐색`으로 Index를 찾음.
   2. 자기 자신의 부분 수열 길이를 저장하는 배열을 따로 설정하고, 이후 마지막에 맨 뒤 (N - 1)부터 탐색하며 길이가 가장 긴 원소부터 차례대로 추출하여 최장 부분 수열을 출력

   ```java
   // 수열 각 위치별 최대 부분 수열 길이
   int[] sequenceCount = new int[N];
   // 현재 최장 subsequence 기록
   int[] subsequence = new int[N];
   int size = 1, idx;
   subsequence[0] = sequence[0];
   for(int i = 1; i < N; i++) {
   	idx = findIdx(sequence[i], 0, size - 1, subsequence);
   	// 자신보다 작은 값이 없으면 새로 추가
   	if(idx == size) subsequence[size++] = sequence[i];
   	// 현재 Index 값이 자신보다 크면 그 값을 교체
   	else subsequence[idx] = sequence[i];
   	// Sequence의 길이는 자신이 들어간 Index + 1
   	sequenceCount[i] = idx + 1;
   }

   // 이분 탐색으로 현재 수를 넣을 수 있는 위치 탐색
   public static int findIdx(int target, int start, int end, int[] sequence) {
   	int mid = (start + end) / 2;

   	if(sequence[mid] == target) return mid;
   	else if(sequence[mid] > target) {
   		// 마지막 위치의 값이 자신보다 크면 그 값을 교체
   		if(start == end) return start;
   		return findIdx(target, start, mid, sequence);
   	} else {
   		// 자신보다 작은 값이 없으면 새로 추가
   		if(start == end) return end + 1;
   		return findIdx(target, mid + 1, end, sequence);
   	}
   }
   ```

# Backtracking [(백트래킹)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Backtracking.md)

1. 해를 찾는 도중, 해가 아니라서 막히면 되돌아가서 다시 해를 찾아가는 기법
2. 되돌아 갈 때 이전 상태를 반복하지 않도록 하는 장치와 현재 선택했던 상태를 지우는 방법을 구현해주어야 한다. [(1799)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1799.java) [(2239)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2239.java)
3. DFS를 수행하면서 유망하지 않은 노드가 어떤 것인지 확실하게 판별해야 함. 완전탐색을 수행하면서 '시간 초과'나 '메모리 초과'가 아닌 '틀렸습니다'가 발생하는 경우 지금까지 당연히 맞다고 생각했던 조건을 다시 생각해볼 필요가 있음 [(6987)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/6000/Main_6987.java)

# Recursive [(재귀)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Recursive.md)

1. 자신의 정의하는 내용 안에 자신을 포함하는 형태의 함수
2. 주어진 문제의 해를 구하기 위해 `동일하면서 더 작은 문제(최적 부분 구조)`의 해를 이용하는 방법
3. 점화식 / 수열의 귀납적 정의에 자주 이용 [(9527)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9527.java)
4. [DFS](#depth-first-search-깊이-우선-탐색) 탐색을 수행할 때 Stack을 사용하는 경우보다 빠름. `재귀 깊이가 너무 깊지 않은 경우 재귀로 구현하는 것이 좋음` [(3234)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/3000/Solution_3234.java)

# Dynamic Programming [(다이나믹 프로그래밍)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DynamicProgramming.md)

1. 특정 범위까지의 값을 구하기 위해서 이전에 계산했던 다른 범위의 값을 이용하여 효율적으로 계산하는 알고리즘

# String [(문자열)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/String.md)

## Longest Common Subsequence (LCS) [(9252)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9252.java)

1. 다이나믹 프로그래밍을 통해 계산하는 알고리즘의 일종
2. 각 문자열의 길이만큼 행, 열을 가지는 2차원 배열을 설정한 뒤 각 문자열에 해당하는 알파벳이 동일하다면 대각선 방향(행 - 1, 열 - 1)의 값에 1을 더한다.
3. 문자열에 해당하는 알파벳이 다르다면 왼쪽(열 - 1)과 위(행 - 1)의 값 중 더 큰 값을 현재 행, 열의 값으로 설정한다.
4. 이 때 LCS의 길이는 각 문자열의 길이를 Index로 하는 위치에 기록된 값이며, 연산 도중 대각선, 왼쪽, 위를 기록해두었다면 역순으로 탐색하면서 대각선으로 이동한 경우의 문자를 추출한다면 LCS 자체를 출력할 수도 있다.

   ```java
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

# Union Find [(Disjoint Set)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/UnionFind.md)

# Graph [(그래프 탐색 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Graph.md)

1. 인접 행렬 / 인접 리스트 / 인접 해시테이블로 그래프 표현 가능
2. 인접 리스트의 경우 ArrayList로 구현했을 때 메모리를 생각보다 많이 잡아먹지 않음

## Dijkstra (다익스트라 알고리즘)

1. 시작점과 그래프가 주어졌을 때, 시작점에서부터 다른 정점까지의 최단 경로를 구하는 문제에 활용
2. `음의 가중치를 가지는 사이클이 존재할 때는 사용할 수 없다.`
3. 가중치 값이 가장 작은 정점을 찾을 때 [우선순위 큐](#heap-힙)를 활용하면 더 빠르게 수행할 수 있다. [(1753)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1753.java)
4. 알고리즘

   1. 출발 정점 이외의 나머지 정점은 가중치 무한으로 설정
   2. 출발 정점으로부터 다른 정점까지 도달하는 가중치 최솟값 갱신
   3. 방문하지 않은 정점 중 가장 가중치 값이 작은 정점 선택
   4. 해당 노드를 거쳐 다른 정점까지 도달하는 가중치 최솟값 갱신
   5. 3 ~ 4번을 더 이상 방문 할 정점이 없을 때까지 반복

   ```java
   // 가중치 최솟값을 가지는 정점을 빠르게 구하기 위한 우선순위 큐
   PriorityQueue<Vertex> queue = new PriorityQueue<>();
   // 시작 정점은 가중치 0
   graph[K].weight = 0;
   queue.offer(graph[K]);
   Vertex curr;
   while(!queue.isEmpty()) {
      // 가장 가중치가 작은 정점에서 가중치 갱신
      curr = queue.poll();
      // 가중치를 갱신한 정점은 우선순위 큐에 넣어서 이후에 해당 정점을 기준으로 다른 정점 가중치 갱신하기
      curr.updateWeight(graph, queue);
   }
   ```

## Bellman-Ford (밸만-포드 알고리즘)

## Floyd-Warshall (플로이드-워셜 알고리즘)

## Minimum Spanning Tree (최소 신장 트리)

### 1. Kruskal (크루스칼 알고리즘)

### 2. Prim (프림 알고리즘)
