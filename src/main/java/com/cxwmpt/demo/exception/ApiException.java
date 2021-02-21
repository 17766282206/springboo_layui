package com.cxwmpt.demo.exception;

import com.cxwmpt.demo.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义api返回接口异常
 * @author Administrator
 */
@Data
public class ApiException extends RuntimeException {


    private Integer code;
    private String  message;
    public ApiException(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    public ApiException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

}