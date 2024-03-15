package com.ict.teamProject.challenge_room.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.challenge_room.service.CPDto;
import com.ict.teamProject.challenge_room.service.CRDto;
import com.ict.teamProject.challenge_room.service.ImplDto;
import com.ict.teamProject.challenge_room.service.SuccessPeopleDto;

//24.02.18 생성
@Mapper
public interface CRMapper {

	//전체 조회
	List findAll();
	
	//자기 챌린지 보기
	CRDto findByBBS(int bno);
		
	//입력/수정/삭제
	int save(CRDto map);
	int update(String id);
	int delete(int room);
	
	// 자기 방 번호 가져오기
	Integer getMyRoom(String id);
	int insertP(CPDto dto);

	int getSeqValue();

	void deletep(String id);
	
	Map findmyData(String id);

	List participantsdata(int room);

	int join(CPDto dto);

	CRDto findRoomData(int challNo);

	String selectManager(int room);

	Map findGoal(String id);

	void insertImpl(ImplDto dto);
	
	Date findImpl(String id);

	void updateImpl(ImplDto dto);
	
	ImplDto findImplAll(String id);

	List implcal(int challNo);

	List<String> getId(int challNo);
	
	Date startchall(int challNo);

	String findGoalOfNum(int challNo);

	void implinsert(Map map);

	void deletePeople(int challNo);

	void insertEattingImpl(ImplDto dto);

	void insertExerciseImpl(ImplDto dto);

	void updateExerciseImpl(ImplDto dto);

	void updateEattingImpl(ImplDto dto);
	
	List<SuccessPeopleDto> successPeople(int challNo);
	
	int successCount(int challNo);
	
	int givePoint(String id, int point);

	int updatEndDate(int challNo);

}
