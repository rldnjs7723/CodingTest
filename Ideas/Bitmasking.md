# 비트마스킹 문제 정리

## 목차

1. [SWEA_3234 준환이의 양팔 저울](#1-swea_3234-준환이의-양팔-저울-swea-링크-소스-코드)
2. [SWEA_1288 새로운 불면증 치료법](#2-swea_1288-새로운-불면증-치료법-swea-링크-소스-코드)

## 1. SWEA_3234 준환이의 양팔 저울 [(SWEA 링크)](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWAe7XSKfUUDFAUw) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/3000/Solution_3234.java)

### 문제 요약

N개의 무게 추를 양팔 저울의 왼쪽, 오른쪽에 놓는 경우의 수를 찾는 문제.  
이 때 양팔 저울의 오른쪽에 올라간 무게의 총합이 왼쪽에 올라간 무게의 총합보다 커서는 안 됨

### 풀이 아이디어

무게 추를 올린 모든 경우의 수를 DFS로 탐색하여 경우의 수를 체크.  
N <= 9밖에 안 되지만 도합 2^N _ N!개의 경우의 수이므로 중간에 왼쪽에 올라간 무게의 총합이 전체 추 무게의 절반 이상인 경우 나머지는 2^N _ N!을 직접 수식으로 계산하여 탐색하는 수를 줄임.  
그래도 여전히 시간 초과가 발생하였기에 다른 사람의 코드를 참고하여 `추를 사용했는지 체크하는 과정을 비트마스킹으로 바꾸고, Stack을 사용하던 코드를 재귀로 바꾸어 시간을 단축시킴.`

## 2. SWEA_1288 새로운 불면증 치료법 [(SWEA 링크)](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV18_yw6I9MCFAZN) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/1000/Solution_1288.java)

### 문제 요약

N이 주어졌을 때 N의 각 자릿수를 보고 0 ~ 9까지 전부 볼 수 있을 때까지 N을 더했을 때의 N 값을 계산하는 문제

### 풀이 아이디어

0 ~ 9까지 전부 봤을 때의 비트마스크인 0b1111111111 값과 현재 비트마스크에 xor 연산을 수행하여 값이 동일할 때까지 Brute Force 방식으로 N을 더하면서 비교
