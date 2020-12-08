package com.david.module.util.constanthash;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 原理见 https://juejin.im/post/5cfdf4e5f265da1bd260e04c
 * 没有考虑虚拟node的情况
 */
public class ConstantHashRing {

    private Integer ringLength = Integer.MAX_VALUE;

    TreeMap<Integer, Node> constantHashRing;

    public ConstantHashRing() {
        constantHashRing = new TreeMap<>();
    }

    public void addNode(Node node) {

        String key = node.getKey();
        Integer keyHash = key.hashCode();

        if (constantHashRing.get(keyHash) == null) {
            constantHashRing.put(keyHash, node);
        } else {

        }
    }

    public Node getNode(String key) {

        Integer keyHash = key.hashCode();
        return constantHashRing.get(keyHash);
    }

    /**
     * 获取一致性Hash值
     *
     * @param key
     */
    public Node clientGet(String key) {
        Integer keyHash = hashCode(key);
        SortedMap<Integer, Node> tailMap = constantHashRing.tailMap(keyHash);

        Integer first = tailMap.isEmpty() ? constantHashRing.firstKey() : tailMap.firstKey();
        return constantHashRing.get(first);

    }

    private Integer hashCode(String key) {
        return key.hashCode();
    }

    public static void main(String[] args) {

        Node node = new Node();
        node.setKey("a");
        node.setName("节点a");

        Node nodec = new Node();
        nodec.setKey("c");
        nodec.setName("节点b");

        ConstantHashRing constantHashRing = new ConstantHashRing();
        constantHashRing.addNode(node);
        constantHashRing.addNode(nodec);


        /**
         * 即使增加了一个f节点，下面的客户端的b也映射到了c节点上
         */
        Node nodef = new Node();
        nodef.setKey("f");
        nodef.setName("节点f");
        constantHashRing.addNode(nodef);

        Node node3 = constantHashRing.clientGet("b");

        System.out.print("");


    }

}
