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

1. UserDetails

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

2. UserDetailsService

```
Spring Security가 사용자 정보를 데이터베이스나,
다른 저장소에서 가져오는 데 사용하는 인터페이스이다.
```

```java
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User not found -> " + username);

        return new CustomUserDetails(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities()
        );
    }

}
```

<br /><br /><br />

3. Security Config

```java
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/register").permitAll() // 회원가입 테스트
                .antMatchers("/index").permitAll() // 경로에 대해 모든 사용자 허용
                .antMatchers("/all/**").permitAll() // all 하위 경로에 대해 모든 사용자 허용
                .antMatchers("/user").hasAnyRole("USER") // USER 권한을 가진 사용자만 허용
                .antMatchers("/test").hasAnyRole("USER", "ADMIN") // USER, ADMIN 권한을 가진 사용자만 허용
                .antMatchers("/admin").hasAnyRole("ADMIN") // ADMIN 권한을 가진 사용자만 허용
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied") // Access Denied 시 사용할 사용자 정의 페이지 URL 지정
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home", true) // 로그인 성공 후 리다이렉트 URL
                .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트 URL
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

<br /><br /><br />

4. User entity

```java
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    @Builder
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

}
```

<br /><br /><br />

4. User repository

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
```

<br /><br /><br />

5. passwordEncoder

```java
@Configuration
public class BCrypt {

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

}
```

<br /><br /><br />

7. Controller

```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        // 사용자 등록 테스트
        userRepository.save(
            User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build()
        );
    }

}

// View 관련
@Controller
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "login"; // 로그인 페이지로 이동
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // 로그인 성공 / 실패 후 이동 페이지
    }

    @GetMapping("/session")
    public String test() {
        return "test"; // Session 테스트
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin"; // 로그인 성공 후 이동 페이지
    }

    @GetMapping("/user")
    public String user() {
        return "user"; // 로그인 성공 후 이동 페이지
    }

    @GetMapping("/access-denied")
    public String err() {
        return "/access_denied"; // 로그인 성공 후 이동 페이지
    }

}
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
```
