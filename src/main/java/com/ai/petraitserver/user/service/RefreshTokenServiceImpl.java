package com.ai.petraitserver.user.service;

import com.ai.petraitserver.common.jwt.RefreshToken;
import com.ai.petraitserver.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void saveRefreshToken(String refreshToken, Long userId) {
        refreshTokenRepository.save(new RefreshToken(refreshToken, userId));
    }

}
