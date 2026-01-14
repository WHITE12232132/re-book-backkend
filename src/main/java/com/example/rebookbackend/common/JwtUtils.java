package com.example.rebookbackend.common;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtils {
    private static final long EXPIRE = 1000 * 60 * 60 * 24; // 24小时过期
    private static final String SECRET = "re-book-secret-key-2026"; // 签名密钥

    // 生成 Token
    public static String createToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject("RE-BOOK-USER")
                .claim("userId", userId)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    // 解析 Token
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}
