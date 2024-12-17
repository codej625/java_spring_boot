package com.domain.users.dto;

import com.domain.users.entity.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;

    @Builder
    public UserResponseDto(Users users) {
        this.id = users.getId();
    }
}
