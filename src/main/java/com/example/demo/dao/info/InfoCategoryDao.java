package com.example.demo.dao.info;

import com.example.demo.entity.dos.info.InfoCategoryDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoCategoryDao extends JpaRepository<InfoCategoryDO, Long> {
    List<InfoCategoryDO> findByLevelOrderBySort(Integer level);

    List<InfoCategoryDO> findByParentIdOrderBySort(Long parentId);

    boolean existsByParentIdAndCode(Long parentId, String code);

    void deleteByParentId(Long parentId);
}
