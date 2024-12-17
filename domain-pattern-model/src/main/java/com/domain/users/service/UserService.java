package com.domain.users.service;

import com.domain.users.dto.UserRequestDto;
import com.domain.users.dto.UserResponseDto;
import com.domain.users.entity.Users;
import com.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        Users users = Users.builder()
                        .name(userRequestDto.getName())
                        .password(userRequestDto.getPassword())
                        .email(userRequestDto.getEmail())
                        .build();

        // ... 비즈니스 로직

        return UserResponseDto.builder()
                              .users(userRepository.save(users))
                              .build();
    }
}
