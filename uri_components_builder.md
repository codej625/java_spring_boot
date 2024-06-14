# POST Method에서 GET처럼 파라미터를 전송해보자.

<br /><br />

* 스프링부트에서 API 통신을 하는 방법?
---

```
스프링 부트에서는 UriComponentsBuilder() 객체를 사용해 API 전송을 간편히 할 수 있다.
간혹 오래된 프로젝트의 API Server에 요청작업을 하다 보면 GET, POST 두 가지 Method 지원하는 모습을 볼 수 있는데
문제는 여기서 시작된다.

GET방식이야 애초에 바디가 없지만 POST 방식은 일반적으로 바디에 값을 감싸서 보내기 때문에
보안에 유리하다.
하지만 GET, POST 방식을 하나의 컨트롤러로 해결하기 위해,
간혹 POST Method를 사용하면서도 Body에 값을 담는 게 아니라 GET 방식 처럼 파라미터를
전송해야 하는 경우가 생긴다.

이때 필요한게 UriComponentsBuilder() 객체이다.
```

<br /><br /><br />

* 사용 예시
---

```java
public ResponseEntity<String> authPost(LoginRequestDto loginRequestDto) throws Exception {
  // 필수 값 세팅
  UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl({url}) // {url} 자리에는 주소를 적는다.
      .queryParam("AUTH_KEY", LoginHandlerVO.AUTH_KEY)
      .queryParam("MANAGE_CODE", LoginHandlerVO.MANAGE_CODE)
      // Parameter 추가 작업이 여기서 끝난다면 마지막에 .toUriString();을 붙여준다.
      .queryParam("FAMILY_YN", LoginHandlerVO.FAMILY_YN);

  LoginType type = checkLoginDataType(loginRequestDto);

  if (type == LoginType.SERIAL) {
    builder.queryParam("USER_SERIAL", loginRequestDto.getUserSerial()); // 추가로 Parameter를 추가하고,
  }
  // Parameter 추가 작업이 끝났기 때문에 .toUriString();을 붙여 Uri를 String으로 변환.
  String urlWithParams = builder.toUriString();

  // POST Method를 사용했지만 파라미터를 GET처럼 Parameter를 추가해서 요청한다.
  RestTemplate restTemplate = new RestTemplate();
  ResponseEntity<String> lasResponse = restTemplate.postForEntity(urlWithParams, null, String.class);
  return lasResponse;
}
```
