package com.nlk.agriculture.service.impl;


import com.nlk.agriculture.dao.SysRoleRepository;
import com.nlk.agriculture.domain.SysRole;
import com.nlk.agriculture.service.Roleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleserviceImpl implements Roleservice {
    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public SysRole Add(SysRole name) {
        return sysRoleRepository.save(name);
    }
}
