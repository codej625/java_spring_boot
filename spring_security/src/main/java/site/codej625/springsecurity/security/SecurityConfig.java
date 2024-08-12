package site.codej625.springsecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
                .antMatchers("/test/**").permitAll() // Static resource 허용
                .antMatchers("/css/**").permitAll() // Static resource 허용
                .antMatchers("/js/**").permitAll() // Static resource 허용
                .antMatchers("/img/**").permitAll() // Static resource 허용
                .antMatchers("/as/**").permitAll() // Static resource 허용
                .antMatchers("/img/**").permitAll() // Static resource 허용
                // account 테스트 start
                .antMatchers("/index").hasRole("resigner") // 비밀번호 변경 테스트
                .antMatchers("/change-password").permitAll()
                .antMatchers("/account/check-username").permitAll()
                .antMatchers("/account/reset-password").permitAll()
                // account 테스트 end
                .antMatchers("/login").permitAll()
//                .antMatchers("/").hasRole("ADMIN") // ADMIN 권한을 가진 사용자만 허용
//                .antMatchers("/").hasAnyRole("USER", "ADMIN") // USER, ADMIN 권한을 가진 사용자만 허용
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied") // Access Denied 시 사용할 사용자 정의 페이지 URL 지정
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(loginFailHandler) //로그인 실패 시 처리하는 핸들러 등록.
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
