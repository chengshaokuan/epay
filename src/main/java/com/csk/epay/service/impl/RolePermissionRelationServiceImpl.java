package com.csk.epay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csk.epay.dao.RolePermissionRelationDao;
import com.csk.epay.service.RolePermissionRelationService;

@Service("rolePermissionRelationService")
public class RolePermissionRelationServiceImpl implements RolePermissionRelationService {

	@Resource(name="rolePermissionRelationDao")
	private RolePermissionRelationDao rolePermissionRelationDao;
	
	@Override
	public void assign(Integer roleId, Integer[] permissionIds) {
		//先将当前这个角色对应的关系记录删除
		rolePermissionRelationDao.deleteByRoleId(roleId);
		
		//再将角色id和许可id封装为一条关系记录插入到关系表
		for (Integer permissionId : permissionIds) {
			if(!"".equals(permissionId)){
				rolePermissionRelationDao.save(roleId,permissionId);
			}
		}
	}

}
