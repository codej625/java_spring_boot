# HandlerInterceptor에 대해 알아보자.

<br /><br />

* 설명
---

```
HandlerInterceptor는 Spring MVC에서 제공하는 인터페이스이다.
특정한 작업을 수행하기 전이나 후에 사용자의 요청을 가로채는 역할을 한다.
```

<br /><br /><br />

* HandlerInterceptor 구현체
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
    registry.addInterceptor(new SessionInterceptor());
  }
}
```

<br /><br /><br />

* 사용 예시
---

1. 인터셉터 구현체를 만든다.
```java
public class SessionInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
    if (session == null || session.getAttribute({yourSessionAttribute}) == null) {
      response.sendRedirect("/home");
      return false; // 요청 진행 중단
    }
    return true; // 요청 진행 계속
  }
}
```

<br />

2. Config 작성
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
            .addPathPatterns("/**")
            // 클라이언트
            .excludePathPatterns("/")
            .excludePathPatterns("/ncmik/index")
            .excludePathPatterns("/ncmik/main")
            .excludePathPatterns("/ncmik/login")
            .excludePathPatterns("/ncmik/login-failed")
            // 어드민
            .excludePathPatterns("/ncmik/admin")
            // API
            .excludePathPatterns("/auth/login")
            // 정적 리소스 제외
            .excludePathPatterns("/static/**")
            .excludePathPatterns("/css/**")
            .excludePathPatterns("/js/**")
            .excludePathPatterns("/img/**")
            .excludePathPatterns("/font/**")
            .excludePathPatterns("/lib/**")
            .excludePathPatterns("/config/**");
    }

}
```

<br /><br />

```
세션이 없으면 /home 경로로 이동시키는 인터셉터이다.
excludePathPatterns()를 사용해서 정적 리소스와 관련 URL을
인터셉터에 적용되지 않게 해야 한다.
```
