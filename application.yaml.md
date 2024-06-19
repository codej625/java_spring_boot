# application 기본 세팅을 해보자.

<br /><br />

* application.yaml
```yaml
# Port
server:
  port: 8080

# Logback
logging:
  # logback config 지정
  config: classpath:./static/config/logback-spring.xml
  file:
    # 로그가 저장 되는 path 지정
    path: logs

# Spring
spring:
  application:
    name: web

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
    username: {admin_id}
    password: {admin_pw}
    driver-class-name: org.postgresql.Driver

  # Jpa
  jpa:
    open-in-view: false
    # database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      # 스키마를 삭제하고 다시 생성한다.
      # ddl-auto: create
      # 스키마를 변경하지 않는다.
      ddl-auto: none
      # 스키마를 변경된 엔티티에 맞게 업데이트한다.
      # ddl-auto: update

  # Devtools
  devtools:
    restart:
      enabled: true
    livereload:
      enabled: true
```
