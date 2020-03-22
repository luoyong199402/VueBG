package com.example.demo.service.info.impl;

import com.example.demo.dao.info.InfoCategoryDao;
import com.example.demo.entity.dos.info.InfoCategoryDO;
import com.example.demo.entity.dto.InfoCategoryDTO;
import com.example.demo.exception.info.InfoCategoryNotFoundException;
import com.example.demo.exception.info.InfoCategoryParentNotFoundException;
import com.example.demo.exception.info.InfoCategoryRepeatException;
import com.example.demo.orika.OrikaBeanMapper;
import com.example.demo.orika.PageConvertMapper;
import com.example.demo.service.info.InfoCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InfoCategoryServiceImpl implements InfoCategoryService {

    @Autowired
    private InfoCategoryDao infoCategoryDao;

    @Autowired
    private OrikaBeanMapper orikaBeanMapper;

    @Autowired
    private PageConvertMapper pageConvertMapper;

    @Override
    public InfoCategoryDTO addInfoCategory(InfoCategoryDTO categoryDTO) {
        if (infoCategoryDao.existsByParentIdAndCode(categoryDTO.getParentId(), categoryDTO.getCode())) {
            throw new InfoCategoryRepeatException();
        }

        if (categoryDTO.getParentId() != null &&  !infoCategoryDao.existsById(categoryDTO.getParentId())) {
            throw new InfoCategoryParentNotFoundException();
        }


        InfoCategoryDO infoCategoryDO = orikaBeanMapper.map(categoryDTO, InfoCategoryDO.class);
        infoCategoryDO.setId(null);
        // 设置默认值
        if (infoCategoryDO.getParentId() == null) {
            infoCategoryDO.setLevel(1);
        }
        InfoCategoryDO infoCategoryDo = infoCategoryDao.save(infoCategoryDO);
        orikaBeanMapper.map(infoCategoryDo, categoryDTO);
        return categoryDTO;
    }

    @Override
    @Transactional
    public InfoCategoryDTO deleteInfoCategory(Long id) {
        Optional<InfoCategoryDO> optionalInfoCategoryDO = infoCategoryDao.findById(id);
        optionalInfoCategoryDO.orElseThrow(() -> new InfoCategoryNotFoundException());

        // 如果是一级分类，  需要删除其子节点
        if (optionalInfoCategoryDO.get().getLevel() == 1) {
            infoCategoryDao.deleteByParentId(id);
        }

        infoCategoryDao.deleteById(id);
        return orikaBeanMapper.map(optionalInfoCategoryDO.get(), InfoCategoryDTO.class);
    }

    @Override
    public List<InfoCategoryDTO> getInfoCategoryByLevel(Integer level) {
        List<InfoCategoryDO> infoCategoryDOS = infoCategoryDao.findByLevelOrderBySort(level);
        return orikaBeanMapper.mapAsList(infoCategoryDOS, InfoCategoryDTO.class);
    }

    @Override
    public List<InfoCategoryDTO> getInfoCategoryByParentId(Long parentId) {
        List<InfoCategoryDO> infoCategoryDOS = infoCategoryDao.findByParentIdOrderBySort(parentId);
        return orikaBeanMapper.mapAsList(infoCategoryDOS, InfoCategoryDTO.class);
    }
}
