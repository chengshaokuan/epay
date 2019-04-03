package com.csk.epay.dao.annotation;

import com.csk.epay.domain.Permission;
import com.csk.epay.domain.Role;
import com.csk.epay.domain.User;
import com.csk.epay.utils.timeUtil.TimeUtil;
import com.csk.epay.vo.LogCondition;
import com.csk.epay.vo.UserCondition;
import com.csk.epay.domain.Permission;
import com.csk.epay.domain.Role;
import com.csk.epay.domain.User;
import com.csk.epay.utils.timeUtil.TimeUtil;
import com.csk.epay.vo.LogCondition;
import com.csk.epay.vo.UserCondition;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: epay
 * @description: mybatis注解测试
 * @author: Mr.Cheng
 * @create: 2018-07-27 17:42
 **/
public class mybatisAnnotationTest {

    public SqlSession getSqlSession () {
        SqlSession sqlSession = null;
        try {
            /*
             * 1.加载mybatis的配置文件，初始化mybatis，创建出SqlSessionFactory，是创建SqlSession的工厂
             * 这里只是为了演示的需要，SqlSessionFactory临时创建出来，在实际的使用中，SqlSessionFactory只需要创建一次，当作单例来使用
             *
             * 从SqlSession工厂 SqlSessionFactory中创建一个SqlSession，进行数据库操作
             */
//          InputStream resourceAsStream = mybatisTest.class.getClassLoader().getResourceAsStream(resource);
            InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-annotationconfig.xml");
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
            sqlSession = build.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }

    public static void close (SqlSession session) {
        if (session != null) {
            session.commit();
            session.close();
        }
    }

    /**
     * @Description:
     * @param:
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 17:04 2019/1/4
     */
    @Test
    public void PermissionDaoTest () {

        SqlSession sqlSession = getSqlSession();
        PermissionDao permissionDao = sqlSession.getMapper(PermissionDao.class);
        List<Permission> lists = new ArrayList<>();
        Permission permission = null;
        permission = new Permission();
        permission.setCode("000000" );
        permission.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        permission.setName("姓名");
        permission.setModuleUrl("http://www.**.com");
        permission.setPid(100 );
        lists.add(permission);
//            permission.setChildNodes(lists);
//        int save = permissionDao.saveToAll(lists);
////        lists.stream().forEach(s -> System.err.println(s.getId()));

//        int i = permissionDao.saveToOne(permission);
//        System.out.println(permission.getId());
//        List<Permission> all = permissionDao.getAll();
//        all.stream().forEach(System.out::println);
        Permission byCode = permissionDao.getByCode1("aaaaaaa");
        System.out.println(byCode);
        close(sqlSession);
    }

    @Test
    public void RoleDao () {
        SqlSession sqlSession = this.getSqlSession();
//        List<Object> objects = sqlSession.selectList("com.csk.epay.dao.UserDao.getById", 3);
//        objects.stream().forEach(System.err::println);
        RoleDao mapper = sqlSession.getMapper(RoleDao.class);
        Role role = new Role();
        role.setCode("1001");
        role.setName("普通员工");
        role.setRemark("就是普通一号");
        role.setCreateTime(TimeUtil.getNowTimeNormalString());
        mapper.save(role);

        System.out.println(role.getId());
        close(sqlSession);
    }

    @Test
    public void UserDao () {
        SqlSession sqlSession = this.getSqlSession();
//        List<Object> objects = sqlSession.selectList("com.csk.epay.dao.UserDao.getById", 3);
//        objects.stream().forEach(System.err::println);
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User role = new User();
        UserCondition userCondition = new UserCondition();
        userCondition.setName("普通");
        userCondition.setLockStatus(1);
        role.setLockStatus(1);
        role.setName("普通员工");
        role.setPassword("没有密码");
        role.setCreateTime(TimeUtil.getNowTimeNormalString());
//        mapper.save(role);
        System.out.println(role.getId());
        System.out.println(mapper.getTotal(userCondition));
        close(sqlSession);
    }

    @Test
    public void LogDao () {
        SqlSession sqlSession = this.getSqlSession();
        OperationLogDao mapper = sqlSession.getMapper(OperationLogDao.class);
        LogCondition logCondition = new LogCondition();
        logCondition.setEndTime("2018-07-31 16:54:20");
        Long total = mapper.getTotal(logCondition);
        System.out.println(total);
        close(sqlSession);
    }
}

