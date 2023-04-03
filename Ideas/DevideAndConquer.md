# 분할 정복 알고리즘 문제 정리

## 목차

1. [BOJ_1799 비숍](#1-boj_1799-비숍-백준-링크-소스-코드)
2. [BOJ_1992 쿼드트리](#2-boj_1992-쿼드트리-백준-링크-소스-코드)
3. [SWEA_13736 사탕 분배](#3-swea_13736-사탕-분배-swea-링크-소스-코드)

## 1. BOJ_1799 비숍 [(백준 링크)](https://www.acmicpc.net/problem/1799) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1799.java)

### 문제 요약

체스판 위의 색칠되지 않은 부분에 비숍을 놓을 때, 최대 몇 개까지 서로의 범위에 겹치지 않게 놓을 수 있는지 계산하는 문제

### 풀이 아이디어

기본적인 아이디어는 일반적인 사방 탐색 + DFS + 백트래킹 문제였으나, 시간 복잡도를 줄이기 위한 핵심적인 아이디어는 분할 정복  
`체스판의 흰색 / 검은색 부분을 나눠서 계산한 뒤` 기존의 시간 복잡도 O(2^(N \* N))에서 O(2^(N / 2 \* N / 2))로 N 자체를 절반으로 감소시킬 수 있다.  
체스판을 두 부분으로 나눈 뒤에는 북서, 북동, 남서, 남동을 탐색하여 비숍을 놓았을 때 체스판에 놓을 수 없는 부분을 표시하는 방식으로 체크하였고, 더 이상 비숍을 놓을 수 없을 때 놓았던 비숍을 없애도록 백트래킹 방식으로 구현

## 2. BOJ_1992 쿼드트리 [(백준 링크)](https://www.acmicpc.net/problem/1992) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1992.java)

### 문제 요약

0과 1로 이루어진 2차원 배열을 모든 값이 1이면 1, 모든 값이 0이면 0으로 표현하여 압축된 문자열로 표현하는 문제

### 풀이 아이디어

강의에서 봤던 예제와 동일한 문제. 현재 모든 값이 1이나 0이 아닌 경우 동일한 크기의 배열 4개로 분할하여 z 형태로 문자열을 이어 붙임

## 3. SWEA_13736 사탕 분배 [(SWEA 링크)](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AX8BB5d6T7gDFARO) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/13000/Solution_13736.java)

### 문제 요약

사탕의 개수 A, B가 주어지고, 해당 사탕의 분배 횟수 K가 주어졌을 때 사탕의 개수가 많은 쪽에서 작은 쪽으로 작은 쪽의 사탕의 개수만큼 분배한 뒤 최종적으로 더 작은 개수의 사탕 수를 출력하는 문제.

### 풀이 아이디어

작은 사탕의 개수는 P, 2P, 4P, 8P... 의 순서로 계속 증가하는데 만약 전체 사탕의 개수보다 커졌다면 전체 사탕의 개수로 나눈 나머지와 결과가 동일하다는 성질을 발견하였기에 [분할 정복을 이용한 거듭제곱](https://github.com/rldnjs7723/CodingTest#분할-정복을-이용한-거듭제곱)으로 문제를 해결하였다.

## 4. BOJ_7578 공장 [(백준 링크)](https://www.acmicpc.net/problem/7578) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/7000/Main_7578.java)

### 문제 요약

각 Index에 위치한 기계를 A, B에서 서로 다른 위치에 놓은 뒤 전선으로 연결했을 때 전선이 교차하는 부분의 개수를 세는 문제

### 풀이 아이디어

A의 위치에 B에서의 Index 값을 저장한 뒤, 각 Index 뒤에 위치한 수들 중에서 현재 값보다 작은 값의 개수를 세는 문제로 변환할 수 있으며, BOJ_10090 Counting Inversions [(백준 링크)](https://www.acmicpc.net/problem/10090) 문제로 나타낼 수 있다.  
현재 값보다 작은 값의 개수를 셀 때 [Merge Sort](https://github.com/rldnjs7723/CodingTest#merge-sort-병합-정렬)를 수행하면서 왼쪽 배열의 원소 중 오른쪽에 위치한 배열의 원소보다 큰 값의 개수를 카운트해주면 문제를 해결할 수 있다.
