package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysTopicRepository extends JpaRepository<SysTopic,Long> {
    public List<SysTopic> findByUsername(String username);
    public SysTopic findById(Long id);
}
