# 간단한 중복 제거를 해보자!

<br/>

```java
package kr.co.mplanit;

public class Constants {
  /* ex) IP black list */
  public static final String[] IP = {
    "125.133.53.131", 
    "114.199.232.125",
    "117.111.23.195", 
    "118.235.32.181", 
    "125.244.92.41", 
    "175.199.126.78", 
    "180.64.169.10", 
    "180.92.74.123",
    "203.232.48.174", 
    "210.204.217.235",
    "49.165.168.210",
    "210.105.142.197",
    "59.17.191.175"
  };
}
```

<br/>

```java
/* Black IP */
String black = getClientIpAddr(request); /* 아이피를 가져온다. */
if (Arrays.asList(Constants.IP).contains(black)) { /* IP 변수의 값을 DB에서 조회할 수 있게 하는 걸 권장한다. */
  ...
}
```
