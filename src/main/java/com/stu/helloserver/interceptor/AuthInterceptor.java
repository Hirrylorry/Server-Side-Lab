package com.stu.helloserver.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        boolean isRegister = "POST".equalsIgnoreCase(method) && "/api/users".equals(uri);
        boolean isLogin = "POST".equalsIgnoreCase(method) && "/api/users/login".equals(uri);

        if (isRegister || isLogin) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录或Token无效\",\"data\":null}");
            return false;
        }
        if (!token.startsWith("Bearer ")) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录或Token无效\",\"data\":null}");
            return false;
        }

        return true;
    }
}
