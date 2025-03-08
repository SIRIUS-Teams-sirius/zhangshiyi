package com.example.federatedlearning;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", 1);
        claims.put("name", "John Doe");
        //生成
        String token=JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*24))//添加过期时间
                .sign(Algorithm.HMAC256("NIDSFL"));
        System.out.println(token);
    }
   @Test
    public void testParse() {
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7Im5hbWUiOiJKb2huIERvZSIsImlkIjoxfSwiZXhwIjoxNzQxNTA5NjU0fQ." +
                "imVR4UZO_kP2Aj-Srp6_WfgwKMDbk9CkKQYN6tGhqNs";

       JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256("NIDSFL")).build();

        DecodedJWT decodedJWT=jwtVerifier.verify(token);
        Map<String, Claim> claims =decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
