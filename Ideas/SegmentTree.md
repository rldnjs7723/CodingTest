# 세그먼트 트리 문제 정리

## 목차

1. [BOJ_2042 구간 합 구하기](#1-boj_2042-구간-합-구하기-백준-링크-소스-코드)
2. [BOJ_14428 수열과 쿼리 16](#2-boj_14428-수열과-쿼리-16-백준-링크-소스-코드)
3. [BOJ_10090 Counting Inversions](#3-counting-inversions-백준-링크-소스-코드)
4. [BOJ_10999 구간 합 구하기 2](#4-boj_10999-구간-합-구하기-2-백준-링크-소스-코드)
5. [BOJ_17353 하늘에서 떨어지는 1, 2, ..., R-L+1개의 별](#5-boj_17353-하늘에서-떨어지는-1-2--r-l1개의-별-백준-링크-소스-코드)
6. [BOJ_11658 구간 합 구하기 3](#6-boj_11658-구간-합-구하기-3-백준-링크-소스-코드)

## 1. BOJ_2042 구간 합 구하기 [(백준 링크)](https://www.acmicpc.net/problem/2042) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2042.java)

### 문제 요약

N개의 수를 받아 특정 Index의 숫자를 바꾸고, 특정 구간의 합을 계산하는 문제

### 풀이 아이디어

[Segment Tree (세그먼트 트리)](https://github.com/rldnjs7723/CodingTest#segment-tree-%EC%84%B8%EA%B7%B8%EB%A8%BC%ED%8A%B8-%ED%8A%B8%EB%A6%AC)를 구성하여 해결하는 가장 기본적인 문제.  
구간의 합을 O(log N)의 시간만에 계산할 수 있다.

## 2. BOJ_14428 수열과 쿼리 16 [(백준 링크)](https://www.acmicpc.net/problem/14428) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/14000/Main_14428.java)

### 문제 요약

길이 N인 수열이 주어질 때, 수열의 특정 위치의 값을 바꾸거나 특정 범위 내에서 가장 작은 값을 가지는 Index를 리턴하는 문제.

### 풀이 아이디어

[Segment Tree (세그먼트 트리)](https://github.com/rldnjs7723/CodingTest#segment-tree-%EC%84%B8%EA%B7%B8%EB%A8%BC%ED%8A%B8-%ED%8A%B8%EB%A6%AC)를 간단하게 응용한 문제.  
세그먼트 트리를 구성할 때, 특정 구간의 합을 계산하는 대신 최솟값의 Index를 저장하는 방식으로 구현하면 된다.

## 3. BOJ_10090 Counting Inversions [(백준 링크)](https://www.acmicpc.net/problem/10090) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/10000/Main_10090.java)

### 문제 요약

수열이 주어졌을 때, 해당 수열에서 Inversion이 발생한 횟수를 계산하는 문제. Inversion은 해당 수열이 오름차순으로 정렬되었을 때 자신보다 뒤에 위치하는 수가 앞에 위치하는 경우로 생각할 수 있다.  
[BOJ_7578 공장](https://www.acmicpc.net/problem/7578)을 현재 문제로 변환할 수 있다.

### 풀이 아이디어

[BOJ_7578 공장](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/7000/Main_7578.java)의 경우 현재 문제로 변환한 뒤, 분할 정복(Merge Sort)을 이용하여 해결하였으나 이번에는 세그먼트 트리로 해결해보았다.  
세그먼트 트리는 수열의 앞에서부터 수를 하나씩 추출하여 해당 수보다 큰 값을 가지는 수들이 먼저 등장했는지 여부를 세그먼트 트리의 노드에 저장한 뒤, 이후 자신이 속한 범위의 세그먼트 트리의 모든 노드에 개수를 +1 해주는 방식으로 구현할 수 있다.

## 4. BOJ_10999 구간 합 구하기 2 [(백준 링크)](https://www.acmicpc.net/problem/10999) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/10000/Main_10999.java)

### 문제 요약

수열이 주어지고, 수열의 값을 변경하는 과정이 주어진 구간에 대해서 이뤄질 때 구간 합을 계산하는 문제.

### 풀이 아이디어

[느리게 갱신되는 세그먼트 트리](https://github.com/rldnjs7723/CodingTest#segment-tree-with-lazy-propagation-느리게-갱신되는-세그먼트-트리)를 이용하여 해결하는 대표적인 문제.

## 5. BOJ_17353 하늘에서 떨어지는 1, 2, ..., R-L+1개의 별 [(백준 링크)](https://www.acmicpc.net/problem/17353) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/17000/Main_17353.java)

### 문제 요약

구간 [L, R]이 주어졌을 때 수열의 값에 1, 2, 3, ... , R-L+1을 더하는 방식으로 갱신하는 문제.

### 풀이 아이디어

[느리게 갱신되는 세그먼트 트리](https://github.com/rldnjs7723/CodingTest#segment-tree-with-lazy-propagation-느리게-갱신되는-세그먼트-트리)를 이용하여 해결할 수 있는 문제.  
lazy 값 이외에 lazyCount라는 해당 자식 노드에서 갱신할 쿼리의 개수를 추가로 카운트하여 [등차 수열의 합](https://github.com/rldnjs7723/CodingTest#등차-수열의-합)을 구하는 공식으로 추후 해당 노드에 방문했을 시 값을 더해주어야 한다.

## 6. BOJ_11658 구간 합 구하기 3 [(백준 링크)](https://www.acmicpc.net/problem/11658) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/11000/Main_11658.java)

### 문제 요약

2차원 배열이 주어졌을 때 배열의 직사각형 범위의 값을 모두 더한 값을 계산하는 문제.

### 풀이 아이디어

[다차원 세그먼트 트리](https://github.com/rldnjs7723/CodingTest#multidimensional-segment-tree-다차원-세그먼트-트리)를 사용하는 대표적인 문제.  
단순히 세그먼트 트리 안에 세그먼트 트리를 넣는 작업으로 생각하면 자칫 값을 갱신하는 과정이 $O(N log N)$으로 수행될 수 있기 때문에 조심해야 한다.
