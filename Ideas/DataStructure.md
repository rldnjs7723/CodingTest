# 자료 구조 문제 정리

## 목차

1. [BOJ_9935 문자열 폭발](#1-boj_9935-문자열-폭발-백준-링크-소스-코드)
2. [SWEA_13072 병사관리](#2-swea_13072-병사관리-swea-링크-소스-코드)
3. [SWEA_5644 무선 충전](#3-swea_5644-무선-충전-swea-링크-소스-코드)
4. [BOJ_16236 아기 상어](#4-boj_16236-아기-상어-백준-링크-소스-코드)

## 1. BOJ_9935 문자열 폭발 [(백준 링크)](https://www.acmicpc.net/problem/9935) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/9000/Main_9935.java)

### 문제 요약

문자열 A와 폭탄 문자열 B가 주어졌을 때, 문자열 A안에 포함되어 있는 문자열 B를 공백으로 대체하는 문제.  
문자열 A 내부의 문자열 B가 공백으로 대체된 이후에도 문자열 B가 계속 존재할 수 있다.

### 풀이 아이디어

문자열 A를 `Stack`으로 저장한 뒤, 문자열 B를 역순으로 참조하며 pop한 결과와 비교하면서 문자열 B와 동일하면 해당 문자열을 제거한 뒤 문자열 B의 길이만큼 이전에 확인했던 문자열을 stack에 넣어 다시 확인

## 2. SWEA_13072 병사관리 [(SWEA 링크)](https://swexpertacademy.com/main/talk/codeBattle/problemDetail.do?contestProbId=AXxODdXKQAADFASZ&categoryId=AYYZruxqM7YDFARc&categoryType=BATTLE&battleMainPageIndex=1#none) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/13000/Solution_13072.java)

### 문제 요약

답안을 체크하는 Main 함수가 주어졌을 때, 병사를 관리하는 UserSolution 클래스를 완성하는 문제.  
병사 고용, 병사 해고, 병사의 평판 점수 변경, 특정 팀에 속한 병사들의 평판 점수를 일괄 변경, 특정 팀 안에서 가장 평판 점수가 높은 병사를 검색하는 기능을 구현해야 한다.

### 풀이 아이디어

기본적으로 병사를 고용, 해고, 점수 변경의 작업은 배열을 통해 구현하면 O(1) 시간 내에 수행을 마칠 수 있지만, 특정 팀에 속한 병사의 평판 점수를 일괄 변경하는 작업이 기본적으로 O(N)이었기 때문에 시간 초과가 발생하여 해설을 보고 해결하였다.  
`각 병사의 평판 점수 범위가 1 <= score <= 5이므로 각 팀별로 점수가 1 ~ 5인 병사를 각각 배열로 관리하고, Soldier 클래스를 직접 구현 LinkedList의 노드로 활용하여 점수를 일괄적으로 변경할 때 해당 점수 리스트의 Head 노드부터 Tail 노드까지 전부 다른 점수 리스트의 Tail 뒤에 붙여버리는 방식으로 이 연산 또한 O(1)의 시간 복잡도로 구현할 수 있음을 확인했다.`

## 3. SWEA_5644 무선 충전 [(SWEA 링크)](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRDL1aeugDFAUo) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/SWEA/5000/Solution_5644.java)

### 문제 요약

두 사람 A, B가 이동하는 경로가 주어지고, 무선 충전기의 위치와 범위, 성능이 주어졌을 때 두 사람의 충전량 최댓값을 구하는 문제.

### 풀이 아이디어

전반적으로 A, B가 처할 수 있는 모든 경우의 수를 확인하여 각 시간대별 최대 충전량을 계산하면 되는 문제. 충전에 사용할 상위 2개의 충전기를 찾기 위해, 접근 가능한 충전기들을 `TreeSet`을 사용하여 성능을 기준으로 정렬하면 간단하게 해결할 수 있다. (우선 순위 큐로도 구현 가능)  
또한, A와 B가 같은 충전기에 위치했을 때 충전량이 분산된다는 설명이 있는데, 어차피 한 명이 충전량을 다 가져가면 되기 때문에 `의미 없는 조건`

## 4. BOJ_16236 아기 상어 [(백준 링크)](https://www.acmicpc.net/problem/16236) [(소스 코드)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16236.java)

### 문제 요약

생선과 상어의 위치와 크기가 주어진 공간이 있을 때, 해당 공간에서 상어가 더 이상 먹을 수 있는 물고기가 없게 되는 시간을 계산하는 문제. 상어는 자신의 크기와 같은 개수의 물고기를 먹으면 크기가 1 증가한다.

### 풀이 아이디어

기본적으로는 BFS를 통해 상어가 먹을 수 있는 가장 가까운 생선을 찾는 문제. 대신 거리가 가까운 생선 중에서 행의 크기와 열의 크기가 가장 작은 생선을 선택해야 하기 때문에, compareTo 메서드를 override하여 정렬 기준을 설정하고, `PriorityQueue`에 넣으면 자동으로 먼저 먹어야 하는 생선을 구할 수 있다.
