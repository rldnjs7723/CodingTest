# 이진 탐색 (이분 탐색) 알고리즘 문제 정리

## 목차

1. [BOJ_2805 나무 자르기](#1-boj_2805-나무-자르기-백준-링크-소스-코드)
2. [BOJ_12015 가장 긴 증가하는 부분 수열2](#2-boj_12015-가장-긴-증가하는-부분-수열2-백준-링크-소스-코드)

## 1. BOJ_2805 나무 자르기 [(백준 링크)](https://www.acmicpc.net/problem/2805) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2805.java)

### 문제 요약

나무들의 높이가 주어졌을 때, 어떤 높이에서 나무를 잘라야 원하는 길이의 나무를 베어갈 수 있는지 계산하는 문제

### 풀이 아이디어

나무의 높이를 정렬한 뒤 각 나무의 높이를 기준으로 절단기를 설정했을 때 가져 갈 수 있는 나무의 길이를 계산하여 (O(N))  
나무의 길이가 짧으면 높이가 낮은 나무를 기준으로 절단기를 설정하고, 나무의 길이가 길면 높이가 높은 나무를 기준으로 절단기를 설정하도록 하여 이진 탐색으로 최적의 높이를 찾도록 수행. (O(log N))  
N <= 1000000 이므로 O(N log N)의 시간복잡도에서 충분히 잘 돌아갈 것이라 판단.  
이후 결과로 반환 받은 Index값에 해당하는 나무의 높이를 기준으로 절단기를 잘랐을 때 길이가 부족하면 높이를 1씩 줄여가며 원하는 길이에 도달하도록 탐색을 수행.

## 2. BOJ_12015 가장 긴 증가하는 부분 수열2 [(백준 링크)](https://www.acmicpc.net/problem/12015) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/12000/Main_12015.java)

### 문제 요약

수열이 주어졌을 때, 그 중 가장 긴 증가하는 부분 수열의 길이를 구하는 문제

### 풀이 아이디어

14003 가장 긴 증가하는 부분 수열5 문제의 하위 호환. 길이만 구하면 되므로 쉬움
