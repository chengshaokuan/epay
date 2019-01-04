package com.csk.epay.web.controller;

import com.csk.epay.domain.OperationLog;
import com.csk.epay.service.OperationLogService;
import com.csk.epay.vo.LogCondition;
import com.csk.epay.vo.PaginationVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: epay
 * @description: 日志操作
 * @author: Mr.Cheng
 * @create: 2018-07-26 16:53
 **/
@Controller
@RequestMapping("log")
public class LogController {

    @Resource(name = "operationLogService")
    private OperationLogService operationLogService;



    @RequestMapping("/index")
    public String index(){
        return "qx/log/index";
    }

    @RequestMapping("/list")
    public String list(){
        return "qx/log/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Integer[] ids){
        //{"success":true} 成功 {"success":false} 失败
        Map<String,Object> jsonMap = new HashMap<String,Object>();

        try {
            operationLogService.delete(ids);
            jsonMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("success", false);
        }

        return jsonMap;
    }

    
    /** 
     * @Description: 查询操作日志记录
     * @param: 
     * @return: java.util.List<com.csk.epay.domain.OperationLog> 
     * @Author: Mr.Cheng
     * @Date: 17:23 2018/7/26 
     */ 
    @RequestMapping("/getByPage")
    @ResponseBody
    public PaginationVO<OperationLog> selectLog(LogCondition logCondition){
        PaginationVO<OperationLog> paginationVO = operationLogService.selectLog(logCondition);
        return paginationVO;
    }
}
