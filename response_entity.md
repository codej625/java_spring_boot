# ResponseEntity 정리.

<br /><br />

* ResponseEntity.
---
```
ResponseEntity는 스프링 프레임워크에서 HTTP 응답을 나타내는 클래스이다.
클라이언트로부터의 HTTP 요청에 대한 응답을 생성하고,
해당 응답에 대한 상태 코드, 헤더, 본문 등을 포함한다.
```

<br /><br /><br />

1. 주요 기능
```
* HTTP 상태 코드 설정
ResponseEntity를 사용하여 응답의 HTTP 상태 코드를 설정할 수 있다.

예시 -> ok() 메서드를 사용하면 상태 코드 200(OK)를 설정할 수 있다.
```

<br />

```
* HTTP 헤더 설정
필요에 따라 응답의 HTTP 헤더를 설정할 수 있다.
```

<br />

```
*응답 본문 설정
클라이언트에 반환될 데이터를 설정할 수 있다.
이 데이터는 제네릭으로 지정하여,
형식에 따라 자동으로 렌더링 되게 만들 수 있다.

예시 -> ResponseEntity<?>
```

<br /><br /><br />

2. ResponseEntity의 여러 유용한 정적 팩토리 메서드. 
```
* ok

HTTP 상태 코드 200(OK)를 반환한다.
주로 성공적으로 요청을 처리한 경우에 사용된다.
반환할 데이터를 함께 전달할 수 있다.

예시 -> return ResponseEntity.ok(data);
```

<br />

```
* notFound

HTTP 상태 코드 404(Not Found)를 반환.
주로 요청한 자원을 찾을 수 없는 경우에 사용.

예시 -> return ResponseEntity.notFound().build();
```

<br />

```
* badRequest

HTTP 상태 코드 400(Bad Request)를 반환.
주로 클라이언트의 요청이 유효하지 않은 경우에 사용.

예시 -> return ResponseEntity.badRequest().body("Invalid request");
```

<br />

```
* created

HTTP 상태 코드 201(Created)을 반환.
주로 새로운 리소스가 성공적으로 생성된 경우에 사용.
생성된 리소스의 위치(URI)를 함께 반환할 수 있다.

URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
return ResponseEntity.created(location).build();
```

<br />

```
* noContent

HTTP 상태 코드 204(No Content)를 반환.
주로 요청을 성공적으로 처리했지만 반환할 내용이 없는 경우에 사용.

예시 -> return ResponseEntity.noContent().build();
```

<br /><br />

```
* Tip

여기서 "data"는 반환할 데이터를 나타낸다.
이 데이터는 제네릭으로 지정된 형식에 따라 자동으로 렌더링된다.

ok() 메서드를 사용하여 데이터를 반환하는 경우.

예시 ->
User user = userRepository.findById(userId);
return ResponseEntity.ok(user);

이 코드는 HTTP 상태 코드 200(OK)를 반환하고,
user 객체를 JSON 형식으로 렌더링하여 응답 본문에 포함시킨다.
```
