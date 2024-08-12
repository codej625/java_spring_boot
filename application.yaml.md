# application 기본 세팅을 해보자.

<br /><br />

* application.yaml
```yaml
# Port
server:
  port: 8080
# Session
  servlet:
    session:
      # 세션 유지 시간
      timeout: 20m 

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

  # Profile
  profiles:
    default: local

  # 개별 프로파일 분리 시 사용
  # profiles:
    # config:
      # activate:
        # on-profile: local

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
    url: {database_url}
    username: {admin_id}
    password: {admin_pw}
    driver-class-name: {driver}

  # Jpa
  jpa:
    open-in-view: false
    hibernate:
      # 스키마를 삭제하고 다시 생성한다.
      # ddl-auto: create
      # 스키마를 변경하지 않는다.
      ddl-auto: none
      # 스키마를 변경된 엔티티에 맞게 업데이트한다.
      # ddl-auto: update
    # 쿼리 표시
    show-sql: true
    properties:
      hibernate:
        format_sql: true

  # Devtools
  devtools:
    restart:
      enabled: true
    livereload:
      enabled: true

# 마이바티스 설정 
# mybatis:
  # type-aliases-package: com.enicom.portal.domain.login.dao.model
  # config-location: classpath:mybatis/mybatis-config.xml
  # mapper-locations: classpath:mappers/*.xml
```

```xml
<!-- 마이바티스 사용 시 mybatis-config.xml -->
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "HTTP://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <!-- Model alias -->
        <typeAlias alias="login" type="com.enicom.portal.domain.login.dao.model.Employee"/>
        <typeAlias alias="as" type="com.enicom.portal.domain.as.dao.model.As"/>
        <typeAlias alias="library" type="com.enicom.portal.domain.library.dao.model.Library"/>
    </typeAliases>
</configuration>
```

```
* 프로파일 분리 jar 실행 시

java -jar {jar_name.jar} --spring.profiles.active={profile}
```
