package site.codej625.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                .antMatchers("/public/**").permitAll()  // "/public/**" 경로는 인증 없이 접근 허용
                .anyRequest().authenticated()          // 그 외의 모든 요청은 인증 필요
            )
            .formLogin()  // 기본 로그인 폼 제공
            .and()
            .httpBasic();  // HTTP 기본 인증 활성화

        return http.build();
    }
}