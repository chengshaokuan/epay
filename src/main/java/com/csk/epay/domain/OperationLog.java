package com.csk.epay.domain;

import lombok.Data;

@Data
public class OperationLog {
	private Integer id;
	
	private String ip;
	private String operator;
	private String time;
	private String module;
	private String type;
	private String node;


}
