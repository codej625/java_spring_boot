# 시간 관련 객체를 알아보자.

<br /><br />

* LocalDate
```java
날짜를 표현한다. (시간은 포함하지 않음)
예를 들어, 우리는 이 클래스를 사용하여 오늘 날짜를 얻을 수 있다.

LocalDate today = LocalDate.now();
System.out.println("오늘의 날짜: " + today);
```

<br /><br />

* LocalTime
```java
시간을 표현한다. (날짜는 포함하지 않음).
예를 들어, 우리는 이 클래스를 사용하여 현재 시간을 얻을 수 있다.

LocalTime time = LocalTime.now();
System.out.println("현재 시간: " + time);
```

<br /><br />

* LocalDateTime
```java
날짜와 시간을 모두 표현한다.
LocalDate와 LocalTime의 기능을 결합한(?)버전.

LocalDateTime now = LocalDateTime.now();
System.out.println("현재 날짜와 시간: " + now);
```

<br /><br />

* LocalTime
```java
특정 시점을 나노초 단위로 표현.
주로 타임스탬프 생성에 사용된다.

Instant timestamp = Instant.now();
System.out.println("현재 타임스탬프: " + timestamp);
```
