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
* @create: 2020-04-03 10:15
**/

@Data
@TableName(value = "sys_log")
@EqualsAndHashCode(callSuper = true)
public class SysLog extends BaseEntity<SysLog> implements Serializable {


    /**
     * 请求类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 日志标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 操作IP地址
     */
    @TableField(value = "remote_addr")
    private String remoteAddr;

    /**
     * 操作用户昵称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 请求URI
     */
    @TableField(value = "request_uri")
    private String requestUri;

    /**
     * 操作方式
     */
    @TableField(value = "http_method")
    private String httpMethod;

    /**
     * 请求类型.方法
     */
    @TableField(value = "class_method")
    private String classMethod;

    /**
     * 操作提交的数据
     */
    @TableField(value = "params")
    private String params;

    /**
     * sessionId
     */
    @TableField(value = "session_id")
    private String sessionId;

    /**
     * 返回内容
     */
    @TableField(value = "response")
    private String response;

    /**
     * 方法执行时间
     */
    @TableField(value = "use_time")
    private Long useTime;

    /**
     * 浏览器信息
     */
    @TableField(value = "browser")
    private String browser;

    /**
     * 地区
     */
    @TableField(value = "area")
    private String area;

    /**
     * 省
     */
    @TableField(value = "province")
    private String province;

    /**
     * 市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 网络服务提供商
     */
    @TableField(value = "isp")
    private String isp;

    /**
     * 异常信息
     */
    @TableField(value = "exception")
    private String exception;



    @TableField(value = "remarks")
    private String remarks;

}