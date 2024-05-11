# String을 합칠때 효율적인 리소스 관리를 위해 StringBuilder를 사용하자.

<br /><br />

* 예시 1
```java
StringBuilder builder = new StringBuilder(); /* StringBuilder 객체 생성 */
/* Add */
builder.append("1");
builder.append("2");
```

<br />

* 예시 2
```java
StringBuilder builder = new StringBuilder();

for (Map.Entry<String, String> kv : params.entrySet()) {
    builder.append(kv.getKey());
    builder.append('=');
    builder.append(kv.getValue());
    builder.append('&');
}
```

<br />

* 예시 3
```java
StringBuilder builder = new StringBuilder();
builder.append("mkey=" + {variable});
/* String 변환 */
builder.toString();
```
