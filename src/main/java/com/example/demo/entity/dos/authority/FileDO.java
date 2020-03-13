package com.example.demo.entity.dos.authority;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authority_file", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Builder
/**
 * 文件信息
 */
public class FileDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '文件编码'"
    )
    private String code;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '文件名称'"
    )
    private String name;

    @Column(
            nullable = false,
            columnDefinition = "varchar(1024) comment '文件路径'"
    )
    private String path;

    @ManyToMany(mappedBy = "fileDOList")
    private List<AuthorityDO> authorityDOList;
}
