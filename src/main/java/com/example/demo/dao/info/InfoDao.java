package com.example.demo.dao.info;

import com.example.demo.entity.dos.info.InfoCategoryDO;
import com.example.demo.entity.dos.info.InfoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InfoDao  extends JpaRepository<InfoDO, Long>, JpaSpecificationExecutor<InfoDO> {
}
