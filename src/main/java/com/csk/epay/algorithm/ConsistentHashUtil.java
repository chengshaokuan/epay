package com.csk.epay.algorithm;

import com.csk.epay.utils.util.SnowFlake;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @program: epay
 * @description:
 * @author: Mr.Cheng
 * @create: 2019-01-18 14:50
 **/
public class ConsistentHashUtil {

    private static final SortedMap<Integer, String> circle = new TreeMap<>();

    static {
        ConsistentHashUtil.add(5,"tbl_user_");
    }

    /**
     * @Description: 改进的32位FNV算法1
     * @param: data
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 11:59 2019/1/18
     */
    public static int FNVHash1 (String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        //如果参数是数组，需要改变下面的方法,其他都不需要改变
        //for (byte b : data) {hash = (hash ^ b) * p;}
        for (int i = 0; i < data.length(); i++) {
            hash = (hash ^ data.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    /**
     * @Description: 添加虚拟节点, numberOfReplicas为虚拟节点的数量，初始化hash环的时候传入，我们使用300个虚拟节点
     * @param: node
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:53 2019/1/18
     */
    public static void add (Integer numOfRep,String node) {
        for (int i = 0; i <= numOfRep; i++) {
            circle.put(ConsistentHashUtil.FNVHash1(node + String.valueOf(i)),String.valueOf(i));
        }
    }

    /**
     * @Description: 移除节点
     * @param: node
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:54 2019/1/18
     */
    public void remove (Integer numOfRep,String node) {
        for (int i = 0; i <= numOfRep; i++) {
            circle.remove(ConsistentHashUtil.FNVHash1(node + String.valueOf(i)));
        }
    }

    public static void main (String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 2);
        String id = String.valueOf(snowFlake.nextId());
        System.out.println(id);
        int i = ConsistentHashUtil.FNVHash1(id);
        System.out.println(i);
        String tbl = ConsistentHashUtil.get(i);
        System.out.println(tbl);
    }

    /**
     * @Description:  获得一个最近的顺时针节点
     * @param: key 为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 14:57 2019/1/18
     */
    public static String get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        Integer hash = ConsistentHashUtil.FNVHash1(String.valueOf(key));
        if (!circle.containsKey(hash)) {
            //返回此映射的部分视图，其键大于等于 hash
            SortedMap<Integer,String> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }


}
