package com.ict.teamProject.actuality;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.actuality.dto.ActualityEatingDto;

@Service
public class ActualityService {
	
	//mapper 생성자 주입
	ActualityMapper mapper;
	public ActualityService(ActualityMapper mapper) {
		this.mapper = mapper;
	}

	public int saveActuality(String id, String ae_foodname, String ae_diettype) {
		return mapper.saveActuality(id, ae_foodname, ae_diettype);
	}

	public int checkdailydiet(String id, String ae_diettype) {
		return mapper.checkdailydiet(id, ae_diettype);
	}

	public List<ActualityEatingDto> dailyActuality(String id) {
		return mapper.dailyActuality(id);
	}

	public int updateActuality(String id, String ae_foodname, String ae_diettype) {
		return mapper.updateActuality(id, ae_foodname, ae_diettype);
	}

	public List getEatting(String string) {
		return mapper.getEatting(string);
	}
}
