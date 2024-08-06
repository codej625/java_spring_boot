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

작동 순서를 알아보자.

1) 사용자가 아이디, 비밀번호를 Form을 통해 전송

2) AuthenticationFilter에서 UsernamePasswordAuthenticationToken을 생성하여 AuthenticationManager에게 전달

3) AuthenticationManager는 등록된 AuthenticationProvider(들)을 조회하여 인증을 요구

4) AuthenticationProvider는 UserDetailsService를 통해 입력받은 아이디에 대한 사용자 정보를 DB에서 조회

5) 입력받은 비밀번호를 암호화하여 DB의 비밀번호와 매칭시켜, 일치하는 경우 인증된 UsernamePasswordAuthenticationToken을 생성하여 AuthenticationManager에 전달

6) AuthenticationManager는 UsernameAuthenticationToken을 AuthenticationFilter로 전달

7) AuthenticationFilter는 전달받은 UsernameAuthenticationToken을 LoginSuccessHandler로 전송하고, SecurityContextHolder에 저장한다.
```
