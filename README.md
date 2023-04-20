# Shell 파일 사용법

1. ./execute.sh <사이트 타입> <문제 번호>
2. <사이트 타입> = BOJ: B, SWEA: S
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
        2. F6 누르면 다음 라인 실행 (F5 누르면 함수 내부로 들어감)
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
3. 반올림: Math.round()
4. 절댓값: Math.abs()
5. 최대/최소: Math.Max(a, b) | Math.min(a, b)

# 알고리즘

소스 코드가 들어가 있는 경우는 구현 방식 자체를 외워야 하는 것들

## 목차

1. [Math (수학)](#math-수학)

    1. [Prime Number (소수)](#prime-number-소수)

    2. [Modulo (나머지 연산)](#modulo-나머지-연산)

    3. [Intermediate Value Theorem (중간값 정리)](#intermediate-value-theorem-중간값-정리)

    4. [Matrix Exponentiation (행렬 멱법)](#matrix-exponentiation-행렬-멱법)

    5. [GCD, LCM (최대 공약수, 최소 공배수)](#gcd-lcm-최대-공약수-최소-공배수)

2. [Brute Force (완전 탐색)](#brute-force-완전-탐색)

    1. [Direction Search (사방 탐색)](#direction-search-사방-탐색)

    2. [Depth First Search (깊이 우선 탐색)](#depth-first-search-깊이-우선-탐색)

    3. [Breadth First Search (너비 우선 탐색)](#breadth-first-search-너비-우선-탐색)

    4. [Permutation (순열)](#permutation-순열)

    5. [Combination (조합)](#combination-조합)

    6. [Next Permutation (다음 순열)](#next-permutation-다음-순열)

3. [Data Structure (자료 구조)](#data-structure-자료-구조)

    1. [Stack (스택)](#stack-스택)

    2. [Queue (큐)](#queue-큐)

    3. [Tree (트리)](#tree-트리)

    4. [Heap (힙)](#heap-힙)

    5. [Set (집합)](#set-집합)

    6. [LinkedList (연결 리스트)](#linkedlist-연결-리스트)

4. [Greedy Algorithm (그리디 알고리즘)](#greedy-algorithm-그리디-알고리즘)

5. [Divide and Conquer (분할 정복)](#divide-and-conquer-분할-정복)

    1. [Binary Search (이분 탐색)](#binary-search-이분-탐색)

    2. [Parametric Search (매개 변수 탐색)](#parametric-search-매개-변수-탐색)

    3. [Longest Increasing Subsequence (LIS, 최장 증가 부분 수열)](#longest-increasing-subsequence-lis-최장-증가-부분-수열-참고)

6. [Backtracking (백트래킹)](#backtracking-백트래킹)

7. [Dynamic Programming (다이나믹 프로그래밍)](#dynamic-programming-다이나믹-프로그래밍)

    1. [Longest Common Subsequence (LCS)](#longest-common-subsequence-lcs-9252)

    2. [Lowest Common Ancestor (LCA) 알고리즘](#lowest-common-ancestor-lca-알고리즘)

    3. [Traveling Salesman Problem (TSP, 외판원 문제)](#traveling-salesman-problem-tsp-외판원-문제)

8. [Union Find (Disjoint Set)](#union-find-disjoint-set)

    1. [Flood Fill 알고리즘](#flood-fill-알고리즘)

9. [Graph (그래프 탐색 알고리즘)](#graph-그래프-탐색-알고리즘)

    1. [Dijkstra (다익스트라 알고리즘)](#dijkstra-다익스트라-알고리즘)

    2. [Bellman-Ford (밸만-포드 알고리즘)](#bellman-ford-밸만-포드-알고리즘)

    3. [Floyd-Warshall (플로이드-워셜 알고리즘)](#floyd-warshall-플로이드-워셜-알고리즘)

    4. [Minimum Spanning Tree (최소 신장 트리)](#minimum-spanning-tree-최소-신장-트리)

        1. [Kruskal (크루스칼 알고리즘)](#1-kruskal-크루스칼-알고리즘)

        2. [Prim (프림 알고리즘)](#2-prim-프림-알고리즘)

    5. [Topological Sort (위상 정렬)](#topological-sort-위상-정렬)

        1. [DAG에서의 최단 경로 (최장 경로)](#dag에서의-최단-경로-최장-경로)

    6. [Strongly Connected Component (강한 결합 요소)](#strongly-connected-component-강한-결합-요소)

10. [Segment Tree (세그먼트 트리)](#segment-tree-세그먼트-트리)

    1. [Fenwick Tree (Binary Indexed Tree, BIT)](#fenwick-tree-binary-indexed-tree-bit)

11. [String (문자열)](#string-문자열)

    1. [Knuth-Morris-Pratt (KMP) Pattern Matching](#knuth-morris-pratt-kmp-pattern-matching)

    2. [Rabin-Karp Algorithm (라빈-카프 알고리즘)](#rabin-karp-algorithm-라빈-카프-알고리즘)

    3. [Boyer-Moore Algorithm (보이어-무어 알고리즘)](#boyer-moore-algorithm-보이어-무어-알고리즘)

    4. [Trie (트라이)](#trie-트라이)

    5. [Aho-Corasick (아호-코라식-알고리즘)](#aho-corasick-아호-코라식-알고리즘)

12. [Sorting (정렬 알고리즘)](#sorting-정렬-알고리즘)

    1. [Merge Sort (병합 정렬)](#merge-sort-병합-정렬)

13. [ETC. (기타 기법)](#etc-기타-기법)

    1. [Two Pointers (투포인터 알고리즘)](#two-pointers-투포인터-알고리즘)

    2. [Sliding Window (슬라이딩 윈도우)](#sliding-window-슬라이딩-윈도우)

    3. [Bitmasking (비트마스킹)](#bitmasking-비트마스킹)

    4. [Recursive (재귀)](#recursive-재귀)

    5. [Sweeping (스위핑 알고리즘)](#sweeping-스위핑-알고리즘)

    6. [Prefix Sum (누적 합)](#prefix-sum-누적-합)

    7. [Ad Hoc (애드 혹)](#ad-hoc-애드-혹)

    8. [Sparse Table (희소 배열)](#sparse-table-희소-배열)

# [Math](#목차) [(수학)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Math.md)

1. 일반적으로 수열이 주어졌을 때 규칙을 찾아서 계산 식으로 표현해야 하는 경우가 많다. [(9527)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9527.java)

## [Prime Number](#목차) [(소수)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/PrimeNumber.md)

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

## [Modulo (나머지 연산)](#목차)

다음 성질은 [분할 정복을 이용한 거듭 제곱](#분할-정복을-이용한-거듭제곱) 문제에서 자주 사용됨.

1. 분배 법칙 [(1328)](https://www.acmicpc.net/problem/1328)  
   나누기에 대해서는 성립하지 않는다.
    ```
    (a × b) % c = ((a % c) × (b % c)) % c
    (a ± b) % c = ((a % c) ± (b % c)) % c
    ```
2. 페르마의 소정리 [(13172)](https://www.acmicpc.net/problem/13172)  
   X가 소수일 때,  
   $\frac{a}{b}$ mod X = (a × b<sup>-1</sup>) mod X  
   b<sup>X - 1</sup> = 1 mod X  
   b<sup>X - 2</sup> = b<sup>-1</sup> mod X
3. 이항 계수 [(참고)](https://velog.io/@kasterra/%ED%81%B0-%EC%88%98%EC%9D%98-%EC%9D%B4%ED%95%AD-%EA%B3%84%EC%88%98-mod-K-%EA%B5%AC%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95)

    1. 다이나믹 프로그래밍을 이용한 계산 (파스칼의 삼각형) [(1010)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1010.java)  
       r이 0이나 n일 때 <sub>n</sub>C<sub>r</sub> = 1  
       나머지의 경우 <sub>n</sub>C<sub>r</sub> = <sub>n-1</sub>C<sub>r-1</sub> + <sub>n-1</sub>C<sub>r</sub>  
       이러한 특성에 분배 법칙을 적용하여 계산할 수도 있다.  
       시간 복잡도 O(n<sup>2</sup>)

    2. 페르마의 소정리를 이용한 계산 [(11401)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/11000/Main_11401.java)  
       <sub>n</sub>C<sub>r</sub> = $\frac{n!}{r! × (n - r)!}$  
       이항 계수의 나머지를 계산할 때, 나누기에 대해서는 분배 법칙이 성립하지 않기 때문에  
       페르마의 소정리를 통해 (r! × (n - r)!)<sup>-1</sup>의 나머지를 계산하여  
       ((n!) % X × (r! × (n - r)!)<sup>-1</sup> % X) % X를 계산하면  
       <sub>n</sub>C<sub>r</sub>를 X로 나눈 나머지를 계산할 수 있다.  
       시간 복잡도 O(log N)

    3. 뤼카의 정리를 이용한 계산 [(11402)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/11000/Main_11402.java) [(참고)](https://bowbowbow.tistory.com/2)  
       페르마의 소정리를 이용하는 경우, 기본적으로 n!을 계산할 수 있어야 사용할 수 있는 방법이다.  
       이 때, n 값이 너무 큰 경우(10<sup>18</sup>) 시간, 공간 복잡도 측면에서 수용할 수 없는 범위이므로 계산할 수 없다.  
       뤼카의 정리는 <sub>n</sub>C<sub>r</sub> mod p를 계산하고자 할 때,  
       n = n<sub>m</sub>p<sup>m</sup> + n<sub>m-1</sub>p<sup>m-1</sup> + $\cdots$ + n<sub>1</sub>p + n<sub>0</sub>  
       r = r<sub>m</sub>p<sup>m</sup> + r<sub>m-1</sub>p<sup>m-1</sup> + $\cdots$ + r<sub>1</sub>p + r<sub>0</sub>  
       위와 같이 n과 r을 각각 p진법 전개식으로 나타낼 수 있으며,  
       다음과 같은 수식을 통해 구하고자 하는 이항 계수 값을 작은 여러 개의 이항 계수의 곱으로 표현할 수 있다.

```math
   \begin{pmatrix}
   n \\
   r
   \end{pmatrix}
   =
   \prod_{i=0}^m
   \begin{pmatrix}
   n_i \\
   r_i
   \end{pmatrix}
   \bmod
   p
```

```math
   이\,때,
   n_i \lt r_i 라면
   \begin{pmatrix}
   n_i \\
   r_i
   \end{pmatrix}
   = 0 이므로
   \begin{pmatrix}
   n \\
   r
   \end{pmatrix}
   = 0
```

## [Intermediate Value Theorem (중간값 정리)](#목차)

함수 f가 연속 함수이고, f(a) \* f(b) < 0을 만족한다면 (a, b) 사이에 근을 적어도 하나 가진다.

## [Matrix Exponentiation (행렬 멱법)](#목차)

1. 선형 재귀 점화식이 주어질 때 이를 행렬 곱을 통해 계산하는 방법.
   단순 다이나믹 프로그래밍으로는 시간이 부족할 경우, 분할 정복을 이용하여 최적화 할 때 사용된다.

2. 항이 3개인 점화식에 대한 행렬 멱법

    F<sub>n</sub> = a × F<sub>n-1</sub> + b × F<sub>n-2</sub> + c × F<sub>n-3</sub>  
    을 만족할 때,

```math
   \begin{pmatrix}
   F_{n} \\
   F_{n-1} \\
   F_{n-2}
   \end{pmatrix}
   =
   \begin{pmatrix}
   a & b & c \\
   a & b & c \\
   a & b & c
   \end{pmatrix}
   \times
   \begin{pmatrix}
   F_{n-1} \\
   F_{n-2} \\
   F_{n-3}
   \end{pmatrix}
```

```math
   \begin{pmatrix}
   F_{n} \\
   F_{n-1} \\
   F_{n-2}
   \end{pmatrix}
   =
   \begin{pmatrix}
   a & b & c \\
   a & b & c \\
   a & b & c
   \end{pmatrix}
   ^{n-2}
   \times
   \begin{pmatrix}
   F_{2} \\
   F_{1} \\
   F_{0}
   \end{pmatrix}
```

3. 피보나치 수 행렬 멱법

```math
   \begin{pmatrix}
   1 & 1 \\
   1 & 0
   \end{pmatrix}
   ^n
   =
   \begin{pmatrix}
   F_{n+1} & F_{n} \\
   F_{n} & F_{n-1}
   \end{pmatrix}
```

## [GCD, LCM (최대 공약수, 최소 공배수)](#목차)

1. 최대 공약수, 최소 공배수 성질

    ```
    A = GCD × a
    B = GCD × b
    ```

    를 만족할 때,

    ```
    LCM = GCD × a × b
    A × B = GCD × LCM
    ```

2. 유클리드 호제법

    ```
    두 양의 정수 a, b (a > b)에 대해 a = b × q + r (0 ≤ r < b)라 하면,
    a, b의 최대 공약수는 b와 r의 최대 공약수와 같다.
    즉, GCD(a, b) = GCD(b, r)
    ```

    위 성질을 반복해서 적용하면 두 수의 최대 공약수를 구할 수 있다.

    ```
    a = b × q1 + r1
    b = r1 × q2 + r2
    r1 = r2 × q3 + r3
    ...
    rn-1 = rn × qn+1
    GCD(a, b) = rn
    ```

    위와 같이 나머지가 0이 될 때까지 반복하면 된다.

## [카탈란 수열](#목차)

# [Brute Force](#목차) [(완전 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/BruteForce.md)

1. 가능한 모든 경우의 수를 탐색하여 최적의 해를 찾는 방법. 시간이 많이 소요됨
2. N의 수가 합리적으로 작은 경우에서 시도 가능. `시간 복잡도 계산 필수`
3. 완전 탐색 문제를 풀 때 항상 고려할 사항
    1. 백트래킹 (가지치기): `확실하게` 아닌 경우만 탐색을 수행하지 않도록 해야 함
    2. 메모이제이션: 동일한 부분에서 연산을 반복하면 기존에 계산한 결과를 저장해두었다가 활용할 필요가 있음

## [Direction Search (사방 탐색)](#목차)

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

5. BFS를 수행할 때 행, 열의 값이 작은 것을 먼저 선택해야 한다면 위 -> 왼쪽 -> 오른쪽 -> 아래 순서로 탐색을 수행하는 것이 좋다. [(코드트리 빵)](https://github.com/rldnjs7723/CodingTest/blob/main/ETC/Codetree/코드트리_빵.java) 다만 이 경우는 벽이 존재하지 않기 때문에 벽이 존재하는 경우에는 우선순위 큐를 통해 정렬을 하는 것이 좋다.

## [Depth First Search (깊이 우선 탐색)](#목차)

1. [트리 구조](#tree-트리)에서 루트 노드부터 더 이상 자식 노드가 없을 때까지 한 방향으로(왼쪽 자식) 탐색하고, 이후에는 마지막에 만난 갈림길에서 다른 방향으로 탐색을 수행하는 방식
2. 일반적으로 가장 마지막에 발견한 위치를 가장 먼저 탐색하기 때문에 후입 선출 구조인 [Stack](#stack-스택)을 활용하여 구현.
3. 다만 [재귀](#recursive-재귀) 깊이가 너무 깊지 않은 경우 재귀로 구현했을 때의 속도가 더 빠르기 때문에 비트마스킹을 이용한 재귀로 구현하는 것을 추천 [(3234)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/3000/Solution_3234.java)

## [Breadth First Search (너비 우선 탐색)](#목차)

1. [트리 구조](#tree-트리)에서 루트 노드의 자식 노드를 먼저 방문한 후 방문한 자식 노드에서 자식 노드를 차례로 방문하는 방식
2. 일반적으로 같은 높이에 위치한 노드를 먼저 탐색하려면 먼저 발견한 위치를 먼저 탐색해야 하기 때문에 선입 선출 구조인 [Queue](#queue-큐)를 활용해서 구현

## [Permutation (순열)](#목차)

1. 가능한 모든 순서쌍에 대해 재귀나 Stack을 이용하여 DFS를 통한 완전 탐색을 수행

## [Combination (조합)](#목차)

1. 특정 원소의 선택 여부를 통해 부분 집합을 구성하는 문제로 자주 등장
2. 완전 탐색으로 수행할 경우 선택 / 비선택의 경우로 시간 복잡도 O(2^N)

## [Next Permutation](#목차) [(다음 순열)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/NextPermutation.md)

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

# [Data Structure](#목차) [(자료 구조)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DataStructure.md)

1. 스택, 큐, 트리, 힙, 리스트와 같은 기본적인 자료 구조는 직접 구현할 수 있어야 B형 문제 풀이를 대비할 수 있음 [(13072)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/13000/Solution_13072.java)

## [Stack (스택)](#목차)

1. 후입 선출 구조 (LIFO, Last-In-First-Out): 마지막에 삽입한 자료를 가장 먼저 꺼냄
2. [DFS](#depth-first-search-깊이-우선-탐색) 구현에 자주 사용

## [Queue (큐)](#목차)

1. 선입 선출 구조 (FIFO, First In First Out): 가장 먼저 삽입된 원소는 가장 먼저 삭제
2. Queue는 인터페이스로 구현되어 있고, 실제 사용할 때는 `ArrayDeque 클래스`를 사용하는 것이 속도 측면에서 좋음
3. [BFS](#breadth-first-search-너비-우선-탐색) 구현에 자주 사용
4. Deque의 경우 앞과 뒤 모두에서 삽입, 삭제가 가능한 자료구조.
    1. deque.getFirst() / deque.getLast()로 값을 얻고,
    2. deque.removeFirst() / deque.removeLast()로 값을 삭제

## [Tree (트리)](#목차)

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

## [Heap (힙)](#목차)

1. 완전 이진 트리 중 자식 노드가 부모 노드보다 작은(최대 힙) / 큰(최소 힙) 조건을 만족하는 트리
2. 일반적으로 최댓값, 최솟값을 빠르게 O(log N) 시간 안에 찾기 위해 사용하는 자료 구조
3. [Dijkstra 알고리즘](#dijkstra-다익스트라-알고리즘)에서 활용하면 탐색 시간을 줄일 수 있음
4. PriorityQueue 클래스로 기본 연산 수행 가능. Comparator를 따로 설정해주면 힙 정렬 상태도 수정 가능
    1. 최소힙: PriorityQueue()
    2. 최대힙: PrioirtyQueue(Collections.reverseOrder())
5. 원하는 객체 중에서 정렬 조건을 만족했을 때 가장 앞에 나오는 객체를 고르고 싶다면 Comparable 인터페이스를 구현한 뒤 PriorityQueue에 집어넣으면 됨. [(16236)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16236.java)

## [Set (집합)](#목차)

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

## [LinkedList (연결 리스트)](#목차)

1. 연산 시간 복잡도 비교
    1. Array: 정적, 조회 O(1), 삽입/삭제 O(N)
    2. ArrayList: 동적, 조회 O(1), 삽입/삭제 O(N)
    3. LinkedList: 동적, 조회 O(N), 삽입/삭제 O(1)
2. 기본적으로 A형 알고리즘 문제를 풀 때는 ArrayList를 사용하는 것이 메모리, 시간적인 측면에서 더 좋은 결과를 가져옴.
3. 리스트에 삽입, 삭제 연산을 여러 번 수행해야 할 경우 뒤 인덱스부터 수행하면 불필요한 처리 작업을 수행하지 않아도 된다.

# [Greedy Algorithm](#목차) [(그리디 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Greedy.md)

1. 여러 경우 중 하나를 선택할 때 그 순간에 최적이라고 생각되는 것을 선택하는 알고리즘.
    1. 탐욕적 선택이 최적해로 갈 수 있음이 증명된 경우에만 사용
    2. 최적 부분 구조: 탐욕적 선택 이후 동일한 하위 문제를 해결해야 하는 구조에 사용
2. `한 번 선택한 것을 번복하지 않음`
3. 규칙을 찾지 못하고 너무 복잡하게 해결하는 경우 일정 조건을 완전 탐색으로 제한하고 그리디로 접근하는 방식도 좋음 [(2138)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2138.java)

# [Divide and Conquer](#목차) [(분할 정복)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DevideAndConquer.md)

1.  1. 분할: 해결할 문제를 여러 작은 부분으로 나눔
    2. 정복: 나눈 작은 문제를 각각 해결
    3. 통합: 필요하면 해결된 해답을 모음
2.  시간 복잡도를 줄이기 위해 `현재의 문제를 크기가 작은 문제 여러 개로 분할하여 계산할 수 있는지 확인해볼 필요가 있다.` [(1799)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1799.java)

## 분할 정복을 이용한 거듭제곱

1. base<sup>N</sup>의 계산을 O(log N) 시간에 수행하기 위한 방법
2. 주로 [나머지 연산의 성질](#modulo-나머지-연산)을 이용하여 특정 수를 제곱한 뒤, 해당 수를 나눈 나머지를 계산하는 문제가 자주 출제된다.
3. 알고리즘

    1. 거듭제곱을 저장할 수 있도록 새로운 클래스를 하나 만들어주고, 이진 탐색에 사용하기 위해 Comparable 인터페이스도 구현한다.

    ```java
    // 거듭제곱 저장 클래스
    public static class Exponent implements Comparable<Exponent> {
    	long power;
    	long val;

    	public Exponent(long power, long val) {
    		super();
    		this.power = power;
    		this.val = val;
    	}

    	@Override
    	public int compareTo(Exponent o) {
    		return Long.compare(this.power, o.power);
    	}

    	@Override
    	public String toString() {
    		return "Exponent [power=" + power + ", val=" + val + "]";
    	}
    }
    ```

    2. 거듭 제곱을 미리 계산하고, 나머지 연산의 성질을 이용하여 미리 나머지만 남긴다.

    ```java
    // 지수
    long power = 1;
    // 거듭 제곱 값
    long val = 2;
    // 거듭 제곱 미리 계산
    TreeSet<Exponent> powDP = new TreeSet<>();
    while(power <= K) {
      powDP.add(new Exponent(power, val));
      power *= 2;
      val *= val;
      val %= DIV;
    }
    ```

    3. 곱하기 연산의 수행 횟수를 K라고 했을 때, K보다 작은 가장 큰 지수를 찾아 해당 지수의 거듭 제곱 결과를 곱한다. 이는 TreeSet의 floor 메서드를 통해 빠르게 찾을 수 있다.
       이후 거듭 제곱의 지수만큼 횟수를 차감한 뒤 연산을 반복한다.

    ```java
    Exponent curr;
    while(K > 0) {
      binarySearch.power = K;
      curr = twoPow.floor(binarySearch);
      // 결과 계산
      result *= curr.val;
      result %= DIV;
      // 곱하기 연산 수행 횟수 차감
      K -= curr.power;
    }
    ```

## [Binary Search](#목차) [(이분 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/BinarySearch.md)

1. 자료의 가운데에 있는 항목의 키 값과 비교하여 다음 검색의 위치를 결정하고 검색을 계속 진행하는 방식
2. `자료가 정렬된 상태에서만 가능`.
   입력 자체가 정렬되어 들어오는 경우 이분 탐색을 생각해보면 좋다.
3. 이진 탐색 도중에 다른 작업을 수행하지 않고, 특정 값이 위치할 Index를 찾으려고 한다면 Arrays.binarySearch() 메서드를 사용해도 된다.
    1. Arrays.binarySearch()에서 목표 값을 못 찾는 경우 해당 값이 위치해야 할 Index를 음수로 만든 후 -1을 더한다. (2번 인덱스에 있어야 했다면 -3을 반환)
    2. Arrays.binarySearch(배열, fromIndex, toIndex, key)로 범위 지정도 가능
4. 이분 탐색 팁
    1. 이분 탐색을 구현 할 때 ==, >, <와 같이 3가지 경우로 나누기보다 >=, <와 같이 두 가지 경우로 나눠서 구현하여 생각하는 것이 좋은 사고 방식
    2. 중간값을 계산할 때
    ```
    (start + end) / 2
    ```
    와 같이 계산하면 오버플로우가 발생할 수 있기 때문에
    ```
    start + (end - start) / 2
    ```
    처럼 계산하는 것이 좋다. 3. 중간값 별 다음 탐색 범위 설정
    ```
    start + (end - start) / 2
    left의 end = mid
    right의 start = mid + 1
    ```
    ```
    start + (end - start) / 2 + 1
    left의 end = mid - 1
    right의 start = mid
    ```

## [Parametric Search](#목차) [(매개 변수 탐색)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/BinarySearch.md)

1. Binary Search (이분 탐색)가 일정 범위 내부에서 일정 조건을 만족하는 원소를 찾는 `최적화 문제`를 해결하는 알고리즘이라면, 이를 어떤 값이 조건을 만족하는 지 확인하는 `결정 문제`로 바꿔서 해결하는 방식이 Parametric Search (매개 변수 탐색)이다.
2. 파라미터 탐색이 가능한 조건
    ```
    x1 < x2 일 때, f(x1) = true라면 f(x2)도 true인 경우
    ```
    이러한 조건을 만족하면 이분 탐색을 수행하는 것처럼 함수 값을 비교하면서 O(log N)으로 비교 횟수를 줄일 수 있다.
    즉, `이분 탐색 내부에서 함수를 적용하여 다음 분기를 결정하는 문제는 파라미터 탐색 방식으로 해결할 수 있다.`

## [Longest Increasing Subsequence (LIS, 최장 증가 부분 수열)](#목차) [(참고)](https://chanhuiseok.github.io/posts/algo-49/)

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

# [Backtracking](#목차) [(백트래킹)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Backtracking.md)

1. 해를 찾는 도중, 해가 아니라서 막히면 되돌아가서 다시 해를 찾아가는 기법
2. 되돌아 갈 때 이전 상태를 반복하지 않도록 하는 장치와 현재 선택했던 상태를 지우는 방법을 구현해주어야 한다. [(1799)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1799.java) [(2239)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2239.java)
3. DFS를 수행하면서 유망하지 않은 노드가 어떤 것인지 확실하게 판별해야 함. 완전탐색을 수행하면서 '시간 초과'나 '메모리 초과'가 아닌 '틀렸습니다'가 발생하는 경우 지금까지 당연히 맞다고 생각했던 조건을 다시 생각해볼 필요가 있음 [(6987)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/6000/Main_6987.java)

# [Dynamic Programming](#목차) [(다이나믹 프로그래밍)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/DynamicProgramming.md)

1. 특정 범위까지의 값을 구하기 위해서 이전에 계산했던 다른 범위의 값을 이용하여 효율적으로 계산하는 알고리즘

## [Longest Common Subsequence (LCS)](#목차) [(9252)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9252.java)

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

## [Lowest Common Ancestor (LCA) 알고리즘](#목차)

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

## [Traveling Salesman Problem (TSP, 외판원 문제)](#목차)

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

# [Union Find](#목차) [(Disjoint Set)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/UnionFind.md)

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

## [Flood Fill 알고리즘](#목차)

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

# [Graph](#목차) [(그래프 탐색 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Graph.md)

1. 인접 행렬 / 인접 리스트 / 인접 해시테이블로 그래프 표현 가능
2. 인접 리스트의 경우 ArrayList로 구현했을 때 메모리를 생각보다 많이 잡아먹지 않음

## [Dijkstra (다익스트라 알고리즘)](#목차)

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

## [Bellman-Ford (밸만-포드 알고리즘)](#목차)

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

## [Floyd-Warshall (플로이드-워셜 알고리즘)](#목차)

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

## [Minimum Spanning Tree (최소 신장 트리)](#목차)

### [1. Kruskal (크루스칼 알고리즘)](#목차)

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

### [2. Prim (프림 알고리즘)](#목차)

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

## [Topological Sort (위상 정렬)](#목차)

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

### [DAG에서의 최단 경로 (최장 경로)](#목차)

1. 기본적으로 그래프에서의 최장 경로를 구하는 문제는 NP-Hard로 정의 되지만, DAG에서는 위상 정렬을 통해 빠르게 계산할 수 있다. [(1948)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1948.java)
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

## [Strongly Connected Component (강한 결합 요소)](#목차)

# [Segment Tree](#목차) [(세그먼트 트리)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/SegmentTree.md)

1. 수열이 주어질 때 특정 구간의 합을 O(log N) 시간에 계산할 수 있도록 만드는 자료구조
2. 구간 합 트리를 구성할 때는 (N보다 큰 가장 작은 2의 배수 \* 2) 만큼의 개수를 가지므로 배열을 해당 크기만큼 초기화해줘야 한다. 이는 (4 \* N)만큼의 크기를 가지도록 구현하더라도 문제가 없다. [(참고)](https://m.blog.naver.com/ndb796/221282210534)
3. 알고리즘

    1. 전체 구간 합 구하기
        1. 수열의 범위를 절반씩 줄여가면서 구간의 합을 계산
        2. Index가 1부터 시작하도록 구현하면 자식 노드를 구하기 편하다.

    ```java
    // 범위를 절반씩 줄이면서 구간 합 구하기
    public long init(int start, int end, int idx) {
      // 리프 노드인 경우 현재 값 추가
      if(start == end) {
         subSum[idx] = num[start];
         return subSum[idx];
      }

      int mid = (start + end) / 2;
      // 왼쪽 자식 노드
      long left = init(start, mid, idx * 2);
      // 오른쪽 자식 노드
      long right = init(mid + 1, end, idx * 2 + 1);

      // 양쪽 자식 노드 더하고 리턴
      subSum[idx] = left + right;
      return subSum[idx];
    }
    ```

    2. 구간 합 계산하기
        1. 수열의 범위를 절반 씩 줄여가며 입력 받은 범위와 겹치는지 체크

    ```java
    private long calculateSum(int start, int end, int idx, int sumStart, int sumEnd) {
      // 범위를 벗어난 경우 더할 필요 없음
      if(end < sumStart || sumEnd < start) return 0;
      // 범위 안에 완전히 포함된 경우 그대로 더하기
      if(sumStart <= start && end <= sumEnd) return subSum[idx];

      // 범위가 겹친 경우 범위를 절반으로 줄여서 더하기
      int mid = (start + end) / 2;
      // 왼쪽 자식 노드
      long left = calculateSum(start, mid, idx * 2, sumStart, sumEnd);
      // 오른쪽 자식 노드
      long right = calculateSum(mid + 1, end, idx * 2 + 1, sumStart, sumEnd);

      return left + right;
    }
    ```

    3. 특정 원소 값 수정하기
        1. 수정하려는 원소의 Index를 포함하는 구간 합이 존재한다면 값을 갱신

    ```java
    private void updateSum(int start, int end, int idx, int changeIdx, long diff) {
      // 범위를 벗어나면 생략
      if(changeIdx < start || end < changeIdx) return;

      // 현재 구간합 갱신
      subSum[idx] += diff;
      // 리프 노드는 생략
      if(start == end) return;

      // 자식 노드도 갱신
      int mid = (start + end) / 2;
      // 왼쪽 자식 노드
      updateSum(start, mid, idx * 2, changeIdx, diff);
      // 오른쪽 자식 노드
      updateSum(mid + 1, end, idx * 2 + 1, changeIdx, diff);
    }
    ```

## [Fenwick Tree](#목차) [(Binary Indexed Tree, BIT)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/SegmentTree.md)

1. 세그먼트 트리의 메모리를 절약하기 위한 목적으로 고안. 구간 합 대신 부분 합만을 빠르게 계산할 수 있는 자료구조를 만들어도 구간 합을 빠르게 계산할 수 있다. [(참고)](https://www.crocus.co.kr/666)
2. 세그먼트 트리는 수열의 길이가 N일 때 4N 정도의 배열을 선언한 뒤 트리를 구현했지만, 펜윅 트리는 N 크기의 배열만 선언해도 된다.
3. 알고리즘

    1. 기본적인 아이디어는 2의 보수를 이용하여 맨 뒤에 위치한 1의 위치를 파악하는 것으로 시작된다.

    ```
    i & -i
    ```

    2. 수열을 입력 받을 때는 펜윅 트리를 구성하기 편하게 Index가 1부터 시작하도록 하는 것이 좋다.
    3. 특정 원소 값 수정하기

    ```java
    public void update(int i, int diff) {
      // 수를 담당하는 구간 전부 갱신
      while(i <= N) {
         tree[i] += diff;
         // 마지막 1의 값을 더하는 방식으로 수행
         i += i & -i;
      }
    }
    ```

    세그먼트 트리와 달리 수열을 한 번에 받아 트리를 구성하는 과정이 없고, 수열의 각 Index를 하나씩 업데이트 하는 과정으로 대체할 수 있다.

    ```java
    for(int i = 1; i <= N; i++) {
      update(i, sequence[i]);
    }
    ```

    4. 구간 합 구하기

    ```java
    // 1 ~ i까지의 합 계산
    public int sum(int i) {
      int result = 0;
      while(i > 0) {
         result += tree[i];
         // 마지막 1의 값을 빼는 방식으로 수행
         i -= i & -i;
      }

      return result;
    }
    ```

    i ~ j의 구간 합을 구하고 싶다면 위의 Index 1부터의 합을 계산하는 함수를 통해 1 ~ j 까지의 합과 1 ~ i - 1 까지의 합을 빼서 계산하면 된다.

    ```
    sum(j) - sum(i - 1)
    ```

4. 펜윅 트리로 최솟값 구하기 [(참고)](https://tkql.tistory.com/69)

    [1, K]에서의 최솟값을 구하는 경우에는 일반적인 펜윅 트리로도 최솟값을 구할 수 있으나, 값이 갱신되거나 임의의 구간 [a, b]에서의 최솟값을 구하는 경우 불필요한 값이 포함되어 있기 때문에 사용할 수 없다.  
    이 경우 펜윅 트리를 하나는 정방향, 다른 하나는 역방향으로 구성하여 첫 번째 트리에서는 b부터 a까지의 최솟값을, 두 번째 트리에서는 a부터 b까지의 최솟값을 비교하여 찾을 수 있다.

    ```java
    // 값 갱신 (정방향, 역방향 한 번씩)
    public void updateMinVal(int i, int val) {
       sequence[i] = val;
       update(i, val);
       update2(i, val);
    }

    // 일반적인 펜윅 트리 업데이트
    public void update(int i, int val) {
       while(i <= N) {
          tree[i] = Math.min(tree[i], val);
          i += i & -i;
       }
    }

    // 역방향 펜윅 트리 업데이트
    public void update2(int i, int val) {
       while(i > 0) {
          tree2[i] = Math.min(tree2[i], val);
          i -= i & -i;
       }
    }
    ```

    ```java
    // a, b 사이의 최솟값 리턴
    public int getMinValue(int a, int b) {
      int min = Integer.MAX_VALUE;

      int prev, curr;
      prev = a;
      curr = prev + (prev & - prev);
      while(curr <= b) {
         min = Math.min(min, tree2[prev]);
         prev = curr;
         curr = prev + (prev & - prev);
      }
      min = Math.min(min, sequence[prev]);

      prev = b;
      curr = prev - (prev & -prev);
      while(curr >= a) {
         min = Math.min(min, tree[prev]);
         prev = curr;
         curr = prev - (prev & -prev);
      }

      return min;
    }
    ```

# [String](#목차) [(문자열)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/String.md)

## [Knuth-Morris-Pratt (KMP) Pattern Matching](#목차)

1. 문자열의 각 위치를 비교하면서 다른 문자열이 나왔다면, 이전까지 일치했던 부분으로 되돌아가 이어서 탐색하도록 하는 알고리즘
2. 시간 복잡도: O(N + M)
3. 알고리즘

    1. 접두사 - 접미사의 최대 일치 길이 테이블 계산하기

    ```java
    // 접두사, 접미사 일치 길이 테이블 계산
    public static int[] makeTable(String pattern) {
    	int size = pattern.length();
    	int[] table = new int[size];

    	int j = 0;
    	for(int i = 1; i < size; i++) {
    		while(j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
    			j = table[j - 1];
    		}

    		if(pattern.charAt(i) == pattern.charAt(j)) {
    			table[i] = ++j;
    		}
    	}

    	return table;
    }
    ```

    2. 테이블이 일치하지 않는다면 일치하는 부분까지 돌아가서 이어서 탐색 수행

    ```java
    for(int i = 0; i < size; i++) {
      // 일치하는 부분까지 돌아가기
      while(j > 0 && input.charAt(i) != pattern.charAt(j)) {
         j = table[j - 1];
      }

      if(input.charAt(i) == pattern.charAt(j)) {
         j++;
         if(j == patternSize) {
            j = table[j - 1];
            sameIndex.add(i - patternSize + 1);
         }
      }
    }
    ```

4. 오토마타 방식으로 구현하기
   ![오토마타](https://user-images.githubusercontent.com/20474034/233273807-83bbd053-4604-493f-9728-683fa9072017.PNG)

    1. 문자열을 트라이처럼 노드를 연결한 상태로 구성한 뒤 Fail 함수 계산

    ```java
    // Fail 시 이동할 노드 탐색
    public void findFail() {
      // 루트 노드의 Fail은 루트 노드
      root.fail = root;

      Node curr = root;
      Node next, fail;
      for(int i = 0; i < N; i++) {
         next = curr.next;

         if(curr == root) {
            // 루트 노드의 자식 노드의 Fail은 루트 노드
            next.fail = root;
         } else {
            fail = curr.fail;
            while(fail != root && fail.next.val != next.val) {
               fail = fail.fail;
            }
            if(fail.next.val == next.val) fail = fail.next;

            next.fail = fail;
         }

         curr = next;
      }
    }
    ```

    2. 재귀적으로 Fail 노드를 타면서 다음 위치 탐색

    ```java
    // 입력 받은 노드의 다음 노드 탐색
    public Node getNext(Node curr, char val) {
      while(curr != root && (curr.next == null || curr.next.val != val)) {
         curr = curr.fail;
      }
      if(curr.next.val == val) curr = curr.next;

      return curr;
    }
    ```

## [Rabin-Karp Algorithm (라빈-카프 알고리즘)](#목차)

1. 문자열을 해싱한 뒤 해시 값으로 비교하는 알고리즘
2. 최악의 경우 O(MN)이지만 평균적으로 O(N). 선형에 가까움
3. 알고리즘

    1. 해시 값을 계산할 때는 이전에 계산했던 해시 값을 재활용하여 Sliding Window 형태로 계산
    2. 문자열 길이가 길어지는 경우 해시 값에 Modular 연산을 수행하여 일정 자리수로 맞춰준다.
    3. 해시 값이 일치하더라도 실제 문자열이 일치하지 않을 수 있으므로 해시 값이 일치할 때 Brute Force로 일치하는 지 검사해야 한다.

## [Boyer-Moore Algorithm (보이어-무어 알고리즘)](#목차)

1. 패턴과 문자열을 비교할 때 불일치하는 부분이 있다면 패턴 내에 이동하는 해당 문자의 위치로 이동하는 알고리즘
2. 최악의 경우 O(MN). 평균적으로 O(N)
3. 알고리즘
    1. 문자가 불일치 할 때 이동할 거리를 계산한 Skip 테이블 구성
    2. 패턴을 비교할 때 오른쪽에서 왼쪽으로 비교
    3. 문자가 불일치한다면 Skip 테이블에서의 이동 거리만큼 이동.
    4. 마지막 문자가 아닌 중간에서 불일치가 발생한 경우 (Skip 테이블의 값 - 일치한 문자 개수) 만큼 이동

## [Trie](#목차) [(트라이)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Trie.md)

1. 문자열 집합을 관리하는 트리. 각 노드에 하나씩 단위(알파벳, 이진수, 문자열)가 대응됨.
2. 문자열의 삽입/삭제/탐색의 시간복잡도는 O(문자열의 길이)
3. 알고리즘

    1. 각 노드별로 알파벳 개수 크기의 배열을 할당하여 구현 [(27652)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/27000/Main_27652.java)

    ```java
    public static class Node {
    	int count;
    	Node[] child;

    	public Node() {
    		this.count = 0;
    		this.child = new Node[SIZE];
    	}

    	public Node addChar(char c) {
    		if(child[c - 'a'] == null) child[c - 'a'] = new Node();
    		child[c - 'a'].count++;

    		return child[c - 'a'];
    	}

    	public Node deleteChar(char c) {
    		child[c - 'a'].count--;

    		return child[c - 'a'];
    	}

    	public Node getChar(char c) {
    		return child[c - 'a'];
    	}
    }
    ```

    2. 각 노드별로 Map을 통해 자식 노드를 저장하여 구현 [(7432)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/7000/Main_7432.java)

    ```java
    public static class Node implements Comparable<Node>{
    	int depth;
    	String name;
    	Map<String, Node> child;

    	public Node(String name, int depth) {
    		this.name = name;
    		this.depth = depth;
    		this.child = new TreeMap<>();
    	}

    	public void putString(String input) {
    		int firstIdx = findFirst(input);
    		if(firstIdx == -1) {
    			if(!child.containsKey(input)) child.put(input, new Node(input, depth + 1));
    			return;
    		}

    		String curr = input.substring(0, firstIdx);
    		String next = input.substring(firstIdx + 1, input.length());

    		if(!child.containsKey(curr)) child.put(curr, new Node(curr, depth + 1));
    		// 자식 노드에 재귀적으로 추가
    		child.get(curr).putString(next);
    	}
    }
    ```

## [Aho-Corasick](#목차) [(아호-코라식 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Trie.md)

1. 일대다 문자열 패턴 매칭 알고리즘. 일치하는지 판단할 패턴 문자열이 여러 개인 경우 패턴을 통해서 트라이를 구성한 뒤, 트라이 내부에서 BFS를 통해 Fail이 발생했을 때 이동할 노드를 탐색한다. [(그림 출처)](https://ansohxxn.github.io/algorithm/ahocorasick/)

![아호코라식](https://user-images.githubusercontent.com/20474034/233271722-a5ce6378-6780-4493-a7f3-58f3aeb55dae.PNG)

2. 알고리즘 [(10538)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/10000/Main_10538.java)

    1. 트라이 구성 이후 Fail 시 이동할 노드 탐색  
       1-1. 현재 자신을 Fail 노드로 가리키는 경우 또 Fail이 발생했을 때 이동할 위치를 탐색하는 것이기 때문에 루트 노드의 자식 노드는 루트 노드를 가리키도록 설정

    ```java
    // BFS를 통해 트라이 노드의 Fail 노드 구성
    public void findFail() {
       Queue<Node> bfsQueue = new ArrayDeque<>();
       // 루트 노드에서 fail 하면 루트 노드로 이동
       root.fail = root;
       bfsQueue.offer(root);

       Node curr = null, next, fail;
       while(!bfsQueue.isEmpty()) {
          curr = bfsQueue.poll();

          // 리프 노드인 경우 중단
          if(curr.isLeaf()) break;

          // 이진 트라이
          for(int idx = 0; idx < 2; idx++) {
             next = curr.child[idx];
             // Fail인 경우 생략
             if(next == null) continue;

             // 루트 노드의 자식이 실패한 경우 반드시 루트로 이동
             if(curr == root) next.fail = root;
             else {
                // Fail 노드 탐색
                fail = curr.fail;
                // 현재 노드에서 실패했을 때 이동하는 노드에서 다음 위치가 존재할 때까지 올라가기
                while(fail != root && fail.child[idx] == null) {
                   fail = fail.fail;
                }
                if(fail.child[idx] != null) fail = fail.child[idx];

                next.fail = fail;
             }

             // 자식 노드 Queue에 넣기
             bfsQueue.offer(next);
          }
       }
    }
    ```

    2. Fail이 발생한 경우 KMP와 동일하게 재귀적으로 다음 문자가 존재할 때까지 (Fail이 아닐 때까지) 루트 노드까지 이동

    ```java
    // 현재 위치에서 Index와 맞는 다음 노드 반환
    public static Node nextNode(Node curr, int idx) {
       // KMP 처럼 다음 Index 맞는 부분 탐색
       while(curr.val != ROOT && curr.child[idx] == null) {
          curr = curr.fail;
       }
       if(curr.child[idx] != null) curr = curr.child[idx];

       return curr;
    }
    ```

# [Sorting (정렬 알고리즘)](#목차)

## [Merge Sort (병합 정렬)](#목차)

1.  분할 정복을 통해 배열을 정렬하는 방식. 시간 복잡도는 O(N log N)
2.  알고리즘

    1. 배열의 범위를 절반씩 나누면서 왼쪽, 오른쪽 배열을 재귀적으로 정렬
    2. 양쪽의 배열을 정렬한 이후 두 배열을 합치면서 정렬

    ```java
    // 병합 정렬
    public static long mergeSort(int start, int end, int[] arr) {
       if(start == end) return 0;

       int mid = start + (end - start) / 2;

       // 왼쪽 배열
       mergeSort(start, mid, arr);
       // 오른쪽 배열
       mergeSort(mid + 1, end, arr);

       // 배열 합치기
       merge(start, end, arr);
    }
    ```

    3. 배열을 합칠 때는 왼쪽 배열과 오른쪽 배열의 Index에 위치한 값을 비교하며 더 작은 값을 결과 배열에 추가한 뒤 Index++을 수행한다.

    ```java
    public static void merge(int start, int end, int[] arr) {
    // 병합 정렬한 결과 저장할 배열
    int[] result = new int[end - start + 1];
    int mid = start + (end - start) / 2;

    // 병합
    int i = 0, j = 0;
    while(i + start <= mid && j + mid + 1 <= end) {
       if(arr[i + start] >= arr[j + mid + 1]) {
          result[i + j] = arr[j++ + mid + 1];
       } else {
          result[i + j] = arr[i++ + start];
       }
    }

    // 남은 부분 정렬
    while(i + start <= mid) {
       result[i + j] = arr[i++ + start];
    }
    while(j + mid + 1 <= end) {
       result[i + j] = arr[j++ + mid + 1];
    }

    // 병합 정렬한 결과 배열에 반영
    int len = end - start + 1;
    for(int k = 0; k < len; k++) {
       arr[start + k] = result[k];
    }
    }
    ```

# [ETC. (기타 기법)](#목차)

## [Two Pointers](#목차) [(투포인터 알고리즘)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/TwoPointers.md)

1.  index 값을 가지는 i, j를 사용하여, 일정 조건에 따라 `i와 j를 증감 연산자로` 이동시켜 불필요한 경우의 수를 제거하는 알고리즘. [(16472)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16472.java)
2.  슬라이딩 윈도우와 비슷하지만, 구간의 너비가 가변적이라는 차이점이 존재. [(Two Pointers)](https://butter-shower.tistory.com/226)

## [Sliding Window (슬라이딩 윈도우)](#목차)

1.  고정 사이즈의 윈도우가 이동하면서 윈도우 내에 있는 데이터로 문제를 푸는 방식

## [Bitmasking](#목차) [(비트마스킹)](https://github.com/rldnjs7723/CodingTest/blob/main/Ideas/Bitmasking.md)

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

## [Recursive (재귀)](#목차)

1. 자신의 정의하는 내용 안에 자신을 포함하는 형태의 함수
2. 주어진 문제의 해를 구하기 위해 `동일하면서 더 작은 문제(최적 부분 구조)`의 해를 이용하는 방법
3. 점화식 / 수열의 귀납적 정의에 자주 이용 [(9527)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9527.java)
4. [DFS](#depth-first-search-깊이-우선-탐색) 탐색을 수행할 때 Stack을 사용하는 경우보다 빠름. `재귀 깊이가 너무 깊지 않은 경우 재귀로 구현하는 것이 좋음` [(3234)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/3000/Solution_3234.java)

## [Sweeping (스위핑 알고리즘)](#목차)

1. 전체 공간에 대해 같은 방향으로 이동하면서 마주치는 모든 원소에 대해 한 번씩만 작업을 수행해주며 진행하는 알고리즘

## [Prefix Sum (누적 합)](#목차)

1. 앞선 Index에 대한 정보를 뒤의 Index가 저장하고 있다는 특성을 이용하여 문제를 해결하는 방법

## [Ad Hoc (애드 혹)](#목차)

1. 해당 문제를 풀기 위해 잘 알려진 알고리즘을 적용하지 않고 해결할 수 있는 유형의 문제
2. 구현, 수학, 그리디 관련이 포함되는 경우가 있고, 문제의 규칙을 파악해야 하는 문제도 있다. [(16919)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16919.java)

## [Sparse Table (희소 배열)](#목차)

1. 배열 원소의 개수가 무조건 배열의 length 값보다 작은 배열
2. LCA와 같이 자신의 조상 노드의 정보를 저장할 때 깊이 차이가 2의 제곱수가 되는 조상만 저장하도록 하는 경우를 생각할 수 있다.
