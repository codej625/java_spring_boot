# HandlerInterceptor애 대해 알아보자.

<br /><br />

* 설명
---

```
HandlerInterceptor는 Spring MVC에서 제공하는 인터페이스이다.
특정한 작업을 수행하기 전이나 후에 사용자의 요청을 가로채는 역할을 한다.
```

<br /><br /><br />

* 사용 예시
---


1. HandlerInterceptor 인터페이스를 구현하는 Class를 만들고 메서드를 목정에 맞게 재정의 한다.

```java
public class SessionInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    /* 컨트롤러 메소드가 실행되기 전에 호출 */
    /* false를 반환하면 요청 처리가 중단되고, true를 반환하면 계속 진행 */
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    /* 컨트롤러 메소드가 실행된 후, 뷰를 렌더링하기 전에 호출 */
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    /* 요청 처리가 완전히 끝난 후, 즉 뷰를 렌더링한 후에 호출 */
  }
}
```

<br />

2. Intercepter가 사용될 Path를 지정하고 config로 등록한다.

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
