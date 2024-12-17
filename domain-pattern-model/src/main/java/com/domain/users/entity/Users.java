package com.domain.users.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Long id;

    private String name;

    private String password;

    private String email;

    @Builder
    public Users(String name, String password, String email) {}

    // ... 비즈니스 로직

}