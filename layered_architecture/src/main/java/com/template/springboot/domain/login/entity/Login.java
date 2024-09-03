package com.template.springboot.domain.login.entity;

import com.template.springboot.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "LOGIN")
public class Login {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "CREAT_DATE", nullable = true)
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE", nullable = true)
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Account account;

    public Login() {}

    // 생성자에 @Builder 적용
    @Builder
    public Login(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
