package com.ict.teamProject.bbs.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSService;
import com.ict.teamProject.bbs.service.BBSUsersProfileDto;
import com.ict.teamProject.bbs.service.LikesDto;
import com.ict.teamProject.comm.CommService;
import com.ict.teamProject.comm.dto.SubscribeToDto;
import com.ict.teamProject.command.FileUtils;
import com.ict.teamProject.s3.S3UploadService;



@Controller
@RequestMapping("/bbs")
@RestController

@CrossOrigin(origins = "http://localhost:3333")
public class BBSController {
	
	//서비스 주입
	@Autowired
	private BBSService<BBSDto> service;
	
	@Autowired
	private S3UploadService s3UploadService;
	
	@Autowired
	private CommService commservice;
	
	
	//입력처리]
	@PostMapping("/Write.do")
	@ResponseBody
	public int writeOk(
			@RequestParam Map map,@RequestParam(name="files", required=false) MultipartFile[] files, @RequestParam(name="ciu", required=false) String ciuJson) throws JsonMappingException, JsonProcessingException {
		
		System.out.println("머야 왜안돼!!");
		System.out.println(map.get("id"));
		System.out.println(map.get("content"));
		System.out.println(map.get("hashTag"));
		System.out.println(map.get("type"));
		System.out.println(map.get("disclosureYN"));
		int affected = 0;
	    
		map= service.insert(map);
		if (ciuJson != null) {
			 // ciuJson을 파싱하여 List<Map<String, String>> 형태로 변환
		    ObjectMapper objectMapper = new ObjectMapper();
		    JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class);
		    List<Map<String, String>> ciu = objectMapper.readValue(ciuJson, type);
	
		    // ciu를 사용하여 필요한 처리 수행
		    for (Map<String, String> item : ciu) {
		        String url = item.get("url");
		        File file = new File(url);
		        String name = file.getName();
	    		map.put("urls", url);
	    		map.put("names", name);
		        System.out.println(name);
		        System.out.println(url);
		        affected += service.insertFile(map);

		    }
		}
	
	    if (files != null) {
		    try {
		        for (MultipartFile file : files) {
		            // S3에 업로드하고 URL을 가져옴
		            String imageUrl = s3UploadService.saveFile(file);
		            String filename = file.getOriginalFilename();

		            map.put("urls", imageUrl);
		            map.put("names", filename);

		            affected += service.insertFile(map);
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		        return 0;
		    }
	    }
		if(affected==0) {//입력 실패
			return affected;
		}
		return affected;
	}/////
	

	@RequestMapping(value="/ViewOne.do",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public BBSDto viewOne(@RequestParam String bno) {
		int bnoInt = Integer.parseInt(bno);
		System.out.println("상세보기의 NO:"+bnoInt);
		//서비스 호출
		BBSDto record= service.selectOne(bnoInt);
		//줄바꿈
		record.setContent(record.getContent().replace("\r\n", "<br/>"));
		//뷰정보 반환
		return record;
	}
	
	@GetMapping("/ViewMy.do")
	@ResponseBody
	public List<BBSDto> viewMy(@RequestParam String id) {
		System.out.println("ID:"+id);
		//서비스 호출
		List<BBSDto> records= service.selectMy(id);
		//줄바꿈
	    for (BBSDto record : records) {
	        int bno = record.getBno();
	        record.setContent(record.getContent().replace("\r\n", "<br/>"));
	        List<String> files = service.selectFiles(bno);
	        record.setFiles(files);  // 게시글에 파일들을 추가
	        System.out.println("files:"+record.getFiles());
	    }
		return records;
	}
	
	//게시물 전체 뿌려주기
	@RequestMapping ("/List.do")
	public List view(@RequestBody Map map) {
	    List<Integer> types = new ArrayList<>();
	    System.out.println("selectedItems---"+map.get("selectedItems").toString());
	    List<String> selectedItems = (List<String>)map.get("selectedItems");
	    System.out.println("selectedItems---"+selectedItems);
	    if(selectedItems != null) {
	        for(String item : selectedItems) {
	            switch(item) {
	                case "식단":
	                    types.add(1);
	                    break;
	                case "운동":
	                    types.add(2);
	                    break;
	                case "심리":
	                    types.add(4);
	                    break;
	                default:
	                    break;
	            }
	        }
	    }
	    if(types.isEmpty()) {
	        types.add(0);
	    }
	    map.put("types", types);
	    System.out.println("types----"+map.get("types"));
	    //서비스 호출
	    List<BBSDto> records = service.selectAll(map);
	    System.out.println("여기야????");
	    //구독목록
	    List<String> subs = new ArrayList<>();
	    if(map.get("id") != null) {
	    	System.out.println("map.get(id):"+map.get("id"));
	    	for(SubscribeToDto dto : commservice.findAllSubToById(String.valueOf(map.get("id")))) {
	    		subs.add(dto.getSubscribe_id());
	    	}
	    	System.out.println("subs:"+subs.size());
	    }
	    System.out.println("records:"+records);
	    for (BBSDto record : records) {
	        int bno = record.getBno();
	        System.out.println("bno:"+bno);
	        List<String> files = service.selectFiles(bno);
	        record.setFiles(files);  // 게시글에 파일들을 추가
	        record.setContent(record.getContent().replace("\r\n", "<br/>"));
	        if(subs != null) {
	        	System.out.println("record.getId()"+record.getId());
	        	int subToFlag = subs.contains(String.valueOf(record.getId())) ? 1 : 0;
		        record.setIsSubto(subToFlag);
	        }
	        record.setProfilepath(service.findProfilePathById(record.getId()));
	        System.out.println("files:"+record.getFiles());
	        System.out.println("record.likes()"+record.getLikes());
	        System.out.println("record.type()"+record.getType());
	        System.out.println("record.getIsSubto()"+record.getIsSubto());
	    }
	    
		return records;
	}
	
	//수정하기
	@PostMapping("/Edit.do")
	public int edit(@RequestBody Map<String, Object> map) {
		int affected=0;
		BBSDto record = null;
		System.out.println("데이타 넘어오냐??"+map);
        int bno = Integer.parseInt(map.get("bno").toString());
        System.out.println("글번호~~~:"+bno);
        // 기존 레코드
        record = service.selectOne(bno);
        System.out.println("record.getDisclosureYN():"+record.getDisclosureYN());
        System.out.println("record.getHashTag():"+record.getHashTag());
        System.out.println("record.getContent():"+record.getContent());
	    // map에 있는 값만 변경.
        
        if (map.get("content") != null && !map.get("content").toString().isEmpty()) {
            record.setContent(map.get("content").toString());
            System.out.println("getContent()::"+record.getContent());
        }
        if (map.get("disclosureYN") != null && !map.get("disclosureYN").toString().isEmpty()) {
            record.setDisclosureYN(map.get("disclosureYN").toString().charAt(0));
            System.out.println("getDisclosureYN()::"+record.getDisclosureYN());
        }
        if (map.get("hashTag") != null && !map.get("hashTag").toString().isEmpty()) {
            record.setHashTag(map.get("hashTag").toString());
            System.out.println("getHashTag()::"+record.getHashTag());
        }

        if(record.getHashTag() == null) record.setHashTag("");
        if(record.getContent() == null) record.setContent("");
	    System.out.println("=============수정 후?=======");
        System.out.println("record.getDisclosureYN():"+record.getDisclosureYN());
        System.out.println("record.getHashTag():"+record.getHashTag());
        System.out.println("record.getContent():"+record.getContent());
	    // 변경된 레코드를 업데이트.
	    affected = service.update(record);				
	    return affected;
	}/////
	
	//삭제하기
	@GetMapping("/{bno}/Delete.do")
	public ResponseEntity delete(@PathVariable String bno) {
		try {
	        int bnoInt = Integer.parseInt(bno);
	        int affected = 0;
	        List<String> files = service.selectFiles(bnoInt);
	        
	        String baseDirectory = "E:/images/";
	        String imageDirectory = "src/main/resources/static/images/";
	        
	        // files에 값이 있으면 해당 파일들을 삭제
	        if (files != null && !files.isEmpty()) {
	            for (String fileUrl : files) {
	                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	                Path filePath = Paths.get(baseDirectory + fileName);
	                Path imagePath = Paths.get(imageDirectory + fileName);
	                try {
	                    Files.deleteIfExists(filePath);
	                    Files.deleteIfExists(imagePath);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        System.out.println("bnoInt:"+bnoInt);
	        service.deleteFiles(bnoInt);
	        affected += service.deleteBBS(bnoInt);
	        System.out.println("affected2:"+affected);
	        return ResponseEntity.ok(String.valueOf(affected));
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body("bno: " + bno);
	    }
	}
	
	
	@PostMapping("/userProfile")
	public List<BBSUsersProfileDto> getAllUsersById(@RequestBody Map<String,List<String>> map){
		System.out.println("값이 요청됨:"+map.get("ids"));
		List<BBSUsersProfileDto> dtos = new ArrayList<BBSUsersProfileDto>();
		int flag = 0;
		Map<String, String> param = new HashMap<>();
		for (String id : map.get("ids")) {
			if (flag==0) {
				param.put("userId", id);
			}
			else {
				param.put("otherId", id);
				BBSUsersProfileDto tempDto = new BBSUsersProfileDto().builder()
										.id(id)
										.isFriend(service.findIsFriend(param))
										.isSubTo(service.findIsSubto(param))
										.profilePath(service.findProfilePathById(id))
										.build();
				System.out.println(String.format("요청보낸 아이디: %s, 요청받는 아이디: %s, 친구 수: %s", param.get("userId"), id, service.findIsFriend(param)));
				dtos.add(tempDto);
			}
			flag++;
		}
		return dtos;
	}
	
	//좋아요 얻어오기
	@PostMapping("/likes.do")
	public Map likes(@RequestBody Map map) {
		LikesDto likes = new LikesDto();
		String id = map.get("id").toString();
		System.out.println("id:-- "+id);
		String state = map.get("isLiked").toString();
		System.out.println("state:-- "+state);
		int bno = Integer.parseInt(map.get("bno").toString());
		System.out.println("bno:-- "+bno);
		int cno = 0;
		if(map.get("cno") != null && !map.get("cno").toString().isEmpty()) {
			cno = Integer.parseInt(map.get("cno").toString());
			System.out.println("cno:-- "+cno);
		}
		likes.setBno(bno);
		likes.setId(id);
	    if (state.equals("false")) {
	        service.setLikes(likes);
	        System.out.println("좋아요 누른 사람~~"+likes);
	    } else if (state.equals("true")) {
	        service.deleteLikes(likes);
	        System.out.println("좋아요 해제한 사람~~"+likes);
	    }
	    int likesnum = service.findLikes(bno);
	    List likesId = new ArrayList();
	    likesId = service.whereLikes(bno);
	    Map like = new HashMap();
	    like.put("likesnum", likesnum);
	    like.put("likes", likesId);
	    
		return like;
	}
	
	//좋아요 얻어오기
	@GetMapping("/likesPro.do")
	public Map likesPro(@RequestParam("bno") int bno) {
	    Map likeMap = new HashMap();
	    List<String> userIds = service.findUserByLike(bno);
	    List<String> userProfiles = new ArrayList<>();

	    if (userIds != null && !userIds.isEmpty()) {
	        for (String userId : userIds) {
	    	    System.out.println("에러위치 22222"+userId);
	            String userProfile = service.findUserProfileByLike(userId);
	            userProfiles.add(userProfile);
	        }
	    }
	    System.out.println("에러위치 33333");
	    likeMap.put("likesPro", userProfiles);
	    likeMap.put("likes", userIds);
	    return likeMap;
	}
	
	
	//관리자 페이지 게시글 전체 뿌려주기
	@RequestMapping ("/allList.do")
	public List allView(@RequestBody Map map) {
	    List<Integer> types = new ArrayList<>();
	    System.out.println("selectedItems---"+map.get("selectedItems").toString());
	    List<String> selectedItems = (List<String>)map.get("selectedItems");
	    System.out.println("selectedItems---"+selectedItems);
	    if(selectedItems != null) {
	        for(String item : selectedItems) {
	            switch(item) {
	                case "식단":
	                    types.add(1);
	                    break;
	                case "운동":
	                    types.add(2);
	                    break;
	                case "심리":
	                    types.add(4);
	                    break;
	                default:
	                    break;
	            }
	        }
	    }
	    if(types.isEmpty()) {
	        types.add(0);
	    }
	    map.put("types", types);
	    System.out.println("types----"+map.get("types"));
	    //서비스 호출
	    List<BBSDto> records = service.findAllList(map);
	    //구독목록
	    List<String> subs = new ArrayList<>();
	    if(map.get("id") != null) {
	    	System.out.println("map.get(id):"+map.get("id"));
	    	for(SubscribeToDto dto : commservice.findAllSubToById(String.valueOf(map.get("id")))) {
	    		subs.add(dto.getSubscribe_id());
	    	}
	    	System.out.println("subs:"+subs.size());
	    }
	    System.out.println("records:"+records);
	    for (BBSDto record : records) {
	        int bno = record.getBno();
	        System.out.println("bno:"+bno);
	        List<String> files = service.selectFiles(bno);
	        record.setFiles(files);  // 게시글에 파일들을 추가
	        record.setContent(record.getContent().replace("\r\n", "<br/>"));
	        if(subs != null) {
	        	System.out.println("record.getId()"+record.getId());
	        	int subToFlag = subs.contains(String.valueOf(record.getId())) ? 1 : 0;
		        record.setIsSubto(subToFlag);
	        }
	        record.setProfilepath(service.findProfilePathById(record.getId()));
	        System.out.println("files:"+record.getFiles());
	        System.out.println("record.likes()"+record.getLikes());
	        System.out.println("record.type()"+record.getType());
	        System.out.println("record.getIsSubto()"+record.getIsSubto());
	    }
	    
		return records;
	}
}
