package com.csk.epay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csk.epay.dao.UserRoleRelationDao;
import com.csk.epay.domain.Role;
import com.csk.epay.service.UserRoleRelationService;

@Service("userRoleRelationService")
public class UserRoleRelationServiceImpl implements UserRoleRelationService {

	@Resource(name="userRoleRelationDao")
	private UserRoleRelationDao userRoleRelationDao; 
	
	@Override
	public List<Role> getAssignedRolesByUserId(Integer userId) {
		return userRoleRelationDao.getAssignedRolesByUserId(userId);
	}

	@Override
	public List<Role> getUnAssignedRolesByUserId(Integer userId) {
		return userRoleRelationDao.getUnAssignedRolesByUserId(userId);
	}

	@Override
	public void assign(Integer userId, Integer[] roleIds) {
		for (Integer roleId : roleIds) {
			//根据用户id和角色id到关系表中查询
			Long count = userRoleRelationDao.getCountByUserIdAndRoleId(userId,roleId);
			if(count==0){
				//正向授权  insert
				userRoleRelationDao.save(userId,roleId);
			}else{
				//负向授权  delete
				userRoleRelationDao.delete(userId,roleId);
			}
		}
	}

}
