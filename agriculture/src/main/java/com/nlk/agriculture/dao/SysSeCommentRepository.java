package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysSecondComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysSeCommentRepository extends JpaRepository<SysSecondComment,Long> {
    public SysSecondComment findById(Long id);
}
