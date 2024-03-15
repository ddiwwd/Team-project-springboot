package com.ict.teamProject.diary.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.ict.teamProject.comm.dto.FriendDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("DiaryDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
	private String diaryId;
	private String id;
	private String diary_content;
	private float stress;
	private String emotion;
	private List<String> imgUrls;
}
