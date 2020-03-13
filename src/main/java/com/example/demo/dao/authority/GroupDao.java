package com.example.demo.dao.authority;

import com.example.demo.entity.dos.authority.GroupDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao extends JpaRepository<GroupDO, Long> {
}
