# 롬복을 설치해보자!

<br />

```
공식 사이트에서 lombok을 다운 받는다.
다운 받은 경로로 이동하고,
terminal을 열어 java -jar {lombok.jar}을 실행시킨다.
이클립스 or STS에 ini파일을 찾아 install 한다.
```

<br />

```
롬복 어노테이션을 못 찾으면 밑에 의존성을 추가 해준다.
```
```gradle
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
  implementation 'org.springframework.boot:spring-boot-starter-data-redis'
  implementation 'org.springframework.boot:spring-boot-starter-mail'
  implementation 'org.springframework.boot:spring-boot-starter-security'
  implementation 'org.springframework.boot:spring-boot-starter-web'
  
  
  /* 롬복 의존성 추가 */
  implementation 'org.projectlombok:lombok'

  ...
}
```
