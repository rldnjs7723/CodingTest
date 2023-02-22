# 그래프 탐색 알고리즘 문제 정리

## 목차

1. [BOJ_1753 최단 경로](#1-boj_1753-최단-경로-백준-링크-소스-코드)

## 1. BOJ_1753 최단 경로 [(백준 링크)](https://www.acmicpc.net/problem/1753) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1753.java)

### 문제 요약

정점과 간선이 입력으로 주어졌을 때 입력 받은 시작점을 기준으로 다른 정점에 이르는 최단 경로의 가중치 값을 계산하는 문제.

### 풀이 아이디어

[Dijkstra 알고리즘](https://github.com/rldnjs7723/CodingTest/blob/main/README.md#dijkstra-다익스트라-알고리즘)
을 사용해서 해결하는 전형적인 문제. 일반적인 Dijkstra 알고리즘을 사용해도 시간초과가 발생하지는 않지만, `우선순위 큐를 사용해서 해결하면 더 빠른 해답을 얻을 수 있다.`
