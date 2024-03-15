package com.ict.teamProject.challenge_room.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ict.teamProject.challenge_room.service.CPDto;
import com.ict.teamProject.challenge_room.service.CRDto;
import com.ict.teamProject.challenge_room.service.CRService;
import com.ict.teamProject.challenge_room.service.ImplDto;
import com.ict.teamProject.challenge_room.service.SuccessPeopleDto;

//24.02.18 생성
@Service
public class CRServiceImpl implements CRService<CRDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private CRMapper mapper;

	@Override
	public List<CRDto> selectAll() {
		return mapper.findAll();
	}

	@Override
	public CRDto viewMyRoom(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectMyRoom(String id) {
		Integer room = mapper.getMyRoom(id);
		return room;
	}

	@Override
	public int insert(CRDto map) {
		int affected = mapper.save(map);
		return affected;
	}

	@Override
	public int update(String id) {
		return mapper.update(id);
	}

	@Override
	public int delete(int room) {
		int affected = mapper.delete(room);
		return affected;
	}

	@Override
	public int insertP(CPDto dto) {
		int affected = mapper.insertP(dto);
		return affected;
	}

	@Override
	public int getSeqValue() {
		return mapper.getSeqValue();
	}

	@Override
	public void deletep(String id) {
		mapper.deletep(id);
	}

	@Override
	public Map findmyData(String id) {
		return mapper.findmyData(id);
	}

	@Override
	public List participantsdata(int room) {
		return mapper.participantsdata(room);
	}

	@Override
	public int join(CPDto dto) {
		return mapper.join(dto);
	}

	@Override
	public CRDto findRoomData(int challNo) {
		return mapper.findRoomData(challNo);
	}

	@Override
	public String selectManager(int room) {
		return mapper.selectManager(room);
	}

	@Override
	public Map findGoal(String id) {
		return mapper.findGoal(id);
	}

	@Override
	public void insertImpl(ImplDto dto) {
		mapper.insertImpl(dto);
		
	}

	@Override
	public Date findImpl(String id) {
		return mapper.findImpl(id);
	}

	@Override
	public void updateImpl(ImplDto dto) {
		mapper.updateImpl(dto);
		
	}

	@Override
	public ImplDto findImplAll(String id) {
		return mapper.findImplAll(id);
	}

	@Override
	public List implcal(int challNo) {
		return mapper.implcal(challNo);
	}

	@Override
	public List<String> getId(int challNo) {
		return mapper.getId(challNo);
	}

	@Override
	public Date startchall(int challNo) {
		return mapper.startchall(challNo);
	}

	@Override
	public String findGoalOfNum(int challNo) {
		return mapper.findGoalOfNum(challNo);
	}

	@Override
	public void implinsert(Map map) {
		mapper.implinsert(map);
	}

	@Override
	public void deletePeople(int challNo) {
		mapper.deletePeople(challNo);
		
	}

	@Override
	public void insertEattingImpl(ImplDto dto) {
		mapper.insertEattingImpl(dto);
		
	}

	@Override
	public void insertExerciseImpl(ImplDto dto) {
		mapper.insertExerciseImpl(dto);
		
	}

	@Override
	public void updateExerciseImpl(ImplDto dto) {
		mapper.updateExerciseImpl(dto);
		
	}

	@Override
	public void updateEattingImpl(ImplDto dto) {
		mapper.updateEattingImpl(dto);
		
	}

	@Override
	public List<SuccessPeopleDto> successPeople(int challNo) {
		return mapper.successPeople(challNo);
	}
	
	@Override
	public int successCount(int challNo) {
		return mapper.successCount(challNo);
	}

	@Override
	public int givePoint(String id, int point) {
		return mapper.givePoint(id, point);
	}

	@Override
	public int updatEndDate(int challNo) {
		return mapper.updatEndDate(challNo);
	}
}
