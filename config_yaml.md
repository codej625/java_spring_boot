# 운영 환경별 Config 세팅

<br /><br />

* 운영 환경을 왜 나누는가?
---

```
* 환경 분리

개발, 테스트, 운영(프로덕션) 등의 환경을 분리함으로써 각각의 환경에서 애플리케이션이 정상적으로 동작하는지를 보장하고,
각 환경에 맞춘 설정과 리소스를 제공할 수 있다.
```
```
* 안정성 보장

각각의 환경에서 다른 데이터베이스, 서비스, 리소스를 사용하여
테스트 및 개발 중에는 운영 환경에 영향을 주지 않고 안정적으로 작업할 수 있다.
```
```
* 보안 강화

운영 환경은 보안 측면에서 더욱 엄격한 접근 제어와 권한 설정이 필요하다.
따라서 개발 환경과 운영 환경을 분리함으로써 보안 취약성을 줄이고 운영 환경을 보호할 수 있다.
```
```
* 성능 최적화

운영 환경에서는 더 높은 성능과 안정성을 요구한다.
이를 위해 운영 환경에서는 튜닝된 설정 및 리소스를 제공하여 애플리케이션의 성능을 최적화할 수 있다.
```
```
* 문제 해결 용이성

운영 환경에서 발생하는 문제를 빠르게 해결하기 위해,
개발 환경과는 독립적인 모니터링, 로깅, 디버깅 등의 도구 및 방법론을 사용할 수 있다.
```
```
* 버전 관리 및 배포 관리

다양한 환경에 대한 버전 관리와 효율적인 배포 관리를 할 수 있다.
개발 환경에서는 실험적인 기능이나 개발 중인 기능을 테스트할 수 있고,
운영 환경에서는 안정적이고 검증된 버전을 배포할 수 있다.
```

<br /><br /><br />

* 일반적인 Config 네이밍
---

```
* Common

application.yaml
```
```
* Local

application-local.yaml
```
```
* Dev

application-dev.yaml
```
```
* Production

application-prod.yaml
```

<br /><br /><br />

* 사용 예시
---

```yaml
* application.yaml 예시

# Server
server:
  # Port
  port: 8080

  # Session
  servlet:
    session:
      timeout: 20m

# Logback
logging:
  # logback config
  config: classpath:./static/config/logback-spring.xml
  file:
    path: logs

# Spring
spring:
  application:
    name: {name}

  # View
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
    cache: false
    mode: HTML
    encoding: UTF-8
    servlet:
      content-type: text/html;charset=UTF-8

  # Database
  datasource:
    url: jdbc:postgresql://localhost:5432/{database_name}
    username: {user_name}
    password: {user_pw}
    driver-class-name: org.postgresql.Driver

  # Jpa
  jpa:
    open-in-view: false
  # database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      # 스키마를 삭제하고 다시 생성한다.
      # ddl-auto: create
      # 스키마를 변경하지 않는다.
      # ddl-auto: none
      # 스키마를 변경된 엔티티에 맞게 업데이트한다.
      ddl-auto: update

  # Devtools
  devtools:
    restart:
      enabled: true
    livereload:
      enabled: true
```

<br /><br />

1. Local
```yaml
# 일반적으로 작성한다.

spring:
  profiles:
# 이런 식으로 프로파일을 추가할 필요는 없다.
```

<br /><br />

2. Dev
```yaml
spring:
  profiles:
    active: dev
```

<br /><br />

3. Pord
```yaml
spring:
  profiles:
    active: prod
```

<br /><br />

4. 여러개 파일 Include 방법
```yaml
spring:
  profiles:
    include:
      - profile1
      - profile2
```
<br /><br /><br />

* 실행 방법
---

```
* 별다른 설정 없이 관련 프로파일을 적용시켜 jar파일 실행(권장 하지 않음) 

java -jar my-application.jar --spring.profiles.active=dev
```

<br /><br />

```
* 운영 환경별로 yaml을 나누고 build 시 프로파일을 선택한다.(권장)

./gradlew clean bootjar -Pspring.profiles.active=dev
```
