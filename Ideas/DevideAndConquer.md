# 분할 정복 알고리즘 문제 정리

## 목차

1. [BOJ_1799 비숍](#1-boj_1799-비숍-백준-링크-소스-코드)

## 1. BOJ_1799 비숍 [(백준 링크)](https://www.acmicpc.net/problem/1799) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1799.java)

### 문제 요약

체스판 위의 색칠되지 않은 부분에 비숍을 놓을 때, 최대 몇 개까지 서로의 범위에 겹치지 않게 놓을 수 있는지 계산하는 문제

### 풀이 아이디어

기본적인 아이디어는 일반적인 사방 탐색 + DFS + 백트래킹 문제였으나, 시간 복잡도를 줄이기 위한 핵심적인 아이디어는 분할 정복  
`체스판의 흰색 / 검은색 부분을 나눠서 계산한 뒤` 기존의 시간 복잡도 O(2^(N \* N))에서 O(2^(N / 2 \* N / 2))로 N 자체를 절반으로 감소시킬 수 있다.  
체스판을 두 부분으로 나눈 뒤에는 북서, 북동, 남서, 남동을 탐색하여 비숍을 놓았을 때 체스판에 놓을 수 없는 부분을 표시하는 방식으로 체크하였고, 더 이상 비숍을 놓을 수 없을 때 놓았던 비숍을 없애도록 백트래킹 방식으로 구현