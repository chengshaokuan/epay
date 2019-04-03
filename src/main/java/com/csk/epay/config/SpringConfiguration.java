package com.csk.epay.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @program: epay
 * @description: mybatis的注解式配置,尽量使用xml配置
 * @author: Mr.Cheng
 * @create: 2019-01-08 10:44
 **/
@Configuration //表示配置文件类
//@ComponentScan("com.csk.epay.service.impl") //扫描的包 ，代替 <context:component-scan base-package=""/>

//添加配置文件,使用@Value("${key的值}")
@PropertySource(value = {"classpath:jdbc.properties",}, ignoreResourceNotFound = true)

//@ImportResource(value = {"classpath:/spring-mvc.xml"})   // 引入配置文件
//指导需要注入数据原的包路径
@MapperScan(basePackages = {"classpath:com/csk/epay/dao/mapper/*.xml"}, sqlSessionFactoryRef = "sqlSessionFactory")
//@EnableTransactionManagement //开启事务管理
public class SpringConfiguration {

    @Value("${jdbc.user}")
    private String Username;
    @Value("${jdbc.user}")
    private String Password;
    @Value("${jdbc.user}")
    private String Url;
    @Value("${jdbc.user}")
    private String DriverClassName;
    @Value("${mapperLocations}")
    private String MapperLocations;
    @Value("${typeAliasesPackage}")
    private String TypeAliasesPackage;


    @Autowired
    private Environment env;

    /**
     * @Description: 配置数据源
     * @param: propertiesConfig
     * @return: javax.sql.DataSource
     * @Author: Mr.Cheng
     * @Date: 10:56 2019/1/8
     */
    @Bean
    public DataSource dataSource () {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(Username);
        dataSource.setPassword(Password);
        dataSource.setUrl(Url);
        dataSource.setDriverClassName(DriverClassName);
        return dataSource;
    }

    /**
     * @Description: 配置spring的声明式事务
     * @param: dataSource
     * @return: org.springframework.transaction.PlatformTransactionManager
     * @Author: Mr.Cheng
     * @Date: 11:00 2019/1/8
     */
    @Bean
    public PlatformTransactionManager transactionManager (DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;

    }

    /**
     * @Description:
     * @param:
     * @return: org.springframework.context.support.PropertySourcesPlaceholderConfigurer
     * @Author: Mr.Cheng
     * @Date: 11:00 2019/1/8
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer () {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }

    /**
     * @Description: 配置mybatis的SqlSessionFactoryBean
     * @param: dataSource
     * @return: org.mybatis.spring.SqlSessionFactoryBean
     * @Author: Mr.Cheng
     * @Date: 11:35 2019/1/8
     */
    @Bean // 带参数的bean ,有参数的bean方法。 spring会从ioc中找对应的bean注入，如果ico中没有会报错
    public SqlSessionFactoryBean sqlSessionFactory (DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //mybatis的xml文件路径
//        sessionFactoryBean.setMapperLocations(resolver.getResources(MapperLocations));
        //以下是读取配置的值得两种方法
        sessionFactoryBean.setTypeAliasesPackage(TypeAliasesPackage);
//        sessionFactoryBean.setTypeAliasesPackage(env.getProperty(typeAliasesPackage1));需要使用默认配置文件application.properties
        return sessionFactoryBean;
    }
}

//问题是:读取配置文件的值，和注入配置文件？？？
