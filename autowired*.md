# Autowired와 Bean에 대해 알아보자.

<br /><br />

1. Autowired
---

```
Autowired 어노테이션은 의존성 주입을 위해 사용된다.

스프링은 @Autowired 어노테이션이 붙은 필드나 생성자, 메서드에 해당 타입의 빈을 주입한다.
예를 들어, 컨트롤러에서 서비스를 주입하거나 서비스에서 리포지토리를 주입할 때 사용된다.
```

<br /><br /><br />

2. Bean
---

```
Bean 어노테이션은 메서드가 빈을 생성하고 컨테이너에 추가하는 데 사용된다.

@Configuration 클래스 내에서 해당 메서드가 @Bean 어노테이션을 가지고 있으면,
그 메서드의 반환 값은 빈으로 등록된다.

이를 통해 스프링 컨테이너는 해당 빈을 관리하고 필요한 곳에서 주입할 수 있다.
```

```
여기서 "Bean"이라 함은 단순히 자바 객체를 가리키는 용어보다는, 
스프링 컨테이너에 의해 관리되고
의존성 주입(Dependency Injection) 등의 스프링 기능을 적용할 수 있는 객체를 말한다.
```

<br /><br />

```
1) 빈 정의(Bean Definition)

빈은 XML, JavaConfig 또는 어노테이션을 사용하여 스프링 컨테이너에 정의한다.
이 단계에서는 빈의 클래스와 해당 빈이 의존하는 다른 빈들에 대한 정보가 명시된다.
이는 스프링이 어떻게 빈을 생성하고 구성할지를 결정하는데 사용한다.
```

<br />

```
2) 빈 생성(Bean Instantiation)

스프링 컨테이너는 빈 정의를 기반으로 해당 빈을 생성하고 초기화한다.
이 과정에서 빈의 의존성이 주입되고, 필요한 초기화 작업이 수행된다.
```

<br />

```
3) 빈 사용(Bean Usage)

빈이 생성되면, 스프링 애플리케이션 내에서 필요한 곳에서 해당 빈을 가져와 사용할 수 있다.
이를 통해 스프링의 핵심 기능인 의존성 주입(Dependency Injection)과 관리를 활용할 수 있다.
```

<br />

```
이러한 빈은 주로 애플리케이션의 구성 요소나 서비스를 나타내며, 
스프링의 IoC (Inversion of Control) 컨테이너에 의해 관리된다.

이는 개발자가 객체의 생성과 관리에 대한 제어를 스프링 프레임워크에 위임함으로써 애플리케이션의 유연성과 확장성을 높인다.
```

<br /><br /><br />

3. Tip
---

<br />

```
@Component와 @Bean의 차이?
```

```
@Component 어노테이션은 클래스 레벨에서 사용되며, 
해당 "클래스"를 스프링 빈으로 등록하려는 것을 나타낸다.
주로 POJO(Plain Old Java Object) 클래스를 빈으로 등록할 때 사용된다.

@Component 어노테이션이 붙은 클래스는 스프링 컴포넌트 스캔(Component Scan)의 대상이 된다.
이를 통해 스프링은 해당 클래스를 찾아서 빈으로 등록한다.
```

<br />

```java
ex)
@Component
public class EncryptionUtil {

  // SHA-256 암호화
  public String sha256(String plaintext) {
    return DigestUtils.sha256Hex(plaintext);
  }
}
```

<br />

```
@Bean 어노테이션은 메서드 레벨에서 사용되며,
해당 메서드가 반환하는 객체를 스프링 빈으로 등록하려는 것을 나타낸다.
주로 복잡한 객체 생성 로직이나 외부 라이브러리를 사용하는 경우에 해당 메서드를 사용하여 빈을 생성하고 등록한다.

@Configuration 어노테이션이 붙은 클래스 내부에서 @Bean 어노테이션을 사용하여 메서드를 정의한다.
```

<br />

```java
ex)
@Configuration
public class AppConfig {
    
  @Bean
  public MyBean myBean() {
    return new MyBean();
  }
}
```

<br />

```
따라서, @Component는 스프링에게 해당 클래스를 스캔하여 자동으로 빈으로 등록하라고 알리는 데 사용되고,
@Bean은 개발자가 직접 메서드를 통해 빈을 등록하는 데 사용된다.
```

<br />

```
마지막으로 @Bean으로 등록한 메서드 혹은 @Component로 등록한 Class에 속한 메서드를 사용할 때
Lombok을 활용할 수 있다.

@RequiredArgsConstructor 어노테이션을 사용하려는 Class에 붙이면,
final 키워드(상수)가 붙은 필드 값에 자동으로 생성자를 만들어 준다.

(@Autowired를 사용하지 않고 의존성 주입)
```
