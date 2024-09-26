# Lamda & Stream

<br /><br />

* 람다 표현식
---

```
람다 표현 식은 익명 함수(이름이 없는 함수)를 사용하여,
메소드를 간단하게 표현하는 방법이다.

특정 함수형 인터페이스의 메소드를 구현하는 방식으로 함수형 인터페이스를 정의하고,
그 인터페이스의 메소드를 구현하는 방식으로 람다 표현 식을 사용할 수 있다.

대부분 java.util.function 패키지의 기본 제공하는 함수형 인터페이스가 속해 있다.
(Runnable, Function, BiFunction 함수형 인터페이스들을 주로 사용)
```

<br /><br />

1. 예시1

```java
String a = "a";
String b = "b";

// 람다 표현식으로 StringConcatenator 구현
StringConcatenator concatenator = (String x, String y) -> x + y;

String result = concatenator.concatenate(a, b);
System.out.println(result); // ab
```

<br /><br />

2. 예시2

```
* 사용자 정의 함수형 인터페이스 만들기 (오직 하나의 추상 메소드만 가지는 인터페이스)

@FunctionalInterface 어노테이션을 사용한다.
(해당 인터페이스가 함수형 인터페이스임을 나타낸다.)
```

<br />

```java
// 예시 코드

@FunctionalInterface
interface StringConcatenator {
    String concatenate(String a, String b);
}

// ... 생략

public class Main {
    public static void main(String[] args) {
        // StringConcatenator 인터페이스를 구현하는 람다 표현식
        StringConcatenator concatenator = (a, b) -> a + b;

        // 메소드 호출
        String result = concatenator.concatenate("Hello, ", "world!");
        System.out.println(result); // Hello, world!
    }
}
```

<br /><br /><br />

* Java에서 기본적으로 제공되는 함수형 인터페이스
---

| 인터페이스            | 패키지                 | 설명                                       | 메소드                            |
|-----------------------|------------------------|--------------------------------------------|-----------------------------------|
| `Runnable`            | `java.lang`            | 인자와 반환값이 없는 작업을 정의              | `void run()`                     |
| `Function<T, R>`      | `java.util.function`   | 하나의 인자를 받아 결과를 반환                | `R apply(T t)`                   |
| `BiFunction<T, U, R>` | `java.util.function`   | 두 개의 인자를 받아 결과를 반환               | `R apply(T t, U u)`              |
| `Consumer<T>`         | `java.util.function`   | 하나의 인자를 받아 처리만 하는 함수           | `void accept(T t)`               |
| `BiConsumer<T, U>`    | `java.util.function`   | 두 개의 인자를 받아 처리만 하는 함수          | `void accept(T t, U u)`          |
| `Supplier<T>`         | `java.util.function`   | 인자 없이 결과를 반환하는 함수                | `T get()`                        |
| `Predicate<T>`        | `java.util.function`   | 하나의 인자를 받아 boolean 값을 반환          | `boolean test(T t)`              |
| `UnaryOperator<T>`    | `java.util.function`   | 하나의 인자를 받아 같은 타입을 반환            | `T apply(T t)`                  |
| `BinaryOperator<T>`   | `java.util.function`   | 두 개의 같은 타입 인자를 받아 같은 타입을 반환 | `T apply(T t1, T t2)`            |
