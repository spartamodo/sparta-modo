package com.example.sparta_modo.domain.dto;

import lombok.Getter;

@Getter
public class MsgDto {
	private String msg;

	public MsgDto(String msg) {
		this.msg = msg;
	}
}
