package com.ict.teamProject.notic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ict.teamProject.notic.dto.NoticDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;
@RestController

@CrossOrigin(origins = "http://localhost:3333")
public class NoticController {
	NoticService service;
	public NoticController(NoticService service) {
		this.service = service;
	}
	
	@GetMapping("/Notic/View.do") //조회
	public List<NoticDto> getAllList(@RequestParam String id){
		System.out.println("들어온 아이디:"+id);
		List<NoticDto> noticlist = service.findAllNotic(id);
		System.out.println("아웃풋 : "+noticlist);
		return noticlist;
	}
	
	@GetMapping("/Notic/Update.do")
	public void UpdateNotic(@RequestParam int trigger_pk){
		System.out.println("들어온 pk:"+trigger_pk);
		int affected = service.updateNotic(trigger_pk);
		System.out.println("아웃풋 : "+affected);
	}
	
	@GetMapping("/Notic/Delete.do")
	public void DeleteNotic(@RequestParam int trigger_pk){
		System.out.println("들어온 pk:"+trigger_pk);
		int affected = service.deleteNotic(trigger_pk);
		System.out.println("아웃풋 : "+affected);
	}
	
	@GetMapping("/Notic/AddAFMLYN")
	public int AddAFMLYN(@RequestParam int trigger_pk, int trigger_no, String yn){
		System.out.println("들어온 no:"+trigger_pk + " + " + trigger_no + " + " + yn);
		if("Y".equals(yn)){ //수락 시, 업데이트 처리
			int affected = service.afmlSaveY(trigger_no);
			service.deleteNotic(trigger_pk);
			System.out.println(affected);
			return affected;
		}else if("N".equals(yn)) { //거절 시 삭제,
			int affected = service.afmlDeleteN(trigger_no);
			System.out.println(affected);
			return affected;
		}
		return 3;
	}
}