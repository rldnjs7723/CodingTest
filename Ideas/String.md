# 문자열 문제 정리

## 목차

1. [BOJ_9252 LCS 2](#1-boj_9252-lcs-2-백준-링크-소스-코드)
2. [BOJ_1786 찾기](#2-boj_1786-찾기-백준-링크-소스-코드)

## 1. BOJ_9252 LCS 2 [(백준 링크)](https://www.acmicpc.net/problem/9252) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9252.java)

### 문제 요약

유명한 LCS 문제. 두 문자열이 주어졌을 때 LCS 길이와 LCS를 출력

### 풀이 아이디어

[LCS 알고리즘](https://github.com/rldnjs7723/CodingTest/#longest-common-subsequence-lcs-9252)이 기억나지 않아 다시 공부하고 풀었던 문제.

## 2. BOJ_1786 찾기 [(백준 링크)](https://www.acmicpc.net/problem/1786) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1786.java)

### 문제 요약

문자열 2개가 주어졌을 때, 패턴 문자열이 존재하는 개수와 Index를 출력하는 문제.

### 풀이 아이디어

가장 기본적인 [KMP 알고리즘](https://github.com/rldnjs7723/CodingTest#knuth-morris-pratt-kmp-pattern-matching) 적용 문제.  
구현이 어렵기 때문에 그냥 외우자.

## 3. BOJ_11585 속타는 저녁 메뉴 [(백준 링크)](https://www.acmicpc.net/problem/11585) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/11000/Main_11585.java)

### 문제 요약

문자열이 주어지고, 해당 문자열을 한 칸씩 Queue처럼 맨 앞에 위치한 문자를 맨 뒤로 보냈을 때 패턴 문자열이 등장하는 개수를 세는 문제

### 풀이 아이디어

주어진 문자열을 그대로 뒤에 이어 붙인 뒤에 [KMP 알고리즘](https://github.com/rldnjs7723/CodingTest#knuth-morris-pratt-kmp-pattern-matching)으로 탐색하면 해결할 수 있다.  
KMP 알고리즘을 내 방식대로 편하게 구현해보기 위해 [(아호-코라식 알고리즘)](https://github.com/rldnjs7723/CodingTest#aho-corasick-아호-코라식-알고리즘)과 비슷한 방법으로 각 문자를 트라이 노드에 저장한 뒤 Fail 함수를 구성하여 해결해보았다.
