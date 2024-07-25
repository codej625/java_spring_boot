# Optional에 대해 알아보자.

<br /><br />

* Optional 이란?
---

```
Java 8에서 추가된 클래스로,
값이 있을 수도 있고 없을 수도 있는 상황을 다룰 때 유용하게 사용된다.

Optional을 사용하면 값이 있을 때는 해당 값을,
값이 없을 때는 명시적으로 빈 상태를 나타내는 객체를 반환한다.

이는 NullPointerException을 방지할 수 있다.
```

<br /><br /><br />

* 사용 예시
---

```java
if (entity.getDate() != null) {
  // ... 로직
}
```

<br />

```
위에서 entity.getDate()가 null을 반환할 경우,
NullPointerException이 발생할 수 있다.
이를 방지하기 위해 Java 8 이상에서는 Optional을 사용할 수 있다.
```

<br /><br />

1. Optional.ofNullable()

```java
// 1번 예시

if (Optional.ofNullable(entity.getDate()).isPresent()) {
  // ... 로직
}
```

<br />

```java
// 2번 예시

Optional<String> optionalDate = Optional.ofNullable(entity.getDate());

// 값이 있는 경우
if (optionalDate.isPresent()) {
  String date = optionalDate.get();
  // date을 사용하는 로직
} else {
  // 값이 없는 경우 처리
}
```

<br />

```java
// 3번 예시

Optional<String> optionalDate = Optional.ofNullable(entity.getDate());

// 값이 있을 때 처리
optionalDate.ifPresent(date -> {
  // date을 사용하는 로직
});

// 값이 없을 때 로직을 수행할수도 있다.
Date date = optionalDate.orElseGet(() -> {
  // ... 로직
});

// 값이 없을 때 기본값 설정
String dateOrDefault = optionalDate.orElse("defaultDate");
```

<br />

```
Optional.ofNullable() 메서드는 null이 아니면 해당 객체를 감싸서 Optional 객체를 반환하며,
isPresent() 메서드는 Optional 객체가 값이 존재하는지를 체크한다.
```

<br /><br /><br />

2. Null-safe equals 비교

```java
if (Objects.equals(entity.getDate(), null)) {
  // ... 로직
}
```

<br />

```
Objects.equals() 메서드를 사용하여 null-safe하게 비교할 수 있다.
이 방법은 명시적으로 null을 체크하면서 NullPointerException을 피할 수 있다.
```
