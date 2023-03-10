# Shell 파일 사용법

1. ./execute.sh <사이트 타입> <문제 번호>
2. <사이트 타입> = BOJ: B, SWEA: S, Programmers: P
3. <문제 번호> = 숫자로 그대로 입력

# 자바 코딩테스트 팁

1. 입력 / 출력 값이 Overflow가 발생하는지 항상 수학적으로 계산하여 int 대신 long을 사용할 필요가 있는지 고려해야 함
2. 시간 복잡도는 1억 번의 연산을 1초로 넉넉하게 두고 풀기
3. 소프트웨어 역량 평가 A형의 경우 기본적으로 확인하는 능력이 `조건을 치환하는 능력`.  
   주어진 조건 중에서 불필요한 내용은 생략하고, 주어진 조건을 시간 복잡도를 감소시킬 수 있는 형태로 표현하는 것이 중요하다. [(5644)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DataStructure.md/#3-swea_5644-무선-충전-swea-링크-소스-코드) [(1767)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Backtracking.md/#3-swea_1767-프로세서-연결하기-swea-링크-소스-코드)
4. 재귀를 돌릴 때 깊이가 5000 이상으로 들어갈 것 같다면 Stack이 터질 수 있기 때문에, 이 경우 반복문으로 수정하는 것이 좋다. 특히, 기본 예제는 다 되는데 입력이 큰 예제에서 Segmentation Fault가 발생한다면 이를 의심하는 것이 좋다.
5. 시간 초과나 틀렸습니다가 발생할 때, 놓친 조건이 없는지 다시 천천히 읽어 보자. 접근 자체가 틀렸다면 어차피 그 문제는 못 푼다.

## 이클립스 사용법

1. Ctrl + F11: 실행
2. Ctrl + Space: 자동완성.
3. sysout 입력 후 Ctrl + Space를 누르면 System.out.println()이 입력 [(2558)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2558.java)
4. 디버거
   1. 디버거 설정
      1. Preferences -> Java -> Debug -> Detail Formatters
         -> 하단의 Show variable details에서 As the label for all variables 체크
      2. Preferences -> Java -> Debug -> Step Filtering
         -> Use Step Filters 체크 후 하단의 Defined step filters 전부 체크
   2. 디버거 조작법
      1. F11 누르면 디버그 모드 실행.
      2. F6 누르면 다음 라인 실행
      3. F8 누르면 끝까지 실행
      4. 코드 줄 왼쪽을 클릭해서 원이 생기면 breakpoint 생성된 것

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

1. [Set 메서드](https://github.com/rldnjs7723/CodingTest#set-%EC%A7%91%ED%95%A9)

### Integer

1. Integer.MAX_VALUE / MIN_VALUE : 정수의 최대, 최소 값
2. Integer.toBinaryString() : 정수를 이진수로 변환. [비트마스킹](#bitmasking-비트마스킹) 디버깅에 유용 [(10726)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/10000/Solution_10726.java)
3. Integer.compare() : compareTo 메서드 Override 할 때 유용 [(20366)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/20000/Main_20366.java)

### Arrays

1. Arrays.fill()
2. Arrays.sort(). 배열을 정렬. 새로 정의한 클래스에 적용하려면 Comparable 인터페이스 구현 필요.  
   정렬할 배열의 범위를 지정할 수도 있음 sort(배열, 시작 idx, 끝 idx); (끝 idx 미포함) [(20366)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/20000/Main_20366.java)
3. Arrays.binarySearch(). 이진 탐색으로 특정 값이 존재하는 (들어가야 하는) Index를 찾는 메서드 [(이진 탐색)](#binary-search-이진-탐색)

### Math

1. 버림: Math.floor()
2. 올림: Math.ceil()
3. 반올림: Math.rount()
4. 절댓값: Math.abs()
5. 최대/최소: Math.Max(a, b) | Math.min(a, b)

# 알고리즘

소스 코드가 들어가 있는 경우는 구현 방식 자체를 외워야 하는 것들

## 목차

1. [Math (수학)](#math-수학)

   1. [Prime Number (소수)](#prime-number-소수)
   2. [Modulo (나머지 연산)](#modulo-나머지-연산)

2. [Brute Force (완전 탐색)](#brute-force-완전-탐색)

   1. [Direction Search (사방 탐색)](#direction-search-사방-탐색)
   2. [Depth First Search (깊이 우선 탐색)](#depth-first-search-깊이-우선-탐색)
   3. [Breadth First Search (너비 우선 탐색)](#breadth-first-search-너비-우선-탐색)
   4. [Permutation (순열)](#permutation-순열)
   5. [Combination (조합)](#combination-조합)

   6. [Next Permutation (다음 순열)](#next-permutation-다음-순열)

3. [Two Pointers (투포인터 알고리즘)](#two-pointers-투포인터-알고리즘)

   1. [Sliding Window (슬라이딩 윈도우)](#sliding-window-슬라이딩-윈도우)

4. [Data Structure (자료 구조)](#data-structure-자료-구조)

   1. [Stack (스택)](#stack-스택)
   2. [Queue (큐)](#queue-큐)
   3. [Tree (트리)](#tree-트리)
   4. [Heap (힙)](#heap-힙)
   5. [Set (집합)](#set-집합)
   6. [LinkedList (연결 리스트)](#linkedlist-연결-리스트)

5. [Bitmasking (비트마스킹)](#bitmasking-비트마스킹)
6. [Greedy Algorithm (그리디 알고리즘)](#greedy-algorithm-그리디-알고리즘)
7. [Divide and Conquer (분할 정복)](#divide-and-conquer-분할-정복)

   1. [Binary Search (이진 탐색)](#binary-search-이진-탐색)
   2. [Longest Increasing Subsequence (LIS, 최장 증가 부분 수열)](#longest-increasing-subsequence-lis-최장-증가-부분-수열-참고)

8. [Backtracking (백트래킹)](#backtracking-백트래킹)
9. [Recursive (재귀)](#recursive-재귀)
10. [Dynamic Programming (다이나믹 프로그래밍)](#dynamic-programming-다이나믹-프로그래밍)

    1. [Longest Common Subsequence (LCS)](#longest-common-subsequence-lcs-9252)
    2. [Lowest Common Ancestor (LCA) 알고리즘](#lowest-common-ancestor-lca-알고리즘)
    3. [Traveling Salesman Problem (TSP, 외판원 문제)](#traveling-salesman-problem-tsp-외판원-문제)

11. [Union Find (Disjoint Set)](#union-find-disjoint-set)
    1. [Flood Fill 알고리즘](#flood-fill-알고리즘)
12. [Graph (그래프 탐색 알고리즘)](#graph-그래프-탐색-알고리즘)

    1. [Dijkstra (다익스트라 알고리즘)](#dijkstra-다익스트라-알고리즘)
    2. [Bellman-Ford (밸만-포드 알고리즘)](#bellman-ford-밸만-포드-알고리즘)
    3. [Floyd-Warshall (플로이드-워셜 알고리즘)](#floyd-warshall-플로이드-워셜-알고리즘)
    4. [Minimum Spanning Tree (최소 신장 트리)](#minimum-spanning-tree-최소-신장-트리)
       1. [Kruskal (크루스칼 알고리즘)](#1-kruskal-크루스칼-알고리즘)
       2. [Prim (프림 알고리즘)](#2-prim-프림-알고리즘)
    5. [Topological Sort (위상 정렬)](#topological-sort-위상-정렬)

       1. [DAG에서의 최단 경로 (최장 경로)](#dag에서의-최단-경로-최장-경로)

    6. [Strongly Connected Component (강한 결합 요소)](#strongly-connected-component-강한-결합-요소)

13. [String (문자열)](#string-문자열)
    1. [Trie (트라이)](#trie-트라이)
14. [Segment Tree (세그먼트 트리)](#segment-tree-세그먼트-트리)

15. [Sweeping (스위핑 알고리즘)](#sweeping-스위핑-알고리즘)

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

## Modulo [(나머지 연산)]

다음 성질은 분할 정복을 통한 거듭 제곱 문제에서 자주 사용됨.

1. 분배 법칙 [(1328)](https://www.acmicpc.net/problem/1328)  
   나누기에 대해서는 성립하지 않는다.
   ```
   (a × b) % c = ((a % c) × (b % c)) % c
   (a ± b) % c = ((a % c) ± (b % c)) % c
   ```
2. 페르마의 소정리 [(13172)](https://www.acmicpc.net/problem/13172)  
   X가 소수일 때,  
   $\frac{a}{b}$ mod X = (a × b<sup>-1</sup>) mod X  
   b<sup>X - 2</sup> = b<sup>-1</sup> mod X
3. 이항 계수 [(11401)](https://www.acmicpc.net/problem/11401)  
   <sub>n</sub>C<sub>r</sub> = $\frac{n!}{r! × (n - r)!}$  
   이항 계수의 나머지를 계산할 때, 나누기에 대해서는 분배 법칙이 성립하지 않기 때문에  
   페르마의 소정리를 통해 (r! × (n - r)!)<sup>-1</sup>의 나머지를 계산하여  
   ((n!) % X + (r! × (n - r)!)<sup>-1</sup> % X) % X를 계산하면  
   <sub>n</sub>C<sub>r</sub>를 X로 나눈 나머지를 계산할 수 있다.

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

3. 위 구조처럼 왼쪽 -> 위 -> 오른쪽 -> 아래 순서로 탐색을 수행할 경우, 현재 방향의 반대 방향을 다음과 같이 나타낼 수 있다. [(16724)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16724.java)
   ```
   opposite = (dir + 2) % 4
   ```
4. 방향에 따라 달라지는 값이 있다면 해당 값을 2번과 같이 미리 정의해두고 사용하면 시간도 적게 걸리고 덜 혼동 된다. [(15683)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/15000/Main_15683.java)

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
4. 알고리즘

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

# Two Pointers [(투포인터 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/TwoPointers.md)

1.  index 값을 가지는 i, j를 사용하여, 일정 조건에 따라 `i와 j를 증감 연산자로` 이동시켜 불필요한 경우의 수를 제거하는 알고리즘. [(16472)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16472.java)
2.  슬라이딩 윈도우와 비슷하지만, 구간의 너비가 가변적이라는 차이점이 존재. [(Two Pointers)](https://butter-shower.tistory.com/226)

## Sliding Window (슬라이딩 윈도우)

1.  고정 사이즈의 윈도우가 이동하면서 윈도우 내에 있는 데이터로 문제를 푸는 방식

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
2. 특징: 위 정의에 따라 사이클이 존재하지 않는 특징을 가짐
3. 용어
   1. 차수(크기): 노드에 연결된 자식 노드의 수 (노드 기준) / 루트 노드의 차수 (트리 기준)
   2. 높이(height, 레벨, level): 노드에서 리프 노드에 이르는 간선의 수 (노드 기준) / 루트 노드에서 리프 노드에 이르는 간선의 수 (트리 기준)
   3. 깊이(depth): 루트 노드에서 노드에 이르는 간선의 수 (노드 기준) / 루트 노드에서 리프 노드까지 이르는 간선의 수 (트리 기준)
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
5. 원하는 객체 중에서 정렬 조건을 만족했을 때 가장 앞에 나오는 객체를 고르고 싶다면 Comparable 인터페이스를 구현한 뒤 PriorityQueue에 집어넣으면 됨. [(16236)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16236.java)

## Set (집합)

1. 특정한 순서를 가지지 않고 (입력 순서를 유지하지 않고), 중복을 허용하지 않는 자료구조
2. equals와 hashCode 메서드를 override하지 않으면 객체의 주소값을 기준으로 중복을 판단.
3. 자바에서는 대표적으로 HashSet과 TreeSet을 많이 사용
   1. HashSet: O(1) 시간으로 참조하고 싶을 때 사용. 일반적으로 가장 많이 활용
   2. TreeSet: O(log N) 시간 복잡도를 가지지만, Set 내부의 값을 정렬된 상태로 유지하고 싶을 때 사용.  
      Comparable 인터페이스를 통해 compareTo 메서드를 Override 하면 사용자가 정의한 기준으로 정렬을 수행 [(5644)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/5000/Solution_5644.java)
4. 알면 유용한 메서드
   1. 부분집합: .containsAll(집합) => 부분집합이라면 true
   2. 교집합: .retainAll(집합) => 교집합을 리턴
   3. 합집합: .addAll(집합) => 합집합을 리턴
   4. 차집합: .removeAll(집합) => 차집합을 리턴
   5. TreeSet 메서드
      1. 가장 앞의 객체: treeSet.first()
      2. 가장 뒤의 객체: treeSet.last()
      3. 특정 객체보다 한 단계 작은 객체: treeSet.lower(객체)
      4. 특정 객체보다 한 단계 큰 객체: treeSet.higher(객체)

## LinkedList (연결 리스트)

1. 연산 시간 복잡도 비교
   1. Array: 정적, 조회 O(1), 삽입/삭제 O(N)
   2. ArrayList: 동적, 조회 O(1), 삽입/삭제 O(N)
   3. LinkedList: 동적, 조회 O(N), 삽입/삭제 O(1)
2. 기본적으로 A형 알고리즘 문제를 풀 때는 ArrayList를 사용하는 것이 메모리, 시간적인 측면에서 더 좋은 결과를 가져옴.
3. 리스트에 삽입, 삭제 연산을 여러 번 수행해야 할 경우 뒤 인덱스부터 수행하면 불필요한 처리 작업을 수행하지 않아도 된다.

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

## Longest Common Subsequence (LCS) [(9252)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9252.java)

1. 다이나믹 프로그래밍을 통해 계산하는 알고리즘의 일종
2. 알고리즘

   1. 각 문자열의 길이만큼 행, 열을 가지는 2차원 배열을 설정한 뒤 각 문자열에 해당하는 알파벳이 동일하다면 대각선 방향(행 - 1, 열 - 1)의 값에 1을 더한다.
   2. 문자열에 해당하는 알파벳이 다르다면 왼쪽(열 - 1)과 위(행 - 1)의 값 중 더 큰 값을 현재 행, 열의 값으로 설정한다.
   3. 이 때 LCS의 길이는 각 문자열의 길이를 Index로 하는 위치에 기록된 값이며, 연산 도중 대각선, 왼쪽, 위를 기록해두었다면 역순으로 탐색하면서 대각선으로 이동한 경우의 문자를 추출한다면 LCS 자체를 출력할 수도 있다.

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

## Lowest Common Ancestor (LCA) 알고리즘

1. 트리에서 2차원 배열로 조상 노드를 희소 배열에 저장하여 표현 (다이나믹 프로그래밍의 일종)
2. 기본 점화식
   ```
   P[i][j] = (i노드의 2^j 번 째 조상)
   P[i][j] = P[P[i][j - 1]][j - 1]
   ```
3. 위 점화식 덕분에 Balanced Tree가 아니더라도 LCA를 O(log N) 시간에 찾는 것이 가능 (트리 구성에는 O(N) 시간 소요)
4. 알고리즘 [(1855)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/1000/Solution_1855.java)

   1. depth 0부터 최대 depth까지 BFS 형태로 자기 자신의 조상에 대한 정보를 업데이트

   ```java
   nodes[child].parent[0] = parent;
   ```

   다만 N이 클 경우 재귀로 구현하면 Stack 메모리가 터질 수 있으므로 반복문으로 구현하는 것을 권장한다.

   ```java
   public void updateStatus(Node[] nodes, Stack<Integer> stack) {
      Node parentNode = nodes[parent[0]];
      if(parentNode != null) {
         // 현재 깊이는 부모 노드의 깊이 + 1
         this.depth = parentNode.depth + 1;
         this.maxDepth = log2(this.depth);

         // DP로 조상 노드 정보 갱신
         for(int i = 1; i <= maxDepth; i++) {
            // 현재 노드의 2^i 번째 조상은 2^i-1 번째 조상의 2^i-1번째 조상
            parent[i] = nodes[parent[i - 1]].parent[i - 1];
         }
      }

      // 자식 노드 정렬 (자식 노드를 작은 번호부터 순서대로 탐색)
      Collections.sort(children);

      // 자식 노드들도 정보 갱신
      for(int child: children) {
         // N이 클 때 재귀로 구현하면 Stack 메모리가 터질 수 있음
         stack.push(child);
      }
   }
   ```

   2. 최소 공통 조상을 비교하려는 두 노드의 높이를 같게 설정

   ```java
   // 두 노드의 높이 맞추기
   int depthDiff;
   if(nodeA.depth >= nodeB.depth) {
      while(nodeA.depth > nodeB.depth) {
         depthDiff = log2(nodeA.depth - nodeB.depth);
         nodeA = nodes[nodeA.parent[depthDiff]];
      }
   } else {
      while(nodeA.depth < nodeB.depth) {
         depthDiff = log2(nodeB.depth - nodeA.depth);
         nodeB = nodes[nodeB.parent[depthDiff]];
      }
   }
   ```

   3. 깊이를 맞췄다면 부모 노드로 한 단계씩 이동하며 부모 노드가 같을 때까지 반복

   ```java
   // 공통 조상 찾기
   int next;
   while(nodeA != nodeB) {
      next = nodeA.maxDepth;
      // 부모가 같지 않을 때까지 최대 노드부터 탐색
      while(nodeA.parent[next] == nodeB.parent[next]) {
         if(next == 0) break;
         next--;
      }

      nodeA = nodes[nodeA.parent[next]];
      nodeB = nodes[nodeB.parent[next]];
   }
   ```

## Traveling Salesman Problem (TSP, 외판원 문제)

1. 비트필드를 이용한 다이나믹 프로그래밍을 사용하는 문제. 비트마스크를 통해 현재 위치에서 방문한 도시 상태를 저장하고, 아직 방문하지 않은 영역에 대한 최단 경로를 한 번만 탐색하도록 수행
2. 구현 시 주의할 점
   1. 이 문제는 최적의 Hamiltonian Cycle을 찾는 문제이므로 어떤 정점에서 출발하더라도 항상 최적해는 같게 나온다.
   2. 다만, 현재 도시와 방문한 도시의 비트마스크에 따라 최적 거리가 다르게 나오기 때문에 각 도시별로 비트마스크를 따로 저장해줘야 한다.
   3. 만약 모든 도시를 방문한 이후, 출발점으로 돌아갈 수 없는 경우에는 초기값으로 설정해준 INF 값과, 출발점으로 돌아갈 수 없다는 것을 나타내는 INVALID 값을 서로 다르게 설정해줘야 한다.  
      그렇지 않다면 이후 출발점으로 돌아갈 수 없다는 것을 탐색했던 도시에서 INF 값이 저장되어 있는 것을 보고 반복적으로 탐색을 수행하기 때문에 시간 초과가 발생한다. [(참고)](https://chb2005.tistory.com/86)
   4. 앞서 INVALID를 INF와 분리할 때는 Math.min으로 최솟값을 갱신했기 때문에 INVALID가 제대로 저장되도록 INF보다 작은 값으로 설정해줘야 한다. 다만 전체 비용보다는 반드시 커야 한다.
3. 알고리즘 [(2098)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2098.java)

   1. 점화식: tsp[curr][bitmask] = Math.min(tsp[curr][bitmask], dfs(i, next) + W[curr][i])
   2. 현재 위치, 방문한 도시 상태에서 나머지 도시를 방문하는 최소 거리 = 다음 점으로 이동한 뒤, 다음 점에서 나머지 도시를 방문하는 최소 거리 + 다음 점까지의 비용
   3. 시작 도시를 0번으로 고정한 뒤, 0번 도시에서 출발하여 Bottom-Top 형태로 구현
   4. 최소 거리가 아닌, 최적 경로를 구하고 싶다면 최솟값을 갱신할 때 도시 Index를 기록하도록 수정.

   ```java
   // curr에서 시작했을 때, 다음 도시에서 남은 점을 방문하는 최소 거리 탐색
   public static int dfs(int curr, int bitmask) {
   	if(bitmask == FULL) {
   		// 출발점으로 돌아오는 최소 비용 갱신

   		// 출발점으로 돌아가지 못하는 경우
   		if(W[curr][START] == INF) {
   			tsp[curr][bitmask] = INVALID;
   		} else {
   			tsp[curr][bitmask] = W[curr][START];
   		}
   		return tsp[curr][bitmask];
   	}

   	// 이미 탐색을 수행했다면 생략
   	if(tsp[curr][bitmask] != INF) return tsp[curr][bitmask];

   	// 현재 위치에서 하나씩 더하며 탐색 수행
   	int next;
   	for(int i = 0; i < N; i++) {
   		next = bitmask | (1 << i);
   		// 이미 방문한 경우 생략
   		if(next == bitmask) continue;
   		// 다음 점까지 이동할 수 없다면 생략
   		if(W[curr][i] == INF) continue;

   		// 다음 점까지의 거리 + 다음 점에 방문하고 남은 점을 최적 경로로 돌았을 때의 거리가 최소가 되는 값 탐색
   		tsp[curr][bitmask] = Math.min(tsp[curr][bitmask], dfs(i, next) + W[curr][i]);
   	}

   	return tsp[curr][bitmask];
   }
   ```

# Union Find [(Disjoint Set)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/UnionFind.md)

1. 서로소 집합: 서로 중복 포함된 원소가 없는 집합
2. 알고리즘 (트리로 표현) [(3289)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/3000/Solution_3289.java)

   1. 두 개의 집합을 합칠 때, 한 집합의 루트 노드가 다른 집합의 루트 노드를 가리키도록 구현
   2. Rank를 이용한 Union: 각 트리의 높이를 rank로 저장하고, rank가 낮은 집합을 rank가 높은 집합에 붙임
   3. Path Compression: 루트 노드를 찾는 과정에서 마주치는 모든 노드가 직접 루트 노드를 가리키도록 포인터를 바꿔줌

   ```java
   // MakeSet
   public DisjointSet() {
      this.rank = 0;
      this.parent = null;
   }

   // FindSet
   public DisjointSet findSet() {
      if(this.parent == null) return this;
      // Path Compression
      this.parent = this.parent.findSet();
      return this.parent;
   }

   // UnionSet
   public static void unionSet(DisjointSet A, DisjointSet B) {
      DisjointSet rootA = A.findSet();
      DisjointSet rootB = B.findSet();

      // 루트 노드가 같으면 같은 집합
      if(rootA == rootB) return;

      // Rank를 이용한 Union
      if(rootA.rank >= rootB.rank) {
         rootB.parent = rootA;
         // 두 집합의 Rank가 같으면 Rank 증가
         if(rootA.rank == rootB.rank) rootA.rank++;
      } else {
         rootA.parent = rootB;
      }
   }
   ```

## Flood Fill 알고리즘

1. 시작점으로부터 연결된 영역을 찾는 알고리즘
2. 기존의 Union Find와 달리 모든 영역에서 루트 노드만을 가리키도록 설정해주면 된다. [(1868)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/1000/Solution_1868.java)

   ```java
   // 시작점에서 새로운 객체 생성
   disjointSet[row][col] = new Object();
   // 연결된 영역에서 같은 객체를 가리키도록 설정
   disjointSet[r][c] = disjointSet[row][col];
   // 구역의 개수를 카운트 할 경우 Set에 넣어서 중복된 객체를 제거한 뒤
   set.add(disjointSet[row][col]);
   // Set에 남아 있는 개수가 전체 구역의 개수
   System.out.println(set.size())
   ```

# Graph [(그래프 탐색 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Graph.md)

1. 인접 행렬 / 인접 리스트 / 인접 해시테이블로 그래프 표현 가능
2. 인접 리스트의 경우 ArrayList로 구현했을 때 메모리를 생각보다 많이 잡아먹지 않음

## Dijkstra (다익스트라 알고리즘)

1. 시작점과 그래프가 주어졌을 때, 시작점에서부터 다른 정점까지의 최단 경로를 구하는 문제에 활용
2. `음의 가중치를 가지는 사이클이 존재할 때는 사용할 수 없다.`
3. 위 2번 조건 때문에 가중치가 항상 양수인 경우에 사용하면 좋다.
4. 가중치 값이 가장 작은 정점을 찾을 때 [우선순위 큐](#heap-힙)를 활용하면 더 빠르게 수행할 수 있다. [(1753)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1753.java)
5. 모든 정점에서 다른 정점으로 가는 최소 비용을 계산할 때, 일반적으로는 Dijkstra를 여러 번 사용하는 것이 Floyd-Warshall을 사용하는 것보다 빠르다. [(1238)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1238.java)
6. 시간 복잡도: θ(|E| log |V|)
7. 알고리즘

   1. 출발 정점 이외의 나머지 정점은 가중치 무한으로 설정
   2. 출발 정점으로부터 다른 정점까지 도달하는 가중치 최솟값 갱신
   3. 방문하지 않은 정점 중 가장 가중치 값이 작은 정점 선택
   4. 해당 노드를 거쳐 다른 정점까지 도달하는 가중치 최솟값 갱신
   5. 3 ~ 4번을 더 이상 방문 할 정점이 없을 때까지 반복
   6. `종료 정점 하나만 최단 경로를 구해야 하는 경우, 중간에 해당 정점에 도착했을 때 break 하도록 하면 시간을 단축할 수 있다.`

   ```java
   // 시작점과 다른 모든 정점 사이의 최단 경로 가중치를 다익스트라 알고리즘으로 계산
   public void dijkstra(int start) {
      // 최소 가중치 간선을 구하기 위한 우선순위 큐
      Queue<Edge> minEdge = new PriorityQueue<>();
      // 시작 정점 가중치 0으로 초기화
      vertices[start].w = 0;
      minEdge.offer(new Edge(start, 0));
      Edge curr;
      Vertex currV, nextV;
      int v, w;
      while(!minEdge.isEmpty()) {
         curr = minEdge.poll();
         currV = vertices[curr.v];
         // 이미 방문한 정점은 생략
         if(currV.visited) continue;
         // 방문 체크
         currV.visited = true;

         // 다른 정점의 가중치 갱신
         for(Entry<Integer, Integer> edge: currV.edges.entrySet()) {
            // 간선의 종점
            v = edge.getKey();
            // 간선의 가중치
            w = edge.getValue();
            // 다음 정점
            nextV = vertices[v];
            // 이미 방문한 정점은 생략
            if(nextV.visited) continue;

            // 가중치 갱신
            if(nextV.w > currV.w + w) {
               nextV.w = currV.w + w;
               minEdge.offer(new Edge(v, nextV.w));
            }
         }
      }
   }
   ```

## Bellman-Ford (밸만-포드 알고리즘)

1. 문제의 조건에서 `음의 사이클을 가지고 있는지 여부`를 판단해야 하는 경우 사용하는 알고리즘
2. 시간 복잡도: θ(|E||V|)
3. 알고리즘

   1. 시작 정점의 비용은 0, 다른 모든 정점의 비용은 무한으로 초기화
   2. 모든 정점에 대해 비용 갱신하는 과정을 |V| - 1(정점 수 - 1)번만큼 반복.
      이렇게 하면 |V| - 1개의 노드를 거쳐서 갈 때의 최소 비용이 각 정점에 저장
   3. 중간에 갱신되는 정점이 없다면 갱신이 끝난 것.
   4. |V| - 1개의 노드를 거쳐간 이후에도 갱신이 가능하다면 음의 사이클이 존재하는 경우.

   ```java
   // 시작 정점으로 돌아오는 음의 사이클이 존재하는지 확인
   public boolean BellmanFord() {
      // 간선이 존재하는 정점을 시작 정점으로 설정
      int start = hasEdge();

      // 각 정점 비용 초기화
      initialize();
      // 시작 정점 비용 0
      if(start != -1) edges[start][COST] = 0;

      int changed = 0;
      for(int i = 0; i < N; i++) {
         // 각 정점별로 비용 갱신 후 갱신된 정점의 개수 반환
         changed = updateCost();
         // 갱신된 정점이 없다면 갱신 완료
         if(changed == 0) break;
      }

      // 전체 갱신 이후에도 계속 갱신이 가능하면 음의 사이클이 존재
      changed = updateCost();
      if(changed != 0) return true;
      else return false;
   }

   // 각 정점별로 비용 갱신 후 갱신된 정점의 개수 반환
   public int updateCost() {
      int changed = 0;
      int[] edge, curr;
      for(int u = 1; u <= N; u++) {
         edge = edges[u];

         // 간선이 없다면 다음 정점으로 이동
         if(edge[COUNT] == 0) continue;
         // 각 정점에서 다른 정점으로 가는 비용 갱신
         for(int v = 1; v <= N; v++) {
            curr = edges[v];
            if(curr[COST] > edges[u][v] + edge[COST]) {
               curr[COST] = edges[u][v] + edge[COST];
               changed++;
            }
         }
      }

      return changed;
   }
   ```

## Floyd-Warshall (플로이드-워셜 알고리즘)

1. `모든 정점에서 다른 정점으로 가는 최소 비용`을 전부 계산해야 할 때 주로 사용하는 알고리즘
2. 각 간선에서 가중치 무한으로 초기화 할 때 값을 연산하는 과정에서 오버플로우가 발생할 수 있기 때문에 MAX_VALUE / 2 - 1 정도로 설정하는 것을 추천 [(1238)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1238.java)
3. 모든 정점에서 다른 정점으로 가는 최소 비용을 계산할 때, 일반적으로는 Dijkstra를 여러 번 사용하는 것이 Floyd-Warshall을 사용하는 것보다 빠르다. [(1238)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1238.java)
4. 알고리즘

   1. 중간에 거쳐 갈 정점을 선택
   2. 선택한 정점을 거쳐가는 경로에서 가중치가 갱신되면 갱신
   3. 중간에 거쳐 갈 정점을 모든 정점을 돌아가며 설정한 뒤, 2번의 갱신 과정을 반복하면 인접 행렬로 표현했을 때 [i][j] 는 i에서 j로 가는 최소 비용이 완성됨.

   ```java
   // 플로이드-워셜 알고리즘 수행
   public void floyd() {
      // 중간 정점 k
      for(int k = 1; k <= N; k++) {
         // 시작 정점 i
         for(int i = 1; i <= N; i++) {
            // 끝 정점 j
            for(int j = 1; j <= N; j++) {
               // i -> j로 도달하는 최소비용 갱신
               if(getCost(i, j) > getCost(i, k) + getCost(k, j)) {
                  setCost(i, j, getCost(i, k) + getCost(k, j));
               }
            }
         }
      }
   }
   ```

## Minimum Spanning Tree (최소 신장 트리)

### 1. Kruskal (크루스칼 알고리즘)

1. 처음에 n 개의 집합으로 시작해서 n-1번의 합집합을 통해 최적해를 찾는 방식 [(Union Find)](https://github.com/rldnjs7723/CodingTest#union-find-disjoint-set)
2. 시간복잡도: O(|E| log |E|) = O(|E| log |V|) (점근적으로 log |E|는 log |V|와 동일)
3. 간선 중심이라 간선 정보가 주어질 때 사용하면 좋음
4. 알고리즘

   1. 모든 간선을 가중치 순으로 정렬
   2. 최소 비용의 간선을 제거한 후, 해당 간선의 두 정점이 서로 다른 집합에 속한다면 두 집합을 Union
   3. 모든 간선이 제거될 때까지 반복

   ```java
   public static int Kruskal(DisjointSet[] set, Queue<Edge> queue) {
   	int weight = 0;
   	Edge curr;
   	while(!queue.isEmpty()) {
   		// 최소 가중치인 간선을 선택
   		curr = queue.poll();
   		// 간선을 이루는 두 정점이 다른 집합인 경우에만 연결하고 가중치 추가
   		if(DisjointSet.UnionSet(set[curr.u], set[curr.v])) weight += curr.w;
   	}

   	return weight;
   }
   ```

### 2. Prim (프림 알고리즘)

1. Dijkstra 알고리즘과 비슷한 그리디 알고리즘
2. 시간 복잡도: O(|E| log |V|)
3. 정점 중심이라 인접 행렬이 주어질 때 사용하면 좋음
4. 알고리즘

   1. 선택된 정점의 집합 S를 구성. 단계를 넘어갈 때마다 집합 S가 하나씩 커짐
   2. 각 Vertex 중 인접한 Vertex로 가는 가장 비용이 작은 값을 기록 (Relaxation)
   3. 집합 S에 인접한 Vertex 중 가장 비용이 작은 Vertex를 집합 S에 포함 (Heap 사용하면 시간복잡도 줄일 수 있음)
   4. 모든 정점을 선택할 때까지 반복

   ```java
   // 프림 알고리즘으로 MST 가중치 합 계산
   public long prim() {
      long weight = 0;
      // 시작 정점 가중치 0으로 설정
      vertices[START].weight = 0;
      // 우선 순위 큐로 최소 가중치 간선 체크
      Queue<Edge> minEdge = new PriorityQueue<>();
      minEdge.offer(new Edge(START, 0));
      Edge curr;
      Vertex currVertex, nextVertex;
      int v, w;
      while(!minEdge.isEmpty()) {
         curr = minEdge.poll();
         currVertex = vertices[curr.v];
         // 이미 방문한 정점은 생략
         if(currVertex.visited) continue;
         // 정점 방문 체크
         currVertex.visited = true;
         // 가중치 추가
         weight += curr.w;

         for(Entry<Integer, Integer> edge: currVertex.edges.entrySet()) {
            v = edge.getKey();
            w = edge.getValue();
            nextVertex = vertices[v];
            // 이미 방문한 정점은 생략
            if(nextVertex.visited) continue;

            // 가중치 갱신
            if(nextVertex.weight > w) {
               nextVertex.weight = w;
               minEdge.offer(new Edge(v, w));
            }
         }
      }

      return weight;
   }
   ```

## Topological Sort (위상 정렬)

1. 그래프가 비순환 유향 그래프 (Directed Acyclic Graph, DAG)일 때 사용 가능
2. 정점을 변의 방향을 거스르지 않도록 나열하는 것
3. 시간 복잡도: θ(|V| + |E|) = θ(max(|V|, |E|))
4. 진출, 진입 간선 삭제를 반대로 하면 Topological Order의 역순 형태로 나타낼 수 있다. [(1005)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1005.java)
5. 알고리즘

   1. Incoming Edge (진입 간선): Vertex로 들어오는 Edge  
      Outgoing Edge (진출 간선): Vertex에서 나가는 Edge
   2. 시작 정점은 Incoming Edge가 없는 정점을 선택
   3. 정점의 Outgoing Edge를 전부 삭제한 후 다음 정점을 선택

   ```java
   // 위상 정렬 수행
   public Queue<Integer> topologicalSort() {
      // 위상 정렬 결과
      Queue<Integer> result = new ArrayDeque<>();

      // 위상 정렬 시작점 탐색
      Queue<Integer> sortQueue = new ArrayDeque<>();
      for(int u = 1; u <= N; u++) {
         // 진입 간선 수가 0인 경우.
         if(V[u].incoming == 0) sortQueue.offer(u);
      }
      // DAG가 아니라면 위상 정렬은 불가능
      if(sortQueue.isEmpty()) return null;

      // 모든 정점을 포함할 때까지 위상 정렬 수행
      int u;
      while(!sortQueue.isEmpty()) {
         u = sortQueue.poll();
         result.offer(u);

         for(int v: V[u].outgoing) {
            // 현재 시작 정점을 진입 간선으로 가지는 경우 삭제
            V[v].incoming--;
            // 만약 방금 삭제한 간선이 마지막 진입 간선이면 정렬 큐에 포함
            if(V[v].incoming == 0) sortQueue.offer(v);
         }
      }

      return result;
   }
   ```

### DAG에서의 최단 경로 (최장 경로)

1. 기본적으로 그래프에서의 최장 경로를 구하는 문제는 NP-Hard로 정의 되지만, DAG에서는 위상 정렬을 통해 빠르게 계산할 수 있다. [(1948)]()
2. 시간 복잡도: O(|V| + |E|)
3. 알고리즘

   1. 위상 정렬을 통해 주어진 DAG에 대해 위상 순서를 계산
   2. 시작 정점의 비용을 0으로 두고, 나머지 정점의 비용은 무한으로 초기화
   3. 시작 정점부터 시작하여 위상 순서대로 Relaxation을 수행

   ```java
   // 위상 정렬을 통해 시작 도시에서 각 도시까지의 최단 거리 계산
   public void topologicalSort(int start) {
      int u, v, cost;
      // BFS로 위상 정렬 수행
      Queue<Integer> sortedVertex = new ArrayDeque<>();
      Queue<Integer> bfsQueue = new ArrayDeque<>();
      bfsQueue.offer(start);
      while(!bfsQueue.isEmpty()) {
         u = bfsQueue.poll();
         sortedVertex.offer(u);

         // 진출 간선에 연결된 모든 정점에 대해 진입 간선 제거
         for(int vertex: V[u].outgoing.keySet()) {
            V[vertex].incomingCount--;
            if(V[vertex].incomingCount == 0) bfsQueue.offer(vertex);
         }
      }

      // 시작 정점 비용 0으로 초기화
      V[start].cost = 0;
      // 위상 정렬된 순서로 Relaxation 수행
      while(!sortedVertex.isEmpty()) {
         u = sortedVertex.poll();

         for(Entry<Integer, Integer> entry: V[u].outgoing.entrySet()) {
            v = entry.getKey();
            cost = entry.getValue();

            // Relaxation
            V[v].cost = Math.min(V[v].cost, V[u].cost + cost);
         }
      }
   }
   ```

   4. 최장 경로를 구하는 문제의 경우, 모든 간선의 가중치에 -1을 곱하여 음수로 만든 뒤 최단 거리를 구해서 모든 비용에 다시 -1을 곱해주면 빠르게 계산할 수 있다.

   ```java
   // 모든 정점의 비용 양수로 변환
   for(u = 1; u <= N; u++) {
      V[u].cost = -V[u].cost;
   }
   ```

## Strongly Connected Component (강한 결합 요소)

# String [(문자열)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/String.md)

## Trie (트라이)

# Segment Tree (세그먼트 트리)

# Sweeping (스위핑 알고리즘)
