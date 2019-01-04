package com.csk.epay.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Permission {

	private Integer id;
	private String code;
	private String name;
	private String moduleUrl;// 当点击某一节点 要跳转的资源路径 但是并不是每一个许可 都有模块url
	private String operationUrl;// 当执行某一个操作的时候 对应的跳转资源路径 例如：添加车辆
								// car/add.do,car/save.do
	private Integer orderNo;// 用户指定排序规则
	private String createTime;
	private String editTime;
	private Integer pid;

	private List<Permission> childNodes;
	private List<Role> childRole;
	private Role role;


}
