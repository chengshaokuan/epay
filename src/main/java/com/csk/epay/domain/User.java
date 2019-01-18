package com.csk.epay.domain;

import com.csk.epay.entity.AbstractShardingTable;
import lombok.Data;

@Data
public class User extends AbstractShardingTable{
	
	public static final Integer LOCK_CODE=2;
	public static final Integer UN_LOCK_CODE=1;
	public static final String LOCK_TEXT="锁定";
	public static final String UN_LOCK_TEXT="启用";
	
	
	private String id;
	private String accountNo;
	private String name;
	private String email;
	private String password;
	private Integer lockStatus;// 1 启用 2锁定
	private String expireTime;
	private String allowIps;
	private String createTime;
	private String editTime;


}