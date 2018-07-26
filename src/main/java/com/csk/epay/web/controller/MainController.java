package com.csk.epay.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.csk.epay.domain.User;
import com.csk.epay.exceptions.ApplicationException;
import com.csk.epay.service.UserService;
import com.csk.epay.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("main")
public class MainController {

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping("/main")
    public String main () {
        return "main";
    }

    @RequestMapping("/changepwd")
    public String changepwd () {
        return "changepwd";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Object login (String accountNo, String password) {
        //{"success":true}成功  {"success":false,"errMsg":""} 失败
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            User user = userService.login(accountNo, MD5Util.MD5(password));

            //如果认证成功,需要将用户信息放到session
//			request.getSession().setAttribute(Constant.SESSION_USER, user);
            //获取当前这个用户能够操作的所有urls
            Set<String> urls = userService.getUrlsByUserId(user.getId());

//			request.getSession().setAttribute(Constant.URLS, urls);
            jsonMap.put("success", true);
        } catch (ApplicationException e) {
            jsonMap.put("success", false);
            jsonMap.put("errMsg", e.getMessage());
        }
        return jsonMap;
    }
}
