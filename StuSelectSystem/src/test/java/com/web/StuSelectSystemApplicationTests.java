package com.web;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class StuSelectSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void JwtTest(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "123456")
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
        System.out.println(jwt);
    }

}
