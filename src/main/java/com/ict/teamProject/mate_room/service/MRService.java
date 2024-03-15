package com.ict.teamProject.mate_room.service;

import java.util.List;
import java.util.Map;

//24.02.21 생성
public interface MRService<T> {

	//전체 챌린지 보기
	List<MRDto> selectAll();
	
	//자기 챌린지 보기
	MRDto viewMyRoom(String id);
	
	//자기 챌린지 방 번호 찾기
	Integer selectMyRoom(String id);
		
	//입력/수정/삭제용
	int insert(MRDto dto);
	int update(String id);
	int delete(int room);

	int insertP(MPDto dto);

	int getSeqValue();

	void deletep(String id);

	Map findmyData(String id);

	List participantsdata(int i);

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
