package com.example.demo.dao.authority;

import com.example.demo.entity.dos.authority.FunctionalOperationDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionalOperationDao extends JpaRepository<FunctionalOperationDO, Long> {
}
