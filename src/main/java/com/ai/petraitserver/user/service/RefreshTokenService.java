package com.ai.petraitserver.user.service;

public interface RefreshTokenService {
    void saveRefreshToken(String refreshToken, Long userId);
}
