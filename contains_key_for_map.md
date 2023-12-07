# map에서 for문 없이 key의 value를 찾아보자!

<br />

```java
Map<String, Object> result = new HashMap<>(); /* 객체라고 가정 */

result.containsKey(${key})) { /* 특정 키를 찾음 */
  String value = (String) result.get(${key});
}
```
