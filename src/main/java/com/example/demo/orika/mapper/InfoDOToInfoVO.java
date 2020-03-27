package com.example.demo.orika.mapper;

import com.example.demo.entity.dos.info.InfoDO;
import com.example.demo.entity.dto.InfoCategoryDTO;
import com.example.demo.entity.vo.InfoVO;
import com.example.demo.service.info.InfoCategoryService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InfoDOToInfoVO extends CustomMapper<InfoDO, InfoVO> {
	@Autowired
	private InfoCategoryService infoCategoryService;

	@Override
	public void mapAtoB(InfoDO infoDO, InfoVO infoVO, MappingContext context) {
		InfoCategoryDTO infoCategoryDTO = null;
		try {
			infoCategoryDTO = infoCategoryService.getInfoCategoryById(infoDO.getCategoryId());
		} catch (Exception e) {
			infoCategoryDTO = new InfoCategoryDTO();
			log.warn(e.toString());
		}

		infoVO.setId(infoDO.getId());
		infoVO.setCategoryId(infoDO.getCategoryId());
		infoVO.setCategoryName(infoCategoryDTO.getName());
		infoVO.setCreateTime(infoDO.getCreateTime().toString());
		infoVO.setCreateUserId(infoDO.getCreateUserId());
		infoVO.setCreateUserName(infoDO.getCreateUserName());
		infoVO.setTitle(infoDO.getTitle());
	}
}
