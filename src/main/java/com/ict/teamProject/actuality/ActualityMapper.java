package com.ict.teamProject.actuality;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.actuality.dto.ActualityEatingDto;

@Mapper
public interface ActualityMapper {

	public int saveActuality(String id, String ae_foodname, String ae_diettype);

	public int checkdailydiet(String id, String ae_diettype);

	public List<ActualityEatingDto> dailyActuality(String id);

	public int updateActuality(String id, String ae_foodname, String ae_diettype);

	public List getEatting(String string);
	
}
