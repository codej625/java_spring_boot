# AuthenticationFailureHandler

<br /><br />

```
AuthenticationFailureHandler는
Spring Security에서 인증 실패 시에 호출되는 핸들러이다.

이 핸들러는 사용자가 로그인할 때 인증에 실패했을 때의 동작을 정의하는 데 사용된다.
예를 들어, 잘못된 사용자 이름이나 비밀번호를 입력했을 때 어떤 페이지로 리다이렉트할지,
사용자에게 어떤 메시지를 보여줄지 등을 설정할 수 있다.
```

<br /><br /><br />

1. 구현 예시

```java
// 1) Security config

@Configuration
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final  LoginFailHandler loginFailHandler;

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http

        // ... 로직

        .formLogin()
            .loginPage("/login")
            .successHandler(customAuthenticationSuccessHandler)
            .failureHandler(loginFailHandler) // 로그인 실패 시 처리하는 핸들러 등록.
            .permitAll()
            .and()
```

<br /><br /><br />

```java
// 2) AuthenticationFailureHandler 구현체

@RequiredArgsConstructor
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

    private final LoginBusinessLogic loginBusinessLogic;

    @Override
    public void onAuthenticationFailure
    (
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    )
    throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/login?check=false");
    }

}
```
