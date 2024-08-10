# NullPointerException

<br /><br />

* null의 발생
---

```
NullPointerException를 주제로 한 이유는
많은 개발자가 Null 값이 발생했을 때,
NullPointerException 발생하는 걸로 알고 있다.

하지만 그건 잘못된 정보이고 실제로는
NullPointerException은 객체가 null인 상태에서 메서드를 호출하거나 필드에 접근하려고 할 때 발생한다.

예시로 확인해보자.
```

<br /><br />

1. 객체의 메서드 호출 시

```java
// 객체가 null일 때 그 객체의 메서드를 호출하려고 하면 발생.

String str = null;
str.length(); // NullPointerException
```

<br /><br />

2. 객체의 필드에 접근 시

```java
// 객체가 null일 때 그 객체의 필드에 접근하려고 하면 발생.

MyClass obj = null;
int value = obj.someField; // NullPointerException
```

<br /><br />

3. 배열의 요소에 접근 시

```java
// 배열의 요소가 null일 때, 그 요소의 메서드나 필드에 접근하려고 하면 발생.

String[] array = new String[1];
array[0].length(); // NullPointerException (array[0]이 null이므로)
```

<br /><br />

4. 자동 언박싱

```java
// Integer 같은 래퍼 클래스에서 null 값을 자동으로 언박싱하려고 할 때 발생. (int는 Primitive type이라 null 값을 허용하지 않음)

Integer num = null;
int value = num; // NullPointerException
```

<br /><br /><br />

* null의 처리 방법
---

```
이러한 상황을 방지하기 위해서는
객체가null인지 확인하는 방어적 프로그래밍 기법을 사용하거나,
Optional 클래스를 활용하여 null을 명시적으로 처리하는 방법이 있다.
```

<br /><br />

1. 방어적 프로그래밍

```java
String input = null;

// null을 체크 
if (input != null) {
  // ... null 아닐 시 처리 로직
}
```

<br /><br /><br />

2. Optional 사용

```java
/**
*  Optional.ofNullable() 메서드는 null이 아니면 해당 객체를 감싸서 Optional 객체를 반환하며,
*  isPresent() 메서드는 Optional 객체가 값이 존재하는지를 체크한다.
*/

boolean input = Optional.ofNullable(entity.getDate()).isPresent();

if (input != null) {
  // ... null 아닐 시 처리 로직
}
```
