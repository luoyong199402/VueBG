package com.example.demo.entity.dos.authority;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authority_authority", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Builder
/**
 * 权限信息
 */
public class AuthorityDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '权限编码'"
    )
    private String code;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '权限名称'"
    )
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "authority_menu_mapping",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")}
    )
    private List<MenuDO> menuDOList;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "authority_page_element_mapping",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "page_element_id", referencedColumnName = "id")}
    )
    private List<PageElementDO> pageElementDOList;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "authority_file_mapping",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id", referencedColumnName = "id")}
    )
    private List<FileDO> fileDOList;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "authority_functional_operation_mapping",
            joinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "functional_operation_id", referencedColumnName = "id")}
    )
    private List<FunctionalOperationDO> functionalOperationDOList;
}
