package com.example.demo.dao.authority;

import com.example.demo.entity.dos.authority.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<RoleDO, Long> {
}
