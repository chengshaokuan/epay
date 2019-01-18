package com.csk.epay.algorithm.NormalHash;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: epay
 * @description:
 * @author: Mr.Cheng
 * @create: 2019-01-17 10:11
 **/
@Data
public class Node {
    //域名
    private String domain;
    //IP地址
    private String ip;
    //节点存储数据
    private Map<String, Object> data;

    public Node(String domain,String ip){
        this.domain =domain;
        this.ip = ip;
        data = new HashMap<>();
    }

    public <T> void put (String key, T value) {
        data.put(key, value);
    }

    public void remove (String key) {
        data.remove(key);
    }

    public <T> T get (String key) {
        return (T) data.get(key);
    }
}
