package com.example.demo.dao.info;

import com.example.demo.entity.dos.info.InfoCategoryDO;
import com.example.demo.entity.dos.info.InfoDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoDao  extends JpaRepository<InfoDO, Long> {
}
