package com.ict.teamProject.comm;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ict.teamProject.comm.dto.FriendDto;
import com.ict.teamProject.comm.dto.MateDto;
import com.ict.teamProject.comm.dto.MySubscriberDto;
import com.ict.teamProject.comm.dto.SubscribeToDto;
import com.ict.teamProject.comm.dto.UserProfileDto;

@Service
public class CommService {
	
	//mapper 생성자 주입
	private CommMapper mapper;
	public CommService(CommMapper mapper) {
		this.mapper = mapper;
	}
	
	//친구, 메이트, 구독자 조회
	public List<MateDto> findAllMatesById(String id) {//메이트
		return mapper.findAllMatesById(id);
	}
	
	public List<FriendDto> findAllFriendsById(String id) {//친구
		return mapper.findAllFriendsById(id);
	}
	
	
	
	public List<SubscribeToDto> findAllSubToById(String id){//구독한 목록
		return mapper.findAllSubToById(id);
	}
	
	public List<MySubscriberDto> findAllMySubById(String id){//구독자 목록
		return mapper.findAllMySubById(id);
	}
	
	//이름 조회
	public String findNameById(String id) {
		return mapper.findNameById(id);
	}
	
	//친구,메이트, 구독자 수 조회
	public int findFMSnumById(String id, String cate) {
		if(cate.equals("f")) return mapper.findFnumById(id);
		else if(cate.equals("m")) return mapper.findMnumById(id);
		else return mapper.findSnumById(id);
	}
	
	//프로필 사진 조회
	public String findProPathById(String id) {
		return mapper.findProPathById(id);
	}
	
	//가입날짜 조회
	public Date findJoinDateById(String id) {
		return mapper.findJoinDateById(id);
	}
	
	//메이트의 호감도 수정
	public void putFavorableRating(MateDto dto) {
		mapper.putFavorableRating(dto);
	}
	
	//유저의 한줄 소개
	public String findIntroductionById(String id) {
		return mapper.findIntroductionById(id);
	}
	
	//친구 차단
	public void putFriendBlocking(Map<String, String> ids) {
		mapper.putFriendBlocking(ids);
	}
	
	//친구 삭제
	public void deleteFriend(Map<String, String> ids) {
		mapper.deleteFriend(ids);
	}
	
	//구독 끊기
	public void deleteSubTo(Map<String, String> ids) {
		mapper.deleteSubTo(ids);
	}
	
	//메이트 끊기
	public void deleteMate(Map<String, String> ids) {
		mapper.deleteMate(ids);
	}
	
	//구독자 삭제
	public void deleteSubscriber(Map<String, String> ids) {
		mapper.deleteSubscriber(ids);
	}
	
	//유저소개 변경
	public int updateIntro(String id, String proIntroduction) {
		return mapper.updateIntro(id, proIntroduction);
	}
	
	//변경된 유저프로필 사진정보 DB 저장
	public int putProfileImage(UserProfileDto dto) {
		return mapper.putProfileImage(dto);
	}
	
	//구독
	public void updateSubscribe(Map<String, String> ids) {
		mapper.updateSubscribe(ids);
	}
	
	//메이트 혹은 친구요청
	public void postFriendORMateRequest(Map<String, String> idsNtype) {
		mapper.postFriendORMateRequest(idsNtype);
	}
	
	//메이트 신고
	public void reportWarningMate(Map<String,String>idsNReason) {
		//(메이트 신고용)메이트 목록에서 값 삭제
		mapper.deleteMate(idsNReason);
		//(메이트 신고용)신고리스트에 값 추가
		mapper.saveMateForWarning(idsNReason);
	}
	
	public int isUserInSchedule(Map map) {
		return mapper.isUserInSchedule(map);
	}
	
	//랜덤으로 친구목록(5명) 생성
	public Map<String,String> getRandomFriendList(String id){
		
		Map<String,String> friends = new HashMap<String, String>();
		List<Integer> friendsIndex = new ArrayList<Integer>(); //생성한 난수를 담아줄 변수(중복난수판단용)
		
		List<String> allUsersExceptFriend = new ArrayList<String>(); //모든 유저 아이디
		allUsersExceptFriend = mapper.findAllUserId();
		for(FriendDto dto : mapper.findAllFriendsById(id)) { //이미 친구인 유저의 아이디 제거
			allUsersExceptFriend.remove(dto.getFriend_id());
		}
		allUsersExceptFriend.remove(id);//접속자의 아이디 제외
		allUsersExceptFriend.remove("google"); //google 키 넣어놓은 값도 제외
		
		int count = allUsersExceptFriend.size();
		
		Random rd = new Random(); //인덱스로 사용할 난수 생성하기
		int i=0;
		while(i<5) {
			int temp = rd.nextInt(count-1);
			if(!friendsIndex.contains(temp)) { //이미 생성된 난수가 아니라면 실행되는 코드
				friendsIndex.add(temp);
				String tempid = allUsersExceptFriend.get(temp);
				friends.put(tempid, findProPathById(tempid));
				i++;
			}
		}
		return friends;
	}
	
	public List<String> findRequestedFriendORMate(Map map){
		return mapper.findRequestedFriendORMate(map);
	}
	
	public List<MateDto> getAllMatesAvailable(Map map){
		return mapper.getAllMatesAvailable(map);
	}
}
