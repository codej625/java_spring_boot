# 여러개의 접속 경로가 하나의 엔드포인트로 매핑되게 해보자.(알면서도 새로운 매핑 메서드를 만드는 행위를 하지 말자)

<br />

1. 여러개의 경로를 하나의 엔드포인트에 매핑
```java
@RestController
public class MyController {

  @RequestMapping(value = {"/url1", "/url2", "/url3"})
  public ResponseEntity<String> handleRequest() {
    /* 처리할 내용 */
    return ResponseEntity.ok("Success");
  }
}
```

<br />

2. 같은 경로지만 http 메서드를 다르게 받기
```java
@RestController
public class MyController {

  @GetMapping("/url1")
  @PostMapping("/url1")
  public ResponseEntity<String> handleRequest() {
    /* 처리할 내용 */
    return ResponseEntity.ok("Success");
  }
}
```
```java
@RequestMapping(value = "/url1", method = {RequestMethod.GET, RequestMethod.POST})
public ResponseEntity<String> handleRequest() {
  /* 처리할 내용 */
  return ResponseEntity.ok("Success");
}
```
