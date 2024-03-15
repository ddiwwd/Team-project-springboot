package com.ict.teamProject.diary;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.diary.dto.DiaryDto;
import com.ict.teamProject.diary.dto.DiaryImagesDto;

@Mapper
public interface ManageMapper {
	public DiaryDto findDiaryById(String userNDate); //다이어리 텍스트 내용 뿌려주기 용
	public List<String> findDiaryImgUrlsByDiaryId(String diaryId); //다이어리에 들어간 사진 뿌려주기 용
	public int uploadDiaryById(DiaryDto diaryDto); //다이어리 내용 입력용
	public int uploadDiaryImageById(List<DiaryImagesDto> imgs); //다이어리 사진 입력용
	public DiaryDto findAllusertext(String id);
}
