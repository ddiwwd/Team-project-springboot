package com.ict.teamProject.comm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import com.ict.teamProject.comm.dto.FriendDto;
import com.ict.teamProject.comm.dto.MateDto;
import com.ict.teamProject.comm.dto.MySubscriberDto;
import com.ict.teamProject.comm.dto.SubscribeToDto;
import com.ict.teamProject.comm.dto.UserProfileDto;
import com.ict.teamProject.command.FileUtils;
import com.ict.teamProject.s3.S3UploadService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/comm")
@CrossOrigin(origins = {"http://localhost:3333", "http://localhost:5000"})
public class CommController {
	
	@Autowired
	private S3UploadService s3UploadService;
	
	CommService service;
	public CommController(CommService service) {
		this.service = service;
	}
	//mate
	@GetMapping("/mate") //조회
	public List<MateDto> getAllMates(@RequestParam String id){
		List<MateDto> mates = service.findAllMatesById(id);
		for(MateDto m : mates) {
			m.setName(service.findNameById(m.getMate_id()));//이름
			m.setFNum(service.findFMSnumById(m.getMate_id(), "f"));//친구 수
			m.setMNum(service.findFMSnumById(m.getMate_id(), "m"));//메이트 수
			m.setSNum(service.findFMSnumById(m.getMate_id(), "s"));//구독자 수
			m.setProfilePath(service.findProPathById(m.getMate_id()));//프로필 사진
		}
		return mates;
	}
	//뿌려주기 위함
	@GetMapping("/mate/available")
	public List<MateDto> getAllMatesAvailable(@RequestParam Map map){
		System.out.println("메이트 추천 받은 값:" + map);
		List<MateDto> mates = service.getAllMatesAvailable(map);
		for(MateDto m : mates) {
			m.setName(service.findNameById(m.getMate_id()));//이름
			m.setFNum(service.findFMSnumById(m.getMate_id(), "f"));//친구 수
			m.setMNum(service.findFMSnumById(m.getMate_id(), "m"));//메이트 수
			m.setSNum(service.findFMSnumById(m.getMate_id(), "s"));//구독자 수
			m.setProfilePath(service.findProPathById(m.getMate_id()));//프로필 사진
		}
		List<MateDto> allMates = service.findAllMatesById(String.valueOf(map.get("id")));
		//아이디값, 날짜
		for(MateDto m : allMates) {
			Map tempMap = new HashMap<String, String>();
			tempMap.put("id", m.getMate_id());
			tempMap.put("sch_date", String.valueOf(map.get("sch_date")));
			if(service.isUserInSchedule(tempMap)==0) {
				System.out.println("스케줄이 등록되지 않은 사용자"+m.getMate_id());
				m.setProfilePath(service.findProPathById(m.getMate_id()));
				mates.add(m);
			}
		}
		return mates;
	}//getAllMatesAvailable
	
	@PutMapping("/mate/changefavorable")//호감도 수정
	public void updateFavorableRating(@RequestBody Map map) {
		MateDto dto = new MateDto().builder()
						.mate_id(String.valueOf(map.get("mate_id")))
						.favorable_rating(((Integer)map.get("favorable_rating")))
						.build();
		service.putFavorableRating(dto);
	}
	
	//메이트 끊기
	@DeleteMapping("/mate/delete")
	public void deleteMate(@RequestBody Map map) {
		service.deleteMate(map);
	}
	
	//friend
	@GetMapping("/friend") //조회
	public List<FriendDto> getAllFriends(@RequestParam String id){
		List<FriendDto> friends = service.findAllFriendsById(id);
		for(FriendDto f : friends) {
			f.setName(service.findNameById(f.getFriend_id()));
			f.setFNum(service.findFMSnumById(f.getFriend_id(), "f"));
			f.setSNum(service.findFMSnumById(f.getFriend_id(), "s"));
			f.setMNum(service.findFMSnumById(f.getFriend_id(), "m"));
			f.setProfilePath(service.findProPathById(f.getFriend_id()));
			System.out.println("f.getName():"+f.getName());
			System.out.println("f.getProfilePath():"+f.getProfilePath());
			System.out.println("f.getFriend_id():"+f.getFriend_id());
		}
		return friends;
	}
	
	//친구끊기
	@DeleteMapping("/friend/delete")
	public void deleteFriend(@RequestBody Map map) {
		service.deleteFriend(map);
	}
	
	//친구 차단
	@PutMapping("/friend/block")
	public void blockFriend(@RequestBody Map map) {
		service.putFriendBlocking(map);
	}
	
	//subscribe
	@GetMapping("/subscribe") //조회
	public Map getAllSubInfo(@RequestParam String id){
		System.out.println("구독 파라미터 id:"+id);
		List<SubscribeToDto> subscribeTo = service.findAllSubToById(id); //내가 구독한 목록
		for(SubscribeToDto dto : subscribeTo) {
			dto.setName(service.findNameById(dto.getSubscribe_id()));
			dto.setFNum(service.findFMSnumById(dto.getSubscribe_id(), "f"));
			dto.setSNum(service.findFMSnumById(dto.getSubscribe_id(), "s"));
			dto.setMNum(service.findFMSnumById(dto.getSubscribe_id(), "m"));
			dto.setProfilePath(service.findProPathById(dto.getSubscribe_id()));
		}
		
		List<MySubscriberDto> subscribers = service.findAllMySubById(id); //나의 구독자 목록
		for(MySubscriberDto dto : subscribers) {
			dto.setName(service.findNameById(dto.getId()));
			dto.setFNum(service.findFMSnumById(dto.getId(), "f"));
			dto.setSNum(service.findFMSnumById(dto.getId(), "s"));
			dto.setMNum(service.findFMSnumById(dto.getId(), "m"));
			dto.setProfilePath(service.findProPathById(dto.getId()));
		}
		Map total = new HashMap();
		total.put("subTo", subscribeTo);
		total.put("MySub", subscribers);
		System.err.println("구독자 요청 total : "+total);
		return total;
	}
	
	//구독 취소
	@DeleteMapping("/subscribe/delete")
	public void deleteSubTo(@RequestBody Map map) {
		System.out.println("구독취소 요청됨: "+map);
		service.deleteSubTo(map);
	}
	
	
	//구독자 삭제
	@DeleteMapping("/subscribe/deleteSubscriber")
	public void deleteSubscriber(@RequestBody Map map) {
		service.deleteSubscriber(map);
	}
	
	//구독 등록
	@PostMapping("/subscribe/subscribing")
	public void updateSubscribe(@RequestBody Map map) {
		System.out.println("구독등록 요청됨: "+map.get("userId")+ " : "+map.get("subToId"));
		service.updateSubscribe(map);
	}
	
	//공통 기능
	//메이트 및 친구 요청
	@PostMapping("/request")
	public void requestMateOrFriend(@RequestBody Map map) {
		service.postFriendORMateRequest(map);
	}
	
	//요청을 넣은 목록 모두 확인 용
	@GetMapping("/request/check")
	public List<String> findRequestedFriendORMate(@RequestParam String id){
		Map map = new HashMap();
		map.put("userId", id);
		map.put("type", 1);
		return service.findRequestedFriendORMate(map);
	}
	
	@PutMapping("/intro/update")
	public void updateInro(@RequestBody Map<String, Object> requestBody) {
	    String id = (String) requestBody.get("id");
	    String proIntroduction = (String) requestBody.get("proIntroduction");
	    System.out.println("아이디: " + id);
	    System.out.println("소개: " + proIntroduction);
	    int check = service.updateIntro(id, proIntroduction);
	    System.out.println(check);
	}

	
	//유저프로필 사진경로 변경
	@PutMapping("/profile/update")
	public void updateProfilePath(@RequestBody Map map) {
		System.out.println(map);
		UserProfileDto dto = new UserProfileDto().builder()
				.id(String.valueOf(map.get("id")))
				.profilePath(String.valueOf(map.get("profilePath")))
				.build();
		service.putProfileImage(dto);
	}
	
	//사용중인 유저프로필
	@GetMapping("/profile")
	public UserProfileDto getUserProfile(@RequestParam String id) {
		List<FriendDto> friendsList = service.findAllFriendsById(id);
		Map map = new HashMap();
		map.put("userId", id);
		map.put("type", 1);
		UserProfileDto dto = new UserProfileDto().builder()
				.id(id)
				.name(service.findNameById(id))
				.profilePath(service.findProPathById(id))
				.proIntroduction(service.findIntroductionById(id))
				.date(service.findJoinDateById(id))
				.friendsList(friendsList)
				.requestedFriendsList(service.findRequestedFriendORMate(map))
				.build();
		System.out.println("여기 안들어오나?"+dto);
		return dto;
	}
	
    // 파일 업로드 처리
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void uploadFile(MultipartFile file) throws IOException {
	    System.out.println("파일 업로드"+file);
	    if (file != null) {
		    try {
		    	String imageUrl = s3UploadService.saveFile(file);
		   }catch (IOException e) {
		        e.printStackTrace();
		   }
	    }
		   
	}
	
	//메이트 신고 처리
	@PostMapping("/mate/reportwarning")
	public void reportWarningMate(@RequestBody Map map) {
		service.reportWarningMate(map);
	}
	
	//(게시판용)랜덤 친구 추천 
	@GetMapping("/friend/random")
	public Map<String,String> getRandomFriends(@RequestParam String id){
		return service.getRandomFriendList(id);
	}
	
	//(친구 목록 페이지 용)랜덤 친구 추천
	@GetMapping("/randomfriend") //조회
	public List<FriendDto> getRandomFriendsForListPage(@RequestParam String id){
		Map<String, String> recommendList = service.getRandomFriendList(id); //랜덤 유저 리스트
		List<FriendDto> friends = new ArrayList<FriendDto>();
		for(String tempid:recommendList.keySet()) {
			FriendDto f = new FriendDto().builder()
					.friend_id(tempid)
					.name(service.findNameById(tempid))
					.fNum(service.findFMSnumById(tempid, "f"))
					.sNum(service.findFMSnumById(tempid, "s"))
					.mNum(service.findFMSnumById(tempid, "m"))
					.profilePath(recommendList.get(tempid))
					.build();
			friends.add(f);
		}
		return friends;
	}
	
	
}
