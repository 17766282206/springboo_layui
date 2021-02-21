package com.cxwmpt.demo.mybatisPlue;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 字段自动填充
 * @author Administrator
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 新增的时候自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createDate")) {
            this.setFieldValByName("createDate",new Date(),metaObject);

        }
        if (metaObject.hasGetter("updateDate")) {
            this.setFieldValByName("updateDate", new Date(), metaObject);
        }
    }



    /**
     * 更新的税后自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateDate")) {
            this.setFieldValByName("updateDate", new Date(),metaObject);

        }
    }
}