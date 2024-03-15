package com.ict.teamProject.notic;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.notic.dto.NoticDto;

@Service
public class NoticService {
	
	//mapper 생성자 주입
	private NoticMapper mapper;
	public NoticService(NoticMapper mapper) {
		this.mapper = mapper;
	}
	
	//유저 PayList 목록
	public List<NoticDto> findAllNotic(String id) {
		return mapper.findAllNotic(id);
	}


	public int updateNotic(int trigger_pk) {
		return mapper.updateNotic(trigger_pk);
	}

	public int deleteNotic(int trigger_pk) {
		return mapper.deleteNotic(trigger_pk);
	}

	public int afmlSaveY(int trigger_no) {
		return mapper.afmlSaveY(trigger_no);
	}

	public int afmlDeleteN(int trigger_no) {
		return mapper.afmlDeleteN(trigger_no);
	}
}
