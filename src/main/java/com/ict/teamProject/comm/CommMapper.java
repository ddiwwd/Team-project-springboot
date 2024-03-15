package com.ict.teamProject.comm;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.comm.dto.FriendDto;
import com.ict.teamProject.comm.dto.MateDto;
import com.ict.teamProject.comm.dto.MySubscriberDto;
import com.ict.teamProject.comm.dto.SubscribeToDto;
import com.ict.teamProject.comm.dto.UserProfileDto;

@Mapper
public interface CommMapper {
	public List<MateDto> findAllMatesById(String id);
	public List<FriendDto> findAllFriendsById(String id);
	public List<SubscribeToDto> findAllSubToById(String id); //내가 구독한 목록
	public List<MySubscriberDto> findAllMySubById(String id); //나의 구독자 목록
	public String findNameById(String id);
	public int findFnumById(String id); //친구 수
	public int findMnumById(String id); //메이트 수
	public int findSnumById(String id); //구독자 수
	public String findProPathById(String id); //프로필 사진
	public void putFavorableRating(MateDto dto); //호감도 수정
	public String findIntroductionById(String id);//한줄 소개
	public Date findJoinDateById(String id); //가입 날짜 받기
	public void putFriendBlocking(Map<String, String> ids); //친구 차단
	public void deleteFriend(Map<String, String> ids); //친구 삭제
	public void deleteSubTo(Map<String, String> ids); //구독 끊기
	public void deleteMate(Map<String, String> ids); //메이트 끊기
	public void deleteSubscriber(Map<String, String> ids); //구독자 삭제
	public int updateIntro(String id, String proIntroduction); //유저소개 변경
	public int putProfileImage(UserProfileDto dto); //프로필 사진 교체
	public void updateSubscribe(Map<String, String> ids); //구독하기
	public void postFriendORMateRequest(Map<String, String> idsNtype); //친구 또는 메이트 요청보내기
	public void saveMateForWarning(Map<String, String> idsNReason); //신고리스트에 값 추가
	public int getAllUserNum(); //모든 사용자 수 얻기
	public List<String> findAllUserId(); //모든 유저 아이디 얻기
	public List<String> findRequestedFriendORMate(Map map); //신청한 메이트 혹은 친구 목록 모두 보기
	public List<MateDto> getAllMatesAvailable(Map map); //경로 일정 등록이 가능한 유저 값
	public int isUserInSchedule(Map map); //스케줄이 등록된 사용자인지 확인
}
