package com.ict.teamProject.comm.dto;

import org.apache.ibatis.type.Alias;

import com.nimbusds.jose.shaded.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("SubscribeToDto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeToDto {
	@SerializedName("subscribe_id")
	private String subscribe_id;
	private String name;
	private int fNum;
	private int mNum;
	private int sNum;
	private String profilePath;
}
