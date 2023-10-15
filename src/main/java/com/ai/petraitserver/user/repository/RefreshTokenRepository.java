package com.ai.petraitserver.user.repository;

import com.ai.petraitserver.common.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
