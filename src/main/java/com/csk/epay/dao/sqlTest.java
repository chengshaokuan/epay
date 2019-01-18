package com.csk.epay.dao;

import com.csk.epay.domain.Role;
import com.csk.epay.utils.timeUtil.JodaTimeUtil;
import com.csk.epay.utils.util.RandomChar;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * @program: epay
 * @description:
 * @author: Mr.Cheng
 * @create: 2019-01-14 11:15
 **/
public class sqlTest {


    public char RandomHan () {
        Random ran = new Random();
        final int delta = 0x9fa5 - 0x4e00 + 1;
        return (char) (0x4e00 + ran.nextInt(delta));

    }

    @Test
    public void R () {
        for (int i = 0; i < 200; i++) {
            String randomChar = RandomChar.getRandomChar(1);
            System.out.print(randomChar);
        }
        for (int i = 1; i <= 10000; i++) {

            char c = (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
            if (i % 10 == 0) {
                System.out.println("");
            }
            System.out.print(c);
        }
    }

    public SqlSession getSqlSession () {
        String resourceConfig = "mybatis-config.xml";
        SqlSession sqlSession = null;
        try {

            InputStream resourceAsStream = Resources.getResourceAsStream(resourceConfig);
//          InputStream resourceAsStream = mybatisTest.class.getClassLoader().getResourceAsStream(resource);
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
            sqlSession = build.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }

    public void close (SqlSession sqlSession) {
        sqlSession.commit();
        sqlSession.close();
        sqlSession.clearCache();
    }

    @Test
    public void sqlTest () {
        long lon = 0;

        SqlSession sqlSession = this.getSqlSession();
        RoleDao mapper = sqlSession.getMapper(RoleDao.class);
        List<Role> rolesByCode = null;
        long timestamp = JodaTimeUtil.timestamp();
        rolesByCode = mapper.getRolesByCode("贝胺管");
        long timestamp1 = JodaTimeUtil.timestamp();
        long l = timestamp1 - timestamp;
        System.err.println(timestamp1 - timestamp);

        lon = lon + l;
        rolesByCode.forEach(System.out::println);
        System.out.println(lon);
        close(sqlSession);

//        }
    }

}
