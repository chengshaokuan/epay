package com.csk.epay.dao;

import java.util.List;

public interface RolePermissionRelationDao {

	/**
	 * @Description:  保存角色许可关系
	 * @param: roleId
	 * @param: permissionId
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 14:43 2019/4/1
	 */
	void save(Integer roleId, Integer permissionId);

	/**
	 * @Description: 根据角色id删除该角色对应的关系记录
	 * @param: roleId
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 14:43 2019/4/1
	 */
	void deleteByRoleId(Integer roleId);

	/**
	 * @Description:  根据角色id获取当前这个角色拥有的所有许可ids
	 * @param: roleId
	 * @return: java.util.List<java.lang.Integer>
	 * @Author: Mr.Cheng
	 * @Date: 14:43 2019/4/1
	 */
	List<Integer> getPermissionIdsByRoleId(Integer roleId);

	/**
	 * @Description:  根据角色id和许可id获取关系表中的记录数
	 * @param: roleId
	 * @param: permissionId
	 * @return: java.lang.Long
	 * @Author: Mr.Cheng
	 * @Date: 14:43 2019/4/1
	 */
	Long getCountByRoleIdAndPermissionId(Integer roleId, Integer permissionId);

}
