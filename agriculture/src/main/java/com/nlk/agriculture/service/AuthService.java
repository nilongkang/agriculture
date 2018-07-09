package com.nlk.agriculture.service;

import com.nlk.agriculture.domain.SysUser;

public interface AuthService {
    SysUser register(SysUser userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}