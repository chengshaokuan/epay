package com.csk.epay.dao;

import com.csk.epay.domain.Role;
import com.csk.epay.domain.Role;

import java.util.List;

public interface UserRoleRelationDao {

    /**
     * @Description: 保存用户角色关系
     * @param: userId
     * @param: roleId
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:48 2019/4/1
     */
    void save (Integer userId, Integer roleId);

    /**
     * @Description: 删除用户角色关系记录
     * @param: userId
     * @param: roleId
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:48 2019/4/1
     */
    void delete (Integer userId, Integer roleId);

    /**
     * @Description: 根据用户id获取当前用户已经分配的角色
     * @param: userId
     * @return: java.util.List<com.csk.epay.domain.Role>
     * @Author: Mr.Cheng
     * @Date: 14:47 2019/4/1
     */
    List<Role> getAssignedRolesByUserId (Integer userId);

    /**
     * @Description: 根据用户id获取当前用户未分配角色列表
     * @param: userId
     * @return: java.util.List<com.csk.epay.domain.Role>
     * @Author: Mr.Cheng
     * @Date: 14:47 2019/4/1
     */
    List<Role> getUnAssignedRolesByUserId (Integer userId);

    /**
     * @Description: 根据用户id和角色id到关系表中查找对应的关系记录
     * @param: userId
     * @param: roleId
     * @return: java.lang.Long
     * @Author: Mr.Cheng
     * @Date: 14:48 2019/4/1
     */
    Long getCountByUserIdAndRoleId (Integer userId, Integer roleId);

}
