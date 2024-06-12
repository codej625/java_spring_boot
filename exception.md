# 예외 처리를 알아보자.

<br /><br />

* 예외 처리의 중요성
---

```
예외 처리는 소프트웨어 시스템의
안정성, 유지보수성, 사용자 경험, 코드 구조 및 시스템의 신뢰성을 크게 향상시킨다.
개발자는 예외 처리를 적절하게 설계하고 구현하여 발생할 수 있는 다양한 오류 상황에 대비해야 한다.
```

<br /><br /><br />

* try-catch의 구조 설명
---

1. try-catch 블록
```
메서드 내부에서 발생할 수 있는 특정 예외를 처리하기 위해 사용.
catch 블록이 해당 예외를 처리하면,
예외는 메서드 외부로 전파되지 않는다.
```
```java
try { // 예외가 발생할 수 있는 코드를 작성한다.(스코프를 가진다.)

} catch(Exception e) { // catch()에는 예외 종류와 처리 방법을 작성한다. 

} finally {} // 옵션이지만 예외처리와 상관없이 무조건 실행돼야 하는 부분이 있다면 작성한다.
```

<br />

2. throws
```
해당 메서드가 처리하지 않고
메서드 호출자에게 전달하려는 예외를 선언한다.

메서드 내의 try-catch 블록이 예외를 처리하지 않거나 일부만 처리하는 경우,
처리되지 않은 예외는 throws 절에 명시된 예외 목록에 따라 호출자에게 전달된다.
```
```java
public void myMethod() throws IOException, SQLException {
  try {
    // 코드가 IOException을 발생시킬 수 있음
    someIOOperation();
  } catch (IOException e) {
    // IOException을 처리합니다.
    System.out.println("IOException 발생: " + e.getMessage());
  }

  // 다른 코드가 SQLException을 발생시킬 수 있음
  someDatabaseOperation();
}
```
```
위에 경우,
someIOOperation()에서 IOException이 발생하면,
catch 블록에서 이 예외를 처리한다.
따라서 IOException은 메서드 외부로 전파되지 않는다.

someDatabaseOperation()에서 SQLException이 발생하면,
이를 처리하는 catch 블록이 없으므로 예외는 메서드 외부로 전파된다.
이때, 메서드 선언에 throws SQLException이 포함되어 있어 호출자는 이 예외를 처리해야 한다.
```

<br /><br /><br />

* 정리
---

```
try-catch 블록과 throws 절은 서로 보완적인 역할을 한다.
try-catch 블록은 메서드 내부에서 특정 예외를 처리하는 반면,
throws 절은 메서드 외부로 전달할 예외를 선언한다.
```
