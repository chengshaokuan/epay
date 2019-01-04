package com.csk.epay.dao;

import com.csk.epay.domain.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PermissionDao {

    /**
     * 保存许可
     *
     * @param permission
     */
   /* @Insert("<script>"+
            "insert into tbl_permission(code,name,moduleUrl,operationUrl,order_no,create_time,pid) values " +
            "<foreach collection='list' item='item' index='index' separator=','>" +
            "(#{item.code},#{item.name},#{item.moduleUrl},#{item.operationUrl},#{item.orderNo},#{item.createTime},#{item.pid})" +
            "</foreach>" +
            "</script>")
	  @Options(useGeneratedKeys = true)*/
//	  @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = int.class, before = false)//可以将值插入到实体类
//    @SelectKey(statement = "SELECT @@identity", keyProperty = "id", resultType = int.class, before = false)


//    @Insert("insert into tbl_permission(code,name,moduleUrl,operationUrl,order_no,create_time,pid) values (#{code},#{name},#{moduleUrl},#{operationUrl},#{orderNo},#{createTime},#{pid})")
////	@Options(useGeneratedKeys = true)
////	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = int.class, before = false)可以将值插入到实体类
//    @SelectKey(statement = "SELECT @@identity", keyProperty = "id", resultType = int.class, before = false)
     int save (List<Permission> permission);
    int save (Permission permission);

    /**
     * 根据id删除许可
     *
     * @param id
     */
    void deleteById (Integer id);

    /**
     * 根据id获取许可对象
     * @param id
     * @return
     */
    Permission getById (Integer id);

    /**
     * 修改许可
     *
     * @param permission
     */
    void update (Permission permission);

    /**
     * 获取所有许可
     * @return
     */
    List<Permission> getAll ();

    /**
     * 根据许可代码获取许可对象
     *
     * @param code
     * @return
     */
    @Select("select tr.* from tbl_permission tp,tbl_role tr where tp.code = tr.code and tp.code = #{code}")
    @ResultMap("com.csk.epay.dao.PermissionDao.permissionMap")
    List<Permission> getByCode1 (String code);


    Permission getByCode (String code);

    /**
     * 根据许可名称和父许可id获取许可对象
     *
     * @param name
     * @param pid
     * @return
     */
    Permission getByNameAndPid (String name, Integer pid);

    /**
     * 根据用户id获取当前用户拥有的所有许可
     *
     * @param userId
     * @return
     */
    List<Permission> getPermissionsByUserId (Integer userId);


}
