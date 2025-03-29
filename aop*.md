# AOP(Aspect-Oriented Programming)

<br />
<br />

* AOP
---

```
AOP는 관점 지향 프로그래밍이라고 불리며,
애플리케이션의 핵심적인 비즈니스 로직에서 공통적으로 발생하는 관심사(Cross-cutting Concerns)를 분리하여,
관리하는 프로그래밍 패러다임이다.
```

<br />
<br />
<br />
<br />

1. AOP의 주요 개념

<br />

| 개념 | 설명 | 레스토랑 비유 | 스프링 부트 용어 |
|---|---|---|---|
| **AOP (관점 지향 프로그래밍)** | 핵심 로직에서 공통 관심사를 분리하여 관리하는 프로그래밍 패러다임 | 공통 업무 (로그, 보안 등)를 담당하는 별도의 직원 | `@Aspect` 어노테이션을 사용하여 정의 |
| **핵심 관심사 (Core Concern)** | 애플리케이션의 주요 비즈니스 로직 | 손님 주문 받기, 음식 만들기, 서빙하기 | 서비스(Service) 계층, 컨트롤러(Controller) 계층의 주요 로직 |
| **공통 관심사 (Cross-cutting Concern)** | 여러 핵심 관심사에 걸쳐 반복적으로 발생하는 기능 (로그, 보안, 트랜잭션 관리 등) | 로그 기록, 보안 확인, 주문-결제 묶음 처리 | 로깅, 인증/인가, 트랜잭션 관리 |
| **Aspect (관점)** | 공통 관심사를 구현한 모듈 | 로그 기록 담당 직원, 보안 담당 직원 | `@Aspect` 어노테이션이 붙은 클래스 |
| **Advice (조언/행동)** | Aspect가 실제로 수행하는 작업 (언제, 무엇을 할지 정의) | 로그 기록 직원이 기록하는 행위, 보안 담당Around`, `@AfterReturning`, `@AfterThrowing` 등의 어노테이션이 붙은 메서드 |
| **Join Point (조인 포인트)** | Advice가 적용될 수 있는 핵심 로직의 특정 지점 (메서드 실행, 예외 발생 등) | 손님이 주문하는 시점, 음식을 만들기 시작하는 시점, 서빙이 완료되는 시점 | 메서드 실행 시점, 메서드 종료 시점, 예외 발생 시점 등 |
| **Pointcut (포인트컷)** | Advice를 적용할 Join Point를 구체적으로 정의하는 규칙 (표현식 사용) | "모든 주문 관련 기능 실행 전", "특정 서빙 기능 실행 후" | `@Pointcut("execution(* com.example.service..*.*(..))")` 와 같은 표현식 |
| **Weaving (위빙)** | Aspect를 핵심 로직 코드에 결합하는 과정 (AOP 컨테이너가 담당) | 실제로 직원이 해당 시점에 로그를 기록하거나 보안 검사를 수행하는 과정 | 컴파일 시 위빙, 클래스 로딩 시 위빙, 런타임 시 위빙 (스프링은 런타임 시 프록시 방식 주로 사용) |

<br />
<br />
<br />

2. Spring AOP

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

<br />
<br />
<br />

3. 예시

<br />

`LoggingAspect class`

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

`@EnableAspectJAutoProxy 어노테이션`

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

`Service class`

```java
@Service
public class MyService {

  public void doSomething() {
    System.out.println("... logic");
  }
}
```

<br />

`Controller`

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
<br />

```
위의 예시에서 MyController의 /dosomething 엔드포인트를 호출하면,
MyService의 doSomething() 메서드가 실행될 때마다 Aspect가 실행되어 로깅을 수행한다.
```
