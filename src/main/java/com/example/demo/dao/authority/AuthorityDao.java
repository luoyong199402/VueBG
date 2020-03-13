package com.example.demo.dao.authority;

import com.example.demo.entity.dos.authority.AuthorityDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityDao extends JpaRepository<AuthorityDO, Long> {
}
