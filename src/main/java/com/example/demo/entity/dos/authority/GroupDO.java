package com.example.demo.entity.dos.authority;

import com.example.demo.entity.dos.UserDO;
import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authority_group", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Builder
/**
 * 组信息
 */
public class GroupDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '组名称'"
    )
    private String name;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '组编码'"
    )
    private String code;

    @Column(
            columnDefinition = "bigint comment '父组id'"
    )
    private String parentGroupId;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "group_user_mapping",
            joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private Set<UserDO> userDoSet;

    @ManyToMany(mappedBy = "userDOSet")
    private Set<RoleDO> roleDoSet;

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
