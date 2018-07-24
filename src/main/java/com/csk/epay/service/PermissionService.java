package com.csk.epay.service;

import java.util.List;
import java.util.Map;

import com.csk.epay.domain.Permission;

public interface PermissionService {
	/**
	 * 保存许可
	 * @param permission
	 */
	void save(Permission permission);
	
	/**
	 * 根据id删除许可
	 * @param id
	 */
	void deleteById(Integer id);
	
	/**
	 * 根据id获取许可对象
	 * @param id
	 * @return
	 */
	Permission getById(Integer id);
	
	/**
	 * 修改许可
	 * @param permission
	 */
	void update(Permission permission);
	
	/**
	 * 拼接json格式的字符串，建立许可树
	 * @return
	 */
	String getPermissionTree();

	/**
	 * 根据许可代码获取许可对象
	 * @param code
	 * @return
	 */
	Permission getByCode(String code);

	/**
	 * 根据许可名称和父许可id获取许可对象
	 * @param name
	 * @param pid
	 * @return
	 */
	Permission getByNameAndPid(String name, Integer pid);

	/**
	 * 拼接给角色分配 许可功能中的许可树
	 * @param roleId
	 * @return
	 */
	List<Map<String, Object>> getCheckboxTree(Integer roleId);

	/**
	 * 拼接查看角色明细功能中的许可树
	 * @param roleId
	 * @return
	 */
	List<Map<String, Object>> getDetailTree(Integer roleId);

	/**
	 * 拼接查看用户明细功能中的许可树
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> getFinalTree(Integer userId);

	/**
	 * 拼接菜单树
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> getMenuTree(Integer userId);
}	
