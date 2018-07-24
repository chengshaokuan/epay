package com.csk.epay.service;

import com.csk.epay.domain.Role;
import com.csk.epay.vo.PaginationVO;

public interface RoleService {
	
	/**
	 * 保存角色
	 * @param role
	 */
	void save(Role role);
	
	/**
	 * 删除角色
	 * @param ids
	 */
	void delele(Integer[] ids);
	
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
	 * 分页查询角色信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PaginationVO<Role> getByPage(Integer pageNo,Integer pageSize);
}
