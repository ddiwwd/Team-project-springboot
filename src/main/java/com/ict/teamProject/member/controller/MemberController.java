package com.ict.teamProject.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ict.teamProject.comm.CommService;
import com.ict.teamProject.mate_room.service.MRDto;
import com.ict.teamProject.member.service.MemberDto;
import com.ict.teamProject.member.service.MemberManageDto;
import com.ict.teamProject.member.service.MemberService;

import oracle.jdbc.OracleDatabaseException;

import org.springframework.ui.Model;


@CrossOrigin(origins = "http://localhost:3333")
@RestController
public class MemberController {

	private MemberService service;
	private CommService commService;
	
	
	@CrossOrigin(origins = "http://localhost:3333")
	@RequestMapping(value = "/user/updateSocialUser",  method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
	public int updateSocialUser(@RequestBody MemberDto dto){
	    int affected = service.updateSocialUser(dto);
	    
	    System.out.println("하? 여기 들어와 지니?"+dto);
	    return affected;
	}
	
	public MemberController(MemberService service, CommService commService) {
		this.service=service;
		this.commService = commService;
	}
	
	
	@RequestMapping(value = "/user/View", method = {RequestMethod.GET,RequestMethod.POST})
	public MemberDto view(@RequestParam String id){
		MemberDto memberdata = service.selectdata(id);
		System.out.println("user/View"+memberdata);
		return memberdata;
	}
	@RequestMapping(value = "/user/Edit", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
	public int edit(@RequestParam String id, String colname, String newcolval){
		int affected = service.updatedata(id, colname, newcolval);
		
		System.out.println("여기 들어와 지니?");
		return affected;
	}
	
	
	@RequestMapping(value = "/usercheck", method = {RequestMethod.GET,RequestMethod.POST,})
	public int usercheck(@RequestParam String id, String pwd) {
		System.out.println("chk");
		return service.logincheck(id, pwd);
	}
	
	
	@RequestMapping(value = "/searchPoint", method = {RequestMethod.GET})
	public Map searchPoint(@RequestParam String id) {
		System.out.println("searchPoint");
		return service.searchPoint(id);
	}
	
	@CrossOrigin(origins = "http://localhost:3333")
	@RequestMapping(value = "/getMemberById", method = RequestMethod.GET)
	public MemberDto getMemberById(@RequestParam String id) {
	    return service.getMemberById(id);
	}
	@CrossOrigin(origins = "http://localhost:3333")
	@RequestMapping(value = "/check-userID", method = RequestMethod.POST)
	public MemberDto checkUserID(@RequestBody Map<String, String> params) {
	    String name = params.get("name");
	    String b_day = params.get("b_day");
	    System.out.println("name"+name);
	    System.out.println("b_day"+b_day);
	    return service.getMemberByNameAndBday(name, b_day);
	}
	@CrossOrigin(origins = "http://localhost:3333")
	@RequestMapping(value = "/check-userPWD", method = RequestMethod.POST)
	public MemberDto checkUserPWD(@RequestBody Map<String, String> params) {
	    String id = params.get("id");
	    System.out.println("id: " + id);
	    return service.getMemberByIdAndEmail(id);
	}
	//FMC 토큰을 DB에 저장
	@PostMapping("/fmctoken")
	public void saveFMCToken(@RequestBody Map map) {
		service.saveFMCToken(map);
	}
	
	@CrossOrigin(origins = "http://localhost:3333")
	@RequestMapping(value = "/update-password", method = RequestMethod.POST)
	public int updatePWD(@RequestBody Map<String, String> params) {
	    String id = params.get("id");
	    String pwd = params.get("pwd");
	    System.out.println("id: " + id);
	    return service.updateMemberPwd(id,pwd);
	}
	
	//DB에 저장된 FMC 토큰 불러오기
	@GetMapping("/get/fmctoken")
	public String findFMCToken(@RequestParam String id) {
		return service.findFMCTokenById(id);
	}
	@CrossOrigin(origins = "http://localhost:3333")
	@RequestMapping(value = "/getUserAddress", method = {RequestMethod.GET,RequestMethod.POST,})
	public Map getUserAddress(@RequestParam String id) {
		System.out.println("찾을 유저:"+id);
		return service.getUserAddress(id);
	}
	
	//모든 유저의 데이타 가져오기]
	@GetMapping("/user/findAllUser")
	@ResponseBody
	public List findAllUser() {
	    List<Map<String, Object>> userList = service.findAllUser();
	    return userList;
	}/////
	
	@GetMapping("/user/relationship")
	public Map<String, Integer> findUserFMSById(String id){
		Map<String, Integer> relation = new HashMap<String, Integer>();
		relation.put("f", commService.findFMSnumById(id, "f"));
		relation.put("m", commService.findFMSnumById(id, "m"));
		relation.put("s", commService.findFMSnumById(id, "s"));
		return relation;
	}
	
	@GetMapping("/manage/member")
	public List<MemberManageDto> findAllComplainedUsers() {
		return service.findAllComplainedUsers();
	}
	//블랙리스트에서 유저 삭제
	@DeleteMapping("/manage/complained/delete")
	public void deleteUserFromComplainList(@RequestBody Map map) {
		System.out.println("요청이 들어왔는지 확인:" + String.valueOf(map.get("id")));
		service.deleteUserFromComplainList(String.valueOf(map.get("id")));
	}
	@PostMapping("/manage/complained/create")
	public void addUserToComplainList(@RequestBody Map map) {
		System.out.println("인자로 받은 값:"+ map);
		List<MemberManageDto> dtos = new ArrayList<MemberManageDto>();
		for(String cl_id:(ArrayList<String>)map.get("cl_id")) {
			MemberManageDto dto = new MemberManageDto().builder()
					.id(String.valueOf(map.get("id")))
					.cl_id(cl_id)
					.cl_reason(String.valueOf(map.get("cl_reason")))
					.build();
			dtos.add(dto);
		}
		service.addUserToComplainList(dtos);
	}
	
	//기본 장소
	@GetMapping("/defaultArea")
	public String defaultArea(@RequestParam String id) {
		String defaultarea = service.defaultArea(id);
		return defaultarea;
	}
}
