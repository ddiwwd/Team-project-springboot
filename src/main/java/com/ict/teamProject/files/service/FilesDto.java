package com.ict.teamProject.files.service;
import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("FilesDto")
@Builder
public class FilesDto {
	private int bno;
	private String filePath;
	private String fileName;
}
