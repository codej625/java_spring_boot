# 인증 성공 이후 로직을 구현하는 방법

<br /><br />

* AuthenticationSuccessHandler
---

```
사용자가 성공적으로 인증될 때 실행할 특정 작업을 정의할 수 있게 한다.
주로 로그인 성공 후에 사용자를 리디렉션하거나 특정 동작을 수행하는 데 활용한다.
```

<br /><br /><br />

* 활용 예시
---

```java
// 1) SecurityConfig

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    // Autowierd
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css/**").permitAll() 
                .antMatchers("/js/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/").hasRole("ADMIN")
                .antMatchers("/").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 시 핸들링
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .httpBasic()
                .and()
            .csrf().disable();

        return http.build();
    }

}
```

<br />

```java
// 2) CustomAuthenticationSuccessHandler 구현

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginMapper loginMapper;

    @Override
    public void onAuthenticationSuccess
    (
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    )
    throws IOException, ServletException {
        // 로그인 성공 이후 로직 처리
        loginMapper.updateUser(authentication.getName());

        response.sendRedirect(request.getContextPath() + "/index");
    }

}
```

<br /><br />

```
스프링 시큐리티에서 AuthenticationSuccessHandler 인터페이스를 구현한 클래스는,
사용자가 성공적으로 인증된 후에 실행된다.

1) 사용자가 로그인을 시도
스프링 시큐리티는 설정된 인증 프로바이더(예: DaoAuthenticationProvider)를 통해 사용자 정보를 인증한다.
(인증이 성공하면 Authentication 객체가 생성된다.)


2) AuthenticationSuccessHandler 호출
인증이 성공하면 UsernamePasswordAuthenticationFilter는 SuccessfulAuthenticationEvent를 발생시킨다.
이 이벤트는 AbstractAuthenticationProcessingFilter에서 처리되며,
이 필터는 내부적으로 AuthenticationSuccessHandler의 구현체를 호출한다.


3) onAuthenticationSuccess 메서드 실행
AuthenticationSuccessHandler를 구현한
CustomAuthenticationSuccessHandler 클래스에서 오버라이드 된 onAuthenticationSuccess 메서드가 호출된다.
이때 메서드 파라미터로는 다음과 같은 정보가 전달된다.

HttpServletRequest request: 사용자의 요청 정보를 담고 있는 객체
HttpServletResponse response: 응답을 처리할 수 있는 객체
Authentication authentication: 인증된 사용자의 정보를 담고 있는 객체

위의 객체를 갖고 onAuthenticationSuccess 메서드에서 추가적인 처리를 한다.
```
