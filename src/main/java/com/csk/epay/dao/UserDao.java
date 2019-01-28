package com.csk.epay.dao;

import java.util.List;
import java.util.Map;

import com.csk.epay.domain.User;
import com.csk.epay.vo.UserCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


public interface UserDao {
	
	/**
	 * 保存用户
	 * @param user
	 */
	void save(@Param("tableNum") int tableNum,@Param("user") User user);

	List<User> getUser(@Param("tableNum") int tableNum,@Param("id") String id);


	/**
	 * 删除用户
	 * @param
	 */
	void deleteById(@Param("table") int table,@Param("id") Integer id);

	/**
	 * 获取总记录条数
	 * @param userCondition
	 * @return
	 */
	Long getTotal(UserCondition userCondition);

	/**
	 * 获取分页查询的数据
	 * @param userCondition
	 * @return
	 */
	List<User> getUsersByPage(UserCondition userCondition);

	/**
	 * 根据用户id获取用户对象
	 * @param id
	 * @return
	 */
	User getById(Integer id);

	/**
	 * 根据用户账号和密码获取用户对象
	 * @param accountNo
	 * @param password
	 * @return
	 */
	User getByAccountNoAndPassword(String accountNo, String password);
}
