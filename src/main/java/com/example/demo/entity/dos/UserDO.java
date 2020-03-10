package com.example.demo.entity.dos;

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
@Table(name = "vue_user", uniqueConstraints = {@UniqueConstraint(columnNames= {"username"})})
@Builder
public class UserDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
			nullable = false,
			columnDefinition = "varchar(128) comment '用户名'"
	)
	private String username;

	@Column(
			nullable = false,
			columnDefinition = "varchar(512) comment '密码'"
	)
	private String password;

	@Column(
			nullable = false,
			columnDefinition = "tinyint unsigned comment '用户是否过期'"
	)
	private Boolean accountNonExpired;

	@Column(
			nullable = false,
			columnDefinition = "tinyint unsigned comment '用户是否被锁'"
	)
	private Boolean accountNonLocked;


	@Column(
			nullable = false,
			columnDefinition = "tinyint unsigned comment '用户是否启用'"
	)
	private Boolean enabled;

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
