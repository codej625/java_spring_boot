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
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          .anyRequest().permitAll() /* 모든 요청 허용 */
      )
      .csrf(csrf -> csrf.disable()); /* CSRF 보호 비활성화 */

    return http.build();
  }
}
```
