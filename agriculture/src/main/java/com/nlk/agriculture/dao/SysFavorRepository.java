package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysFavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysFavorRepository extends JpaRepository<SysFavor, Long> {
    public SysFavor findByUsername(String username);
}
