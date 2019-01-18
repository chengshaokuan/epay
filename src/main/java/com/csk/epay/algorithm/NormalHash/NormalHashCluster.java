package com.csk.epay.algorithm.NormalHash;

import com.csk.epay.algorithm.NormalHash.Cluster;
import com.csk.epay.algorithm.NormalHash.Node;

import static java.util.Objects.hash;

/**
 * @program: epay
 * @description: 取模
 * @author: Mr.Cheng
 * @create: 2019-01-17 10:19
 **/
public class NormalHashCluster extends Cluster {

    public NormalHashCluster () {
        super();
    }


    @Override
    public void addNode (Node node) {
        this.nodes.add(node);
    }

    @Override
    public void removeNode (Node node) {
        this.nodes.removeIf(o -> o.getIp().equals(node.getIp()) || o.getDomain().equals(node.getDomain()));
    }

    @Override
    public Node getNode (String key) {
        long hash = hash(key);
        long index = hash % nodes.size();
        return nodes.get((int) index);
    }
}
