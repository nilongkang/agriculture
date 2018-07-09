package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysCommentRepository extends JpaRepository<SysComment,Long> {
    public SysComment findById(Long id);
}
