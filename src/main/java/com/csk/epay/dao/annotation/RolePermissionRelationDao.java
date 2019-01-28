package com.csk.epay.dao.annotation;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface RolePermissionRelationDao {

	/**
	 * @Description:  保存角色许可关系
	 * @param: roleId
	 * @param: permissionId
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 14:23 2019/1/7
	 */
	@Insert("insert into tbl_role_permission_relation (role_id,permission_id) values(#{0},#{1})")
	@SelectKey(keyProperty = "id",statement = "select @@identity",resultType = int.class,before = false)
	void save (Integer roleId, Integer permissionId);

	/**
	 * @Description:  根据角色id删除该角色对应的关系记录
	 * @param: roleId
	 * @return: void
	 * @Author: Mr.Cheng
	 * @Date: 14:25 2019/1/7
	 */
	@Delete("delete from tbl_role_permission_relation where role_id = #{roleId}")
	void deleteByRoleId (Integer roleId);

	/**
	 * @Description:  根据角色id和许可id获取关系表中的记录数
	 * @param: roleId
	 * @param: permissionId
	 * @return: java.lang.Long
	 * @Author: Mr.Cheng
	 * @Date: 14:26 2019/1/7
	 */
	@Select("select count(*) from tbl_role_permission_relation where role_id = #{0} and permission_id = #{1}")
	Long getCountByRoleIdAndPermissionId (Integer roleId, Integer permissionId);

	/**
	 * @Description:  根据角色id获取当前这个角色拥有的所有许可ids
	 * @param: roleId
	 * @return: java.util.List<java.lang.Integer>
	 * @Author: Mr.Cheng
	 * @Date: 14:27 2019/1/7
	 */
	@Select("select permission_id from tbl_role_permission_relation where role_id = #{roleId}")
	List<Integer> getPermissionIdsByRoleId (Integer roleId);

}
