package com.csk.epay.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csk.epay.dao.PermissionDao;
import com.csk.epay.dao.UserDao;
import com.csk.epay.domain.Permission;
import com.csk.epay.domain.User;
import com.csk.epay.exceptions.ApplicationException;
import com.csk.epay.service.UserService;
import com.csk.epay.utils.DateUtil;
import com.csk.epay.vo.PaginationVO;
import com.csk.epay.vo.UserCondition;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource(name="userDao")
	private UserDao userDao;

	@Resource(name="permissionDao")
	private PermissionDao permissionDao;
	
	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			userDao.deleteById(id);
		}
	}

	@Override
	public PaginationVO<User> getByPage(UserCondition userCondition) {
		PaginationVO<User> paginationVO = new PaginationVO<User>();
		userCondition.setPageNo((userCondition.getPageNo()-1)*userCondition.getPageSize()); 
		paginationVO.setTotal(userDao.getTotal(userCondition));
		paginationVO.setDataList(userDao.getUsersByPage(userCondition));
		return paginationVO;
	}

	@Override
	public User getById(Integer id) {
		return userDao.getById(id);
	}

	@Override
	public User login(String accountNo, String password) {
		User user =  userDao.getByAccountNoAndPassword(accountNo,password);

		//账号或者密码是否正确
		if(user==null){
			throw new ApplicationException("账号或者密码错误");
		}
		//是否锁定
		if(User.LOCK_CODE.equals(user.getLockStatus())){
			throw new ApplicationException("账号已被锁定请联系管理员");
		}
		//是否失效
		if(user.getExpireTime()!=null&&user.getExpireTime()!=""&&DateUtil.getSystemTime().compareTo(user.getExpireTime())>0){
			throw new ApplicationException("账号已失效请联系管理员");			
		}
		//是否ip受限
//		if(user.getAllowIps()!=null&&user.getAllowIps().trim()!=""&&!"".equals(user.getAllowIps())&&!user.getAllowIps().contains(ip)){
//			throw new ApplicationException("ip受限请联系管理员");
//		}
		
		return user;
	}

	@Override
	public Set<String> getUrlsByUserId(Integer userId) {
		Set<String> urls = new HashSet<String>();
		List<Permission> permissions = permissionDao.getPermissionsByUserId(userId);
		for (Permission permission : permissions) {
			if(permission.getModuleUrl()!=null){
				urls.add(permission.getModuleUrl());
			}
			if(permission.getOperationUrl()!=null){
				//将数组中的元素放到集合中去
				Collections.addAll(urls, permission.getOperationUrl().split(","));
			}
		}
		return urls;
	}

}
