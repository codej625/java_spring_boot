# 스프링에서 사용되는 로그인 관리인 spring security에 기본 설정을 알아보자!

<br/>

```java

```

<br/>

### CSRF

Spring security는 CSRF 보호를 고려해야 한다.<br/>
Spring Security 4 이상에서는 CSRF 보호가 기본적으로 활성화되어 있기 때문이다.
이 경우, Request시 CSRF 토큰과 함께 전송되어야 한다.

```html
ex) Thymeleaf
<form action="/your-action" method="post">
  <input type="hidden" name="_csrf" value="${_csrf.token}"><!-- 숨겨진 폼 필드 -->
  <!-- 폼 필드 -->
</form>

ex) JSP
<form action="/your-action" method="post">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /><!-- 숨겨진 폼 필드 -->
  <!-- 폼 필드 -->
</form>
```

<br/>

만약 AJAX 요청을 사용하는 경우, CSRF 토큰은 요청 헤더에 포함되어야 한다.<br/>
Spring Security는 헤더 이름으로 "X-CSRF-TOKEN"을 기본값으로 사용하며, 이 토큰 값을 JavaScript를 통해 읽어서 요청 헤더에 추가할 수 있다.<br/>

1) CSRF 토큰 찾기
```html
먼저, CSRF 토큰이 웹 페이지에 어떻게 포함되어 있는지 확인해야 한다.
일반적으로, 이 토큰은 HTML 페이지의 메타 태그나 숨겨진 폼 필드에 저장 된다.
예를 들어, 메타 태그를 사용하는 경우 다음과 같이 페이지에 포함될 수 있다.

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
```

2) JavaScript를 통한 토큰 읽기
```javascript
const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
```

3) 이제 AJAX 요청을 보낼 때마다 이 토큰을 요청 헤더에 포함시켜야 한다.<br/>
   jQuery를 사용하는 경우, 전역 AJAX 설정을 사용하여 모든 요청에 자동으로 이 헤더를 추가할 수 있다.
```javascript
$.ajaxSetup({
  beforeSend: function(xhr) {
    xhr.setRequestHeader(csrfHeader, csrfToken);
  }
});
```
전역 처리를 하지 않고 ajax 요청시,
```javascript
var csrfToken = $("meta[name='_csrf']").attr("content");
var csrfHeader = $("meta[name='_csrf_header']").attr("content");

$.ajax({
  url: '/your-endpoint',
  type: 'post',
  data: {
    // 요청 데이터
  },
  beforeSend: function(xhr) {
    xhr.setRequestHeader(csrfHeader, csrfToken);
  },
  success: function(response) {
    // 성공 처리
  },
  error: function(xhr) {
    // 오류 처리
  }
});
```

Axios를 사용하는 경우,
```javascript
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

axios({
  method: 'post',
  url: '/your-endpoint',
  data: {
    // 요청 데이터
  },
  headers: {
    [csrfHeader]: csrfToken
  }
})
.then(response => {
  // 성공 처리
})
.catch(error => {
  // 오류 처리
});
```

Fetch API를 사용하는 경우,
```javascript
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

fetch('/your-endpoint', {
  method: 'POST',
  headers: {
      'Content-Type': 'application/json',
      [csrfHeader]: csrfToken
  },
  body: JSON.stringify({
      // 요청 데이터
  })
})
.then(response => response.json())
.then(data => {
  // 성공 처리
})
.catch(error => {
  // 오류 처리
});
```

4) 서버 측에서는 AJAX 요청이 도착하면 요청 헤더에 포함된 CSRF 토큰을 검증 한다.<br/>
   Spring Security는 이를 자동으로 처리 한다.<br/>
   요청 헤더에 유효한 CSRF 토큰이 포함되어 있지 않은 경우, 요청은 거부 된다.

<br/>

### 서버에서 토큰을 직접 만들어 전송 하는방법

1) CSRF 토큰 쿠키 생성 필터 구현
```java
import org.springframework.security.web.csrf.CsrfToken;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsrfTokenCookieFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    if (csrfToken != null) {
      String token = csrfToken.getToken();
      Cookie csrfCookie = new Cookie("XSRF-TOKEN", token);
      csrfCookie.setPath("/");
      csrfCookie.setHttpOnly(false);
      csrfCookie.setSecure(true); // HTTPS 환경에서만 true로 설정
      response.addCookie(csrfCookie);
    }
    chain.doFilter(request, response);
  }
  // 필터 초기화 및 파괴 메서드는 생략 (필요시 구현)
}
```

2) 생성한 필터를 Spring Boot 애플리케이션에 등록 한다.
   이를 위해 WebSecurityConfigurerAdapter를 확장한 클래스에서 필터를 등록할 수 있다.
```java
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
        // 기타 보안 구성
        .addFilterAfter(new CsrfTokenCookieFilter(), CsrfFilter.class); // 필터 추가
  }
}
```
위 코드에서 CsrfFilter.class 이후에 커스텀 필터를 추가하면, Spring Security의 CSRF 토큰이 생성된 후에 해당 필터가 동작 한다.

<br/>

#### 고려 사항
```
1. HTTPS 환경
csrfCookie.setSecure(true)는 HTTPS 환경에서만 사용해야 한다.
만약 개발 환경이 HTTP라면, 이 옵션을 false로 설정해야 한다.

2. CsrfToken 유효성
이 필터는 CsrfToken이 요청의 속성으로 존재할 때만 작동 한다.
Spring Security의 CSRF 보호가 활성화되어 있어야 한다.

3. 쿠키 옵션
쿠키의 HttpOnly와 Secure 플래그는 애플리케이션의 보안 요구사항에 따라 적절히 설정해야 한다.
HttpOnly를 false로 설정하면 JavaScript가 쿠키를 읽을 수 있다.
이렇게 구현하면, Spring Boot 애플리케이션에서 응답 시 CSRF 토큰을 쿠키로 클라이언트에 전달할 수 있으며,
클라이언트 측 JavaScript는 이 토큰을 읽어서 AJAX 요청에 사용할 수 있다.
```
