package com.csk.epay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.csk.epay.domain.Permission;
import com.csk.epay.service.PermissionService;


@RunWith(SpringJUnit4ClassRunner.class)//指定spring测试环境
//指定spring配置文件的路径   /代表classpath
@ContextConfiguration({"/spring-base.xml","/spring-service.xml"})
public class PermissionServiceImplTest {

	@Resource(name="permissionService")
	private PermissionService permissionService;
	
	@Before
	public void init(){
		System.out.println("start");
	}
	
	@After
	public void destory(){
		System.out.println("end");
	}
	
	
	@Test
	public void testSave() {
		Permission permission = new Permission();
		permission.setCode("001");
		permission.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		permission.setModuleUrl("a");
		permission.setName("车辆维护");
		permission.setOperationUrl("aaa");
		permission.setOrderNo(1);
		permission.setPid(null);
		permissionService.save(permission);
		
		Permission permission1 = new Permission();
		permission1.setCode("001001");
		permission1.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		permission1.setModuleUrl("b");
		permission1.setName("新增车辆");
		permission1.setOperationUrl("bbb");
		permission1.setOrderNo(1);
		permission1.setPid(permission.getId());
		permissionService.save(permission1);
		
		Permission permission2 = new Permission();
		permission2.setCode("001002");
		permission2.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		permission2.setModuleUrl("c");
		permission2.setName("删除车辆");
		permission2.setOperationUrl("ccc");
		permission2.setOrderNo(1);
		permission2.setPid(permission.getId());
		permissionService.save(permission2);
		
		Permission permission3 = new Permission();
		permission3.setCode("001003");
		permission3.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		permission3.setModuleUrl("d");
		permission3.setName("修改车辆");
		permission3.setOperationUrl("ddd");
		permission3.setOrderNo(1);
		permission3.setPid(permission.getId());
		permissionService.save(permission3);
		
		
	}

	@Test
	public void testDeleteById() {
		permissionService.deleteById(4);
	}

	@Test
	public void testGetById() {
		Permission permission = permissionService.getById(3);
		System.out.println(permission.getName());
	}

	@Test
	public void testUpdate() {
		Permission permission = permissionService.getById(3);
		permission.setName("删除车辆111");
		permissionService.update(permission);
	}

	@Test
	public void testGetPermissionTree() {
		String jsonString = permissionService.getPermissionTree();
		System.out.println(jsonString);
	}

}
