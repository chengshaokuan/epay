package com.csk.epay.dao;

import com.csk.epay.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao {

    /**
     * @Description: 保存许可
     * @param: permission
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 17:38 2019/1/7
     */
    int save (Permission permission);

    /**
     * @Description: 根据id删除许可
     * @param: id
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 17:38 2019/1/7
     */
    void deleteById (Integer id);

    /**
     * @Description: 修改许可
     * @param: permission
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 17:38 2019/1/7
     */
    void update (Permission permission);

    /**
     * @Description: 根据id获取许可对象
     * @param: id
     * @return: com.csk.epay.domain.Permission
     * @Author: Mr.Cheng
     * @Date: 17:38 2019/1/7
     */
    Permission getById (Integer id);

    /**
     * @Description: 获取所有许可
     * @param:
     * @return: java.util.List<com.csk.epay.domain.Permission>
     * @Author: Mr.Cheng
     * @Date: 17:38 2019/1/7
     */
    List<Permission> getAll ();

    /**
     * @Description: 根据许可代码获取许可对象
     * @param: code
     * @return: com.csk.epay.domain.Permission
     * @Author: Mr.Cheng
     * @Date: 14:28 2019/1/8
     */
    Permission getByCode (@Param("code") String code);

    /**
     * @Description: 根据许可名称和父许可id获取许可对象
     * @param: name
     * @param: pid
     * @return: com.csk.epay.domain.Permission
     * @Author: Mr.Cheng
     * @Date: 17:39 2019/1/7
     */
    Permission getByNameAndPid (String name, Integer pid);

    /**
     * @Description: 根据用户id获取当前用户拥有的所有许可
     * @param: userId
     * @return: java.util.List<com.csk.epay.domain.Permission>
     * @Author: Mr.Cheng
     * @Date: 17:39 2019/1/7
     */
    List<Permission> getPermissionsByUserId (Integer userId);


}
