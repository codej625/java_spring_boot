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

        return new CustomUserDetails(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities()
        );
    }

}