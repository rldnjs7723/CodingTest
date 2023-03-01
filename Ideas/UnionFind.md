# Union Find (Disjoint Set, 분리 집합) 알고리즘 문제 정리

1. [BOJ_16946 벽 부수고 이동하기 4](#1-boj_16946-벽-부수고-이동하기-4-백준-링크-소스-코드)
2. [BOJ_10026 적록색약](#2-boj_10026-적록색약-백준-링크-소스-코드)
3. [BOJ_16724 피리 부는 사나이](#3-boj_16724-피리-부는-사나이-백준-링크-소스-코드)
4. [SWEA_3289 서로소 집합](#4-swea_3289-서로소-집합-백준-링크-소스-코드)

## 1. BOJ_16946 벽 부수고 이동하기 4 [(백준 링크)](https://www.acmicpc.net/problem/16946) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16946.java)

### 문제 요약

벽 주변에서 도달할 수 있는 모든 0의 개수를 세서 배열로 표현하는 문제

### 풀이 아이디어

모든 벽에 대해서 주변의 0의 개수를 체크해야 하기 때문에 각각 BFS를 수행하면 시간이 너무 오래 걸려 이미 개수를 셌던 구획은 다시 세지 않도록 따로 객체를 통해 표시해 둠.  
추가적인 시간 복잡도 차이가 없기 때문에 DFS로 수행해도 무방할 것이라 생각  
`묶여 있는 그룹을 같은 객체로 표시하는 발상이 Union Find 알고리즘을 적용했다고 생각해도 무방 (Flood Fill 알고리즘)`

## 2. BOJ_10026 적록색약 [(백준 링크)](https://www.acmicpc.net/problem/10026) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/10000/Main_10026.java)

### 문제 요약

색이 같은 구역끼리 묶은 뒤, 구역의 개수를 출력하는 문제

### 풀이 아이디어

[백준 16946 벽 부수고 이동하기 4](#1-boj_16946-벽-부수고-이동하기-4-백준-링크-소스-코드)와 같이 객체를 통해 방문 체크를 수행 (Flood Fill 알고리즘).  
객체를 Set에 넣었을 때 equals랑 hashcode 메서드를 상속 받지 않았다면 객체의 주소값을 기준으로 동일한 객체를 판별하기 때문에, 객체가 몇 개 존재하는지가 몇 개의 구역이 존재하는지를 나타냄

## 3. BOJ_16724 피리 부는 사나이 [(백준 링크)](https://www.acmicpc.net/problem/16724) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16724.java)

### 문제 요약

지도에 각 행, 열의 위치에서 이동할 방향이 주어져 있고, 방향을 따라 계속 이동했을 때 반드시 도달할 지역의 개수를 세는 문제.

### 풀이 아이디어

BFS를 통해 현재 위치에서 나가는 방향이거나 현재 위치로 들어오는 방향의 구역을 전부 같은 그룹으로 묶는다면 각 그룹의 마지막 위치에만 SAFE ZONE을 설치하면 되기 때문에, 전체 그룹의 개수를 카운트하기만 하면 되는 문제.  
다른 Union Find 문제와 마찬가지로 더미 객체를 생성하여 방문 체크와 그룹 포함을 수행

## 4. SWEA_3289 서로소 집합 [(SWEA 링크)](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWBJKA6qr2oDFAWr) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/3000/Main_3289.java)

### 문제 요약

1부터 N까지 자기 자신만을 원소로 가지는 집합이 있을 때, 합집합 연산 / 두 집합이 같은 집합인지 확인하는 연산 두 가지를 구현하고, 결과를 출력하는 문제

### 풀이 아이디어

전형적인 [Union Find (Disjoint Set)](https://github.com/rldnjs7723/CodingTest#union-find-disjoint-set) 문제.  
Path Compression과 Rank를 이용한 Union을 통해 해결하면 수행 시간을 단축할 수 있다.

## 5. BOJ_1197 최소 스패닝 트리 [(백준 링크)](https://www.acmicpc.net/problem/1197) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1197.java)

### 문제 요약

그래프의 간선이 주어졌을 때 최소 신장 트리의 가중치 합을 계산하는 문제

### 풀이 아이디어

유명한 MST 문제.  
[Kruskal 알고리즘](https://github.com/rldnjs7723/CodingTest#1-kruskal-%ED%81%AC%EB%A3%A8%EC%8A%A4%EC%B9%BC-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98)으로 푸는 경우 Union Find과 우선순위 큐를 기반으로 탐색을 수행할 수 있다.
