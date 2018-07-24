package com.csk.epay.service;

import java.util.List;

import com.csk.epay.domain.Role;

public interface UserRoleRelationService {
	
	/**
	 * 根据用户id获取当前这个用户拥有的所有角色
	 * @param userId
	 * @return
	 */
	List<Role> getAssignedRolesByUserId(Integer userId);

	/**
	 * 根据用户id获取当前这个用户未分配的角色列表
	 * @param userId
	 * @return
	 */
	List<Role> getUnAssignedRolesByUserId(Integer userId);

	/**
	 * 给用户分配角色
	 * @param userId
	 * @param roleIds
	 */
	void assign(Integer userId, Integer[] roleIds);

}
