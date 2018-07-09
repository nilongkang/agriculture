package com.nlk.agriculture.dao;

import com.nlk.agriculture.domain.SysVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysVideoRepository extends JpaRepository<SysVideo,Long> {
    public List<SysVideo> findByUsername(String username);
    public List<SysVideo> findAll();
    @Query("select sysvideo from SysVideo sysvideo where video_kind like ?1")
    public List<SysVideo> findByVideo_kind(String video_kind);
    public SysVideo findById(Long id);
}
