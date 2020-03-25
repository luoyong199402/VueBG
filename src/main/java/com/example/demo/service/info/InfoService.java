package com.example.demo.service.info;

import com.example.demo.config.security.authentication.VueUser;
import com.example.demo.entity.dto.InfoDTO;
import com.example.demo.entity.query.InfoQuery;
import com.example.demo.entity.vo.InfoVO;
import com.example.demo.orika.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface InfoService {
	InfoDTO addInfo(InfoDTO infoDTO, VueUser vueUser);

	InfoDTO deleteInfoById(Long id);

	InfoDTO updateInfo(Long id, InfoDTO infoDTO);

	InfoDTO getInfoById(Long id);

	Page<InfoVO> queryInfo(InfoQuery infoQuery, Pageable pageable);
}
