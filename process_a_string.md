# 문자열을 가공해보자!

<br/>

1. 문자열 사이에 특수문자를 넣어 연결시키기
```java
String source = "source";
String medium = "medium";
String campaign = "campaign";
String term = "term";

String[] params = { source, medium, campaign, term }; /* array */
String path = String.join("|", params);
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
