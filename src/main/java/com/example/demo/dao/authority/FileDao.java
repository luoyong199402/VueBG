package com.example.demo.dao.authority;

import com.example.demo.entity.dos.authority.FileDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDao extends JpaRepository<FileDO, Long> {
}
