package com.cxwmpt.demo.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data//使用这个注解，就不用再去手写Getter,Setter,equals,canEqual,hasCode,toString等方法了，注解后在编译时会自动加进去。
@AllArgsConstructor//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
@NoArgsConstructor//使用后创建一个无参构造函数

public class ResultMessage<T> implements Serializable {

    private boolean status;
    private Integer code;
    private String message;
    private T data;
    private Integer count;
    /**
     * 数据总长度
     */
    private Integer total;


    public static <T> ResultMessage<T> create(boolean status, Integer code, String message, T data,Integer count,Integer total) {
        return new ResultMessage<>(status,code, message,data,count,total);
    }


    public static <T> ResultMessage<T> success() {
        return create(true, 200, "success",null,null,null);
    }
    public static <T> ResultMessage<T> success(String message) {
        return create(true, 200, message,null,null,null);
    }
    public static <T> ResultMessage<T> success(T data) {
        return create(true, 200, "success",data,null,null);
    }
    public static <T> ResultMessage<T> success(String message,T data) {
        return create(true, 200, message,data,null,null);
    }
    public static <T> ResultMessage<T> success(T data,Integer count) {
        return create(true, 200, "success",data,count,null);
    }
    public static <T> ResultMessage<T> success(String message,T data,Integer count,Integer total) {
        return create(true, 200, message,data,count,total);
    }
    public static <T> ResultMessage<T> error(String message) {
        return create(false, -1,message,null,null,null);
    }

    public static <T> ResultMessage<T> error(Integer code,String message) {
        return create(false, code,message,null,null,null);
    }

    public static <T> ResultMessage<T> error(ResultCodeEnum resultCodeEnum) {
        return create(false, resultCodeEnum.getCode(),resultCodeEnum.getMessage(),null,null,null);
    }
    public static <T> ResultMessage<T> error(Integer code,String message,T data) {
        return create(false, code,message,data,null,null);
    }

    public static <T> ResultMessage<T> error(ResultCodeEnum resultCodeEnum,T data) {
        return create(false, resultCodeEnum.getCode(),resultCodeEnum.getMessage(),data,null,null);
    }

}
