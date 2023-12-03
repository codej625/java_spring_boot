# request 객체를 사용해서 여러가지 값을 받아보자!

<br/>

1. 사용할 객체를 import 한다.
```java
import javax.servlet.http.HttpServletReque
```

<br>

2. 사용 예시
```java
@PostMapping(${url})
public Map<String, Object> RequsetController(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
  
  String utm_source = request.getParameter("utm_source");
  String utm_medium = request.getParameter("utm_medium");
  String utm_campaign = request.getParameter("utm_campaign");
  String utm_content = request.getParameter("utm_content");
  String utm_term = request.getParameter("utm_term");
  String ip = request.getRemoteAddr();
  
  System.out.println("utm_source => " + utm_source);
  System.out.println("utm_medium => " + utm_medium);
  System.out.println("utm_campaign => " + utm_campaign);
  System.out.println("utm_content => " + utm_content);
  System.out.println("utm_term => " + utm_term);
  System.out.println("ip => " + ip);
  System.out.println("params => " + params);
  
  return new HashMap<String, Object>() {{
    put("status", "200");
  }};
}
```
