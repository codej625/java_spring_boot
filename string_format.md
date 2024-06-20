# String format에 대해 알아보자.

<br /><br />

* 포맷 지시자 설명
```
%d -> 10진 정수

%f -> 부동 소수점 숫자

%s -> 문자열

%c -> 문자

%b -> 불리언

%x, %o, %e 등 -> 16진수, 8진수, 지수 형식 숫자
```

<br /><br /><br />

* 예시
```java
String name = "codej625";
int age = 34;

String message = String.format("이름은 %s이고, 나이는 %d살입니다.", name, age);
System.out.println(message);
```
