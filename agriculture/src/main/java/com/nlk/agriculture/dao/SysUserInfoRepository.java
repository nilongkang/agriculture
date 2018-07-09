package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserInfoRepository extends JpaRepository<SysUserInfo, Long> {
    public SysUserInfo findByUsername(String username);
}
