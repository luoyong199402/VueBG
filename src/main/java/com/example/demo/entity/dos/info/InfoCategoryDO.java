package com.example.demo.entity.dos.info;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "info_category", uniqueConstraints = {@UniqueConstraint(columnNames= {"code", "level"})})
@Builder
public class InfoCategoryDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "varchar(128) comment '分类名称'"
    )
    private String name;

    @Column(
            nullable = false,
            columnDefinition = "varchar(128) comment '编码'"
    )
    private String code;

    @Column(
            nullable = false,
            columnDefinition = "Integer comment '排序号'"
    )
    private Integer sort;

    /**
     * 父分类id
     */
    @Column(
            nullable = true,
            columnDefinition = "Long comment '父节点id'"
    )
    private Long parentId;

    /**
     * 所属层级
     */
    @Column(
            nullable = false,
            columnDefinition = "Long comment '父节点id'"
    )
    private Integer level;

    @CreationTimestamp
    @Column(
            nullable = false,
            updatable = false,
            columnDefinition = "timestamp comment '创建时间'"
    )
    private Date createTime;

    @UpdateTimestamp
    @Column(
            nullable = false,
            columnDefinition = "timestamp comment '更新时间'"
    )
    private Date modifyTime;


}
