package com.example.demo.entity.dos.authority;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authority_operation", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Builder
/**
 * 功能操作信息
 */
public class FunctionalOperationDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '功能操作编码'"
    )
    private String code;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '功能操作名称'"
    )
    private String name;

    @ManyToMany(mappedBy = "functionalOperationDOList")
    private List<AuthorityDO> authorityDOList;
}
