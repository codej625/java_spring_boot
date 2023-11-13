package site.codej625.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
		/* 로직이 바로 적용이 안 된다면, STS를 재실행 해야 한다. */
	
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(BCryptPasswordEncoder passwordEncoder) {
      this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
        .withUser("mplanit") /* id */
        .password(passwordEncoder.encode("Mplanit1909!")) /* password */
        .authorities("ROLE_USER");
    }

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
    		.csrf().disable() /* CSRF 보호 비활성화 default enable */
        .authorizeRequests(authorizeRequests -> 
          authorizeRequests
            .antMatchers("/dev/**").authenticated() /* Require authentication for specific paths */
            .anyRequest().permitAll() /* Allow access without authentication for all other requests */
        )
        .formLogin(formLogin -> 
          formLogin
            /* .loginPage("/login-form") /* Custom login page URL */
	          /* .loginProcessingUrl("/perform_login") /* URL where the login form is submitted */
	          .defaultSuccessUrl("/home", true) /* Redirect URL after successful login */
	          .failureUrl("/login-form?error=true") /* Redirect URL after login failure */
	          .permitAll()
        )
        .logout(logout -> 
          logout
            .logoutUrl("/logout") /* Set the logout processing URL */
            .logoutSuccessUrl("/login?logout") /* Redirect URL after successful logout */
            .invalidateHttpSession(true) /* Invalidate session on logout */
            .deleteCookies("JSESSIONID") /* Delete cookies on logout */
            .permitAll()
        )
        .httpBasic();

    return http.build();
  }
  
}