# Spring Security에서 UserDetails와 UserDetailsService에 대해 알아보자.

<br /><br />

```
사용자 인증과 관련된 핵심 인터페이스이다.

1) UserDetails
Spring Security에서 사용자의 정보를 담는 인터페이스.

Spring Security에서 사용자의 정보를 불러오기 위해서 구현해야 하는 인터페이스로 기본 오버라이드 메서드들을 제공.
UserDetails 를 CustomUserDetails라는 이름으로 구현하는 경우가 많다.
```

<br />

```
2) UserDetailsService

Spring Security에서 유저의 정보를 가져오는 인터페이스.

Spring Security에서 유저의 정보를 불러오기 위해서 구현해야하는 인터페이스로 기본 오버라이드 메서드들을 제공.
```

<br /><br /><br />

1. UserDetails 사용 예시

```
UserDetails 인터페이스는 Spring Security가 사용자 정보를 관리하는 핵심 인터페이스이다.
일반적으로 사용자의 기본 정보와 권한 정보를 제공한다.
```

```java
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 아래 메서드들은 예제에서 필요한 경우 구현할 수 있다.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```

<br /><br /><br />

2. UserDetailsService 사용 예시

```
Spring Security가 사용자 정보를 데이터베이스나,
다른 저장소에서 가져오는 데 사용하는 인터페이스이다.
```

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // 실제로는 여기에서 데이터베이스나 외부 저장소에서 사용자 정보를 가져옵니다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 예제에서는 하드코딩으로 사용자 정보를 만듭니다.
        if ("user".equals(username)) {
            return new CustomUserDetails("user", "$2a$10$j5mEVvP41gRZREpueNn3B.xtZI6CgsQSwvWcPt3GZTbjDhw3F.CpW",
                    List.of(() -> "ROLE_USER"));
        } else {
            throw new UsernameNotFoundException("User not found with username -> " + username);
        }
    }
}
```

<br /><br /><br />

3. 시큐리티 Config 설정

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/public/**").permitAll()
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .httpBasic()
                .and()
            .csrf().disable() // CSRF 보안 기능 비활성화 (테스트 목적)
    }
}
```

```
* 설명

SecurityConfig
이 클래스는 WebSecurityConfigurerAdapter를 확장하여 스프링 시큐리티 설정을 구성한다.


SecurityFilterChain 빈
SecurityFilterChain을 반환하는 securityFilterChain 메서드를 정의한다.
이 메서드는 HttpSecurity를 사용하여 스프링 시큐리티 필터 체인을 구성한다.


HttpSecurity
http 객체를 사용하여 다양한 보안 설정을 구성한다.


authorizeRequests()
요청에 대한 접근 제한을 설정.


antMatchers()
특정 URL 패턴에 대한 접근 권한을 설정.


authenticated()
모든 요청은 인증된 사용자만 접근할 수 있도록 설정.


formLogin()폼 기반 로그인 설정을 구성.


loginPage()와 permitAll()
사용자 지정 로그인 페이지 설정 및 로그인 페이지에 대한 접근 허용 설정을 구성.


logout()
로그아웃 설정을 구성.


httpBasic()
HTTP Basic 인증을 설정.


csrf().disable()
CSRF(Cross-Site Request Forgery) 보안 기능을 비활성화한다. (테스트 목적)
```

<br /><br />

```
* 설명

UserDetails
사용자의 식별 정보(username), 비밀번호(password), 권한(authorities)을 제공한다.
추가적으로 계정 만료 여부, 잠긴 계정 여부, 자격 증명 만료 여부, 활성화 여부를 구현할 수 있다.


UserDetailsService
사용자 정보를 제공하기 위해 필요한 인터페이스로,
loadUserByUsername 메서드를 구현하여 사용자의 식별 정보(username)을 받아 해당 사용자의 UserDetails 객체를 반환한다.

실제 애플리케이션에서는 데이터베이스나 LDAP 등을 통해 실제 사용자 정보를 조회하고
UserDetails 객체를 구성하는 로직을 구현해야 한다.
```
