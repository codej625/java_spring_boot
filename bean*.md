# Bean

<br /><br />

* 빈(Bean)
---

```
스프링 프레임워크(Spring Framework)에서 빈(Bean)은
애플리케이션 컨텍스트(Application Context)에서 관리되는 객체를 의미한다.

스프링은 빈을 통해 애플리케이션의 컴포넌트 간의 의존성을 관리하고, 객체의 생명 주기를 제어한다.
```

<br /><br /><br />

1. 빈 정의(Bean Definition)

```
스프링 컨테이너에 의해 관리되는 객체를 정의하는 방법.

빈 정의는 XML 설정, 자바 설정 클래스(@Configuration 어노테이션을 사용하는 클래스),
또는 컴포넌트 스캔(@Component, @Service, @Repository 등)을 통해 이루어질 수 있다.
```

<br /><br />

2. 의존성 주입(Dependency Injection)

```
빈은 스프링 컨테이너가 자동으로 관리하고 주입해주는 의존성(다른 빈이나 값)을 가진다.
의존성 주입에는 생성자 주입, 세터 주입, 필드 주입 등이 있다.
```

<br /><br />

3. 빈 생명 주기(Bean Lifecycle)

```
빈은 스프링 컨테이너에 의해 생성되고 소멸된다.

빈의 생명 주기는 초기화 및 소멸 메서드를 통해 제어할 수 있다.

예를 들어, @PostConstruct와 @PreDestroy 어노테이션을 사용하거나,
InitializingBean과 DisposableBean 인터페이스를 구현할 수 있다.
(거의 사용하지 않음)
```

<br /><br />

4. 스코프(Bean Scope)
```
빈의 생명 주기와 범위를 정의.

스프링은 여러 가지 스코프를 지원하며,
가장 일반적인 것은 singleton(기본값), prototype, request, session, application 이다.
```

<br /><br />

5. 자동 와이어링(Autowiring)

```
스프링 컨테이너가 빈의 의존성을 자동으로 주입하는 방법이다.

@Autowired 어노테이션을 사용하여 의존성을 주입할 수 있으며,
@Qualifier를 사용하여 특정 빈을 지정할 수도 있다.
```

<br /><br /><br />

* Bean 설정 예시
---

<br />

1. @Component 어노테이션 사용하기

```
Bean으로 관리하고 싶은 클래스를 
@Component 어노테이션으로 표시할 수 있다.
```

<br />

```java
import org.springframework.stereotype.Component;

@Component
public class MyService {
    public void serve() {
        System.out.println("Service is serving...");
    }
}
```

<br /><br />

2. @Configuration과 @Bean 어노테이션 사용하기

```
@Bean 어노테이션을 사용하여,
메서드 단위로 Bean을 정의할 수 있다.
```

<br />

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```
