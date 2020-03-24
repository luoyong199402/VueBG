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
@Table(name = "info")
@Builder
public class InfoDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
			nullable = false,
			columnDefinition = "varchar(128) comment '标题'"
	)
	private String title;

	@Column(
			nullable = false,
			columnDefinition = "varchar(5096) comment '内容'"
	)
	private String content;

	@Column(
			nullable = false,
			columnDefinition = "Long comment '分类id'"
	)
	private Long categoryId;

	@Column(
			nullable = false,
			columnDefinition = "Long comment '创建用户id'"
	)
	private Long createUserId;

	@Column(
			nullable = false,
			columnDefinition = "varchar(128) comment '创建用户名称'"
	)
	private String createUserName;

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
