package com.template.springboot.controller;

import com.template.springboot.dto.request.RequestDto;
import com.template.springboot.service.impl.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/path/")
@Controller
public class RestController {

    private final RestService restService;

    @GetMapping("/")
    public String logout(Model model) { return "/index"; }

    @ResponseBody
    @PostMapping("/test")
    public ResponseEntity<?> getLoginInfo(@RequestBody(required = false) RequestDto RequestDto) {
        // ... 로직
//        return ResponseEntity.ok().body(result);
        return ResponseEntity.ok().build();
    }

}
