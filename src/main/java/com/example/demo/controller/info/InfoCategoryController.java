package com.example.demo.controller.info;

import com.example.demo.entity.dto.InfoCategoryDTO;
import com.example.demo.service.info.InfoCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/info/category")
public class InfoCategoryController {

    @Autowired
    private InfoCategoryService infoCategoryService;

    @PostMapping
    public InfoCategoryDTO addInfoCategory(@RequestBody InfoCategoryDTO infoCategoryDTO) {
        return infoCategoryService.addInfoCategory(infoCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public InfoCategoryDTO deleteInfoCategory(@PathVariable Long id) {
        return infoCategoryService.deleteInfoCategory(id);
    }

    @PutMapping("/{id}")
    public InfoCategoryDTO deleteInfoCategory(@PathVariable Long id, @RequestBody InfoCategoryDTO infoCategoryDTO) {
        return infoCategoryService.updateInfoCategory(id, infoCategoryDTO);
    }

    @GetMapping("/level/{level}")
    public List<InfoCategoryDTO> getInfoCategoryListByLevel(@PathVariable Integer level) {
        return infoCategoryService.getInfoCategoryByLevel(level);
    }

    @GetMapping("/parentId/{parentId}")
    public List<InfoCategoryDTO> getInfoCategoryListByParentId(@PathVariable Long parentId) {
        return infoCategoryService.getInfoCategoryByParentId(parentId);
    }
}
