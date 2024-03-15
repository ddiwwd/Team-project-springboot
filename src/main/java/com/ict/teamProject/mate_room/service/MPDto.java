package com.ict.teamProject.mate_room.service;
import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("MP")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class MPDto {
	private int mPNo; //참여자 번호
	private int mateNo; //방 번호
	private String id; //아이디
	private char mate_Manager; //방장유무

}
