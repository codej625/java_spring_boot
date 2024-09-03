package com.template.springboot.domain.login;

import com.template.springboot.domain.login.entity.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginBusinessLogic {

    public boolean isAccountActive() {
        // ... 로직
        return false;
    }

    public Login getUserInfo() {
        // ... 로직
        return new Login();
    }

}