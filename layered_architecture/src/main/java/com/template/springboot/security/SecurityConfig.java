package com.template.springboot.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/**").permitAll() // 전체 허용
                    .requestMatchers("/admin").hasAnyRole("manage", "admin") // manage, admin 권한 확인후 허용
                    .requestMatchers(
                        "/path/test1",
                        "/path/test2",
                        "/path/test3"
                    ).hasRole("system") // system 권한 확인후 허용
                    .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
            )
            .exceptionHandling(exceptionHandling ->
                exceptionHandling
                    .accessDeniedPage("/access-denied")
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login") // 로그인
                    .successHandler(loginSuccessHandler()) // 로그인 성공 시 핸들러
                    .failureHandler(loginFailHandler()) // 로그인 실패 시 핸들러
                    .permitAll()
            )
            .logout(LogoutConfigurer::permitAll) // 로그아웃
            .httpBasic(withDefaults()) // HTTP 기본 인증 설정
            .csrf(AbstractHttpConfigurer::disable); // TEST 환경 시
        return http.build();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() { return new LoginSuccessHandler(); }

    @Bean
    public LoginFailHandler loginFailHandler() { return new LoginFailHandler(); }

}