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