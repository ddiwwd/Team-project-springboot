package com.ict.teamProject.exercise;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ict.teamProject.exercise.bbs.RoadPathDto;
import com.ict.teamProject.exercise.bbs.RoadPointDto;
import com.ict.teamProject.exercise.bbs.RoadSchDto;

@Service
public class ExerciseService {
	ExerciseMapper mapper;
	public ExerciseService(ExerciseMapper mapper) {
		this.mapper = mapper;
	}
	
	public void uploadPath(RoadPathDto dto) {
		mapper.uploadPath(dto);
	}
	
	public void uploadPathPoint(List dto) {
		mapper.uploadPathPoint(dto);
	}
	//지도 카테고리 가져오기
	public List<RoadPathDto> getAllPathsCate(String id){
		return mapper.getAllPathsCate(id);
	}
	//각 카테고리 별 경로 지표 가져오기
	public List<RoadPointDto> getAllPointsByCate(String cate) {
		return mapper.getAllPointsByCate(cate);
	}
	//rpath_no에 맞는 좌표값 가져오기
	public List<RoadPointDto> getAllPointsByRpathNo(int rpath_no){
		return mapper.getAllPointsByRpathNo(rpath_no);
	}
	//스케줄에 이동 경로 등록
	public void uploadSchedulePath(RoadSchDto dto) {
		mapper.uploadSchedulePath(dto);
	}
	//경로 아이디로 마지막 경로의 주소 값 가져오기
	public RoadPointDto findFianlPointById(String rpath_no) {
		return mapper.findFianlPointById(rpath_no);
	}
}
