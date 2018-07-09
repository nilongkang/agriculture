package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysFileRepository extends JpaRepository<SysFile,Long> {
    public SysFile findById(Long id);
    public List<SysFile> findByUsername(String username);
}
