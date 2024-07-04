# 기본 적인 환경설정

<br /><br />

* 참고
```yaml
# Server
server:
  # Port
  port: {port}

  # Session
  servlet:
    session:
      timeout: 10m

# Spring
spring:
  # Profile
  profiles:
    config:
      activate:
        on-profile: local

  # Profile(Default)
  #   profiles:
  #     default: local

  application:
    name: {project_name}

  # Devtools
  devtools:
    restart:
      enabled: true
    livereload:
      enabled: true

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
    url: {url}
    username: {user_name}
    password: {pw}

  # Jpa
  jpa:
    open-in-view: false
    hibernate:
      # 스키마를 삭제하고 다시 생성한다.
      # ddl-auto: create
      # 스키마를 변경하지 않는다.
      # ddl-auto: none
      # 스키마를 변경된 엔티티에 맞게 업데이트한다.
      ddl-auto: update

    show-sql: true
    properties:
      hibernate:
        format_sql=true:

  # Logback
  logging:
  # logback config 지정
  config: classpath:./static/config/logback-spring.xml
  file:
    # 로그가 저장 되는 path 지정
    path: logs
```
