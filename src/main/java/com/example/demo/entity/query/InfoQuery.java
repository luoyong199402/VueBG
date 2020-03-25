package com.example.demo.entity.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoQuery {
	private Long id;
	private Long categoryId;
	private Date createTimeStartTime;
	private Date createTimeEndTime;
	private String title;
}
