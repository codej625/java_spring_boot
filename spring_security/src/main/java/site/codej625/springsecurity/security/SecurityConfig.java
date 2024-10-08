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
