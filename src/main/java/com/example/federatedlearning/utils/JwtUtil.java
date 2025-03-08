/*
package com.example.federatedlearning.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String KEY = "INDSFL";

    //接收业务数据，生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims",claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))
                .sign(Algorithm.HMAC256(KEY));
    }

    //接收token，验证token，并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();

    }
}
*/
package com.example.federatedlearning.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String KEY = "INDSFL"; // 确保使用安全的密钥

    // 接收业务数据，生成 token 并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 12小时有效期
                .sign(Algorithm.HMAC256(KEY));
    }

    // 接收 token，验证 token，并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token is null or empty");
        }

        try {
            return JWT.require(Algorithm.HMAC256(KEY))
                    .build()
                    .verify(token)
                    .getClaim("claims")
                    .asMap();
        } catch (JWTVerificationException e) {
            System.out.println("Token verification failed: " + e.getMessage());
            throw e; // 重新抛出异常
        } catch (Exception e) {
            System.out.println("Failed to parse token: " + e.getMessage());
            throw e; // 重新抛出异常
        }
    }
    
    public static boolean isTokenExpired(Map<String, Object> claims) {
        // 获取过期时间（假设它是一个 Long 类型的时间戳）
        if (claims.containsKey("exp")) {
            long expirationTime = ((Number) claims.get("exp")).longValue();
            return System.currentTimeMillis() > expirationTime; // 判断当前时间是否超过过期时间
        }
        return true; // 如果没有 exp 字段，默认认为 token 过期
    }
}