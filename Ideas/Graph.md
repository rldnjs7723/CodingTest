# 그래프 탐색 알고리즘 문제 정리

## 목차

1. [BOJ_1753 최단 경로](#1-boj_1753-최단-경로-백준-링크-소스-코드)
2. [BOJ_11779 최소비용 구하기 2](#2-boj_11779-최소비용-구하기-2-백준-링크-소스-코드)
3. [BOJ_1238 파티](#3-boj_1238-파티-백준-링크-소스-코드)
4. [SWEA_3124 최소 스패닝 트리](#4-swea_3124-최소-스패닝-트리-swea-링크-소스-코드)
5. [BOJ_1005 ACM Craft](#5-boj_1005-acm-craft-백준-링크-소스-코드)

## 1. BOJ_1753 최단 경로 [(백준 링크)](https://www.acmicpc.net/problem/1753) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1753.java)

### 문제 요약

정점과 간선이 입력으로 주어졌을 때 입력 받은 시작점을 기준으로 다른 정점에 이르는 최단 경로의 가중치 값을 계산하는 문제.

### 풀이 아이디어

[Dijkstra 알고리즘](https://github.com/rldnjs7723/CodingTest/#dijkstra-다익스트라-알고리즘)을 사용해서 해결하는 전형적인 문제. 일반적인 Dijkstra 알고리즘을 사용해도 시간초과가 발생하지는 않지만, `우선순위 큐를 사용해서 해결하면 더 빠른 해답을 얻을 수 있다.`

## 2. BOJ_11779 최소비용 구하기 2 [(백준 링크)](https://www.acmicpc.net/problem/11779) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/11000/Main_11779.java)

### 문제 요약

도시의 개수, 버스의 개수가 주어지고, 버스는 시작 도시와 도착 도시, 비용에 대한 정보 (양수)가 주어졌을 때 시작 도시부터 목표 도시까지의 최소 비용과 경로 사이의 도시 수와 도시를 출력하는 문제

### 풀이 아이디어

[Dijkstra 알고리즘](https://github.com/rldnjs7723/CodingTest/#dijkstra-다익스트라-알고리즘)을 사용하는 전형적인 문제.  
비용에 대한 정보가 양수로 주어져 있기 때문에 다익스트라를 사용해야 한다고 판단. 이전에 풀었던 문제와 동일하지만 최소 비용을 구성하는 경로를 직접 출력해야 한다는 차이점이 있었기에, 정점의 가중치를 갱신할 때 갱신한 정점에 대한 정보를 저장해야 한다.

## 3. BOJ_1238 파티 [(백준 링크)](https://www.acmicpc.net/problem/1238) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1238.java)

### 문제 요약

마을의 개수와 각 마을 사이를 이동할 수 있는 도로의 정보가 주어졌을 때, 파티가 일어나는 마을까지 왕복하는 시간이 최대가 되는 값을 계산하는 문제.

### 풀이 아이디어

한 마을에서 파티 마을로 가는 모든 최소 비용을 계산해야 한다고 생각하여 [Floyd-Warshall 알고리즘](https://github.com/rldnjs7723/CodingTest/#floyd-warshall-플로이드-워셜-알고리즘)을 사용하는 문제라고 생각하였다. 이렇게 풀어도 자바 기준으로 시간 초과가 발생하지는 않았으나,  
플로이드-워셜 알고리즘의 경우 모든 마을에서 다른 모든 마을로 가는 최소 비용을 계산하는 것이기 때문에 실제 계산해야 하는 경우의 수 자체가 더 많아져 낭비가 된다.  
이 경우, [Dijkstra 알고리즘](https://github.com/rldnjs7723/CodingTest/#dijkstra-다익스트라-알고리즘)을 여러 번 사용하는 것이 플로이드-워셜 알고리즘을 사용하는 것보다 더 빠르게 수행할 수 있다.

## 4. SWEA_3124 최소 스패닝 트리 [(SWEA 링크)](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV_mSnmKUckDFAWb) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/3000/Solution_3124.java)

### 문제 요약

[Kruskal 알고리즘](https://github.com/rldnjs7723/CodingTest#1-kruskal-%ED%81%AC%EB%A3%A8%EC%8A%A4%EC%B9%BC-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98)에서 Rank를 이용한 Union, Path Compression을 적용한 Union Find로 크루스칼 알고리즘을 구현하거나,  
[Prim 알고리즘](https://github.com/rldnjs7723/CodingTest#2-prim-%ED%94%84%EB%A6%BC-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98)에서 Heap을 이용하여 다음 최소 간선을 O(1)의 시간에 찾을 수 있도록 구현해야 하는 문제

### 풀이 아이디어

크루스칼 알고리즘은 백준에서 사용했기 때문에 학습을 위해 프림 알고리즘을 사용하여 구현.  
`최소인 정점을 우선순위 큐에 넣으면 값이 변했을 때 큐가 깨지기 때문에 최소인 간선을 넣어야 한다. Dijkstra 알고리즘도 동일하여 이번 기회에 수정하였다.`

## 5. BOJ_1005 ACM Craft [(백준 링크)](https://www.acmicpc.net/problem/1005) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1005.java)

### 문제 요약

주어진 순서대로 건물을 지어야 할 때, 건물을 지을 때 걸리는 최소 시간을 계산하는 문제

### 풀이 아이디어

최종적으로 지어야 하는 건물을 위상 정렬의 시작점으로 잡고, `진입 간선과 진출 간선을 반대로 설정한다면 위상 정렬의 역순으로 정렬된다.`  
이 때, DFS 형태로 자신보다 먼저 건설해야 하는 건물의 시간을 계산하고, 해당 건물 중에서 시간이 가장 오래 걸리는 건물의 시간 + 현재 건물을 짓는 시간이 현재 건물을 지을 때 걸리는 최소 시간이다.  
이런 방식으로 계산하면 반복 되는 계산이 존재하기 때문에, 다이나믹 프로그래밍으로 방금 전 계산했던 최소 시간을 기록해두고 바로 리턴하도록 하면 빠르게 해결할 수 있다.

## 6. BOJ_1219 오민식의 고민 [(백준 링크)](https://www.acmicpc.net/problem/1219) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1219.java)

### 문제 요약

교통 수단과 비용, 각 도시에 도착했을 때 벌 수 있는 돈의 액수가 주어졌을 때 도착 정점에서의 돈 최댓값을 구하는 문제

### 풀이 아이디어

기본적인 아이디어로는 [Bellman-Ford 알고리즘](https://github.com/rldnjs7723/CodingTest#bellman-ford-%EB%B0%B8%EB%A7%8C-%ED%8F%AC%EB%93%9C-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98)을 사용하여 음의 사이클이 존재하는지 확인만 할 수 있으면 된다.  
다만, 음의 사이클이 존재할 때 해당 사이클이 도착 도시에 영향을 미칠 수 있는지 확인하기 위해 DFS로 연결 되어 있는지 확인하는 작업을 추가로 수행해야 한다.

## 7. BOJ_1948 임계경로 [(백준 링크)](https://www.acmicpc.net/problem/1948) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1948.java)

### 문제 요약

Directed Acyclic Graph (DAG)가 입력으로 주어질 때, 시작 정점에서 도착 정점으로 가는 최장 거리를 계산하고, 최장 경로를 이루는 모든 간선의 개수를 계산하는 문제

### 풀이 아이디어

[DAG에서의 최단 경로](https://github.com/rldnjs7723/CodingTest#topology-sort-%EC%9C%84%EC%83%81-%EC%A0%95%EB%A0%AC)를 구하는 알고리즘을 모르고 있었기에 공부를 하고 문제 풀이를 진행.  
위상 정렬을 통해 DAG에서의 최장 거리를 각 정점에 계산한 뒤, 위상 정렬을 역순으로 수행하면서 해당 간선이 최장 경로를 이루는 간선인지 체크.
