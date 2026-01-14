package com.example.rebookbackend.config;

import com.example.rebookbackend.common.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 放行预检请求 (解决跨域导致的拦截失效)
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 2. 获取请求头里的 Token
        String token = request.getHeader("Authorization");
        System.out.println("拦截器正在检查 Token: " + token);

        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        try {
            // 3. 尝试解析 (非法 Token 会在这里炸掉，跳进 catch)
            JwtUtils.parseToken(token);
            return true; // 校验通过，放行
        } catch (Exception e) {
            System.out.println("Token 校验失败: " + e.getMessage());
            response.setStatus(401); // 关键：给前端返回 401
            return false; // 拦截！
        }
    }
}
