package com.example.demo.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoCategoryDTO {
    /**
     * 主键信息
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 排序好
     */
    private Integer sort;

    /**
     * 父分类id
     */
    private Long parentId;

    /**
     * 所属层级
     */
    private Integer level;
}
