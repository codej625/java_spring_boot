# 로그에 대해 알아보자.

<br /><br />

* lombok을 이용한 log를 사용.
```
* 참고

Lombok의 Slf4j는 Log4j와 같은 로깅 라이브러리를 이용하기 위한 Wrapper(혹은 어댑터)와도 같은 것이다.
어느 구현체를 사용하느냐에 따라 다른데, Log4j를 사용하고 있는 곳이라면 보안에 문제가 있으므로 참고하길 바란다.
default는 Logback으로 되어있다.
```

<br /><br /><br />

1. 로그의 종류

<br />

```
* 정보 로그 (Informational Logs)
애플리케이션의 정상적인 운영 상태나 중요한 이벤트를 기록.

log.info();
```

<br />

```
* 디버그 로그 (Debug Logs)
개발 과정에서 디버깅 목적으로 상세한 정보를 기록.

log.debug();
```

<br />

```
* 경고 로그 (Warning Logs)
잠재적인 문제나 비정상적인 상황을 알린다.
현재 애플리케이션의 운영에는 영향을 미치지 않지만, 주의를 기울여야 할 때 사용.

log.warn();
```

<br />

```
* 오류 로그 (Error Logs)
애플리케이션 실행 중에 발생한 오류를 기록.
이 오류는 복구 가능한 오류일 수도 있고, 애플리케이션의 일부 기능이 실패했음을 나타낸다.

log.error();
```

<br />

```
* 치명적 로그 (Fatal Logs)
시스템이나 애플리케이션이 더 이상 운영을 계속할 수 없는 치명적인 오류를 기록.

log.fatal();
```

<br /><br /><br />

2. Logback 설정

<br />

```
Logback 설정 파일은 일반적으로 Java 프로젝트의 리소스 디렉토리에 위치하며,
파일 이름은 logback.xml로 지정된다.

/src/main/resources/logback.xml

위 위치에 logback.xml 파일을 만들고 내용을 설정 파일 내용으로 채워넣으면 된다.
이렇게 하면 Java 애플리케이션을 실행할 때 Logback이 이 설정 파일을 자동으로 로딩하여 사용한다.
```
```gradle
* 의존성 추가

dependencies {
    implementation 'org.slf4j:slf4j-api:1.7.32'
    implementation 'ch.qos.logback:logback-classic:1.2.10'
}
```

<br />

```xml
* XML
파일 이름 -> logback.xml

<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```
