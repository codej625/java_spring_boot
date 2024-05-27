# 함수형 인터페이스(Functional Interface)와 람다식(Lambda Expressions)

<br /><br />

* 함수형 인터페이스는 함수형 프로그래밍의 함수 객체를 정의하고, 람다식은 이러한 함수 객체를 생성하는데 사용.
---

1. 함수형 인터페이스
```
1) 하나의 추상 메서드 -> 오직 하나의 추상 메서드만을 가져야 한다. 함수형 인터페이스는 여러 개의 메서드를 가질 수 없다.

2) 기본 메서드(default method)와 정적 메서드(static method)는 제한이 없음.

3) @FunctionalInterface 어노테이션 -> 함수형 인터페이스를 명시적으로 선언할 때,
컴파일러가 해당 인터페이스가 함수형 인터페이스임을 확인하도록 하기 위해 사용.
하지만 이 어노테이션을 사용하지 않아도 추상 메서드가 하나인 인터페이스는 함수형 인터페이스로 간주한다.
```

<br />

2. 람다식
```
람다식은 함수형 인터페이스(Functional Interface)의 인스턴스를 생성하는 방법 중 하나이다.
조금 더 정확히 하자면,
람다식은 익명 함수의 표현이며, 함수형 인터페이스의 단일 추상 메서드의 구현을 단순화한 것이다.
```

<br />

3. 예시
```java
/* @FunctionalInterface -> 함수형 인터페이스 정의하는 어노테이션. */
@FunctionalInterface
interface MathOperation {
  int f(int a, int b);
}

public class Main {
  public static void main(String[] args) {
    /* 함수형 인터페이스를 구현하는 람다식을 사용하여 덧셈과 곱셈 기능 구현 */
    MathOperation addition = (a, b) -> a + b;
    MathOperation multiplication = (a, b) -> a * b;

    /* 덧셈과 곱셈 기능 사용 */
    int result1 = operate(5, 3, addition);
    int result2 = operate(5, 3, multiplication);

    /* 결과 출력 */
    System.out.println("Addition: " + result1);
    System.out.println("Multiplication: " + result2);
  }

  /* 함수형 인터페이스를 사용하는 메서드 */
  private static int operate(int a, int b, MathOperation mathOperation) {
    return mathOperation.f(a, b);
  }
}
```

<br />

```
* operate 메서드가 static으로 선언되어 있는 이유.

operate 메서드는 Main 클래스의 메서드이지만,
객체 인스턴스에 속하는 것이 아니라 클래스 수준의 독립적인 기능을 제공하는 메서드이다.
따라서 객체 인스턴스를 생성하지 않고도 이 메서드를 호출할 수 있어야 하기 때문에 static으로 선언되어 있다.
```

<br /><br /><br />

* java.util.function 패키지(자바 8부터 제공하는 함수형 프로그래밍을 위한 다양한 내장 인터페이스)
---

| 인터페이스         | 설명                                      | 예시                                             |
|-------------------|-------------------------------------------|--------------------------------------------------|
| `Consumer<T>`     | 결과를 반환하지 않는 동작을 수행          | `(Integer x) -> System.out.println(x)`          |
| `Supplier<T>`     | 결과값을 제공                              | `() -> "Hello, World!"`                         |
| `Function<T, R>`  | 입력값을 받아 결과값을 반환                | `(Integer x) -> x * 2`                          |
| `Predicate<T>`    | 조건을 검사하여 `true` 또는 `false` 반환 | `(Integer x) -> x > 0`                           |
| `BiConsumer<T, U>`| 두 개의 인수를 받아 결과를 반환하지 않음 | `(Integer x, Integer y) -> System.out.println(x + y)` |
| `BiFunction<T, U, R>` | 두 개의 인수를 받아 결과값을 반환     | `(Integer x, Integer y) -> x + y`               |

<br />

```
* Tip

제네릭(Generic) 타입으로 사용되는 타입
T는 Type의 약어로 파라미터 뜻함.
U는 흔히 두 번째 파라미터를 뜻함.
R은 반환 타입을 뜻한다.
```
