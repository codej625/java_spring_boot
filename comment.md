# Method에 주석을 달아보자.

<br /><br />

* 메서드(함수)에 주석을 달아주는 이유?
---
```
메서드가 무엇을 수행하는지,
그 목적이 무엇인지를 설명하여 코드를 읽는 사람이 빠르게 이해할 수 있게 한다.

코드를 처음 읽는 사람이나 이해하려는 사람에게 중요한 정보를 제공하는 역할을 한다.
```

<br /><br /><br />

* 예시
---
```java
/**
 * {설명} 예시 -> 패스워드 암호화 공통 룰
 * @param password - 암호화 되기 전 평문
 * @param emplyrkey - 유저 로그인 키
 * @param sha2 - 유저 암호화 규칙
 * @return 암호화된 문장(password)
 */

public static String passwordEncryption(String password, String emplyrkey, String sha2) {
  String sha = "X";
  
  if ("E".equals(sha2)) { sha = "M"; }
  if ("Y".equals(sha2)) { sha = "S"; }
  if ("D".equals(sha2)) { sha = "D"; }
  
  String userPw = encode(password);
  String shapw = shacode(password + emplyrkey);
  
  password = "M".equals(sha) || "D".equals(sha) ? userPw : shapw;
  
  return password;
}
```
