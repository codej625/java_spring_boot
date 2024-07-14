# Controller에서 Parameter를 핸들링 하는 방법.

<br /><br />

```
Controller에서는 요청하는 Http의 Method에 따라,
혹은 요청하는 방법에 따라 여러 가지 방법으로 핸들링할 수 있다.

예시를 보며 알아보자.
```

<br /><br /><br />

1. Handling query parameters.(query string)

```
쿼리 파라미터를 받을 때는 @RequestParam을 사용한다.
예를 들어, http://localhost:8080/api/users?id=1&name=John와 같은 요청에서id와 name을 받으려면,
다음과 같이 한다.
(물론 DTO를 사용하여 필드와 Setter를 사용할 수도 있다.)
```

<br />

```java
@GetMapping("/api/users")
public String getUser(@RequestParam Long id, @RequestParam String name) {
  // ... 메서드 내용
}
```

<br /><br /><br />

2. Handling path variables.
3. 
```
URI의 일부를 변수로 사용할 때는 @PathVariable을 사용한다.
예를 들어, http://localhost:8080/api/users/1에서 1은 사용자 ID일 수 있다.
```
```
https://github.com/codej625/java_spring_boot/blob/main/path_variable.md 에 상세히 정리 해놓음
```

<br />

```java
@GetMapping("/api/users/{id}")
public String getUserById(@PathVariable Long id) {
  // ... 메서드 내용
}
```

<br /><br /><br />

3. Handling request body.

```
HTTP 요청의 본문(body)에 JSON 형태로 데이터가 있을 때,
@RequestBody를 사용하여 객체로 변환할 수 있다.
```

<br />

```java
@PostMapping("/api/users")
public String createUser(@RequestBody User user) {
  // ... 메서드 내용
}
```
```
여기서 User는 요청 본문의 JSON 데이터를 자바 객체로 매핑할 클래스이다.
혹은 Map<String, Object>, List<Map<String, Object>> 와 같이 맵이나 리스트로 매핑이 가능하다. 
```

<br /><br /><br />

4. Handling HTTP request.

```
필요에 따라 HTTP 요청 자체에 대한 정보를 받을 수도 있다.
이 경우에는 HttpServletRequest를 매개변수로 사용한다.
```

<br />

```java
@GetMapping("/api/users")
public String getUsers(HttpServletRequest request) {
  // ... 메서드 내용
}
```

<br /><br /><br />

5. Handling form data.

```
@ModelAttribute는 HTML 폼을 통해 전송된 데이터를 자바 객체로 변환할 때 사용한다.

예를 들어, 회원 가입 폼에서 입력된 데이터를 받아서
User 객체로 변환하는 경우
```

<br />

```java
@PostMapping("/register")
public String registerUser(@ModelAttribute User user) {
  // user 객체로 받은 폼 데이터 처리
  userService.registerUser(user);

  // ... 메서드 내용
}
```
```
이 경우 User 클래스는 폼 데이터의 각 필드와 일치하는 필드들을 가지고 있어야 한다.

스프링은 폼 데이터의 이름과 User 클래스의 필드 이름을 매칭하여
자동으로 객체를 생성하고 값을 할당한다.
```

<br />

```java
* Tip

@GetMapping("/register")
public String showRegistrationForm(Model model) {
  model.addAttribute("user", new User());
  
  // ... 메서드 내용
}
```
```
위와 같이 Model 객체에 담아 뷰(View)에서 객체를
사용하는 것도 가능하다.
```
