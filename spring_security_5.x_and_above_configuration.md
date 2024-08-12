# 스프링시큐리티를 5.x 버전 이상에서 세팅해보자.

<br /><br />

* 설정 방법
---

```
스프링 시큐리티 5.7.0 버전 이후부터는 WebSecurityConfigurerAdapter 대신
SecurityFilterChain과 @Bean을 사용하여 보안 구성을 해야 한다.
```

<br /><br /><br />

* 예시
---

```java
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final  LoginFailHandler loginFailHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Pages
                .antMatchers("/login").permitAll()
                // Static Resource
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/as/**", "/account/**", "/login/**").permitAll()
                // Account Domain
                .antMatchers("/account/change-password").permitAll()
                .antMatchers("/account/check-username").permitAll()
                .antMatchers("/account/reset-password").permitAll()
                // Index or Home
                .antMatchers("/index").hasAnyRole("system", "admin", "manage", "user", "distributor", "resigner")
//                .antMatchers("/").hasRole("admin") // admin 권한만 허용
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied") // Access Denied 시 사용할 사용자 정의 페이지 URL
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 시 처리 핸들러
                .failureHandler(loginFailHandler) // 로그인 실패 시 처리 핸들러
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .httpBasic()
                .and()
            .csrf().disable(); // CSRF 보안 기능 비활성화 (테스트 목적)

        return http.build();
    }

}
```
