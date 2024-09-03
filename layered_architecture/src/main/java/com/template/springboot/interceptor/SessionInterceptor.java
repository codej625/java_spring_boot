package com.template.springboot.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
//
//        if (session == null) {
//            response.sendRedirect("/");
//            return false;
//        }

        return true;
    }

}
