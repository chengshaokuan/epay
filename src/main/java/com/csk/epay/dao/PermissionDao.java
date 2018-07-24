package com.csk.epay.dao;

import java.util.List;

import com.csk.epay.domain.Permission;

public interface PermissionDao {
	
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
	 * 获取所有许可
	 * @return
	 */
	List<Permission> getAll();

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
	 * 根据用户id获取当前用户拥有的所有许可
	 * @param userId
	 * @return
	 */
	List<Permission> getPermissionsByUserId(Integer userId);
}
