package com.csk.epay.service;

import java.util.Set;

import com.csk.epay.domain.User;
import com.csk.epay.vo.PaginationVO;
import com.csk.epay.vo.UserCondition;

public interface UserService {
	
	/**
	 * 保存用户
	 * @param user
	 */
	void save(User user);

	/**
	 * @Description: 删除用户
	 * @param: ids
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 10:16 2018/7/26
	 */
	void delete(Integer[] ids);

	/**
	 * 分页查询
	 * @param userCondition
	 * @return
	 */
	PaginationVO<User> getByPage(UserCondition userCondition);

	/**
	 * 根据用户id获取用户对象
	 * @param id
	 * @return
	 */
	User getById(Integer id);

	/**
	 * 登录认证
	 * @param accountNo
	 * @param password
	 * @param ip
	 * @return
	 */
	User login(String accountNo, String password);

	/**
	 * 根据用户id获取当前用户能够操作的所有urls
	 * @param userId
	 * @return
	 */
	Set<String> getUrlsByUserId(Integer userId);
}
