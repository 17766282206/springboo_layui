package com.cxwmpt.demo.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cxwmpt.demo.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
* @program: backend
*
* @description
*
* @author: YouName
*
* @create: 2020-04-08 17:02
**/
@Data
@TableName(value = "sys_menu")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity<SysMenu> implements Serializable {
    /**
     * 菜单父级编号
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 菜单编码
     */
    @TableField(value = "menu_code")
    private String menuCode;

    /**
     * 图标名称
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * url地址
     */
    @TableField(value = "href")
    private String href;

    /**
     * 菜单类型(0:停用;1:启用)
     */
    @TableField(value = "type")
    private String type;

    /**
     * 权限
     */
    @TableField(value = "permission")
    private String permission;

    /**
     * 排序号
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    private static final long serialVersionUID = 1L;
}