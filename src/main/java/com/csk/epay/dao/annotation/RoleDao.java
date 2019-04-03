package com.csk.epay.dao.annotation;

import com.csk.epay.domain.Role;
import com.csk.epay.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleDao {
	
	/**
	 * @Description:  保存角色
	 * @param: role
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 11:06 2019/1/7
	 */
	@Insert("insert into tbl_role (code,name,remark,createTime)" +
			"values(#{code},#{name},#{remark},#{createTime})")
	@SelectKey(keyProperty = "id",keyColumn = "id",before = false,statement = "select @@identity",resultType = int.class)
	void save (Role role);

	/**
	 * @Description:  根据角色id删除角色
	 * @param: id
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 11:48 2019/1/7
	 */
	@Delete("delete from tbl_role where id = #{id}")
	void deleteById (Integer id);

	/**
	 * @Description:  根据角色id获取角色对象
	 * @param: id
	 * @return: com.csk.epay.domain.Role
	 * @Author: Mr.Cheng
	 * @Date: 14:13 2019/1/7
	 */
	@Select("select * from tbl_role where id= #{1}")
	Role getById (Integer id);

	/**
	 * @Description:  修改角色
	 * @param: role
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 14:15 2019/1/7
	 */
	@Update("update tbl_role set code = #{code}," +
			"name = #{name}," +
			"remark = #{remark}," +
			"editTime = #{editTime}" +
			"where id = #{id}")
	void update (Role role);

	/**
	 * @Description: 获取总记录条数
	 * @param:
	 * @return: java.lang.Long
	 * @Author: Mr.Cheng
	 * @Date: 14:15 2019/1/7
	 */
	@Select("select count(*) from tbl_role")
	Long getTotal ();

	/**
	 * @Description:  获取当前页记录
	 * @param: pageNo
	 * @param: pageSize
	 * @return: java.util.List<com.csk.epay.domain.Role>
	 * @Author: Mr.Cheng
	 * @Date: 14:17 2019/1/7
	 */
	@Select("select * from tbl_role where code!='000' limit #{0},#{1}")
	List<Role> getRolesByPage (Integer pageNo, Integer pageSize);
}
