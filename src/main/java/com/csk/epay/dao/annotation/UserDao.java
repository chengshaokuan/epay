package com.csk.epay.dao.annotation;

import com.csk.epay.domain.User;
import com.csk.epay.vo.UserCondition;
import com.csk.epay.domain.User;
import com.csk.epay.vo.UserCondition;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface UserDao {

    /**
     * @Description: 保存用户
     * @param: user
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 11:27 2019/1/7
     */
    @Insert("insert into tbl_user(accountNo,name,email,password,lockStatus,expireTime,allowIps,createTime)" +
            "values(#{accountNo},#{name},#{email},#{password},#{lockStatus},#{expireTime},#{allowIps},#{createTime})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", resultType = int.class, before = false)
    void save (User user);

    /**
     * @Description: 删除用户
     * @param: id
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:30 2019/1/7
     */
    @Delete("delete from tbl_user where id = #{id}")
    void deleteById (Integer id);

    /**
     * @Description: 获取总记录条数
     * @param: userCondition
     * @return: java.lang.Long
     * @Author: Mr.Cheng
     * @Date: 14:31 2019/1/7
     */
    //限制条件中值为空的时候，不包含在accountNo！='admin'之内
    //大小写匹配'%' || UPPER('aa') || '%'或者LIKE '%' ||LOWER('aaaa') || '%'
    // 拼接匹配 LIKE '%${text}%';
    @Select("<script>select count(*) from tbl_user " +
            "<where> accountNo!='admin' and name like concat('%',#{name},'%')" +
            "<if test=\"startTime!=null and startTime!=''\">and expireTime=#{startTime}</if>" +
            "<if test=\"endTime !=null and endTime !=''\">and expireTime =#{endTime}</if>" +
            "<if test=\"lockStatus!=0\">and lockStatus = #{lockStatus}</if>" +
            "</where></script>")
    Long getTotal (UserCondition userCondition);

    /**
     * @Description: 获取分页查询的数据
     * @param: userCondition
     * @return: java.util.List<com.csk.epay.domain.User>
     * @Author: Mr.Cheng
     * @Date: 15:56 2019/1/7
     */
    @Select("<script>select * from tbl_user  where accountNo!='admin'" +
            "<if test=\"userName!=null and userName !=''\">and name like #{userName} '%' </if>" +
            "<if test=\"startTime!=null and startTime!=''\">and expireTime=#{startTime}</if>" +
            "<if test=\"endTime !=null and endTime !=''\">and expireTime &lt;=#{endTime}</if>" +
            "<if test=\"lockStatus!=0\">and lockStatus = #{lockStatus}</if>" +
            "limit #{pageNo},#{pageSize}</script>")
    List<User> getUsersByPage (UserCondition userCondition);

    /**
     * @Description:  根据用户id获取用户对象
     * @param: id
     * @return: com.csk.epay.domain.User
     * @Author: Mr.Cheng
     * @Date: 16:01 2019/1/7
     */
    @Select("select * from tbl_user where id = #{id}")
    //如果其他查询语句，可以直接使用@ResultMap("】a")来调用这个@Results
    @Results(id = "a", value = {
            @Result(column = "accountNo", property = "accountNo"),
            @Result(column = "id", property = "id")
    })
    User getById (Integer id);

    /**
     * @Description:  根据用户账号和密码获取用户对象
     * @param: accountNo
     * @param: password
     * @return: com.csk.epay.domain.User
     * @Author: Mr.Cheng
     * @Date: 16:02 2019/1/7
     */
    @Select("select * from tbl_user where accountNo = #{0} and password = #{1}")
    User getByAccountNoAndPassword (String accountNo, String password);


}
