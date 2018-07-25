package com.csk.epay.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csk.epay.domain.Permission;
import com.csk.epay.service.PermissionService;
import com.csk.epay.utils.DateUtil;
import org.springframework.web.bind.annotation.RestController;


/**
 * 如果处理器继承了MultiActionController,可以在处理器中定义多个处理方法
 * 要求:方法的签名遵循如下格式
 * 		public ModelAndView 方法名(HttpServletRequest request,HttpServletRespose response) throws Exception{}
 */
@Controller
@RequestMapping("/permission")
public class PermissionController{
	
	@Resource(name="permissionService")
	private PermissionService permissionService;
	
	@RequestMapping("/index")
	public String index(){
		return "qx/permission/index";
	}
	
	@RequestMapping("/tree")
	public String tree(){
		return "qx/permission/tree";
	}
	
	@RequestMapping("/workarea")
	public String workarea(){
		return "qx/permission/workarea";
	}
											
	@RequestMapping(value="/makePermissionTree",produces="text/json;charset=UTF-8")
	@ResponseBody
	public Object makePermissionTree(String response) throws IOException{
		return permissionService.getPermissionTree();
	}
	
	@RequestMapping("/detail")
	public String detail(Integer id,Model model){
		model.addAttribute("permission", permissionService.getById(id));
		return "qx/permission/detail";
	}
	
	@RequestMapping("/add")
	public String add(){
		return "qx/permission/add";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer id){
		//{"success":true }成功 	{"success":false}失败
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		try {
			permissionService.deleteById(id);
			jsonMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("success", false);
		}
		return jsonMap;
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id,Model model){
		model.addAttribute("permission", permissionService.getById(id));
		return "qx/permission/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(Permission permission){
		//{"success":true,"data":{"id":"","name":"","pid":""}}成功		{"success":false} 失败
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		permission.setCreateTime(DateUtil.getSystemTime());
		try {
			permissionService.save(permission);
			jsonMap.put("success", true);
			Map<String,Object> permissionMap = new HashMap<String,Object>();
			permissionMap.put("id", permission.getId());
			permissionMap.put("name", permission.getName());
			permissionMap.put("pid", permission.getPid());
			jsonMap.put("data", permissionMap);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("success", false);
		}
		
		return jsonMap;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Object update(Permission permission){
		//{"success":true} 成功  {"success":false} 失败
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		permission.setEditTime(DateUtil.getSystemTime());
		try {
			permissionService.update(permission);
			jsonMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("success", false);			
		}
		return jsonMap;
	}
	
	@RequestMapping("/getByCode")
	@ResponseBody
	public Object getByCode(String code){
		//{"success":true} 不重复  校验通过了		{"success":false} 重复 	校验没通过
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		//根据许可的代码获取许可对象
		Permission permission = permissionService.getByCode(code);
		if(permission==null){
			jsonMap.put("success", true);
		}else{
			jsonMap.put("success", false);			
		}
		return jsonMap;
	}
	
	@RequestMapping("/getByNameAndPid")
	@ResponseBody
	public Object getByNameAndPid(String name,Integer pid){
		//{"success":true} 不重复 校验通过  {"success":false} 重复 校验没通过    
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		Permission permission = permissionService.getByNameAndPid(name,pid);
		if(permission ==null){
			jsonMap.put("success", true);
		}else{
			jsonMap.put("success", false);			
		}
		return jsonMap;
	}
	
	@RequestMapping("/makeCheckboxTree")
	@ResponseBody
	public Object makeCheckboxTree(Integer roleId){
		//[{id:"000",pId:"0",name:"外汇局业务办公系统",open:true},{id:"7001",pId:"700",name:"公告管理"}]
		List<Map<String,Object>> jsonList = permissionService.getCheckboxTree(roleId);
		return jsonList;
		
	}
	
	@RequestMapping("/makeDetailTree")
	@ResponseBody
	public Object makeDetailTree(Integer roleId){
		//[{id:"000",pId:"0",name:"外汇局业务办公系统",open:true},{id:"7001",pId:"700",name:"公告管理"}]
		List<Map<String,Object>> jsonList = permissionService.getDetailTree(roleId);
		return jsonList;
		
	}
	
	@RequestMapping("/makeFinalTree")
	@ResponseBody
	public Object makeFinalTree(Integer userId){
		//[{id:"000",pId:"0",name:"外汇局业务办公系统",open:true},{id:"7001",pId:"700",name:"公告管理"}]
		List<Map<String,Object>> jsonList = permissionService.getFinalTree(userId);
		return jsonList;
		
	}
	
	@RequestMapping("/makeMenuTree")
	@ResponseBody
	public Object makeMenuTree(Integer userId){
		//[{id:"000",pId:"0",name:"外汇局业务办公系统",open:true},{id:"7001",pId:"700",name:"公告管理"}]
		List<Map<String,Object>> jsonList = permissionService.getMenuTree(userId);
		return jsonList;
		
	}
	
}
