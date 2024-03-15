package com.ict.teamProject.commentline.service;
import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("CommentLineDto")
@Builder
public class CommentLineDto {
	private int c_no;
	private String id;
	private int bbs_no;
	private String ccomment;
	private int parent_comment;
	private java.sql.Date postDate;
	private java.sql.Timestamp update_time;
	private char deleted;
	private int level;
}
