package com.csk.epay.service;

public interface RolePermissionRelationService {

	/**
	 * 给角色分配许可
	 * @param roleId
	 * @param permissionIds
	 */
	void assign(Integer roleId, Integer[] permissionIds);

}
