package site.codej625.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.codej625.springsecurity.domain.users.entity.User;
import site.codej625.springsecurity.domain.users.repository.UserRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {

        userRepository.save(
            User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build()
        );
    }

}