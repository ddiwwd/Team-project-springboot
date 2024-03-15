package com.ict.teamProject.bbs.service;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("BBSDto")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class BBSDto {
	private int bno;
	private String id;
	private int type;
	private String content;
	private char disclosureYN;
	private String hashTag;//해
	private java.sql.Date postDate;
	private List<String> files;  // 파일들을 저장할 필드
	private List<String> likes;
	private List<Integer> likesnum;
	private String profilepath;
	private int isSubto; //구독된 상태인지 체크
}
