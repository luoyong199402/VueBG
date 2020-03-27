package com.example.demo.service.info.impl;

import com.example.demo.config.security.authentication.VueUser;
import com.example.demo.dao.info.InfoDao;
import com.example.demo.entity.dos.info.InfoDO;
import com.example.demo.entity.dto.InfoDTO;
import com.example.demo.entity.query.InfoQuery;
import com.example.demo.entity.vo.InfoVO;
import com.example.demo.orika.OrikaBeanMapper;
import com.example.demo.orika.Page;
import com.example.demo.orika.PageConvertMapper;
import com.example.demo.service.info.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InfoServiceImpl implements InfoService {
	@Autowired
	private OrikaBeanMapper orikaBeanMapper;

	@Autowired
	private PageConvertMapper pageConvertMapper;

	@Autowired
	private InfoDao infoDao;

	@Override
	public InfoDTO addInfo(InfoDTO infoDTO, VueUser vueUser) {
		InfoDO infoDO = orikaBeanMapper.map(infoDTO, InfoDO.class);
		infoDO.setCreateUserId(vueUser.getId());
		infoDO.setCreateUserName(vueUser.getUsername());
		infoDO.setId(null);

		InfoDO saveInfo = infoDao.save(infoDO);
		return orikaBeanMapper.map(saveInfo, InfoDTO.class);
	}

	@Override
	public InfoDTO deleteInfoById(Long id) {
		Optional<InfoDO> infoDOOptional = infoDao.findById(id);
		infoDao.deleteById(id);
		InfoDTO infoDTO = orikaBeanMapper.map(infoDOOptional, InfoDTO.class);
		return infoDTO;
	}

	@Override
	public InfoDTO updateInfo(Long id, InfoDTO infoDTO) {
		Optional<InfoDO> infoDOOptional = infoDao.findById(id);

		InfoDO infoDO = infoDOOptional.get();
		infoDO.setContent(infoDTO.getContent());
		infoDO.setTitle(infoDTO.getTitle());
		infoDO.setCategoryId(infoDO.getCategoryId());

		infoDao.save(infoDO);
		return orikaBeanMapper.map(infoDOOptional, InfoDTO.class);
	}

	@Override
	public InfoDTO getInfoById(Long id) {
		Optional<InfoDO> infoDOOptional = infoDao.findById(id);
		InfoDTO retInfoDTO = orikaBeanMapper.map(infoDOOptional.get(), InfoDTO.class);
		return retInfoDTO;
	}


	@Override
	public Page<InfoVO> queryInfo(InfoQuery infoQuery, Pageable pageable) {
		Specification<InfoDO> specification = (Root<InfoDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicateList = new ArrayList<>();
			if (infoQuery.getId() != null && infoQuery.getId() != 0) {
				predicateList.add(cb.equal(root.<Long>get("id"), infoQuery.getId()));
			}

			if (StringUtils.isNotEmpty(infoQuery.getTitle())) {
				predicateList.add(cb.like(root.get("title"),  String.format("%%%s%%", infoQuery.getTitle())));
			}

			if (infoQuery.getCategoryId() != null && infoQuery.getCategoryId() != 0) {
				predicateList.add(cb.equal(root.<Boolean>get("categoryId"), infoQuery.getCategoryId()));
			}

			if (infoQuery.getCreateTimeStartTime() != null) {
				predicateList.add(cb.greaterThanOrEqualTo(root.<Date>get("createTime"), infoQuery.getCreateTimeStartTime()));
			}

			if (infoQuery.getCreateTimeEndTime() != null) {
				predicateList.add(cb.lessThanOrEqualTo(root.<Date>get("createTime"), infoQuery.getCreateTimeEndTime()));
			}

			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		};

		org.springframework.data.domain.Page<InfoDO> infoDOPage = infoDao.findAll(specification, pageable);
		Page<InfoVO> convertPage = pageConvertMapper.convert(infoDOPage, InfoVO.class);
		return convertPage;
	}
}
