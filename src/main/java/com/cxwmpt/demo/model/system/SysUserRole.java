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
* @create: 2020-04-08 17:01
**/
@Data
@TableName(value = "sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity implements Serializable {
    /**
     * 用户编号
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 权限编号
     */
    @TableField(value = "role_id")
    private String roleId;

    private static final long serialVersionUID = 1L;
}