# mac openjdk_set

<br/>

### brew 최신버전 업데이트
```
brew update
```

### brew cask 최신버전 설치
```
brew install cask
```

### adoptopenjdk/openjdk 추가하기
```
brew tap adoptopenjdk/openjdk
```

### 설치 가능한 모든 JDK 찾기
```
brew search jdk
```

### 원하는 버전 설치(ex 11)
```
brew install --cask adoptopenjdk11
```

### 자바가 설치된 곳 확인하기
```
/usr/libexec/java_home -V
```

### 자바 버전 확인하기 (기본적으로 최신버전으로 세팅된다.)
```
java --version
```

### 자바 버전 바꾸기 (zsh쉘인 경우 ~/.zshrc 파일을 수정해주명된다.)
### shell check
```
echo $SHELL
```

### shell update
```
vi ~/.zshrc
```

### insert mode ("i"를 입력하면 insert mode로 변경된다.)
```
# Java Paths
# export JAVA_HOME_11=$(/usr/libexec/java_home -v11)
export JAVA_HOME_14=$(/usr/libexec/java_home -v14)

# Java 11
export JAVA_HOME=$JAVA_HOME_11

# Java 14
# 14버전을 사용하고자 하는 경우 아래 주석(#)을 해제하고 위에 11버전을 주석처리 하면된다.
# export JAVA_HOME=$JAVA_HOME_14
```

### esc를 누르고 insert mode종료 후 :wq 입력

### 변경사항 저장
```
source ~/.zshrc
```

### 세팅되어 있는 자바 버전 확인
```
java --version
```

### JDK path 확인
```
echo $JAVA_HOME
```
