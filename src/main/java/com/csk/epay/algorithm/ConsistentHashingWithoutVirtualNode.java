package com.csk.epay.algorithm;

import com.csk.epay.utils.util.ChineseName;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * @program: epay
 * @description:
 * @author: Mr.Cheng
 * @create: 2019-01-17 10:46
 **/
public class ConsistentHashingWithoutVirtualNode {

    //待添加入Hash环的服务器列表
    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.4:111"};
    //key表示服务器的hash值，value表示服务器
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>();

    //程序初始化，将所有的服务器放入sortedMap中
    static {
        for (String server : servers) {
            int hash = getHash(server);
            System.out.println("[" + server + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash, server);
        }
    }

    /**
     * @Description: 得到应当路由到的结点
     * @param: key
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:21 2019/1/17
     */
    private static String getServer (String key) {
        //得到该key的hash值
        int hash = getHash(key);
        //得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Integer i = sortedMap.firstKey();
            //返回对应的服务器
            return sortedMap.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            return subMap.get(i);
        }
    }

    /**
     * @Description: 使用FNV1_32_HASH算法计算服务器的Hash值(FNV - 1和FNV - 1a版本号),
     * 这里不使用重写hashCode的方法，最终效果没区别
     * FNV-1和FNV-1a算法对于最终生成的哈希值（hash）有一定限制
     * 　1，hash是无符号整型
     * 　2，hash的位数（bits），应该是2的n次方（32，64，128，256，512，1024），一般32位的就够用了。
     * @param: str
     * @return: int 一个n位的unsigned int型hash值
     * @Author: Mr.Cheng
     * @Date: 10:56 2019/1/17
     */
    private static int getHash (String str) {
        //32 bit FNV用于散列的质数 FNV_prime = 2^24 + 2^8 + 0x93 = 16777619
        final int p = 16777619;
        //32 bit 初始的哈希值offset_basis = 2166136261
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        //如果算出来的值为负数则取其绝对值
        hash = Math.abs(hash);
        return hash;
    }


    public static void main (String[] args) {
//        String[] keys = {"太阳", "月亮", "星星","ccc","bbbb","afasdfas234","asfasdfewaef"};
        List<String> list = new ArrayList<>();
        IntStream.range(0, 200).forEach(index -> {
            list.add(ChineseName.getChineseName());
        });
        for (String key : list) {
            System.out.println("[" + key + "]的hash值为" + getHash(key) + ", 被路由到结点[" + getServer(key) + "]"+list.size());
        }
    }
}
