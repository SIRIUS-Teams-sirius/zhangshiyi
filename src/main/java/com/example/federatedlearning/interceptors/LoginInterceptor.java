/*
package com.example.federatedlearning.interceptors;

import com.example.federatedlearning.utils.JwtUtil;
import com.example.federatedlearning.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
*/
/*    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        String token = request.getHeader("Authorization");
        try{
            Map<String,Object> claims = JwtUtil.parseToken(token);

            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        }
        catch(Exception e){
            response.setStatus(401);
            e.printStackTrace(); // 打印异常信息
            //不放行
            return false;
        }
    }*//*

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀
            System.out.println("Received Token: " + token); // 打印 Token
            try {
                Map<String, Object> claims = JwtUtil.parseToken(token);
                // 处理 claims
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                e.printStackTrace(); // 打印异常
                return false; // 拒绝请求
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false; // 拒绝请求
        }
        return true; // 放行请求
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
*/
package com.example.federatedlearning.interceptors;

import com.example.federatedlearning.service.UserService;
import com.example.federatedlearning.utils.JwtUtil;
import com.example.federatedlearning.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private UserService userService;

    private static final String[] EXCLUDED_PATHS = {"/user/login", "/user/register", "/"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        boolean isExcludedPath = false;

        // 检查请求路径是否在排除列表中
        for (String path : EXCLUDED_PATHS) {
            if (requestURI.equals(path)) {
                isExcludedPath = true;
                break;
            }
        }

        // 如果是排除路径，则直接放行
        if (isExcludedPath) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀
            logger.info("Received Token: {}", token); // 打印 Token
            try {
                Map<String, Object> claims = JwtUtil.parseToken(token);
                // 将业务数据存储到 ThreadLocal 中
                ThreadLocalUtil.set(claims);

                // 检查 token 是否过期
                if (JwtUtil.isTokenExpired(claims)) {
                    Long userId = (Long) claims.get("userId"); // 假设 claims 中包含 userId
                    userService.setUserStatusOffline(userId); // 设置用户状态为 offline
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置状态码为 401
                    logger.warn("Token expired, user {} status set to offline", userId);
                    redirectToLogin(response);
                    return false; // 拒绝请求
                }

                // 检查用户状态
                Long userId = (Long) claims.get("userId");
                String userStatus = userService.getUserStatus(userId); // 获取用户状态
                if ("offline".equals(userStatus)) {
                    redirectToLogin(response);
                    return false; // 拒绝请求
                }

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                logger.error("Token validation failed: {}", e.getMessage());
                redirectToLogin(response);
                return false; // 拒绝请求
            }
        } else {
            // 如果没有 token，跳转至登录页面
            redirectToLogin(response);
            return false; // 拒绝请求
        }
        return true; // 放行请求
    }

    private void redirectToLogin(HttpServletResponse response) {
        try {
            response.sendRedirect("/user/login"); // 跳转至登录页面
        } catch (IOException e) {
            logger.error("Redirection failed: {}", e.getMessage());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清空 ThreadLocal 中的数据
        ThreadLocalUtil.remove();
    }
}