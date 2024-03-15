package com.ict.teamProject.notic.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("NoticDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticDto {
	private int trigger_pk;
	private int trigger_no;
	private String notic_receive_user;
	private String notic_trigger_user;
	private int notic_type;
	private Timestamp notic_trigger_date;
	private Timestamp checked_time;
	private int bbs_no;
	private String bbs_content;
	private String ccomment;
	private int parent_comment;
	private String pro_filepath;
}
