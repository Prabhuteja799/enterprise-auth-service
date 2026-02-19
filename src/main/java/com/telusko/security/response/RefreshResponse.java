package com.telusko.security.response;

public record RefreshResponse(String newAccessToken ,String newRefreshToken) {
}
