# Arrays에 대해 알아보자!

<br />

```
자바에서도 자바스크립트처럼 array 객체가 있다.
객체 속에는 훌륭한 내장 객체들이 많다.

예를 들어, SQL에서 리스트를 불러와서
특정 요청을 차단한다고 해보자.
```

<br />

```java
/* 예시 */
/* Request param */
String mobile = "01012345678";

/* 데이터베이스에서 조회한 리스트 array */
String[] phone = {
  01022695901,
  01022695902,
  01022695903
};

if (Arrays.asList(phone).contains(mobile)) return RESULT_STATUS_FALSE; /* false */
```
