package com.csk.epay.vo;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

@Data
public class UserCondition {

	private Integer pageNo;
	private Integer pageSize;
	private Integer lockStatus;
	private String name;
	private String startTime;
	private String endTime;
}
