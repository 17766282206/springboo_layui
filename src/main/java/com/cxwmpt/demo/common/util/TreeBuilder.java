package com.cxwmpt.demo.common.util;


import com.cxwmpt.demo.common.vo.Node;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class TreeBuilder {

    private List<Node> nodes = new ArrayList<>();
    public TreeBuilder(List<Node> nodes) {
        super();
        this.nodes= nodes;
    }


    /**
     *
     * @return
     */
    public List<Node> buildTree() {
        List<Node> treeNodes = new ArrayList<Node>();
        List<Node> rootNodes = getRootNodes();
        for (Node rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }



    /**
     * 递归子节点
     * @param node
     */
    public void buildChildNodes(Node node) {
        List<Node> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for(Node child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);
        }
    }

    /**
     * 获取父节点下所有的子节点
     * @param
     * @param
     * @return
     */
    public List<Node> getChildNodes(Node pnode) {
        List<Node> childNodes = new ArrayList<Node>();
        for (Node n : nodes){
            if (pnode.getId().equals(n.getParentId())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }
    /**
     * 判断是否为根节点
     * @param
     * @param
     * @return
     */
    public boolean rootNode(Node node) {
        boolean isRootNode = true;
        for (Node n : nodes){
            if (node.getParentId().equals(n.getId())) {
                isRootNode= false;
                break;
            }
        }
        return isRootNode;
    }

    /**
     * 获取集合中所有的根节点
     * @param
     * @return
     */
    public List<Node> getRootNodes() {
        List<Node> rootNodes = new ArrayList<Node>();
        for (Node n : nodes){
            if (rootNode(n)) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }








}
