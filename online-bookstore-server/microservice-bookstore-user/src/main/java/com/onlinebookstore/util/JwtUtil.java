package com.onlinebookstore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @author rkc
 * @date 2020/9/14 10:01
 * @version 1.0
 */
public class JwtUtil {

    /**
     * 设置有效期 60 * 60s
     */
    private static final Long JWT_TTL = 3600000L;

    /**
     * 设置秘钥明文
     */
    private static final String JWT_KEY = "NVIDIA";

    /**
     * 创建token
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }

        //过期时间
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        SecretKey secretKey = generalKey();

        JwtBuilder jwtBuilder = Jwts.builder()
                //唯一id
                .setId(id)
                //主题，可以是json数据
                .setSubject(subject)
                //签发者
                .setIssuer("admin")
                //折佣HS256对称加密算法签名，参数二为秘钥
                .signWith(signatureAlgorithm, secretKey)
                .setExpiration(expDate);
        return jwtBuilder.compact();
    }

    /**
     * 解析JWT
     */
    public static Claims parseJwt(String compactJwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(compactJwt).getBody();
    }

    /**
     * 生成加密后的秘钥
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(JwtUtil.JWT_KEY.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
