# 문자열 문제 정리

## 목차

1. [BOJ_2042 구간 합 구하기](#1-boj_2042-구간-합-구하기-백준-링크-소스-코드)
2. [BOJ_14428 수열과 쿼리 16](#2-boj_14428-수열과-쿼리-16-백준-링크-소스-코드)

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
