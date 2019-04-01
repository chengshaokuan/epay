package com.csk.epay.dao;

import com.csk.epay.domain.User;
import com.csk.epay.vo.UserCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao {

    /**
     * @Description: 保存用户
     * @param: tableNum
     * @param: user
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:40 2019/4/1
     */
    void save (@Param("tableNum") int tableNum, @Param("user") User user);

    /**
     * @Description: 删除用户
     * @param: table
     * @param: id
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:44 2019/4/1
     */
    void deleteById (@Param("table") int table, @Param("id") Integer id);

    /**
     * @Description: 获取总记录条数
     * @param: userCondition
     * @return: java.lang.Long
     * @Author: Mr.Cheng
     * @Date: 14:45 2019/4/1
     */
    Long getTotal (UserCondition userCondition);

    /**
     * @Description: 获取分页查询的数据
     * @param: userCondition
     * @return: java.util.List<com.csk.epay.domain.User>
     * @Author: Mr.Cheng
     * @Date: 14:45 2019/4/1
     */
    List<User> getUsersByPage (UserCondition userCondition);

    /**
     * @Description: 根据用户id获取用户对象
     * @param: id
     * @return: com.csk.epay.domain.User
     * @Author: Mr.Cheng
     * @Date: 14:45 2019/4/1
     */
    User getById (Integer id);

    /**
     * @Description: 根据用户账号和密码获取用户对象
     * @param: accountNo
     * @param: password
     * @return: com.csk.epay.domain.User
     * @Author: Mr.Cheng
     * @Date: 14:45 2019/4/1
     */
    User getByAccountNoAndPassword (String accountNo, String password);

    /**
     * @Description:
     * @param: accoutNo
     * @return: com.csk.epay.domain.User
     * @Author: Mr.Cheng
     * @Date: 17:37 2019/4/1
     */
    User getByAccountNo(List<String> accoutNo);
}
