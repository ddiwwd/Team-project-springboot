package com.ict.teamProject.mate_room.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ict.teamProject.mate_room.service.MPDto;
import com.ict.teamProject.mate_room.service.MRDto;
import com.ict.teamProject.mate_room.service.MRService;


//24.02.18 생성
@Service
public class MRServiceImpl implements MRService<MRDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private MRMapper mapper;

	@Override
	public List<MRDto> selectAll() {
		return mapper.findAll();
	}

	@Override
	public MRDto viewMyRoom(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectMyRoom(String id) {
		Integer room = mapper.getMyRoom(id);
		return room;
	}

	@Override
	public int insert(MRDto map) {
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
	public int insertP(MPDto dto) {
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
	public int join(MPDto dto) {
		return mapper.join(dto);
	}

	@Override
	public MRDto findRoomData(int challNo) {
		return mapper.findRoomData(challNo);
	}

	@Override
	public String selectManager(int room) {
		return mapper.selectManager(room);
	}

	@Override
	public void deletePeople(int mateNo) {
		mapper.deletePeople(mateNo);
	}

	@Override
	public void updateRoom(Map map) {
		mapper.updateRoom(map);		
	}

	@Override
	public Integer matchingRoom(MRDto dto) {
		return mapper.matchingRoom(dto);
	}

	@Override
	public List<MPDto> findparticipants(int myRoom) {
		return mapper.findparticipants(myRoom);
	}

	@Override
	public void matching(Map data) {
		mapper.matching(data);
	}

	@Override
	public Integer isMatching(int myRoom) {
		return mapper.isMatching(myRoom);
	}

	@Override
	public void readyMyRoom(MRDto dto) {
		mapper.readyMyRoom(dto);
		
	}

	@Override
	public void deleteMatching(int myRoom) {
		mapper.deleteMatching(myRoom);
		
	}
}
