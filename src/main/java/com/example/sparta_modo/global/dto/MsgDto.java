package com.example.sparta_modo.global.dto;

import lombok.Getter;

@Getter
public class MsgDto {
	private String msg;

	public MsgDto(String msg) {
		this.msg = msg;
	}
}
