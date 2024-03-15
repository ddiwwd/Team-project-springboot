package com.ict.teamProject.member_history.service;
import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("MemberHistoryDto")
@Builder
public class MemberHistoryDto {
	private String id;
	private String mem_colname;
	private String before_value;
	private String after_value;
	private java.sql.Timestamp update_day;
}
	