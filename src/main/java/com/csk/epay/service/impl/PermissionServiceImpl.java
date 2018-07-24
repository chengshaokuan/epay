package com.csk.epay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csk.epay.dao.PermissionDao;
import com.csk.epay.dao.RolePermissionRelationDao;
import com.csk.epay.domain.Permission;
import com.csk.epay.service.PermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Resource(name="permissionDao")
	private PermissionDao permissionDao;
	
	@Resource(name="rolePermissionRelationDao")
	private RolePermissionRelationDao rolePermissionRelationDao; 

	@Override
	public void save(Permission permission) {
		permissionDao.save(permission);
	}

	@Override
	public void deleteById(Integer id) {
		permissionDao.deleteById(id);
	}

	@Override
	public Permission getById(Integer id) {
		return permissionDao.getById(id);
	}

	@Override
	public void update(Permission permission) {
		permissionDao.update(permission);
	}

	@Override
	public String getPermissionTree() {
		List<Permission> permissions = permissionDao.getAll();
		StringBuilder jsonString = new StringBuilder(200);
		jsonString.append("[");
		for (Permission permission : permissions) {
			Integer pid = permission.getPid()==null?0:permission.getPid();
			jsonString.append("{\"id\":\""+permission.getId()+"\",\"pId\":\""+pid+"\",\"name\":\""+permission.getName()+"\",\"open\":true},");
		}
		jsonString.append("]");
		return jsonString.toString().replace(",]", "]");
	}

	@Override
	public Permission getByCode(String code) {
		return permissionDao.getByCode(code);
	}

	@Override
	public Permission getByNameAndPid(String name, Integer pid) {
		return permissionDao.getByNameAndPid(name,pid);
	}

	@Override
	public List<Map<String, Object>> getCheckboxTree(Integer roleId) {
		//[{id:"000",pId:"0",name:"外汇局业务办公系统",open:true},{id:"7001",pId:"700",name:"公告管理"}]
		List<Map<String,Object>> jsonList = new ArrayList<Map<String,Object>>();
		
		//根据角色id获取当前这个角色拥有的所有许可ids
		List<Integer> permissionIds = rolePermissionRelationDao.getPermissionIdsByRoleId(roleId);
		
		//获取所有许可
		List<Permission> permissions = permissionDao.getAll();
		for (Permission permission : permissions) {
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("id", permission.getId().toString());
			jsonMap.put("pId", permission.getPid()==null?"0":permission.getPid().toString());
			jsonMap.put("name", permission.getName());
			jsonMap.put("open", true);
			//如果当前这个角色有这个许可,那么打勾
			if(permissionIds.contains(permission.getId())){
				jsonMap.put("checked", true);				
			}
			jsonList.add(jsonMap);
		}
		return jsonList;
	}

	@Override
	public List<Map<String, Object>> getDetailTree(Integer roleId) {
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		
		//根据角色id获取当前这个角色拥有的所有许可ids
		List<Integer> permissionIds = rolePermissionRelationDao.getPermissionIdsByRoleId(roleId);
		
		//获取所有许可
		List<Permission> permissions = permissionDao.getAll();
		for (Permission permission : permissions) {
			//判断当前角色是否用这个许可 
			if(permissionIds.contains(permission.getId())){
				Map<String,Object> jsonMap = new HashMap<String,Object>();
				jsonMap.put("id", permission.getId().toString());
				jsonMap.put("pId", permission.getPid()==null?"0":permission.getPid().toString());
				jsonMap.put("name", permission.getName());
				jsonMap.put("open", true);
				jsonList.add(jsonMap);
			}
		}
		return jsonList;
	}

	@Override
	public List<Map<String, Object>> getFinalTree(Integer userId) {
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		
		//根据用户id获取当前这个用户拥有的所有许可
		List<Permission> permissions = permissionDao.getPermissionsByUserId(userId);
		for (Permission permission : permissions) {
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("id", permission.getId().toString());
			jsonMap.put("pId", permission.getPid()==null?"0":permission.getPid().toString());
			jsonMap.put("name", permission.getName());
			jsonMap.put("open", true);
			jsonList.add(jsonMap);
		}
		return jsonList;
	}

	@Override
	public List<Map<String, Object>> getMenuTree(Integer userId) {
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		//根据用户id获取当前这个用户拥有的所有许可
		List<Permission> permissions = permissionDao.getPermissionsByUserId(userId);
		for (Permission permission : permissions) {
			if(permission.getChildNodes().size()>0){
				Map<String,Object> jsonMap = new HashMap<String,Object>();
				jsonMap.put("id", permission.getId().toString());
				jsonMap.put("name", permission.getName());
				jsonMap.put("pId", permission.getPid()==null?"0":permission.getPid().toString());
				jsonMap.put("open", true);
				if(permission.getModuleUrl()!=null){
					jsonMap.put("url", permission.getModuleUrl());				
					jsonMap.put("target", "navTab");
				}
				jsonList.add(jsonMap);				
			}
		}
		return jsonList;
	}

}
