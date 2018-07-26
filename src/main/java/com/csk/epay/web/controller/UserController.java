package com.csk.epay.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csk.epay.domain.Role;
import com.csk.epay.domain.User;
import com.csk.epay.service.UserRoleRelationService;
import com.csk.epay.service.UserService;
import com.csk.epay.utils.DateUtil;
import com.csk.epay.utils.MD5Util;
import com.csk.epay.vo.PaginationVO;
import com.csk.epay.vo.UserCondition;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="userRoleRelationService")
	private UserRoleRelationService userRoleRelationService;
	
	@RequestMapping("/index")
	public String index(){
		return "qx/user/index";
	}
	
	@RequestMapping("/list")
	public String list(){
		return "qx/user/list";
	}
	
	@RequestMapping("/add")
	public String add(){
		return "qx/user/add";
	}
	
	@RequestMapping("/edit")
	public String edit(){
		return "qx/user/edit";
	}
	
	@RequestMapping("/assign")
	@ResponseBody
	public String assign(Integer id,Model model){
		//获取用户信息
		User user = userService.getById(id);
		model.addAttribute("user", user);
		
		//获取当前用户已经分配的角色列表
		List<Role> assignedRoles = userRoleRelationService.getAssignedRolesByUserId(id);
		model.addAttribute("assignedRoles", assignedRoles);
		
		//获取当前用户未分配的角色列表
		List<Role> unAssignedRoles = userRoleRelationService.getUnAssignedRolesByUserId(id);
		model.addAttribute("unAssignedRoles", unAssignedRoles);
		return "qx/user/assign";
	}
	
	@RequestMapping("/detail")
	public String detail(Integer id,Model model){
		model.addAttribute("user", userService.getById(id));
		return "qx/user/detail";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(User user){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		try {
			user.setCreateTime(DateUtil.getSystemTime());
			user.setPassword(MD5Util.MD5(user.getPassword()));
			userService.save(user);
			jsonMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("success", false);
		}
		return jsonMap; 
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer[] ids){
		//{"success":true} 成功 {"success":false} 失败
		Map<String,Object> jsonMap = new HashMap<String,Object>();

		try {
			userService.delete(ids);
			jsonMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("success", false);
		}

		return jsonMap;
	}
	
	@RequestMapping("/getByPage")
	@ResponseBody
	public Object getByPage(UserCondition userCondition){
		//{"total":100,"dataList":[{"id":"","accountNo":"","name":"","createTime":"","expireTime":"","allowIps":"","lockStatusText":""},{},{}]}
		PaginationVO<User> paginationVO = userService.getByPage(userCondition);
		return paginationVO;
	}
}


