# AOP(Aspect-Oriented Programming)

<br /><br />

* AOP
---

```
AOP란 Aspect Oriented Programming의 약자로 관점 지향 프로그래밍이라고 한다.
(여기서 Aspect(관점)이란 흩어진 관심사들을 하나로 모듈화한 것을 의미한다.)

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

| 개념      | 설명                                                                                      |
|-----------|-------------------------------------------------------------------------------------------|
| **Aspect (측면)** | 프로그램의 관심사 또는 기능을 모듈화한 것입니다. 예: 로깅, 보안, 트랜잭션 관리 등.   |
| **Join Point (조인 포인트)** | 코드 실행 중 Aspect가 적용될 수 있는 특정 지점입니다. 예: 메소드 호출, 객체 생성, 필드 접근 등. |
| **Advice (어드바이스)** | 조인 포인트에서 실행될 코드입니다. 적용 방식: 메소드 실행 전(before), 메소드 실행 후(after), 메소드 실행 중(execute) 등. |
| **Pointcut (포인트컷)** | 어느 조인 포인트에서 Advice를 적용할지를 결정하는 표현식입니다. 예: 특정 메소드, 클래스, 패턴 등. |
| **Weaving (위빙)** | Aspect를 기존의 코드에 적용하는 과정입니다. 위빙 시점: 컴파일 시점, 로드 시점, 런타임 시점 등. |

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

| 항목              | Spring AOP                                             | AspectJ                                                  |
|-------------------|--------------------------------------------------------|----------------------------------------------------------|
| **지원 범위**     | Spring 프레임워크와 통합된 AOP 구현                    | 독립적인 AOP 프레임워크, Spring 외부에서도 사용 가능     |
| **위빙 방식**     | 런타임 위빙 (Runtime Weaving)                           | 컴파일 타임 위빙 (Compile-Time Weaving), 로드 타임 위빙 (Load-Time Weaving), 런타임 위빙 |
| **구성 방법**     | Spring 컨테이너 설정 파일이나 주석을 사용하여 구성    | AspectJ 문법을 사용하여 직접 구성                       |
| **언어 지원**     | 자바 바이트코드 기반, 기본적으로 자바에 적용            | 자바, 클로저, XML 등을 지원, 더 강력한 문법 제공         |
| **성능**          | 런타임 위빙으로 인해 성능이 상대적으로 낮을 수 있음  | 컴파일 타임 위빙을 통해 성능이 더 우수할 수 있음       |
| **설정 복잡성**   | 비교적 간단하며 Spring의 설정과 통합되어 있어 사용하기 쉬움 | AspectJ의 설정은 더 복잡할 수 있으며, 별도의 도구 필요  |
| **기능 지원**     | 기본적인 AOP 기능 제공, 트랜잭션, 보안 등과 통합      | 더 강력한 AOP 기능 제공, 복잡한 포인트컷 표현식과 다채로운 어드바이스 타입 지원 |
| **서드파티 라이브러리** | Spring AOP는 Spring의 종속성으로 사용, 별도의 라이브러리 없음 | AspectJ는 독립적인 라이브러리로, Spring 외부에서도 사용할 수 있음 |


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

<br /><br />

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

<br /><br />

3. Service class
```java
@Service
public class MyService {

  public void doSomething() {
    System.out.println("... logic");
  }
}
```

<br /><br />

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

<br />

```
위의 예시에서 MyController의 /dosomething 엔드포인트를 호출하면,
MyService의 doSomething() 메서드가 실행될 때마다 Aspect가 실행되어 로깅을 수행한다.
```
