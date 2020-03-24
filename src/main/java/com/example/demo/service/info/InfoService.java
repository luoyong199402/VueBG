package com.example.demo.service.info;

import com.example.demo.config.security.authentication.VueUser;
import com.example.demo.entity.dto.InfoDTO;
import org.springframework.stereotype.Service;


public interface InfoService {
	InfoDTO addInfo(InfoDTO infoDTO, VueUser vueUser);
}
