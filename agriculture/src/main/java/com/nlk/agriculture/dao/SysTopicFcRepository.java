package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysTopicFc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysTopicFcRepository extends JpaRepository<SysTopicFc, Long> {
    public SysTopicFc findById(Long id);
}
