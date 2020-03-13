package com.example.demo.entity.dos.authority;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 菜单信息
 */
@Table(name = "authority_menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Builder
public class MenuDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '菜单编码'"
    )
    private String code;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '菜单名称'"
    )
    private String name;

    @Column(
            nullable = false,
            columnDefinition = "varchar(2048) comment '菜单url'"
    )
    private String url;

    @Column(
            nullable = false,
            columnDefinition = "bigint comment '父菜单id'"
    )
    private String parentId;

    @ManyToMany(mappedBy = "menuDOList")
    private List<AuthorityDO> authorityDOList;
}
