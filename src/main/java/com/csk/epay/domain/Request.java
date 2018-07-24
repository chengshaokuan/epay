package com.csk.epay.domain;

import lombok.Data;

/**
 * @program: epay
 * @description: 请求参数
 * @author: Mr.Cheng
 * @create: 2018-07-24 15:05
 **/
@Data
public class Request {


    private String usrname;

    private String password;

    private String remoteAddr;

    private String accountNo;

}
