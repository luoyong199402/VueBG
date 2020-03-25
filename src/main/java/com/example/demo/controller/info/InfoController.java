package com.example.demo.controller.info;

import com.example.demo.config.security.authentication.VueUser;
import com.example.demo.entity.dos.info.InfoDO;
import com.example.demo.entity.dto.InfoCategoryDTO;
import com.example.demo.entity.dto.InfoDTO;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.query.InfoQuery;
import com.example.demo.entity.vo.InfoVO;
import com.example.demo.orika.Page;
import com.example.demo.service.info.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/info/")
public class InfoController {
	@Autowired
	private InfoService infoService;

	@PostMapping
	public InfoDTO addInfo(@RequestBody InfoDTO infoDTO, Authentication authentication) {
		return infoService.addInfo(infoDTO, (VueUser) authentication.getPrincipal());
	}

	@DeleteMapping("/{id}")
	public InfoDTO deleteInfoById(@PathVariable Long id) {
		return infoService.deleteInfoById(id);
	}

	@PutMapping("/{id}")
	public InfoDTO updateInfo(@PathVariable Long id, @RequestBody InfoDTO infoDTO) {
		return infoService.updateInfo(id, infoDTO);
	}

	@GetMapping("/{id}")
	public InfoDTO getInfoById(@PathVariable Long id) {
		return infoService.getInfoById(id);
	}

	@GetMapping("/page")
	public Page<InfoVO> queryUser(InfoQuery infoQuery,
								  @PageableDefault(
										   value = 20,
										   sort = { "createTime" },
										   direction = Sort.Direction.DESC) Pageable pageable) {
		return infoService.queryInfo(infoQuery, pageable);
	}
}
