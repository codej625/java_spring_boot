package site.codej625.springsecurity.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.codej625.springsecurity.domain.users.entity.User;
import site.codej625.springsecurity.domain.users.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User not found -> " + username);

        // 스프링 시큐리티에서 사용하는 아이디, 비밀번호, 권한 값이다. 필드 커스텀 가능
        return new CustomUserDetails(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities()
        );
    }

}
