package com.csk.epay.dao;

import com.csk.epay.domain.Permission;
import com.csk.epay.domain.Role;
import com.csk.epay.utils.timeUtil.TimeUtil;
import com.csk.epay.utils.util.ChineseName;
import com.csk.epay.utils.util.RandomChar;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: epay
 * @description: mapper测试
 * @author: Mr.Cheng
 * @create: 2018-07-27 17:42
 **/
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/spring-base.xml")
@Slf4j
public class mybatisTest {

    @Test
    public void jdbcTest () {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取数据库连接
            String url = "jdbc:mysql://localhost:3306/db";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            //关闭自动提交，开启事务
            conn.setAutoCommit(false);
            //3.定义SQL语句框架
            String sql = "select * from TABLE_NAME where id = ? for UPDATE ";
            //4.进行SQL语句预编译
            ps = conn.prepareStatement(sql);
            //5.进行赋值
            ps.setInt(1, 6);
            //6.执行SQL
            rs = ps.executeQuery();
            while (rs.next()) {
                String code = rs.getString("number");
                System.out.println(code + " ");
            }
            //提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //事务的回滚
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            //关闭资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public List<SqlSession> getSqlSession () {
        String resourceConfig = "mybatis-config.xml";
        ArrayList<SqlSession> list = new ArrayList<>();
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream(resourceConfig);
//          InputStream resourceAsStream = mybatisTest.class.getClassLoader().getResourceAsStream(resource);
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
            SqlSession sqlSession = build.openSession();
            list.add(sqlSession);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void close (SqlSession sqlSession) {
        sqlSession.commit();
        sqlSession.close();
        sqlSession.clearCache();
    }

    @Test
    public void PermissionDao () {

        List<SqlSession> sqlSession = this.getSqlSession();
        SqlSession sqlSession1 = sqlSession.get(0);
        PermissionDao permissionDao1 = sqlSession1.getMapper(PermissionDao.class);
//        List<Object> list = sqlSession2.selectList("com.csk.epay.dao.PermissionDao.getAll");

        /**
         * getMapper()得到实例，permissionDao1.getById(198)实例调用方法的时候，动态代理会拦截方法，
         * 根据sql类型，最终会跳转到permissionDao1.select("statementId",param)这种模式，
         * 然后根据statementID ,在Configuration中查找SQL语句，调用Executor中的query(查找的sql语句，
         * 参数param,等)方法
         *
         */
        Permission byId = permissionDao1.getByCode("(select code from tbl_permission where code ='姓名6')");
        sqlSession1.close();
        sqlSession1.clearCache();
//        list.stream().forEach(System.out::println);
           /* Permission permission = new Permission();
            permission.setId(2);
            permission.setName("chengsk");
            permission.setCode("编码");
            permission.setModuleUrl("http://xxx.com");
            permissionDao.save(permission);*/
//        List<Permission> lists = new ArrayList<>();
//        Permission permission = new Permission();
//        Permission permission1 = new Permission();
//        permission.setCode("aaaaaaa");
//        permission.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        permission.setName("aaaaaa");
//        permission.setModuleUrl("aaaaa");
//        permission.setPid(106);
//        permission1.setCode("bbbbbbb");
//        permission1.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        permission1.setName("bbbbbb");
//        permission1.setModuleUrl("bbbbbbb");
//
//        lists.add(permission);
//        lists.add(permission1);
////            permission.setChildNodes(lists);
//        int save = permissionDao.save(lists);
//        Integer id = permission.getId();
//        lists.stream().forEach(s -> System.err.println(s.getId()));
//            List<Permission> aaaaaaa = permissionDao.getByCode1("aaaaaaa");
//            aaaaaaa.stream().forEach(System.out::println);

    }

    @Test
    public void RoleDao () {

        List<SqlSession> sqlSession = this.getSqlSession();
        SqlSession sqlSession1 = sqlSession.get(0);
        RoleDao mapper = sqlSession1.getMapper(RoleDao.class);

        Role role = new Role();
        role.setCode("00000");
        role.setName(ChineseName.getChineseName());
        role.setRemark("随机产生:" + RandomChar.getRandomChar(5));
        role.setCreateTime(TimeUtil.getNowTimeNormalString());

        mapper.save(role);
        close(sqlSession1);
        System.out.println(role.getId());

    }

}

