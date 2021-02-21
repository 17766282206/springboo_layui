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
* @create: 2020-04-08 17:02
**/
@Data
@TableName(value = "sys_job")
public class SysJob extends BaseEntity implements Serializable {

    @TableField(value = "job_name")
    private String jobName;

    @TableField(value = "job_group")
    private String jobGroup;

    @TableField(value = "cron_expression")
    private String cronExpression;

    @TableField(value = "job_status")
    @SysDict(dictCode = "jobStatus")
    private String jobStatus;

    @TableField(value = "job_class")
    private String jobClass;

}