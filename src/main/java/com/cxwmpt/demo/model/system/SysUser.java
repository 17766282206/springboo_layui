package com.cxwmpt.demo.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.cxwmpt.demo.annotation.SysDict;
import com.cxwmpt.demo.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* Created by Mybatis Generator 2020/04/07
*/
@Data
@TableName(value = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity<SysUser> implements Serializable {


    /**
     * 用户登录帐号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户姓名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 性别  1:男2:女3:未知
     */
    @TableField(value = "sex")
    @SysDict(dictCode = "sex")
    private String sex;

    /**
     * 用户状态 0:正常1:停用 2:锁定
     */
    @TableField(value = "status")
    @SysDict(dictCode = "status")
    private String status;

    /**
     * 所属部门流水号
     */
    @TableField(value = "company_id")
    private String companyId;

    /**
     * 联系电话
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * QQ号码
     */
    @TableField(value = "qq")
    private String qq;

    /**
     * 微信
     */
    @TableField(value = "wechat")
    private String wechat;

    /**
     * 电子邮件
     */
    @TableField(value = "email")
    private String email;

    /**
     * 身份证号
     */
    @TableField(value = "userNo")
    private String userNo;

    /**
     * 联系地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 角色集合
     */
    @TableField(exist=false)
    private Set<SysRole> roleLists = new HashSet<SysRole>();

    /**
     * 菜单集合
     */
    @TableField(exist=false)
    private Set<SysMenu> menus= new HashSet<SysMenu>();

}