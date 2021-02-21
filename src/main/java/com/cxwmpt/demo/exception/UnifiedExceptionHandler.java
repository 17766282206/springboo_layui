package com.cxwmpt.demo.exception;



import com.cxwmpt.demo.common.result.ResultMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 统一异常返回
 * @author Administrator
 */
@ControllerAdvice
public class UnifiedExceptionHandler {



    /**
     * 捕获api抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResultMessage apiException(ApiException e) {
        return ResultMessage.error(e.getCode(),e.getMessage());
    }


}
