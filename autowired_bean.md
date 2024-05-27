# Autowired와 Bean에 대해 알아보자.

<br /><br />

* Autowired
```
Autowired 어노테이션은 의존성 주입을 위해 사용된다.
스프링은 @Autowired 어노테이션이 붙은 필드나 생성자, 메서드에 해당 타입의 빈을 주입한다.
예를 들어, 컨트롤러에서 서비스를 주입하거나 서비스에서 리포지토리를 주입할 때 사용된다.
```

<br />

* Bean
```
Bean 어노테이션은 메서드가 빈을 생성하고 컨테이너에 추가하는 데 사용된다.
@Configuration 클래스 내에서 해당 메서드가 @Bean 어노테이션을 가지고 있으면,
그 메서드의 반환 값은 빈으로 등록된다.
이를 통해 스프링 컨테이너는 해당 빈을 관리하고 필요한 곳에서 주입할 수 있다.
```
```
여기서 "Bean"이라 함은 단순히 자바 객체를 가리키는 용어보다는, 
스프링 컨테이너에 의해 관리되고, 
의존성 주입(Dependency Injection) 등의 스프링 기능을 적용할 수 있는 객체를 말한다.

1) Bean 정의 -> XML, JavaConfig 또는 어노테이션을 사용하여 스프링 컨테이너에 빈을 정의한다.
이 정의는 해당 빈의 클래스와 다른 빈에 대한 의존성을 명시한다.

3) Bean 생성 -> 스프링 컨테이너는 이러한 빈 정의를 기반으로 해당 빈을 생성하고 초기화한다.

4) Bean 사용 -> 스프링 애플리케이션 내에서 이러한 빈을 필요에 따라 가져와 사용할 수 있다.
이를 통해 의존성 주입 및 관리 등의 스프링의 기능을 활용할 수 있다.

이러한 빈은 주로 애플리케이션의 구성 요소나 서비스를 나타내며, 
스프링의 IoC (Inversion of Control) 컨테이너에 의해 관리된다.
이는 개발자가 객체의 생성과 관리에 대한 제어를 스프링 프레임워크에 위임함으로써 애플리케이션의 유연성과 확장성을 높인다.
```

<br /><br />

```
* Tip

@Autowired는 이미 스프링이 관리하는 빈을 주입하는 데 사용되고,
@Bean은 새로운 빈을 정의하고 등록하는 데 사용된다.
```