package site.codej625.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "login"; // 로그인 페이지로 이동
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // 로그인 성공 / 실패 후 이동 페이지
    }

    @GetMapping("/session")
    public String test() {
        return "test"; // Session 테스트
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin"; // 로그인 성공 후 이동 페이지
    }

    @GetMapping("/user")
    public String user() {
        return "user"; // 로그인 성공 후 이동 페이지
    }

    @GetMapping("/access-denied")
    public String err() {
        return "/access_denied"; // 로그인 성공 후 이동 페이지
    }

}