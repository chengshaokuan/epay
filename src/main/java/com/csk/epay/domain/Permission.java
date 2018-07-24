package com.csk.epay.domain;

import java.util.List;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public String getOperationUrl() {
		return operationUrl;
	}

	public void setOperationUrl(String operationUrl) {
		this.operationUrl = operationUrl;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public List<Permission> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Permission> childNodes) {
		this.childNodes = childNodes;
	}

}
