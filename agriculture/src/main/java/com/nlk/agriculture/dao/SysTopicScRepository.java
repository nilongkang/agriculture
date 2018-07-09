package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysTopicSc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysTopicScRepository extends JpaRepository<SysTopicSc,Long> {
    public SysTopicSc findById(long id);
}
