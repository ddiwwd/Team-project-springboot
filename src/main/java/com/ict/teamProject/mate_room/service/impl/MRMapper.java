package com.ict.teamProject.mate_room.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.mate_room.service.MPDto;
import com.ict.teamProject.mate_room.service.MRDto;

//24.02.21 생성
@Mapper
public interface MRMapper {

	//전체 조회
	List findAll();
	
	//자기 챌린지 보기
	MRDto findByBBS(int bno);
		
	//입력/수정/삭제
	int save(MRDto map);
	int update(String id);
	int delete(int room);
	
	// 자기 방 번호 가져오기
	Integer getMyRoom(String id);
	int insertP(MPDto dto);

	int getSeqValue();

	void deletep(String id);
	
	Map findmyData(String id);

	List participantsdata(int room);

	int join(MPDto dto);

	MRDto findRoomData(int challNo);

	String selectManager(int room);

	void deletePeople(int mateNo);

	void updateRoom(Map map);

	Integer matchingRoom(MRDto dto);

	List<MPDto> findparticipants(int myRoom);

	void matching(Map data);

	Integer isMatching(int myRoom);

	void readyMyRoom(MRDto dto);

	void deleteMatching(int myRoom);

}
