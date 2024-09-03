package com.template.springboot.domain.account;

import com.template.springboot.domain.login.entity.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountBusinessLogic {

    public boolean isAccountActive() {
        // ... 로직
        return false;
    }

    public Login getUserInfo() {
        // ... 로직
        return new Login();
    }

}