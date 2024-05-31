# Optional에 대해 알아보자.

<br /><br />

* Optional은 값의 존재 여부를 나타내는 Java 제네릭 클래스이다.
---

```
NullPointerException을 방지하고 코드를 더 안전하고 명확하게 만들 수 있다.
그러나 과용될 경우 코드를 복잡하게 만들 수 있으므로 주의가 필요하다.
주로 메서드 시그니처에 Optional을 사용하여 메서드가 값을 반환할 수도 있고 없을 수도 있음을 나타낸다.


* Optional의 장점

1) NullPointerException ->
Optional을 사용하면 값이 없을 때 발생할 수 있는 NullPointerException을 방지할 수 있다.

2) 명시적인 값의 존재 여부 전달 ->
Optional을 사용하면 메서드의 반환 값이 항상 존재하는지 여부를 명시적으로 전달할 수 있다.
메서드 시그니처에 Optional을 사용하면 해당 메서드가 값이 없을 수도 있다는 사실을 명확히 알 수 있으므로,
클라이언트 코드에서 적절한 처리를 할 수 있다.

3) 함수형 프로그래밍 스타일 지원 ->
Optional은 함수형 프로그래밍 스타일을 지원하므로,
간결하고 함수적인 코드를 작성할 수 있다.
map(), flatMap(), filter() 등의 메서드를 사용하여 코드를 작성할 수 있으며,
이는 가독성을 향상시키고 코드의 의도를 명확하게 전달할 수 있다.
```

<br /><br /><br />

* 예시
---

1. Optional으로 Null 값을 체크하고 if-else 문법처럼 사용하는 예시.
```java
@RestController
public class MyController {

  @GetMapping("/api/resource/{id}")
  public String getResourceById(@PathVariable Optional<Long> id) {

    return id.map(resourceId -> {
      /* 값이 존재하는 경우, 해당 리소스를 가져오는 로직 수행 */
      return "Resource with ID " + resourceId + " found.";
    }).orElse("No resource ID provided.");
  }
}
```
```
map() ->

만약 id Optional에 값이 존재한다면,
해당 값에 대해 주어진 함수(람다 표현식)를 적용하여 처리.
즉, 값이 있는 경우에만 주어진 함수를 실행하여 리소스를 가져오는 로직을 수행한다.
만약 id Optional에 값이 없다면,
map() 메서드는 아무런 작업도 수행하지 않고 빈 Optional을 반환한다.
```
```
orElse() ->

map() 메서드가 실행된 후의 결과(Optional)를 처리.
만약 map() 메서드에서 리턴된 Optional에 값이 있다면 -> 해당 값이 그대로 반환.
만약 map() 메서드에서 리턴된 Optional에 값이 없다면 ->
대체값으로 지정된 문자열("No resource ID provided.")이 반환된다.
```

<br />

2. Optional의 isPresent() 메서들 사용한 예시
```java
@RestController
public class MyController {

  @GetMapping("/api/resource/{id}")
  public String getResourceById(@PathVariable Optional<Long> id) {
    if (id.isPresent()) {
      /* id가 주어진 경우, 해당 리소스를 가져오는 로직 수행 */
      Long resourceId = id.get();
      return "Resource with ID " + resourceId + " found.";
    } else {
      /* id가 주어지지 않은 경우, 에러 또는 기본 응답 처리 */
      return "No resource ID.";
    }
  }
}
```
```
isPresent()을 사용하면,
Optional 객체가 값을 가지고 있는지 여부를 반환한다.
이 메서드는 값이 존재하는지를 확인하여 그 결과에 따라 true 또는 false를 반환한다.
```
