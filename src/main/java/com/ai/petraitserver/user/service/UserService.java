package com.ai.petraitserver.user.service;


import java.util.Map;

public interface UserService {
    Map<String, String> refreshToken(String refreshToken);

}
