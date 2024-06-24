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
먼저, yaml 파일 이름을 정할 때는 나름의 규칙을 지켜야 한다.
스프링에서는 application-{profile}.yaml을 통해 이들을 구분하고 활용할 수 있다.
{profile}에 원하는 이름을 작성하면 된다.
```

<br /><br />

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

* 운영 환경 분리하기
---

```yaml
# application.yaml

# Spring
spring:
  # Profile
  profiles:
    default: local

  application:
    name: web

  # Devtools
  devtools:
    restart:
      enabled: true
    livereload:
      enabled: true
```

```yaml
# application-local.yaml

# Server
server:
  # Port
  port: 8080
```

```yaml
# application-dev.yaml

# Server
server:
  # Port
  port: 8081
```

```yaml
# application-prod.yaml

# Server
server:
  # Port
  port: 8082
```

<br /><br />

```
Spring Boot는 응용 프로그램이 시작될 때,
다음 위치에서 application.properties 및 application.yaml 파일을 자동으로 찾아 로드한다.

classpath:/
classpath:/config/
file:./
file:./config/
```

<br /><br /><br />

* Build 및 실행
---

```
// 빌드

./gradlew clean build 
```

<br />

```
// jar 실행

java -jar {jarname}.jar --spring.profiles.active={Profile}

// java -jar {jarname}.jar 이렇게만 실행 시키면 Defalut profile을 따른다.
```
