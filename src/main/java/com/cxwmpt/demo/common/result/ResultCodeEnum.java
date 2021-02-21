package com.cxwmpt.demo.common.result;

/**
 * @author Administrator
 */

public enum  ResultCodeEnum {

    /*** 通用部分 100 - 599***/

    SUCCESS(200, "成功请求"),

    REDIRECT(301, "重定向"),

    NOT_FOUND(404, "资源未找到"),

    SERVER_ERROR(500,"服务器错误"),



    /*** 数据库操作 600-999***/
    GET_PRIMARY_KEY_IS_NULL(600,"根据主键查询数据为空"),

    SEARCH_DATA_IS_NULL(601,"查询数据为空"),

    PASSWORD_ERROR1(602,"密码不正确"),

    STATUS_ERROR1(603,"用户状态异常"),

    OPERATION_DATA_IS_NULL(604,"您的操作数据为空"),

    UPDATE_DATA_IS_NULL(605,"您要编辑的数据为空"),

    UPDATE_NOTNULL_DATE_ERROR(606,"编辑不为空的数据错误"),

    INSERT_NOTNULL_DATE_ERROR(607,"添加不为空的数据错误"),

    DELETE_DATE_ERROR(608,"删除数据失败"),

    ADD_DATE_ERROR(609,"添加数据失败"),

    UPDATE_DATE_ERROR(610,"修改数据失败"),
    /*** 用户 1000-1999***/

    USER_IS_NULL(1000,"用户不存在"),

    PASSWORD_ERROR(2001,"密码不正确"),

    UPDATE_PASSWORD_ERROR(2002,"密码修改失败"),

    STATUS_ERROR(3002,"用户状态异常"),

    OLD_PASSWORD_NOT_NEW_PASSWORD(3003,"您的旧密码和新密码不相同"),
    ;


    /**
     * 响应状态码
     */
    private final Integer  code;
    /**
     * 响应信息
     */
    private final String message;

    ResultCodeEnum(Integer  code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer  getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public String getMessage(Integer code) {
        for (ResultCodeEnum c : ResultCodeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.getMessage();
            }
        }
        return null;
    }
}
