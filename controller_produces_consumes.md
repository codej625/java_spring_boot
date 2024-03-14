# produces, consumes에 대해 알아보자.

<br />

```
아래와 같은 코드가 있다고 해보자.
```

```java
@PostMapping(value = "/test")
public ResponseEntity<Void> test(@RequestBody Map<String, Object> params) throws Exception {
  log.info(">>> test >>>");

  try {
    String test = (String) params.get("test");
    testMapper.testInsert(test);
    return new ResponseEntity<>(HttpStatus.CREATED);
  } catch (Exception e) {
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
```

<br />

```
클라이언트에서는 밑에와 같은 요청을 하고 있다고 가정해보자.
```

```javascript
async function insertPhone(test) {
  const insertCheck = confirm('등록하시겠습니까?');

  if (insertCheck) {
    try {
      /* csrf 처리 */
      const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
      const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
      const errorMessage = 'Network error';
      
      const response = await fetch('/test', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          [csrfHeader]: csrfToken,
        },
        body: JSON.stringify({test})
      })
      
      if (!response.ok) {
        throw new Error(errorMessage);
      }

      alert('등록되었습니다.');
      fetchData(); // fetchData 함수 호출

    } catch (error) {
      console.error(error);
      alert('Network error');
      /*
        ... 
        Additional logic needs to be added later. 
      */
    }
  } else {
    alert('취소하였습니다.');
  }
}
```

<br />

```
위와 같은 상황에서는
해당 컨트롤러 메서드가 JSON 형식의 요청을 수락하지 않았기 때문에
에러가 날 것이다.
```

```
@PostMapping(value = "/blocked-numbers/add" , consumes = "application/json")
위와 같이 내용을 추가해준다.
consumes="application/json"은 해당 컨트롤러 메서드가 JSON 형식의 요청을 수락한다는 것을 나타낸다.
```

<br />

```
정리하면,
produces="application/json"은 해당 컨트롤러 메서드가 JSON 형식의 응답을 생성한다는 것을 나타낸다.
클라이언트가 이 엔드포인트를 요청할 때 서버는 JSON 형식의 응답을 생성하여 반환한다.

consumes="application/json"은 해당 컨트롤러 메서드가 JSON 형식의 요청을 수락한다는 것을 나타낸다.
클라이언트가 JSON 형식의 데이터를 요청 본문에 포함하여 이 엔드포인트를 요청할 때만 요청을 수락한다.

이러한 속성들은 컨트롤러 메서드가 클라이언트와 상호 작용할 때 사용되는 데이터 형식을 명시적으로 정의하는 데 사용된다.
이를 통해 클라이언트와 서버 간의 통신이 명확하게 이루어지고, 잘못된 데이터 형식으로 인한 오류를 방지할 수 있다.
```
