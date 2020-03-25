package com.example.demo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoVO {
	private Long id;
	private Long categoryId;
	private String categoryName;
	private String title;
	private String createTime;
	private Long createUserId;
	private String createUserName;
}
