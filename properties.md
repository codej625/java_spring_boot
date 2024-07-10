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
  # Profile(Option)
  # profiles:
  #   config:
  #     activate:
  #       on-profile: local

  # Profile(Default)
  profiles:
    default: local

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

<br /><br />

* Tip

```
properties에 환경변수 값을 사용하는 방법
yaml 예시
```
```yaml
myapp:
  myvariable: my_value_from_yaml
```
```
위의 예에서 myapp.myvariable은 환경 변수 이름이고,
my_value_from_yaml은 해당 변수의 값이다.

YAML 파일은 들여쓰기를 사용하여 계층적인 구조를 나타내므로,
설정이 복잡해질 때 유용하게 사용된다.
````

<br />

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyClass {

  @Value("${myapp.myvariable}")
  private String myVariable;

  public void someMethod() {
    // 환경 변수 사용 예시
    System.out.println("My variable value is: " + myVariable);
  }
}
```
```
위의 예에서 @Value("${myapp.myvariable}")는 application.yml 파일에서
myapp.myvariable 환경 변수의 값을 가져와서 myVariable 필드에 주입한다.

이후에는 myVariable을 클래스 내에서 사용할 수 있다.

@Value 어노테이션을 사용하기 위해서는 클래스가 스프링의 빈으로 등록되어야 한다.

@Component, @Service, @RestController 등의 어노테이션을 사용하여,
스프링에게 이 클래스가 빈으로 등록되어야 한다.
```
