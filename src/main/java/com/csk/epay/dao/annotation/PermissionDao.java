package com.csk.epay.dao.annotation;

import com.csk.epay.domain.Permission;
import com.csk.epay.domain.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PermissionDao {


    /**
     * @Description: 遍历插入返回插入个数，主键数返回到实体类中
     * @param: permission
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 15:52 2019/1/4
     */
    @Insert("<script>" +
            "insert into tbl_permission(code,name,moduleUrl,operationUrl,order_no,create_time,pid) values " +
            "<foreach collection='list' item='item' index='index' separator=','>" +
            "(#{item.code},#{item.name},#{item.moduleUrl},#{item.operationUrl},#{item.orderNo},#{item.createTime},#{item.pid})" +
            "</foreach>" +
            "</script>")
    //返回主键值到实体类中
    @Options(useGeneratedKeys = true)
    int saveToAll (List<Permission> permission);

    @Insert("insert into tbl_permission(code,name,moduleUrl,operationUrl,order_no,create_time,pid) values (#{code},#{name},#{moduleUrl},#{operationUrl},#{orderNo},#{createTime},#{pid})")
    //以下三种注解任意一种都可以返回主键值到实体类中。
    @Options(useGeneratedKeys = true)
//	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = int.class, before = false)
//  @SelectKey(statement = "SELECT @@identity", keyProperty = "id", resultType = int.class, before = false)
    int saveToOne (Permission permission);

    /**
     * @Description: 根据id删除许可
     * @param: id
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 17:14 2019/1/4
     */
    @Delete("<script>" +
            "delete from tbl_permission " +
            "<where>" +
            "<if test=\"id!=null\">id = #{id}</if>" +
            "</where>" +
            "</script>")
    //不使用@Param注解时，参数只能有一个，并且是Javabean。
    // 在SQL语句里可以引用JavaBean的属性，而且只能引用JavaBean的属性。
    //如果不引用@Param注解，会报错
    void deleteById (@Param("id") Integer id);

    /**
     * @Description: 根据id获取许可对象
     * @param: id
     * @return: com.csk.epay.domain.Permission
     * @Author: Mr.Cheng
     * @Date: 17:38 2019/1/4
     */
    @Select("<script>select * from tbl_permission where id=#{id}</script>")
    Permission getById (Integer id);

    /**
     * @Description: 修改许可
     * @param: permission
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 18:34 2019/4/1
     */
    @Update("update tbl_permission set" +
            "tcode = #{code}," +
            "name = #{name}," +
            "moduleUrl = #{moduleUrl}," +
            "operationUrl = #{operationUrl}," +
            "orderNo = #{orderNo}," +
            "editTime = #{editTime}" +
            "where id = #{id}")
    void update (Permission permission);

    /**
     * @Description:  获取所有许可
     * @param:
     * @return: java.util.List<com.csk.epay.domain.Permission>
     * @Author: Mr.Cheng
     * @Date: 18:34 2019/4/1
     */
    @Select("select * from tbl_permission order by order_no")
    List<Permission> getAll ();

    /**
     * @Description: 根据许可代码获取许可对象
     * @param: code
     * @return: java.util.List<com.csk.epay.domain.Permission>
     * @Author: Mr.Cheng
     * @Date: 18:05 2019/1/4
     */
    @Select("select * from tbl_permission  where code = #{code}")
    //@ResultMap可以引用xml配置文件
    @ResultMap("com.csk.epay.dao.annotation.PermissionDao.permissionMap")
    //或者使用@Results格式也能解决pojo和表中字段不对应问题
//    @Results({
//            @Result(column = "order_no",property = "orderNo"),
//            @Result(column = "create_time",property = "createTime")})
    //如果下面还使用这种对应关系，可以直接使用@ResultMap("a")来调用这个@Results
//    @Results(id="a",value = {
//            @Result(column = "order_no",property = "orderNo"),
//            @Result(column = "create_time",property = "createTime")})
    Permission getByCode1 (String code);


    /**
     * @Description: 模糊查询返回多条记录
     * @param: code
     * @return: java.util.List<com.csk.epay.domain.Permission>
     * @Author: Mr.Cheng
     * @Date: 10:24 2019/1/7
     */
    @Select("<script>" +
            "select * from tbl_permission <where><if test=\"code!=null\">code like concat(\"%\",#{code},\"%\")</if></where></script>")
    List<Permission> getByCode (@Param("code") String code);

    /**
     * @Description: 根据许可名称和父许可id获取许可对象
     * @param: name
     * @param: pid
     * @return: com.csk.epay.domain.Permission
     * @Author: Mr.Cheng
     * @Date: 18:06 2019/1/4
     */
    @Select("select * from tbl_permission where name = #{0} and pid = #{1}")
    Permission getByNameAndPid (String name, Integer pid);

    /**
     * @Description: 根据用户id获取当前用户拥有的所有许可
     * @param: userId
     * @return: java.util.List<com.csk.epay.domain.Permission>
     * @Author: Mr.Cheng
     * @Date: 18:06 2019/1/4
     */
    @Select("SELECT * FROM tbl_permission WHERE id IN " +
            "(SELECT permission_id FROM tbl_role_permission_relation WHERE role_id IN " +
            "(SELECT role_id FROM tbl_user_role_relation WHERE user_id = #{userId}))")
    List<Permission> getPermissionsByUserId (Integer userId);


}
