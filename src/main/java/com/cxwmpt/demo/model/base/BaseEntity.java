package com.cxwmpt.demo.model.base;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: backend
 * @description
 * @author: YouName
 * @create: 2020-04-03 10:55
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntity<T extends Model> extends Model {

    /**
     * 实体编号（唯一标识）
     */
    @TableId(value = "id",type= IdType.UUID)
    protected String id;

    /**
     *  创建者
     */

    @TableField(value = "create_id")
    protected String createId;

    /**
     * 创建日期
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date createDate;

    /**
     * 更新者
     */
    @TableField(value = "update_id")
    protected String updateId;

    /**
     * 更新日期
     */
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    protected Date updateDate;

    /**
     * 逻辑删除（Y：正常；N：删除；A：审核；）
     */
    @TableField(value = "del_flag")
    protected Boolean delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return null != this.getId() && this.getId().equals(that.getId());
    }



}


