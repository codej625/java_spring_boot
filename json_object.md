# 자바&스프링부트에서 json 값을 다루는 법.

<br /><br />

* 예시 json
```json
{
  "items": [
    {
      "rec_KEY": "00001",
      "user_NO": "000001",
      "user_ID": "codej625",
      "name": "이진우",
      "civil_NO": "000000-1000000"
    }
  ]
}
```

<br /><br /><br />

* Jackson Library 사용
```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// JSON 문자열
String jsonString = "{예시_json}";

// ObjectMapper 생성
ObjectMapper objectMapper = new ObjectMapper();

try {
  // JSON 문자열을 JsonNode로 변환
  JsonNode jsonNode = objectMapper.readTree(jsonString);

  // "name" 필드의 값을 가져오기
  String name = jsonNode.get("items").get(0).get("name").asText();

  // 값 출력
  System.out.println("Name: " + name);
} catch (Exception e) {
  e.printStackTrace();
}
```

<br />

```java
String jsonString = "{예시_json}";
JsonNode responseJson = objectMapper.readTree(jsonString);

if (responseJson.has("message")) {
  String message = responseJson.get("message").asText();
}
```

<br /><br /><br />

* ResponseEntity(Spring Boot)
```java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

  @GetMapping("/")
  public ResponseEntity<String> ex() {
    // JSON 문자열
    String jsonString = "{예시_json}";

    // JSON 문자열을 ResponseEntity로 반환
    return ResponseEntity.ok().body(jsonString);
  }
}
```
