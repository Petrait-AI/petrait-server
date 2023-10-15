package com.ai.petraitserver.user.service;

import com.ai.petraitserver.common.jwt.JwtUtil;
import com.ai.petraitserver.common.jwt.RefreshToken;
import com.ai.petraitserver.user.entity.User;
import com.ai.petraitserver.user.repository.RefreshTokenRepository;
import com.ai.petraitserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Override
    public Map<String, String> refreshToken(String refreshToken) throws NoSuchElementException {
        if (refreshToken == null) throw new NoSuchElementException("refreshToken이 만료되었습니다.");
        RefreshToken redisToken = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new NoSuchElementException("refreshToken이 만료되었습니다.")); // 무조건 @Id로만 찾기
        Long userId = redisToken.getUserId();
        User user = userRepository.findById(userId).get();
        Map<String, String> tokens = new HashMap<>();
        String newToken = jwtUtil.createToken(user.getUsername(), user.getRole(), user.getId());
        tokens.put("accessToken", newToken);
        tokens.put("refreshToken", refreshToken); // 기존 refreshToken 유효하므로 그대로 반환
        return tokens;
    }
}
