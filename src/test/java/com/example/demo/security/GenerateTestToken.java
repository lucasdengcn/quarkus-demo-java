package com.example.demo.security;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

public class GenerateTestToken {

    /**
     * Generate JWT token
     */
    public static void main(String[] args) {
        String token = signTestToken("test@example.com");
        System.out.println(token);
    }

    public static String signTestToken(String uid) {
        String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn(uid)
                        .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                        .expiresIn(Duration.of(10, ChronoUnit.MINUTES))
                        .audience("demo-app")
                        .subject("u1001")
                        .claim(Claims.birthdate.name(), "2001-07-13")
                        .sign();
        return token;
    }

}
