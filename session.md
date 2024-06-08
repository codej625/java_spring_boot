# 세션에 대해 알아보자.

<br /><br />

* 세션이란?
```
세션(Session)은 웹 애플리케이션에서 클라이언트와 서버 간의 상태를 유지하는 데 사용된다.
HTTP 프로토콜은 기본적으로 상태를 유지하지 않는(stateless) 프로토콜이기 때문에,
각각의 요청은 독립적으로 처리되고 서버는 이전 요청과 관련된 정보를 기억하지 않는다.
이것은 HTTP의 설계상의 장점 중 하나로,
서버 측에서 상태를 유지하지 않아도 확장성이 좋고 간단한 아키텍처를 가질 수 있도록 한다.

하지만 웹 애플리케이션은 종종 사용자의 상태를 추적하고 유지해야 하기 때문에,
이를 위해 세션은 클라이언트가 웹 서버에 연결한 후 일정 시간 동안 지속되는 상태 정보를 저장한다.
보통 세션은 서버 측에 저장되며, 각 클라이언트는 고유한 세션 ID를 받아 세션에 저장된 정보에 액세스할 수 있다.
```

<br /><br /><br />

* 예시
```
Intercepter를 사용해서 Session이 있을 때와 없을 때를 구별하는 로직을 만들어보자.

Intercepter가 사용될 Path를 지정하고 config로 등록한다.
```
```java
/* WebMvcConfig Class */ 

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SessionInterceptor())
            .addPathPatterns("/*") /* 해당 패턴의 모든 요청에 Interceptor 적용 */
            .excludePathPatterns("/login"); /* 로그인은 예외로 처리 */
  }
}
```

<br />

```

세션이 있을 때와 없을 때의 처리 로직을 만든다.

```
```java
public class SessionInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    /* 세션을 확인하고 세션이 없는 경우 리다이렉션 수행 */
    if (request.getSession(false) == null) {
      /* index 페이지로 리다이렉션 */
      response.sendRedirect("/");

      return false; /* 요청을 처리하지 않고 중단 */
    }
    return true; /* 요청을 계속 진행 */
  }
}
```
