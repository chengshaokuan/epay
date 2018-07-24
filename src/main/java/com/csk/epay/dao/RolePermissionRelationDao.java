package com.csk.epay.dao;

import java.util.List;

public interface RolePermissionRelationDao {

	/**
	 * 根据角色id和许可id获取关系表中的记录数
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	Long getCountByRoleIdAndPermissionId(Integer roleId, Integer permissionId);

	/**
	 * 根据角色id删除该角色对应的关系记录
	 * @param roleId
	 */
	void deleteByRoleId(Integer roleId);

	/**
	 * 保存角色许可关系
	 * @param roleId
	 * @param permissionId
	 */
	void save(Integer roleId, Integer permissionId);

	/**
	 * 根据角色id获取当前这个角色拥有的所有许可ids
	 * @param roleId
	 * @return
	 */
	List<Integer> getPermissionIdsByRoleId(Integer roleId);

}
