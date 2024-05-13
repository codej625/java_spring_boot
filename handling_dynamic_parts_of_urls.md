# 스프링 부트에서는 URL의 동적인 부분을 어떻게 처리할까?

<br /><br />

* @PathVariable
```
@PathVariable은 RESTful 웹 애플리케이션에서 URL 경로의 일부를 추출하여 메서드 파라미터로 전달하는 데 사용된다.
이 어노테이션을 사용하면 클라이언트가 요청하는 URL의 일부를 동적으로 처리할 수 있다.
```

<br /><br /><br />

* 예시
```
사용자가 웹 애플리케이션에서 특정 ID를 가진 리소스를 요청할 때, URL 경로에 해당 ID가 포함될 수 있다.
이때 @PathVariable을 사용하여 해당 ID를 추출할 수 있다.
```
```java
@RestController
public class MyController {

  @GetMapping("/books/{id}")
  public ResponseEntity<String> getBookById(@PathVariable Long id) {
    /* ... id를 사용하여 특정 책을 검색하고 해당 정보를 반환하는 로직이라 가정 */
    return ResponseEntity.ok("Requested book ID: " + id);
  }
}
```
```
위의 코드에서 @GetMapping("/books/{id}")는 "/books/{id}" 경로에 대한 GET 요청을 처리한다.
여기서 {id} 부분은 동적으로 변할 수 있는 부분을 나타낸다.
이 부분의 값은 메서드의 @PathVariable 어노테이션이 붙은 파라미터로 전달된다.

따라서 /books/123과 같은 요청이 들어오면 getBookById 메서드의 id 파라미터에는
123이 전달되어 해당 책의 정보를 검색하고 반환할 수 있다.
```
