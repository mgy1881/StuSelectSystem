package com.web;

import com.web.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
@Slf4j
class StuSelectSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void JwtTest(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("name","7898789");
        String jwt = JwtUtils.generateJwt(claims);
        System.out.println(jwt);
    }

    @Test
    void JwtParseTest(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiNzg5ODc4OSIsImV4cCI6MTcwMTU3NzI4N30.ZbQET0hnwVDmM0ustzSBwRkmX2aBIy1TqfkvTZ95Sic";
        Claims res = JwtUtils.parseJWT(jwt);
        System.out.println(res);
        System.out.println(res.get("is") == null);

    }

}
