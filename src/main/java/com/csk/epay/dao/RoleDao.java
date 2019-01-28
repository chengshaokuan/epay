package com.csk.epay.dao;

import java.util.List;

import com.csk.epay.domain.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleDao {
	
	/**
	 * 保存角色
	 * @param role
	 */
	void save(Role role);

	/**
	 * 根据角色id删除角色
	 * @param id
	 */
	void deleteById(Integer id);
	
	/**
	 * 根据角色id获取角色对象
	 * @param id
	 * @return
	 */
	Role getById(Integer id);
	
	/**
	 * 修改角色
	 * @param role
	 */
	void update(Role role);
	
	/**
	 * 获取总记录条数
	 * @return
	 */
	Long getTotal();
	
	/**
	 * 获取当前页记录
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Role> getRolesByPage(Integer pageNo,Integer pageSize);


	List<Role> getRolesByCode(@Param("name") String code);


}
