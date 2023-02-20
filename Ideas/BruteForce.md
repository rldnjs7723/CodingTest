# 완전 탐색 알고리즘 문제 정리

## 목차

1. [BOJ_16946 벽 부수고 이동하기 4](#1-boj_16946-벽-부수고-이동하기-4-백준-링크-소스-코드)
2. [SWEA_1953 탈주범 검거](#2-swea_1953-탈주범-검거-swea-링크-소스-코드)

## 1. BOJ_16946 벽 부수고 이동하기 4 [(백준 링크)](https://www.acmicpc.net/problem/16946) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16946.java)

### 문제 요약

벽 주변에서 도달할 수 있는 모든 0의 개수를 세서 배열로 표현하는 문제

### 풀이 아이디어

모든 벽에 대해서 주변의 0의 개수를 체크해야 하기 때문에 각각 BFS를 수행하면 시간이 너무 오래 걸려 이미 개수를 셌던 구획은 다시 세지 않도록 따로 객체를 통해 표시해 둠.  
추가적인 시간 복잡도 차이가 없기 때문에 DFS로 수행해도 무방할 것이라 생각

## 2. SWEA_1953 탈주범 검거 [(SWEA 링크)](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5PpLlKAQ4DFAUq) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/1000/Solution_1953.java)

### 문제 요약

지하 터널 구조와 파이프 별 이동 가능한 상황, 경과한 시간이 주어졌을 때 탈주범이 존재할 수 있는 영역의 개수를 세는 문제

### 풀이 아이디어

기본적인 사방 탐색 + BFS로 문제 해결.  
[백준 16946 벽 부수고 이동하기 4](#1-boj_16946-벽-부수고-이동하기-4-백준-링크-소스-코드)와 같이 객체를 통해 방문 체크를 수행
