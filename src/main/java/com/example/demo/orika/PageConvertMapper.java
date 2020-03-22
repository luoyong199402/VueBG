package com.example.demo.orika;

import java.util.List;

public class PageConvertMapper {
	private OrikaBeanMapper orikaBeanMapper;

	public PageConvertMapper(OrikaBeanMapper orikaBeanMapper) {
		this.orikaBeanMapper = orikaBeanMapper;
	}

	public <T> Page<T> convert(org.springframework.data.domain.Page page, Class<T> tClass) {
		List<T> list = orikaBeanMapper.mapAsList(page.getContent(), tClass);
		Page<T> myPage = new Page<>(list, page.getTotalElements(), page.getNumber(), page.getSize());
		return myPage;
	}
}
