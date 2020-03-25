package com.example.demo.service.info;

import com.example.demo.entity.dto.InfoCategoryDTO;

import java.util.List;

public interface InfoCategoryService {
    InfoCategoryDTO addInfoCategory(InfoCategoryDTO categoryDTO);

    InfoCategoryDTO deleteInfoCategory(Long id);

    InfoCategoryDTO updateInfoCategory(Long id, InfoCategoryDTO categoryDTO);

    List<InfoCategoryDTO> getInfoCategoryByLevel(Integer level);

    List<InfoCategoryDTO> getInfoCategoryByParentId(Long parentId);

    InfoCategoryDTO getInfoCategoryById(Long id);
}
