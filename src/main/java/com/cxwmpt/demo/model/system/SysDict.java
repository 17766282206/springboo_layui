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
@TableName(value = "sys_dict")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseEntity<SysDict> implements Serializable {
    /**
     * 所属字典流水号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 字典对照码
     */
    @TableField(value = "name")
    private String name;

    /**
     * 当前状态(0:正常;1:禁用)
     */
    @TableField(value = "status")
    @com.cxwmpt.demo.annotation.SysDict(dictCode = "status")
    private String status;

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
}