package com.ict.teamProject.exercise;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.exercise.bbs.RoadPathDto;
import com.ict.teamProject.exercise.bbs.RoadPointDto;
import com.ict.teamProject.exercise.bbs.RoadSchDto;

@Mapper
public interface ExerciseMapper {
	//경로 등록 세트---------
	public void uploadPath(RoadPathDto dto); //경로 등록
	public void uploadPathPoint(List dto); //경로 포인트 등록
	//경로 등록 세트 end-----
	
	//모든 카테고리 가져오기
	public List<RoadPathDto> getAllPathsCate(String id); 
	//카테고리에 맞는 좌표값들 가져오기
	public List getAllPointsByCate(String cate);
	//rpath_no에 맞는 좌표값 가져오기
	public List<RoadPointDto> getAllPointsByRpathNo(int rpath_no);
	//스케줄에 이동 경로 등록
	public void uploadSchedulePath(RoadSchDto dto);
	//경로 아이디로 마지막 경로의 주소 값 가져오기
	public RoadPointDto findFianlPointById(String rpath_no);
}
