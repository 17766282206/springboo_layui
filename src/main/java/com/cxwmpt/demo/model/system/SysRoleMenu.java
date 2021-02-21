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
@TableName(value = "sys_role_menu")
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenu extends BaseEntity implements Serializable {
    /**
     * 权限编号
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 菜单编号
     */
    @TableField(value = "menu_id")
    private String menuId;

    private static final long serialVersionUID = 1L;
}