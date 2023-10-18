# 문자열을 가공해보자!

<br/>

1. 문자열 사이에 특수문자를 넣어 연결시키기
```java
String source = "source";
String medium = "medium";
String campaign = "campaign";
String term = "term";

1) 
String[] params = { source, medium, campaign, term }; /* array */
String path = String.join("|", params);

2)
String path = String.join("|", source, medium, campaign, term);
```

<br/>

2. 하이픈이 없는 전화번호를 정규식을 이용해 하이픈을 붙여보자!
```java
String originPhone = "01012345678";
String mobile = originPhone.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
```

<br/>

3. m/f 값의 성별을 1/2로 변경해보자!
```java
String gender = "m";
String sex = gender.equals("m") ? "1" : "2";
```

<br/>

4. 문자열을 분할 해보자
```java
String text = "Hello World";
String[] parts = text.split(" "); /* 공백을 기준으로 분할하고 배열값으로 반환 된다. */

for (String part : parts) {
  System.out.println(part); /* Hello, World  */
}
```

<br/>

5. 문자열을 추출 해보자
```java
String text = "Hello, World!";

1)
String part = text.substring(7, 12); /* 인덱스 7부터 12 직전까지 추출 */
System.out.println(part); /* World */

2) 두 번째 매개변수를 지정하지 않으면 시작 인덱스부터 문자열의 끝까지 추출한다.
String part = text.substring(7); /* 인덱스 7부터 끝까지 추출 */
System.out.println(part); /* World! */
```
