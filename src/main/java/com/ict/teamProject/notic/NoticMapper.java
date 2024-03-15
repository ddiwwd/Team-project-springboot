package com.ict.teamProject.notic;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.notic.dto.NoticDto;

@Mapper
public interface NoticMapper {
	public List<NoticDto> findAllNotic(String id);

	public int updateNotic(int trigger_pk);

	public int deleteNotic(int trigger_pk);

	public int afmlSaveY(int trigger_no);

	public int afmlDeleteN(int trigger_no);
}
