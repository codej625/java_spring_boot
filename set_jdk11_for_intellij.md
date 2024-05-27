# Intellij를 사용해서 Spring Boot Project의 jdk level을 11으로 바꿔보자.

<br /><br />

* Spring Boot 버전 및 JDK 버전 변경.
```
최신 버전의 Spring Boot에서는 최소 JDK 사용 버전이 17이기 때문에
17보다 아래 버전을 사용하고 싶으면 별도의 작업을 해줘야 한다.

* MacOS를 기준으로 설명
```

<br /><br /><br />

1. Project SDK 변경
```
File -> Project Structure 으로 이동한다.
좌측 메뉴에 Project를 누르면,
Project SDK를 변경할 수 있다.
원하는 SDK로 변경한다.

SDK 바로 밑에
Language level 또한 체크한다.
```

<br />

2. Module level 변경
```
좌측 메뉴에 Project 밑에 Modules을 선택한다.
Language level 을 원하는 level로 변경한다.
```

<br />

3. SDKs 변경
```
좌측 메뉴에 SDKs를 선택한다.
원하는 JDK를 선택한다.
```

<br />

4. Gradle JVM 변경
```
Intellij IDEA를 선택 후,
Settings...를 선택한다.

Build, Execution, Deployment -> Build Tools -> Gradle 으로 이동한다.

Build and run using을 IntelliJ IDEA 으로 변경한다.
그리고 밑에 Run tests using 또한 IntelliJ IDEA 으로 변경한다.

마지막으로 Gradle JVM에서 원하는 JDK 버전을 선택한다.
```

<br />

5. Java Compiler 변경
```
Build Tools 밑에 Compiler -> Java Compiler를 선택한다.
Project bytecode version을 변경한다.

(필요에 따라 Per-modules bytecode version 밑에 프로젝트별로 module을 추가해 놓는다.
```

<br />

6. Spring Boot 버전 변경
```
build.gradle 파일을 열고,

...

plugins {
  id 'java'
  id 'org.springframework.boot' version '2.7.10' <- JDK 버전이 11이라면 스프링 부트 버전을 2.7.10 정도로 낮춰야 한다.
  id 'io.spring.dependency-management' version '1.1.4'
}
```
