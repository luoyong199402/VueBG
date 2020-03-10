package com.example.demo.dao;

import com.example.demo.entity.dos.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao  extends JpaRepository<UserDO, Long> {
	Optional<UserDO> findByUsername(String username);
}
