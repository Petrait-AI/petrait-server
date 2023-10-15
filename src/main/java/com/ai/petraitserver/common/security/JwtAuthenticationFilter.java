package com.ai.petraitserver.common.security;


import com.ai.petraitserver.common.jwt.JwtUtil;
import com.ai.petraitserver.user.dto.LoginRequestDto;
import com.ai.petraitserver.user.enums.UserRoleEnum;
import com.ai.petraitserver.user.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException {
        log.info("로그인 성공");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        String token = jwtUtil.createToken(username, role, ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId());
        String refreshToken = jwtUtil.createRefreshToken();
        refreshTokenService.saveRefreshToken(refreshToken, ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);
    }

//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
//        log.info("로그인 실패");
//        ApiResponseDto apiResponseDto = new ApiResponseDto("로그인 실패", HttpStatus.BAD_REQUEST.value());
//        response.setStatus(HttpStatus.BAD_REQUEST.value());
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        String json = new ObjectMapper().writeValueAsString(apiResponseDto);
//        response.getWriter().write(json);
//    }

}