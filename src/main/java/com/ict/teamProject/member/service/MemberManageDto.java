package com.ict.teamProject.member.service;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.ict.teamProject.diary.dto.DiaryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("MemberManageDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberManageDto {
	private String id; //신고한 유저 아이디
	private String cl_id; //신고당한 유저 아이디
	private String cl_reason; //신고당한 이유
	private Date cl_date; //신고 날짜
}
