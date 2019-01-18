package com.csk.epay.algorithm.NormalHash;

import java.util.stream.IntStream;

/**
 * @program: epay
 * @description:
 * @author: Mr.Cheng
 * @create: 2019-01-17 10:21
 **/
public class HashTest {


    public static void main (String[] args) {
        int DATA_COUNT = 500;
        String PRE_KEY = "192.168.0.";
        Cluster cluster = new ConsistencyHashCluster();
        cluster.addNode(new Node("c1", "192.168.0.1"));
        cluster.addNode(new Node("c2", "192.168.0.2"));
        cluster.addNode(new Node("c3", "192.168.0.3"));
        cluster.addNode(new Node("c4", "192.168.0.4"));

        IntStream.range(200, DATA_COUNT).forEach(index -> {
            Node node = cluster.getNode(PRE_KEY + index);
            node.put(PRE_KEY + index, "HashTest");
            System.out.println(PRE_KEY+index);
            System.out.println(node.getIp());
        });
        cluster.nodes.forEach(node -> {
            System.out.println("IP:" + node.getIp() + ",数据量:" + node.getData().size());
        });
        //缓存命中率
        long hitCount = IntStream.range(0, DATA_COUNT).
                filter(index -> cluster.getNode(PRE_KEY + index).
                        get(PRE_KEY + index) != null).count();
        System.out.println("缓存命中率：" + hitCount * 1f / DATA_COUNT);
    }
}
