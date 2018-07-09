package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysBankRepository extends JpaRepository<SysBank,Long>{
    public SysBank findById(Long id);
}
