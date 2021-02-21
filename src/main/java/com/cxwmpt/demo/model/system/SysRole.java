package com.cxwmpt.demo.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.cxwmpt.demo.annotation.SysDict;
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
* @create: 2020-04-08 17:01
**/
@Data
@TableName(value = "sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity implements Serializable {


    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 当前状态 0启用 1禁用
     */
    @TableField(value = "status")
    @SysDict(dictCode = "status")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;


    private static final long serialVersionUID = 1L;
}