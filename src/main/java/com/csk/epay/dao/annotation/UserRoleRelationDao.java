package com.csk.epay.dao.annotation;

import com.csk.epay.domain.Role;
import com.csk.epay.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface UserRoleRelationDao {

    /**
     * @Description: 保存用户角色关系
     * @param: userId
     * @param: roleId
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 16:04 2019/1/7
     */
    @Insert("insert into tbl_user_role_relation (user_id,role_id) values(#{0},#{1})")
    @SelectKey(statement = "select last_select_id()", before = false, resultType = int.class, keyProperty = "id")
    void save (Integer userId, Integer roleId);

    /**
     * @Description: 根据用户id获取当前用户已经分配的角色
     * @param: userId
     * @return: java.util.List<com.csk.epay.domain.Role>
     * @Author: Mr.Cheng
     * @Date: 16:04 2019/1/7
     */
    @Select("select * from tbl_role where id in(select role_id from tbl_user_role_relation where user_id = #{userId})")
    List<Role> getAssignedRolesByUserId (Integer userId);

    /**
     * @Description: 根据用户id获取当前用户未分配角色列表
     * @param: userId
     * @return: java.util.List<com.csk.epay.domain.Role>
     * @Author: Mr.Cheng
     * @Date: 16:04 2019/1/7
     */
    @Select("SELECT * FROM tbl_role WHERE CODE != '000' AND id NOT IN (SELECT role_id FROM tbl_user_role_relation WHERE user_id = #{userId})")
    List<Role> getUnAssignedRolesByUserId (Integer userId);

    /**
     * @Description: 根据用户id和角色id到关系表中查找对应的关系记录
     * @param: userId
     * @param: roleId
     * @return: java.lang.Long
     * @Author: Mr.Cheng
     * @Date: 16:04 2019/1/7
     */
    @Select("select count(*) from tbl_user_role_relation where user_id = #{0} and role_id = #{1}")
    Long getCountByUserIdAndRoleId (Integer userId, Integer roleId);

    /**
     * @Description: 删除用户角色关系记录
     * @param: userId
     * @param: roleId
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 16:05 2019/1/7
     */
    @Delete("delete from tbl_user_role_relation where userId=#{0} and roleId =#{1}")
    void delete (Integer userId, Integer roleId);

}
