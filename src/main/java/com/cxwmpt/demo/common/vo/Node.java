package com.cxwmpt.demo.common.vo;

import lombok.Data;

import java.util.List;

@Data
public  class Node {
    private String id;
    private String parentId;
    private String menuName;
    private String menuCode;
    private String icon;
    private String href;
    private String type;
    private String permission;
    private List<Node> children;
}