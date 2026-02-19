package com.telusko.security.config;

import org.hibernate.annotations.CollectionIdMutability;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class TokenHashUtil {


    public String hash(String token){

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");

        byte[] hashedBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8)); //hashing
        String base64Token= Base64.getEncoder().encodeToString(hashedBytes); //SHA-256 might have unreadable special characters,so converting it tobase64
        return base64Token;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 ALGORITHM IS NOT FOUND");
        }
    }
}
