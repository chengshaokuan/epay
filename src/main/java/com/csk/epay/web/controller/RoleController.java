package com.csk.epay.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csk.epay.domain.Role;
import com.csk.epay.service.RoleService;
import com.csk.epay.utils.DateUtil;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	@RequestMapping("/index")
	public String index(){
		return "qx/role/index";
	}
	
	@RequestMapping("/list")
	public String list(){
		return "qx/role/list";
	}
	
	@RequestMapping("/add")
	public String add(){
		return "qx/role/add";
	}
	
	@RequestMapping("/edit")
	public String edit(Integer id,Model model){
		model.addAttribute("role",roleService.getById(id));
		return "qx/role/edit";
	}
	
	@RequestMapping("/assign")
	public String assign(Integer id,Model model){
		model.addAttribute("role", roleService.getById(id));
		return "qx/role/assign";
	}
	
	@RequestMapping("/detail")
	public String detail(Integer id,Model model){
		model.addAttribute("role", roleService.getById(id));
		return "qx/role/detail";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(Role role){
		Map<String, Object> jsonMap = new HashMap<String,Object>();
		role.setCreateTime(DateUtil.getSystemTime());
		try {
			roleService.save(role);
			jsonMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("success", false);
		}
		return jsonMap;
	}
	
	
	@RequestMapping("/getByPage")
	@ResponseBody
	public Object getByPage(Integer pageNo,Integer pageSize){
		//{"total":100,"dataList":[{"id":"","code":"","name":"","remark":""},{},{}]}
		return roleService.getByPage(pageNo, pageSize);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer[] ids){
		//{"success":true} 成功 {"success":false} 失败
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		
		try {
			roleService.delele(ids);
			jsonMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("success", false);
		}
		
		return jsonMap;
	}
	@RequestMapping("/update")
	@ResponseBody
	public Object update(Role role){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		role.setEditTime(DateUtil.getSystemTime());
		try {
			roleService.update(role);
			jsonMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("success", false);
		}
		return jsonMap;
	}
	
}
