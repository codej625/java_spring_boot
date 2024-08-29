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

    private final LoginBusinessLogic loginBusinessLogic;

    @Override
    public void onAuthenticationSuccess
    (
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    )
    throws IOException, ServletException {

        String empId = authentication.getName(); // Origin ID
        String encryptId = ""; // Encrypt ID

        loginBusinessLogic.updateUserLastLog(empId); // 1) 로그인 성공 시 로그 업데이트

        boolean checkPassword = loginBusinessLogic.checkPassword(empId); // 2-1) 초기 비밀번호 체크

        // 2-2) 조건에 따른 세션 해제
        if (!checkPassword) {
            request.getSession().invalidate(); // 세션 해제
            encryptId = loginBusinessLogic.encryptId(empId); // 아이디 암호화
        }

        // 3) Redirect Path 생성
        String redirectUrl = loginBusinessLogic.redirectUrl(checkPassword, empId, encryptId);

        response.sendRedirect(request.getContextPath() + redirectUrl);
    }

}
```

<br /><br />

```
스프링 시큐리티에서 AuthenticationSuccessHandler 인터페이스를 구현한 클래스는,
사용자가 성공적으로 인증된 후에 실행된다.

작동 순서를 알아보자.

1) 사용자가 아이디, 비밀번호를 Form을 통해 전송

2) AuthenticationFilter에서 UsernamePasswordAuthenticationToken을 생성하여 AuthenticationManager에게 전달

3) AuthenticationManager는 등록된 AuthenticationProvider(들)을 조회하여 인증을 요구

4) AuthenticationProvider는 UserDetailsService를 통해 입력받은 아이디에 대한 사용자 정보를 DB에서 조회

5) 입력받은 비밀번호를 암호화하여 DB의 비밀번호와 매칭시켜, 일치하는 경우 인증된 UsernamePasswordAuthenticationToken을 생성하여 AuthenticationManager에 전달

6) AuthenticationManager는 UsernameAuthenticationToken을 AuthenticationFilter로 전달

7) AuthenticationFilter는 전달받은 UsernameAuthenticationToken을 LoginSuccessHandler로 전송하고, SecurityContextHolder에 저장한다.


* n개의 Filter가 존재한다.
* Filter 아래 Manager 1 개가 존재한다.
* Manager 아래 n’개의 Provider가 존재한다.
* Provider를 통해 인증정보와 권한이 담긴 Authentication을 반환한다.
* Spring Security는 기본적으로 세션 <-> 쿠키방식으로 인증한다.
* 생성된 유저의 session은 세션저장소인 SecurityContextHolder에 저장된다.

* 정리
모든 접근 주체는 Authentication 를 생성한다.
이것은 SecurityContext 에 보관되고 사용된다.
즉, security의 세션들은 내부 메모리(SecurityContextHolder)에 저장되고 필요 시 꺼내 사용한다.
```
