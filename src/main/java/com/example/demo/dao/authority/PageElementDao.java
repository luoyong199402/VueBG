package com.example.demo.dao.authority;

import com.example.demo.entity.dos.authority.PageElementDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageElementDao extends JpaRepository<PageElementDO, Long> {
}
