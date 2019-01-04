package com.csk.epay.vo;

import lombok.Data;

@Data
public class UserCondition {

	private Integer pageNo;
	private Integer pageSize;
	private Integer lockStatus;
	private String userName;
	private String startTime;
	private String endTime;
}
