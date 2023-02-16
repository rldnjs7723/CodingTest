# Shell 파일 사용법

1. ./execute.sh <사이트 타입> <문제 번호>
2. <사이트 타입> = BOJ: B, SWEA: S, Programmers: P
3. <문제 번호> = 숫자로 그대로 입력

# 사이트별 Java 8 (OpenJDK) 기준

1. 백준: 클래스 명 Main. 시간 제한 x2 + 1초, 메모리 제한 x2 + 16MB
2. SWEA: 클래스 명 Solution

# 문제풀이 교훈 정리

## 이클립스 사용법

1. Ctrl + F11: 실행
2. Ctrl + Space: 자동완성.
3. sysout 입력 후 Ctrl + Space를 누르면 System.out.println()이 입력 [(2558)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/2000/Main_2558.java)

## 자바 기본

1. 입력의 크기가 작을 때, Scanner를 사용하면 정수, 실수, 문자열 등의 입력을 간단하게 받을 수 있다.  
   정수: [(1000)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1000.java)  
   실수: [(1008)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1008.java)
2. 실수를 출력할 때 C에서와 같이 printf를 사용하여 실수의 자리수를 설정 가능 [(1008)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/1000/Main_1008.java)

   ```
   System.out.printf("%.12f", A / B);
   ```

# 알고리즘

## Two Pointers (투포인터 알고리즘)

1. index 값을 가지는 i, j를 사용하여, 일정 조건에 따라 i와 j를 이동시켜 불필요한 경우의 수를 제거하는 알고리즘. [(16472)](https://github.com/rldnjs7723/CodingTest/blob/main/BOJ/16000/Main_16472.java)
2. 슬라이딩 윈도우와 비슷하지만, 구간의 너비가 가변적이라는 차이점이 존재. [(Two Pointers)](https://butter-shower.tistory.com/226)
