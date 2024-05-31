# Spring AOP에 대해 알아보자.

<br /><br />

* AOP
---

```
AOP란 Aspect Oriented Programming의 약자로 관점 지향 프로그래밍이라고 한다.

여기서 Aspect(관점)이란 흩어진 관심사들을 하나로 모듈화한 것을 의미한다.

OOP에서는 주요 관심사에 따라 클래스를 나눈다.
이 클래스들은 보통 SRP(Single Responsibility Principle)에 따라 하나의 책임만을 갖게 설계된다.
하지만 클래스를 설계하다 보면 로깅, 보안, 트랜잭션 등 여러 클래스에서 공통으로 사용하는 부가 기능들이 생긴다.
이들은 주요 비즈니스 로직은 아니지만,
반복적으로 여러 곳에서 쓰이는 데 이를 흩어진 관심사(Cross Cutting Concerns)라고 한다.

AOP 없이 흩어진 관심사를 처리하면 다음과 같은 문제가 발생한다.
여러 곳에서 반복적인 코드를 작성해야 하고,
코드가 변경될 경우 여러 곳에 가서 수정이 필요하다.
주요 비즈니스 로직과 부가 기능이 한 곳에 섞여 가독성이 떨어진다.

따라서 흩어진 관심사를 별도의 클래스로 모듈화하여 위의 문제들을 해결하고,
결과적으로 OOP를 더욱 잘 지킬 수 있도록 도움을 주는 것이 AOP이다.
```

<br /><br /><br />

* AOP의 주요 개념
---

```
Aspect -> Advice + PointCut로 AOP의 기본 모듈
Advice -> Target에 제공할 부가 기능을 담고 있는 모듈
Target -> Advice이 부가 기능을 제공할 대상 (Advice가 적용될 비즈니스 로직)
JointPoint -> Advice가 적용될 위치(메서드 진입 지점, 생성자 호출 시점, 필드에서 값을 꺼내올 때 등 다양한 시점에 적용 가능)
PointCut -> Target을 지정하는 정규 표현식
```

<br /><br /><br />

* Spring AOP
---

```
Spring AOP는 기본적으로 프록시 방식으로 동작한다.
프록시 패턴이란 어떤 객체를 사용하고자 할 때,
객체를 직접적으로 참조 하는 것이 아니라 해당 객체를 대행(대리, proxy)하는 객체를 통해 대상객체에 접근하는 방식을 말한다.

아래는 Spring AOP와 AspectJ의 주요 차이를 정리한 표이다.
```

<br />

| 특성                          | Spring AOP                            | 순수 자바 AOP (예: AspectJ)               |
|-------------------------------|--------------------------------------|----------------------------------------|
| 의존성                        | Spring Framework에 포함             | 별도의 AOP 도구 필요 (예: AspectJ)        |
| AOP 구현 방식                | 프록시 기반                           | 직접 바이트 코드 조작                      |
| AOP 적용 범위               | 메서드 레벨                           | 메서드, 필드, 생성자, 클래스 레벨 등 다양한 레벨 |
| 컴파일 타임 vs 런타임   | 런타임                                  | 컴파일 타임                                  |

<br /><br /><br />

* 예시
---

1. LoggingAspect class
```java
@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Before("execution(* com.example.service.*.*(..))")
  public void beforeServiceMethodExecution(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getTarget().getClass().toString();
    log.info("Executing method {} of class {}", methodName, className);
  }
}
```

<br />

2. @EnableAspectJAutoProxy 어노테이션
```java
@SpringBootApplication
@EnableAspectJAutoProxy /* @EnableAspectJAutoProxy 어노테이션을 추가하여 AspectJ AOP를 활성화 */
public class MyApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyApplication.class, args);
  }
}
```

<br />

3. Service class
```java
@Service
public class MyService {

  public void doSomething() {
    System.out.println("... logic");
  }
}
```

<br />

4. Controller
```java
@RestController
public class MyController {

  private final MyService myService;

  @Autowired
  public MyController(MyService myService) {
    this.myService = myService;
  }

  @GetMapping("/dosomething")
  public void doSomething() {
    myService.doSomething();
  }
}
```
```
위의 예시에서 MyController의 /dosomething 엔드포인트를 호출하면,
MyService의 doSomething() 메서드가 실행될 때마다 Aspect가 실행되어 로깅을 수행한다.
```
