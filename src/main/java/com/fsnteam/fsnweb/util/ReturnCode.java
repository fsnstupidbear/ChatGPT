package com.fsnteam.fsnweb.util;

public enum  ReturnCode implements IReturnCode {
    SUCCESS(200,"操作成功"),
    LOST(400,"资源未找到"),
    SERVERERROR(500,"服务器内部错误"),
    USER_ACCOUNT_DISABLE(308,"账号被禁用"),
    ARITHMETIC_EXCEPTION(501,"服务器内部计算异常"),
    USER_NOT_FOUND(301,"未找到该用户"),
    USER_CREDENTIALS_ERROR(302,"密码错误"),
    USER_ACCOUNT_EXPIRED(303,"账号过期"),
    USER_ACCOUNT_NOT_EXIST(304,"用户不存在"),
    COMMON_FAIL(305,"其他错误"),
    USER_ACCOUNT_LOCKED(306,"账号锁定"),
    AUTHORITY_ERROR(307,"无访问权限");

    private Integer code;

    private String message;

    ReturnCode(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
