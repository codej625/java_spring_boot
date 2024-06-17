# 로그 라이브러리 logback에 대
<br /><br />

* logback 기본 개념
---

1. Logger
```
로깅을 수행하는 주요 객체.
로거는 이름을 가지며 주로 패키지 이름 또는 클래스 이름과 일치하게 설정한다.
로거는 계층 구조를 가지는데 ROOT 로거는 모든 로거의 상위 로거이다.
(로그 레벨 수준을 결정)
```

<br />

2. Appender
```
로깅 이벤트를 특정 출력 대상으로 전송하는 구성 요소.
쉽게 말해 로그 메세지가 출력할 대상을 결정한다고 할 수 있다.
콘솔에 출력할지, 파일에 출력할지 등..

각 로거는 하나 이상의 Appender에 연결될 수 있다.
```

<br />

3. Layout
```
로깅 이벤트가 어떻게 포맷되어 출력될지 결정하는 구성 요소.
사용자가 지정한 형식으로 로그 메세지를 변환한다는 것을 의미한다.
예를 들어 자바에서 문자열 포맷팅과 비슷하다고 할 수 있다.
```

<br /><br /><br />

* 로그 롤링 설정
---

1. application.yaml
```yaml
# Port
server:
  port: 8081 # Temporary port number

...

# Logback
logging:
  # logback config 지정
  config: classpath:./static/config/logback-spring.xml
  file:
    # 로그가 저장 되는 path 지정
    path: logs

...
```

<br />

2. logback-spring.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <!-- application.yaml에 기재한 path를 변수화 -->
    <property name="LOGS_PATH" value="logs" />

    <!-- 폴더 이름에 사용할 timestamp 변수 -->
    <timestamp key="year" datePattern="yyyy"/>
    <timestamp key="month" datePattern="MM"/>
    <timestamp key="day" datePattern="yyyyMMdd"/>

    <!-- 콘솔에 로그를 출력하는 설정 -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %highlight(%-5level) %cyan(%logger{100}) - %msg%n</pattern>
            <!-- 패턴 레이아웃 헤더 출력 여부 -->
            <outputPatternAsHeader>true</outputPatternAsHeader>
        </encoder>
    </appender>

    <!-- 로그 파일에 로그를 출력하는 설정 -->
    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 로그 저장 파일 경로 (실제 저장 경로) -->
        <file>${LOGS_PATH}/${year}/${month}/${day}.log</file>

        <!-- 로그 파일 이름 설정 패턴 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">

            <!-- 로그 파일의 이름 형식 지정 (-{file_name}.%i.log 생략 불가능) -->
            <fileNamePattern>${LOGS_PATH}/${year}/${month}/${day}-%d{yyyy-MM-dd}.%i.log</fileNamePattern>

            <!-- 하루 단위로 로그 파일 생성 -->
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <!-- 100MB 넘으면 or 하루 지나면 다음 파일 생성 -->
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!-- 로그 보관 기간 지정 (200일 후에 삭제) -->
            <maxHistory>200</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 루트 로거 설정 -->
    <root level="info">
        <!-- 콘솔과 파일 전부 출력 -->
        <appender-ref ref="console"/>
        <appender-ref ref="file"/>
    </root>
</configuration>
```

<br /><br />

```
상황에 맞게 조금씩 수정해서 사용하기 위한 기본 구조이다.
위처럼 사용할 시 년도/월/년도월일자.log 형식으로 기록되고 롤링 된다.
```
