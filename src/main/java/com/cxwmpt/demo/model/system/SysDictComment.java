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
@TableName(value = "sys_dict_comment")
@EqualsAndHashCode(callSuper = true)
public class SysDictComment extends BaseEntity<SysDictComment> implements Serializable {
    /**
     * 字典标识
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField(value = "code")
    private String code;
    /**
     * 字典名称
     */
    @TableField(value = "name")
    private String name;
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