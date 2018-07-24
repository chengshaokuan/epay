package com.csk.epay.dao;

import java.util.List;

import com.csk.epay.domain.Role;

public interface UserRoleRelationDao {

	/**
	 * 根据用户id获取当前用户已经分配的角色
	 * @param userId
	 * @return
	 */
	List<Role> getAssignedRolesByUserId(Integer userId);

	/**
	 * 根据用户id获取当前用户未分配角色列表
	 * @param userId
	 * @return
	 */
	List<Role> getUnAssignedRolesByUserId(Integer userId);

	/**
	 * 根据用户id和角色id到关系表中查找对应的关系记录
	 * @param userId
	 * @param roleId
	 * @return
	 */
	Long getCountByUserIdAndRoleId(Integer userId, Integer roleId);

	/**
	 * 保存用户角色关系
	 * @param userId
	 * @param roleId
	 */
	void save(Integer userId, Integer roleId);

	/**
	 * 删除用户角色关系记录
	 * @param userId
	 * @param roleId
	 */
	void delete(Integer userId, Integer roleId);

}
