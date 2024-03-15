package com.ict.teamProject.challenge_room.service;
import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("CP")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class CPDto {
	private int cPNo; //참여자 번호
	private int challNo; //방 번호
	private String id; //아이디
	private char Chall_Manager; //방장유무
	private int CIR; //이행률
	private String PRO_FILEPATH; //프로필

}
