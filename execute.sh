#!/bin/bash

if [ -z $1 ]; then
    echo "사이트 종류를 입력해주세요. (BOJ: B, SWEA: S)"
else
    if [ $1 == "B" ]; then
        echo "선택된 사이트: 백준"
        directory="./BOJ"
    elif [ $1 == "S" ]; then
        echo "선택된 사이트: SWEA"
        directory="./SWEA"
    else 
        echo "B, S, P 중 하나를 입력해주세요."
    fi
fi

if [ -z $1 ] && [ -z $2 ]; then
    echo "문제 번호를 입력해주세요."
else
    problem=$(($2 / 1000 * 1000))
    directory+=/$problem

    if [ $1 == "B" ]; then
        file="Main_"
        file+=$2
        executeFile=$file
        file+=.java
    elif [ $1 == "S" ]; then
        file="Solution_"
        file+=$2
        executeFile=$file
        file+=.java
    else 
        echo "B, S 중 하나를 입력해주세요."
    fi

    cd $directory

    javac -J-Xms1024m -J-Xmx1920m -J-Xss512m -encoding UTF-8 $file
    java -Xms1024m -Xmx1920m -Xss512m -Dfile.encoding=UTF-8 $executeFile
fi
