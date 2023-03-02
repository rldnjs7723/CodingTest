# 그래프 탐색 알고리즘 문제 정리

## 목차

1. [BOJ_1753 최단 경로](#1-boj_1753-최단-경로-백준-링크-소스-코드)
2. [BOJ_11779 최소비용 구하기 2](#2-boj_11779-최소비용-구하기-2-백준-링크-소스-코드)
3. [BOJ_1238 파티](#3-boj_1238-파티-백준-링크-소스-코드)
4. [SWEA_3124 최소 스패닝 트리](#4-swea_3124-최소-스패닝-트리-swea-링크-소스-코드)

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
