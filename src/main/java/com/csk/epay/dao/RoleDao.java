package com.csk.epay.dao;

import com.csk.epay.domain.Role;
import com.csk.epay.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {
	
	/**
	 * @Description:  保存角色
	 * @param: role
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 14:41 2019/4/1
	 */
	void save(Role role);

	/**
	 * @Description:  根据角色id删除角色
	 * @param: id
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 14:41 2019/4/1
	 */
	void deleteById(Integer id);
	
	/**
	 * @Description:  根据角色id获取角色对象
	 * @param: id
	 * @return: com.csk.epay.domain.Role
	 * @Author: Mr.Cheng
	 * @Date: 14:41 2019/4/1
	 */
	Role getById(Integer id);
	
	/**
	 * @Description:  修改角色
	 * @param: role
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 14:42 2019/4/1
	 */
	void update(Role role);
	
	/**
	 * @Description:  获取总记录条数
	 * @param:
	 * @return: java.lang.Long
	 * @Author: Mr.Cheng
	 * @Date: 14:42 2019/4/1
	 */
	Long getTotal();
	
	/**
	 * @Description:  获取当前页记录
	 * @param: pageNo
	 * @param: pageSize
	 * @return: java.util.List<com.csk.epay.domain.Role>
	 * @Author: Mr.Cheng
	 * @Date: 14:42 2019/4/1
	 */
	List<Role> getRolesByPage(Integer pageNo,Integer pageSize);


	List<Role> getRolesByCode(@Param("name") String code);


}
