package com.nlk.agriculture.service.impl;


import com.nlk.agriculture.dao.SysUserRepository;
import com.nlk.agriculture.domain.SysUser;
import com.nlk.agriculture.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserserviceImpl implements Userservice {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser save(SysUser user) {
        return sysUserRepository.save(user);
    }

}
