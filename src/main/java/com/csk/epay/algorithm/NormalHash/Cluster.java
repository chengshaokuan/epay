package com.csk.epay.algorithm.NormalHash;


import java.util.ArrayList;
import java.util.List;

/**
 * @program: epay
 * @description: 集群
 * @author: Mr.Cheng
 * @create: 2019-01-17 10:10
 **/
public abstract class Cluster {

    protected List<Node> nodes;

    public Cluster () {
        this.nodes = new ArrayList<>();
    }

    public abstract void addNode (Node node);

    public abstract void removeNode (Node node);

    public abstract Node getNode (String key);
}
