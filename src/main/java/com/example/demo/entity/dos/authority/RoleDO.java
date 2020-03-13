package com.example.demo.entity.dos.authority;

import com.example.demo.entity.dos.UserDO;
import lombok.*;
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
@Table(name = "authority_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Builder
/**
 * 角色信息
 */
public class RoleDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '角色名称'"
    )
    private String name;

    @Column(
            nullable = false,
            columnDefinition = "varchar(32) comment '角色编码'"
    )
    private String code;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "role_group_mapping",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")}
    )
    private Set<GroupDO> userGroupDOSet;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "role_user_mapping",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private Set<UserDO> userDOSet;

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
