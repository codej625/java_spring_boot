# @SpringBootApplication

<br /><br />

* @SpringBootApplication 역할?
---

```
@SpringBootApplication 어노테이션이 붙은 클래스는 다음과 같은 기능을 수행한다.

@Configuration
이 클래스가 설정 클래스로 동작할 수 있도록 한다.


@EnableAutoConfiguration
Spring Boot의 자동 구성 기능을 활성화한다.


@ComponentScan
기본적으로 MyApplication 클래스가 위치한 패키지와 하위 패키지를 스캔하여 빈을 검색한다.
```

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

<br />

```
어노테이션을 개별적으로 사용할 필요 없이 모든 설정을 한 번에 처리할 수 있어,
설정을 간단하고 명확하게 만들어 줍니다.

따라서, Spring Boot 애플리케이션에서는 보통 @SpringBootApplication을 사용하는 것이 표준적인 방식이다.
```
