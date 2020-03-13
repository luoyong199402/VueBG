package com.example.demo.dao.authority;

import com.example.demo.entity.dos.authority.MenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao extends JpaRepository<MenuDO, Long> {
}
