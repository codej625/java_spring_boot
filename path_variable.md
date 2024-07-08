# @PathVariable

<br /><br />

* 설명
---

```
Spring 프레임워크에서 사용되는 어노테이션으로,
URL 경로에서 변수 값을 추출하여 메서드 파라미터로 전달할 때 사용된다.
주로 RESTful 웹 서비스에서 클라이언트가 전달하는 경로 변수를 처리하는 데 유용하게 쓰인다.
```

<br /><br /><br />

* 사용 예시
---

```
* 기본 사용법

예제에서 /users/{userId}는 URL 경로 패턴을 나타내며,
{userId} 부분은 실제로 사용자 ID 값으로 치환될 변수이다.

@PathVariable Long userId에서 userId는 메서드의 파라미터 이름으로,
{userId}에서 추출된 값이 여기에 매핑된다.
```

```java
@GetMapping("/users/{userId}")
public ResponseEntity<User> getUserById(@PathVariable Long userId) {}
```

<br /><br />

```
* 필수 제외 

@PathVariable은 기본적으로 필수적인 값이라고 가정한다.
즉, URL 경로에 {userId}와 같은 변수가 반드시 존재해야 하는데
경우에 따라 선택적으로 값을 받을 수 있다.
```

```java
@GetMapping("/users/{userId}")
public ResponseEntity<User> getUserById(@PathVariable(required = false) Long userId) {}
```

```
여기서 required = false로 설정하면,
userId가 URL 경로에 없는 경우 null로 처리된다.
(기본값은 true)
```

<br /><br />

```
* 변수 매칭

@PathVariable 매칭
PathVariable과 다른 변수명을 사용하려면 예시 -> @PathVariable("id") 이런식으로 url의 Path variable 이름을 맞춰주고,

메서드에서 사용할 변수명을 다르게 설정할 수 있다.
```

```java
@GetMapping ("/order/{id}")
public ResponseEntity<User> getUserById(@PathVariable("id") String user){
  log.info("id : {}", user);
  // ...   
}
```

```
예시 -> @GetMapping ("/order/{id}") id의 값이 동적으로 계속 변하는 상황이라면,
Method 선언 부분에 (@PathVariable("id") <- 이 부분을 반드시 명시해준다.)
```
