package com.telusko.security.response;

public record AuthResponse(String jwtToken, String refreshToken) {
}
