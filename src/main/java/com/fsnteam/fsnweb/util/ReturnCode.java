package com.fsnteam.fsnweb.util;

public enum  ReturnCode implements IReturnCode {
    SUCCESS(200,"操作成功"),
    LOST(400,"资源未找到"),
    SERVERERROR(500,"服务器内部错误"),
    NOT_LOGIN(300,"未登录"),
    ARITHMETIC_EXCEPTION(501,"服务器内部计算异常"),
    USER_NOT_FOUND(301,"未找到该用户"),
    ;

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
